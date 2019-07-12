package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class ProcToCheckResp implements Serializable {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("cart_items")
    @Expose
    private List<ProcToCheckCartItem> proToCheckCartItems = new ArrayList<>();
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("delivery_cost")
    @Expose
    private String deliveryCost;
    @SerializedName("address_list")
    @Expose
    private List<ProToCheckAddressList> addressList = new ArrayList<>();
    @SerializedName("gateway_list")
    @Expose
    private List<ProcToCheckGatewayList> gatewayList = new ArrayList<>();
    @SerializedName("delivery_slots")
    @Expose
    private List<ProcToCheckDeliverySlot> deliverySlots = new ArrayList<>();
    @SerializedName("time_interval")
    @Expose
    private List<ProcToCheckTimeInterval> timeInterval = new ArrayList<>();

//    @SerializedName("delivery_slot_array")
//    @Expose
//    private DeliverySlotArray deliverySlotArray;

//    @SerializedName("weekOfdays")
//    @Expose
//    private WeekOfdays weekOfdays;

//    @SerializedName("weekOfdays_mob")
//    @Expose
//    private List<String> weekOfdaysMob = new ArrayList<String>();

//    @SerializedName("week_mob")
//    @Expose
//    private List<String> weekMob = new ArrayList<String>();

//    @SerializedName("week")
//    @Expose
//    private Week week;

    @SerializedName("outlet_detail")
    @Expose
    private PorcToCheckOutletDetail outletDetail;
    @SerializedName("address_type")
    @Expose
    private List<ProcToCheckAddressType> addressType = new ArrayList<>();
    @SerializedName("delivery_settings")
    @Expose
    private ProcToCheckDeliverySettings deliverySettings;
    @SerializedName("avaliable_slot_mob")
    @Expose
    private List<ProcToCheckAvaliableSlotMob> avaliableSlotMob = new ArrayList<>();

    /**
     *
     * @return
     *     The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     *     The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     *     The proToCheckCartItems
     */
    public List<ProcToCheckCartItem> getCartItems() {
        return proToCheckCartItems;
    }

    /**
     *
     * @param cartItems
     *     The proToCheckCartItems
     */
    public void setCartItems(List<ProcToCheckCartItem> cartItems) {
        this.proToCheckCartItems = cartItems;
    }

    /**
     *
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     *     The subTotal
     */
    public String getSubTotal() {
        return subTotal;
    }

    /**
     *
     * @param subTotal
     *     The sub_total
     */
    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    /**
     *
     * @return
     *     The tax
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     *     The tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     *     The deliveryCost
     */
    public String getDeliveryCost() {
        return deliveryCost;
    }

    /**
     *
     * @param deliveryCost
     *     The delivery_cost
     */
    public void setDeliveryCost(String deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    /**
     *
     * @return
     *     The addressList
     */
    public List<ProToCheckAddressList> getAddressList() {
        return addressList;
    }

    /**
     *
     * @param addressList
     *     The address_list
     */
    public void setAddressList(List<ProToCheckAddressList> addressList) {
        this.addressList = addressList;
    }

    /**
     *
     * @return
     *     The gatewayList
     */
    public List<ProcToCheckGatewayList> getGatewayList() {
        return gatewayList;
    }

    /**
     *
     * @param gatewayList
     *     The gateway_list
     */
    public void setGatewayList(List<ProcToCheckGatewayList> gatewayList) {
        this.gatewayList = gatewayList;
    }

    /**
     *
     * @return
     *     The deliverySlots
     */
    public List<ProcToCheckDeliverySlot> getDeliverySlots() {
        return deliverySlots;
    }

    /**
     *
     * @param deliverySlots
     *     The delivery_slots
     */
    public void setDeliverySlots(List<ProcToCheckDeliverySlot> deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    /**
     *
     * @return
     *     The timeInterval
     */
    public List<ProcToCheckTimeInterval> getTimeInterval() {
        return timeInterval;
    }

    /**
     *
     * @param timeInterval
     *     The time_interval
     */
    public void setTimeInterval(List<ProcToCheckTimeInterval> timeInterval) {
        this.timeInterval = timeInterval;
    }

//    /**
//     *
//     * @return
//     *     The deliverySlotArray
//     */
//    public DeliverySlotArray getDeliverySlotArray() {
//        return deliverySlotArray;
//    }
//
//    /**
//     *
//     * @param deliverySlotArray
//     *     The delivery_slot_array
//     */
//    public void setDeliverySlotArray(DeliverySlotArray deliverySlotArray) {
//        this.deliverySlotArray = deliverySlotArray;
//    }

//    /**
//     *
//     * @return
//     *     The weekOfdays
//     */
//    public WeekOfdays getWeekOfdays() {
//        return weekOfdays;
//    }
//
//    /**
//     *
//     * @param weekOfdays
//     *     The weekOfdays
//     */
//    public void setWeekOfdays(WeekOfdays weekOfdays) {
//        this.weekOfdays = weekOfdays;
//    }

//    /**
//     *
//     * @return
//     *     The weekOfdaysMob
//     */
//    public List<String> getWeekOfdaysMob() {
//        return weekOfdaysMob;
//    }
//
//    /**
//     *
//     * @param weekOfdaysMob
//     *     The weekOfdays_mob
//     */
//    public void setWeekOfdaysMob(List<String> weekOfdaysMob) {
//        this.weekOfdaysMob = weekOfdaysMob;
//    }

//    /**
//     *
//     * @return
//     *     The weekMob
//     */
//    public List<String> getWeekMob() {
//        return weekMob;
//    }
//
//    /**
//     *
//     * @param weekMob
//     *     The week_mob
//     */
//    public void setWeekMob(List<String> weekMob) {
//        this.weekMob = weekMob;
//    }

//    /**
//     *
//     * @return
//     *     The week
//     */
//    public Week getWeek() {
//        return week;
//    }
//
//    /**
//     *
//     * @param week
//     *     The week
//     */
//    public void setWeek(Week week) {
//        this.week = week;
//    }

    /**
     *
     * @return
     *     The outletDetail
     */
    public PorcToCheckOutletDetail getOutletDetail() {
        return outletDetail;
    }

    /**
     *
     * @param outletDetail
     *     The outlet_detail
     */
    public void setOutletDetail(PorcToCheckOutletDetail outletDetail) {
        this.outletDetail = outletDetail;
    }

    /**
     *
     * @return
     *     The addressType
     */
    public List<ProcToCheckAddressType> getAddressType() {
        return addressType;
    }

    /**
     *
     * @param addressType
     *     The address_type
     */
    public void setAddressType(List<ProcToCheckAddressType> addressType) {
        this.addressType = addressType;
    }

    /**
     *
     * @return
     *     The deliverySettings
     */
    public ProcToCheckDeliverySettings getDeliverySettings() {
        return deliverySettings;
    }

    /**
     *
     * @param deliverySettings
     *     The delivery_settings
     */
    public void setDeliverySettings(ProcToCheckDeliverySettings deliverySettings) {
        this.deliverySettings = deliverySettings;
    }

    /**
     *
     * @return
     *     The avaliableSlotMob
     */
    public List<ProcToCheckAvaliableSlotMob> getAvaliableSlotMob() {
        return avaliableSlotMob;
    }

    /**
     *
     * @param avaliableSlotMob
     *     The avaliable_slot_mob
     */
    public void setAvaliableSlotMob(List<ProcToCheckAvaliableSlotMob> avaliableSlotMob) {
        this.avaliableSlotMob = avaliableSlotMob;
    }


    // User Selected delivery values

    private String delivery_Addr_id = "";
    private String delivery_date_time = "";
    private String deliver_instruction = "";

    private int deliver_type;

    private int delivery_promocode = 0;
    private String delivery_coupon_id = "";

    private String delivery_slot_id = "";
    private String delivery_time = "";

    private String coupon_id = "";
    private String coupon_type = "";
    private String coupon_amount = "";


    public void setDelivery_Addr_id(String delivery_Addr_id) {
        this.delivery_Addr_id = delivery_Addr_id;
    }

    public String getDelivery_Addr_id() {
        return delivery_Addr_id;
    }

    public String getDelivery_date_time() {
        return delivery_date_time;
    }

    public void setDelivery_date_time(String delivery_date_time) {
        this.delivery_date_time = delivery_date_time;
    }

    public void setDeliveryInstruction(String deliver_instruction) {
        this.deliver_instruction = deliver_instruction;
    }

    public String getDelivery_Instruction() {
        return deliver_instruction;
    }

    public void setDeliveryType(int deliver_type) {
        this.deliver_type = deliver_type;
    }

    public int getDeliveryType() {
        return deliver_type;
    }

    public void setDeliveryPromoCode(int deliver_type) {
        this.delivery_promocode = deliver_type;
    }

    public int getDeliveryPromoCode() {
        return delivery_promocode;
    }

    public void setDeliveryCouponId(String delivery_coupon_id) {
        this.delivery_coupon_id = delivery_coupon_id;
    }

    public String getDeliveryCouponId() {
        return delivery_coupon_id;
    }

    public void setDeliverySlotId(String delivery_slot_id) {
        this.delivery_slot_id = delivery_slot_id;
    }

    public String getDeliverySlotId() {
        return delivery_slot_id;
    }

    public void setDeliveryTime(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getDeliveryTime() {
        return delivery_time;
    }



    public void setCouponId(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getCouponId() {
        return coupon_id;
    }

    public void setCouponType(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCouponType() {
        return coupon_type;
    }

    public void setCouponAmount(String coupon_amount) {
        this.coupon_amount = coupon_amount;
    }

    public String getCouponAmount() {
        return coupon_amount;
    }



}
