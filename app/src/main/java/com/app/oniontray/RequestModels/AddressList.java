package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AddressList implements Serializable {


//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("address")
//    @Expose
//    private String address;
//    @SerializedName("city_id")
//    @Expose
//    private Object cityId;
//    @SerializedName("country_id")
//    @Expose
//    private Object countryId;
//
//    @SerializedName("location_id")
//    @Expose
//    private String locationId;
//    @SerializedName("postal_code")
//    @Expose
//    private Object postalCode;
//    @SerializedName("active_status")
//    @Expose
//    private String activeStatus;
//    @SerializedName("created_date")
//    @Expose
//    private String createdDate;
//    @SerializedName("modified_date")
//    @Expose
//    private String modifiedDate;
//    @SerializedName("user_id")
//    @Expose
//    private int userId;
//    @SerializedName("id")
//    @Expose
//    private int id;
//    @SerializedName("latitude")
//    @Expose
//    private String latitude;
//    @SerializedName("longtitude")
//    @Expose
//    private String longtitude;
//    @SerializedName("address_type")
//    @Expose
//    private String addressType;
//    @SerializedName("address_id")
//    @Expose
//    private int addressId;
//
//    @SerializedName("user_address_latitude")
//    @Expose
//    private String user_address_latitude;
//    @SerializedName("user_address_longtitude")
//    @Expose
//    private String user_address_longtitude;
//
//
//
//    /**
//     *
//     * @return
//     * The name
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     *
//     * @param name
//     * The name
//     */
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     *
//     * @return
//     * The address
//     */
//    public String getAddress() {
//        return address;
//    }
//
//    /**
//     *
//     * @param address
//     * The address
//     */
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    /**
//     *
//     * @return
//     * The cityId
//     */
//    public Object getCityId() {
//        return cityId;
//    }
//
//    /**
//     *
//     * @param cityId
//     * The city_id
//     */
//    public void setCityId(Object cityId) {
//        this.cityId = cityId;
//    }
//
//    /**
//     *
//     * @return
//     * The countryId
//     */
//    public Object getCountryId() {
//        return countryId;
//    }
//
//    /**
//     *
//     * @param countryId
//     * The country_id
//     */
//    public void setCountryId(Object countryId) {
//        this.countryId = countryId;
//    }
//
//    /**
//     *
//     * @return
//     * The postalCode
//     */
//    public Object getPostalCode() {
//        return postalCode;
//    }
//
//    /**
//     *
//     * @param postalCode
//     * The postal_code
//     */
//    public void setPostalCode(Object postalCode) {
//        this.postalCode = postalCode;
//    }
//
//    /**
//     *
//     * @return
//     * The activeStatus
//     */
//    public String getActiveStatus() {
//        return activeStatus;
//    }
//
//    /**
//     *
//     * @param activeStatus
//     * The active_status
//     */
//    public void setActiveStatus(String activeStatus) {
//        this.activeStatus = activeStatus;
//    }
//
//    /**
//     *
//     * @return
//     * The createdDate
//     */
//    public String getCreatedDate() {
//        return createdDate;
//    }
//
//    /**
//     *
//     * @param createdDate
//     * The created_date
//     */
//    public void setCreatedDate(String createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    /**
//     *
//     * @return
//     * The modifiedDate
//     */
//    public String getModifiedDate() {
//        return modifiedDate;
//    }
//
//    /**
//     *
//     * @param modifiedDate
//     * The modified_date
//     */
//    public void setModifiedDate(String modifiedDate) {
//        this.modifiedDate = modifiedDate;
//    }
//
//    /**
//     *
//     * @return
//     * The userId
//     */
//    public int getUserId() {
//        return userId;
//    }
//
//    /**
//     *
//     * @param userId
//     * The user_id
//     */
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    /**
//     *
//     * @return
//     * The id
//     */
//    public int getId() {
//        return id;
//    }
//
//    /**
//     *
//     * @param id
//     * The id
//     */
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    /**
//     *
//     * @return
//     * The latitude
//     */
//    public String getLatitude() {
//        return latitude;
//    }
//
//    /**
//     *
//     * @param latitude
//     * The latitude
//     */
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    /**
//     *
//     * @return
//     * The longtitude
//     */
//    public String getLongtitude() {
//        return longtitude;
//    }
//
//    /**
//     *
//     * @param longtitude
//     * The longtitude
//     */
//    public void setLongtitude(String longtitude) {
//        this.longtitude = longtitude;
//    }
//
//    /**
//     *
//     * @return
//     * The addressType
//     */
//    public String getAddressType() {
//        return addressType;
//    }
//
//    /**
//     *
//     * @param addressType
//     * The address_type
//     */
//    public void setAddressType(String addressType) {
//        this.addressType = addressType;
//    }
//
//    /**
//     *
//     * @return
//     * The addressId
//     */
//    public int getAddressId() {
//        return addressId;
//    }
//
//    /**
//     *
//     * @param addressId
//     * The address_id
//     */
//    public void setAddressId(int addressId) {
//        this.addressId = addressId;
//    }
//
//
//    /**
//     *
//     * @return
//     * The user_address_latitude
//     */
//    public String getUserAddressLatitude() {
//        return user_address_latitude;
//    }
//
//    /**
//     *
//     * @param user_address_latitude
//     * The user_address_latitude
//     */
//    public void setUserAddressLatitude(String user_address_latitude) {
//        this.user_address_latitude = user_address_latitude;
//    }
//
//    /**
//     *
//     * @return
//     * The user_address_longtitude
//     */
//    public String getUserAddressLongtitude() {
//        return user_address_longtitude;
//    }
//
//    /**
//     *
//     * @param user_address_longtitude
//     * The user_address_longtitude
//     */
//    public void setUserAddressLongtitude(String user_address_longtitude) {
//        this.user_address_longtitude = user_address_longtitude;
//    }
//
//    public String getLocationId() {
//        return locationId;
//    }
//
//    public void setLocationId(String locationId) {
//        this.locationId = locationId;
//    }


    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address_id")
    @Expose
    private Integer addressId;
    @SerializedName("address_type")
    @Expose
    private String addressType;
    @SerializedName("user_address_latitude")
    @Expose
    private String userAddressLatitude;
    @SerializedName("user_address_longtitude")
    @Expose
    private String userAddressLongtitude;
    @SerializedName("landmark")
    @Expose
    private String landmark;
    @SerializedName("flat_number")
    @Expose
    private String flatNumber;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getUserAddressLatitude() {
        return userAddressLatitude;
    }

    public void setUserAddressLatitude(String userAddressLatitude) {
        this.userAddressLatitude = userAddressLatitude;
    }

    public String getUserAddressLongtitude() {
        return userAddressLongtitude;
    }

    public void setUserAddressLongtitude(String userAddressLongtitude) {
        this.userAddressLongtitude = userAddressLongtitude;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }


}
