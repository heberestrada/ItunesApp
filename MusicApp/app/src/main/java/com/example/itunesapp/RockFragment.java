package com.example.itunesapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itunesapp.Model.Music;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RockFragment extends Fragment {

    final String BASE_URL = "https://itunes.apple.com";
    ArrayList<DataListRock> rockSongsList = new ArrayList<>();
    RecyclerView rockSongsInScreen;
    int moveOn = 0;
    SwipeRefreshLayout layoutRefresher;
    Realm realm;
    RealmConfiguration realmConf;
    RockAdapterData adapter = new RockAdapterData(rockSongsList);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rock, null);

        layoutRefresher = v.findViewById(R.id.swipe_container);
        rockSongsInScreen = v.findViewById(R.id.rockSongs);
        rockSongsInScreen.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        Realm.init(getActivity().getBaseContext());
        realmConf = new RealmConfiguration.Builder().build();

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        CheckConnection();

        layoutRefresher.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        CheckConnection();
                        layoutRefresher.setRefreshing(false);
                    }
                }
        );
        return v;
    }

    public void initializeRetrofitRock() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RockInterfaceApi interfaceApi = retrofit.create(RockInterfaceApi.class);
        interfaceApi.getRockSongs().enqueue(new Callback<RockSongs>() {
            @Override
            public void onResponse(Call<RockSongs> call, Response<RockSongs> response) {
                if (response.body() != null) {

                    rockSongsList.clear();
                    for (moveOn = 0; moveOn < response.body().getResultCount(); moveOn++) {
                        rockSongsList.add(new DataListRock(
                                response.body().getResults().get(moveOn).getArtworkUrl60(),
                                response.body().getResults().get(moveOn).getCollectionName(),
                                response.body().getResults().get(moveOn).getArtistName(),
                                response.body().getResults().get(moveOn).getTrackPrice().toString(),
                                response.body().getResults().get(moveOn).getCurrency(),
                                response.body().getResults().get(moveOn).getPreviewUrl()));
                        saveRockSongs(response.body().getResults().get(moveOn).getCollectionName(),
                                response.body().getResults().get(moveOn).getArtistName(),
                                response.body().getResults().get(moveOn).getTrackPrice().toString(),
                                response.body().getResults().get(moveOn).getCurrency());
                    }
                    Toast.makeText(getActivity().getBaseContext(), "Found " + response.body().getResultCount() + " results.", Toast.LENGTH_SHORT).show();
                }
                rockSongsInScreen.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<RockSongs> call, Throwable t) {

            }
        });
    }

    private void saveRockSongs(final String songNameR,final String songArtistR,final String songPriceR,final String currencyR){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Music songAll= realm.createObject(Music.class);
                songAll.setSongName(songNameR);
                songAll.setSogArtist(songArtistR);
                songAll.setSongPrice(songPriceR);
                songAll.setCurrency(currencyR);
            }
        });
    }
    private void readRockSongs(){
        RealmResults <Music> music = realm.where(Music.class).findAll();
        for(Music track : music){
            rockSongsList.add(new DataListRock("empty",
                    track.getSongName(),
                    track.getSogArtist(),
                    track.getSongPrice(),
                    track.getCurrency(),
                    "empty"
            ));
            Toast.makeText(getActivity().getBaseContext(), "Offline Mode!", Toast.LENGTH_SHORT).show();
        }
        rockSongsInScreen.setAdapter(adapter);
    }
    public  boolean CheckConnection() {

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            initializeRetrofitRock();
            return !ipAddr.equals("connected");
        } catch (Exception e) {
            readRockSongs();
            e.printStackTrace();
            return false;
        }
    }
}
