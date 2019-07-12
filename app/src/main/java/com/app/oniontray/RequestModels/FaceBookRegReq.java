
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceBookRegReq {

    @SerializedName("response")
    @Expose
    private FaceBookRegRespData response;

    public FaceBookRegRespData getResponse() {
        return response;
    }

    public void setResponse(FaceBookRegRespData response) {
        this.response = response;
    }

}
