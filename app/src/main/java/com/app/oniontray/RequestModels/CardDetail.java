package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardDetail {

    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city_id")
    @Expose
    private Object cityId;
    @SerializedName("country_id")
    @Expose
    private Object countryId;
    @SerializedName("postal_code")
    @Expose
    private Object postalCode;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("address_type")
    @Expose
    private int addressType;

    /**
     *
     * @return
     * The name
     */
    public Object getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(Object name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     * The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     * The cityId
     */
    public Object getCityId() {
        return cityId;
    }

    /**
     *
     * @param cityId
     * The city_id
     */
    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    /**
     *
     * @return
     * The countryId
     */
    public Object getCountryId() {
        return countryId;
    }

    /**
     *
     * @param countryId
     * The country_id
     */
    public void setCountryId(Object countryId) {
        this.countryId = countryId;
    }

    /**
     *
     * @return
     * The postalCode
     */
    public Object getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode
     * The postal_code
     */
    public void setPostalCode(Object postalCode) {
        this.postalCode = postalCode;
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
     * The userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     *
     * @return
     * The longtitude
     */
    public String getLongtitude() {
        return longtitude;
    }

    /**
     *
     * @param longtitude
     * The longtitude
     */
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    /**
     *
     * @return
     * The addressType
     */
    public int getAddressType() {
        return addressType;
    }

    /**
     *
     * @param addressType
     * The address_type
     */
    public void setAddressType(int addressType) {
        this.addressType = addressType;
    }

}
