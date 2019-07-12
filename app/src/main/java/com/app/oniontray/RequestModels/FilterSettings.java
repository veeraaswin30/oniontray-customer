package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterSettings {

    @SerializedName("response")
    @Expose
    private FilterSettingsResponse response;

    public FilterSettingsResponse getResponse() {
        return response;
    }

    public void setResponse(FilterSettingsResponse response) {
        this.response = response;
    }

}