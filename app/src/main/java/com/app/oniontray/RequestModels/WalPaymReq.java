package com.app.oniontray.RequestModels;

/**
 * Created by nextbrain on 17/5/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalPaymReq {

    @SerializedName("response")
    @Expose
    private WalletResponse response;

    public WalletResponse getResponse() {
        return response;
    }

    public void setResponse(WalletResponse response) {
        this.response = response;
    }

}
