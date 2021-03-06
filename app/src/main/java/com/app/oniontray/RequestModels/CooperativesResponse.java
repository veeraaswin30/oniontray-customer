package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class CooperativesResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<CooperativeList> data = new ArrayList<>();

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
     * The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The data
     */
    public List<CooperativeList> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<CooperativeList> data) {
        this.data = data;
    }

}
