
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryList {

    @SerializedName("main_category_id")
    @Expose
    private String mainCategoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("sub_category_name")
    @Expose
    private String subCategoryName;
    @SerializedName("sub_category_url_key")
    @Expose
    private String subCategoryUrlKey;
    @SerializedName("category_image")
    @Expose
    private String categoryImage;


    /**
     *
     * @return
     *     The mainCategoryId
     */
    public String getMainCategoryId() {
        return mainCategoryId;
    }

    /**
     *
     * @param mainCategoryId
     *     The main_category_id
     */
    public void setMainCategoryId(String mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
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
     *     The subCategoryName
     */
    public String getSubCategoryName() {
        return subCategoryName;
    }

    /**
     * 
     * @param subCategoryName
     *     The sub_category_name
     */
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    /**
     * 
     * @return
     *     The subCategoryUrlKey
     */
    public String getSubCategoryUrlKey() {
        return subCategoryUrlKey;
    }

    /**
     * 
     * @param subCategoryUrlKey
     *     The sub_category_url_key
     */
    public void setSubCategoryUrlKey(String subCategoryUrlKey) {
        this.subCategoryUrlKey = subCategoryUrlKey;
    }

    /**
     *
     * @return
     * The categoryImage
     */
    public String getCategoryImage() {
        return categoryImage;
    }

    /**
     *
     * @param categoryImage
     * The category_image
     */
    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

}


