package com.app.oniontray.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.RequestModels.SelectedIngredient;

import java.util.ArrayList;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by nextbrain on 2/6/2017.
 */

public class IngredientRepository {

    private Ingredient ingredient;

    public IngredientRepository() {
    }


    public static String createTable() {
        return "CREATE TABLE " + Ingredient.TABLE + "("
                + Ingredient.KEY_RunningID + " TEXT,"
                + Ingredient.KEY_ProductId + " TEXT,"
                + Ingredient.KEY_IngredientId + " TEXT, "
                + Ingredient.KEY_IngredientName + " TEXT, "
                + Ingredient.KEY_IngredientTypeId + " TEXT, "
                + Ingredient.KEY_IngredientType + " TEXT, "
                + Ingredient.KEY_ingredientRequired + " TEXT, "
                + Ingredient.KEY_IngredientTypeName + " TEXT, "
                + Ingredient.KEY_IngredientPrice + " TEXT )";
    }


    public void insert(ProductList_Data productListData) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Ingredient.KEY_ProductId, productListData.getProductId());
//        values.put(Ingredient.KEY_IngredientId, productListData.getIngredTypeList().get());
//        values.put(Ingredient.KEY_IngredientName, Ingredient.getIngredientName());
//        values.put(Ingredient.KEY_IngredientPrice, Ingredient.getIngredientName());

        // Inserting Row
        db.insert(Ingredient.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }


    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Ingredient.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


    public void insert(int productId, ArrayList<ArrayList<IngredientList>> chooseIngredientLists) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < chooseIngredientLists.size(); i++) {
            for (int j = 0; j < chooseIngredientLists.get(i).size(); j++) {
                values.put(Ingredient.KEY_ProductId, productId);
                values.put(Ingredient.KEY_IngredientId, chooseIngredientLists.get(i).get(j).getIngredientId());
                values.put(Ingredient.KEY_IngredientName, chooseIngredientLists.get(i).get(j).getIngredientName());
                values.put(Ingredient.KEY_IngredientPrice, chooseIngredientLists.get(i).get(j).getPrice());

                // Inserting Row
                db.insert(Ingredient.TABLE, null, values);
            }
        }

        DatabaseManager.getInstance().closeDatabase();
    }

    public void insertIngredientData(ProductList_Data productList, int cartCount) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = null;
        String sql = "SELECT * FROM " + Product.TABLE + " WHERE ProductId=" + productList.getProductId();
        cursor = db.rawQuery(sql, null);
//        Log.e("cursor", "" + cursor.getCount());

        if (cursor.getCount() > 0) {

            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    do {
//                        cartCount = Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.KEY_cartCount)));
//                    } while (cursor.moveToNext());
//                }

//                if(operator.equals("+"))
//                {
//                    cartCount += 1;
//                }else
//                {
//                    cartCount -= 1;
//                }
//                price = Integer.parseInt(productList.getDiscountPrice()) * cartCount;
//                Log.e("count", "" + cartCount);
                if (cartCount == 0) {
                    deleteProduct("" + productList.getProductId());

                } else {
                    update(cartCount, "" + productList.getProductId());
                }
            }


        } else {
            productList.setCartCount(cartCount);
            insert(productList);
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    public void update(int cartCount, String productID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();


        ContentValues values = new ContentValues();

//        values.put(product.KEY_cartCount, cartCount);


        String[] whereArgs = {productID};

        // Inserting Row
        db.update(Product.TABLE, values, Product.KEY_ProductId + "=?", whereArgs);
        DatabaseManager.getInstance().closeDatabase();


    }

    private void deleteProduct(String s) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Ingredient.TABLE + " WHERE " + ingredient.KEY_IngredientId + "=" + s;
        try {
            db.beginTransaction();

            Log.d(TAG, sql);

            db.execSQL(sql);

            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    public void insert(int productId, ArrayList<SelectedIngredient> chooseIngredientLists, int id) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();

        for (int i = 0; i < chooseIngredientLists.size(); i++) {
            for (int j = 0; j < chooseIngredientLists.get(i).getIngredientLists().size(); j++) {
                values.put(Ingredient.KEY_RunningID, id);
                values.put(Ingredient.KEY_ProductId, productId);
                values.put(Ingredient.KEY_IngredientId, chooseIngredientLists.get(i).getIngredientLists().get(j).getIngredientId());
                values.put(Ingredient.KEY_IngredientName, chooseIngredientLists.get(i).getIngredientLists().get(j).getIngredientName());
                values.put(Ingredient.KEY_IngredientPrice, chooseIngredientLists.get(i).getIngredientLists().get(j).getPrice());
                values.put(Ingredient.KEY_IngredientType, chooseIngredientLists.get(i).getType());
                values.put(Ingredient.KEY_IngredientTypeId, chooseIngredientLists.get(i).getIngredientTypeId());
                values.put(Ingredient.KEY_ingredientRequired, chooseIngredientLists.get(i).getRequired());
                values.put(Ingredient.KEY_IngredientTypeName, chooseIngredientLists.get(i).getIngredientTypeName());
//                values.put(Ingredient.KEY_IngredientCount,chooseIngredientLists.get(i).getIngredientTypeName());

                // Inserting Row
                db.insert(Ingredient.TABLE, null, values);
            }
        }

        DatabaseManager.getInstance().closeDatabase();
    }


    public void deleteBasedOnPrimaryID(String s) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Ingredient.TABLE + " WHERE " + ingredient.KEY_RunningID + "=" + s;
        try {
            db.beginTransaction();

            Log.d(TAG, sql);

            db.execSQL(sql);

            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
        DatabaseManager.getInstance().closeDatabase();

    }


    public void ClearIngredient() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Ingredient.TABLE;
        try {
            db.beginTransaction();

            Log.d(TAG, sql);

            db.execSQL(sql);

            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
        DatabaseManager.getInstance().closeDatabase();

    }

}

