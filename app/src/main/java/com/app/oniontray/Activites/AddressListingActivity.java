package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.AddressAdapter;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddressListing;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddressListingActivity extends LocalizationActivity {

    private LoginPrefManager preferenceAccess;
    private DDProgressBarDialog progress;

    private ListView address_list;
    private AddressAdapter addressAdapter;


    private TextView add_add_list_empty_txt_view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_listing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.My_add_list_toolbar_id);
        toolbar.setTitle(getString(R.string.myadd_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(AddressListingActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_arrow_back_ic_ar);
        }else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.nav_arrow_back_ic_ar);
*/
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        preferenceAccess = new LoginPrefManager(AddressListingActivity.this);
        progress = new DDProgressBarDialog(AddressListingActivity.this);

        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        LinearLayout text_click = (LinearLayout) findViewById(R.id.textClick);
        text_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListingActivity.this, AddAddressActivity.class);
                intent.putExtra("screen_flow", "1");
                startActivity(intent);

//                startActivity(new Intent(AddressListingActivity.this, AddAddressActivity.class));
                finish();
            }
        });

        add_add_list_empty_txt_view = (TextView)findViewById(R.id.add_add_list_empty_txt_view);
        add_add_list_empty_txt_view.setTextColor( Color.parseColor(loginPrefManager. getThemeFontColor()));
        address_list = (ListView) findViewById(R.id.input_addr_list);


        addresListRequestMethod();


    }

    private void addresListRequestMethod(){

        if (progress != null) {
            progress.show();
        }

        try {

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.get_address(preferenceAccess.getStringValue("user_id"), preferenceAccess.getStringValue("user_token"), preferenceAccess.getStringValue("Lang_code")).enqueue(new Callback<AddressListing>() {
                @Override
                public void onResponse(Call<AddressListing> call, Response<AddressListing> response) {

                    progress.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {
//                        Log.e("data", String.valueOf(response.body().getResponse().getAddressList().size()));
                        addressAdapter = new AddressAdapter(AddressListingActivity.this, response.body().getResponse().getAddressList());
                        address_list.setAdapter(addressAdapter);

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(AddressListingActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        if(response.body().getResponse().getAddressList().size() == 0){
                            add_add_list_empty_txt_view.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<AddressListing> call, Throwable t) {
                    progress.dismiss();
//                    Log.e("error", t.toString());
                }
            });

        }catch (Exception e){
//            Log.e("addresListRequestMethod","Exception :"+ e.getMessage());
        }

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
