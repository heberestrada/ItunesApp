package com.example.itunesapp.ModelMvp.Rock;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.itunesapp.MainActivity;
import com.example.itunesapp.Model.Music;
import com.example.itunesapp.ModelMvp.Model.RockInterfaceApi;
import com.example.itunesapp.ModelMvp.Model.RockSongs;
import com.example.itunesapp.ModelMvp.Presenters.PresenterRock;
import com.example.itunesapp.R;

import java.net.InetAddress;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RockFragment extends Fragment {

    public static ArrayList<DataListRock> rockSongsList = new ArrayList<>();
    public static RecyclerView rockSongsInScreen;
    SwipeRefreshLayout layoutRefresher;
    Realm realm;
    RealmConfiguration realmConf;
    public static RockAdapterData adapterR = new RockAdapterData(rockSongsList);
    PresenterRock presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rock, null);

        layoutRefresher = v.findViewById(R.id.swipe_container);
        rockSongsInScreen = v.findViewById(R.id.rockSongs);
        rockSongsInScreen.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

        presenter= new PresenterRock(adapterR);
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
        }

    }

    public  boolean CheckConnection() {

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            presenter.initializeRockRetrofit();

            return !ipAddr.equals("connected");
        } catch (Exception e) {
           // readRockSongs();
            e.printStackTrace();
            return false;
        }
    }
}
