package com.app.oniontray.Activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.app.oniontray.Adapters.MyAddressAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.PickMyLocAddItemDecoration;
import com.app.oniontray.RequestModels.AddressListing;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyAddressListActivity extends LocalizationActivity implements MyAddressAdapter.MyAddressAdapterInterface{

    private Toolbar toolbar;

    private RecyclerView my_addr_list_recycler_view;
    private MyAddressAdapter myAddressAdapter;

    private TextView my_addr_emty_txt_view;

    private TextView address_title;

    private View my_addr_add_new_add_view;

    private BroadcastReceiver my_address_BroadcastReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address_list);

        toolbar = (Toolbar) findViewById(R.id.my_addr_toolbar_id);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(MyAddressListActivity.this));


        if (language.equals("en")) {
          //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        my_addr_emty_txt_view = (TextView) findViewById(R.id.my_addr_emty_txt_view);

        address_title = (TextView) findViewById(R.id.address_title);
        address_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        my_addr_add_new_add_view = (View) findViewById(R.id.my_addr_add_new_add_view);
        my_addr_add_new_add_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MyAddressListActivity.this, AddAddressActivity.class);
                intent.putExtra("screen_flow", "3");
                intent.putExtra("country_id", "" + loginPrefManager.getCountryID());
                startActivity(intent);

            }
        });

        my_addr_list_recycler_view = (RecyclerView) findViewById(R.id.my_addr_list_recycler_view);
        my_addr_list_recycler_view.setHasFixedSize(true);
        my_addr_list_recycler_view.setLayoutManager(new LinearLayoutManager(MyAddressListActivity.this));
        my_addr_list_recycler_view.addItemDecoration(new PickMyLocAddItemDecoration(MyAddressListActivity.this, R.dimen.ho_search_divider_line_size));
//        myAddressAdapter = new MyAddressAdapter(MyAddressListActivity.this, new ArrayList<AddressList>());
//        myAddressAdapter.MyAddrAddapterInterfaceMethod(MyAddressListActivity.this);
//        my_addr_list_recycler_view.setAdapter(myAddressAdapter);

        GetMySavedAddressRequestMethod();


        my_address_BroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

//                Log.e("my_address_receiv","my_address_receiv");
                GetMySavedAddressRequestMethod();
            }
        };
        LocalBroadcastManager.getInstance(MyAddressListActivity.this).registerReceiver(my_address_BroadcastReceiver, new IntentFilter("my_address_receiv"));

    }

    private void GetMySavedAddressRequestMethod() {

        try {

//            Log.e("user_id", ""+loginPrefManager.getStringValue("user_id"));
//            Log.e("user_token", ""+loginPrefManager.getStringValue("user_token"));

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);  // user_token

            apiService.get_address("" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("Lang_code"),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<AddressListing>() {
                @Override
                public void onResponse(Call<AddressListing> call, Response<AddressListing> response) {

                    try {

                        progressDialog.dismiss();

//                        Log.e("onResponse", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

//                            Log.e("onResponse","200");


                            if(myAddressAdapter != null){

//                                Log.e("onResponse","1");

                                myAddressAdapter = null;

                                myAddressAdapter = new MyAddressAdapter(MyAddressListActivity.this, response.body().getResponse().getAddressList());
                                myAddressAdapter.MyAddrAddapterInterfaceMethod(MyAddressListActivity.this);
                                my_addr_list_recycler_view.setAdapter(myAddressAdapter);

                            }else{

//                                Log.e("onResponse","2");

                                myAddressAdapter = new MyAddressAdapter(MyAddressListActivity.this, response.body().getResponse().getAddressList());
                                myAddressAdapter.MyAddrAddapterInterfaceMethod(MyAddressListActivity.this);
                                my_addr_list_recycler_view.setAdapter(myAddressAdapter);

                            }

//                            if(myAddressAdapter != null){
//                                myAddressAdapter.UpdateMyAddressAdapter(response.body().getResponse().getAddressList());
//                            }

                            if (response.body().getResponse().getAddressList().size() == 0) {
                                my_addr_emty_txt_view.setVisibility(View.VISIBLE);
                            } else {
                                my_addr_emty_txt_view.setVisibility(View.GONE);
                            }

                        } else {

//                            Log.e("onResponse","" + response.body().getResponse().getHttpCode());

                            if (response.body().getResponse().getAddressList().size() == 0) {
                                my_addr_emty_txt_view.setVisibility(View.VISIBLE);
                            } else {
                                my_addr_emty_txt_view.setVisibility(View.GONE);
                            }

                        }

                    } catch (Exception e) {
//                        Log.e("Exception", "-" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AddressListing> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", "-" + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
//            Log.e("get_address Api ", "Exception " + e.getMessage());
        }


    }

    @Override
    public void MyAddrEmptyViewInterface(int array_size) {
        if (array_size == 0) {
            my_addr_emty_txt_view.setVisibility(View.VISIBLE);
        } else {
            my_addr_emty_txt_view.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(MyAddressListActivity.this).unregisterReceiver(my_address_BroadcastReceiver);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }



}
