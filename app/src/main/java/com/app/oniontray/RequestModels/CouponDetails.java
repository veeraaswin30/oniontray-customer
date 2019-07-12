package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponDetails {

//    @SerializedName("coupon_id")
//    @Expose
//    private String couponId;
//    @SerializedName("coupon_type")
//    @Expose
//    private String couponType;
//    @SerializedName("offer_amount")
//    @Expose
//    private String offerAmount;
//    @SerializedName("coupon_code")
//    @Expose
//    private String couponCode;
//
//    /**
//     *
//     * @return
//     * The couponCode
//     */
//    public String getCouponCode() {
//        return couponCode;
//    }
//
//    /**
//     *
//     * @param couponCode
//     * The coupon_code
//     */
//    public void setCouponCode(String couponCode) {
//        this.couponCode = couponCode;
//    }
//
//    /**
//     *
//     * @return
//     * The couponType
//     */
//    public String getCouponType() {
//        return couponType;
//    }
//
//    /**
//     *
//     * @param couponType
//     * The coupon_type
//     */
//    public void setCouponType(String couponType) {
//        this.couponType = couponType;
//    }
//
//    /**
//     *
//     * @return
//     * The offerAmount
//     */
//    public String getOfferAmount() {
//        return offerAmount;
//    }
//
//    /**
//     *
//     * @param offerAmount
//     * The offer_amount
//     */
//    public void setOfferAmount(String offerAmount) {
//        this.offerAmount = offerAmount;
//    }
//
//    /**
//     *
//     * @return
//     * The couponId
//     */
//    public String getCouponId() {
//        return couponId;
//    }
//
//    /**
//     *
//     * @param couponId
//     * The coupon_id
//     */
//    public void setCouponId(String couponId) {
//        this.couponId = couponId;
//    }



    @SerializedName("coupon_id")
    @Expose
    private Integer couponId;
    @SerializedName("coupon_type")
    @Expose
    private Integer couponType;
    @SerializedName("offer_amount")
    @Expose
    private String offerAmount;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("offer_type")
    @Expose
    private Integer offerType;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("offer_percentage")
    @Expose
    private String offerPercentage;

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getOfferType() {
        return offerType;
    }

    public void setOfferType(Integer offerType) {
        this.offerType = offerType;
    }

    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }


}