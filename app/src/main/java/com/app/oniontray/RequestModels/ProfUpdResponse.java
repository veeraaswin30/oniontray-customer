
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfUpdResponse {

    @SerializedName("response")
    @Expose
    private ProfUpdData response;

    /**
     * 
     * @return
     *     The response
     */
    public ProfUpdData getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(ProfUpdData response) {
        this.response = response;
    }

}
