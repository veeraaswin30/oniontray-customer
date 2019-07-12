
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationOutlet {

    @SerializedName("response")
    @Expose
    private LocOutLetResponse response;

    public LocOutLetResponse getResponse() {
        return response;
    }

    public void setResponse(LocOutLetResponse response) {
        this.response = response;
    }

}
