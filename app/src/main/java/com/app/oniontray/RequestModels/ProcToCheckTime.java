package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class ProcToCheckTime implements Serializable {

    @SerializedName("week_mob_time")
    @Expose
    private String weekMobTime;
    @SerializedName("slot_id")
    @Expose
    private Integer slotId;

    /**
     *
     * @return
     *     The weekMobTime
     */
    public String getWeekMobTime() {
        return weekMobTime;
    }

    /**
     *
     * @param weekMobTime
     *     The week_mob_time
     */
    public void setWeekMobTime(String weekMobTime) {
        this.weekMobTime = weekMobTime;
    }

    /**
     *
     * @return
     *     The slotId
     */
    public Integer getSlotId() {
        return slotId;
    }

    /**
     *
     * @param slotId
     *     The slot_id
     */
    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }


}
