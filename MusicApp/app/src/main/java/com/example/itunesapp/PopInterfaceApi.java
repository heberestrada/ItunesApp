package com.example.itunesapp;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface PopInterfaceApi {
    @GET("/search?term=pop&media=music&entity=song&limit=50")
    Call<PopSongs> getPopSongs();
}
