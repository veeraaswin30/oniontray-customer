package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AutoDetectLocation {

    @SerializedName("response")
    @Expose
    private AutoDetectLocationResponse response;

    public AutoDetectLocationResponse getResponse() {
        return response;
    }

    public void setResponse(AutoDetectLocationResponse response) {
        this.response = response;
    }

}