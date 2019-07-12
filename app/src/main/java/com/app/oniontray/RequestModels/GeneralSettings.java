package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneralSettings {

    @SerializedName("site_name")
    @Expose
    private String siteName;
    @SerializedName("default_language")
    @Expose
    private String defaultLanguage;
    @SerializedName("default_country")
    @Expose
    private String defaultCountry;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("copyrights")
    @Expose
    private String copyrights;
    @SerializedName("site_owner")
    @Expose
    private String siteOwner;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("fax")
    @Expose
    private String fax;
    @SerializedName("geocode")
    @Expose
    private String geocode;
    @SerializedName("site_description")
    @Expose
    private String siteDescription;
    @SerializedName("currency_side")
    @Expose
    private int currencySide;
    @SerializedName("default_currency")
    @Expose
    private int default_currency;



    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultCountry() {
        return defaultCountry;
    }

    public void setDefaultCountry(String defaultCountry) {
        this.defaultCountry = defaultCountry;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getCopyrights() {
        return copyrights;
    }

    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    public String getSiteOwner() {
        return siteOwner;
    }

    public void setSiteOwner(String siteOwner) {
        this.siteOwner = siteOwner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getGeocode() {
        return geocode;
    }

    public void setGeocode(String geocode) {
        this.geocode = geocode;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public int getCurrencySide() {
        return currencySide;
    }

    public void setCurrencySide(int currencySide) {
        this.currencySide = currencySide;
    }

    public int getDefaultCurrency() {
        return default_currency;
    }

    public void setDefaultCurrency(int default_currency) {
        this.default_currency = default_currency;
    }


    @SerializedName("cur_symbol_space")
    @Expose
    private Integer cur_symbol_space;

    public Integer getCurSymbolSpace() {
        return cur_symbol_space;
    }

    public void setCurSymbolSpace(int cur_symbol_space) {
        this.cur_symbol_space = cur_symbol_space;
    }


}
