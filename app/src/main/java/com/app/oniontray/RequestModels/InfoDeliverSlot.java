
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoDeliverSlot implements Serializable{

        @SerializedName("Thursday")
        @Expose
        private String thursday;
        @SerializedName("Friday")
        @Expose
        private String friday;
        @SerializedName("Saturday")
        @Expose
        private String saturday;
        @SerializedName("Sunday")
        @Expose
        private String sunday;
        @SerializedName("Monday")
        @Expose
        private String monday;
        @SerializedName("Tuesday")
        @Expose
        private String tuesday;
        @SerializedName("Wednesday")
        @Expose
        private String wednesday;

        /**
         * @return The thursday
         */
        public String getThursday() {
            return thursday;
        }

        /**
         * @param thursday The Thursday
         */
        public void setThursday(String thursday) {
            this.thursday = thursday;
        }

        /**
         * @return The friday
         */
        public String getFriday() {
            return friday;
        }

        /**
         * @param friday The Friday
         */
        public void setFriday(String friday) {
            this.friday = friday;
        }

        /**
         * @return The saturday
         */
        public String getSaturday() {
            return saturday;
        }

        /**
         * @param saturday The Saturday
         */
        public void setSaturday(String saturday) {
            this.saturday = saturday;
        }

        /**
         * @return The sunday
         */
        public String getSunday() {
            return sunday;
        }

        /**
         * @param sunday The Sunday
         */
        public void setSunday(String sunday) {
            this.sunday = sunday;
        }

        /**
         * @return The monday
         */
        public String getMonday() {
            return monday;
        }

        /**
         * @param monday The Monday
         */
        public void setMonday(String monday) {
            this.monday = monday;
        }

        /**
         * @return The tuesday
         */
        public String getTuesday() {
            return tuesday;
        }

        /**
         * @param tuesday The Tuesday
         */
        public void setTuesday(String tuesday) {
            this.tuesday = tuesday;
        }

        /**
         * @return The wednesday
         */
        public String getWednesday() {
            return wednesday;
        }

        /**
         * @param wednesday The Wednesday
         */
        public void setWednesday(String wednesday) {
            this.wednesday = wednesday;
        }

    }