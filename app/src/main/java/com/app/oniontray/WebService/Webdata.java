package com.app.oniontray.WebService;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.app.oniontray.AppControler.onionTray;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.InternetConnectionListener;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.facebook.FacebookSdk.getCacheDir;


public class Webdata {


    private static DDProgressBarDialog ddProgressBarDialog = null;


    //    public static String BaseUrl = "http://192.168.1.43:1016/api/";
    public static String MainUrl = "http://oniontray.nbtdemo.com/";
    //private static String MainUrl = "http://192.168.1.110:1009/";
    //private static String MainUrl = "http://192.168.1.111:8096/";
    public static String BaseUrl = MainUrl + "api/";
    //    private static String NbtBaseUrl = "http://192.168.1.43:1016/api/";
    private static String NbtBaseUrl = MainUrl + "api/  ";

    public static final String helpandSuppord_Url = MainUrl + "mob-cms/faq";
    public static final String aboutUS_Url = MainUrl + "mob-cms/about-us";
    public static final String term_condi_Url = MainUrl + "mob-cms/terms-conditions";
    public static final String privacy_policy_Url = MainUrl + "mob-cms/privacy-policy";

    private static InternetConnectionListener mInternetConnectionListener;

    private static final String PayPalTokenUrl = "https://api.sandbox.paypal.com/v1/oauth2/";
    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB


    public static DDProgressBarDialog getProgressBarDialog(Context context) {
        ddProgressBarDialog = new DDProgressBarDialog(context);
        return ddProgressBarDialog;
    }

    public static Retrofit getRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        httpClient.cache(getCache());

        httpClient.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return onionTray.isInternetAvailable();
            }

            @Override
            public void onInternetUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onInternetUnavailable();
                }
            }

            @Override
            public void onCacheUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onCacheUnavailable();
                }
            }


        });

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

                Log.e("url", "" + original.url().toString());
                Log.e("response", new Gson().toJson(response.request()));


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


    public static void setInternetConnectionListener(InternetConnectionListener listener) {
        mInternetConnectionListener = listener;
    }

    public static void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }

    public static Cache getCache() {
        File cacheDir = new File(getCacheDir(), "cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return cache;
    }

}
