package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ForgotPassword {

    @SerializedName("response")
    @Expose
    private ForgotPasswordResponse response;

    /**
     *
     * @return
     * The response
     */
    public ForgotPasswordResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(ForgotPasswordResponse response) {
        this.response = response;
    }

}