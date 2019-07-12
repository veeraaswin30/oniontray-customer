
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverLocDetRes {

    @SerializedName("response")
    @Expose
    private DriverLocResp response;

    public DriverLocResp getResponse() {
        return response;
    }

    public void setResponse(DriverLocResp response) {
        this.response = response;
    }

}
