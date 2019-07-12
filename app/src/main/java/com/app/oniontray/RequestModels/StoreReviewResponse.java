package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class StoreReviewResponse {


        @SerializedName("httpCode")
        @Expose
        private Integer httpCode;
        @SerializedName("Message")
        @Expose
        private String message;
        @SerializedName("review_list")
        @Expose
        private ArrayList<ReviewList> reviewList = new ArrayList<>();

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
         * The reviewList
         */
        public ArrayList<ReviewList> getReviewList() {
            return reviewList;
        }

        /**
         *
         * @param reviewList
         * The review_list
         */
        public void setReviewList(ArrayList<ReviewList> reviewList) {
            this.reviewList = reviewList;
        }

    }