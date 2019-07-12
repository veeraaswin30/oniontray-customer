
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoreList_Data implements Serializable{

//    @SerializedName("vendors_id")
//    @Expose
//    private Integer vendorsId;
//    @SerializedName("vendor_name")
//    @Expose
//    private String vendorName;
//    @SerializedName("category_ids")
//    @Expose
//    private String categoryIds;
//    @SerializedName("contact_address")
//    @Expose
//    private String contactAddress;
//    @SerializedName("vendors_delivery_time")
//    @Expose
//    private String vendorsDeliveryTime;
//    @SerializedName("vendors_average_rating")
//    @Expose
//    private String vendorsAverageRating;
//    @SerializedName("logo_image")
//    @Expose
//    private String logoImage;
//    @SerializedName("outlets_count")
//    @Expose
//    private Integer outletsCount;
//    @SerializedName("outlets_id")
//    @Expose
//    private String outletsId;
//    @SerializedName("delivery_charges_fixed")
//    @Expose
//    private int delivery_charges_fixed;
//    @SerializedName("delivery_charges_variation")
//    @Expose
//    private String deliveryChargesVariation;
//    @SerializedName("cuisine_name")
//    @Expose
//    private String cuisine_name;
//    @SerializedName("location_name")
//    @Expose
//    private String location_name;
//    @SerializedName("outlet_name")
//    @Expose
//    private String outlet_name;
//    @SerializedName("outlets_average_rating")
//    @Expose
//    private String outlets_average_rating;
//    @SerializedName("vendors_total_rating")
//    @Expose
//    private Integer vendorsTotalRating;
//    @SerializedName("minimum_order_amount")
//    @Expose
//    private String minimumOrderAmount;
//    @SerializedName("outlet_total_rating")
//    @Expose
//    private String outlet_total_rating;
//    @SerializedName("outlet_delivery_time")
//    @Expose
//    private String outlet_delivery_time;
//    @SerializedName("open_restaurant")
//    @Expose
//    private int open_restaurant;
//
//    /**
//     *
//     * @return
//     *     The vendorsId
//     */
//    public Integer getVendorsId() {
//        return vendorsId;
//    }
//
//    /**
//     *
//     * @param vendorsId
//     *     The vendors_id
//     */
//    public void setVendorsId(Integer vendorsId) {
//        this.vendorsId = vendorsId;
//    }
//
//    /**
//     *
//     * @return
//     *     The vendorName
//     */
//    public String getVendorName() {
//        return vendorName;
//    }
//
//    /**
//     *
//     * @param vendorName
//     *     The vendor_name
//     */
//    public void setVendorName(String vendorName) {
//        this.vendorName = vendorName;
//    }
//
//    /**
//     *
//     * @return
//     *     The categoryIds
//     */
//    public String getCategoryIds() {
//        return categoryIds;
//    }
//
//    /**
//     *
//     * @param categoryIds
//     *     The category_ids
//     */
//    public void setCategoryIds(String categoryIds) {
//        this.categoryIds = categoryIds;
//    }
//
//    /**
//     *
//     * @return
//     *     The contactAddress
//     */
//    public String getContactAddress() {
//        return contactAddress;
//    }
//
//    /**
//     *
//     * @param contactAddress
//     *     The contact_address
//     */
//    public void setContactAddress(String contactAddress) {
//        this.contactAddress = contactAddress;
//    }
//
//    /**
//     *
//     * @return
//     *     The vendorsDeliveryTime
//     */
//    public String getVendorsDeliveryTime() {
//        return vendorsDeliveryTime;
//    }
//
//    /**
//     *
//     * @param vendorsDeliveryTime
//     *     The vendors_delivery_time
//     */
//    public void setVendorsDeliveryTime(String vendorsDeliveryTime) {
//        this.vendorsDeliveryTime = vendorsDeliveryTime;
//    }
//
//    /**
//     *
//     * @return
//     *     The vendorsAverageRating
//     */
//    public String getVendorsAverageRating() {
//        return vendorsAverageRating;
//    }
//
//    /**
//     *
//     * @param vendorsAverageRating
//     *     The vendors_average_rating
//     */
//    public void setVendorsAverageRating(String vendorsAverageRating) {
//        this.vendorsAverageRating = vendorsAverageRating;
//    }
//
//    /**
//     *
//     * @return
//     *     The logoImage
//     */
//    public String getLogoImage() {
//        return logoImage;
//    }
//
//    /**
//     *
//     * @param logoImage
//     *     The logo_image
//     */
//    public void setLogoImage(String logoImage) {
//        this.logoImage = logoImage;
//    }
//
//    /**
//     *
//     * @return
//     *     The outletsCount
//     */
//    public Integer getOutletsCount() {
//        return outletsCount;
//    }
//
//    /**
//     *
//     * @param outletsCount
//     *     The outlets_count
//     */
//    public void setOutletsCount(Integer outletsCount) {
//        this.outletsCount = outletsCount;
//    }
//
//    /**
//     *
//     * @return
//     *     The outletsId
//     */
//    public String getOutletsId() {
//        return outletsId;
//    }
//
//    /**
//     *
//     * @param outletsId
//     *     The outlets_id
//     */
//    public void setOutletsId(String outletsId) {
//        this.outletsId = outletsId;
//    }
//
//    /**
//     *
//     * @return
//     *     The delivery_charges_fixed
//     */
//    public int getFixedDeliveryCharges() {
//        return delivery_charges_fixed;
//    }
//
//    /**
//     *
//     * @param delivery_charges_fixed
//     *     The delivery_charges_fixed
//     */
//    public void setFixedDeliveryCharges(int delivery_charges_fixed) {
//        this.delivery_charges_fixed = delivery_charges_fixed;
//    }
//
//    /**
//     *
//     * @return
//     *     The cuisine_name
//     */
//    public String getCuisineNames() {
//        return cuisine_name;
//    }
//
//    /**
//     *
//     * @param cuisine_name
//     *     The cuisine_name
//     */
//    public void setCuisineNames(String cuisine_name) {
//        this.cuisine_name = cuisine_name;
//    }
//
//    /**
//     *
//     * @return
//     *     The location_name
//     */
//    public String getLocationName() {
//        return location_name;
//    }
//
//    /**
//     *
//     * @param location_name
//     *     The location_name
//     */
//    public void setLocationName(String location_name) {
//        this.location_name = location_name;
//    }
//
//    /**
//     *
//     * @return
//     *     The outlet_name
//     */
//    public String getOutletName() {
//        return outlet_name;
//    }
//
//    /**
//     *
//     * @param outlet_name
//     *     The outlet_name
//     */
//    public void setOutletName(String outlet_name) {
//        this.outlet_name = outlet_name;
//    }
//
//    /**
//     *
//     * @return
//     *     The outlets_average_rating
//     */
//    public String getOutletsAverageRating() {
//        return outlets_average_rating;
//    }
//
//    /**
//     *
//     * @param outlets_average_rating
//     *     The outlets_average_rating
//     */
//    public void setOutletsAverageRating(String outlets_average_rating) {
//        this.outlets_average_rating = outlets_average_rating;
//    }
//
//    /**
//     *
//     * @return
//     *     The outlet_total_rating
//     */
//    public String getOutletTotalRating() {
//        return outlet_total_rating;
//    }
//
//    /**
//     *
//     * @param outlet_total_rating
//     *     The outlet_total_rating
//     */
//    public void setOutletTotalRating(String outlet_total_rating) {
//        this.outlet_total_rating = outlet_total_rating;
//    }
//
//    /**
//     *
//     * @return
//     *     The outlet_delivery_time
//     */
//    public String getOutletDeliveryTime() {
//        return outlet_delivery_time;
//    }
//
//    /**
//     *
//     * @param outlet_delivery_time
//     *     The outlet_delivery_time
//     */
//    public void setOutletDeliveryTime(String outlet_delivery_time) {
//        this.outlet_delivery_time = outlet_delivery_time;
//    }
//
//    /**
//     *
//     * @return
//     *     The open_restaurant
//     */
//    public int getOpenRestaurant() {
//        return open_restaurant;
//    }
//
//    /**
//     *
//     * @param open_restaurant
//     *     The open_restaurant
//     */
//    public void setOpenRestaurant(int open_restaurant) {
//        this.open_restaurant = open_restaurant;
//    }
//
//    public Integer getVendorsTotalRating() {
//        return vendorsTotalRating;
//    }
//
//    public void setVendorsTotalRating(Integer vendorsTotalRating) {
//        this.vendorsTotalRating = vendorsTotalRating;
//    }
//
//    public String getMinimumOrderAmount() {
//        return minimumOrderAmount;
//    }
//
//    public void setMinimumOrderAmount(String minimumOrderAmount) {
//        this.minimumOrderAmount = minimumOrderAmount;
//    }
//
//    public String getDeliveryChargesVariation() {
//        return deliveryChargesVariation;
//    }
//
//    public void setDeliveryChargesVariation(String deliveryChargesVariation) {
//        this.deliveryChargesVariation = deliveryChargesVariation;
//    }

//    @SerializedName("vendors_id")
//    @Expose
//    private Integer vendorsId;
//    @SerializedName("vendor_name")
//    @Expose
//    private String vendorName;
//    @SerializedName("category_ids")
//    @Expose
//    private String categoryIds;
//    @SerializedName("contact_address")
//    @Expose
//    private String contactAddress;
//    @SerializedName("location_name")
//    @Expose
//    private String locationName;
//    @SerializedName("delivery_mode")
//    @Expose
//    private Integer deliveryMode;
//    @SerializedName("vendor_description")
//    @Expose
//    private String vendorDescription;
//    @SerializedName("vendors_delivery_time")
//    @Expose
//    private String vendorsDeliveryTime;
//    @SerializedName("delivery_charges_fixed")
//    @Expose
//    private String deliveryChargesFixed;
//    @SerializedName("delivery_charges_variation")
//    @Expose
//    private String deliveryChargesVariation;
//    @SerializedName("vendors_average_rating")
//    @Expose
//    private Integer vendorsAverageRating;
//    @SerializedName("outlets_average_rating")
//    @Expose
//    private Integer outletsAverageRating;
//    @SerializedName("vendors_total_rating")
//    @Expose
//    private Integer vendorsTotalRating;
//    @SerializedName("minimum_order_amount")
//    @Expose
//    private String minimumOrderAmount;
//    @SerializedName("cuisine_name")
//    @Expose
//    private String cuisineName;
//    @SerializedName("logo_image")
//    @Expose
//    private String logoImage;
//    @SerializedName("outlets_count")
//    @Expose
//    private Integer outletsCount;
//    @SerializedName("outlets_id")
//    @Expose
//    private String outletsId;
//    @SerializedName("outlets_name")
//    @Expose
//    private String outletName;
//    @SerializedName("outlet_delivery_time")
//    @Expose
//    private String outletDeliveryTime;
//    @SerializedName("outlet_total_rating")
//    @Expose
//    private Integer outletTotalRating;
//    @SerializedName("open_restaurant")
//    @Expose
//    private Integer openRestaurant;
//
//    @SerializedName("url_index")
//    @Expose
//    private String url_index;
//
//    @SerializedName("featured_image")
//    @Expose
//    private String featured_image;
//
//    @SerializedName("delivery_time")
//    @Expose
//    private String delivery_time;
//
//    @SerializedName("average_rating")
//    @Expose
//    private String average_rating;
//
//    @SerializedName("delivery_type")
//    @Expose
//    private Integer delivery_type;
//
//    @SerializedName("delivery_cost_fixed")
//    @Expose
//    private String delivery_cost_fixed;
//
//
//
//    public Integer getVendorsId() {
//        return vendorsId;
//    }
//
//    public void setVendorsId(Integer vendorsId) {
//        this.vendorsId = vendorsId;
//    }
//
//    public String getVendorName() {
//        return vendorName;
//    }
//
//    public void setVendorName(String vendorName) {
//        this.vendorName = vendorName;
//    }
//
//    public String getCategoryIds() {
//        return categoryIds;
//    }
//
//    public void setCategoryIds(String categoryIds) {
//        this.categoryIds = categoryIds;
//    }
//
//    public String getContactAddress() {
//        return contactAddress;
//    }
//
//    public void setContactAddress(String contactAddress) {
//        this.contactAddress = contactAddress;
//    }
//
//    public String getLocationName() {
//        return locationName;
//    }
//
//    public void setLocationName(String locationName) {
//        this.locationName = locationName;
//    }
//
//    public Integer getDeliveryMode() {
//        return deliveryMode;
//    }
//
//    public void setDeliveryMode(Integer deliveryMode) {
//        this.deliveryMode = deliveryMode;
//    }
//
//    public String getVendorDescription() {
//        return vendorDescription;
//    }
//
//    public void setVendorDescription(String vendorDescription) {
//        this.vendorDescription = vendorDescription;
//    }
//
//    public String getVendorsDeliveryTime() {
//        return vendorsDeliveryTime;
//    }
//
//    public void setVendorsDeliveryTime(String vendorsDeliveryTime) {
//        this.vendorsDeliveryTime = vendorsDeliveryTime;
//    }
//
//    public String getDeliveryChargesFixed() {
//        return deliveryChargesFixed;
//    }
//
//    public void setDeliveryChargesFixed(String deliveryChargesFixed) {
//        this.deliveryChargesFixed = deliveryChargesFixed;
//    }
//
//    public String getDeliveryChargesVariation() {
//        return deliveryChargesVariation;
//    }
//
//    public void setDeliveryChargesVariation(String deliveryChargesVariation) {
//        this.deliveryChargesVariation = deliveryChargesVariation;
//    }
//
//    public Integer getVendorsAverageRating() {
//        return vendorsAverageRating;
//    }
//
//    public void setVendorsAverageRating(Integer vendorsAverageRating) {
//        this.vendorsAverageRating = vendorsAverageRating;
//    }
//
//    public Integer getOutletsAverageRating() {
//        return outletsAverageRating;
//    }
//
//    public void setOutletsAverageRating(Integer outletsAverageRating) {
//        this.outletsAverageRating = outletsAverageRating;
//    }
//
//    public Integer getVendorsTotalRating() {
//        return vendorsTotalRating;
//    }
//
//    public void setVendorsTotalRating(Integer vendorsTotalRating) {
//        this.vendorsTotalRating = vendorsTotalRating;
//    }
//
//    public String getMinimumOrderAmount() {
//        return minimumOrderAmount;
//    }
//
//    public void setMinimumOrderAmount(String minimumOrderAmount) {
//        this.minimumOrderAmount = minimumOrderAmount;
//    }
//
//    public String getCuisineName() {
//        return cuisineName;
//    }
//
//    public void setCuisineName(String cuisineName) {
//        this.cuisineName = cuisineName;
//    }
//
//    public String getLogoImage() {
//        return logoImage;
//    }
//
//    public void setLogoImage(String logoImage) {
//        this.logoImage = logoImage;
//    }
//
//    public Integer getOutletsCount() {
//        return outletsCount;
//    }
//
//    public void setOutletsCount(Integer outletsCount) {
//        this.outletsCount = outletsCount;
//    }
//
//    public String getOutletsId() {
//        return outletsId;
//    }
//
//    public void setOutletsId(String outletsId) {
//        this.outletsId = outletsId;
//    }
//
//    public String getOutletName() {
//        return outletName;
//    }
//
//    public void setOutletName(String outletName) {
//        this.outletName = outletName;
//    }
//
//    public String getOutletDeliveryTime() {
//        return outletDeliveryTime;
//    }
//
//    public void setOutletDeliveryTime(String outletDeliveryTime) {
//        this.outletDeliveryTime = outletDeliveryTime;
//    }
//
//    public Integer getOutletTotalRating() {
//        return outletTotalRating;
//    }
//
//    public void setOutletTotalRating(Integer outletTotalRating) {
//        this.outletTotalRating = outletTotalRating;
//    }
//
//    public Integer getOpenRestaurant() {
//        return openRestaurant;
//    }
//
//    public void setOpenRestaurant(Integer openRestaurant) {
//        this.openRestaurant = openRestaurant;
//    }
//
//
//    public String getUrlIndex() {
//        return url_index;
//    }
//
//    public void setUrlIndex(String url_index) {
//        this.url_index = url_index;
//    }
//
//    public String getFeaturedImage() {
//        return featured_image;
//    }
//
//    public void setFeaturedImage(String featured_image) {
//        this.featured_image = featured_image;
//    }
//
//    public String getDeliveryTime() {
//        return delivery_time;
//    }
//
//    public void setDeliveryTime(String delivery_time) {
//        this.delivery_time = delivery_time;
//    }
//
//    public String getAverageRating() {
//        return average_rating;
//    }
//
//    public void setAverageRating(String average_rating) {
//        this.average_rating = average_rating;
//    }
//
//    public Integer getDeliveryType() {
//        return delivery_type;
//    }
//
//    public void setDeliveryType(Integer delivery_type) {
//        this.delivery_type = delivery_type;
//    }
//
//    public Integer getDeliveryType() {
//        return delivery_cost_fixed;
//    }
//
//    public void setDeliveryType(Integer delivery_cost_fixed) {
//        this.delivery_cost_fixed = delivery_cost_fixed;
//    }




    @SerializedName("vendors_id")
    @Expose
    private Integer vendorsId;
    @SerializedName("outlets_id")
    @Expose
    private Integer outletsId;
    @SerializedName("outlets_name")
    @Expose
    private String outletsName;
    @SerializedName("url_index")
    @Expose
    private String urlIndex;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisineName;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("average_rating")
    @Expose
    private Integer averageRating;
    @SerializedName("open_restaurant")
    @Expose
    private Integer openRestaurant;
    @SerializedName("delivery_type")
    @Expose
    private Integer deliveryType;
    @SerializedName("promotion")
    @Expose
    private Integer promotion;
    @SerializedName("promotion_name")
    @Expose
    private String promotion_name;
    @SerializedName("delivery_cost_fixed")
    @Expose
    private String deliveryCostFixed;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;

    public Integer getVendorsId() {
        return vendorsId;
    }

    public void setVendorsId(Integer vendorsId) {
        this.vendorsId = vendorsId;
    }

    public Integer getOutletsId() {
        return outletsId;
    }

    public void setOutletsId(Integer outletsId) {
        this.outletsId = outletsId;
    }

    public String getOutletsName() {
        return outletsName;
    }

    public void setOutletsName(String outletsName) {
        this.outletsName = outletsName;
    }

    public String getUrlIndex() {
        return urlIndex;
    }

    public void setUrlIndex(String urlIndex) {
        this.urlIndex = urlIndex;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getOpenRestaurant() {
        return openRestaurant;
    }

    public void setOpenRestaurant(Integer openRestaurant) {
        this.openRestaurant = openRestaurant;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getPromotionName() {
        return promotion_name;
    }

    public void setPromotionName(String promotion_name) {
        this.promotion_name = promotion_name;
    }

    public String getDeliveryCostFixed() {
        return deliveryCostFixed;
    }

    public void setDeliveryCostFixed(String deliveryCostFixed) {
        this.deliveryCostFixed = deliveryCostFixed;
    }

    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }



}
