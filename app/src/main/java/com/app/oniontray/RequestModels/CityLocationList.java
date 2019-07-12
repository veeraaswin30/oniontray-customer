package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nextbrain on 2/8/2017.
 */

public class CityLocationList {
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("location_list")
    @Expose
    private List<LocationList> locationList = null;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<LocationList> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<LocationList> locationList) {
        this.locationList = locationList;
    }
}
