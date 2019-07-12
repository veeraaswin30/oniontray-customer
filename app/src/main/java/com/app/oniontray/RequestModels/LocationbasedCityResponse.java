
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class LocationbasedCityResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<LocationbasedCityDatum> data = new ArrayList<>();

    /**
     * 
     * @return
     *     The httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * 
     * @param httpCode
     *     The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * 
     * @return
     *     The status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     *     The Message
     */
    public String getMessage() {
        return Message;
    }

    /**
     *
     * @param Message
     *     The Message
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }

    /**
     * 
     * @return
     *     The data
     */
    public List<LocationbasedCityDatum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<LocationbasedCityDatum> data) {
        this.data = data;
    }


}
