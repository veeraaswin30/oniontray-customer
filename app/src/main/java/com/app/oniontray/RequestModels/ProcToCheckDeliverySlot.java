package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProcToCheckDeliverySlot implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("time_interval_id")
    @Expose
    private Integer timeIntervalId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    /**
     *
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The day
     */
    public Integer getDay() {
        return day;
    }

    /**
     *
     * @param day
     *     The day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     *
     * @return
     *     The timeIntervalId
     */
    public Integer getTimeIntervalId() {
        return timeIntervalId;
    }

    /**
     *
     * @param timeIntervalId
     *     The time_interval_id
     */
    public void setTimeIntervalId(Integer timeIntervalId) {
        this.timeIntervalId = timeIntervalId;
    }

    /**
     *
     * @return
     *     The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     *     The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


}
