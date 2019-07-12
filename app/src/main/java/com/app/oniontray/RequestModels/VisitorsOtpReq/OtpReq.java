package com.app.oniontray.RequestModels.VisitorsOtpReq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpReq {

    @SerializedName("response")
    @Expose
    private OtpResp response;

    public OtpResp getResponse() {
        return response;
    }

    public void setResponse(OtpResp response) {
        this.response = response;
    }

}
