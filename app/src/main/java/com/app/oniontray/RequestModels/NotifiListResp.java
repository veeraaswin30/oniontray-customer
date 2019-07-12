package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class NotifiListResp {

    @SerializedName("response")
    @Expose
    private NotifiResponseData response;

    public NotifiResponseData getResponse() {
        return response;
    }

    public void setResponse(NotifiResponseData response) {
        this.response = response;
    }


}
