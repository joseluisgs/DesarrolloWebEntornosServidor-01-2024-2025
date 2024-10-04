package dev.joseluisgs.rest;


import com.jakewharton.retrofit2.adapter.reactor.ReactorCallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .addCallAdapterFactory(ReactorCallAdapterFactory.create()) // Para usar Reactor
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // Para usar RxJava
                    .build();
        }
        return retrofit;
    }
}