package com.app.oniontray.AppControler;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

public class CustomFontStyle {

    public static void replaceDefaultFount(Context context) {

        Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), "FilsonProReg.otf");
        replaceFont("DEFAULT", customFontTypeface);

        Typeface customBoldFontTypeface = Typeface.createFromAsset(context.getAssets(), "FilsonProBold.otf");
        replaceFont("MONOSPACE", customBoldFontTypeface);

    }

    private static void replaceFont(String nameofBeingReplaced, Typeface customFontTypeface) {
        try {
            Field myfield = Typeface.class.getDeclaredField(nameofBeingReplaced);
            myfield.setAccessible(true);
            myfield.set(null, customFontTypeface);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
