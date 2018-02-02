package routeapp.android.aman.com.busroute.presenter;

import android.view.View;

import routeapp.android.aman.com.busroute.R;
import routeapp.android.aman.com.busroute.model.Route;
import routeapp.android.aman.com.busroute.util.ImageCache;
import routeapp.android.aman.com.busroute.util.ImageFetcher;
import routeapp.android.aman.com.busroute.view.ItemListActivity;
import routeapp.android.aman.com.busroute.view.RecyclingImageView;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by aman on 1/2/18.
 */

public  class ListPresenterImpl implements ListPresenter,ListInteractor.ApiCallListener {


    private ItemListView mItemListView;
    private View previousSelView;
    private ListInteractor mListInteractor;
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private int mImageThumbSize;
    private ImageFetcher mImageFetcher;

    public ListPresenterImpl(ItemListView itemListView,ListInteractor listInteractor){
        mItemListView = itemListView;
        mListInteractor = listInteractor;
        mImageThumbSize = mItemListView.getContext().getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(mItemListView.getContext(), IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(mItemListView.getContext(), mImageThumbSize);
        mImageFetcher.addImageCache(((ItemListActivity)mItemListView.getContext()).getSupportFragmentManager(), cacheParams);
        mImageFetcher.clearCache();
    }

    @Override
    public void onItemClicked(View view) {
        if(previousSelView!=null)
            previousSelView.setSelected(false);
        previousSelView = view;
        view.setSelected(true);
        if(mItemListView!=null)
          mItemListView.showDetailFragment((Route) view.getTag());
    }

    @Override
    public void loadImage(String url, RecyclingImageView view) {
        mImageFetcher.loadImage(url,view);
    }

    @Override
    public void onButtonClicked() {
        mListInteractor.callApiService(this);
    }

    @Override
    public void onLoadSuccess(List<Route> routeList) {
        if(mItemListView!=null) {
            mItemListView.setRouteList(routeList);
            mItemListView.hideProgress();
        }
    }

    @Override
    public void onLoadFailed() {
        if(mItemListView!=null) {
            mItemListView.showErrorToast();
            mItemListView.hideProgress();
        }
    }

    @Override
    public void onCreate() {
        mListInteractor.callApiService(this);
    }

    @Override
    public void onDestroy() {
        mImageFetcher.closeCache();
        mItemListView=null;
    }

    @Override
    public void onResume() {
        mImageFetcher.setExitTasksEarly(false);
        if(mItemListView!=null){
            if(mItemListView.isRouteEmpty()) {
                mImageFetcher.clearCache();
                mListInteractor.callApiService(this);
            }else
            mItemListView.refreshList();
        }
    }

    @Override
    public void onPause() {
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    public void onResult(Disposable routeSubscription,List<Route> routeList) {
        if(!routeList.isEmpty()){
            onLoadSuccess(routeList);
        }
        else{
            onLoadFailed();
        }
        if (routeSubscription != null && !routeSubscription.isDisposed()) {
            routeSubscription.dispose();
        }
    }
}
