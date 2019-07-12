package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ProcToCheckAvaliableSlotMob  implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("week_date")
    @Expose
    private String weekDate;
    @SerializedName("time")
    @Expose
    private List<ProcToCheckTime> time = new ArrayList<>();

    /**
     *
     * @return
     *     The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     *     The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     *     The weekDate
     */
    public String getWeekDate() {
        return weekDate;
    }

    /**
     *
     * @param weekDate
     *     The week_date
     */
    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    /**
     *
     * @return
     *     The time
     */
    public List<ProcToCheckTime> getTime() {
        return time;
    }

    /**
     *
     * @param time
     *     The time
     */
    public void setTime(List<ProcToCheckTime> time) {
        this.time = time;
    }


}
