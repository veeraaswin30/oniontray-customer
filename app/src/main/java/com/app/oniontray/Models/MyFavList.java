package com.app.oniontray.Models;


public class MyFavList {
    private long id;
    private String My_Fav_Store_name;
    private String my_fav_storeDuration;
    private String my_fav_storeRating ;
    private byte[] My_Fav_Store_Image;
    private String my_fav_storeOfferDetail;
    private String my_fav_storeItems;

    public MyFavList(String My_Fav_Store_name, String my_fav_storeDuration, String my_fav_storeRating, byte[] My_Fav_Store_Image, String my_fav_storeOfferDetail, String my_fav_storeItems) {
        this.My_Fav_Store_name=My_Fav_Store_name;
        this.my_fav_storeDuration=my_fav_storeDuration;
        this.my_fav_storeRating=my_fav_storeRating;
        this.My_Fav_Store_Image=My_Fav_Store_Image;
        this.my_fav_storeOfferDetail=my_fav_storeOfferDetail;
        this.my_fav_storeItems=my_fav_storeItems;
    }

    public MyFavList(long id, String My_Fav_Store_name, String my_fav_storeDuration, String my_fav_storeRating, byte[] My_Fav_Store_Image, String my_fav_storeOfferDetail, String my_fav_storeItems) {
        this.id=id;
        this.My_Fav_Store_name=My_Fav_Store_name;
        this.my_fav_storeDuration=my_fav_storeDuration;
        this.my_fav_storeRating=my_fav_storeRating;
        this.My_Fav_Store_Image=My_Fav_Store_Image;
        this.my_fav_storeOfferDetail=my_fav_storeOfferDetail;
        this.my_fav_storeItems=my_fav_storeItems;
    }

    public MyFavList() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public byte[] getMy_Fav_Store_Image() {
        return My_Fav_Store_Image;
    }

    public void setMy_Fav_Store_Image(byte[] my_Fav_Store_Image) {
        My_Fav_Store_Image = my_Fav_Store_Image;
    }

    public String getMy_Fav_Store_name() {
        return My_Fav_Store_name;
    }

    public void setMy_Fav_Store_name(String my_Fav_Store_name) {
        My_Fav_Store_name = my_Fav_Store_name;
    }

    public String getMy_fav_storeItems() {
        return my_fav_storeItems;
    }

    public void setMy_fav_storeItems(String my_fav_storeItems) {
        this.my_fav_storeItems = my_fav_storeItems;
    }

    public String getMy_fav_storeOfferDetail() {
        return my_fav_storeOfferDetail;
    }

    public void setMy_fav_storeOfferDetail(String my_fav_storeOfferDetail) {
        this.my_fav_storeOfferDetail = my_fav_storeOfferDetail;
    }

    public String getMy_fav_storeDuration() {
        return my_fav_storeDuration;
    }

    public void setMy_fav_storeDuration(String my_fav_storeDuration) {
        this.my_fav_storeDuration = my_fav_storeDuration;
    }

    public String getMy_fav_storeRating() {
        return my_fav_storeRating;
    }

    public void setMy_fav_storeRating(String my_fav_storeRating) {
        this.my_fav_storeRating = my_fav_storeRating;
    }

}
