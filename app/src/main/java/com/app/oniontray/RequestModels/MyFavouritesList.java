package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFavouritesList {

    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("vendor_description")
    @Expose
    private String vendorDescription;

//    @SerializedName("first_name")
//    @Expose
//    private String firstName;
//    @SerializedName("last_name")
//    @Expose
//    private String lastName;

    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;

//    @SerializedName("outlets_contact_address")
//    @Expose
//    private String outletsContactAddress;

    @SerializedName("delivery_time")
    @Expose
    private String outletsDeliveryTime;
    @SerializedName("outlets_average_rating")
    @Expose
    private String outletsAverageRating;

//    @SerializedName("category_ids")
//    @Expose
//    private String categoryIds;
//    @SerializedName("outlets_delivery_charges_fixed")
//    @Expose
//    private String outlets_delivery_charges_fixed;
//    @SerializedName("outlets_delivery_charges_variation")
//    @Expose
//    private String outlets_delivery_charges_variation;

    @SerializedName("minimum_order_amount")
    @Expose
    private String MinimumOrderAmount;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisine_name;



    /**
     * @return The vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId The vendor_id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName The vendor_name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return The vendorDescription
     */
    public String getVendorDescription() {
        return vendorDescription;
    }

    /**
     * @param vendorDescription The vendor_description
     */
    public void setVendorDescription(String vendorDescription) {
        this.vendorDescription = vendorDescription;
    }

//    /**
//     * @return The firstName
//     */
//    public String getFirstName() {
//        return firstName;
//    }
//
//    /**
//     * @param firstName The first_name
//     */
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

//    /**
//     * @return The lastName
//     */
//    public String getLastName() {
//        return lastName;
//    }
//
//    /**
//     * @param lastName The last_name
//     */
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }

    /**
     * @return The featuredImage
     */
    public String getFeaturedImage() {
        return featuredImage;
    }

    /**
     * @param featuredImage The featured_image
     */
    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    /**
     * @return The logoImage
     */
    public String getLogoImage() {
        return logoImage;
    }

    /**
     * @param logoImage The logo_image
     */
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The outletId
     */
    public Integer getOutletId() {
        return outletId;
    }

    /**
     * @param outletId The outlet_id
     */
    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    /**
     * @return The outletName
     */
    public String getOutletName() {
        return outletName;
    }

    /**
     * @param outletName The outlet_name
     */
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

//    /**
//     * @return The outletsContactAddress
//     */
//    public String getOutletsContactAddress() {
//        return outletsContactAddress;
//    }
//
//    /**
//     * @param outletsContactAddress The outlets_contact_address
//     */
//    public void setOutletsContactAddress(String outletsContactAddress) {
//        this.outletsContactAddress = outletsContactAddress;
//    }

    /**
     * @return The outletsDeliveryTime
     */
    public String getOutletsDeliveryTime() {
        return outletsDeliveryTime;
    }

    /**
     * @param outletsDeliveryTime The outlets_delivery_time
     */
    public void setOutletsDeliveryTime(String outletsDeliveryTime) {
        this.outletsDeliveryTime = outletsDeliveryTime;
    }

    /**
     * @return The outletsAverageRating
     */
    public String getOutletsAverageRating() {
        return outletsAverageRating;
    }

    /**
     * @param outletsAverageRating The outlets_average_rating
     */
    public void setOutletsAverageRating(String outletsAverageRating) {
        this.outletsAverageRating = outletsAverageRating;
    }

//    /**
//     * @return The categoryIds
//     */
//    public String getcategoryIds() {
//        return categoryIds;
//    }
//
//    /**
//     * @param categoryIds The categoryIds
//     */
//    public void setcategoryIds(String categoryIds) {
//        this.categoryIds = categoryIds;
//    }
//
//    /**
//     * @return The outlets_delivery_charges_fixed
//     */
//    public String getOutletsDeliveryChargesFixed() {
//        return outlets_delivery_charges_fixed;
//    }
//
//    /**
//     * @param outlets_delivery_charges_fixed The outlets_delivery_charges_fixed
//     */
//    public void setOutletsDeliveryChargesFixed(String outlets_delivery_charges_fixed) {
//        this.outlets_delivery_charges_fixed = outlets_delivery_charges_fixed;
//    }
//
//    /**
//     * @return The outlets_delivery_charges_variation
//     */
//    public String getOutletsDeliveryChargesVariation() {
//        return outlets_delivery_charges_variation;
//    }
//
//    /**
//     * @param outlets_delivery_charges_variation The outlets_delivery_charges_variation
//     */
//    public void setgetOutletsDeliveryChargesVariation(String outlets_delivery_charges_variation) {
//        this.outlets_delivery_charges_variation = outlets_delivery_charges_variation;
//    }

    /**
     * @return The MinimumOrderAmount
     */
    public String getMinimumOrderAmount() {
        return MinimumOrderAmount;
    }

    /**
     * @param MinimumOrderAmount
     * The MinimumOrderAmount
     */
    public void setMinimumOrderAmount(String MinimumOrderAmount) {
        this.MinimumOrderAmount = MinimumOrderAmount;
    }

    /**
     * @return The cuisine_name
     */
    public String getCuisineName() {
        return cuisine_name;
    }

    /**
     * @param cuisine_name
     * The cuisine_name
     */
    public void setCuisineName(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }


}