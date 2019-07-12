
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesList {

    @SerializedName("response")
    @Expose
    private CategoriesResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public CategoriesResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(CategoriesResponse response) {
        this.response = response;
    }

}
