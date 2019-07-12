package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class NotifData {

    @SerializedName("notification_id")
    @Expose
    private int id;
    @SerializedName("order_id")
    @Expose
    private int orderId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("read_status")
    @Expose
    private int readStatus;
    @SerializedName("created_date")
    @Expose
    private String createdDate;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }


}
