package com.example.avto.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

//    private static final String BASE_URL = "http://178.172.137.13:5555";
//    private static final String BASE_URL = "http://10.0.2.2:8000";

    public static Retrofit getRetrofitIntance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiBaseUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
