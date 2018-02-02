package routeapp.android.aman.com.busroute.presenter;

import android.content.Context;

import routeapp.android.aman.com.busroute.model.Route;

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

    boolean isRouteEmpty();
}
