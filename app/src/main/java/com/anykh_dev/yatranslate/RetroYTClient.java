package com.anykh_dev.yatranslate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class RetroYTClient {
    private static final String AUTH_KEY = "trnsl.1.1.20170322T083423Z.2baf7b3e51e4e51a.a9c7bb2e081d4efa8ba67571d36290024d2a7f08";
    private static final String BASE_URL = "https://translate.yandex.net/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static YTApiService getYTApiService(){
        return getRetrofitInstance().create(YTApiService.class);
    }

    static String getAuthKey() {
        return AUTH_KEY;
    }
}
