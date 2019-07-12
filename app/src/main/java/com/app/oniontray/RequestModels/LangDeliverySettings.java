package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by NEXTBRAIN on 2/11/2017.
 */

public class LangDeliverySettings {


    @SerializedName("tax_type")
    @Expose
    private String tax_type;
    @SerializedName("label_name")
    @Expose
    private String label_name;
    @SerializedName("tax_percentage")
    @Expose
    private String tax_percentage;
    @SerializedName("delivery_type")
    @Expose
    private String delivery_type;
    @SerializedName("delivery_cost_fixed")
    @Expose
    private String delivery_cost_fixed;
    @SerializedName("delivery_km_fixed")
    @Expose
    private String delivery_km_fixed;
    @SerializedName("delivery_cost_variation")
    @Expose
    private String delivery_cost_variation;



    public String getTaxType() {
        return tax_type;
    }

    public void setTaxType(String tax_type) {
        this.tax_type = tax_type;
    }

    public String getLabelName() {
        return label_name;
    }

    public void setLabelName(String label_name) {
        this.label_name = label_name;
    }

    public String getTaxPercentage() {
        return tax_percentage;
    }

    public void setTaxPercentage(String tax_percentage) {
        this.tax_percentage = tax_percentage;
    }

    public String getDeliveryType() {
        return delivery_type;
    }

    public void setDeliveryType(String delivery_type) {
        this.delivery_type = delivery_type;
    }

    public String getDeliveryCostFixed() {
        return delivery_cost_fixed;
    }

    public void setDeliveryCostFixed(String delivery_cost_fixed) {
        this.delivery_cost_fixed = delivery_cost_fixed;
    }

    public String getDeliveryKmFixed() {
        return delivery_km_fixed;
    }

    public void setDeliveryKmFixed(String delivery_km_fixed) {
        this.delivery_km_fixed = delivery_km_fixed;
    }

    public String getDeliveryCostVariation() {
        return delivery_cost_variation;
    }

    public void setDeliveryCostVariation(String delivery_cost_variation) {
        this.delivery_cost_variation = delivery_cost_variation;
    }



}
