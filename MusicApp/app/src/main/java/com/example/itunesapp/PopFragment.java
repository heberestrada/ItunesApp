package com.example.itunesapp;



import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopFragment extends Fragment {

    final String BASE_URL="https://itunes.apple.com";
    ArrayList<DataListPop> popSongsList= new ArrayList<>();
    RecyclerView popSongsInScreen;
    int moveOn=0;

    PopAdapterData adapter = new PopAdapterData(popSongsList);
    SwipeRefreshLayout layoutRefresher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pop, null);

        popSongsInScreen=v.findViewById(R.id.popSongs);
        popSongsInScreen.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        initializeRetrofitPop();

        layoutRefresher=v.findViewById(R.id.swipe_container);
        layoutRefresher.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        initializeRetrofitPop();
                        layoutRefresher.setRefreshing(false);
                    }
                }
        );

        return v;
    }

    public void initializeRetrofitPop(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PopInterfaceApi interfaceApi = retrofit.create(PopInterfaceApi.class);
        interfaceApi.getPopSongs().enqueue(new Callback<PopSongs>() {
            @Override
            public void onResponse(Call<PopSongs> call, Response<PopSongs> response) {
                if (response.body() != null ) {
                    popSongsList.clear();
                    for (moveOn = 0; moveOn < response.body().getResultCount(); moveOn++) {
                        popSongsList.add(new DataListPop(
                                response.body().getResults().get(moveOn).getArtworkUrl60(),
                                response.body().getResults().get(moveOn).getCollectionName(),
                                response.body().getResults().get(moveOn).getArtistName(),
                                response.body().getResults().get(moveOn).getTrackPrice().toString(),
                                response.body().getResults().get(moveOn).getCurrency(),
                                response.body().getResults().get(moveOn).getPreviewUrl()));



                    }
                    Toast.makeText(getActivity().getBaseContext(), "Found " + response.body().getResultCount() + " results.", Toast.LENGTH_SHORT).show();
                }
                popSongsInScreen.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<PopSongs> call, Throwable t) {
                //Toast.makeText(getActivity().getBaseContext(), "No data found!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}