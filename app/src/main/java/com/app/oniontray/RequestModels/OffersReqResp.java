
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OffersReqResp implements Serializable {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("coupons_list")
    @Expose
    private List<CouponsList> couponsList = null;
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

    public List<CouponsList> getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(List<CouponsList> couponsList) {
        this.couponsList = couponsList;
    }

    public List<PromotionList> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<PromotionList> promotionList) {
        this.promotionList = promotionList;
    }

}
