package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Currency {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("currency_name")
    @Expose
    private String currencyName;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("currency_code")
    @Expose
    private String currency_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencyCode() {
        return currency_code;
    }

    public void setCurrencyCode(String currencySymbol) {
        this.currency_code = currencySymbol;
    }


}
