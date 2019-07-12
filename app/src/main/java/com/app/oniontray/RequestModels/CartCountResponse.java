package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CartCountResponse {


    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("cart_count")
    @Expose
    private String cartCount;

    /**
     * @return The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     * @param httpCode The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * @return The cartCount
     */
    public String getCartCount() {
        return cartCount;
    }

    /**
     * @param cartCount The cart_count
     */
    public void setCartCount(String cartCount) {
        this.cartCount = cartCount;
    }

}
