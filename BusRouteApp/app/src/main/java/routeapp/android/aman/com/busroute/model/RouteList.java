package routeapp.android.aman.com.busroute.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by aman on 1/2/18.
 */

public class RouteList {

    @SerializedName("routes")
    @Expose
    private ArrayList<Route> routes = new ArrayList<>();

    /**
     * @return The Routes
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    /**
     * @param routes The Routes
     */
    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
}
