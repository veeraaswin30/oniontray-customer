package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
//    @SerializedName("status")
//    @Expose
//    private String status;
    @SerializedName("data")
    @Expose
    private ArrayList<Offer> data = new ArrayList<>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

//    /**
//     *
//     * @return
//     * The status
//     */
//    public String getStatus() {
//        return status;
//    }
//
//    /**
//     *
//     * @param status
//     * The status
//     */
//    public void setStatus(String status) {
//        this.status = status;
//    }

    /**
     *
     * @return
     * The data
     */
    public ArrayList<Offer> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(ArrayList<Offer> data) {
        this.data = data;
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
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }


}