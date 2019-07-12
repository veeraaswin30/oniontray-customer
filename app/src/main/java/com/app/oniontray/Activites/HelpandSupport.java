package com.app.oniontray.Activites;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.Webdata;



public class HelpandSupport extends LocalizationActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support_page_lay);

        Toolbar toolbar = (Toolbar) findViewById(R.id.help_support_id);
        toolbar.setTitle(getString(R.string.help_support));
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(HelpandSupport.this));
        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_arrow_back_ic_ar);
        }else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_arrow_back_ic_ar);*/
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        WebView FAQ_text = (WebView) findViewById(R.id.faq);
        FAQ_text.setBackgroundColor(Color.TRANSPARENT);

        FAQ_text.setWebViewClient(new WebViewClient());
        WebSettings settings = FAQ_text.getSettings();
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

//        FAQ_text.loadUrl("" + Webdata.helpandSuppord_Url);

        String helpandSuppord_Url = String.format("%s/%s", "" + Webdata.helpandSuppord_Url, "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("helpandSuppord_Url", "" + helpandSuppord_Url);
        FAQ_text.loadUrl("" + helpandSuppord_Url);

    }

}

