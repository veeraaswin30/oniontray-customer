package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorInfo {

    @SerializedName("outlet_name")
    @Expose
    private String Outlet_Name;
    @SerializedName("logo_image")
    @Expose
    private String LogoImage;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("created_date")
    @Expose
    private String createdDate;

    @SerializedName("order_status")
    @Expose
    private Integer orderStatus;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("payment_gateway_name")
    @Expose
    private String paymentGatewayName;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("order_key_formated")
    @Expose
    private String orderKeyFormated;
    @SerializedName("start_time")
    @Expose
    private String start_time;
    @SerializedName("end_time")
    @Expose
    private String end_time;
    @SerializedName("invoice_id")
    @Expose
    private String invoice_id;
    @SerializedName("color_code")
    @Expose
    private String colorCode;
    @SerializedName("cancel_time")
    @Expose
    private String cancelTime;

    @SerializedName("payment_type")
    @Expose
    private String paymentType;






    /**
     *
     * @return
     * The vendorName
     */
    public String getOutletName() {
        return Outlet_Name;
    }

    /**
     *
     * @param Outlet_Name
     * The vendor_name
     */
    public void setOutletName(String Outlet_Name) {
        this.Outlet_Name = Outlet_Name;
    }

    /**
     *
     * @return
     * The LogoImage
     */
    public String getLogoImage() {
        return LogoImage;
    }

    /**
     *
     * @param LogoImage
     * The LogoImage
     */
    public void setLogoImage(String LogoImage) {
        this.LogoImage = LogoImage;
    }

    /**
     *
     * @return
     * The orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     * The order_id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
     * The orderStatus
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     *
     * @param orderStatus
     * The order_status
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
     * The paymentGatewayName
     */
    public String getPaymentGatewayName() {
        return paymentGatewayName;
    }

    /**
     *
     * @param paymentGatewayName
     * The payment_gateway_name
     */
    public void setPaymentGatewayName(String paymentGatewayName) {
        this.paymentGatewayName = paymentGatewayName;
    }

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
     * The vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     *
     * @param vendorId
     * The vendor_id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     *
     * @return
     * The orderKeyFormated
     */
    public String getOrderKeyFormated() {
        return orderKeyFormated;
    }

    /**
     *
     * @param orderKeyFormated
     * The order_key_formated
     */
    public void setOrderKeyFormated(String orderKeyFormated) {
        this.orderKeyFormated = orderKeyFormated;
    }

    /**
     *
     * @return
     * The start_time
     */
    public String getstart_time() {
        return start_time;
    }

    /**
     *
     * @param start_time
     * The order_key_formated
     */
    public void setstart_time(String start_time) {
        this.start_time = start_time;
    }

    /**
     *
     * @return
     * The end_time
     */
    public String getend_time() {
        return end_time;
    }

    /**
     *
     * @param end_time
     * The order_key_formated
     */
    public void setend_time(String end_time) {
        this.end_time = end_time;
    }

    /**
     *
     * @return
     * The invoice_id
     */
    public String getInvoiceId() {
        return invoice_id;
    }

    /**
     *
     * @param invoice_id
     * The order_key_formated
     */
    public void setInvoiceId(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    /**
     *
     * @return
     * The colorCode
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     *
     * @param colorCode
     * The order_key_formated
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }



    @SerializedName("order_type")
    @Expose
    private Integer orderType;

    @SerializedName("contact_address")
    @Expose
    private String contactAddress;

    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;

    @SerializedName("delivery_instructions")
    @Expose
    private String deliveryInstructions;

    @SerializedName("user_contact_address")
    @Expose
    private String userContactAddress;

    @SerializedName("featured_image")
    @Expose
    private String featured_image;

    @SerializedName("tax_label_name")
    @Expose
    private String tax_label_name;



    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }


    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }


    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public String getDeliveryInstructions() {
        return deliveryInstructions;
    }

    public void setDeliveryInstructions(String deliveryInstructions) {
        this.deliveryInstructions = deliveryInstructions;
    }


    public String getUserContactAddress() {
        return userContactAddress;
    }

    public void setUserContactAddress(String userContactAddress) {
        this.userContactAddress = userContactAddress;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }


    public void setTaxLabelName(String tax_label_name) {
        this.tax_label_name = tax_label_name;
    }

    public String getTaxLabelName() {
        return tax_label_name;
    }


}