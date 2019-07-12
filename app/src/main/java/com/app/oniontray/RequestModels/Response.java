package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Response {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("languages")
    @Expose
    private List<Datum> data = new ArrayList<>();
    @SerializedName("modules_list")
    @Expose
    private SettOffMod settOffMod;
    @SerializedName("general_settings")
    @Expose
    private GeneralSettings general_settings;
    @SerializedName("theme_settings")
    @Expose
    private ThemeList themeList;
    @SerializedName("social_settings")
    @Expose
    private SocialSettings social_settings;
    @SerializedName("currency_list")
    @Expose
    private List<Currency> currencyList = null;
    @SerializedName("delivery_settings")
    @Expose
    private LangDeliverySettings delivery_settings;

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
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The settOffMod
     */
    public SettOffMod getSettOffMod() {
        return settOffMod;
    }

    /**
     *
     * @param settOffMod
     * The settOffMod
     */
    public void setSettOffMod(SettOffMod settOffMod) {
        this.settOffMod = settOffMod;
    }

    /**
     *
     * @return
     * The general_settings
     */
    public GeneralSettings getGeneralSettings() {
        return general_settings;
    }

    /**
     *
     * @param general_settings
     * The general_settings
     */
    public void setGeneralSettings(GeneralSettings general_settings) {
        this.general_settings = general_settings;
    }

    /**
     *
     * @return
     * The social_settings
     */
    public SocialSettings getSocialSettings() {
        return social_settings;
    }

    /**
     *
     * @param social_settings
     * The social_settings
     */
    public void setSocialSettings(SocialSettings social_settings) {
        this.social_settings = social_settings;
    }

    /**
     *
     * @return
     * The currencyList
     */
    public List<Currency> getCurrencyList() {
        return currencyList;
    }

    /**
     *
     * @param currencyList
     * The currencyList
     */
    public void setCurrencyList(List<Currency> currencyList) {
        this.currencyList = currencyList;
    }

    /**
     *
     * @return
     * The delivery_settings
     */
    public LangDeliverySettings getLangDeliverySettings() {
        return delivery_settings;
    }

    /**
     *
     * @param delivery_settings
     * The delivery_settings
     */
    public void setLangDeliverySettings(LangDeliverySettings delivery_settings) {
        this.delivery_settings = delivery_settings;
    }

    public ThemeList getThemeList() {
        return themeList;
    }

    public void setThemeList(ThemeList themeList) {
        this.themeList = themeList;
    }


}