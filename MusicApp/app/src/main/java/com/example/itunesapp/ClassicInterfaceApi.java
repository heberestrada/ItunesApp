package com.example.itunesapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ClassicInterfaceApi {
    @GET("/search?term=classic&media=music&entity=song&limit=50")
    Call<ClassicSongs> getClassicSongs();
}