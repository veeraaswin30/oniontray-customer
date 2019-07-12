
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoRettingRes {

    @SerializedName("response")
    @Expose
    private StoRattRespData response;

    public StoRattRespData getResponse() {
        return response;
    }

    public void setResponse(StoRattRespData response) {
        this.response = response;
    }


}
