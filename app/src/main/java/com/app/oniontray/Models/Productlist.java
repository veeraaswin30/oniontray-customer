package com.app.oniontray.Models;


class Productlist {
    private int image;
    private String title;
    private String litre;
    private String  price;
    private int plusimage;
    private int minusimage;
    private int quantity;

    public String getDiscount(String discount) {
        return this.discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    private String discount;



    public int getMinusimage() {
        return minusimage;
    }

    public void setMinusimage(int minusimage) {
        this.minusimage = minusimage;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLitre() {
        return litre;
    }

    public void setLitre(String litre) {
        this.litre = litre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getPlusimage() {
        return plusimage;
    }

    public void setPlusimage(int plusimage) {
        this.plusimage = plusimage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
