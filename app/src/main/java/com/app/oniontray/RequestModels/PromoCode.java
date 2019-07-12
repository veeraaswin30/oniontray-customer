package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PromoCode {


        @SerializedName("response")
        @Expose
        private PromoCodeResponse response;

        /**
         *
         * @return
         * The response
         */
        public PromoCodeResponse getResponse() {
            return response;
        }

        /**
         *
         * @param response
         * The response
         */
        public void setResponse(PromoCodeResponse response) {
            this.response = response;
        }

    }
