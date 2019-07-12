package com.app.oniontray.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.app.oniontray.RequestModels.IngredTypeList;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.RequestModels.SelectedIngredient;

import java.util.ArrayList;


/**
 * Created by nextbrain on 2/6/2017.
 */

public class ProductRespository {

    private final String TAG = ProductRespository.class.getSimpleName().toString();

    private primaryIDCallback mListener;

    public ProductRespository() {

    }

    private Product product;

    private IngredientRepository ingredientRepository = new IngredientRepository();

    public static String createTable() {
        return "CREATE TABLE " + Product.TABLE + "("
                + Product.KEY_RunningID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Product.KEY_ProductId + " TEXT, "
                + Product.KEY_ProductName + " TEXT, "
                + Product.KEY_productUrl + " TEXT, "
                + Product.KEY_weight + " TEXT, "
                + Product.KEY_originalPrice + " TEXT, "
                + Product.KEY_discountPrice + " TEXT, "
                + Product.KEY_unit + " TEXT, "
                + Product.KEY_description + " TEXT, "
                + Product.KEY_title + " TEXT, "
                + Product.KEY_outletName + " TEXT, "
                + Product.KEY_productImage + " TEXT, "
                + Product.KEY_categoryId + " TEXT, "
                + Product.KEY_outlet_id + " TEXT, "
                + Product.KEY_cartCount + " INTEGER, "
                + Product.KEY_Total + " TEXT, "
                + Product.KEY_VendorID + " TEXT, "
                + Product.KEY_averageRating + " TEXT )";
    }
    public boolean checkForTables() {
        boolean hasTables = false;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Product.TABLE, null);

        if (cursor != null && cursor.getCount() > 0) {
            hasTables = true;
            cursor.close();
        }

        return hasTables;
    }


    public void insert(ProductList_Data productList, ArrayList<SelectedIngredient> chooseIngredientLists, String vendor_id, String totalPrice, int ingredientCount) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();


        ContentValues values = new ContentValues();
        values.put(product.KEY_ProductId, productList.getProductId());
        values.put(product.KEY_ProductName, productList.getproductName());
        values.put(product.KEY_productUrl, productList.getProductUrl());
        values.put(product.KEY_weight, productList.getWeight());
        values.put(product.KEY_originalPrice, productList.getOriginalPrice());
        values.put(product.KEY_discountPrice, productList.getDiscountPrice());
        values.put(product.KEY_unit, productList.getUnit());
        values.put(product.KEY_description, productList.getDescription());
        values.put(product.KEY_title, productList.getTitle());
        values.put(product.KEY_outletName, productList.getOutletName());
        values.put(product.KEY_productImage, productList.getProductImage());
        values.put(product.KEY_categoryId, productList.getCategoryId());
        values.put(product.KEY_outlet_id, productList.getOutletId());
        values.put(product.KEY_cartCount, productList.getCartCount());
        values.put(product.KEY_Total, totalPrice);
        values.put(product.KEY_VendorID, vendor_id);
        values.put(product.KEY_averageRating, productList.getAverageRating());

// Inserting Row
        db.insert(Product.TABLE, null, values);
        Cursor cursor = null;
        int runningID = 0;
        String sql = "SELECT MAX( " + product.KEY_RunningID + " ) FROM " + Product.TABLE;
        cursor = db.rawQuery(sql, null);

        cursor.moveToFirst();
// Log.e("id", "" + cursor.getInt(0));
        runningID = cursor.getInt(0);
        cursor.close();

        if (mListener != null) {
            mListener.setPrimaryID(runningID);
        }

        if (chooseIngredientLists.size() != 0) {

            ingredientRepository.insert(productList.getProductId(), chooseIngredientLists, runningID);
        }

        DatabaseManager.getInstance().closeDatabase();

    }


    public void insertProductData(ProductList_Data productList, int cartCount, ArrayList<SelectedIngredient> chooseIngredientLists, String vendor_id, String totalPrice, int ingredientCount) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();


        int runningID = 0;
        int cursorCount = 0;
        int ingredient_count = 0;

        String id = "SELECT * FROM " + Product.TABLE + " WHERE ProductId=" + productList.getProductId();
        Cursor cursorForProduct = db.rawQuery(id, null);
//        Log.e("product count", "" + cursorForProduct.getCount());


        if (cursorForProduct.getCount() > 0) {

            if (cursorForProduct != null) {
                if (cursorForProduct.moveToFirst()) {
                    do {

                        cursorCount = 0;

                        ingredient_count = 0;

                        runningID = Integer.parseInt(cursorForProduct.getString(cursorForProduct.getColumnIndex(Product.KEY_RunningID)));
                        int dbIngredientCount = getIngredientCount(runningID);

                        if (dbIngredientCount == 0 && chooseIngredientLists.size() == 0) {
                            if (mListener != null) {
                                mListener.setPrimaryID(runningID);
                            }
                            update(runningID);
                            return;
                        } else if (dbIngredientCount == ingredientCount) {
                            for (int i = 0; i < chooseIngredientLists.size(); i++) {


                                ingredient_count += chooseIngredientLists.get(i).getIngredientLists().size();

                                for (int j = 0; j < chooseIngredientLists.get(i).getIngredientLists().size(); j++) {

                                    String ingredient = "SELECT * FROM " + Ingredient.TABLE + " WHERE " + Ingredient.KEY_RunningID + " = " + runningID + " AND " + Ingredient.KEY_IngredientId + " = " + chooseIngredientLists.get(i).getIngredientLists().get(j).getIngredientId();
                                    Cursor cursorIngredient = db.rawQuery(ingredient, null);
                                    cursorCount += cursorIngredient.getCount();

                                }

                            }


                            if (cursorCount == dbIngredientCount) {

                                if (mListener != null) {
                                    mListener.setPrimaryID(runningID);
                                }
                                updateBasedOnPrimaryID("" + runningID);
                                return;
                            }
                        }


                    } while (cursorForProduct.moveToNext());

                }
            }
        }


        productList.setCartCount(cartCount);
        insert(productList, chooseIngredientLists, vendor_id, totalPrice, ingredientCount);


        DatabaseManager.getInstance().closeDatabase();
    }



    public int getProductsListCount() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String countQuery = "SELECT * FROM " + Product.TABLE;
        int count = 0;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.getCount() > 0) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public int getCartCount() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT SUM(" + product.KEY_cartCount + ") FROM " + Product.TABLE;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }

    public String totalPrice() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String total_query = "SELECT SUM(" + product.KEY_cartCount + " * " + product.KEY_Total + ") AS result FROM " + Product.TABLE;
// String total_query = "SELECT SUM(" + product.KEY_Total + ") FROM " + Product.TABLE;
// Log.e("query", total_query);
        Cursor cursor = db.rawQuery(total_query, null);
// Log.e("totalCount", "" + cursor.getCount());

        cursor.moveToFirst();
        String totalPrice = cursor.getString(0);
// Log.e("totalPrice", "" + totalPrice);
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return totalPrice;
    }

    public int getSubTotalPrice() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String total_query = "SELECT SUM(" + product.KEY_cartCount + " * " + product.KEY_Total + ") AS result FROM " + Product.TABLE;
        Cursor cursor = db.rawQuery(total_query, null);
//        Log.e("totalCount", "" + cursor.getCount());

        cursor.moveToFirst();
        int totalPrice = cursor.getInt(0);
//        Log.e("subtotalPrice", "" + totalPrice);
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return totalPrice;
    }

    public ArrayList<ProductList_Data> getCartProductList() {
        ArrayList<ProductList_Data> productListDataList = new ArrayList<>();
        ArrayList<IngredTypeList> ingredTypeListsArray;

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Product.TABLE;
        Cursor cursor = db.rawQuery(query, null);
//        Log.e("size", "" + cursor.getCount());
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ProductList_Data productList = new ProductList_Data();

                ingredTypeListsArray = new ArrayList<>();


                int runningID = cursor.getInt(cursor.getColumnIndex(product.KEY_RunningID));
                productList.setPrimaryID(runningID);
                productList.setProductId(cursor.getInt(cursor.getColumnIndex(product.KEY_ProductId)));
                productList.setproductName(cursor.getString(cursor.getColumnIndex(product.KEY_ProductName)));
                productList.setProductUrl(cursor.getString(cursor.getColumnIndex(product.KEY_productUrl)));
                productList.setWeight(cursor.getString(cursor.getColumnIndex(product.KEY_weight)));
                productList.setOriginalPrice(cursor.getString(cursor.getColumnIndex(product.KEY_originalPrice)));
                productList.setDiscountPrice(cursor.getString(cursor.getColumnIndex(product.KEY_discountPrice)));
                productList.setUnit(cursor.getString(cursor.getColumnIndex(product.KEY_unit)));
                productList.setDescription(cursor.getString(cursor.getColumnIndex(product.KEY_description)));
                productList.setTitle(cursor.getString(cursor.getColumnIndex(product.KEY_title)));
                productList.setOutletName(cursor.getString(cursor.getColumnIndex(product.KEY_outletName)));
                productList.setOutletId(cursor.getInt(cursor.getColumnIndex(product.KEY_outlet_id)));
                productList.setProductImage(cursor.getString(cursor.getColumnIndex(product.KEY_productImage)));
                productList.setCategoryId(""+cursor.getInt(cursor.getColumnIndex(product.KEY_categoryId)));
                productList.setCartCount(cursor.getInt(cursor.getColumnIndex(product.KEY_cartCount)));
                productList.setTotal(cursor.getString(cursor.getColumnIndex(product.KEY_Total)));
                productList.setAverageRating(cursor.getInt(cursor.getColumnIndex(product.KEY_averageRating)));


                String queryForIngredient = "SELECT * FROM " + Ingredient.TABLE + " WHERE " + Ingredient.KEY_RunningID + " = " + runningID;
                Cursor cursorIngredient = db.rawQuery(queryForIngredient, null);
//                Log.e("size", "" + cursorIngredient.getCount());

                if (cursorIngredient.getCount() > 0) {
                    if (cursorIngredient.moveToFirst()) {

                        IngredTypeList ingredTypeList = null, previousIngredientTypeList = null;
                        String firstTypeID, previousTypeID = null;
                        ArrayList<IngredientList> ingredientListsArray = new ArrayList<>();

                        do {
//                Ingredient Table

                            firstTypeID = cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientTypeId));


                            if (!firstTypeID.equals(previousTypeID)) {

                                if (ingredientListsArray.size() != 0 && ingredTypeList != null) {
                                    ingredTypeList.setIngredientList(ingredientListsArray);
                                    ingredTypeListsArray.add(ingredTypeList);
                                    ingredientListsArray = new ArrayList<>();

                                }

                                ingredTypeList = new IngredTypeList();

                                ingredTypeList.setIngredientTypeName(cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientTypeName)));
                                ingredTypeList.setIngredientTypeId(cursorIngredient.getInt(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientTypeId)));
                                ingredTypeList.setRequired(cursorIngredient.getInt(cursorIngredient.getColumnIndex(Ingredient.KEY_ingredientRequired)));
                                ingredTypeList.setType(cursorIngredient.getInt(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientType)));


                            }

                            IngredientList ingredientList = new IngredientList();
                            ingredientList.setIngredientId(cursorIngredient.getInt(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientId)));

                            ingredientList.setIngredientName(cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientName)));
                            ingredientList.setPrice(cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientPrice)));

                            ingredientListsArray.add(ingredientList);

//                            Log.e("last", "" + cursorIngredient.isLast());

                            if (cursorIngredient.isLast()) {

                                ingredTypeList.setIngredientList(ingredientListsArray);
                                ingredTypeListsArray.add(ingredTypeList);

                            }

                            previousTypeID = firstTypeID;

//                            Log.e("1", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientTypeName)));
//                            Log.e("2", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientTypeId)));
//                            Log.e("3", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_ingredientRequired)));
//                            Log.e("4", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientType)));
//                            Log.e("5", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientId)));
//                            Log.e("6", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientName)));
//                            Log.e("7", cursorIngredient.getString(cursorIngredient.getColumnIndex(Ingredient.KEY_IngredientPrice)));

                        } while (cursorIngredient.moveToNext());
                    }
                }

                cursorIngredient.close();

                productList.setIngredTypeList(ingredTypeListsArray);
                productListDataList.add(productList);


            } while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

//        Log.e("count", "" + productListDataList.size());
        return productListDataList;

    }

    public void update(int productID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();


        String sql = "UPDATE " + Product.TABLE + " SET " + product.KEY_cartCount + " = " + product.KEY_cartCount + " + 1 WHERE " + Product.KEY_RunningID + " = " + productID;


        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();

        DatabaseManager.getInstance().closeDatabase();


    }

    public void updateBasedOnPrimaryID(String pID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();


        String sql = "UPDATE " + Product.TABLE + " SET " + product.KEY_cartCount + " = " + product.KEY_cartCount + " + 1 WHERE " + Product.KEY_RunningID + " = " + pID;


        Cursor cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();

        DatabaseManager.getInstance().closeDatabase();


    }

    public void DecreaseCartBasedOnPrimaryID(String pID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = null;
        String sql = "UPDATE " + Product.TABLE + " SET " + product.KEY_cartCount + " = " + product.KEY_cartCount + " - 1 WHERE " + Product.KEY_RunningID + " = " + pID;

        cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        deleteRecord();
        DatabaseManager.getInstance().closeDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put(product.KEY_cartCount, cartCount);
//
//
//        String[] whereArgs = {pID};
//
//        // Inserting Row
//        db.update(Product.TABLE, values, Product.KEY_RunningID + "=?", whereArgs);
//        DatabaseManager.getInstance().closeDatabase();


    }

    //---Get All Contacts from table in SQLite DB---
    public boolean getOutLetID(String outletID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        Cursor cursor = null;
        String sql = "SELECT * FROM " + Product.TABLE + " WHERE outlet_id=" + outletID;
        cursor = db.rawQuery(sql, null);
        int count = cursor.getCount();
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }

    public String getOutletName(String outletID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();

        String outlet_name = "";

        Cursor cursor = null;
        String sql = "SELECT " + product.KEY_outletName + " FROM " + Product.TABLE;
        cursor = db.rawQuery(sql, null);

//        Log.e("getCount", "-" + cursor.getCount());

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            outlet_name = cursor.getString(cursor.getColumnIndex(Product.KEY_outletName));
//            Log.e("outlet_name", "-" + outlet_name);
        }

        cursor.close();
        DatabaseManager.getInstance().closeDatabase();

        return outlet_name;
    }

    public ArrayList<String> getVendorID() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String vendorID = null;
        String outletID = null;
        ArrayList<String> outletAndVendorID = new ArrayList<>();
        Cursor cursor = null;
        String sql = "SELECT * FROM " + Product.TABLE;
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    outletID = cursor.getString(cursor.getColumnIndex(Product.KEY_outlet_id));
                    vendorID = cursor.getString(cursor.getColumnIndex(Product.KEY_VendorID));
                } while (cursor.moveToNext());

            }
            outletAndVendorID.add(vendorID);
            outletAndVendorID.add(outletID);
        }
        DatabaseManager.getInstance().closeDatabase();

        return outletAndVendorID;

    }

    private int getIngredientCount(int runningID) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT COUNT(" + Ingredient.KEY_RunningID + ") FROM " + Ingredient.TABLE + " WHERE " + Ingredient.KEY_RunningID + " = " + runningID;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        DatabaseManager.getInstance().closeDatabase();
        return count;
    }

    private void deleteProduct(String s) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Product.TABLE + " WHERE " + product.KEY_ProductId + "=" + s;
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

    private void deleteRecord() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Product.TABLE + " WHERE " + product.KEY_cartCount + "=" + 0;
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

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Product.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void deleteBasedOnPrimaryID(String s) {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Product.TABLE + " WHERE " + product.KEY_RunningID + "=" + s;
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

    public void ClearCart() {

        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String sql = "DELETE FROM " + Product.TABLE;
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

        ingredientRepository.ClearIngredient();

    }

    public void setListener(primaryIDCallback listener) {
        mListener = listener;
    }

    public interface primaryIDCallback {
        public void setPrimaryID(int primaryID);
    }


}


