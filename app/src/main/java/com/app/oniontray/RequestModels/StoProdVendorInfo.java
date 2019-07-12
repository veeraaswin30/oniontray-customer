
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoProdVendorInfo implements Serializable {

    @SerializedName("response")
    @Expose
    private StoProdVenderResponse response;

    /**
     *
     * @return
     *     The response
     */
    public StoProdVenderResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     *     The response
     */
    public void setResponse(StoProdVenderResponse response) {
        this.response = response;
    }

}
