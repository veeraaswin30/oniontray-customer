package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeliveryDetail {

    @SerializedName("delivery_instructions")
    @Expose
    private String deliveryInstructions;
    @SerializedName("user_contact_address")
    @Expose
    private String userContactAddress;
    @SerializedName("payment_gateway_id")
    @Expose
    private Integer paymentGatewayId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("service_tax")
    @Expose
    private String serviceTax;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("order_type")
    @Expose
    private Integer orderType;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("coupon_amount")
    @Expose
    private String couponAmount;
    @SerializedName("sub_total")
    @Expose
    private String sub_total;

    /**
     *
     * @return
     * The deliveryInstructions
     */
    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    /**
     *
     * @param deliveryInstructions
     * The delivery_instructions
     */
    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }

    /**
     *
     * @return
     * The userContactAddress
     */
    public String getUserContactAddress() {
        return userContactAddress;
    }

    /**
     *
     * @param userContactAddress
     * The user_contact_address
     */
    public void setUserContactAddress(String userContactAddress) {
        this.userContactAddress = userContactAddress;
    }

    /**
     *
     * @return
     * The paymentGatewayId
     */
    public Integer getPaymentGatewayId() {
        return paymentGatewayId;
    }

    /**
     *
     * @param paymentGatewayId
     * The payment_gateway_id
     */
    public void setPaymentGatewayId(Integer paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The totalAmount
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     *
     * @param totalAmount
     * The total_amount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     *
     * @return
     * The deliveryCharge
     */
    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    /**
     *
     * @param deliveryCharge
     * The delivery_charge
     */
    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    /**
     *
     * @return
     * The serviceTax
     */
    public String getServiceTax() {
        return serviceTax;
    }

    /**
     *
     * @param serviceTax
     * The service_tax
     */
    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    /**
     *
     * @return
     * The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     *
     * @param startTime
     * The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     *
     * @return
     * The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     *
     * @param endTime
     * The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     *
     * @param deliveryDate
     * The delivery_date
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     *
     * @return
     * The orderType
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     *
     * @param orderType
     * The order_type
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
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
     * The couponAmount
     */
    public String getCouponAmount() {
        return couponAmount;
    }

    /**
     *
     * @param couponAmount
     * The coupon_amount
     */
    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }

    /**
     *
     * @return
     * The sub_total
     */
    public String getSubTotal() {
        return sub_total;
    }

    /**
     *
     * @param sub_total
     * The sub_total
     */
    public void setSubTotal(String sub_total) {
        this.sub_total = sub_total;
    }


}