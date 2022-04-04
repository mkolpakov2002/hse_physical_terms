package com.example.physicalterms;

import static com.example.physicalterms.Constants.API_BASE_URL;
import static com.example.physicalterms.Constants.TOKEN;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static App instance;

    private static Retrofit retrofit = null;

    private static ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (retrofit == null) {
            retrofit = ApiClient.getAdapterRefresh();
            apiService = retrofit.create(ApiService.class);
        }
    }

    public static App getInstance() {
        return instance;
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

    public static ApiService getApiService() {
        return apiService;
    }
}
