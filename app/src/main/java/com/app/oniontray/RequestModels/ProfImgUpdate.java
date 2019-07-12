package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProfImgUpdate {

    @SerializedName("response")
    @Expose
    private ProfImgUpdResponse response;

    /**
     *
     * @return
     *     The response
     */
    public ProfImgUpdResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     *     The response
     */
    public void setResponse(ProfImgUpdResponse response) {
        this.response = response;
    }


}
