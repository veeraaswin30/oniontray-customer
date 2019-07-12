
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class  OpenTime implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("day_week")
    @Expose
    private Integer dayWeek;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return The vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId The vendor_id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return The day
     */
    public String getDay() {
        return day;
    }

    /**
     * @param day The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * @return The dayWeek
     */
    public Integer getDayWeek() {
        return dayWeek;
    }

    /**
     * @param dayWeek The day_week
     */
    public void setDayWeek(Integer dayWeek) {
        this.dayWeek = dayWeek;
    }

    /**
     * @return The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
