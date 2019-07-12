package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VendorDetailForMyCart implements Serializable {

    @SerializedName("response")
    @Expose
    private VendorDetailForMyCartResponse response;

    public VendorDetailForMyCartResponse getResponse() {
        return response;
    }

    public void setResponse(VendorDetailForMyCartResponse response) {
        this.response = response;
    }

}
