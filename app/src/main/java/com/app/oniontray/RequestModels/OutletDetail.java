package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutletDetail implements Serializable {

    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    /**
     *
     * @return
     * The outletId
     */
    public Integer getOutletId() {
        return outletId;
    }

    /**
     *
     * @param outletId
     * The outlet_id
     */
    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    /**
     *
     * @return
     * The contactAddress
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     *
     * @param contactAddress
     * The contact_address
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
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
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

}
