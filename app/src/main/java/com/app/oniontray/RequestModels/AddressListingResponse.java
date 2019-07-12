package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class AddressListingResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("address_list")
    @Expose
    private ArrayList<AddressList> addressList = new ArrayList<AddressList>();

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
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The addressList
     */
    public ArrayList<AddressList> getAddressList() {
        return addressList;
    }

    /**
     *
     * @param addressList
     * The address_list
     */
    public void setAddressList(ArrayList<AddressList> addressList) {
        this.addressList = addressList;
    }

}