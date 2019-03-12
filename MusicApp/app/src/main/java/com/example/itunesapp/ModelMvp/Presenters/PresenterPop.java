package com.example.itunesapp.ModelMvp.Presenters;

import com.example.itunesapp.ModelMvp.Model.PopInterfaceApi;
import com.example.itunesapp.ModelMvp.Model.PopSongs;
import com.example.itunesapp.ModelMvp.Pop.DataListPop;
import com.example.itunesapp.ModelMvp.Views.ViewControllerPop;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.itunesapp.ModelMvp.Pop.PopAdapterData.popList;
import static com.example.itunesapp.ModelMvp.Pop.PopFragment.popSongsInScreen;
import static com.example.itunesapp.ModelMvp.Pop.PopFragment.adapterP;

public class PresenterPop implements PresentPopContract {

    public static Retrofit retrofit;
    ViewControllerPop view;


    public PresenterPop(ViewControllerPop view) {
        this.view = view;
    }

    @Override
    public void initializePopRetrofit() {
        retrofit=new Retrofit.Builder()
                .baseUrl(view.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        getPopData();
    }

    @Override
    public void getPopData() {
        PopInterfaceApi interfaceApi = retrofit.create(PopInterfaceApi.class);
        interfaceApi.getPopSongs().enqueue(new Callback<PopSongs>() {
            @Override
            public void onResponse(Call<PopSongs> call, Response<PopSongs> response) {
                if (response.body() != null) {
                    popList.clear();
                    for (int moveOn = 0; moveOn < response.body().getResultCount(); moveOn++) {
                        popList.add(new DataListPop(response.body().getResults().get(moveOn).getArtworkUrl60(),
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
            public void onFailure(Call<PopSongs> call, Throwable t) {

            }
        });
    }
    @Override
    public void loadViews() {
        popSongsInScreen.setAdapter(adapterP);

    }
    @Override
    public PresenterPop bind() {
        return this;
    }

    @Override
    public void onDestroy() {

    }
}