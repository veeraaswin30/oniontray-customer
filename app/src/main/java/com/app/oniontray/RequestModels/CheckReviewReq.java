package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nextbrain on 22/5/17.
 */

public class CheckReviewReq  {

    @SerializedName("response")
    @Expose
    private CheckReviewResp response;

    public CheckReviewResp getResponse() {
        return response;
    }

    public void setResponse(CheckReviewResp response) {
        this.response = response;
    }

}
