
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriesDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("url_key")
    @Expose
    private String urlKey;
    @SerializedName("logo_image")
    @Expose
    private String logoImage;
    @SerializedName("banner_image")
    @Expose
    private String banner_image;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * @param categoryName
     *     The category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * @return
     *     The urlKey
     */
    public String getUrlKey() {
        return urlKey;
    }

    /**
     * 
     * @param urlKey
     *     The url_key
     */
    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    /**
     * 
     * @return
     *     The logoImage
     */
    public String getLogoImage() {
        return logoImage;
    }

    /**
     * 
     * @param logoImage
     *     The logo_image
     */
    public void setLogoImage(String logoImage) {
        this.logoImage = logoImage;
    }

    /**
     *
     * @return
     *     The banner_image
     */
    public String getbanner_image() {
        return banner_image;
    }

    /**
     *
     * @param banner_image
     *     The banner_image
     */
    public void setbanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

}
