package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PromoCodeResponse {


//    @SerializedName("httpCode")
//    @Expose
//    private Integer httpCode;
//    @SerializedName("Message")
//    @Expose
//    private String message;
//    @SerializedName("coupon_details")
//    @Expose
//    private CouponDetails couponDetails;
//
//    /**
//     * @return The httpCode
//     */
//    public Integer getHttpCode() {
//        return httpCode;
//    }
//
//    /**
//     * @param httpCode The httpCode
//     */
//    public void setHttpCode(Integer httpCode) {
//        this.httpCode = httpCode;
//    }
//
//    /**
//     * @return The message
//     */
//    public String getMessage() {
//        return message;
//    }
//
//    /**
//     * @param message The Message
//     */
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    /**
//     * @return The couponDetails
//     */
//    public CouponDetails getCouponDetails() {
//        return couponDetails;
//    }
//
//    /**
//     * @param couponDetails The coupon_details
//     */
//    public void setCouponDetails(CouponDetails couponDetails) {
//        this.couponDetails = couponDetails;
//    }


    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("coupon_details")
    @Expose
    private CouponDetails couponDetails;
    @SerializedName("coupon_user_limit_details")
    @Expose
    private CouponUserLimitDetails couponUserLimitDetails;


    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CouponDetails getCouponDetails() {
        return couponDetails;
    }

    public void setCouponDetails(CouponDetails couponDetails) {
        this.couponDetails = couponDetails;
    }

    public CouponUserLimitDetails getCouponUserLimitDetails() {
        return couponUserLimitDetails;
    }

    public void setCouponUserLimitDetails(CouponUserLimitDetails couponUserLimitDetails) {
        this.couponUserLimitDetails = couponUserLimitDetails;
    }


}


