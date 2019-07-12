package com.app.oniontray.Models;


public class ProductCategories {


    private int Type = 0;

    private String MainSubCategoryId;
    private String CategoryId;
    private String CategoryName;
    private String CategoryUrlKey;

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getMainSubCategoryId() {
        return MainSubCategoryId;
    }

    public void setMainSubCategoryId(String mainSubCategoryId) {
        MainSubCategoryId = mainSubCategoryId;
    }

    public String getCategoryUrlKey() {
        return CategoryUrlKey;
    }

    public void setCategoryUrlKey(String categoryUrlKey) {
        CategoryUrlKey = categoryUrlKey;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }



    private String imageId;
    private int cateImgId;
    private String CateName;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }


    public int getCateImgId() {
        return cateImgId;
    }

    public void setCateImgId(int cateImgId) {
        this.cateImgId = cateImgId;
    }


    public String getCateName() {
        return CateName;
    }

    public void setCateName(String cateName) {
        CateName = cateName;
    }


}
