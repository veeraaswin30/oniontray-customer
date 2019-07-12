
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductList implements Serializable{

    @SerializedName("response")
    @Expose
    private ProductListResponse response;

    /**
     * 
     * @return
     *     The response
     */
    public ProductListResponse getResponse() {
        return response;
    }

    /**
     * 
     * @param response
     *     The response
     */
    public void setResponse(ProductListResponse response) {
        this.response = response;
    }

}
