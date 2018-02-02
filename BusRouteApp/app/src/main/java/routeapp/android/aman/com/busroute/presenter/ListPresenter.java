package routeapp.android.aman.com.busroute.presenter;

import android.view.View;

import routeapp.android.aman.com.busroute.model.Route;
import routeapp.android.aman.com.busroute.view.RecyclingImageView;

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
