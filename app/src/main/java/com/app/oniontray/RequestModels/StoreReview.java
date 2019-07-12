package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreReview {

    @SerializedName("response")
    @Expose
    private StoreReviewResponse response;

    /**
     *
     * @return
     * The response
     */
    public StoreReviewResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(StoreReviewResponse response) {
        this.response = response;
    }

}