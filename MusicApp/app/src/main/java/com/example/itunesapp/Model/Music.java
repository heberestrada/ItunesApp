package com.example.itunesapp.Model;

import io.realm.RealmObject;

public class Music extends RealmObject {

    private String songName;
    private String sogArtist;
    private String songPrice;
    private String currency;

    public Music() {
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSogArtist() {
        return sogArtist;
    }

    public void setSogArtist(String sogArtist) {
        this.sogArtist = sogArtist;
    }

    public String getSongPrice() {
        return songPrice;
    }

    public void setSongPrice(String songPrice) {
        this.songPrice = songPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
