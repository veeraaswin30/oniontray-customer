package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("language_code")
    @Expose
    private String languageCode;
    @SerializedName("date_format_short")
    @Expose
    private String dateFormatShort;
    @SerializedName("date_format_full")
    @Expose
    private String dateFormatFull;
    @SerializedName("is_rtl")
    @Expose
    private Integer isRtl;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id")
    @Expose
    private String id;

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
     * The languageCode
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     *
     * @param languageCode
     * The language_code
     */
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     *
     * @return
     * The dateFormatShort
     */
    public String getDateFormatShort() {
        return dateFormatShort;
    }

    /**
     *
     * @param dateFormatShort
     * The date_format_short
     */
    public void setDateFormatShort(String dateFormatShort) {
        this.dateFormatShort = dateFormatShort;
    }

    /**
     *
     * @return
     * The dateFormatFull
     */
    public String getDateFormatFull() {
        return dateFormatFull;
    }

    /**
     *
     * @param dateFormatFull
     * The date_format_full
     */
    public void setDateFormatFull(String dateFormatFull) {
        this.dateFormatFull = dateFormatFull;
    }

    /**
     *
     * @return
     * The isRtl
     */
    public Integer getIsRtl() {
        return isRtl;
    }

    /**
     *
     * @param isRtl
     * The is_rtl
     */
    public void setIsRtl(Integer isRtl) {
        this.isRtl = isRtl;
    }

    /**
     *
     * @return
     * The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

}