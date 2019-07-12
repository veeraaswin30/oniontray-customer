package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckDeliveryAddressData {

    @SerializedName("restaurant_delivery_settings_id")
    @Expose
    private Integer restaurantDeliverySettingsId;
    @SerializedName("delivery_type")
    @Expose
    private Integer deliveryType;
    @SerializedName("tax_type")
    @Expose
    private Integer taxType;
    @SerializedName("label_name")
    @Expose
    private String labelName;
    @SerializedName("tax_percentage")
    @Expose
    private String taxPercentage;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("delivery_cost_fixed")
    @Expose
    private String deliveryCostFixed;

    public Integer getRestaurantDeliverySettingsId() {
        return restaurantDeliverySettingsId;
    }

    public void setRestaurantDeliverySettingsId(Integer restaurantDeliverySettingsId) {
        this.restaurantDeliverySettingsId = restaurantDeliverySettingsId;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Integer getTaxType() {
        return taxType;
    }

    public void setTaxType(Integer taxType) {
        this.taxType = taxType;
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getDeliveryCostFixed() {
        return deliveryCostFixed;
    }

    public void setDeliveryCostFixed(String deliveryCostFixed) {
        this.deliveryCostFixed = deliveryCostFixed;
    }

}
