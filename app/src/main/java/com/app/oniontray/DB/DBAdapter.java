package com.app.oniontray.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.oniontray.Models.MyFavList;

import java.util.ArrayList;
import java.util.List;


class DBAdapter {
    private final DBHelper helper;

    public DBAdapter(Context context) {
        helper = new DBHelper(context);
    }

    //    insert data
    public void insertData(MyFavList myFavList) {
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put(DBHelper.NAME, myFavList.getMy_Fav_Store_name());
        content.put(DBHelper.DURATION, myFavList.getMy_fav_storeDuration());
        content.put(DBHelper.RATING, myFavList.getMy_fav_storeRating());
        content.put(DBHelper.IMAGE, myFavList.getMy_Fav_Store_Image());
        content.put(DBHelper.OFFER, myFavList.getMy_fav_storeOfferDetail());
        content.put(DBHelper.ITEMS, myFavList.getMy_fav_storeItems());
        //Insert query to store the records
        db.insert(DBHelper.TABLE_NAME, null, content);
        db.close();

    }

    //get All data
    public String getData() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DBHelper.UID, DBHelper.NAME, DBHelper.DURATION};
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int Cid = cursor.getInt(0);
            String name = cursor.getString(1);
            String duration = cursor.getString(2);
            buffer.append(Cid).append("").append(name).append("").append(duration).append("\n");
        }
        return buffer.toString();
    }
//
//
//    public ArrayList<MyFavList> getFavDatalist() {
//        ArrayList<MyFavList> myFavListArrayList=new ArrayList<>();
//        SQLiteDatabase db=helper.getWritableDatabase();
//        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;
//
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()){
//            do {
//                MyFavList myFavList=new MyFavList();
//                int index0=cursor.getColumnIndex(DBHelper.UID);
//                int index1=cursor.getColumnIndex(DBHelper.NAME);
//                int index2=cursor.getColumnIndex(DBHelper.DURATION);
//                int index3=cursor.getColumnIndex(DBHelper.RATING);
//                int index4=cursor.getColumnIndex(DBHelper.IMAGE);
//                int index5=cursor.getColumnIndex(DBHelper.OFFER);
//                int index6=cursor.getColumnIndex(DBHelper.ITEMS);
//                myFavList.setId(Integer.parseInt(String.valueOf(Integer.parseInt(cursor.getString(index0)))));
//                myFavList.setMy_Fav_Store_name(cursor.getString(index1));
//                myFavList.setMy_fav_storeDuration(cursor.getString(index2));
//                myFavList.setMy_fav_storeRating(cursor.getString(index3));
//                myFavList.setMy_Fav_Store_Image(cursor.getBlob(index4));
//                myFavList.setMy_fav_storeOfferDetail(cursor.getString(index5));
//                myFavList.setMy_fav_storeItems(cursor.getString(index6));
//
//                myFavListArrayList.add(myFavList);
//
//            }while (cursor.moveToNext());
//        }
//        db.close();
//return  myFavListArrayList;
//    }


    public List<MyFavList> getFavDatalist() {
        List<MyFavList> myFavListing = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    MyFavList myFavList = new MyFavList();
                    int index0 = cursor.getColumnIndex(DBHelper.UID);
                    int index1 = cursor.getColumnIndex(DBHelper.NAME);
                    int index2 = cursor.getColumnIndex(DBHelper.DURATION);
                    int index3 = cursor.getColumnIndex(DBHelper.RATING);
                    int index4 = cursor.getColumnIndex(DBHelper.IMAGE);
                    int index5 = cursor.getColumnIndex(DBHelper.OFFER);
                    int index6 = cursor.getColumnIndex(DBHelper.ITEMS);
                    myFavList.setId(cursor.getLong(index0));
                    myFavList.setMy_Fav_Store_name(cursor.getString(index1));
                    myFavList.setMy_fav_storeDuration(cursor.getString(index2));
                    myFavList.setMy_fav_storeRating(cursor.getString(index3));
                    myFavList.setMy_Fav_Store_Image(cursor.getBlob(index4));
                    myFavList.setMy_fav_storeOfferDetail(cursor.getString(index5));
                    myFavList.setMy_fav_storeItems(cursor.getString(index6));

                    myFavListing.add(myFavList);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Message.message(helper.context, "" + e);
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return myFavListing;
    }

    //get single user
    public String getsingledata(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {DBHelper.NAME, DBHelper.DURATION};
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, DBHelper.NAME + "=' " + name + " '", null, null, null, null);
        StringBuilder buffer = new StringBuilder();
        while (cursor.moveToNext()) {
            int index0 = cursor.getColumnIndex(DBHelper.UID);
            int index1 = cursor.getColumnIndex(DBHelper.NAME);
            int index2 = cursor.getColumnIndex(DBHelper.DURATION);
            int personCid = cursor.getInt(index0);
            String personname = cursor.getString(index1);
            String personduration = cursor.getString(index2);
            buffer.append(personCid).append("").append(personname).append("").append(personduration).append("\n");
        }
        return buffer.toString();
    }

    //delete data
    public void delete() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereargs = {"dummy"};
        db.delete(DBHelper.TABLE_NAME, DBHelper.NAME + "=?", whereargs);
        db.close();
    }

    //update
    public int updata(String oldname, String newName) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.NAME, oldname);
        String[] whereArgs = {oldname};
        int count = db.update(DBHelper.TABLE_NAME, contentValues, DBHelper.NAME + "=?", whereArgs);
        return count;

    }

    public void removeFromFavorite(MyFavList myFavList) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.UID + " = ? ",
                new String[]{String.valueOf(myFavList.getId())});
        db.close();
    }

    public void deleteItem(long itemId) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, DBHelper.UID + "=?",
                new String[]{String.valueOf(itemId)});
        db.close();
    }

    public void droptable() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String item = "DROP TABLE" + DBHelper.TABLE_NAME;
        try {
            db.execSQL(item);
        } catch (SQLException e) {
            Message.message(helper.context, "drop table called");
        }
    }

    public int gettablecount() {
        String countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "Favorites";
        private static final String TABLE_NAME = "MYFAV";
        private static final int DATABASE_VERSION = 4;
        private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private static final String UID = "id";
        private static final String DURATION = "my_fav_storeDuration";
        private static final String NAME = "My_Fav_Store_name";
        private static final String IMAGE = "My_Fav_Store_Image";
        private static final String ITEMS = "my_fav_storeItems";
        private static final String OFFER = "my_fav_storeOfferDetail";
        private static final String RATING = "my_fav_storeRating";


        private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + DURATION + " TEXT, " + RATING + " TEXT," + IMAGE + " BLOB," + OFFER + " TEXT," + ITEMS + " TEXT " + ");";
        private final Context context;

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Message.message(context, "constructor called");
        }


        //When table is created
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context, "oncreatecalled");
            } catch (SQLException e) {

                Message.message(context, "" + e);
            }

        }

        //upgrade Table
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context, "Upgradecalled");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, "" + e);
            }

        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
