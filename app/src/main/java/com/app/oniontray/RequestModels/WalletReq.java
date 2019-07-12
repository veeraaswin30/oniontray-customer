
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletReq {

    @SerializedName("response")
    @Expose
    private WalletResp response;

    public WalletResp getResponse() {
        return response;
    }

    public void setResponse(WalletResp response) {
        this.response = response;
    }

}
