
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoProdVenderResponse implements Serializable {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String Message;
    @SerializedName("outlet_detail")
    @Expose
    private StoInfoOutletDetails vendorDetail;

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
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return Message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.Message = message;
    }

    /**
     *
     * @return
     *     The vendorDetail
     */
    public StoInfoOutletDetails getStoInfoOutletDetails() {
        return vendorDetail;
    }

    /**
     *
     * @param vendorDetail
     *     The vendor_detail
     */
    public void setStoInfoOutletDetails(StoInfoOutletDetails vendorDetail) {
        this.vendorDetail = vendorDetail;
    }

}
