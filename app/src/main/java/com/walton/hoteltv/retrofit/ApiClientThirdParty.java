package com.walton.hoteltv.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClientThirdParty {
    public static final String BASE_URL_1= "http://36.255.68.238:8080/";

    public static Retrofit retrofit=null;



    public static Retrofit getApiClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        if (retrofit==null)
        {
            if (BASE_URL_1!=null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL_1)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();
            }


        }
        return retrofit;
    }
}
