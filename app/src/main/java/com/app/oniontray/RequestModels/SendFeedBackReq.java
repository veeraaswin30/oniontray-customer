
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendFeedBackReq {

    @SerializedName("response")
    @Expose
    private SendFeedBackResponse response;

    public SendFeedBackResponse getResponse() {
        return response;
    }

    public void setResponse(SendFeedBackResponse response) {
        this.response = response;
    }

}
