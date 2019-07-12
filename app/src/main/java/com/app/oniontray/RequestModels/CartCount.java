package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CartCount {

    @SerializedName("response")
    @Expose
    private CartCountResponse response;

    /**
     *
     * @return
     * The response
     */
    public CartCountResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(CartCountResponse response) {
        this.response = response;
    }

}