package routeapp.android.aman.com.busroute.presenter;


import routeapp.android.aman.com.busroute.view.RecyclingImageView;

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
