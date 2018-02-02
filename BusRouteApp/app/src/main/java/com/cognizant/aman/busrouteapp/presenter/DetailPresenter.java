package com.cognizant.aman.busrouteapp.presenter;

import android.view.View;

import com.cognizant.aman.busrouteapp.model.Route;
import com.cognizant.aman.busrouteapp.view.RecyclingImageView;

import java.util.List;

/**
 * Created by aman on 1/2/18.
 */

public interface DetailPresenter {


    void loadImage(String url, RecyclingImageView view);

    void onCreate();

    void onDestroy();

    void onResume();

    void onPause();
}
