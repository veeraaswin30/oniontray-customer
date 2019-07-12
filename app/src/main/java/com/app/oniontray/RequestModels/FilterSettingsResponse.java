package com.app.oniontray.RequestModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterSettingsResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("category_list")
    @Expose
    private List<FilterCategoryList> categoryList = null;
    @SerializedName("cuisine_list")
    @Expose
    private List<CuisineList> cuisineList = null;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public List<FilterCategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<FilterCategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    public List<CuisineList> getCuisineList() {
        return cuisineList;
    }

    public void setCuisineList(List<CuisineList> cuisineList) {
        this.cuisineList = cuisineList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}