
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReOrderReq {

    @SerializedName("response")
    @Expose
    private ReOrdResp response;

    public ReOrdResp getResponse() {
        return response;
    }

    public void setResponse(ReOrdResp response) {
        this.response = response;
    }

}
