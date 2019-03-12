package com.example.itunesapp.ModelMvp.Presenters;

public interface PresentRockContract {
    void initializeRockRetrofit();
    void getRockData();
    void loadViews();
    PresenterRock bind();
    void onDestroy();
}
