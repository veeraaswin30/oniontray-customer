package com.app.oniontray.WebService;


import java.io.IOException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PaypalServiceGenerator {


    private static final String API_BASE_URL = "https://api.sandbox.paypal.com/v1/oauth2/";

    private static final String API_PAYMENT_BASE_URL = "https://api.sandbox.paypal.com/v1/payments/";

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static final Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit.Builder payment_builder = new Retrofit.Builder()
                    .baseUrl(API_PAYMENT_BASE_URL).addConverterFactory(GsonConverterFactory.create());


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }



    public static <S> S createService(Class<S> serviceClass, String username, String password) {

        if (username != null && password != null) {

            String credentials = username + password;

            final String basic = "Basic QVFJR1c4YVd5cjlYOE9ydmNJTkJySlpEUGlPMGFULUF2R0kwcUFwdmxJRVZBZ3ZTbkNnUUhsTklQdFRFT2h0bDh5bk1fWDNnaE04cFp5S1k6RU9lM2poV3R4RmVoYTFwM3A2b2pReDhCdmdqaEZWN09MWWF6VldXMXZELXNzaEZkUkFLd3o5TjNsVjVPYlptM2lUN1dkNTc0bWFJUVdmTFk=";

//            final String basic = "Basic QVFvMllHNEUwZjV4eVhtNkRjTkJEQjdiWXU3S2tXNGgweG9nSEFWRlJrdzBpYzNOWVQ4Y3Zfam5TLVpreVV6QjFNTlBrMUw0Z0t3TE9keEw6RUtVOWxkWER3bk02OHpCSmVRR2xHcG85TkxBeXdQTXdZMGlvcERjY1ZnOVNPQXFNbkhlS2FFN1B1Qm5lR3AySm5CQjhVa2tTMFRQaS1zS2Q=";


            HttpsTrustManager.allowAllSSL();

            httpClient.hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }


        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
//        retrofit.setClient(client);
        return retrofit.create(serviceClass);
    }


    public static <S> S createPaymentService(Class<S> serviceClass, String access_token) {

        if (access_token != null ) {

            final String basic = "Bearer "+access_token;

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Authorization", basic)
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = payment_builder.client(client).build();
        return retrofit.create(serviceClass);
    }


    private static <S> S createService(Class<S> serviceClass, final AccessToken token) {
        if (token != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .header("Authorization", token.getTokenType() + " " + token.getAccessToken())
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }


    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            } };


            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient = okHttpClient.newBuilder()
                    .sslSocketFactory(sslSocketFactory)
                    .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER).build();
//            okHttpClient.sslSocketFactory(new TlsSocketFactory());

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
