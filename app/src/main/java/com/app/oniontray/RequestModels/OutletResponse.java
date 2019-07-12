
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class OutletResponse {

        @SerializedName("httpCode")
        @Expose
        private Integer httpCode;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("outlet_list")
        @Expose
        private ArrayList<OutletList> outletList = new ArrayList<>();
        @SerializedName("vendor_name")
        @Expose
        private String vendorName;

        /**
         *
         * @return
         * The httpCode
         */
        public Integer getHttpCode() {
            return httpCode;
        }

        /**
         *
         * @param httpCode
         * The httpCode
         */
        public void setHttpCode(Integer httpCode) {
            this.httpCode = httpCode;
        }

        /**
         *
         * @return
         * The message
         */
        public String getMessage() {
            return message;
        }

        /**
         *
         * @param message
         * The Message
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         *
         * @return
         * The outletList
         */
        public ArrayList<OutletList> getOutletList() {
            return outletList;
        }

        /**
         *
         * @param outletList
         * The outlet_list
         */
        public void setOutletList(ArrayList<OutletList> outletList) {
            this.outletList = outletList;
        }

        /**
         *
         * @return
         * The vendorName
         */
        public String getVendorName() {
            return vendorName;
        }

        /**
         *
         * @param vendorName
         * The vendor_name
         */
        public void setVendorName(String vendorName) {
            this.vendorName = vendorName;
        }

    }