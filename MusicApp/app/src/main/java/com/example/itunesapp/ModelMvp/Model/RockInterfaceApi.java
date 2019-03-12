package com.example.itunesapp.ModelMvp.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RockInterfaceApi {
    @GET("/search?term=rock&media=music&entity=song&limit=50")
    Call<RockSongs> getRockSongs();
}
