
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassWordUpdResponse {

    @SerializedName("response")
    @Expose
    private PaWdUpdResData response;

    /**
     * 
     * @return
     *     The response
     */
    public PaWdUpdResData getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(PaWdUpdResData response) {
        this.response = response;
    }

}
