package com.example.physicalterms;

import static com.example.physicalterms.Constants.API_BASE_URL;
import static com.example.physicalterms.Constants.TOKEN;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Locale;

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

    static SharedPreferences preferences;

    static String mainLanguage;

    static String learningLanguage;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        if (retrofit == null) {
            retrofit = ApiClient.getAdapterRefresh();
            apiService = retrofit.create(ApiService.class);
        }


        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String name = preferences.getString("isFirstLaunch", "1");
        if(name.equals("1")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("isFirstLaunch","0");
            editor.putString("mainLanguage","en");
            editor.putString("learningLanguage","ru");
            learningLanguage = "ru";
            editor.apply();
        }
        mainLanguage = preferences.getString("mainLanguage", "en");
        learningLanguage = preferences.getString("mainLanguage", "ru");
    }

    public static void setLocale(Activity activity) {
        Locale locale = new Locale(mainLanguage);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
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
