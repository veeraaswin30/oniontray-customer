
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreList {

    @SerializedName("response")
    @Expose
    private StoreListResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public StoreListResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(StoreListResponse response) {
        this.response = response;
    }

}
