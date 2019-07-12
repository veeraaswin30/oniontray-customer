
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductList_Data implements Serializable{

        @SerializedName("product_id")
        @Expose
        private int productId;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("product_url")
        @Expose
        private String productUrl;
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
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("outlet_id")
        @Expose
        private int outlet_id;
        @SerializedName("outlet_name")
        @Expose
        private String outletName;
        @SerializedName("average_rating")
        @Expose
        private int averageRating;
        @SerializedName("product_image")
        @Expose
        private String productImage;
        @SerializedName("cart_count")
        @Expose
        private int cart_count;

        @SerializedName("total")
        @Expose
        private String total;

        @SerializedName("preparation_time")
        @Expose
        private String preparationTime;


        @SerializedName("primary_id")
        @Expose
        private int primaryID;

        @SerializedName("ingred_type_list")
        @Expose
        private List<IngredTypeList> ingredTypeList = new ArrayList<>();
        @SerializedName("open_restaurant")
        @Expose
        private int open_restaurant;


        /**

         *
         * @return
         * The productId
         */
        public int getProductId() {
            return productId;
        }

        /**
         *
         * @param productId
         * The product_id
         */
        public void setProductId(int productId) {
            this.productId = productId;
        }

        /**
         *
         * @return
         * The productName
         */
        public String getproductName() {
            return productName;
        }

        /**
         *
         * @param productName
         * The productName
         */
        public void setproductName(String productName) {
            this.productName = productName;
        }

        /**
         *
         * @return
         * The productUrl
         */
        public String getProductUrl() {
            return productUrl;
        }

        /**
         *
         * @param productUrl
         * The product_url
         */
        public void setProductUrl(String productUrl) {
            this.productUrl = productUrl;
        }

        /**
         *
         * @return
         * The weight
         */
        public String getWeight() {
            return weight;
        }

        /**
         *
         * @param weight
         * The weight
         */
        public void setWeight(String weight) {
            this.weight = weight;
        }

        /**
         *
         * @return
         * The originalPrice
         */
        public String getOriginalPrice() {
            return originalPrice;
        }

        /**
         *
         * @param originalPrice
         * The original_price
         */
        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        /**
         *
         * @return
         * The discountPrice
         */
        public String getDiscountPrice() {
            return discountPrice;
        }

        /**
         *
         * @param discountPrice
         * The discount_price
         */
        public void setDiscountPrice(String discountPrice) {
            this.discountPrice = discountPrice;
        }

        /**
         *
         * @return
         * The categoryId
         */
        public String getCategoryId() {
            return categoryId;
        }

        /**
         *
         * @param categoryId
         * The category_id
         */
        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        /**
         *
         * @return
         * The unit
         */
        public String getUnit() {
            return unit;
        }

        /**
         *
         * @param unit
         * The unit
         */
        public void setUnit(String unit) {
            this.unit = unit;
        }

        /**
         *
         * @return
         * The description
         */
        public String getDescription() {
            return description;
        }

        /**
         *
         * @param description
         * The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         *
         * @return
         * The title
         */
        public String getTitle() {
            return title;
        }

        /**
         *
         * @param title
         * The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         *
         * @return
         * The outlet_id
         */
        public int getOutletId() {
            return outlet_id;
        }

        /**
         *
         * @param outlet_id
         * The outlet_id
         */
        public void setOutletId(int outlet_id) {
            this.outlet_id = outlet_id;
        }

        /**
         *
         * @return
         * The outletName
         */
        public String getOutletName() {
            return outletName;
        }

        /**
         *
         * @param outletName
         * The outlet_name
         */
        public void setOutletName(String outletName) {
            this.outletName = outletName;
        }

        /**
         *
         * @return
         * The averageRating
         */
        public int getAverageRating() {
            return averageRating;
        }

        /**
         *
         * @param averageRating
         * The average_rating
         */
        public void setAverageRating(int averageRating) {
            this.averageRating = averageRating;
        }

        /**
         *
         * @return
         * The productImage
         */
        public String getProductImage() {
            return productImage;
        }

        /**
         *
         * @param productImage
         * The product_image
         */
        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        /**
         *
         * @return
         * The cart_count
         */
        public int getCartCount() {
            return cart_count;
        }

        /**
         *
         * @param cart_count
         * The cart_count
         */
        public void setCartCount(int cart_count) {
            this.cart_count = cart_count;
        }


        public List<IngredTypeList> getIngredTypeList() {
                return ingredTypeList;
        }

        public void setIngredTypeList(List<IngredTypeList> ingredTypeList) {
                this.ingredTypeList = ingredTypeList;
        }

        /**
         *
         * @return
         * The open_restaurant
         */
        public int getOpenRestaurant() {
                return open_restaurant;
        }

        /**
         *
         * @param open_restaurant
         * The open_restaurant
         */
        public void setOpenRestaurant(int open_restaurant) {
                this.open_restaurant = open_restaurant;
        }


        public String getTotal() {
                return total;
        }

        public void setTotal(String total) {
                this.total = total;
        }

        public int getPrimaryID() {
                return primaryID;
        }

        public void setPrimaryID(int primaryID) {
                this.primaryID = primaryID;
        }

        public String getPreparationTime() {
                return preparationTime;
        }

        public void setPreparationTime(String preparationTime) {
                this.preparationTime = preparationTime;
        }

}