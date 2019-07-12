package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Addaddress {

    @SerializedName("response")
    @Expose
    private AddaddressResponse response;

    /**
     *
     * @return
     * The response
     */
    public AddaddressResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(AddaddressResponse response) {
        this.response = response;
    }

}