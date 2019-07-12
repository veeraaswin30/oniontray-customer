package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyCart {

    @SerializedName("response")
    @Expose
    private MyCartResponse response;

    /**
     *
     * @return
     * The response
     */
    public MyCartResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(MyCartResponse response) {
        this.response = response;
    }

}