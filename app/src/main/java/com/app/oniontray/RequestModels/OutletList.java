
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OutletList {

    @SerializedName("outlets_id")
    @Expose
    private Integer outletsId;
    @SerializedName("outlets_vendors_id")
    @Expose
    private Integer outletsVendorsId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("outlets_delivery_time")
    @Expose
    private String outletsDeliveryTime;
    @SerializedName("outlets_average_rating")
    @Expose
    private String outletsAverageRating;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
//    @SerializedName("category_ids")
//    @Expose
//    private String categoryIds;
    @SerializedName("delivery_charges_fixed")
    @Expose
    private String deliveryChargesFixed;
    @SerializedName("delivery_charges_variation")
    @Expose
    private String deliveryChargesVariation;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimum_order_amount;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisine_name;
    @SerializedName("open_restaurant")
    @Expose
    private Integer openRestaurant;

    /**
     *
     * @return
     * The outletsId
     */
    public Integer getOutletsId() {
        return outletsId;
    }

    /**
     *
     * @param outletsId
     * The outlets_id
     */
    public void setOutletsId(Integer outletsId) {
        this.outletsId = outletsId;
    }

    /**
     *
     * @return
     * The outletsVendorsId
     */
    public Integer getOutletsVendorsId() {
        return outletsVendorsId;
    }

    /**
     *
     * @param outletsVendorsId
     * The outlets_vendors_id
     */
    public void setOutletsVendorsId(Integer outletsVendorsId) {
        this.outletsVendorsId = outletsVendorsId;
    }

    /**
     *
     * @return
     * The outletName
     */
    public String getOutletName() {
        return outletName;
    }

    /**
     *
     * @param outletName
     * The outlet_name
     */
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    /**
     *
     * @return
     * The contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     *
     * @param contactAddress
     * The contact_address
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     *
     * @return
     * The outletsDeliveryTime
     */
    public String getOutletsDeliveryTime() {
        return outletsDeliveryTime;
    }

    /**
     *
     * @param outletsDeliveryTime
     * The outlets_delivery_time
     */
    public void setOutletsDeliveryTime(String outletsDeliveryTime) {
        this.outletsDeliveryTime = outletsDeliveryTime;
    }

    /**
     *
     * @return
     * The outletsAverageRating
     */
    public String getOutletsAverageRating() {
        return outletsAverageRating;
    }

    /**
     *
     * @param outletsAverageRating
     * The outlets_average_rating
     */
    public void setOutletsAverageRating(String outletsAverageRating) {
        this.outletsAverageRating = outletsAverageRating;
    }

    /**
     *
     * @return
     * The logoImage
     */
    public String getLogoImage() {
        return logoImage;
    }

    /**
     *
     * @param logoImage
     * The logo_image
     */
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

//    /**
//     *
//     * @return
//     * The categoryIds
//     */
//    public String getCategoryIds() {
//        return categoryIds;
//    }
//
//    /**
//     *
//     * @param categoryIds
//     * The category_ids
//     */
//    public void setCategoryIds(String categoryIds) {
//        this.categoryIds = categoryIds;
//    }

    /**
     *
     * @return
     * The deliveryChargesFixed
     */
    public String getDeliveryChargesFixed() {
        return deliveryChargesFixed;
    }

    /**
     *
     * @param deliveryChargesFixed
     * The delivery_charges_fixed
     */
    public void setDeliveryChargesFixed(String deliveryChargesFixed) {
        this.deliveryChargesFixed = deliveryChargesFixed;
    }

    /**
     *
     * @return
     * The deliveryChargesVariation
     */
    public String getDeliveryChargesVariation() {
        return deliveryChargesVariation;
    }

    /**
     *
     * @param deliveryChargesVariation
     * The delivery_charges_variation
     */
    public void setDeliveryChargesVariation(String deliveryChargesVariation) {
        this.deliveryChargesVariation = deliveryChargesVariation;
    }

    /**
     *
     * @return
     * The minimum_order_amount
     */
    public String getMinimumOrderAmount() {
        return minimum_order_amount;
    }

    /**
     *
     * @param minimum_order_amount
     * The minimum_order_amount
     */
    public void setMinimumOrderAmount(String minimum_order_amount) {
        this.minimum_order_amount = minimum_order_amount;
    }

    /**
     *
     * @return
     * The cuisine_name
     */
    public String getCuisineName() {
        return cuisine_name;
    }

    /**
     *
     * @param cuisine_name
     * The cuisine_name
     */
    public void setCuisineName(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }


    public Integer getOpenRestaurant() {
        return openRestaurant;
    }

    public void setOpenRestaurant(Integer openRestaurant) {
        this.openRestaurant = openRestaurant;
    }



}