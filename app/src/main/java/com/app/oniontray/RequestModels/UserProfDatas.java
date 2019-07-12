
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfDatas {

    @SerializedName("social_title")
    @Expose
    private String socialTitle;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("civil_id")
    @Expose
    private String civilId;
    @SerializedName("cooperative_id")
    @Expose
    private String cooperativeId;
    @SerializedName("cooperative")
    @Expose
    private String cooperative;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * 
     * @return
     *     The socialTitle
     */
    public String getSocialTitle() {
        return socialTitle;
    }

    /**
     * 
     * @param socialTitle
     *     The social_title
     */
    public void setSocialTitle(String socialTitle) {
        this.socialTitle = socialTitle;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 
     * @param gender
     *     The gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 
     * @return
     *     The civilId
     */
    public String getCivilId() {
        return civilId;
    }

    /**
     * 
     * @param civilId
     *     The civil_id
     */
    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

    /**
     * 
     * @return
     *     The cooperativeId
     */
    public String getCooperativeId() {
        return cooperativeId;
    }

    /**
     * 
     * @param cooperativeId
     *     The cooperative_id
     */
    public void setCooperativeId(String cooperativeId) {
        this.cooperativeId = cooperativeId;
    }

    /**
     * 
     * @return
     *     The cooperative
     */
    public String getCooperative() {
        return cooperative;
    }

    /**
     * 
     * @param cooperative
     *     The cooperative
     */
    public void setCooperative(String cooperative) {
        this.cooperative = cooperative;
    }

    /**
     * 
     * @return
     *     The memberId
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * 
     * @param memberId
     *     The member_id
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 
     * @return
     *     The image
     */
    public Object getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

}
