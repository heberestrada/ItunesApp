package com.example.itunesapp.ModelMvp.Classic;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.itunesapp.Model.Music;
import com.example.itunesapp.ModelMvp.Presenters.PresenterClassic;
import com.example.itunesapp.R;

import java.net.InetAddress;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ClassicFragment extends Fragment {

    final String BASE_URL = "https://itunes.apple.com";
    public static ArrayList<DataListClassic> classicSongsList = new ArrayList<>();
    public static RecyclerView classicSongsInScreen;
    int moveOn = 0;
    SwipeRefreshLayout layoutRefresher;
    Realm realm;
    RealmConfiguration realmConf;
    public static ClassicAdapterData adapterC = new ClassicAdapterData(classicSongsList);
    PresenterClassic presenterClassic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_classic, null);

        layoutRefresher = v.findViewById(R.id.swipe_container);
        classicSongsInScreen = v.findViewById(R.id.classicSongs);
        classicSongsInScreen.setHasFixedSize(true);
        classicSongsInScreen.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        presenterClassic=new PresenterClassic(adapterC);

        Realm.init(getActivity().getBaseContext());
        realmConf = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(realmConf);
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

    private void saveClassicSongs(final String songNameR,final String songArtistR,final String songPriceR,final String currencyR){
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
    private void readClassicSongs(){
        RealmResults <Music> music = realm.where(Music.class).findAll();

        for(Music track : music){
            classicSongsList.add(new DataListClassic("empty",
                    track.getSongName(),
                    track.getSogArtist(),
                    track.getSongPrice(),
                    track.getCurrency(),
                    "empty"
            ));
        }
        //Toast.makeText(getActivity().getBaseContext(), "Offline Mode!", Toast.LENGTH_SHORT).show();
        classicSongsInScreen.setAdapter(adapterC);
    }
    public  boolean CheckConnection() {

        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            presenterClassic.initializeClassicRetrofit();
            return !ipAddr.equals("connected");

        } catch (Exception e) {
            readClassicSongs();

            return false;
        }
    }
}
