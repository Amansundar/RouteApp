package com.cognizant.aman.busrouteapp.presenter;

import android.content.Context;

import com.cognizant.aman.busrouteapp.R;
import com.cognizant.aman.busrouteapp.util.ImageCache;
import com.cognizant.aman.busrouteapp.util.ImageFetcher;
import com.cognizant.aman.busrouteapp.view.ItemDetailActivity;
import com.cognizant.aman.busrouteapp.view.ItemListActivity;
import com.cognizant.aman.busrouteapp.view.RecyclingImageView;


/**
 * Created by aman on 1/2/18.
 */

public class DetailPresenterImpl implements DetailPresenter {

    private static final String IMAGE_CACHE_DIR = "details";
    private int mImageThumbSize;
    private ImageFetcher mImageFetcher;
    private Context mContext;

    public DetailPresenterImpl(Context context){
        mContext = context;
    }

    @Override
    public void loadImage(String url, RecyclingImageView view) {
        mImageFetcher.clearCache();
        mImageFetcher.loadImage(url,view);
    }

    @Override
    public void onCreate() {
        mImageThumbSize = mContext.getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        ImageCache.ImageCacheParams cacheParams =
                new ImageCache.ImageCacheParams(mContext, IMAGE_CACHE_DIR);
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory
        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(mContext, mImageThumbSize);
        mImageFetcher.addImageCache(((ItemDetailActivity)mContext).getSupportFragmentManager(), cacheParams);
    }

    @Override
    public void onDestroy() {
        mImageFetcher.closeCache();
    }

    @Override
    public void onResume() {
        mImageFetcher.setExitTasksEarly(false);
    }

    @Override
    public void onPause() {
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }
}
