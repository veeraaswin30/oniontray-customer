package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VendorDetailForMyCartResponse implements Serializable {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("outlet_details")
    @Expose
    private OutletDetails outletDetails;
    @SerializedName("Message")
    @Expose
    private String message;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public OutletDetails getOutletDetails() {
        return outletDetails;
    }

    public void setOutletDetails(OutletDetails outletDetails) {
        this.outletDetails = outletDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
