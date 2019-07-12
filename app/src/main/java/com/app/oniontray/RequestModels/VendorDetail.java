
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VendorDetail implements Serializable {

    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;

    @SerializedName("delivery_mode")
    @Expose
    private Integer deliveryMode;
    @SerializedName("online_available_option")
    @Expose
    private Integer onlineAvailableOption;

    @SerializedName("vendors_delivery_time")
    @Expose
    private String vendorsDeliveryTime;
    @SerializedName("vendors_average_rating")
    @Expose
    private Integer vendorsAverageRating;
    @SerializedName("outlets_contact_address")
    @Expose
    private String outletsContactAddress;
    @SerializedName("outlets_id")
    @Expose
    private Integer outletsId;
    @SerializedName("outlets_vendors_id")
    @Expose
    private Integer outletsVendorsId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("outlets_delivery_time")
    @Expose
    private String outletsDeliveryTime;
    @SerializedName("outlets_average_rating")
    @Expose
    private Integer outletsAverageRating;
    @SerializedName("outlets_pickup_time")
    @Expose
    private String outletsPickupTime;
    @SerializedName("outlets_latitude")
    @Expose
    private String outletsLatitude;
    @SerializedName("outlets_longitude")
    @Expose
    private String outletsLongitude;
    @SerializedName("outlets_delivery_charges_fixed")
    @Expose
    private String outletsDeliveryChargesFixed;
    @SerializedName("outlets_delivery_charges_variation")
    @Expose
    private String outletsDeliveryChargesVariation;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("category_list")
    @Expose
    private List<CategoryList> categoryList = new ArrayList<>();
    @SerializedName("user_favourite")
    @Expose
    private Integer userFavourite;

    @SerializedName("deliver_slot")
    @Expose
    private InfoDeliverSlot deliverSlot;
    @SerializedName("open_time")
    @Expose
    private List<OpenTime> openTime = new ArrayList<>();
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisine_name;
    @SerializedName("open_restaurant")
    @Expose
    private int open_restaurant;

    /**
     *
     * @return
     * The vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     *
     * @param vendorId
     * The vendor_id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     *
     * @return
     * The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     *
     * @param vendorName
     * The vendor_name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     *
     * @return
     * The featuredImage
     */
    public String getFeaturedImage() {
        return featuredImage;
    }

    /**
     *
     * @param featuredImage
     * The featured_image
     */
    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
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

    /**
     *
     * @return
     * The vendorsDeliveryTime
     */
    public String getVendorsDeliveryTime() {
        return vendorsDeliveryTime;
    }

    /**
     *
     * @param vendorsDeliveryTime
     * The vendors_delivery_time
     */
    public void setVendorsDeliveryTime(String vendorsDeliveryTime) {
        this.vendorsDeliveryTime = vendorsDeliveryTime;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Integer getOnlineAvailableOption() {
        return onlineAvailableOption;
    }

    public void setOnlineAvailableOption(Integer onlineAvailableOption) {
        this.onlineAvailableOption = onlineAvailableOption;
    }
    /**
     *
     * @return
     * The vendorsAverageRating
     */
    public Integer getVendorsAverageRating() {
        return vendorsAverageRating;
    }

    /**
     *
     * @param vendorsAverageRating
     * The vendors_average_rating
     */
    public void setVendorsAverageRating(Integer vendorsAverageRating) {
        this.vendorsAverageRating = vendorsAverageRating;
    }

    /**
     *
     * @return
     * The outletsContactAddress
     */
    public String getOutletsContactAddress() {
        return outletsContactAddress;
    }

    /**
     *
     * @param outletsContactAddress
     * The outlets_contact_address
     */
    public void setOutletsContactAddress(String outletsContactAddress) {
        this.outletsContactAddress = outletsContactAddress;
    }

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
    public Integer getOutletsAverageRating() {
        return outletsAverageRating;
    }

    /**
     *
     * @param outletsAverageRating
     * The outlets_average_rating
     */
    public void setOutletsAverageRating(Integer outletsAverageRating) {
        this.outletsAverageRating = outletsAverageRating;
    }

    /**
     *
     * @return
     * The outletsPickupTime
     */
    public String getOutletsPickupTime() {
        return outletsPickupTime;
    }

    /**
     *
     * @param outletsPickupTime
     * The outlets_pickup_time
     */
    public void setOutletsPickupTime(String outletsPickupTime) {
        this.outletsPickupTime = outletsPickupTime;
    }

    /**
     *
     * @return
     * The outletsLatitude
     */
    public String getOutletsLatitude() {
        return outletsLatitude;
    }

    /**
     *
     * @param outletsLatitude
     * The outlets_latitude
     */
    public void setOutletsLatitude(String outletsLatitude) {
        this.outletsLatitude = outletsLatitude;
    }

    /**
     *
     * @return
     * The outletsLongitude
     */
    public String getOutletsLongitude() {
        return outletsLongitude;
    }

    /**
     *
     * @param outletsLongitude
     * The outlets_longitude
     */
    public void setOutletsLongitude(String outletsLongitude) {
        this.outletsLongitude = outletsLongitude;
    }

    /**
     *
     * @return
     * The outletsDeliveryChargesFixed
     */
    public String getOutletsDeliveryChargesFixed() {
        return outletsDeliveryChargesFixed;
    }

    /**
     *
     * @param outletsDeliveryChargesFixed
     * The outlets_delivery_charges_fixed
     */
    public void setOutletsDeliveryChargesFixed(String outletsDeliveryChargesFixed) {
        this.outletsDeliveryChargesFixed = outletsDeliveryChargesFixed;
    }

    /**
     *
     * @return
     * The categoryList
     */
    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    /**
     *
     * @param categoryList
     * The category_list
     */
    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     *
     * @return
     * The userFavourite
     */
    public Integer getUserFavourite() {
        return userFavourite;
    }

    /**
     *
     * @param userFavourite
     * The user_favourite
     */
    public void setUserFavourite(Integer userFavourite) {
        this.userFavourite = userFavourite;
    }

    public String getOutletsDeliveryChargesVariation() {
        return outletsDeliveryChargesVariation;
    }

    public void setOutletsDeliveryChargesVariation(String outletsDeliveryChargesVariation) {
        this.outletsDeliveryChargesVariation = outletsDeliveryChargesVariation;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    /**
     *
     * @return
     * The deliverSlot
     */
    public InfoDeliverSlot getInfoDeliverSlot() {
        return deliverSlot;
    }

    /**
     *
     * @param deliverSlot
     * The deliver_slot
     */
    public void setInfoDeliverSlot(InfoDeliverSlot deliverSlot) {
        this.deliverSlot = deliverSlot;
    }

    /**
     *
     * @return
     * The openTime
     */
    public List<OpenTime> getOpenTime() {
        return openTime;
    }

    /**
     *
     * @param openTime
     * The open_time
     */
    public void setOpenTime(List<OpenTime> openTime) {
        this.openTime = openTime;
    }

    /**
     *
     * @return
     * The minimumOrderAmount
     */
    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    /**
     *
     * @param minimumOrderAmount
     * The minimumOrderAmount
     */
    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
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

    /**
     *
     * @return
     * The open_restaurant
     */
    public int getOpenRestaurant() {
        return open_restaurant;
    }

    /**
     *
     * @param open_restaurant
     * The open_restaurant
     */
    public void setOpenRestaurant(int open_restaurant) {
        this.open_restaurant = open_restaurant;
    }


}