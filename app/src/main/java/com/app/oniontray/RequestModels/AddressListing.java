package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddressListing {

    @SerializedName("response")
    @Expose
    private AddressListingResponse response;

    /**
     *
     * @return
     * The response
     */
    public AddressListingResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(AddressListingResponse response) {
        this.response = response;
    }

}
