package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nextbrain on 2/5/17.
 */

public class RegNewOTPReq {

    @SerializedName("response")
    @Expose
    private RegNewOTPRes response;

    public RegNewOTPRes getResponse() {
        return response;
    }

    public void setResponse(RegNewOTPRes response) {
        this.response = response;
    }

}
