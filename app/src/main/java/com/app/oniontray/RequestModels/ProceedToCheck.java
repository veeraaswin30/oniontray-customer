package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class ProceedToCheck implements Serializable {

    @SerializedName("response")
    @Expose
    private ProceedToCheckResponse response;

    /**
     *
     * @return
     * The response
     */
    public ProceedToCheckResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(ProceedToCheckResponse response) {
        this.response = response;
    }

}