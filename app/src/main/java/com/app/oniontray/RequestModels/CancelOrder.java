package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelOrder {

    @SerializedName("response")
    @Expose
    private CancelOrderResponse response;

    /**
     *
     * @return
     * The response
     */
    public CancelOrderResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(CancelOrderResponse response) {
        this.response = response;
    }

}
