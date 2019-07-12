package com.app.oniontray.Activites;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.oniontray.Adapters.MyOrdersListAdapter;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OffersItemOffsetDecor;
import com.app.oniontray.RequestModels.MyOrders;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyOrdersListActivity extends LocalizationActivity {

    private Toolbar myorders_toolbar;
    private TextView toolbar_title;

    private LinearLayout my_order_list_deliv_food_lay;

    private ImageView my_ord_deli_tit_ic;
    private TextView my_ord_list_deli_tit_txt_view;
    private TextView my_ord_list_empty_txt_view;

    private RecyclerView my_orders_list_recycler_view;
    LinearLayoutManager myOrderLayoutManager;

    private BroadcastReceiver my_order_receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_list);

        myorders_toolbar = (Toolbar) findViewById(R.id.myorders_toolbar);
        myorders_toolbar.setTitle("");
       //toolbar_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        myorders_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        toolbar_title=(TextView)findViewById(R.id.vender_title_txt);
        toolbar_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));



        setSupportActionBar(myorders_toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//           String language = String.valueOf(LanguageSetting.getLanguage(AddAddressActivity.this));


//        if (language.equals("en")) {
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
//        } else {
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
//        }
//
//        myorders_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BaseBackMethod();
//            }
//        });

        my_order_list_deliv_food_lay = (LinearLayout) findViewById(R.id.my_order_list_deliv_food_lay);

        my_ord_deli_tit_ic = (ImageView) findViewById(R.id.my_ord_deli_tit_ic);
        toolbar_title=(TextView)findViewById(R.id.vender_title_txt);

        my_ord_list_deli_tit_txt_view = (TextView) findViewById(R.id.my_ord_list_deli_tit_txt_view);

        String deliverd_by_txt = String.format("%s <font color='%s'>%s</font>", "" + getString(R.string.my_cart_deliverd_by_txt), "" + loginPrefManager.getThemeFontColor(), "" + getString(R.string.app_name));
        my_ord_list_deli_tit_txt_view.setText(Html.fromHtml("" + deliverd_by_txt));

        my_ord_list_empty_txt_view = (TextView) findViewById(R.id.my_ord_list_empty_txt_view);
        my_orders_list_recycler_view = (RecyclerView) findViewById(R.id.my_orders_list_recycler_view);
        my_orders_list_recycler_view.setLayoutManager(new LinearLayoutManager(MyOrdersListActivity.this));
        my_orders_list_recycler_view.setHasFixedSize(true);
        my_orders_list_recycler_view.addItemDecoration(new OffersItemOffsetDecor(MyOrdersListActivity.this, R.dimen.my_order_list_line_height_size));

        my_order_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                MyOrdersRequestMethod();

                if (intent != null) {
                    if (intent.getStringExtra("my_order_list").equals("1")) {
                        MyOrdersRequestMethod();
                    }
                }

            }
        };
        LocalBroadcastManager.getInstance(MyOrdersListActivity.this).registerReceiver(my_order_receiver, new IntentFilter("my_order_list"));


    }

    @Override
    public void onResume() {
        super.onResume();

        LoginValidationCallMethod();
    }

    @Override
    public void onBackPressed() {
        BaseBackMethod();
    }

    public void BaseBackMethod() {
        Intent intent_rece = new Intent("base_activity_receiver");
        intent_rece.putExtra("page_name", "0");
        LocalBroadcastManager.getInstance(MyOrdersListActivity.this).sendBroadcast(intent_rece);
    }

    private void LoginValidationCallMethod() {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {

            MyOrdersRequestMethod();
        } else {

            LoginValidConfirmationDialog();
        }
    }

    public void LoginValidConfirmationDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));

        alertDialog.setMessage("" + getString(R.string.home_login_access_it_txt));

        alertDialog.setNegativeButton("" + getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                BaseBackMethod();
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


    private void MyOrdersRequestMethod() {

        if (!isFinishing()) {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }

//        Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//        Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.order_list("" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("Lang_code"),
                "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                try {

                    Log.e("order_list", "" + response.raw().toString());

                    Log.e("i/p", new Gson().toJson(response.raw().request()));


                    progressDialog.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {
                        my_orders_list_recycler_view.setAdapter(new MyOrdersListAdapter(MyOrdersListActivity.this, response.body().getResponse().getOrderList()));
                        if (response.body().getResponse().getOrderList().size() == 0) {
                            my_ord_list_empty_txt_view.setVisibility(View.VISIBLE);
                        } else {
                            my_ord_list_empty_txt_view.setVisibility(View.GONE);
                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
//                        Log.e("Failure", response.body().getResponse().getStatus().toString());
                    }
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

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
        LocalBroadcastManager.getInstance(MyOrdersListActivity.this).unregisterReceiver(my_order_receiver);
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
