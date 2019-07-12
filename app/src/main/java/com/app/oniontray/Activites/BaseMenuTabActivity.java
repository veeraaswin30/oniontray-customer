package com.app.oniontray.Activites;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.PaymentGateWayResp;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEXTBRAIN on 2/17/2017.
 */

public class BaseMenuTabActivity extends TabActivity {

    public static TabHost base_menu_tabhost;

    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog progressDialog;
    private ImageView icon;

    FrameLayout tabcontent;
    public static TabWidget tabs;


    private BroadcastReceiver baseMenuBroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_menu_tab_layout);

        loginPrefManager = new LoginPrefManager(BaseMenuTabActivity.this);
        progressDialog = Webdata.getProgressBarDialog(BaseMenuTabActivity.this);

        base_menu_tabhost = (TabHost) findViewById(android.R.id.tabhost);

        tabcontent = (FrameLayout) findViewById(android.R.id.tabcontent);
        tabs = (TabWidget) findViewById(android.R.id.tabs);







        Intent tab_intent1 = new Intent(BaseMenuTabActivity.this, MenuRestaurantsActivity.class);
        tab_intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        addTab("" + getString(R.string.base_menu_retaurants_txt), R.drawable.menu_restaurants_selector, tab_intent1);

        Intent tab_intent2 = new Intent(BaseMenuTabActivity.this, RestaurantMycartActivity.class);
        tab_intent2.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        addTab("" + getString(R.string.base_menu_my_carts_txt), R.drawable.menu_my_carts_selector, tab_intent2);

        Intent tab_intent3 = new Intent(BaseMenuTabActivity.this, MyOrdersListActivity.class);
        tab_intent3.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        addTab("" + getString(R.string.base_menu_my_orders_txt), R.drawable.menu_my_orders_selector, tab_intent3);

        Intent tab_intent4 = new Intent(BaseMenuTabActivity.this, MenuAccountsActivity.class);
        tab_intent4.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        addTab("" + getString(R.string.base_menu_my_account_txt), R.drawable.menu_accounts_selector, tab_intent4);


        base_menu_tabhost.setCurrentTab(0);
        TextView textView0 = (TextView) base_menu_tabhost.getTabWidget().getChildAt(0).findViewById(R.id.tab_tv_title);
        textView0.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        ImageView imageView0 = (ImageView) base_menu_tabhost.getTabWidget().getChildAt(0).findViewById(R.id.tab_background_iv_icon);
        imageView0.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()));




        base_menu_tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                TextView textView0 = (TextView) base_menu_tabhost.getTabWidget().getChildAt(0).findViewById(R.id.tab_tv_title);
                textView0.setTextColor(getResources().getColor(R.color.disable_txt_color));
                ImageView imageView0 = (ImageView) base_menu_tabhost.getTabWidget().getChildAt(0).findViewById(R.id.tab_background_iv_icon);
                imageView0.setColorFilter(getResources().getColor(R.color.disable_txt_color));



                TextView textView1 = (TextView) base_menu_tabhost.getTabWidget().getChildAt(1).findViewById(R.id.tab_tv_title);
                textView1.setTextColor(getResources().getColor(R.color.disable_txt_color));
                ImageView imageView1 = (ImageView) base_menu_tabhost.getTabWidget().getChildAt(1).findViewById(R.id.tab_background_iv_icon);
                imageView1.setColorFilter(getResources().getColor(R.color.disable_txt_color));



                TextView textView2 = (TextView) base_menu_tabhost.getTabWidget().getChildAt(2).findViewById(R.id.tab_tv_title);
                textView2.setTextColor(getResources().getColor(R.color.disable_txt_color));
                ImageView imageView2 = (ImageView) base_menu_tabhost.getTabWidget().getChildAt(2).findViewById(R.id.tab_background_iv_icon);
                imageView2.setColorFilter(getResources().getColor(R.color.disable_txt_color));



                TextView textView3 = (TextView) base_menu_tabhost.getTabWidget().getChildAt(3).findViewById(R.id.tab_tv_title);
                textView3.setTextColor(getResources().getColor(R.color.disable_txt_color));
                ImageView imageView3 = (ImageView) base_menu_tabhost.getTabWidget().getChildAt(3).findViewById(R.id.tab_background_iv_icon);
                imageView3.setColorFilter(getResources().getColor(R.color.disable_txt_color));



                TextView textView = (TextView) base_menu_tabhost.getCurrentTabView().findViewById(R.id.tab_tv_title);
                textView.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

                ImageView imageview = (ImageView) base_menu_tabhost.getCurrentTabView().findViewById(R.id.tab_background_iv_icon);
                imageview.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()));



//                if(tabId.equalsIgnoreCase(getString(R.string.base_menu_my_account_txt)))
//                {
//                    Log.e("jagj",""+base_menu_tabhost.getCurrentTab());
//                }

            }
        });

        baseMenuBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getStringExtra("page_name").equals("0")) {
                        base_menu_tabhost.setCurrentTab(0);
                    } else if (intent.getStringExtra("page_name").equals("1")) {
                        base_menu_tabhost.setCurrentTab(1);
                    } else if (intent.getStringExtra("page_name").equals("2")) {
                        base_menu_tabhost.setCurrentTab(2);
                    } else if (intent.getStringExtra("page_name").equals("3")) {
                        base_menu_tabhost.setCurrentTab(3);
                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(BaseMenuTabActivity.this).registerReceiver(baseMenuBroadcastReceiver, new IntentFilter("base_activity_receiver"));

        GetPaymentGatewayRequest();

        getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //do whatever you need

                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    base_menu_tabhost.setCurrentTab(2);
                } else {
                    LoginValidConfirmationDialog();
                }

            }
        });

    }

    public void LoginValidConfirmationDialog() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));

        alertDialog.setMessage("" + getString(R.string.home_login_access_it_txt));

        alertDialog.setNegativeButton("" + getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("" + getString(R.string.login_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CallSignInActivityMethod();
                    }
                });
        alertDialog.show();
    }

    public void CallSignInActivityMethod() {
        Intent signInIntent = new Intent(BaseMenuTabActivity.this, RestaurantSignInSignUpActivity.class);
        signInIntent.putExtra("from_my_account", true);
        startActivity(signInIntent);
    }

    private void GetPaymentGatewayRequest() {

        APIService get_payment_gateway_request = Webdata.getRetrofit().create(APIService.class);
        get_payment_gateway_request.paymentGatewayCall("" + loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<PaymentGateWayResp>() {
            @Override
            public void onResponse(Call<PaymentGateWayResp> call, Response<PaymentGateWayResp> response) {

                try {

//                    Log.e("get_payment_gateway_req", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {
                        boolean found = false;
//                        Searhhint:
                        for (int i = 0; i < response.body().getResponse().getAddressDetail().size(); i++) {

                            if (response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId() == 24) {
                                loginPrefManager.setStringValue("Cash_PaymentGateWay_Id", "" + response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId());
                                loginPrefManager.setStringValue("Cash_Delivery_Commision", "" + response.body().getResponse().getAddressDetail().get(i).getCommision());
                                found = true;
//                                continue ;
//                                continue Searhhint;

                            } else if (response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId() == 1) {
                                loginPrefManager.setStringValue("Paypal_PaymentGateWay_Id", "" + response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId());
                                loginPrefManager.setStringValue("Paypal_Commision", "" + response.body().getResponse().getAddressDetail().get(i).getCommision());
                                found = true;
//                                break Searhhint;
                            }else if (response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId() == 30){
                                loginPrefManager.setStringValue("Razor_PaymentGateWay_Id", "" + response.body().getResponse().getAddressDetail().get(i).getPaymentGatewayId());
                                if (response.body().getResponse().getAddressDetail().get(i).getCommision()==null){
                                    loginPrefManager.setStringValue("Razor_Commision", "0");

                                }else {
                                    loginPrefManager.setStringValue("Razor_Commision", "" + response.body().getResponse().getAddressDetail().get(i).getCommision());
                                }
                                found = true;
                            }
                        }

                            if (found) {
                                Log.e("Cash_PaymentGateWay_Id", loginPrefManager.getStringValue("Cash_PaymentGateWay_Id"));
                                Log.e("Cash_Delivery_Commision", loginPrefManager.getStringValue("Cash_Delivery_Commision"));
                                Log.e("Paypal_PaymentGateId", loginPrefManager.getStringValue("Paypal_PaymentGateWay_Id"));
                                Log.e("Paypal_Commision", loginPrefManager.getStringValue("Paypal_Commision"));




                        }
//                        loginPrefManager.setStringValue("Creditcard_PaymentGateWay_Id", "" + response.body().getResponse().getAddressDetail().get(0).getPaymentGatewayId());
//                        loginPrefManager.setStringValue("Creditcard_Commision", "" + response.body().getResponse().getAddressDetail().get(0).getCommision());


                    } else {
//                        Log.e("GetPayGatReq Excep", "400 onFailure");
                    }

                } catch (Exception e) {
//                    Log.e("GetPayGatReq Excep", "Error" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PaymentGateWayResp> call, Throwable t) {
//                Log.e("GetPayGatReq Excep", "onFailure" + t.getMessage());
            }
        });

    }

    private void addTab(String label, int drawableId, Intent intent) {

        TabHost.TabSpec spec = base_menu_tabhost.newTabSpec(label);
        View tabIndicator = LayoutInflater.from(BaseMenuTabActivity.this).inflate(R.layout.tab_icon_txt_lay, null);
        TextView title = (TextView) tabIndicator.findViewById(R.id.tab_tv_title);
        title.setText(label);
//        title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        icon = (ImageView) tabIndicator.findViewById(R.id.tab_background_iv_icon);
        icon.setImageResource(drawableId);
//        icon.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()));

        spec.setIndicator(tabIndicator);
        spec.setContent(intent);
        base_menu_tabhost.addTab(spec);

    }

    @Override
    protected void onDestroy() {

//        Log.e("onDestroy", "BaseMenuTabActivity");
//        loginPrefManager.setStringValue("first_store_review", "");

        LocalBroadcastManager.getInstance(BaseMenuTabActivity.this).unregisterReceiver(baseMenuBroadcastReceiver);
        super.onDestroy();
    }



}
