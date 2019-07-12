
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StoProdBannerList_Data {

    @SerializedName("banner_id")
    @Expose
    private int bannerId;
    @SerializedName("banner_title")
    @Expose
    private String bannerTitle;
    @SerializedName("banner_subtitle")
    @Expose
    private String bannerSubtitle;
    @SerializedName("banner_image")
    @Expose
    private String bannerImage;
    @SerializedName("banner_link")
    @Expose
    private String bannerLink;

    /**
     * 
     * @return
     *     The bannerId
     */
    public int getBannerId() {
        return bannerId;
    }

    /**
     * 
     * @param bannerId
     *     The banner_id
     */
    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    /**
     * 
     * @return
     *     The bannerTitle
     */
    public String getBannerTitle() {
        return bannerTitle;
    }

    /**
     * 
     * @param bannerTitle
     *     The banner_title
     */
    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    /**
     * 
     * @return
     *     The bannerSubtitle
     */
    public String getBannerSubtitle() {
        return bannerSubtitle;
    }

    /**
     * 
     * @param bannerSubtitle
     *     The banner_subtitle
     */
    public void setBannerSubtitle(String bannerSubtitle) {
        this.bannerSubtitle = bannerSubtitle;
    }

    /**
     * 
     * @return
     *     The bannerImage
     */
    public String getBannerImage() {
        return bannerImage;
    }

    /**
     * 
     * @param bannerImage
     *     The banner_image
     */
    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    /**
     * 
     * @return
     *     The bannerLink
     */
    public String getBannerLink() {
        return bannerLink;
    }

    /**
     * 
     * @param bannerLink
     *     The banner_link
     */
    public void setBannerLink(String bannerLink) {
        this.bannerLink = bannerLink;
    }

}
