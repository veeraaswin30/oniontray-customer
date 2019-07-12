package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class NotifiDeleteResp {

    @SerializedName("response")
    @Expose
    private NotifiRespData response;

    public NotifiRespData getResponse() {
        return response;
    }

    public void setResponse(NotifiRespData response) {
        this.response = response;
    }


}
