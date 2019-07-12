package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Offer {

//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("coupon_title")
//    @Expose
//    private String couponTitle;
//    @SerializedName("coupon_code")
//    @Expose
//    private String couponCode;
//    @SerializedName("start_date")
//    @Expose
//    private String startDate;
//    @SerializedName("end_date")
//    @Expose
//    private String endDate;
//    @SerializedName("created_date")
//    @Expose
//    private String createdDate;
//    @SerializedName("active_status")
//    @Expose
//    private Integer activeStatus;
//    @SerializedName("coupon_status")
//    @Expose
//    private Object couponStatus;
//    @SerializedName("coupon_image")
//    @Expose
//    private String couponImage;
//
//    /**
//     *
//     * @return
//     * The id
//     */
//    public Integer getId() {
//        return id;
//    }
//
//    /**
//     *
//     * @param id
//     * The id
//     */
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    /**
//     *
//     * @return
//     * The couponTitle
//     */
//    public String getCouponTitle() {
//        return couponTitle;
//    }
//
//    /**
//     *
//     * @param couponTitle
//     * The coupon_title
//     */
//    public void setCouponTitle(String couponTitle) {
//        this.couponTitle = couponTitle;
//    }
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
//     * The startDate
//     */
//    public String getStartDate() {
//        return startDate;
//    }
//
//    /**
//     *
//     * @param startDate
//     * The start_date
//     */
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }
//
//    /**
//     *
//     * @return
//     * The endDate
//     */
//    public String getEndDate() {
//        return endDate;
//    }
//
//    /**
//     *
//     * @param endDate
//     * The end_date
//     */
//    public void setEndDate(String endDate) {
//        this.endDate = endDate;
//    }
//
//    /**
//     *
//     * @return
//     * The createdDate
//     */
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    /**
//     *
//     * @param createdDate
//     * The created_date
//     */
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    /**
//     *
//     * @return
//     * The activeStatus
//     */
//    public Integer getActiveStatus() {
//        return activeStatus;
//    }
//
//    /**
//     *
//     * @param activeStatus
//     * The active_status
//     */
//    public void setActiveStatus(Integer activeStatus) {
//        this.activeStatus = activeStatus;
//    }
//
//    /**
//     *
//     * @return
//     * The couponStatus
//     */
//    public Object getCouponStatus() {
//        return couponStatus;
//    }
//
//    /**
//     *
//     * @param couponStatus
//     * The coupon_status
//     */
//    public void setCouponStatus(Object couponStatus) {
//        this.couponStatus = couponStatus;
//    }
//
//    /**
//     *
//     * @return
//     * The couponImage
//     */
//    public String getCouponImage() {
//        return couponImage;
//    }
//
//    /**
//     *
//     * @param couponImage
//     * The coupon_image
//     */
//    public void setCouponImage(String couponImage) {
//        this.couponImage = couponImage;
//    }


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coupon_title")
    @Expose
    private String couponTitle;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("coupon_image")
    @Expose
    private String couponImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponTitle() {
        return couponTitle;
    }

    public void setCouponTitle(String couponTitle) {
        this.couponTitle = couponTitle;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCouponImage() {
        return couponImage;
    }

    public void setCouponImage(String couponImage) {
        this.couponImage = couponImage;
    }


}
