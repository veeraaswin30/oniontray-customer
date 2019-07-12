package com.app.oniontray.WebService;

import android.content.Context;
import android.util.Log;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Webdata {


    private static DDProgressBarDialog ddProgressBarDialog = null;


    public static String BaseUrl = "http://192.168.1.43:1016/api/";
    private static String NbtBaseUrl = "http://192.168.1.43:1016/api/";

    public static final String helpandSuppord_Url = "http://food.nbtdemo.com/mob-cms/faq";
    public static final String aboutUS_Url = "http://food.nbtdemo.com/mob-cms/about-us";
    public static final String term_condi_Url = "http://food.nbtdemo.com/mob-cms/terms-conditions";
    public static final String privacy_policy_Url = "http://food.nbtdemo.com/mob-cms/privacy-policy";


    private static final String PayPalTokenUrl = "https://api.sandbox.paypal.com/v1/oauth2/";


    public static DDProgressBarDialog getProgressBarDialog(Context context) {
        ddProgressBarDialog = new DDProgressBarDialog(context);
        return ddProgressBarDialog;
    }

    public static Retrofit getRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "auth-token")
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                Log.e("url",""+original.url().toString());
                Log.e("response",new Gson().toJson(response.request()));


                // Customize or return the response
                return response;
            }
        });

        OkHttpClient OkHttpClient = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();

        return retrofit;
    }


    public static Retrofit getPaypalRetrofit(final String headerType) {

        final String authorization = headerType;

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("content-type", "application/x-www-form-urlencoded")
                        .header("Authorization", "" + authorization)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        OkHttpClient OkHttpClient = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PayPalTokenUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();

        return retrofit;
    }


    public static Retrofit getNoteRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Customize the request
                Request request = original.newBuilder()
                        .header("Accept", "application/json")
                        .header("Authorization", "auth-token")
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);

                // Customize or return the response
                return response;
            }
        });

        OkHttpClient OkHttpClient = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NbtBaseUrl)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient)
                .build();

        return retrofit;
    }


}
