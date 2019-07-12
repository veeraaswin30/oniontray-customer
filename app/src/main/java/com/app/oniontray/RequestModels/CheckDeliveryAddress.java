package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDeliveryAddress {

    @SerializedName("response")
    @Expose
    private CheckDeliveryAddressResponse response;

    public CheckDeliveryAddressResponse getResponse() {
        return response;
    }

    public void setResponse(CheckDeliveryAddressResponse response) {
        this.response = response;
    }

}