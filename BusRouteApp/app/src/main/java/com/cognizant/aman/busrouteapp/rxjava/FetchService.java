package com.cognizant.aman.busrouteapp.rxjava;

import com.cognizant.aman.busrouteapp.model.RouteList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by aman on 1/2/18.
 */

public interface FetchService {

        @GET("5808f00d10000005074c6340.json")
        Call<RouteList> getRouteList();
}
