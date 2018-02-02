package com.cognizant.aman.busrouteapp.presenter;

import android.content.Context;

import com.cognizant.aman.busrouteapp.model.Route;
import com.cognizant.aman.busrouteapp.rxjava.RestClient;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by aman on 1/2/18.
 */

public class ListInteractorImpl implements ListInteractor {

    private Context mContext;
    private Disposable routeSubscription;
    private RestClient restClient;

    public ListInteractorImpl(Context context){
        mContext = context;
    }

    @Override
    public void callApiService(ApiCallListener apiCallListener) {
        // handle for clicking multiple times continuously
        try {
            restClient = new RestClient(mContext);
            Observable<List<Route>> routeObservable =
                    Observable.fromCallable(() -> restClient.parseResponse());
            routeSubscription = routeObservable.
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(Route -> apiCallListener.onResult(routeSubscription,Route));
        }catch(Exception e){
        }
    }

}
