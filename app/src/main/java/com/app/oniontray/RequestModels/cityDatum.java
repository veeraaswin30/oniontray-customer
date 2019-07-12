
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class cityDatum {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("zone_code")
    @Expose
    private String zoneCode;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;
    @SerializedName("active_status")
    @Expose
    private String activeStatus;
    @SerializedName("default_status")
    @Expose
    private Integer defaultStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("url_index")
    @Expose
    private String urlIndex;
    @SerializedName("modified_date")
    @Expose
    private String modifiedDate;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longtitude")
    @Expose
    private String longtitude;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("language_id")
    @Expose
    private Integer languageId;
    @SerializedName("info_id")
    @Expose
    private Integer infoId;

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The zoneCode
     */
    public String getZoneCode() {
        return zoneCode;
    }

    /**
     * 
     * @param zoneCode
     *     The zone_code
     */
    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    /**
     * 
     * @return
     *     The countryId
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * 
     * @param countryId
     *     The country_id
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * 
     * @return
     *     The activeStatus
     */
    public String getActiveStatus() {
        return activeStatus;
    }

    /**
     * 
     * @param activeStatus
     *     The active_status
     */
    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    /**
     * 
     * @return
     *     The defaultStatus
     */
    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    /**
     * 
     * @param defaultStatus
     *     The default_status
     */
    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    /**
     * 
     * @return
     *     The createdDate
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * 
     * @param createdDate
     *     The created_date
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 
     * @return
     *     The urlIndex
     */
    public String getUrlIndex() {
        return urlIndex;
    }

    /**
     * 
     * @param urlIndex
     *     The url_index
     */
    public void setUrlIndex(String urlIndex) {
        this.urlIndex = urlIndex;
    }

    /**
     * 
     * @return
     *     The modifiedDate
     */
    public String getModifiedDate() {
        return modifiedDate;
    }

    /**
     * 
     * @param modifiedDate
     *     The modified_date
     */
    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * @return
     *     The longtitude
     */
    public String getLongtitude() {
        return longtitude;
    }

    /**
     * 
     * @param longtitude
     *     The longtitude
     */
    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    /**
     * 
     * @return
     *     The cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * 
     * @param cid
     *     The cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * 
     * @return
     *     The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 
     * @param cityName
     *     The city_name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 
     * @return
     *     The languageId
     */
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     * 
     * @param languageId
     *     The language_id
     */
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    /**
     * 
     * @return
     *     The infoId
     */
    public Integer getInfoId() {
        return infoId;
    }

    /**
     * 
     * @param infoId
     *     The info_id
     */
    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

}
