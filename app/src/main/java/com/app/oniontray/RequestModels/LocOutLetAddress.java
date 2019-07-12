package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LocOutLetAddress {

    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("outlets_delivery_time")
    @Expose
    private String outletsDeliveryTime;
    @SerializedName("outlets_average_rating")
    @Expose
    private Integer outletsAverageRating;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
    @SerializedName("category_ids")
    @Expose
    private String categoryIds;
    @SerializedName("delivery_charges_fixed")
    @Expose
    private String deliveryChargesFixed;
    @SerializedName("delivery_charges_variation")
    @Expose
    private String deliveryChargesVariation;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("minimum_order_amount")
    @Expose
    private String MinimumOrderAmount;
    @SerializedName("category_name")
    @Expose
    private String CategoryName;

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletsDeliveryTime() {
        return outletsDeliveryTime;
    }

    public void setOutletsDeliveryTime(String outletsDeliveryTime) {
        this.outletsDeliveryTime = outletsDeliveryTime;
    }

    public Integer getOutletsAverageRating() {
        return outletsAverageRating;
    }

    public void setOutletsAverageRating(Integer outletsAverageRating) {
        this.outletsAverageRating = outletsAverageRating;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getDeliveryChargesFixed() {
        return deliveryChargesFixed;
    }

    public void setDeliveryChargesFixed(String deliveryChargesFixed) {
        this.deliveryChargesFixed = deliveryChargesFixed;
    }

    public String getDeliveryChargesVariation() {
        return deliveryChargesVariation;
    }

    public void setDeliveryChargesVariation(String deliveryChargesVariation) {
        this.deliveryChargesVariation = deliveryChargesVariation;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMinimumOrderAmount() {
        return MinimumOrderAmount;
    }

    public void setMinimumOrderAmount(String MinimumOrderAmount) {
        this.MinimumOrderAmount = MinimumOrderAmount;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }


}
