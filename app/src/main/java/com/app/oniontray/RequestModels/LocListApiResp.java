
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocListApiResp {

    @SerializedName("response")
    @Expose
    private LocationResp response;

    public LocationResp getResponse() {
        return response;
    }

    public void setResponse(LocationResp response) {
        this.response = response;
    }

}
