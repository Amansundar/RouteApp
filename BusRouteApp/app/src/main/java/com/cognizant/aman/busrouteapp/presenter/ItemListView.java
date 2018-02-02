package com.cognizant.aman.busrouteapp.presenter;

import android.content.Context;

import com.cognizant.aman.busrouteapp.model.Route;

import java.util.List;

/**
 * Created by aman on 1/2/18.
 */

public interface ItemListView {

    void showProgress();

    void hideProgress();

    void setRouteList(List<Route> routeList);

    void refreshList();

    void showDetailFragment(Route item);

    Context getContext();

    void showErrorToast();
}
