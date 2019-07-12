package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutletDetails implements Serializable {

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
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("contact_email")
    @Expose
    private String ContactEmail;
    @SerializedName("payment_mode")
    @Expose
    private Integer paymentMode;
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
    @SerializedName("tax_label_name")
    @Expose
    private String taxLabelName;
    @SerializedName("tax_percentage")
    @Expose
    private String taxPercentage;
    @SerializedName("tax_type")
    @Expose
    private Integer taxType;
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
    @SerializedName("zone_url_index")
    @Expose
    private String zoneUrlIndex;
    @SerializedName("city_url_index")
    @Expose
    private String cityUrlIndex;
    @SerializedName("open_restaurant")
    @Expose
    private Integer openRestaurant;
    @SerializedName("commission_amount")
    @Expose
    private String commissionAmount;

    @SerializedName("platform_charge")
    @Expose
    private String platformCharge;

    public String getPlatformCharge() {
        return platformCharge;
    }

    public void setPlatformCharge(String platformCharge) {
        this.platformCharge = platformCharge;
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
    public String getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        this.commissionAmount = commissionAmount;
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

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public void setContactEmail(String ContactEmail) {
        this.ContactEmail = ContactEmail;
    }

    public Integer getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
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

    public String getTaxLabelName() {
        return taxLabelName;
    }

    public void setTaxLabelName(String taxLabelName) {
        this.taxLabelName = taxLabelName;
    }

    public String getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(String taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
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

    public String getZoneUrlIndex() {
        return zoneUrlIndex;
    }

    public void setZoneUrlIndex(String zoneUrlIndex) {
        this.zoneUrlIndex = zoneUrlIndex;
    }

    public String getCityUrlIndex() {
        return cityUrlIndex;
    }

    public void setCityUrlIndex(String cityUrlIndex) {
        this.cityUrlIndex = cityUrlIndex;
    }

    public Integer getOpenRestaurant() {
        return openRestaurant;
    }

    public void setOpenRestaurant(Integer openRestaurant) {
        this.openRestaurant = openRestaurant;
    }


    private String deliveryAddressID;

    private String deliveryAddress;

    private String deliveryInstruction;

    private String grandTotal;

    private String deliveryPromoCode;

    private String SubTotal;

    private String serviceTax="0";

    private String deliveryCost="0";


    private boolean apply_coupon = false;

    private String coupon_id;

    private String coupon_type;

    private String Coupon_amount;

    private int paymentOption;
    private String deliveryDate;

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(int paymentOption) {
        this.paymentOption = paymentOption;
    }

    public String getDeliveryAddressID() {
        return deliveryAddressID;
    }

    public void setDeliveryAddressID(String deliveryAddress) {
        this.deliveryAddressID = deliveryAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public void setDeliveryInstruction(String deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getDeliveryPromoCode() {
        return deliveryPromoCode;
    }

    public void setDeliveryPromoCode(String deliveryPromoCode) {
        this.deliveryPromoCode = deliveryPromoCode;
    }

    public String getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(String total) {
        SubTotal = total;
    }

    public String getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public String getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }


    public boolean getApplyCoupon() {
        return apply_coupon;
    }

    public void setApplyCoupon(boolean apply_coupon) {
        this.apply_coupon = apply_coupon;
    }

    public String getCouponId() {
        return coupon_id;
    }

    public void setCouponId(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCouponType() {
        return coupon_type;
    }

    public void setCouponType(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCouponAmount() {
        return Coupon_amount;
    }

    public void setCouponAmount(String Coupon_amount) {
        this.Coupon_amount = Coupon_amount;
    }

    public void setExp_check_out(boolean exp_check_out) {
        this.exp_check_out = exp_check_out;
    }

    public boolean exp_check_out = false;


    public boolean getExp_check_out() {
        return exp_check_out;
    }


}
