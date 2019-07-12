
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OfflinePayment {

    @SerializedName("response")
    @Expose
    private OfflinePaymentResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public OfflinePaymentResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(OfflinePaymentResponse response) {
        this.response = response;
    }

}
