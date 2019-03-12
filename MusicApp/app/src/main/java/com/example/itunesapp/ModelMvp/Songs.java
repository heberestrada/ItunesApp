package com.example.itunesapp.ModelMvp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Response;

public class Songs{

    @SerializedName("response")
    private Response response;

    @SerializedName("code")
    private String code;

    @SerializedName("success")
    private boolean success;

    @SerializedName("songs")
    private List<Songs> songs;

    public void setResponse(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSongs(List<Songs> customers){
        this.songs = customers;
    }

    public List<Songs> getSongs(){
        return songs;
    }
}