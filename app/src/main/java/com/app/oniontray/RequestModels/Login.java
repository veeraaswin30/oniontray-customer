package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Login {

    @SerializedName("response")
    @Expose
    private LoginResponse response;

    /**
     *
     * @return
     * The response
     */
    public LoginResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(LoginResponse response) {
        this.response = response;
    }

}