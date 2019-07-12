package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class MyOrdersResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("order_list")
    @Expose
    private ArrayList<OrderList> orderList = new ArrayList<>();
    @SerializedName("store_logo_url")
    @Expose
    private String storeLogoUrl;

    /**
     *
     * @return
     * The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The orderList
     */
    public ArrayList<OrderList> getOrderList() {
        return orderList;
    }

    /**
     *
     * @param orderList
     * The order_list
     */
    public void setOrderList(ArrayList<OrderList> orderList) {
        this.orderList = orderList;
    }

    /**
     *
     * @return
     * The storeLogoUrl
     */
    public String getStoreLogoUrl() {
        return storeLogoUrl;
    }

    /**
     *
     * @param storeLogoUrl
     * The store_logo_url
     */
    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }

}