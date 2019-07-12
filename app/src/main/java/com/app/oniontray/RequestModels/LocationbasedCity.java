
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LocationbasedCity {

    @SerializedName("response")
    @Expose
    private LocationbasedCityResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public LocationbasedCityResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(LocationbasedCityResponse response) {
        this.response = response;
    }

}
