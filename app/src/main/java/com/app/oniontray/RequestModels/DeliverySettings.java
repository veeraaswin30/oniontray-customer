package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeliverySettings implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("on_off_status")
    @Expose
    private Integer onOffStatus;
    @SerializedName("delivery_type")
    @Expose
    private Integer deliveryType;
    @SerializedName("delivery_cost_fixed")
    @Expose
    private Integer deliveryCostFixed;
    @SerializedName("delivery_cost_variation")
    @Expose
    private Integer deliveryCostVariation;
    @SerializedName("flat_delivery_cost")
    @Expose
    private Integer flatDeliveryCost;
    @SerializedName("minimum_order_amount")
    @Expose
    private Integer minimumOrderAmount;
    @SerializedName("delivery_km_fixed")
    @Expose
    private Integer deliveryKmFixed;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The onOffStatus
     */
    public Integer getOnOffStatus() {
        return onOffStatus;
    }

    /**
     *
     * @param onOffStatus
     * The on_off_status
     */
    public void setOnOffStatus(Integer onOffStatus) {
        this.onOffStatus = onOffStatus;
    }

    /**
     *
     * @return
     * The deliveryType
     */
    public Integer getDeliveryType() {
        return deliveryType;
    }

    /**
     *
     * @param deliveryType
     * The delivery_type
     */
    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     *
     * @return
     * The deliveryCostFixed
     */
    public Integer getDeliveryCostFixed() {
        return deliveryCostFixed;
    }

    /**
     *
     * @param deliveryCostFixed
     * The delivery_cost_fixed
     */
    public void setDeliveryCostFixed(Integer deliveryCostFixed) {
        this.deliveryCostFixed = deliveryCostFixed;
    }

    /**
     *
     * @return
     * The deliveryCostVariation
     */
    public Integer getDeliveryCostVariation() {
        return deliveryCostVariation;
    }

    /**
     *
     * @param deliveryCostVariation
     * The delivery_cost_variation
     */
    public void setDeliveryCostVariation(Integer deliveryCostVariation) {
        this.deliveryCostVariation = deliveryCostVariation;
    }

    /**
     *
     * @return
     * The flatDeliveryCost
     */
    public Integer getFlatDeliveryCost() {
        return flatDeliveryCost;
    }

    /**
     *
     * @param flatDeliveryCost
     * The flat_delivery_cost
     */
    public void setFlatDeliveryCost(Integer flatDeliveryCost) {
        this.flatDeliveryCost = flatDeliveryCost;
    }

    /**
     *
     * @return
     * The minimumOrderAmount
     */
    public Integer getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    /**
     *
     * @param minimumOrderAmount
     * The minimum_order_amount
     */
    public void setMinimumOrderAmount(Integer minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    /**
     *
     * @return
     * The deliveryKmFixed
     */
    public Integer getDeliveryKmFixed() {
        return deliveryKmFixed;
    }

    /**
     *
     * @param deliveryKmFixed
     * The delivery_km_fixed
     */
    public void setDeliveryKmFixed(Integer deliveryKmFixed) {
        this.deliveryKmFixed = deliveryKmFixed;
    }

}