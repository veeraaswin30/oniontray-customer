package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LocOutLetResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("address")
    @Expose
    private List<LocOutLetAddress> address = null;
    @SerializedName("city_id")
    @Expose
    private int city_id;
    @SerializedName("location_id")
    @Expose
    private int location_id;
    @SerializedName("city_name")
    @Expose
    private String city_name;
    @SerializedName("location_name")
    @Expose
    private String location_name;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<LocOutLetAddress> getLocOutLetAddress() {
        return address;
    }

    public void setLocOutLetAddress(List<LocOutLetAddress> address) {
        this.address = address;
    }

    public int getCityId() {
        return city_id;
    }

    public void setCityId(int city_id) {
        this.city_id = city_id;
    }

    public int getLocationId() {
        return location_id;
    }

    public void setLocationId(int location_id) {
        this.location_id = location_id;
    }

    public String getCityName() {
        return city_name;
    }

    public void setCityName(String city_name) {
        this.city_name = city_name;
    }

    public String getLocationName() {
        return location_name;
    }

    public void setLocationName(String location_name) {
        this.location_name = location_name;
    }


}
