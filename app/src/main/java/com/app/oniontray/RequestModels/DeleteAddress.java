package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeleteAddress {

    @SerializedName("response")
    @Expose
    private DeleteAddressResponse response;

    /**
     *
     * @return
     * The response
     */
    public DeleteAddressResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(DeleteAddressResponse response) {
        this.response = response;
    }

}