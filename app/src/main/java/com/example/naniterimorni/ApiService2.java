package com.example.naniterimorni;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService2 {

    @GET("videos_list")
    Call<List<Display_Rhymes>>getVideosData();

}
