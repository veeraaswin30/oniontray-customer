package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddToCart {

    @SerializedName("response")
    @Expose
    private AddToCartResponse response;

    /**
     *
     * @return
     * The response
     */
    public AddToCartResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(AddToCartResponse response) {
        this.response = response;
    }

}