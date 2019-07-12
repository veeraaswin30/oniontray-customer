package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nextbrain on 28/3/17.
 */

public class CouponUserLimitDetails {

    @SerializedName("cus_order_count")
    @Expose
    private String cusOrderCount;
    @SerializedName("user_limit")
    @Expose
    private Integer userLimit;
    @SerializedName("total_order_count")
    @Expose
    private String totalOrderCount;
    @SerializedName("coupon_limit")
    @Expose
    private Integer couponLimit;

    public String getCusOrderCount() {
        return cusOrderCount;
    }

    public void setCusOrderCount(String cusOrderCount) {
        this.cusOrderCount = cusOrderCount;
    }

    public Integer getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(Integer userLimit) {
        this.userLimit = userLimit;
    }

    public String getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(String totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public Integer getCouponLimit() {
        return couponLimit;
    }

    public void setCouponLimit(Integer couponLimit) {
        this.couponLimit = couponLimit;
    }

}
