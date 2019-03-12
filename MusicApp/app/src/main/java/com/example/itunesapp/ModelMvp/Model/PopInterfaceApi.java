package com.example.itunesapp.ModelMvp.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PopInterfaceApi {
    @GET("/search?term=pop&media=music&entity=song&limit=50")
    Call<PopSongs> getPopSongs();
}
