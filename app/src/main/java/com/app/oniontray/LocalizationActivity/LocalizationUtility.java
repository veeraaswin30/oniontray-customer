package com.app.oniontray.LocalizationActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

public class LocalizationUtility {
    public static Locale getLocaleFromConfiguration(Configuration configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return configuration.getLocales().get(0);
        } else {
            return configuration.locale;
        }
    }

    public static Context applyLocalizationContext(Context baseContext) {
        Locale baseLocale = LocalizationUtility.getLocaleFromConfiguration(baseContext.getResources().getConfiguration());
        Locale currentLocale = LanguageSetting.getLanguage(baseContext);
        if (!baseLocale.toString().equalsIgnoreCase(currentLocale.toString())) {
            LocalizationContext context = new LocalizationContext(baseContext);
            Configuration config = context.getResources().getConfiguration();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                config.setLocale(currentLocale);
                LocaleList localeList = new LocaleList(currentLocale);
                LocaleList.setDefault(localeList);
                config.setLocales(localeList);
                return context.createConfigurationContext(config);
            } else {
                config.setLocale(currentLocale);
                return context.createConfigurationContext(config);
            }
        } else {
            return baseContext;
        }
    }
}
