package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class OrderList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("encrypt_id")
    @Expose
    private String encryptId;
    @SerializedName("vendor_name")
    @Expose
    private String vendorName;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("location_name")
    @Expose
    private String locationName;


    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("status_name")
    @Expose
    private String statusName;


    @SerializedName("order_status_id")
    @Expose
    private Integer order_status_id;


    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("order_key_formated")
    @Expose
    private String orderKeyFormated;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEncryptId() {
        return encryptId;
    }

    public void setEncryptId(String encryptId) {
        this.encryptId = encryptId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }


    public Integer getOrderStatusId() {
        return order_status_id;
    }

    public void setOrderStatusId(Integer order_status_id) {
        this.order_status_id = order_status_id;
    }



    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public String getOrderKeyFormated() {
        return orderKeyFormated;
    }

    public void setOrderKeyFormated(String orderKeyFormated) {
        this.orderKeyFormated = orderKeyFormated;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

}