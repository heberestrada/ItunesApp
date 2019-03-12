package com.example.itunesapp.ModelMvp.Presenters;

import com.example.itunesapp.ModelMvp.Classic.DataListClassic;
import com.example.itunesapp.ModelMvp.Model.ClassicInterfaceApi;
import com.example.itunesapp.ModelMvp.Model.ClassicSongs;
import com.example.itunesapp.ModelMvp.Views.ViewControllerClassic;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.itunesapp.ModelMvp.Classic.ClassicAdapterData.classicList;
import static com.example.itunesapp.ModelMvp.Classic.ClassicFragment.adapterC;
import static com.example.itunesapp.ModelMvp.Classic.ClassicFragment.classicSongsInScreen;

public class PresenterClassic implements PresentClassicContract {

    public static Retrofit retrofit;
    ViewControllerClassic view;


    public PresenterClassic(ViewControllerClassic view) {
        this.view = view;
    }

    @Override
    public void initializeClassicRetrofit() {
        retrofit=new Retrofit.Builder()
                .baseUrl(view.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getClassicData();
    }

    @Override
    public void getClassicData() {
        ClassicInterfaceApi interfaceApi = retrofit.create(ClassicInterfaceApi.class);
        interfaceApi.getClassicSongs().enqueue(new Callback<ClassicSongs>() {
            @Override
            public void onResponse(Call<ClassicSongs> call, Response<ClassicSongs> response) {
                if (response.body() != null) {
                    classicList.clear();
                    for (int moveOn = 0; moveOn < response.body().getResultCount(); moveOn++) {
                        classicList.add(new DataListClassic(response.body().getResults().get(moveOn).getArtworkUrl60(),
                                response.body().getResults().get(moveOn).getCollectionName(),
                                response.body().getResults().get(moveOn).getArtistName(),
                                response.body().getResults().get(moveOn).getTrackPrice().toString(),
                                response.body().getResults().get(moveOn).getCurrency(),
                                response.body().getResults().get(moveOn).getPreviewUrl()));
                    }
                    loadViews();
                }
            }

            @Override
            public void onFailure(Call<ClassicSongs> call, Throwable t) {

            }
        });
    }
    @Override
    public void loadViews() {
        classicSongsInScreen.setAdapter(adapterC);

    }
    @Override
    public PresenterClassic bind() {
        return this;
    }

    @Override
    public void onDestroy() {

    }
}