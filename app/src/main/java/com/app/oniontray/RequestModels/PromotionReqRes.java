package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nextbrain on 10/4/17.
 */

public class PromotionReqRes {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("promotion_list")
    @Expose
    private List<PromotionList> promotionList = null;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<PromotionList> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<PromotionList> promotionList) {
        this.promotionList = promotionList;
    }

}
