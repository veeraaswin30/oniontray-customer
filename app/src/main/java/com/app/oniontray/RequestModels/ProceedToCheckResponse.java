package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProceedToCheckResponse implements Serializable {


    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("cart_items")
    @Expose
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;
    @SerializedName("tax")
    @Expose
    private Integer tax;
    @SerializedName("grand_total_at")
    @Expose
    private Integer grand_total_at;

    @SerializedName("Address_type")
    @Expose
    private Integer Address_type;

    @SerializedName("Address_Name")
    @Expose
    private String Address_Name;
    @SerializedName("Time")
    @Expose
    private String Time;
    @SerializedName("delivery_cost")
    @Expose
    private Integer deliveryCost;

    @SerializedName("address_list")
    @Expose
    private ArrayList<AddressList> addressList = new ArrayList<>();
    @SerializedName("gateway_list")
    @Expose
    private List<GatewayList> gatewayList = new ArrayList<>();
    @SerializedName("delivery_slots")
    @Expose
    private List<DeliverySlot> deliverySlots = new ArrayList<>();
    @SerializedName("time_interval")
    @Expose
    private List<TimeInterval> timeInterval = new ArrayList<>();

    @SerializedName("weekOfdays_mob")
    @Expose
    private List<String> weekOfdaysMob = new ArrayList<>();
    @SerializedName("week_mob")
    @Expose
    private List<String> weekMob = new ArrayList<>();
    @SerializedName("outlet_detail")
    @Expose
    private OutletDetail outletDetail;
    @SerializedName("address_type")
    @Expose
    private List<AddressType> addressType = new ArrayList<>();
    @SerializedName("delivery_settings")
    @Expose
    private DeliverySettings deliverySettings;
    @SerializedName("avaliable_slot_mob")
    @Expose
    private ArrayList<AvaliableSlotMob> avaliableSlotMob = new ArrayList<>();

    private String deliveryInstruction;


    /**
     * @return The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     * @param httpCode The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return The cartItems
     */
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * @param cartItems The cart_items
     */
    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * @return The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * @return The subTotal
     */
    public Integer getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal The sub_total
     */
    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return The tax
     */
    public Integer getTax() {
        return tax;
    }

    /**
     * @param tax The tax
     */
    public void setTax(Integer tax) {
        this.tax = tax;
    }

    /**
     * @return The grand_total_at
     */
    public Integer getGrand_total_at() {
        return grand_total_at;
    }

    /**
     * @param grand_total_at The grand_total_at
     */
    public void setGrand_total_at(Integer grand_total_at) {
        this.grand_total_at = grand_total_at;


    }

    /**
     * @return The Address_type
     */
    public Integer getSelectedAddress() {
        return Address_type;
    }

    /**
     * @param Address_type The Address_type
     */
    public void setSelectedAddress(Integer Address_type) {
        this.Address_type = Address_type;


    }

    /**
     * @return The Address_Name
     */
    public String getSelectedAddressName() {
        return Address_Name;
    }

    /**
     * @param Address_Name The Address_Name
     */
    public void setSelectedAddressName(String Address_Name) {
        this.Address_Name = Address_Name;


    }


    /**
     * @return The Time
     */
    public String getSelectedTime() {
        return Time;
    }

    /**
     * @param Time The Time
     */
    public void setSelectedTime(String Time) {
        this.Time = Time;


    }

    /**
     * @return The deliveryCost
     */
    public Integer getDeliveryCost() {
        return deliveryCost;
    }

    /**
     * @param deliveryCost The delivery_cost
     */
    public void setDeliveryCost(Integer deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    /**
     * @return The addressList
     */
    public ArrayList<AddressList> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList The address_list
     */
    public void setAddressList(ArrayList<AddressList> addressList) {
        this.addressList = addressList;
    }

    /**
     * @return The gatewayList
     */
    public List<GatewayList> getGatewayList() {
        return gatewayList;
    }

    /**
     * @param gatewayList The gateway_list
     */
    public void setGatewayList(List<GatewayList> gatewayList) {
        this.gatewayList = gatewayList;
    }

    /**
     * @return The deliverySlots
     */
    public List<DeliverySlot> getDeliverySlots() {
        return deliverySlots;
    }

    /**
     * @param deliverySlots The delivery_slots
     */
    public void setDeliverySlots(List<DeliverySlot> deliverySlots) {
        this.deliverySlots = deliverySlots;
    }

    /**
     * @return The timeInterval
     */
    public List<TimeInterval> getTimeInterval() {
        return timeInterval;
    }

    /**
     * @param timeInterval The time_interval
     */
    public void setTimeInterval(List<TimeInterval> timeInterval) {
        this.timeInterval = timeInterval;
    }

//    /**
//     *
//     * @return
//     * The deliverySlotArray
//     */
//    public DeliverySlotArray getDeliverySlotArray() {
//        return deliverySlotArray;
//    }
//
//    /**
//     *
//     * @param deliverySlotArray
//     * The delivery_slot_array
//     */
//    public void setDeliverySlotArray(DeliverySlotArray deliverySlotArray) {
//        this.deliverySlotArray = deliverySlotArray;
//    }

//    /**
//     *
//     * @return
//     * The weekOfdays
//     */
//    public WeekOfdays getWeekOfdays() {
//        return weekOfdays;
//    }
//
//    /**
//     *
//     * @param weekOfdays
//     * The weekOfdays
//     */
//    public void setWeekOfdays(WeekOfdays weekOfdays) {
//        this.weekOfdays = weekOfdays;
//    }

    /**
     * @return The weekOfdaysMob
     */
    public List<String> getWeekOfdaysMob() {
        return weekOfdaysMob;
    }

    /**
     * @param weekOfdaysMob The weekOfdays_mob
     */
    public void setWeekOfdaysMob(List<String> weekOfdaysMob) {
        this.weekOfdaysMob = weekOfdaysMob;
    }

    /**
     * @return The weekMob
     */
    public List<String> getWeekMob() {
        return weekMob;
    }

    /**
     * @param weekMob The week_mob
     */
    public void setWeekMob(List<String> weekMob) {
        this.weekMob = weekMob;
    }

//    /**
//     *
//     * @return
//     * The week
//     */
//    public Week getWeek() {
//        return week;
//    }
//
//    /**
//     *
//     * @param week
//     * The week
//     */
//    public void setWeek(Week week) {
//        this.week = week;
//    }

    /**
     * @return The outletDetail
     */
    public OutletDetail getOutletDetail() {
        return outletDetail;
    }

    /**
     * @param outletDetail The outlet_detail
     */
    public void setOutletDetail(OutletDetail outletDetail) {
        this.outletDetail = outletDetail;
    }

    /**
     * @return The addressType
     */
    public List<AddressType> getAddressType() {
        return addressType;
    }

    /**
     * @param addressType The address_type
     */
    public void setAddressType(List<AddressType> addressType) {
        this.addressType = addressType;
    }

    /**
     * @return The deliverySettings
     */
    public DeliverySettings getDeliverySettings() {
        return deliverySettings;
    }

    /**
     * @param deliverySettings The delivery_settings
     */
    public void setDeliverySettings(DeliverySettings deliverySettings) {
        this.deliverySettings = deliverySettings;
    }

    /**
     * @return The avaliableSlotMob
     */
    public ArrayList<AvaliableSlotMob> getAvaliableSlotMob() {
        return avaliableSlotMob;
    }

    /**
     * @param avaliableSlotMob The avaliable_slot_mob
     */
    public void setAvaliableSlotMob(ArrayList<AvaliableSlotMob> avaliableSlotMob) {
        this.avaliableSlotMob = avaliableSlotMob;
    }


}
