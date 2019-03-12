package com.example.itunesapp.ModelMvp.Presenters;

import com.example.itunesapp.ModelMvp.Model.RockInterfaceApi;
import com.example.itunesapp.ModelMvp.Model.RockSongs;
import com.example.itunesapp.ModelMvp.Rock.DataListRock;
import com.example.itunesapp.ModelMvp.Views.ViewControllerRock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.itunesapp.ModelMvp.Rock.RockAdapterData.rockList;
import static com.example.itunesapp.ModelMvp.Rock.RockFragment.adapterR;
import static com.example.itunesapp.ModelMvp.Rock.RockFragment.rockSongsInScreen;

public class PresenterRock implements PresentRockContract {

    public static Retrofit retrofit;
    ViewControllerRock view;


    public PresenterRock(ViewControllerRock view) {
        this.view = view;
    }

    @Override
    public void initializeRockRetrofit() {
        retrofit=new Retrofit.Builder()
                .baseUrl(view.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getRockData();
    }

    @Override
    public void getRockData() {
        RockInterfaceApi interfaceApi = retrofit.create(RockInterfaceApi.class);
        interfaceApi.getRockSongs().enqueue(new Callback<RockSongs>() {
            @Override
            public void onResponse(Call<RockSongs> call, Response<RockSongs> response) {
                if (response.body() != null) {
                    rockList.clear();
                    for (int moveOn = 0; moveOn < response.body().getResultCount(); moveOn++) {
                        rockList.add(new DataListRock(response.body().getResults().get(moveOn).getArtworkUrl60(),
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
            public void onFailure(Call<RockSongs> call, Throwable t) {

            }
        });
    }
    @Override
    public void loadViews() {
        rockSongsInScreen.setAdapter(adapterR);

    }
    @Override
    public PresenterRock bind() {
        return this;
    }

    @Override
    public void onDestroy() {

    }
}