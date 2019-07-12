
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OffersRespReq implements Serializable {

    @SerializedName("response")
    @Expose
    private OffersReqResp response;

    public OffersReqResp getResponse() {
        return response;
    }

    public void setResponse(OffersReqResp response) {
        this.response = response;
    }


}
