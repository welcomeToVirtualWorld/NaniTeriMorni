package com.example.naniterimorni;

import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static ApiService apiService = null;
    private static final String BASE_URL ="http://mapi.trycatchtech.com/v1/kids_stories/";

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

    public static ApiService getApiService()
    {
        if(apiService == null)
        {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }

}
