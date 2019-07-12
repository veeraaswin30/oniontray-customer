
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendFeedBackResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
