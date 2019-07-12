
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("response")
    @Expose
    private LocationResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public LocationResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(LocationResponse response) {
        this.response = response;
    }

}
