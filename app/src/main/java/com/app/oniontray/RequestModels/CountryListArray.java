package com.app.oniontray.RequestModels;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountryListArray {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("url_index")
    @Expose
    private String urlIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getUrlIndex() {
        return urlIndex;
    }

    public void setUrlIndex(String urlIndex) {
        this.urlIndex = urlIndex;
    }


}
