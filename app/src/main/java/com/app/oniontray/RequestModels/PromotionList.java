
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionList implements Serializable {

    @SerializedName("outlet_id")
    @Expose
    private Integer outletId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;
    @SerializedName("vendor_id")
    @Expose
    private Integer vendorId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("promotion_products")
    @Expose
    private List<PromotionProduct> promotionProducts = null;

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

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public List<PromotionProduct> getPromotionProducts() {
        return promotionProducts;
    }

    public void setPromotionProducts(List<PromotionProduct> promotionProducts) {
        this.promotionProducts = promotionProducts;
    }

}
