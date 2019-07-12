package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyOrdRetOrdRes {


    @SerializedName("response")
    @Expose
    private RetOrdRespData response;

    /**
     *
     * @return
     *     The response
     */
    public RetOrdRespData getResponse() {
        return response;
    }

    /**
     *
     * @param response
     *     The response
     */
    public void setResponse(RetOrdRespData response) {
        this.response = response;
    }


}
