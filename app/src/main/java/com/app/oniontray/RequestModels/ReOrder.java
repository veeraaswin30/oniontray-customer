package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReOrder {

    @SerializedName("response")
    @Expose
    private ReOrderResponse response;

    /**
     *
     * @return
     * The response
     */
    public ReOrderResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(ReOrderResponse response) {
        this.response = response;
    }

}