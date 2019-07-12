package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddaddressResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("Message")
    @Expose
    private String message;

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

}