package com.app.oniontray.Activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Language;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.Webdata;


public class AboutUs extends LocalizationActivity {

    private Toolbar toolbar;
    private WebView about_us_text;

    private TextView aboutas_title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_page_lay);

        aboutas_title = findViewById(R.id.my_addr_title_txt1);
        aboutas_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        toolbar = (Toolbar) findViewById(R.id.about_us_toolbar_id);
//        toolbar.setTitle(getString(R.string.aboutustitle));
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        String language = String.valueOf(LanguageSetting.getLanguage(AboutUs.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        about_us_text = (WebView) findViewById(R.id.about_us_text_desc_one);
        about_us_text.setBackgroundColor(Color.TRANSPARENT);

        about_us_text.setWebViewClient(new WebViewClient());
        WebSettings settings = about_us_text.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);


        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

//        about_us_text.loadUrl("" + Webdata.aboutUS_Url);

        String about_us_url = String.format("%s/%s", "" + Webdata.aboutUS_Url, "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("about_us_url", "" + about_us_url);
        about_us_text.loadUrl("" + about_us_url);


//        messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getMessageTime()));


    }


//    private void TestMethod(){
//
//    }
//
//    public class TestInterfaceClass implements SignInVerifyDialog.SignInVerifyInterface{
//
//        @Override
//        public void SentOTPSuccessMethod() {
//            TestMethod();
//        }
//
//    }


}
