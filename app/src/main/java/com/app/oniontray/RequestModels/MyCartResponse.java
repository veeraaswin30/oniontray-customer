package com.app.oniontray.RequestModels;


import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyCartResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("cart_items")
    @Expose
    private ArrayList<CartItem> cartItems = new ArrayList<>();
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;
    @SerializedName("tax")
    @Expose
    private String tax;
    @SerializedName("delivery_cost")
    @Expose
    private String DeliveryCharge;
    @SerializedName("minimum_order_amount")
    @Expose
    private String MinimumOrderAmount;
    @SerializedName("outlet_name")
    @Expose
    private String outlet_name;
    @SerializedName("outlet_logo")
    @Expose
    private String outlet_logo;

    /**
     *
     * @return
     * The httpCode
     */
    public Integer getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cartItems
     */
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     *
     * @param cartItems
     * The cart_items
     */
    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     *
     * @return
     * The total
     */
    public String getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The subTotal
     */
    public String getSubTotal() {
        return subTotal;
    }

    /**
     *
     * @param subTotal
     * The sub_total
     */
    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    /**
     *
     * @return
     * The tax
     */
    public String getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     * The tax
     */
    public void setTax(String tax) {
        this.tax = tax;
    }

    /**
     *
     * @return
     * The DeliveryCharge
     */
    public String getDeliveryCharge() {
        return DeliveryCharge;
    }

    /**
     *
     * @param DeliveryCharge
     * The DeliveryCharge
     */
    public void setDeliveryCharge(String DeliveryCharge) {
        this.DeliveryCharge = DeliveryCharge;
    }

    /**
     *
     * @return
     * The MinimumOrderAmount
     */
    public String getMinimumOrderAmount() {
        return MinimumOrderAmount;
    }

    /**
     *
     * @param MinimumOrderAmount
     * The MinimumOrderAmount
     */
    public void setMinimumOrderAmount(String MinimumOrderAmount) {
        this.MinimumOrderAmount = MinimumOrderAmount;
    }

    /**
     *
     * @return
     * The outlet_name
     */
    public String getOutletName() {
        return outlet_name;
    }

    /**
     *
     * @param outlet_name
     * The outlet_name
     */
    public void setOutletName(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    /**
     *
     * @return
     * The outlet_logo
     */
    public String getOutletLogo() {
        return outlet_logo;
    }

    /**
     *
     * @param outlet_logo
     * The outlet_logo
     */
    public void setOutletLogo(String outlet_logo) {
        this.outlet_logo = outlet_logo;
    }


}