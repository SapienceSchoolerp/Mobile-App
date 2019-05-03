package com.example.android.schoolapp.Networking;

import com.example.android.schoolapp.model.FuzePhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("/photos")
    Call<List<FuzePhoto>> getAllPhotos();

}
