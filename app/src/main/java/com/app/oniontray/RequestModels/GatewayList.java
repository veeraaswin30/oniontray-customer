package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GatewayList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("currency_id")
    @Expose
    private Object currencyId;
    @SerializedName("merchant_key")
    @Expose
    private String merchantKey;
    @SerializedName("merchant_secret_key")
    @Expose
    private String merchantSecretKey;
    @SerializedName("merchant_password")
    @Expose
    private String merchantPassword;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("commision")
    @Expose
    private Integer commision;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("account_id")
    @Expose
    private String accountId;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("payment_id")
    @Expose
    private Integer paymentId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("language_id")
    @Expose
    private Integer languageId;
    @SerializedName("info_id")
    @Expose
    private Integer infoId;
    @SerializedName("payment_gateway_id")
    @Expose
    private Integer paymentGatewayId;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The activeStatus
     */
    public String getActiveStatus() {
        return activeStatus;
    }

    /**
     *
     * @param activeStatus
     * The active_status
     */
    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     *
     * @return
     * The currencyId
     */
    public Object getCurrencyId() {
        return currencyId;
    }

    /**
     *
     * @param currencyId
     * The currency_id
     */
    public void setCurrencyId(Object currencyId) {
        this.currencyId = currencyId;
    }

    /**
     *
     * @return
     * The merchantKey
     */
    public String getMerchantKey() {
        return merchantKey;
    }

    /**
     *
     * @param merchantKey
     * The merchant_key
     */
    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    /**
     *
     * @return
     * The merchantSecretKey
     */
    public String getMerchantSecretKey() {
        return merchantSecretKey;
    }

    /**
     *
     * @param merchantSecretKey
     * The merchant_secret_key
     */
    public void setMerchantSecretKey(String merchantSecretKey) {
        this.merchantSecretKey = merchantSecretKey;
    }

    /**
     *
     * @return
     * The merchantPassword
     */
    public String getMerchantPassword() {
        return merchantPassword;
    }

    /**
     *
     * @param merchantPassword
     * The merchant_password
     */
    public void setMerchantPassword(String merchantPassword) {
        this.merchantPassword = merchantPassword;
    }

    /**
     *
     * @return
     * The paymentMode
     */
    public String getPaymentMode() {
        return paymentMode;
    }

    /**
     *
     * @param paymentMode
     * The payment_mode
     */
    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     *
     * @return
     * The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * @param createdDate
     * The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return
     * The modifiedDate
     */
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     *
     * @param modifiedDate
     * The modified_date
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     *
     * @return
     * The commision
     */
    public Integer getCommision() {
        return commision;
    }

    /**
     *
     * @param commision
     * The commision
     */
    public void setCommision(Integer commision) {
        this.commision = commision;
    }

    /**
     *
     * @return
     * The image
     */
    public Object getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(Object image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The accountId
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     *
     * @param accountId
     * The account_id
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     *
     * @return
     * The paymentType
     */
    public Integer getPaymentType() {
        return paymentType;
    }

    /**
     *
     * @param paymentType
     * The payment_type
     */
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    /**
     *
     * @return
     * The paymentId
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     *
     * @param paymentId
     * The payment_id
     */
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The languageId
     */
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     *
     * @param languageId
     * The language_id
     */
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    /**
     *
     * @return
     * The infoId
     */
    public Integer getInfoId() {
        return infoId;
    }

    /**
     *
     * @param infoId
     * The info_id
     */
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    /**
     *
     * @return
     * The paymentGatewayId
     */
    public Integer getPaymentGatewayId() {
        return paymentGatewayId;
    }

    /**
     *
     * @param paymentGatewayId
     * The payment_gateway_id
     */
    public void setPaymentGatewayId(Integer paymentGatewayId) {
        this.paymentGatewayId = paymentGatewayId;
    }

}
