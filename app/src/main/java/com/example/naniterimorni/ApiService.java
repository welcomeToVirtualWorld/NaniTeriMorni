package com.example.naniterimorni;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("kids_stories_list/")
    Call<Stories> getRhymesData();
}
