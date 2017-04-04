package com.anykh_dev.yatranslate;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class RetroYDClient {
    private static final String YD_AUTH_KEY = "dict.1.1.20170322T122651Z.c51dfa0fb604bddb.8b16fd9148f80e3146a8804dd843d9ad68c665a8";
    private static final String BASE_URL = "https://dictionary.yandex.net/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    static YDAPIService getYDAPIservice(){
        return getRetrofitInstance().create(YDAPIService.class);
    }

    static String getAuthKey() {
        return YD_AUTH_KEY;
    }
}
