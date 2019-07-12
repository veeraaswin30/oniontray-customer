
package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IngredTypeList implements Serializable{

    @SerializedName("ingredient_type_id")
    @Expose
    private Integer ingredientTypeId;
    @SerializedName("required")
    @Expose
    private Integer required;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("ingredient_type_name")
    @Expose
    private String ingredientTypeName;
    @SerializedName("ingredient_list")
    @Expose
    private List<IngredientList> ingredientList = null;

    public Integer getIngredientTypeId() {
        return ingredientTypeId;
    }

    public void setIngredientTypeId(Integer ingredientTypeId) {
        this.ingredientTypeId = ingredientTypeId;
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

    public String getIngredientTypeName() {
        return ingredientTypeName;
    }

    public void setIngredientTypeName(String ingredientTypeName) {
        this.ingredientTypeName = ingredientTypeName;
    }

    public List<IngredientList> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientList> ingredientList) {
        this.ingredientList = ingredientList;
    }



    public boolean bool_validation = false;

    public void setBooleanValidation(boolean bool_valid){
        this.bool_validation = bool_valid;
    }

    public boolean getBooleanValidation(){
        return bool_validation;
    }

}
