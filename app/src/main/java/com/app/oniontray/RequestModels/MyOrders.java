package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyOrders {

    @SerializedName("response")
    @Expose
    private MyOrdersResponse response;

    /**
     *
     * @return
     * The response
     */
    public MyOrdersResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(MyOrdersResponse response) {
        this.response = response;
    }

}