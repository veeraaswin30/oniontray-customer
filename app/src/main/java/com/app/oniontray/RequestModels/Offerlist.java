package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Offerlist {

    @SerializedName("response")
    @Expose
    private OfferResponse response;

    /**
     *
     * @return
     * The response
     */
    public OfferResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(OfferResponse response) {
        this.response = response;
    }

}