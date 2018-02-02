package com.cognizant.aman.busrouteapp.presenter;

import android.content.Context;
import android.view.View;

import com.cognizant.aman.busrouteapp.model.Route;
import com.cognizant.aman.busrouteapp.view.RecyclingImageView;

import java.util.List;

/**
 * Created by aman on 1/2/18.
 */

public interface ListPresenter {

    void onItemClicked(View view);

    void loadImage(String url, RecyclingImageView view);

    void onButtonClicked();

    void onLoadSuccess(List<Route> routeList);

    void onLoadFailed();

    void onCreate();

    void onDestroy();

    void onResume();

    void onPause();
}
