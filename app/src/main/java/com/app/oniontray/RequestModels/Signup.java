package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Signup {

    @SerializedName("response")
    @Expose
    private SignUpresponse response;

    /**
     *
     * @return
     * The response
     */
    public SignUpresponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(SignUpresponse response) {
        this.response = response;
    }

}
