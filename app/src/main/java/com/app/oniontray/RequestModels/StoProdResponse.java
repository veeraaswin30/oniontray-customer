
package com.app.oniontray.RequestModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoProdResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("banner_list")
    @Expose
    private List<StoProdBannerList_Data> bannerList = new ArrayList<>();

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
     *     The bannerList
     */
    public List<StoProdBannerList_Data> getBannerList() {
        return bannerList;
    }

    /**
     * 
     * @param bannerList
     *     The banner_list
     */
    public void setBannerList(List<StoProdBannerList_Data> bannerList) {
        this.bannerList = bannerList;
    }

}
