package com.example.itunesapp.ModelMvp.Views;

public interface ViewControllerRock {
    void PopulateData(String songImage,String songName, String songArtist, String songPrice, String currency, String preview);
    String getBaseUrl();
    void showError(String errorMessage);
}
