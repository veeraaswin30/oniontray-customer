
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoProdBanner {

    @SerializedName("response")
    @Expose
    private StoProdResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public StoProdResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(StoProdResponse response) {
        this.response = response;
    }

}
