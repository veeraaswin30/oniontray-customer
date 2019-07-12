package com.app.oniontray.RequestModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/8/2017.
 */

public class SelectedIngredient implements Serializable {

    public int ingredient_type_id;

    public int required;

    public int type;

    public String ingredient_type_name = "";

    public ArrayList<IngredientList> ingredientLists;



    public void setIngredientTypeId(int ingredient_type_id){
        this.ingredient_type_id = ingredient_type_id;
    }

    public int getIngredientTypeId(){
        return ingredient_type_id;
    }


    public void setRequired(int required){
        this.required = required;
    }

    public int getRequired(){
        return required;
    }


    public void setType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }


    public void setIngredientTypeName(String ingredient_type_name){
        this.ingredient_type_name = ingredient_type_name;
    }

    public String getIngredientTypeName(){
        return ingredient_type_name;
    }


    public void setIngredientLists(ArrayList<IngredientList> ingredientLists){
        this.ingredientLists = ingredientLists;
    }

    public ArrayList<IngredientList> getIngredientLists(){
        return ingredientLists;
    }


}
