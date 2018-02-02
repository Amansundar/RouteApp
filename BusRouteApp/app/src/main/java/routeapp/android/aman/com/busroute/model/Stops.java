package routeapp.android.aman.com.busroute.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by aman on 1/2/18.
 */

public class Stops  implements Serializable {

    private static final long serialVersionUID = 100L;

    @SerializedName("name")
    @Expose
    private String stops;


    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

}
