
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SendOTP {

    @SerializedName("response")
    @Expose
    private SendOTPResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public SendOTPResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(SendOTPResponse response) {
        this.response = response;
    }

}
