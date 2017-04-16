package com.anykh_dev.yatranslate;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class RetroYDClient {
    private final String AUTH_KEY = "dict.1.1.20170322T122651Z.c51dfa0fb604bddb.8b16fd9148f80e3146a8804dd843d9ad68c665a8";
    private final String BASE_URL = "https://dictionary.yandex.net/";

    private YDApiService getApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(YDApiService.class);
    }

    //TODO добавить поддержку направления перевода
    void getTranslations(String text, String langFrom, String langTo, Callback<Translations> callback){

        Call<Translations> translationsCall =
                getApiService().getTranslations(AUTH_KEY, langTo, text);

        translationsCall.enqueue(callback);
    }

    void getTranslations (String text, String langTo, Callback<Translations> callback){
        getTranslations(text, null, langTo, callback);
    }

}
