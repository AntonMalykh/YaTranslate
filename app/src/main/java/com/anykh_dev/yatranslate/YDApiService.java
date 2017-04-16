package com.anykh_dev.yatranslate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



interface YDApiService {

    @GET ("api/v1/dicservice.json/lookup")
    Call<Translations> getTranslations (@Query("key") String key, @Query("lang") String lang,
                                        @Query("text") String text);
}
