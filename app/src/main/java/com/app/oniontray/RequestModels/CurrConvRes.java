
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrConvRes {

    @SerializedName("response")
    @Expose
    private CurrConvDataResp response;

    public CurrConvDataResp getResponse() {
        return response;
    }

    public void setResponse(CurrConvDataResp response) {
        this.response = response;
    }


}
