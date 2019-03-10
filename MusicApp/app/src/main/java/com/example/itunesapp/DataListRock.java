package com.example.itunesapp;

import android.graphics.Bitmap;

public class DataListRock {

    private String songImage;
    private String songName;
    private String songArtist;
    private String songPrice;
    private String currency;
    private String preview;

    public DataListRock(String songImage, String songName, String songArtist, String songPrice, String currency, String preview) {
        this.songImage = songImage;
        this.songName = songName;
        this.songArtist = songArtist;
        this.songPrice = songPrice;
        this.currency = currency;
        this.preview = preview;
    }


    public String getSongImage() {
        return songImage;
    }

    public String getSongName() {
        return songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongPrice() {
        return songPrice;
    }

    public String getCurrency() {
        return currency;
    }


    public String getPreview() {
        return preview;
    }

}
