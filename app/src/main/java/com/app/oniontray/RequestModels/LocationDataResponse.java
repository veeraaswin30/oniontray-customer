package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LocationDataResponse {
    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("city_location_list")
    @Expose
    private List<CityLocationList> cityLocationList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public List<CityLocationList> getCityLocationList() {
        return cityLocationList;
    }

    public void setCityLocationList(List<CityLocationList> cityLocationList) {
        this.cityLocationList = cityLocationList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
