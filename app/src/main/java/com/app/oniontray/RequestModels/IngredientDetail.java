package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nextbrain on 29/3/17.
 */

public class IngredientDetail {

    @SerializedName("ingredient_price")
    @Expose
    private String ingredientPrice;
    @SerializedName("ingredient_name")
    @Expose
    private String ingredientName;

    public String getIngredientPrice() {
        return ingredientPrice;
    }

    public void setIngredientPrice(String ingredientPrice) {
        this.ingredientPrice = ingredientPrice;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

}
