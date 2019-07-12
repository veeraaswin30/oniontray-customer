package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AddressType implements Serializable{

    @SerializedName("response")
    @Expose
    private AddressTypeResponse response;

    /**
     *
     * @return
     * The response
     */
    public AddressTypeResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(AddressTypeResponse response) {
        this.response = response;
    }

}