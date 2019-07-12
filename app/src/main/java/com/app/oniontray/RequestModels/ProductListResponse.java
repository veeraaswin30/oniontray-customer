
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductListResponse implements Serializable{

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("product_list")
    @Expose
    private List<ProductList_Data> productList_data = new ArrayList<>();
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("currency_side")
    @Expose
    private int currencySide;

    /**
     * 
     * @return
     *     The httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * 
     * @param httpCode
     *     The httpCode
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The productList
     */
    public List<ProductList_Data> getProductList_Data() {
        return productList_data;
    }

    /**
     * 
     * @param productList
     *     The product_list
     */
    public void setProductList_Data(List<ProductList_Data> productList) {
        this.productList_data = productList;
    }

    /**
     * 
     * @return
     *     The currencySymbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * 
     * @param currencySymbol
     *     The currency_symbol
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    /**
     *
     * @return
     *     The currencySide
     */
    public int getcurrencySide() {
        return currencySide;
    }

    /**
     *
     * @param currencySide
     *     The currencySide
     */
    public void setcurrencySide(int currencySide) {
        this.currencySide = currencySide;
    }

}
