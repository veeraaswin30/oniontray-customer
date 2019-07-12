package com.app.oniontray.RequestModels.VisitorsVerifyOtp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpReq {
    @SerializedName("response")
    @Expose
    private VerifyOtpRes response;

    public VerifyOtpRes getResponse() {
        return response;
    }

    public void setResponse(VerifyOtpRes response) {
        this.response = response;
    }

}
