package com.app.oniontray.RequestModels;



import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("order_items")
    @Expose
    private ArrayList<OrderItem> orderItems = new ArrayList<>();

    @SerializedName("vendor_info")
    @Expose
    private List<VendorInfo> vendorInfo = new ArrayList<>();

    @SerializedName("mob_return_reasons")
    @Expose
    private List<MobReturnReason> mobReturnReasons = new ArrayList<>();
    @SerializedName("reviews")
    @Expose
    private Reviews reviews;

    @SerializedName("reorder_option")
    @Expose
    private Integer reorderOption;
    @SerializedName("current_time")
    @Expose
    private String currentTime;



    /**
     *
     * @return
     * The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The orderItems
     */
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     *
     * @param orderItems
     * The order_items
     */
    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

//    /**
//     *
//     * @return
//     * The deliveryDetails
//     */
//    public List<DeliveryDetail> getDeliveryDetails() {
//        return deliveryDetails;
//    }
//
//    /**
//     *
//     * @param deliveryDetails
//     * The delivery_details
//     */
//    public void setDeliveryDetails(List<DeliveryDetail> deliveryDetails) {
//        this.deliveryDetails = deliveryDetails;
//    }

    /**
     *
     * @return
     * The vendorInfo
     */
    public List<VendorInfo> getVendorInfo() {
        return vendorInfo;
    }

    /**
     *
     * @param vendorInfo
     * The vendor_info
     */
    public void setVendorInfo(List<VendorInfo> vendorInfo) {
        this.vendorInfo = vendorInfo;
    }

//    /**
//     *
//     * @return
//     * The returnReasons
//     */
//    public ReturnReasons getReturnReasons() {
//        return returnReasons;
//    }
//
//    /**
//     *
//     * @param returnReasons
//     * The return_reasons
//     */
//    public void setReturnReasons(ReturnReasons returnReasons) {
//        this.returnReasons = returnReasons;
//    }
//
//    /**
//     *
//     * @return
//     * The trackingResult
//     */
//    public TrackingResult getTrackingResult() {
//        return trackingResult;
//    }
//
//    /**
//     *
//     * @param trackingResult
//     * The tracking_result
//     */
//    public void setTrackingResult(TrackingResult trackingResult) {
//        this.trackingResult = trackingResult;
//    }

//    /**
//     *
//     * @return
//     * The lastState
//     */
//    public Integer getLastState() {
//        return lastState;
//    }
//
//    /**
//     *
//     * @param lastState
//     * The last_state
//     */
//    public void setLastState(Integer lastState) {
//        this.lastState = lastState;
//    }

//    /**
//     *
//     * @return
//     * The mobTrackingResult
//     */
//    public List<MobTrackingResult> getMobTrackingResult() {
//        return mobTrackingResult;
//    }
//
//    /**
//     *
//     * @param mobTrackingResult
//     * The mob_tracking_result
//     */
//    public void setMobTrackingResult(List<MobTrackingResult> mobTrackingResult) {
//        this.mobTrackingResult = mobTrackingResult;
//    }

//    /**
//     *
//     * @return
//     * The mobReturnReasons
//     */
//    public List<MobReturnReason> getMobReturnReasons() {
//        return mobReturnReasons;
//    }
//
//    /**
//     *
//     * @param mobReturnReasons
//     * The mob_return_reasons
//     */
//    public void setMobReturnReasons(List<MobReturnReason> mobReturnReasons) {
//        this.mobReturnReasons = mobReturnReasons;
//    }

    public Integer getReorderOption() {
        return reorderOption;
    }

    public void setReorderOption(Integer reorderOption) {
        this.reorderOption = reorderOption;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }


    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }




}
