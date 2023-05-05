package com.leo_escobar.pruebafinal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPrueba {

    private static RetrofitPrueba instance = null;

    private String BASE_URL = "https://prueba-backend-node-production.up.railway.app/api/";
    ServiceRetrofit apiPost;
    Retrofit retrofit;
    public RetrofitPrueba(){
        buildRetrofit();
    }
    private void buildRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPost = retrofit.create(ServiceRetrofit.class);
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }

    public ServiceRetrofit getService(){
        return this.apiPost;
    }

    public static synchronized RetrofitPrueba getInstance(){
        if(instance == null){
            instance = new RetrofitPrueba();
        }
        return instance;
    }

}
