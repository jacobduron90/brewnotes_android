package com.android.brewnotes.service;


import com.google.gson.Gson;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jacobduron on 9/4/16.
 */
public class BrewNotesApi{

    private static BrewNotesApi instance;
    private Retrofit retrofit;
    private BrewNotesContract api;

    private BrewNotesApi(){
        createAdapter();
    };

    public static synchronized BrewNotesApi getInstance(){
        if(instance == null){
            instance = new BrewNotesApi();
        }
        return instance;
    }

    private void createAdapter(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://104.131.83.239/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        api = retrofit.create(BrewNotesContract.class);
    }

    public BrewNotesContract getApi(){
        return api;
    }


}
