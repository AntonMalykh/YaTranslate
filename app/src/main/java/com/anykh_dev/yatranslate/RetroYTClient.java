package com.anykh_dev.yatranslate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class RetroYTClient {
    private final String AUTH_KEY = "trnsl.1.1.20170322T083423Z.2baf7b3e51e4e51a.a9c7bb2e081d4efa8ba67571d36290024d2a7f08";
    private final String BASE_URL = "https://translate.yandex.net/";

    private YTApiService getApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(YTApiService.class);
    }

    //TODO добавить выбор направления перевода
    void getTranslation (String text, String langFrom, String langTo, Callback<Translation> callback) {

        Call<Translation> translationCall = getApiService()
                .getTranslate(AUTH_KEY, langTo, text);

        translationCall.enqueue(callback);
    }

    void getTranslation (String text, String langTo, Callback<Translation> callback){
        getTranslation(text, null, langTo, callback);
    }

}
