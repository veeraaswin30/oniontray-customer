
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedStoresList {

    @SerializedName("response")
    @Expose
    private FeaturedStoresListResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public FeaturedStoresListResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(FeaturedStoresListResponse response) {
        this.response = response;
    }

}
