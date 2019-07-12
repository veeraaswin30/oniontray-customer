
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReOrdIngredientList {

    @SerializedName("ingredient_id")
    @Expose
    private Integer ingredientId;
    @SerializedName("ingredient_name")
    @Expose
    private String ingredientName;
    @SerializedName("ingredient_type")
    @Expose
    private Integer ingredientType;
    @SerializedName("ingredient_type_name")
    @Expose
    private String ingredientTypeName;
    @SerializedName("special_req")
    @Expose
    private String specialReq;
    @SerializedName("price")
    @Expose
    private String ingredientPrice;
    @SerializedName("required")
    @Expose
    private Integer required;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Integer getIngredientType() {
        return ingredientType;
    }

    public void setIngredientType(Integer ingredientType) {
        this.ingredientType = ingredientType;
    }

    public String getIngredientTypeName() {
        return ingredientTypeName;
    }

    public void setIngredientTypeName(String ingredientTypeName) {
        this.ingredientTypeName = ingredientTypeName;
    }

    public String getSpecialReq() {
        return specialReq;
    }

    public void setSpecialReq(String specialReq) {
        this.specialReq = specialReq;
    }

    public String getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(String ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
