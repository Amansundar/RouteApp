package com.cognizant.aman.busrouteapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cognizant.aman.busrouteapp.R;
import com.cognizant.aman.busrouteapp.model.Route;
import com.cognizant.aman.busrouteapp.presenter.ItemListView;
import com.cognizant.aman.busrouteapp.presenter.ListInteractorImpl;
import com.cognizant.aman.busrouteapp.presenter.ListPresenter;
import com.cognizant.aman.busrouteapp.presenter.ListPresenterImpl;
import java.util.ArrayList;
import java.util.List;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements ItemListView,View.OnClickListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private ProgressBar progressBar;
    private FloatingActionButton floatingActionButton;
    private SimpleItemRecyclerViewAdapter mSimpleItemRecyclerViewAdapter;
    private ListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item_list);
        progressBar = findViewById(R.id.loader);
        floatingActionButton =  findViewById(R.id.float_button);
        floatingActionButton.setOnClickListener(this);
        presenter = new ListPresenterImpl(this,new ListInteractorImpl(this));
        mSimpleItemRecyclerViewAdapter =new SimpleItemRecyclerViewAdapter(presenter);
        RecyclerView recyclerView = findViewById(R.id.item_list);
        recyclerView.setAdapter(mSimpleItemRecyclerViewAdapter);
        recyclerView.addOnScrollListener(new ScrollListener());
        presenter.onCreate();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRouteList(List<Route> routeList) {
        mSimpleItemRecyclerViewAdapter.setRouteList(routeList);
        refreshList();
    }

    @Override
    public void refreshList() {
        mSimpleItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showDetailFragment(Route item) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(this,getResources().getString(R.string.network_failed),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onClick(View view) {
        presenter.onButtonClicked();
    }

    class ScrollListener  extends RecyclerView.OnScrollListener{
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            refreshList();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    public static class SimpleItemRecyclerViewAdapter
        extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> implements View.OnClickListener{

    private final ListPresenter mPresenter;
    private List<Route> mRouteList = new ArrayList<>();

    SimpleItemRecyclerViewAdapter(ListPresenter presenter) {
        mPresenter = presenter;
    }

    public void setRouteList(List<Route> mRouteList) {
        this.mRouteList = mRouteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Finally load the image asynchronously into the ImageView, this also takes care of
        // setting a placeholder image while the background thread runs
        final Route mRoute = mRouteList.get(position);
        mPresenter.loadImage(mRouteList.get(position).getImage(), holder.mImageView);
        final Bitmap bitmap = ((BitmapDrawable)holder.mImageView.getDrawable()).getBitmap();
        if(bitmap!=null)
            holder.mImageView.setBackground(null);
        holder.mContentView.setText(mRoute.getName());
        holder.itemView.setTag(mRoute);
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }

        @Override
        public void onClick(View view) {
            mPresenter.onItemClicked(view);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
        final RecyclingImageView mImageView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mImageView =   view.findViewById(R.id.id_image);
            mContentView = view.findViewById(R.id.content);
        }
    }
}
}
