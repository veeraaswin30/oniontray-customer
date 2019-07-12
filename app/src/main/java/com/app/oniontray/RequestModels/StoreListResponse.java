
package com.app.oniontray.RequestModels;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreListResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("closed_store_list")
    @Expose
    private ArrayList<StoreList_Data> storeList = new ArrayList<>();


    @SerializedName("open_store_list")
    @Expose
    private ArrayList<StoreList_Data> openStoreList = null;

    /**
     * 
     * @return
     *     The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     * 
     * @param httpCode
     *     The httpCode
     */
    public void setHttpCode(Integer httpCode) {
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
     *     The storeList
     */
    public ArrayList<StoreList_Data> getStoreList() {
        return storeList;
    }

    /**
     * 
     * @param storeList
     *     The store_list
     */
    public void setStoreList(ArrayList<StoreList_Data> storeList) {
        this.storeList = storeList;
    }


    public ArrayList<StoreList_Data> getOpenStoreList() {
        return openStoreList;
    }

    public void setOpenStoreList(ArrayList<StoreList_Data> openStoreList) {
        this.openStoreList = openStoreList;
    }


}
