package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nextbrain on 27/3/17.
 */

public class StoInfoOutletDetails implements Serializable {


    @SerializedName("tax_label_name")
    @Expose
    private String taxLabelName;
    @SerializedName("outlets_id")
    @Expose
    private Integer outletsId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("url_index")
    @Expose
    private String urlIndex;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("average_rating")
    @Expose
    private Integer averageRating;
    @SerializedName("preparation_time")
    @Expose
    private String preparationTime;


    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;
    @SerializedName("open_status")
    @Expose
    private Integer openStatus;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("vendors_id")
    @Expose
    private Integer vendorsId;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
    @SerializedName("cuisine_ids")
    @Expose
    private String cuisineIds;
    @SerializedName("label_name")
    @Expose
    private String labelName;
    @SerializedName("tax_percentage")
    @Expose
    private String taxPercentage;
    @SerializedName("delivery_cost_fixed")
    @Expose
    private String deliveryCostFixed;
    @SerializedName("delivery_type")
    @Expose
    private Integer deliveryType;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisineName;
    @SerializedName("open_restaurant")
    @Expose
    private Integer openRestaurant;
    @SerializedName("category_list")
    @Expose
    private List<CategoryList> categoryList = null;
    @SerializedName("user_favourite")
    @Expose
    private Integer userFavourite;
    @SerializedName("payment_mode")
    @Expose
    private Integer payment_mode;



    @SerializedName("open_time")
    @Expose
    private List<OpenTime> openTime = new ArrayList<>();

    public String getTaxLabelName() {
        return taxLabelName;
    }

    public void setTaxLabelName(String taxLabelName) {
        this.taxLabelName = taxLabelName;
    }

    public Integer getOutletsId() {
        return outletsId;
    }

    public void setOutletsId(Integer outletsId) {
        this.outletsId = outletsId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getUrlIndex() {
        return urlIndex;
    }

    public void setUrlIndex(String urlIndex) {
        this.urlIndex = urlIndex;
    }

    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public Integer getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Integer averageRating) {
        this.averageRating = averageRating;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getVendorsId() {
        return vendorsId;
    }

    public void setVendorsId(Integer vendorsId) {
        this.vendorsId = vendorsId;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }
    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getCuisineIds() {
        return cuisineIds;
    }

    public void setCuisineIds(String cuisineIds) {
        this.cuisineIds = cuisineIds;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public String getDeliveryCostFixed() {
        return deliveryCostFixed;
    }

    public void setDeliveryCostFixed(String deliveryCostFixed) {
        this.deliveryCostFixed = deliveryCostFixed;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
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

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public Integer getOpenRestaurant() {
        return openRestaurant;
    }

    public void setOpenRestaurant(Integer openRestaurant) {
        this.openRestaurant = openRestaurant;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public Integer getUserFavourite() {
        return userFavourite;
    }

    public void setUserFavourite(Integer userFavourite) {
        this.userFavourite = userFavourite;
    }

    public Integer getPaymentMode() {
        return payment_mode;
    }

    public void setPaymentMode(Integer payment_mode) {
        this.payment_mode = payment_mode;
    }



    public List<OpenTime> getOpenTime() {
        return openTime;
    }

    public void setOpenTime(List<OpenTime> openTime) {
        this.openTime = openTime;
    }
    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }


}
