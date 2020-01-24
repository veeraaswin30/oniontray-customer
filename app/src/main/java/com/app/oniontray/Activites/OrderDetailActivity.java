package com.app.oniontray.Activites;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.OrederDetailsItemAdapter;
import com.app.oniontray.CustomViews.MyOrdDetRetOrdDialog;
import com.app.oniontray.CustomViews.UpdateStoreReviewDialog;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.RecyclerView.NotificationListItemOffsetDecor;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.RequestModels.ReOrdResp;
import com.app.oniontray.RequestModels.ReOrderReq;
import com.app.oniontray.RequestModels.SelectedIngredient;
import com.bumptech.glide.Glide;
import com.app.oniontray.Adapters.CustomListAdapterDialog;
import com.app.oniontray.CustomViews.BadgeDrawable;
import com.app.oniontray.CustomViews.ExpandableHeightListView;
import com.app.oniontray.CustomViews.MyOrderInvoiceDialog;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CancelOrder;
import com.app.oniontray.RequestModels.CartCount;
import com.app.oniontray.RequestModels.MobReturnReason;
import com.app.oniontray.RequestModels.OrderDetail;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends LocalizationActivity implements
        MyOrdDetRetOrdDialog.MyorderReloadInterface,
        UpdateStoreReviewDialog.MyorderReviewReloadInterface {

    private Toolbar order_details_toolber;

    private final SimpleDateFormat old_format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat new_format = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

    private final SimpleDateFormat deliv_date_old_format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private final SimpleDateFormat deliv_date_new_format = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);


    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";


    private ImageView Input_order_image;

    private TextView Input_order_status;
    private TextView Input_order_key;
    private TextView Input_vend_name;
    private TextView Input_delv_order_date;

    private LinearLayout my_ord_det_deliv_charge_layout;

    private TableRow my_ord_det_del_date_table_row_lay;
    private TableRow my_ord_det_del_slot_table_row_lay;
    private TableRow my_ord_det_promo_code_table_row_lay;

    private ExpandableHeightListView Input_order_sum_list;

    // Order Charges details
    private TextView my_ord_det_sub_total_txt, my_ord_det_deli_charges_txt, my_ord_det_ser_tax_txt, my_ord_det_total_txt;

    // Delivery Details
    private TextView my_ord_det_pay_type_txt_view, my_ord_det_deli_add_txt_view, my_ord_det_deli_instu_txt_view;

    private TextView my_ord_det_deli_add_holder_txt_view, my_ord_det_deliv_date_holder_txt_view, my_ord_det_deliv_date_txt_view;

    private TextView my_ord_det_deliv_slot_txt_view, my_ord_det_promo_code_amt_txt_view, tvOrderDeliveryCharge;


    private Button my_ord_det_can_btn;
    private Button my_ord_det_ret_btn;
    private Button my_ord_det_review_btn;
    private Button my_ord_det_invoice_btn;
    private Button my_ord_det_re_order_btn;
    private Button my_ord_det_deli_track_btn;


    private OrederDetailsItemAdapter OrderAdapter;
    private List<MobReturnReason> mobReturnReasons = new ArrayList<>();

    private String order_id = "";

    private String value;
    private String inVoice_Pdf = "";

    private LayerDrawable icon;

    private TableRow my_ord_det_deliv_instru_table_row_lay;


    private TextView ord_det_service_tax_lable_view;
    private LinearLayout ord_det_service_tax_lay;

    private RecyclerView ord_details_recycler_view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_detail_layout);

        order_details_toolber = (Toolbar) findViewById(R.id.order_details_toolber);
        order_details_toolber.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(order_details_toolber);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(OrderDetailActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }


        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            new_date_format.setNumberFormat(nf);
            deliv_date_new_format.setNumberFormat(nf);
        }


        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        TextView my_ord_det_bag_tit_txt_view = (TextView) findViewById(R.id.my_ord_details_bag_tit_txt_view);
        String deliverd_by_txt = String.format("%s <font color='%s'>%s</font>", "" + getString(R.string.my_cart_deliverd_by_txt), "" + getString(R.string.my_progress_loader_dot_color), "" + getString(R.string.app_name));
        my_ord_det_bag_tit_txt_view.setText(Html.fromHtml("" + deliverd_by_txt));

        Input_order_image = (ImageView) findViewById(R.id.my_ord_det_img_view);
        Input_order_status = (TextView) findViewById(R.id.my_ord_det_row_process_txt_view);
        Input_order_key = (TextView) findViewById(R.id.my_order_det_row_tit_txt_view);
        Input_vend_name = (TextView) findViewById(R.id.my_ord_det_row_store_name_txt_view);
        tvOrderDeliveryCharge = (TextView) findViewById(R.id.tvOrderDeliveryCharge);

        my_ord_det_deliv_charge_layout = (LinearLayout) findViewById(R.id.my_ord_det_deliv_charge_layout);


        Input_delv_order_date = (TextView) findViewById(R.id.my_ord_det_ord_on_txt_view);

        my_ord_det_can_btn = (Button) findViewById(R.id.my_ord_det_can_btn);
        my_ord_det_invoice_btn = (Button) findViewById(R.id.my_ord_det_invoice_btn);
        my_ord_det_ret_btn = (Button) findViewById(R.id.my_ord_det_ret_btn);
        my_ord_det_review_btn = (Button) findViewById(R.id.my_ord_det_review_btn);
        my_ord_det_re_order_btn = (Button) findViewById(R.id.my_ord_det_re_order_btn);

        Button return_yes_but = (Button) findViewById(R.id.okbutton);
        Button return_no_but = (Button) findViewById(R.id.cancel_button);

        // Order Charges details
        my_ord_det_sub_total_txt = (TextView) findViewById(R.id.my_ord_det_sub_total_txt);
        my_ord_det_deli_charges_txt = (TextView) findViewById(R.id.my_ord_det_deli_charges_txt);

        ord_det_service_tax_lay = (LinearLayout) findViewById(R.id.ord_det_service_tax_lay);
        ord_det_service_tax_lable_view = (TextView) findViewById(R.id.ord_det_service_tax_lable_view);
        my_ord_det_ser_tax_txt = (TextView) findViewById(R.id.my_ord_det_ser_tax_txt);

        my_ord_det_total_txt = (TextView) findViewById(R.id.my_ord_det_total_txt);

        // Delivery Details
        my_ord_det_pay_type_txt_view = (TextView) findViewById(R.id.my_ord_det_pay_type_txt_view);
        TableRow my_ord_det_del_addr_table_row_lay = (TableRow) findViewById(R.id.my_ord_det_del_addr_table_row_lay);
        my_ord_det_deli_add_txt_view = (TextView) findViewById(R.id.my_ord_det_deli_add_txt_view);
        my_ord_det_deli_instu_txt_view = (TextView) findViewById(R.id.my_ord_det_deli_instu_txt_view);

        my_ord_det_deli_add_holder_txt_view = (TextView) findViewById(R.id.my_ord_det_deli_add_holder_txt_view);
        my_ord_det_deliv_date_holder_txt_view = (TextView) findViewById(R.id.my_ord_det_deliv_date_holder_txt_view);


        my_ord_det_del_date_table_row_lay = (TableRow) findViewById(R.id.my_ord_det_del_date_table_row_lay);
        my_ord_det_deliv_date_txt_view = (TextView) findViewById(R.id.my_ord_det_deliv_date_txt_view);
        my_ord_det_del_slot_table_row_lay = (TableRow) findViewById(R.id.my_ord_det_del_slot_table_row_lay);
        my_ord_det_deliv_slot_txt_view = (TextView) findViewById(R.id.my_ord_det_deliv_slot_txt_view);

        my_ord_det_promo_code_table_row_lay = (TableRow) findViewById(R.id.my_ord_det_promo_code_table_row_lay);
        my_ord_det_promo_code_amt_txt_view = (TextView) findViewById(R.id.my_ord_det_promo_code_amt_txt_view);

        my_ord_det_deliv_instru_table_row_lay = (TableRow) findViewById(R.id.my_ord_det_deliv_instru_table_row_lay);


        Input_order_sum_list = (ExpandableHeightListView) findViewById(R.id.order_sum_list);

        ord_details_recycler_view = (RecyclerView) findViewById(R.id.ord_details_recycler_view);
        ord_details_recycler_view.setLayoutManager(new LinearLayoutManager(OrderDetailActivity.this));
        ord_details_recycler_view.setHasFixedSize(true);
        ord_details_recycler_view.addItemDecoration(new NotificationListItemOffsetDecor(OrderDetailActivity.this, R.dimen.notifications_list_item_row_line_hight));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order_id = bundle.getString("Order_id");
        }

        my_ord_det_can_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CancelOrderConfirmDialog();
            }
        });

        my_ord_det_invoice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("inVoice_Pdf url",""+inVoice_Pdf);
                new MyOrderInvoiceDialog(OrderDetailActivity.this, inVoice_Pdf).show();
            }
        });

        my_ord_det_re_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReOrderMethod();
            }
        });


        my_ord_det_deli_track_btn = (Button) findViewById(R.id.my_ord_det_deli_track_btn);
        my_ord_det_deli_track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delivery_boy_intent = new Intent(OrderDetailActivity.this, TrackOrderStatesActivity.class);
                delivery_boy_intent.putExtra("order_id", "" + order_id);
                delivery_boy_intent.putExtra("Screen_Flow", "1");
                startActivity(delivery_boy_intent);
            }
        });

        MyOrderDetailsRequestMethod();

    }

    @Override
    public void onResume() {
        super.onResume();

//        if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//            CartCountRequest();
//        }

        MyOrderDetailsRequestMethod();

    }

    @Override
    public void ReloadOrderDetatils() {
//        LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(new Intent("my_order_list"));
        MyOrderDetailsRequestMethod();

    }

    @Override
    public void ReloadOrderReviewDetatils() {

//        LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(new Intent("my_order_list"));
        MyOrderDetailsRequestMethod();

    }


    private void MyOrderDetailsRequestMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        Log.e("language", loginPrefManager.getStringValue("Lang_code"));
        Log.e("user_id", loginPrefManager.getStringValue("user_id"));
        Log.e("token", loginPrefManager.getStringValue("user_token"));
        Log.e("CityID", "" + loginPrefManager.getCityID());
        Log.e("LocID", "" + loginPrefManager.getLocID());
        Log.e("order_id", "-" + order_id);

        APIService order_detail_api = Webdata.getRetrofit().create(APIService.class);
        order_detail_api.Order_detail_call(loginPrefManager.getStringValue("Lang_code"), "" + order_id, loginPrefManager.getStringValue("user_id"),
                "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(),
                loginPrefManager.getStringValue("user_token")).enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, final Response<OrderDetail> response) {

//                try {

//                Log.e("onResponse", "" + response.raw().toString());
//
//                Log.e("order details", new Gson().toJson(response.raw().request()));

                progressDialog.dismiss();

                if (response.body().getResponse().getHttpCode() == 200) {

                    OrderAdapter = new OrederDetailsItemAdapter(OrderDetailActivity.this, response.body().getResponse().getOrderItems());
                    ord_details_recycler_view.setAdapter(OrderAdapter);

                    Glide.with(getApplicationContext()).load(response.body().getResponse().getVendorInfo().get(0).getFeatured_image()).bitmapTransform(new CenterCrop(OrderDetailActivity.this), new RoundedCornersTransformation(OrderDetailActivity.this, 10, 0)).error(R.color.app_background_color).into(Input_order_image);

                    Input_order_key.setText("" + response.body().getResponse().getVendorInfo().get(0).getOrderKeyFormated());
                    Input_vend_name.setText(response.body().getResponse().getVendorInfo().get(0).getOutletName());


//                        Log.e("getOrderStatus", "-" + response.body().getResponse().getVendorInfo().get(0).getOrderStatus());


                    if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 11) {
                        Input_order_status.setTextColor(Color.RED);
                        my_ord_det_can_btn.setVisibility(View.GONE);
                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 12) {
                        my_ord_det_can_btn.setVisibility(View.GONE);
                        my_ord_det_deliv_date_holder_txt_view.setText("" + getResources().getString(R.string.my_ord_det_deliverd_date_txt));
                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 17) {
                        my_ord_det_can_btn.setVisibility(View.GONE);
                    } else {
                        my_ord_det_deliv_date_holder_txt_view.setText("" + getResources().getString(R.string.my_ord_det_deli_date_txt));
                    }

                    inVoice_Pdf = response.body().getResponse().getOrderItems().get(0).getInvoicPdf();

                    if (!response.body().getResponse().getVendorInfo().get(0).getCreatedDate().isEmpty()) {
                        Date order_date = null;
                        try {
                            order_date = old_date_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getCreatedDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Input_delv_order_date.setText("" + new_date_format.format(order_date));
                    }


                    Float total_amt = Float.parseFloat("" + response.body().getResponse().getOrderItems().get(0).getTotalAmount());
                    Float service_tax = Float.parseFloat("" + response.body().getResponse().getOrderItems().get(0).getServiceTax());
                    Float deliv_charge = Float.parseFloat("" + response.body().getResponse().getOrderItems().get(0).getDeliveryCharge());
                    Float coupon_amt = Float.parseFloat("" + response.body().getResponse().getOrderItems().get(0).getCouponAmount());

                    Float sub_total = (total_amt - (service_tax + deliv_charge));

                    my_ord_det_sub_total_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf("" + sub_total))));


                    if (!response.body().getResponse().getOrderItems().get(0).getDeliveryCharge().equals("0")) {
                        my_ord_det_deli_charges_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.body().getResponse().getOrderItems().get(0).getDeliveryCharge()))));
                    } else {
                        my_ord_det_deli_charges_txt.setText("" + getString(R.string.free));
                    }


                    if (response.body().getResponse().getOrderItems().get(0).getServiceTax().equals("0")) {
                        ord_det_service_tax_lay.setVisibility(View.GONE);
                    } else {
                        ord_det_service_tax_lay.setVisibility(View.VISIBLE);
                        my_ord_det_ser_tax_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.body().getResponse().getOrderItems().get(0).getServiceTax()))));
                    }


                    // Delivery Details txt view
                    my_ord_det_pay_type_txt_view.setText(response.body().getResponse().getVendorInfo().get(0).getPaymentGatewayName());

                    if (!response.body().getResponse().getOrderItems().get(0).getCouponAmount().equals("0")) {

//                            int grand_total_amt = Integer.parseInt("" + response.body().getResponse().getOrderItems().get(0).getTotalAmount());
//                            int coupon_promo_amt = Integer.parseInt("" + response.body().getResponse().getOrderItems().get(0).getCouponAmount());
//                            int redu_coupon_amt = (grand_total_amt - coupon_promo_amt);

                        my_ord_det_total_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf("" + response.body().getResponse().getOrderItems().get(0).getTotalAmount()))));

                        my_ord_det_promo_code_table_row_lay.setVisibility(View.VISIBLE);
                        ord_det_service_tax_lable_view.setText("" + response.body().getResponse().getVendorInfo().get(0).getTaxLabelName().trim());
                        my_ord_det_promo_code_amt_txt_view.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.body().getResponse().getOrderItems().get(0).getCouponAmount()))));

                    } else {
                        my_ord_det_total_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.body().getResponse().getOrderItems().get(0).getTotalAmount()))));
                    }


                    if (!response.body().getResponse().getVendorInfo().get(0).getDeliveryDate().isEmpty()) {
                        Date order_date = null;
                        try {
                            order_date = deliv_date_old_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getDeliveryDate().trim());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        my_ord_det_deliv_date_txt_view.setText("" + deliv_date_new_format.format(order_date));
                    }
                    if (response.body().getResponse().getVendorInfo().get(0).getOrderType() == 2) {
                        tvOrderDeliveryCharge.setText(getString(R.string.platform_charge));
                        my_ord_det_del_date_table_row_lay.setVisibility(View.VISIBLE);
                        my_ord_det_del_slot_table_row_lay.setVisibility(View.GONE);
                        my_ord_det_deliv_date_holder_txt_view.setText(getString(R.string.my_ord_det_pickup_date_txt));
                        my_ord_det_deli_add_holder_txt_view.setText("" + getResources().getString(R.string.my_ord_det_pick_up_address_txt));
                        my_ord_det_deli_add_txt_view.setText(response.body().getResponse().getVendorInfo().get(0).getContactAddress());

                    } else {
                        tvOrderDeliveryCharge.setText(getString(R.string.deliver_charges));


//                            if (!response.body().getResponse().getVendorInfo().get(0).getstart_time().isEmpty() && !response.body().getResponse().getVendorInfo().get(0).getend_time().isEmpty()) {
//                                Date start_date = old_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getstart_time());
//                                Date end_date = old_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getend_time());
//                                my_ord_det_deliv_slot_txt_view.setText("" + new_format.format(start_date) + " - " + new_format.format(end_date));
//                            }

                        my_ord_det_deli_add_holder_txt_view.setText("" + getResources().getString(R.string.my_ord_det_deli_address_txt));
                        my_ord_det_deli_add_txt_view.setText(response.body().getResponse().getVendorInfo().get(0).getUserContactAddress());

                    }


                    if (response.body().getResponse().getVendorInfo().get(0).getDeliveryInstructions().isEmpty()) {
                        my_ord_det_deliv_instru_table_row_lay.setVisibility(View.GONE);
                    } else {
                        my_ord_det_deli_instu_txt_view.setText(response.body().getResponse().getVendorInfo().get(0).getDeliveryInstructions());
                    }

                    if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 12 && response.body().getResponse().getReviews().getReviewStatus() == 0) {

                        my_ord_det_review_btn.setVisibility(View.VISIBLE);

                        my_ord_det_review_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UpdateStoreReviewDialog updateStoreReviewDialog = new UpdateStoreReviewDialog(OrderDetailActivity.this, "" + loginPrefManager.getStringValue("user_id"),
                                        "" + response.body().getResponse().getVendorInfo().get(0).getVendorId(),
                                        "" + response.body().getResponse().getVendorInfo().get(0).getOutletId(),
                                        "" + response.body().getResponse().getVendorInfo().get(0).getOrderId());
                                updateStoreReviewDialog.MyOrderinterfaceCallMethod(OrderDetailActivity.this);
                                updateStoreReviewDialog.show();
                            }
                        });

                    } else {
                        my_ord_det_review_btn.setVisibility(View.GONE);
                    }


                    //        <!--1, 10, 12, 14, 19, 24, 11-->

//                    if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 1) {
//                        Input_order_status.setText("" + getString(R.string.order_states_initiated_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 10) {
//                        Input_order_status.setText("" + getString(R.string.order_states_processed_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 12) {
//                        Input_order_status.setText("" + getString(R.string.order_states_delivered_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 14) {
//                        Input_order_status.setText("" + getString(R.string.order_states_shipped_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 19) {
//                        Input_order_status.setText("" + getString(R.string.order_states_dispatched_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 24) {
//                        Input_order_status.setText("" + getString(R.string.order_states_packed_txt));
//                    } else if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 11) {
//                        Input_order_status.setText("" + getString(R.string.order_states_canceld_txt));
//                    }

                    Input_order_status.setText(response.body().getResponse().getVendorInfo().get(0).getName());

                    if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 19) {
                        my_ord_det_deli_track_btn.setVisibility(View.VISIBLE);
                    } else {
                        my_ord_det_deli_track_btn.setVisibility(View.GONE);
                    }


                    Input_order_status.setTextColor(Color.parseColor(response.body().getResponse().getVendorInfo().get(0).getColorCode().trim()));


                    // Created date & time added with cancel order time. Then compare with current date and time

                    if (!response.body().getResponse().getVendorInfo().get(0).getCreatedDate().isEmpty()) {

                        Date order_date = null;
                        try {
                            order_date = old_date_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getCreatedDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

//                            Log.e("order_date time", "" + old_date_format.format(order_date));
//                            Log.e("getCancelTime", "" + Integer.parseInt(response.body().getResponse().getVendorInfo().get(0).getCancelTime()));

                        // created date
                        Calendar orderd_calendar = Calendar.getInstance();
                        orderd_calendar.setTime(order_date);
                        orderd_calendar.add(Calendar.MINUTE, Integer.parseInt(response.body().getResponse().getVendorInfo().get(0).getCancelTime()));
                        orderd_calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
                        Date created_date = orderd_calendar.getTime();
//                            Log.e("order_date added_time", "" + old_date_format.format(created_date));


                        Date server_cur_date = null;
                        try {
                            server_cur_date = old_date_format.parse("" + response.body().getResponse().getCurrentTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar server_cur_call = Calendar.getInstance();
                        server_cur_call.setTime(server_cur_date);
                        Date created_time_date = server_cur_call.getTime();

//                            Log.e("date 1", "" + old_date_format.format(created_date));
//                            Log.e("date 2", "" + old_date_format.format(created_time_date));
//
//                            Log.e("after", "" + created_date.after(created_time_date));
//                            Log.e("before", "" + created_date.before(created_time_date));

                        //Aswin
                        String mCurrentDate = response.body().getResponse().getCurrentTime();
                        String mDeliveryDte = response.body().getResponse().getVendorInfo().get(0).getDeliveryDate();

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            Date date = sdf.parse(mDeliveryDte);
                            Date CurrDate = sdf.parse(mCurrentDate);

                             if (date.after(CurrDate)) {
                            Log.e("AferDate", "AfterDate");
                            if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 1) {

                                my_ord_det_can_btn.setVisibility(View.VISIBLE);

                                // }
                            } else {
                                my_ord_det_can_btn.setVisibility(View.GONE);
                            }
                            } else {
                                Log.e("OutDated", "OutDated");
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                        /*if (created_date.after(created_time_date)) {
                            if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 1) {

                                if (response.body().getResponse().getVendorInfo().get(0).getPaymentType().equals("COD")) {
                                    my_ord_det_can_btn.setVisibility(View.VISIBLE);
                                }

                            }
                        }
*/
                        if (response.body().getResponse().getVendorInfo().get(0).getOrderStatus() == 12) {
                            my_ord_det_can_btn.setVisibility(View.GONE);
                        }

                    }


                    if (response.body().getResponse().getReorderOption() == 0) {
                        my_ord_det_re_order_btn.setVisibility(View.GONE);
                    } else {
                        my_ord_det_re_order_btn.setVisibility(View.VISIBLE);
                    }


                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(OrderDetailActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }

//                } catch (Exception e) {
////                    Log.e("Exception", e.getMessage());
//                    progressDialog.dismiss();
//                }

            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Log.e("Failure", t.toString());
                progressDialog.dismiss();

            }
        });

    }


    public static Date GetUTCdatetimeAsDate() {
        //note: doesn't check for null
        return StringDateToDate(GetUTCdatetimeAsString());
    }

    public static String GetUTCdatetimeAsString() {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        final String utcTime = sdf.format(new Date());

        return utcTime;
    }

    public static Date StringDateToDate(String StrDate) {
        Date dateToReturn = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        try {
            dateToReturn = (Date) dateFormat.parse(StrDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateToReturn;
    }


    private void ReOrderMethod() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            Log.e("order_id", "" + order_id);
            Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
            Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
            Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));
            Log.e("type", "" + getString(R.string.my_ord_det_ret_ord_type));

            APIService ReOrderService = Webdata.getRetrofit().create(APIService.class);
            ReOrderService.re_order_Request_call_method("" + order_id, "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("Lang_code"), "" + getString(R.string.my_ord_det_ret_ord_type),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<ReOrderReq>() {
                @Override
                public void onResponse(Call<ReOrderReq> call, Response<ReOrderReq> response) {
//
//                    try {

                    Log.e("re_order_response", response.raw().toString());
                    progressDialog.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {

                        UpdateCartDetails(response.body().getResponse());

//                        Toast.makeText(OrderDetailActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(OrderDetailActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }
//                    } catch (Exception e) {
//                        Log.e("Exception", e.toString());
//                    }

                }

                @Override
                public void onFailure(Call<ReOrderReq> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Exception", t.toString());
                }
            });

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    private void UpdateCartDetails(ReOrdResp reOrdResp) {

//        try {

        productRespository.ClearCart();

        if (reOrdResp.getOrders().size() != 0) {

            for (int i = 0; i < reOrdResp.getOrders().size(); i++) {

// Log.e("getOrders", "" + i);

                ProductList_Data productList_data = new ProductList_Data();

                productList_data.setProductId(reOrdResp.getOrders().get(i).getProductId());
                productList_data.setproductName(reOrdResp.getOrders().get(i).getProductName());
                productList_data.setProductUrl(reOrdResp.getOrders().get(i).getProductUrl());
                productList_data.setWeight(reOrdResp.getOrders().get(i).getWeight());
                productList_data.setOriginalPrice(reOrdResp.getOrders().get(i).getOriginalPrice());
                productList_data.setDiscountPrice(reOrdResp.getOrders().get(i).getDiscountPrice());
                productList_data.setCategoryId(reOrdResp.getOrders().get(i).getCategoryId());
                productList_data.setUnit(reOrdResp.getOrders().get(i).getUnit());
                productList_data.setDescription(reOrdResp.getOrders().get(i).getDescription());
                productList_data.setTitle(reOrdResp.getOrders().get(i).getTitle());
                productList_data.setOutletId(reOrdResp.getOrders().get(i).getOutletId());
                productList_data.setOutletName(reOrdResp.getOrders().get(i).getOutletName());
                productList_data.setAverageRating(Integer.parseInt(reOrdResp.getOrders().get(i).getAverageRating()));
                productList_data.setProductImage(reOrdResp.getOrders().get(i).getProductImage());
                productList_data.setCartCount(reOrdResp.getOrders().get(i).getCartCount());

                Float total_covert = Float.valueOf(reOrdResp.getOrders().get(i).getTotal());
                Float qunatity_convert = Float.valueOf(reOrdResp.getOrders().get(i).getQty());

                Float Product_total_convert = total_covert / qunatity_convert;

//                    Log.e("product_total", "" + product_total);

                productList_data.setTotal("" + Product_total_convert);
                ArrayList<SelectedIngredient> chooseIngredientLists = new ArrayList<SelectedIngredient>();


                if (reOrdResp.getOrders().get(i).getIngredientList().size() != 0) {

                    SelectedIngredient selectedIngredient = new SelectedIngredient();
                    selectedIngredient.setIngredientTypeId(reOrdResp.getOrders().get(i).getIngredientList().get(0).getIngredientType());
                    selectedIngredient.setIngredientTypeName(reOrdResp.getOrders().get(i).getIngredientList().get(0).getIngredientTypeName());
                    selectedIngredient.setRequired(reOrdResp.getOrders().get(i).getIngredientList().get(0).getRequired());
                    selectedIngredient.setType(reOrdResp.getOrders().get(i).getIngredientList().get(0).getType());

                    ArrayList<IngredientList> ingredientLists = new ArrayList<IngredientList>();

                    for (int k = 0; k < reOrdResp.getOrders().get(i).getIngredientList().size(); k++) {

// Log.e("getIngredientList", "" + k);

                        IngredientList ingredientList = new IngredientList();
                        ingredientList.setIngredientId(reOrdResp.getOrders().get(i).getIngredientList().get(k).getIngredientId());
                        ingredientList.setIngredientName(reOrdResp.getOrders().get(i).getIngredientList().get(k).getIngredientName());
                        ingredientList.setPrice(reOrdResp.getOrders().get(i).getIngredientList().get(k).getIngredientPrice());

                        ingredientLists.add(ingredientList);

                    }

                    selectedIngredient.setIngredientLists(ingredientLists);

                    chooseIngredientLists.add(selectedIngredient);

                    insertOrupdate(productList_data, reOrdResp.getOrders().get(i).getQty(), chooseIngredientLists,
                            "" + reOrdResp.getOrders().get(i).getVendorId(), "" + Product_total_convert,
                            reOrdResp.getOrders().get(i).getCartCount());

                } else {

                    insertOrupdate(productList_data, reOrdResp.getOrders().get(i).getQty(), chooseIngredientLists,
                            "" + reOrdResp.getOrders().get(i).getVendorId(), "" + Product_total_convert,
                            reOrdResp.getOrders().get(i).getCartCount());

                }
            }

            Toast.makeText(OrderDetailActivity.this, "" + getString(R.string.my_ord_det_reorder_suc_msg), Toast.LENGTH_SHORT).show();

        }
//    }

//        } catch (Exception e) {
// Log.e("Exception", "" + e.getMessage());
//        }


    }

    private void insertOrupdate(ProductList_Data productList_data, int quentity, ArrayList<SelectedIngredient> chooseIngredientLists,
                                String vendor_id, String totalPrice, int count) {

        productRespository.insertProductData(productList_data, quentity, chooseIngredientLists, vendor_id, totalPrice, count);

    }

    private void ReturnReasonMethod() {

        final Dialog dialog = new Dialog(this);
        dialog.setTitle("ReturnReason");

        View view = getLayoutInflater().inflate(R.layout.return_dialog_box, null);
        ListView lv = (ListView) view.findViewById(R.id.returnlist);

        // Change MyActivity.this and myListOfItems to your own values
        CustomListAdapterDialog dialogadapter = new CustomListAdapterDialog(OrderDetailActivity.this, mobReturnReasons);

        lv.setAdapter(dialogadapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // do whatever you want
            }
        });

        dialog.setContentView(view);
        dialog.show();

    }

    private void CancelOrderConfirmDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderDetailActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));

        alertDialog.setMessage("" + getString(R.string.err_smg_my_ord_cancel_your_order_txt));

        alertDialog.setNegativeButton("" + getString(R.string.no_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("" + getString(R.string.yes_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OrderCancelMethod();
                    }
                });
        alertDialog.show();

    }

    private void OrderCancelMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

//        Log.e("loginPref", "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("loginPref", "" + loginPrefManager.getStringValue("user_id"));
//        Log.e("loginPref", "" + loginPrefManager.getStringValue("user_token"));

        APIService cancelService = Webdata.getRetrofit().create(APIService.class);
        cancelService.cancel_order_call("" + loginPrefManager.getStringValue("Lang_code"), "" + order_id, "" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<CancelOrder>() {
            @Override
            public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {
                progressDialog.dismiss();
//                Log.e("onResponse", "" + response.raw().toString());
                try {
                    if (response.body().getResponse().getHttpCode() == 200) {
                        Toast.makeText(OrderDetailActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

//                        LocalBroadcastManager.getInstance(OrderDetailActivity.this).sendBroadcast(new Intent("my_order_list"));
                        MyOrderDetailsRequestMethod();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(OrderDetailActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
//                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CancelOrder> call, Throwable t) {
                progressDialog.dismiss();
//                Log.e("failure", t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.store_prod_categ_menu, menu);
//        MenuItem itemCart = menu.findItem(R.id.action_carts);
//        icon = (LayerDrawable) itemCart.getIcon();
//        setBadgeCount(OrderDetailActivity.this, icon, "0");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_carts:
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void CartCountRequest() {

        APIService cart_count_service = Webdata.getRetrofit().create(APIService.class);
        cart_count_service.cart_count_call("" + loginPrefManager.getStringValue("user_id"),
                "" + loginPrefManager.getStringValue("Lang_code"),
                "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<CartCount>() {
            @Override
            public void onResponse(Call<CartCount> call, Response<CartCount> response) {
//                Log.e("cart_response", response.raw().toString());
                try {
                    if (response.body().getResponse().getHttpCode() == 200) {
                        String cart_count = "" + response.body().getResponse().getCartCount();
                        setBadgeCount(OrderDetailActivity.this, icon, cart_count);
                    }
                } catch (Exception e) {
//                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<CartCount> call, Throwable t) {
            }
        });

    }

    private void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
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
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}


