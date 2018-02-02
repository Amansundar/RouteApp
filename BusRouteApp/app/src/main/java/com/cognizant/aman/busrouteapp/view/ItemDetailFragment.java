package com.cognizant.aman.busrouteapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cognizant.aman.busrouteapp.R;
import com.cognizant.aman.busrouteapp.model.Route;
import com.cognizant.aman.busrouteapp.model.Stops;
import com.cognizant.aman.busrouteapp.presenter.DetailPresenter;
import com.cognizant.aman.busrouteapp.presenter.DetailPresenterImpl;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private StopAdapter mAdapter;
    private DetailPresenter mDetailPresenter;
    /**
     * The dummy content this fragment is presenting.
     */
    private Route mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = (Route) getArguments().getSerializable(ARG_ITEM_ID);
        }
        mDetailPresenter = new DetailPresenterImpl(getActivity());
        mDetailPresenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        ListView listView = rootView.findViewById(R.id.stop_listview);
        if (mItem != null) {
            mDetailPresenter.loadImage(mItem.getImage(), rootView.findViewById(R.id.id_image));
            ((TextView) rootView.findViewById(R.id.content)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.description)).setText(mItem.getDescription());
            (rootView.findViewById(R.id.id_accessable)).setVisibility(mItem.isAccessible()?View.VISIBLE:View.INVISIBLE);
        }
        mAdapter = new StopAdapter(getActivity(),mItem.getStops());
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDetailPresenter.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mDetailPresenter.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetailPresenter.onDestroy();

    }
    /**
     * The main adapter that backs the GridView. This is fairly standard except the number of
     * columns in the GridView is used to create a fake top row of empty views as we use a
     * transparent ActionBar and don't want the real top row of images to start off covered by it.
     */
    private class StopAdapter extends BaseAdapter {

        private final Context mContext;
        private ArrayList<Stops> mStopList = new ArrayList<>();

        public StopAdapter(Context context,ArrayList<Stops> stopList) {
            super();
            mContext = context;
            mStopList = stopList;
        }

        @Override
        public int getCount() {

            return (2*mStopList.size())-1;
        }

        @Override
        public Stops getItem(int position) {
            return (position%2==0) ? mStopList.get(position/2) : null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            // Two types of views, the normal ImageView and the top row of empty views
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return (position%2==0) ? 0 : 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            //Log.d("ListTestView","position = "+getItem(position).getStops());
            //BEGIN_INCLUDE(load_gridview_item)
            // First check if this is the top row
            if (getItemViewType(position)==0) {
                convertView= LayoutInflater.from(mContext)
                        .inflate(R.layout.stop_pt_layout, container, false);
                ((TextView)(convertView.findViewById(R.id.stop_txtview))).setText(getItem(position).getStops());
            }
            else{
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.ver_layout, container, false);
            }
            return convertView;
        }
    }
}
