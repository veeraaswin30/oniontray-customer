package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderDetail {

    @SerializedName("response")
    @Expose
    private OrderResponse response;

    /**
     *
     * @return
     * The response
     */
    public OrderResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(OrderResponse response) {
        this.response = response;
    }

}