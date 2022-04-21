package com.example.naniterimorni;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient2 {
    private static Retrofit retrofit = null;
    private static ApiService2 apiService2 = null;
    private static final String BASE_URL = "http://mapi.trycatchtech.com/v1/kids_stories/";

    private static Retrofit getRetrofit()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ApiService2 getApiService2()
    {
        if(apiService2 == null)
        {
            apiService2 = getRetrofit().create(ApiService2.class);
        }
        return apiService2;
    }
}
