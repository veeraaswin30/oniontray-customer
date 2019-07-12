package com.app.oniontray.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;

import android.text.Html;
import android.text.Spanned;

import com.app.oniontray.Activites.OffersActivity;
import com.app.oniontray.Activites.SplashScreen;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.R;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;


public class LoginPrefManager {

    private final Context _context;

    private SharedPreferences pref;
    private Editor editor;

    private static final String PREF_NAME = "AndroidOddappzFoodPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String Check_login = "0";
    private static JSONObject jsonObject;


    private DecimalFormat decimal_format = new DecimalFormat("0.0");


    public LoginPrefManager(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

        editor.apply();
    }

    public void setLoginPrefData(String text, String data) {
        editor.putString(text, data).commit();
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(Check_login, "1").commit();
        editor.commit();
    }

    public void setPrefData(String text, String data) {
        editor.putString(text, data).commit();
        editor.commit();
    }

    public String getPrefData(String text) {
        return pref.getString(text, "");
    }

    public Editor getEditor() {
        return editor;
    }

    public SharedPreferences getShaPref() {
        return pref;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public void setMyPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public void setIntValue(String keyName, int value) {
        pref.edit().putInt(keyName, value).apply();
    }

    public int getIntValue(String keyName) {
        return pref.getInt(keyName, 0);
    }

    public void setStringValue(String keyName, String value) {
        pref.edit().putString(keyName, value).apply();
    }

    public String getStringValue(String keyName) {
        return pref.getString(keyName, "");
    }
    public void setBooleanValue(String keyName, boolean value) {
        pref.edit().putBoolean(keyName, value).apply();
    }

    public Boolean getBooleanValue(String keyName) {
        return pref.getBoolean(keyName, false);
    }

    public void remove(String key) {
        pref.edit().remove(key).apply();
    }

    public boolean clear() {
        return pref.edit().clear().commit();
    }


    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkPrefLogin() {
        if (!this.isLoggedIn()) {
            Intent i = new Intent(_context, SplashScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        editor.clear();
        editor.putBoolean(IS_LOGIN, false);
        editor.commit();
        Intent logi = new Intent(_context, SplashScreen.class);
        logi.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        logi.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(logi);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    private boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public static void setJsonObject(JSONObject jsonObject) {
        LoginPrefManager.jsonObject = jsonObject;
    }

    public static JSONObject getJsonObject() {
        return jsonObject;
    }

    // Init Default Language
    public void setDefaultLang(String defa_lang) {
        pref.edit().putString("default_lang", defa_lang).apply();
    }

    public String getDefaultLang() {
        return pref.getString("default_lang", "");
    }


    //Pic a Country details
    public void setCountryIDandName(String Country_Id, String Country_Name) {
        pref.edit().putString("Pic_Country_Id", Country_Id).apply();
        pref.edit().putString("Pic_Country_Name", Country_Name).apply();
    }

    public String getCountryID() {
        return pref.getString("Pic_Country_Id", "");
    }

    public String getCountryName() {
        return pref.getString("Pic_Country_Name", "");
    }


    //Pic a City details
    public void setCityIDandName(String City_Id, String City_Name) {
        pref.edit().putString("Pic_City_Id", City_Id).apply();
        pref.edit().putString("Pic_City_Name", City_Name).apply();
    }

    public String getCityID() {
        return pref.getString("Pic_City_Id", "");
    }

    public String getCityName() {
        return pref.getString("Pic_City_Name", "");
    }

    // Pic a Location Details
    public void setLocIDandName(String Loc_Id, String Loc_Name) {
        pref.edit().putString("Pic_loc_id", Loc_Id).apply();
        pref.edit().putString("Pic_loc_name", Loc_Name).apply();
    }

    public String getLocID() {
        return pref.getString("Pic_loc_id", "");
    }

    public String getLocName() {
        return pref.getString("Pic_loc_name", "");
    }

    public String getCurrencySide() {
        return pref.getString("currency_side", "");
    }

    public String getCurrencySymbole() {
        return pref.getString("currency_symbol", "");
    }

    public String getCurrencyName() {
        return pref.getString("currency_name", "");
    }

    public String getCurrencyCode(){
        return pref.getString("currency_code", "");
    }


    public void LogOutClearDataMethod() {

        pref.edit().putString("login_email", "").apply();
        pref.edit().putString("login_password", "").apply();
        pref.edit().putString("user_id", "").apply();
        pref.edit().putString("user_token", "").apply();
        pref.edit().putString("user_email", "").apply();
        pref.edit().putString("user_name", "").apply();
        pref.edit().putString("user_social_title", "").apply();
        pref.edit().putString("user_first_name", "").apply();
        pref.edit().putString("user_last_name", "").apply();
        pref.edit().putString("user_image", "").apply();

    }



    public void setThemeListID(String theme_id) {
        pref.edit().putString("id", theme_id).apply();
    }

    public String getThemeListID() {
        return pref.getString("theme_id", "");
    }

    public void setThemeColor(String theme_color) {
        pref.edit().putString("theme_color", theme_color).apply();
    }

    public String getThemeColor() {
        return pref.getString("theme_color", "");
    }
    public void setThemeFontColor(String theme_fontcolor) {
        pref.edit().putString("font_color", theme_fontcolor).apply();
    }

    public String getThemeFontColor() {
        return pref.getString("font_color", "");
    }
    public void setThemeStatusbarColor(String theme_statusbarcolor) {
        pref.edit().putString("status_bar_color", theme_statusbarcolor).apply();
    }


    public String getThemeStatusbarColor() {
        return pref.getString("status_bar_color", "");
    }
    public String getToolbarIconcolor() {
        return pref.getString("tool_bar_icon", "");
    }
    public void setToolbarIconcolor(String theme_toolbarcolor) {
        pref.edit().putString("tool_bar_icon", theme_toolbarcolor).apply();
    }

    public void setThemeColorAccent(String theme_coloraccent) {
        pref.edit().putString("color_acceent", theme_coloraccent).apply();
    }

    public String getThemeColorAccent() {
        return pref.getString("color_acceent", "");
    }

    public void setThemeType(String theme_type) {
        pref.edit().putString("type", theme_type).apply();
    }

    public String getThemeType() {
        return pref.getString("type", "");
    }




    private boolean getAmountSpace(){
        return pref.getBoolean("cur_symbol_space", true);
    }

    public Spanned getFormatCurrencyValue(String Currency) {

        if(getAmountSpace()){

            if (getCurrencySide().equals("1")) {
                return Html.fromHtml(String.format(_context.getString(R.string.right_amount_space), "" + getCurrencySymbole(), "" + Currency));
            } else {
                return Html.fromHtml(String.format(_context.getString(R.string.amount_space), "" + Currency, "" + getCurrencySymbole()));
            }

        }else{

            if (getCurrencySide().equals("1")) {
                return Html.fromHtml(String.format(_context.getString(R.string.right_amount), "" + getCurrencySymbole(), "" + Currency));
            } else {
                return Html.fromHtml(String.format(_context.getString(R.string.amount), "" + Currency, "" + getCurrencySymbole()));
            }
        }

    }

    public Spanned getFormatCurrencyValueClosed(String Currency) {

        if(getAmountSpace()){

            if (getCurrencySide().equals("1")) {
                return Html.fromHtml(String.format(_context.getString(R.string.closed_amount_space), "" + getCurrencySymbole(), "" + Currency));
            } else {
                return Html.fromHtml(String.format(_context.getString(R.string.closed_amount_space), "" + Currency, "" + getCurrencySymbole()));
            }

        }else{

            if (getCurrencySide().equals("1")) {
                return Html.fromHtml(String.format(_context.getString(R.string.closed_amount), "" + getCurrencySymbole(), "" + Currency));
            } else {
                return Html.fromHtml(String.format(_context.getString(R.string.closed_amount), "" + Currency, "" + getCurrencySymbole()));
            }

        }

    }

    public String getCurrecncyWithDynamicColor(String color,String amount){
        if (getCurrencySide().equals("1")) {
            return String.format("<font color='%s'>%s%s</font>", "" + color, "" + getCurrencySymbole(), "" + amount);
        }else{
            return String.format("<font color='%s'>%s%s</font>", "" + color, "" + getCurrencySymbole(), "" + amount);

        }
    }

    public String getdeltimeWithDynamicColor(String color,String mins){

            return String.format("<font color='%s'>%s</font>", "" + color,"" + mins);


    }




    public String getDecimalRattingValue(Integer integer) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("0.0");
        return df.format(integer);
    }

    public String GetNuberstringFormatValue(String value){
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        try {
            return String.valueOf(nf.parse(value));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearCartForOtherLocation(String locID) {
        if (!getLocID().equals(locID)) {
            ProductRespository productRespository = new ProductRespository();
            productRespository.ClearCart();
        }
    }

    public String GetEngDecimalFormatValues(float value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("##0.000");
        return df.format(value);
    }

    public Drawable getbackbtncolor(OffersActivity context, String color, Drawable drawable){
        drawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);
        return drawable;
    }
    public Drawable changeColor(Context context, String color, int drawable){
        Drawable changedDrawable = ContextCompat.getDrawable(context,
                drawable);
        changedDrawable.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC_ATOP);

        return changedDrawable;
    }



}
