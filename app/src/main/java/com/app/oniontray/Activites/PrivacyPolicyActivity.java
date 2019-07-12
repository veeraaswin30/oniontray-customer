package com.app.oniontray.Activites;

import android.graphics.Color;
import android.os.Bundle;
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
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.Webdata;

/**
 * Created by nextbrain on 25/3/17.
 */

public class PrivacyPolicyActivity extends LocalizationActivity {

    private Toolbar terms_cond_tool_bar;

    private TextView my_addr_title_txt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions_layout);

        terms_cond_tool_bar = (Toolbar) findViewById(R.id.terms_cond_tool_bar);
//        terms_cond_tool_bar.setTitle("" + getString(R.string.terms_conditions_tit_txt));
        terms_cond_tool_bar.setTitle("");
        terms_cond_tool_bar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(terms_cond_tool_bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(PrivacyPolicyActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
        }

        terms_cond_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        my_addr_title_txt = (TextView) findViewById(R.id.my_addr_title_txt);
      //  my_addr_title_txt.setText("" + getString(R.string.privacy_policy_tit_txt));
        my_addr_title_txt.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        my_addr_title_txt.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));



        WebView terms_and_condi_web_view = (WebView) findViewById(R.id.terms_and_condi_web_view);
        terms_and_condi_web_view.setBackgroundColor(Color.TRANSPARENT);

        terms_and_condi_web_view.setWebViewClient(new WebViewClient());
        WebSettings settings = terms_and_condi_web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setBuiltInZoomControls(true);


        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(PrivacyPolicyActivity.this, "" + getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

//        terms_and_condi_web_view.loadUrl("" + Webdata.privacy_policy_Url);

        String privacy_policy_Url = String.format("%s/%s", "" + Webdata.privacy_policy_Url, "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("privacy_policy_Url", "" + privacy_policy_Url);

        terms_and_condi_web_view.loadUrl("" + privacy_policy_Url);


    }

}
