
package com.app.oniontray.RequestModels;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UserProfResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("user_data")
    @Expose
    private List<UserProfDatas> userData = new ArrayList<>();

    /**
     * 
     * @return
     *     The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     * 
     * @param httpCode
     *     The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The userData
     */
    public List<UserProfDatas> getUserData() {
        return userData;
    }

    /**
     * 
     * @param userData
     *     The user_data
     */
    public void setUserData(List<UserProfDatas> userData) {
        this.userData = userData;
    }

}
