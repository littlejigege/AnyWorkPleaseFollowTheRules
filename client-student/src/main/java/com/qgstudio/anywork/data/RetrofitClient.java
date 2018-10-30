package com.qgstudio.anywork.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.qgstudio.anywork.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.qgstudio.anywork.data.ApiStores.API_DEFAULT_URL;

/**
 * Retrofit客户端
 * Created by chenyi on 2017/4/10.
 */

public enum RetrofitClient {

    RETROFIT_CLIENT;

    private static OkHttpClient client = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            })
            //设置超时
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            //错误重连
            .retryOnConnectionFailure(true)
            //添加拦截器
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private Retrofit retrofit;

    /**
     * 获得Retrofit
     *
     * @return
     */
    public Retrofit getRetrofit() {
        //读出ip地址
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences("ip地址", Context.MODE_PRIVATE);
        String baseUrl = sharedPreferences.getString("ip", ApiStores.API_DEFAULT_URL);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 更改IP地址后重新构造Retrofit
     *
     * @return
     */
    public Retrofit setRetrofit() {
        //读出ip地址
        SharedPreferences sharedPreferences = App.getContext().getSharedPreferences("ip地址", Context.MODE_PRIVATE);
        String baseUrl = sharedPreferences.getString("ip", ApiStores.API_DEFAULT_URL);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

