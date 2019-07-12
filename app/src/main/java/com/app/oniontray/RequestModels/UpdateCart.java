package com.app.oniontray.RequestModels;



    import com.google.gson.annotations.Expose;
    import com.google.gson.annotations.SerializedName;

    public class UpdateCart {

        @SerializedName("response")
        @Expose
        private UpdateCartResponse response;

        /**
         *
         * @return
         * The response
         */
        public UpdateCartResponse getResponse() {
            return response;
        }

        /**
         *
         * @param response
         * The response
         */
        public void setResponse(UpdateCartResponse response) {
            this.response = response;
        }

    }
