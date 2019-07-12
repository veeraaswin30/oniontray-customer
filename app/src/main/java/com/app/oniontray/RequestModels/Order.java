
package com.app.oniontray.RequestModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_url")
    @Expose
    private String productUrl;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("discount_price")
    @Expose
    private String discountPrice;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("ingredient_list")
    @Expose
    private List<ReOrdIngredientList> ingredientList = null;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("cart_count")
    @Expose
    private Integer cartCount;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getOutletId() {
        return outletId;
    }

    public void setOutletId(Integer outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public List<ReOrdIngredientList> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<ReOrdIngredientList> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getCartCount() {
        return cartCount;
    }

    public void setCartCount(Integer cartCount) {
        this.cartCount = cartCount;
    }

}
