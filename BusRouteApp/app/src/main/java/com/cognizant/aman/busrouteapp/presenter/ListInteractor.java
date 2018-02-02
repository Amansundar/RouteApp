package com.cognizant.aman.busrouteapp.presenter;

import com.cognizant.aman.busrouteapp.model.Route;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by aman on 2/2/18.
 */

public interface ListInteractor {

     interface ApiCallListener{
         public void onResult(Disposable routeSubscription,List<Route> routeList);
     }

     void callApiService(ApiCallListener apiCallListener);
}
