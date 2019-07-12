package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProcToCheck {

    @SerializedName("response")
    @Expose
    private ProcToCheckResp response;

    /**
     *
     * @return
     *     The response
     */
    public ProcToCheckResp getResponse() {
        return response;
    }

    /**
     *
     * @param response
     *     The response
     */
    public void setResponse(ProcToCheckResp response) {
        this.response = response;
    }

}
