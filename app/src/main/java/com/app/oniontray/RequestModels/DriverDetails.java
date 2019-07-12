
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverDetails {

    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("driver_latitude")
    @Expose
    private String driverLatitude;
    @SerializedName("driver_longitude")
    @Expose
    private String driverLongitude;
    @SerializedName("driver_first_name")
    @Expose
    private String driverFirstName;
    @SerializedName("driver_last_name")
    @Expose
    private String driverLastName;
    @SerializedName("driver_mobile_number")
    @Expose
    private String driverMobileNumber;
    @SerializedName("user_latitude")
    @Expose
    private String userLatitude;
    @SerializedName("user_longtitude")
    @Expose
    private String userLongtitude;

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverLatitude() {
        return driverLatitude;
    }

    public void setDriverLatitude(String driverLatitude) {
        this.driverLatitude = driverLatitude;
    }

    public String getDriverLongitude() {
        return driverLongitude;
    }

    public void setDriverLongitude(String driverLongitude) {
        this.driverLongitude = driverLongitude;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverMobileNumber() {
        return driverMobileNumber;
    }

    public void setDriverMobileNumber(String driverMobileNumber) {
        this.driverMobileNumber = driverMobileNumber;
    }

    public String getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(String userLatitude) {
        this.userLatitude = userLatitude;
    }

    public String getUserLongtitude() {
        return userLongtitude;
    }

    public void setUserLongtitude(String userLongtitude) {
        this.userLongtitude = userLongtitude;
    }




    @SerializedName("driver_profile_image")
    @Expose
    private String driver_profile_image;

    @SerializedName("total_amount")
    @Expose
    private String total_amount;

    @SerializedName("delivery_instructions")
    @Expose
    private String delivery_instructions;

    @SerializedName("delivery_date")
    @Expose
    private String delivery_date;



    public String getDriver_profile_image() {
        return driver_profile_image;
    }

    public void setDriver_profile_image(String driver_profile_image) {
        this.driver_profile_image = driver_profile_image;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDelivery_instructions() {
        return delivery_instructions;
    }

    public void setDelivery_instructions(String delivery_instructions) {
        this.delivery_instructions = delivery_instructions;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }




    @SerializedName("start_time")
    @Expose
    private String start_time;

    @SerializedName("end_time")
    @Expose
    private String end_time;

    @SerializedName("delivery_time")
    @Expose
    private String delivery_time;

    @SerializedName("outlet_delivery_minites")
    @Expose
    private String outlet_delivery_minites;

    @SerializedName("order_items")
    @Expose
    private Integer order_items;

    @SerializedName("order_id")
    @Expose
    private Integer order_id;




    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getOutlet_delivery_minites() {
        return outlet_delivery_minites;
    }

    public void setOutlet_delivery_minites(String outlet_delivery_minites) {
        this.outlet_delivery_minites = outlet_delivery_minites;
    }

    public Integer getOrder_items() {
        return order_items;
    }

    public void setOrder_items(Integer order_items) {
        this.order_items = order_items;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }


}
