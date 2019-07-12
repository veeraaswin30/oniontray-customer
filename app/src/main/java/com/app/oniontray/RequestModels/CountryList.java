package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryList {

    @SerializedName("response")
    @Expose
    private CountryListResponse response;

    public CountryListResponse getResponse() {
        return response;
    }

    public void setResponse(CountryListResponse response) {
        this.response = response;
    }

}