package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ProcToCheckCartItem implements Serializable {

    @SerializedName("cart_id")
    @Expose
    private Integer cartId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("cart_status")
    @Expose
    private Integer cartStatus;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("weight_class_id")
    @Expose
    private Integer weightClassId;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("product_qty")
    @Expose
    private Integer productQty;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("stock_status")
    @Expose
    private Object stockStatus;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("minimum_order_amount")
    @Expose
    private String minimumOrderAmount;
    @SerializedName("delivery_charges_fixed")
    @Expose
    private String deliveryChargesFixed;
    @SerializedName("delivery_charges_variation")
    @Expose
    private String deliveryChargesVariation;
    @SerializedName("service_tax")
    @Expose
    private String serviceTax;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("cart_detail_id")
    @Expose
    private Integer cartDetailId;
    @SerializedName("vendor_key")
    @Expose
    private Object vendorKey;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("title")
    @Expose
    private String title;

    /**
     *
     * @return
     *     The cartId
     */
    public Integer getCartId() {
        return cartId;
    }

    /**
     *
     * @param cartId
     *     The cart_id
     */
    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    /**
     *
     * @return
     *     The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     *     The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     *
     * @return
     *     The storeId
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     *
     * @param storeId
     *     The store_id
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    /**
     *
     * @return
     *     The outletId
     */
    public Integer getOutletId() {
        return outletId;
    }

    /**
     *
     * @param outletId
     *     The outlet_id
     */
    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    /**
     *
     * @return
     *     The cartStatus
     */
    public Integer getCartStatus() {
        return cartStatus;
    }

    /**
     *
     * @param cartStatus
     *     The cart_status
     */
    public void setCartStatus(Integer cartStatus) {
        this.cartStatus = cartStatus;
    }

    /**
     *
     * @return
     *     The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     *     The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The originalPrice
     */
    public String getOriginalPrice() {
        return originalPrice;
    }

    /**
     *
     * @param originalPrice
     *     The original_price
     */
    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     *
     * @return
     *     The discountPrice
     */
    public String getDiscountPrice() {
        return discountPrice;
    }

    /**
     *
     * @param discountPrice
     *     The discount_price
     */
    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     *
     * @return
     *     The weightClassId
     */
    public Integer getWeightClassId() {
        return weightClassId;
    }

    /**
     *
     * @param weightClassId
     *     The weight_class_id
     */
    public void setWeightClassId(Integer weightClassId) {
        this.weightClassId = weightClassId;
    }

    /**
     *
     * @return
     *     The weight
     */
    public String getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     *     The weight
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     *     The productQty
     */
    public Integer getProductQty() {
        return productQty;
    }

    /**
     *
     * @param productQty
     *     The product_qty
     */
    public void setProductQty(Integer productQty) {
        this.productQty = productQty;
    }

    /**
     *
     * @return
     *     The productImage
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     *
     * @param productImage
     *     The product_image
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    /**
     *
     * @return
     *     The stockStatus
     */
    public Object getStockStatus() {
        return stockStatus;
    }

    /**
     *
     * @param stockStatus
     *     The stock_status
     */
    public void setStockStatus(Object stockStatus) {
        this.stockStatus = stockStatus;
    }

    /**
     *
     * @return
     *     The productId
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     *     The product_id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     *     The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     *     The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     *     The subCategoryId
     */
    public String getSubCategoryId() {
        return subCategoryId;
    }

    /**
     *
     * @param subCategoryId
     *     The sub_category_id
     */
    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    /**
     *
     * @return
     *     The vendorId
     */
    public Integer getVendorId() {
        return vendorId;
    }

    /**
     *
     * @param vendorId
     *     The vendor_id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     *
     * @return
     *     The minimumOrderAmount
     */
    public String getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    /**
     *
     * @param minimumOrderAmount
     *     The minimum_order_amount
     */
    public void setMinimumOrderAmount(String minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    /**
     *
     * @return
     *     The deliveryChargesFixed
     */
    public String getDeliveryChargesFixed() {
        return deliveryChargesFixed;
    }

    /**
     *
     * @param deliveryChargesFixed
     *     The delivery_charges_fixed
     */
    public void setDeliveryChargesFixed(String deliveryChargesFixed) {
        this.deliveryChargesFixed = deliveryChargesFixed;
    }

    /**
     *
     * @return
     *     The deliveryChargesVariation
     */
    public String getDeliveryChargesVariation() {
        return deliveryChargesVariation;
    }

    /**
     *
     * @param deliveryChargesVariation
     *     The delivery_charges_variation
     */
    public void setDeliveryChargesVariation(String deliveryChargesVariation) {
        this.deliveryChargesVariation = deliveryChargesVariation;
    }

    /**
     *
     * @return
     *     The serviceTax
     */
    public String getServiceTax() {
        return serviceTax;
    }

    /**
     *
     * @param serviceTax
     *     The service_tax
     */
    public void setServiceTax(String serviceTax) {
        this.serviceTax = serviceTax;
    }

    /**
     *
     * @return
     *     The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     *     The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
     *     The cartDetailId
     */
    public Integer getCartDetailId() {
        return cartDetailId;
    }

    /**
     *
     * @param cartDetailId
     *     The cart_detail_id
     */
    public void setCartDetailId(Integer cartDetailId) {
        this.cartDetailId = cartDetailId;
    }

    /**
     *
     * @return
     *     The vendorKey
     */
    public Object getVendorKey() {
        return vendorKey;
    }

    /**
     *
     * @param vendorKey
     *     The vendor_key
     */
    public void setVendorKey(Object vendorKey) {
        this.vendorKey = vendorKey;
    }

    /**
     *
     * @return
     *     The unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     *
     * @param unit
     *     The unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}
