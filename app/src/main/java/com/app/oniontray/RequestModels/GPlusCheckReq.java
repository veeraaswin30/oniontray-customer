
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GPlusCheckReq {

    @SerializedName("response")
    @Expose
    private GPlusCheckResp response;

    public GPlusCheckResp getResponse() {
        return response;
    }

    public void setResponse(GPlusCheckResp response) {
        this.response = response;
    }

}
