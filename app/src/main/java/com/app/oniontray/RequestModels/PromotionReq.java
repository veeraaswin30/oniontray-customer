
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionReq {

    @SerializedName("response")
    @Expose
    private PromotionReqRes response;

    public PromotionReqRes getResponse() {
        return response;
    }

    public void setResponse(PromotionReqRes response) {
        this.response = response;
    }

}
