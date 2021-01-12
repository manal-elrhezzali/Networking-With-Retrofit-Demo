package me.elrhezzalimanal.networkingwithretrofitdemo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                    .addInterceptor(new Interceptor() {
                        @NotNull
                        @Override
                        public Response intercept(@NotNull Chain chain) throws IOException {
                            //get the current request from the chain parameter
                            Request originRequest = chain.request();

                            Request newRequest = originRequest.newBuilder()
                                    //if a header with this key already exists this will replace it,if you want to add multiple headers with the same key use addHeader instead
                                    .header("Interceptor-Header", "Hiii,I am a the global Request Header's value")
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
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
