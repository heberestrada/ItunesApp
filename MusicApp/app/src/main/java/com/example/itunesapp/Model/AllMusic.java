package com.example.itunesapp.Model;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AllMusic implements RealmModel {


    public AllMusic() {
    }


    public static class Classic extends RealmObject {
        private String songName;
        private String songArtist;
        private String songPrice;
        private String currency;

        public String getSongName() {
            return songName;
        }

        public void setSongName(String songName) {
            this.songName = songName;
        }

        public String getSongArtist() {
            return songArtist;
        }

        public void setSongArtist(String songArtist) {
            this.songArtist = songArtist;
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

}
