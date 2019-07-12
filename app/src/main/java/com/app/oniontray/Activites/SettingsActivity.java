package com.app.oniontray.Activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;

/**
 * Created by nextbrain on 28/3/17.
 */

public class SettingsActivity extends LocalizationActivity {


    private Toolbar menu_accounts_toolbar;

    private RadioGroup sett_lang_radio_group;
    private RadioButton sett_eng_radio_btn;
    private RadioButton sett_arabic_radio_btn;

    private TextView sett_country_txt_view;
    private TextView setting_title;


    private BroadcastReceiver settings_broBroadcastReceiver;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        menu_accounts_toolbar = (Toolbar) findViewById(R.id.my_settings_toolbar);
        menu_accounts_toolbar.setTitle("");

        menu_accounts_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        menu_accounts_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(menu_accounts_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
        String language = String.valueOf(LanguageSetting.getLanguage(SettingsActivity.this));


        if (language.equals("en")) {
           // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
          //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        menu_accounts_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        settings_broBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        LocalBroadcastManager.getInstance(SettingsActivity.this).registerReceiver(settings_broBroadcastReceiver, new IntentFilter("settings_receiver"));

        Init();

    }

    private void Init() {
        ColorStateList colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {

                        Color.BLACK //disabled
                        ,Color.parseColor(loginPrefManager.getThemeFontColor()) //enabled

                }
        );

        sett_lang_radio_group = (RadioGroup) findViewById(R.id.sett_lang_radio_group);
        sett_eng_radio_btn = (RadioButton) findViewById(R.id.sett_eng_radio_btn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sett_eng_radio_btn.setButtonTintList(colorStateList);
        }
        sett_eng_radio_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        sett_arabic_radio_btn = (RadioButton) findViewById(R.id.sett_arabic_radio_btn);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sett_arabic_radio_btn.setButtonTintList(colorStateList);
        }
        setting_title = (TextView) findViewById(R.id.setting_title);
        setting_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        sett_country_txt_view = (TextView) findViewById(R.id.sett_country_txt_view);
        sett_country_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        sett_country_txt_view.setText("" + loginPrefManager.getCountryName());


        sett_lang_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {

                    case R.id.sett_eng_radio_btn:

                        if (!loginPrefManager.getStringValue("Lang").equals("en")) {
                            setLanguage("en");

                            loginPrefManager.setStringValue("Lang", "en");
                            loginPrefManager.setStringValue("Lang_code", "" + loginPrefManager.getStringValue("en_Id"));
                            loginPrefManager.setCountryIDandName("", "");
                            loginPrefManager.setCityIDandName("", "");
                            loginPrefManager.setLocIDandName("", "");


                            Intent splashIntent = new Intent(SettingsActivity.this, SplashScreen.class);
                            splashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(splashIntent);
                        }
                        break;

                    case R.id.sett_arabic_radio_btn:
                        if (!loginPrefManager.getStringValue("Lang").equals("ar")) {

                            setLanguage("ar");

                            loginPrefManager.setStringValue("Lang", "ar");
                            loginPrefManager.setStringValue("Lang_code", "" + loginPrefManager.getStringValue("ar_Id"));
                            loginPrefManager.setCountryIDandName("", "");
                            loginPrefManager.setCityIDandName("", "");
                            loginPrefManager.setLocIDandName("", "");
                            Intent splashIntent = new Intent(SettingsActivity.this, SplashScreen.class);
                            splashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(splashIntent);
                        }
                        break;

                }

            }
        });

        sett_country_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent country_Intent = new Intent(SettingsActivity.this, SelectCountryActivity.class);
                country_Intent.putExtra("wel_loc_flow","1");
//                country_Intent.putExtra("settings_activity", "1");
                startActivity(country_Intent);
            }
        });

        SetNavMenuLanguage();

    }

    private void SetNavMenuLanguage() {

        if (loginPrefManager.getStringValue("Lang").equals("en")) {
            sett_eng_radio_btn.setChecked(true);
        } else if (loginPrefManager.getStringValue("Lang").equals("ar")) {
            sett_arabic_radio_btn.setChecked(true);
        }

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(SettingsActivity.this).unregisterReceiver(settings_broBroadcastReceiver);
    }


}
