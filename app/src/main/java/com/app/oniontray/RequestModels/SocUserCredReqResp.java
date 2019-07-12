
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SocUserCredReqResp {

    @SerializedName("response")
    @Expose
    private SocUserCredCheRes response;

    public SocUserCredCheRes getResponse() {
        return response;
    }

    public void setResponse(SocUserCredCheRes response) {
        this.response = response;
    }

}
