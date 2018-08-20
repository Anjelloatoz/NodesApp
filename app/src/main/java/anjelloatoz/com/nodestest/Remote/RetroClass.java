package anjelloatoz.com.nodestest.Remote;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Anjelloatoz on 8/17/18.
 */

public class RetroClass {
    private static String BASE_URL = "https://api.themoviedb.org/3/";

    private static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
    }

    public static APIService getAPIService(){
        return getRetrofitInstance().create(APIService.class);
    }
}
