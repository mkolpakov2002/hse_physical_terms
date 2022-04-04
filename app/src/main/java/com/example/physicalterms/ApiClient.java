package com.example.physicalterms;

import static com.example.physicalterms.Constants.API_BASE_URL;
import static com.example.physicalterms.Constants.TOKEN;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofitRefresh = null;
    private static Retrofit retrofit = null;
    public static Retrofit getAdapterRefresh() {
        if (retrofitRefresh==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);


            // add your other interceptors …
// add logging as last interceptor

            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder
                    .addInterceptor(new Interceptor() {
                        @NonNull
                        @Override
                        public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                            Request request = chain.request();
                            Request.Builder newRequest = request.newBuilder().header("Authorization","Bearer " + TOKEN);
                            return chain.proceed(newRequest.build());
                        }
                    });

            okHttpClientBuilder.addInterceptor(logging);

            retrofitRefresh= new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofitRefresh;
    }

    public static Retrofit getAdapter() {
        if (retrofit==null) {
            final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("Authorization", TOKEN)
                            .build();
                    return chain.proceed(request);
                }
            });

            httpClient.authenticator(new Authenticator() {
                @Nullable
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
//                    if (!response.request().header("Authorization").equals(preferences.getAccessToken()))
//                        return null;

                    // request new key
//                    String accessToken = null;
//                    ApiService apiService = ApiClient.getAdapterRefresh(context).create(ApiService.class);
//                    Call<TokenResponse> call = apiService.requestAccessToken();
//                    try {
//                        retrofit2.Response responseCall = call.execute();
//                        TokenResponse responseRequest = (TokenResponse) responseCall.body();
//                        if (responseRequest != null) {
//                            TokenDataResponse data = responseRequest.getData();
//                            accessToken = data.getAccessToken();
//                            // save new access token
//                            preferences.setAccessToken(accessToken);
//                        }
//                    }catch (Exception ex){
//                        Log.d("ERROR", "onResponse: "+ ex.toString());
//                    }
                    String accessToken = TOKEN;
                    if (accessToken != null)
                        // retry the failed 401 request with new access token
                        return response.request().newBuilder()
                                .header("Authorization", accessToken) // use the new access token
                                .build();
                    else
                        return null;
                }
            });

            retrofit= new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
