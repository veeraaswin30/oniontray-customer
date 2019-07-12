
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceBookReq {

    @SerializedName("response")
    @Expose
    private FaceBookReqData response;

    public FaceBookReqData getResponse() {
        return response;
    }

    public void setResponse(FaceBookReqData response) {
        this.response = response;
    }

}
