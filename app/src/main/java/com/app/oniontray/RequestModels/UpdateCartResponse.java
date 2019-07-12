package com.app.oniontray.RequestModels;



import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class UpdateCartResponse {

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
    private Integer total;
    @SerializedName("sub_total")
    @Expose
    private Integer subTotal;
    @SerializedName("tax")
    @Expose
    private Integer tax;

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
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     *
     * @return
     * The subTotal
     */
    public Integer getSubTotal() {
        return subTotal;
    }

    /**
     *
     * @param subTotal
     * The sub_total
     */
    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    /**
     *
     * @return
     * The tax
     */
    public Integer getTax() {
        return tax;
    }

    /**
     *
     * @param tax
     * The tax
     */
    public void setTax(Integer tax) {
        this.tax = tax;
    }

}
