
package com.app.oniontray.RequestModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturedStoresListResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("store_list")
    @Expose
    private List<FeaStoreList_Data> storeList = new ArrayList<>();

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
    public List<FeaStoreList_Data> getStoreList() {
        return storeList;
    }

    /**
     * 
     * @param storeList
     *     The store_list
     */
    public void setStoreList(List<FeaStoreList_Data> storeList) {
        this.storeList = storeList;
    }

}
