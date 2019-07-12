
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GustCheckOutReq {

    @SerializedName("response")
    @Expose
    private GustCheckResp response;

    public GustCheckResp getResponse() {
        return response;
    }

    public void setResponse(GustCheckResp response) {
        this.response = response;
    }

}
