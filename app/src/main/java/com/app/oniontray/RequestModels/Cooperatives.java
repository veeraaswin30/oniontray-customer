package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Cooperatives {

    @SerializedName("response")
    @Expose
    private CooperativesResponse response;

    /**
     *
     * @return
     * The response
     */
    public CooperativesResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(CooperativesResponse response) {
        this.response = response;
    }

}
