package routeapp.android.aman.com.busroute.rxjava;


import android.content.Context;

import routeapp.android.aman.com.busroute.model.Route;
import routeapp.android.aman.com.busroute.model.RouteList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private Context mContext;
    private static final String ROOT_URL = "http://www.mocky.io/v2/";

    public RestClient(Context context) {
        mContext = context;
    }

    public  List<Route> parseResponse()  {
        //Creating an object of our api interface
        List<Route> mRoute = new ArrayList<>();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        try {
            FetchService api = mRetrofit.create(FetchService.class);
            Call<RouteList> call = api.getRouteList();
            return call.execute().body().getRoutes();
        }catch(Exception e){}
        return mRoute;
    }

}