
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfDet {

    @SerializedName("response")
    @Expose
    private UserProfResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public UserProfResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(UserProfResponse response) {
        this.response = response;
    }

}
