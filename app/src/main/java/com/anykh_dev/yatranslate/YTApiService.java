package com.anykh_dev.yatranslate;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



interface YTApiService {

    @GET ("api/v1.5/tr.json/translate")
    //TODO добавить выбор направления перевода
    Call<Translation> getTranslate(@Query("key") String key, @Query("lang") String lang,
                                         @Query("text") String text);


}
