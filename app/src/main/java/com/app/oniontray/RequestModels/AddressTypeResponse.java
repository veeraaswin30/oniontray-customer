package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddressTypeResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("address_type")
    @Expose
    private List<AddressTypeList> addressType = new ArrayList<>();

    /**
     *
     * @return
     * The httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     * The status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The addressType
     */
    public List<AddressTypeList> getAddressType() {
        return addressType;
    }

    /**
     *
     * @param addressType
     * The address_type
     */
    public void setAddressType(List<AddressTypeList> addressType) {
        this.addressType = addressType;
    }

}