package com.example.itunesapp.ModelMvp.Presenters;

public interface PresentClassicContract {
    void initializeClassicRetrofit();
    void getClassicData();
    void loadViews();
    PresenterClassic bind();
    void onDestroy();
}
