package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nextbrain on 2/8/2017.
 */

public class LocationData {
    @SerializedName("response")
    @Expose
    private LocationDataResponse response;

    public LocationDataResponse getResponse() {
        return response;
    }

    public void setResponse(LocationDataResponse response) {
        this.response = response;
    }
}
