package com.example.itunesapp.ModelMvp.Presenters;

public interface PresentPopContract {
    void initializePopRetrofit();
    void getPopData();
    void loadViews();
    PresenterPop bind();
    void onDestroy();
}
