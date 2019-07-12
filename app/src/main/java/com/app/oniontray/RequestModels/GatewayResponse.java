package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GatewayResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("payment_detail")
    @Expose
    private List<GateWayAddressDetail> addressDetail = null;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GateWayAddressDetail> getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(List<GateWayAddressDetail> addressDetail) {
        this.addressDetail = addressDetail;
    }


}
