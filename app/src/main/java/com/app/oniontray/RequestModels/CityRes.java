
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityRes {

    @SerializedName("response")
    @Expose
    private CityRespData response;

    public CityRespData getResponse() {
        return response;
    }

    public void setResponse(CityRespData response) {
        this.response = response;
    }

}
