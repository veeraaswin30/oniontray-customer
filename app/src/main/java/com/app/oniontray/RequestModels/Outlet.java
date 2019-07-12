package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Outlet {

    @SerializedName("response")
    @Expose
    private OutletResponse response;

    /**
     *
     * @return
     * The response
     */
    public OutletResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(OutletResponse response) {
        this.response = response;
    }

}
