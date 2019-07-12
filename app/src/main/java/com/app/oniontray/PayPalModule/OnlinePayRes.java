
package com.app.oniontray.PayPalModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnlinePayRes {

    @SerializedName("response")
    @Expose
    private OnllineResponse response;

    public OnllineResponse getResponse() {
        return response;
    }

    public void setResponse(OnllineResponse response) {
        this.response = response;
    }

}
