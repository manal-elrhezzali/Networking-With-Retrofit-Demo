package me.elrhezzalimanal.networkingwithretrofitdemo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";


    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {

            //to force  Retrofit to send null values
//    Gson gson = new GsonBuilder().serializeNulls().create();//then pass this gson as a parameter to the create method of the GsonConverterFactory

            //to Log HTTP Request and Response, define your own okHttpClient add an Logging Interceptor to it and then make Retrofit use this underlying okHttpClient not the default one
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
