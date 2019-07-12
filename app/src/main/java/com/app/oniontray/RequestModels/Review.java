package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("response")
    @Expose
    private ReviewResponse response;

    public ReviewResponse getResponse() {
        return response;
    }

    public void setResponse(ReviewResponse response) {
        this.response = response;
    }

}
