package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


class AvaliableSlotMob implements Serializable{


        @SerializedName("day")
        @Expose
        private Integer day;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("end_time")
        @Expose
        private String endTime;
        @SerializedName("slot_id")
        @Expose
        private Integer slotId;
        @SerializedName("week_day")
        @Expose
        private String weekDay;
        @SerializedName("week_date")
        @Expose
        private String weekDate;
        @SerializedName("week_mob_time")
        @Expose
        private String weekMobTime;

        /**
         *
         * @return
         * The day
         */
        public Integer getDay() {
            return day;
        }

        /**
         *
         * @param day
         * The day
         */
        public void setDay(Integer day) {
            this.day = day;
        }

        /**
         *
         * @return
         * The startTime
         */
        public String getStartTime() {
            return startTime;
        }

        /**
         *
         * @param startTime
         * The start_time
         */
        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        /**
         *
         * @return
         * The endTime
         */
        public String getEndTime() {
            return endTime;
        }

        /**
         *
         * @param endTime
         * The end_time
         */
        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        /**
         *
         * @return
         * The slotId
         */
        public Integer getSlotId() {
            return slotId;
        }

        /**
         *
         * @param slotId
         * The slot_id
         */
        public void setSlotId(Integer slotId) {
            this.slotId = slotId;
        }

        /**
         *
         * @return
         * The weekDay
         */
        public String getWeekDay() {
            return weekDay;
        }

        /**
         *
         * @param weekDay
         * The week_day
         */
        public void setWeekDay(String weekDay) {
            this.weekDay = weekDay;
        }

        /**
         *
         * @return
         * The weekDate
         */
        public String getWeekDate() {
            return weekDate;
        }

        /**
         *
         * @param weekDate
         * The week_date
         */
        public void setWeekDate(String weekDate) {
            this.weekDate = weekDate;
        }

        /**
         *
         * @return
         * The weekMobTime
         */
        public String getWeekMobTime() {
            return weekMobTime;
        }

        /**
         *
         * @param weekMobTime
         * The week_mob_time
         */
        public void setWeekMobTime(String weekMobTime) {
            this.weekMobTime = weekMobTime;
        }

    }