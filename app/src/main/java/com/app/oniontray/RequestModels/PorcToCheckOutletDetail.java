package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;



public class PorcToCheckOutletDetail implements Serializable {

    @SerializedName("vendor_id")
    @Expose
    private int vendor_id;
    @SerializedName("outlet_id")
    @Expose
    private int outletId;
    @SerializedName("contact_address")
    @Expose
    private String contactAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("delivery_time")
    @Expose
    private String deliveryTime;

    /**
     *
     * @return
     *     The vendor_id
     */
    public int getVendorId() {
        return vendor_id;
    }

    /**
     *
     * @param vendor_id
     *     The vendor_id
     */
    public void setVendorId(int vendor_id) {
        this.vendor_id = vendor_id;
    }

    /**
     *
     * @return
     *     The outletId
     */
    public int getOutletId() {
        return outletId;
    }

    /**
     *
     * @param outletId
     *     The outlet_id
     */
    public void setOutletId(int outletId) {
        this.outletId = outletId;
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
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     *     The deliveryTime
     */
    public String getDeliveryTime() {
        return deliveryTime;
    }

    /**
     *
     * @param deliveryTime
     *     The delivery_time
     */
    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


}
