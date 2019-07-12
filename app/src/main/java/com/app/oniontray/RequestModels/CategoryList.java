
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryList implements Serializable {



    @SerializedName("category_id")
    @Expose
    private String category_id;
    @SerializedName("url_key")
    @Expose
    private String url_key;
    @SerializedName("category_name")
    @Expose
    private String category_name;


    /**
     *
     * @return
     *     The category_id
     */
    public String getCategoryId() {
        return category_id;
    }

    /**
     *
     * @param category_id
     *     The category_id
     */
    public void setCategoryId(String category_id) {
        this.category_id = category_id;
    }

    /**
     *
     * @return
     *     The url_key
     */
    public String getUrlKey() {
        return url_key;
    }

    /**
     *
     * @param url_key
     *     The url_key
     */
    public void setUrlKey(String url_key) {
        this.url_key = url_key;
    }

    /**
     *
     * @return
     *     The category_name
     */
    public String getCategoryName() {
        return category_name;
    }

    /**
     *
     * @param category_name
     *     The category_name
     */
    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }






    @SerializedName("main_category_id")
    @Expose
    private String mainCategoryId;
    @SerializedName("main_category_name")
    @Expose
    private String mainCategoryName;
    @SerializedName("main_category_url_key")
    @Expose
    private String mainCategoryUrlKey;
    @SerializedName("sub_category_list")
    @Expose
    private List<SubCategoryList> subCategoryList = new ArrayList<>();

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
     *     The mainCategoryName
     */
    public String getMainCategoryName() {
        return mainCategoryName;
    }

    /**
     * 
     * @param mainCategoryName
     *     The main_category_name
     */
    public void setMainCategoryName(String mainCategoryName) {
        this.mainCategoryName = mainCategoryName;
    }

    /**
     * 
     * @return
     *     The mainCategoryUrlKey
     */
    public String getMainCategoryUrlKey() {
        return mainCategoryUrlKey;
    }

    /**
     * 
     * @param mainCategoryUrlKey
     *     The main_category_url_key
     */
    public void setMainCategoryUrlKey(String mainCategoryUrlKey) {
        this.mainCategoryUrlKey = mainCategoryUrlKey;
    }

    /**
     *
     * @return
     *     The subCategoryList
     */
    public List<SubCategoryList> getSubCategoryList() {
        return subCategoryList;
    }

    /**
     *
     * @param subCategoryList
     *     The sub_category_list
     */
    public void setSubCategoryList(List<SubCategoryList> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }



}
