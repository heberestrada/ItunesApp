package com.example.itunesapp.ModelMvp.Pop;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itunesapp.ModelMvp.Presenters.PresenterPop;
import com.example.itunesapp.R;

import java.util.ArrayList;

public class PopFragment extends Fragment {

    final String BASE_URL="https://itunes.apple.com";
    public static ArrayList<DataListPop> popSongsList= new ArrayList<>();
    public static RecyclerView popSongsInScreen;
    int moveOn=0;

    public static PopAdapterData adapterP = new PopAdapterData(popSongsList);
    SwipeRefreshLayout layoutRefresher;
    PresenterPop presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pop, null);

        popSongsInScreen=v.findViewById(R.id.popSongs);
        popSongsInScreen.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        presenter= new PresenterPop(adapterP);
        presenter.initializePopRetrofit();

        layoutRefresher=v.findViewById(R.id.swipe_container);
        layoutRefresher.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.initializePopRetrofit();
                        layoutRefresher.setRefreshing(false);
                    }
                }
        );

        return v;
    }
}