
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaStoreList_Data {

    @SerializedName("vendors_id")
    @Expose
    private Integer vendorsId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("category_ids")
    @Expose
    private String categoryIds;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("vendor_description")
    @Expose
    private String vendorDescription;
    @SerializedName("vendors_delivery_time")
    @Expose
    private String vendorsDeliveryTime;
    @SerializedName("vendors_average_rating")
    @Expose
    private Integer vendorsAverageRating;
    @SerializedName("featured_vendor")
    @Expose
    private Integer featuredVendor;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
    @SerializedName("outlets_count")
    @Expose
    private int outletsCount;
    @SerializedName("outlets_id")
    @Expose
    private String outletsId;

    /**
     * 
     * @return
     *     The vendorsId
     */
    public Integer getVendorsId() {
        return vendorsId;
    }

    /**
     * 
     * @param vendorsId
     *     The vendors_id
     */
    public void setVendorsId(Integer vendorsId) {
        this.vendorsId = vendorsId;
    }

    /**
     * 
     * @return
     *     The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * 
     * @param vendorName
     *     The vendor_name
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * 
     * @return
     *     The categoryIds
     */
    public String getCategoryIds() {
        return categoryIds;
    }

    /**
     * 
     * @param categoryIds
     *     The category_ids
     */
    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds;
    }

    /**
     * 
     * @return
     *     The contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * 
     * @param contactAddress
     *     The contact_address
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * 
     * @return
     *     The vendorDescription
     */
    public String getVendorDescription() {
        return vendorDescription;
    }

    /**
     * 
     * @param vendorDescription
     *     The vendor_description
     */
    public void setVendorDescription(String vendorDescription) {
        this.vendorDescription = vendorDescription;
    }

    /**
     * 
     * @return
     *     The vendorsDeliveryTime
     */
    public String getVendorsDeliveryTime() {
        return vendorsDeliveryTime;
    }

    /**
     * 
     * @param vendorsDeliveryTime
     *     The vendors_delivery_time
     */
    public void setVendorsDeliveryTime(String vendorsDeliveryTime) {
        this.vendorsDeliveryTime = vendorsDeliveryTime;
    }

    /**
     * 
     * @return
     *     The vendorsAverageRating
     */
    public Integer getVendorsAverageRating() {
        return vendorsAverageRating;
    }

    /**
     * 
     * @param vendorsAverageRating
     *     The vendors_average_rating
     */
    public void setVendorsAverageRating(Integer vendorsAverageRating) {
        this.vendorsAverageRating = vendorsAverageRating;
    }

    /**
     * 
     * @return
     *     The featuredVendor
     */
    public Integer getFeaturedVendor() {
        return featuredVendor;
    }

    /**
     * 
     * @param featuredVendor
     *     The featured_vendor
     */
    public void setFeaturedVendor(Integer featuredVendor) {
        this.featuredVendor = featuredVendor;
    }

    /**
     * 
     * @return
     *     The logoImage
     */
    public String getLogoImage() {
        return logoImage;
    }

    /**
     * 
     * @param logoImage
     *     The logo_image
     */
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    /**
     * 
     * @return
     *     The outletsCount
     */
    public int getOutletsCount() {
        return outletsCount;
    }

    /**
     * 
     * @param outletsCount
     *     The outlets_count
     */
    public void setOutletsCount(int outletsCount) {
        this.outletsCount = outletsCount;
    }

    /**
     * 
     * @return
     *     The outletsId
     */
    public String getOutletsId() {
        return outletsId;
    }

    /**
     * 
     * @param outletsId
     *     The outlets_id
     */
    public void setOutletsId(String outletsId) {
        this.outletsId = outletsId;
    }

}
