
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class cityList {

    @SerializedName("response")
    @Expose
    private cityResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public cityResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(cityResponse response) {
        this.response = response;
    }

}
