package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.OrdConfiRecyadapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OrdConfListItemOffsetDecor;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.Utils.NetworkStatus;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class OrderConfirmationActivity extends LocalizationActivity {

//    private ExpandableHeightListView Input_order_conf_list;
//    private OrderConfirmationAdapter OrderAdapter;

    private TextView Input_sub_tot, Input_tot_amt, Input_service_tax, Input_del_charge;
    private TextView Input_pay_type;
    private TextView Input_contact_add;

    private LinearLayout order_conf_deliv_charge_layout;

    private OutletDetails response;
    private String del_text = "";
    private String pay_type = "";

    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
    private Toolbar order_confirmation_toolbar_id;

    private TableRow ord_conf_deliv_inst_table_lay;


    private TextView service_tax_label;
    private LinearLayout serviceTaxLayout;


    private TextView input_del_instruction;
    private  TextView ord_title;
    private  TextView thank_u;
    private TextView ord_conf_add_holder_txt_view;

    private TableRow order_conf_promo_code_table_row_lay;
    private TextView order_conf_promo_code_txt;

    private TableRow order_conf_deliv_date_table_lay;

    private TextView order_conf_deliv_date_txt;
    private TextView tvDeliveryCharge;

    private TableRow order_conf_deliv_slot_table_lay;
    private TextView order_conf_delivery_slot_time;
    private TextView tv_order_delivery_date;

    private RecyclerView ord_conf_prod_list_recy_view;
    private OrdConfiRecyadapter ordConfiRecyadapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation_page_lay);

        response = (OutletDetails) getIntent().getExtras().getSerializable("ORDER");

        del_text = getIntent().getStringExtra("DELIVERY_TEXT");
        pay_type = getIntent().getStringExtra("PAYMENT_TYPE");

        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        order_confirmation_toolbar_id = (Toolbar) findViewById(R.id.order_confirmation_toolbar_id);
        order_confirmation_toolbar_id.setTitle("");
        order_confirmation_toolbar_id.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(order_confirmation_toolbar_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(OrderConfirmationActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        order_confirmation_toolbar_id.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Input_order_conf_list = (ExpandableHeightListView) findViewById(R.id.order_conf_sum_list);

        ord_title = findViewById(R.id.ord_title);
        ord_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        Input_tot_amt = (TextView) findViewById(R.id.order_conf_tot);
        Input_sub_tot = (TextView) findViewById(R.id.order_conf_sub_tot);
        Input_service_tax = (TextView) findViewById(R.id.order_conf_tax_tot);
        Input_del_charge = (TextView) findViewById(R.id.order_conf_del_charge);
        tvDeliveryCharge = (TextView) findViewById(R.id.tvDeliveryCharge);
        tv_order_delivery_date = (TextView) findViewById(R.id.tv_order_delivery_date);
        thank_u = findViewById(R.id.thank_u);
        thank_u.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        order_conf_deliv_charge_layout = (LinearLayout) findViewById(R.id.order_conf_deliv_charge_layout);

        Input_pay_type = (TextView) findViewById(R.id.order_conf_delivery_pay_type);
        Input_contact_add = (TextView) findViewById(R.id.order_conf_del_contact_ad);

        input_del_instruction = (TextView) findViewById(R.id.order_conf_delivery_inst);
        ord_conf_add_holder_txt_view = (TextView) findViewById(R.id.ord_conf_add_holder_txt_view);


        order_conf_promo_code_table_row_lay = (TableRow) findViewById(R.id.order_conf_promo_code_table_row_lay);
        order_conf_promo_code_txt = (TextView) findViewById(R.id.order_conf_promo_code_txt);

        order_conf_deliv_date_table_lay = (TableRow) findViewById(R.id.order_conf_deliv_date_table_lay);
        order_conf_deliv_date_txt = (TextView) findViewById(R.id.order_conf_deliv_date_txt);

        order_conf_deliv_slot_table_lay = (TableRow) findViewById(R.id.order_conf_deliv_slot_table_lay);
        order_conf_delivery_slot_time = (TextView) findViewById(R.id.order_conf_delivery_slot_time);

        // delivery instruction table row layout reference.
        ord_conf_deliv_inst_table_lay = (TableRow) findViewById(R.id.ord_conf_deliv_inst_table_lay);

        serviceTaxLayout = (LinearLayout) findViewById(R.id.service_tax_layout);
        service_tax_label = (TextView) findViewById(R.id.service_tax_label);


        ord_conf_prod_list_recy_view = (RecyclerView) findViewById(R.id.ord_conf_prod_list_recy_view);
        ord_conf_prod_list_recy_view.setLayoutManager(new LinearLayoutManager(OrderConfirmationActivity.this));
        ord_conf_prod_list_recy_view.setHasFixedSize(true);
        ord_conf_prod_list_recy_view.addItemDecoration(new OrdConfListItemOffsetDecor(OrderConfirmationActivity.this, R.dimen.notifications_list_item_row_line_hight));


        try {

            ordConfiRecyadapter = new OrdConfiRecyadapter(OrderConfirmationActivity.this, productRespository.getCartProductList());
            ord_conf_prod_list_recy_view.setAdapter(ordConfiRecyadapter);







            if (response.getTaxType() == 1) {

                serviceTaxLayout.setVisibility(View.VISIBLE);

//                Log.e("service_tax_label", "-" + response.getTaxLabelName());
//                service_tax_label.setText(response.getTaxLabelName());

                String lable_name = "" + response.getTaxLabelName().trim();

                service_tax_label.setText(lable_name);

                Input_service_tax.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.getServiceTax()))));

            } else {
                serviceTaxLayout.setVisibility(View.GONE);
            }


            if (!response.getDeliveryCost().equals("0")) {
                Input_del_charge.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.getDeliveryCost()))));
            } else {
                Input_del_charge.setText(getString(R.string.free));
            }

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
        }

//        if (response.getPaymentMode() == 2) {
//
//            order_conf_deliv_charge_layout.setVisibility(View.GONE);
//
//        } else {
//
////            if (loginPrefManager.getStringValue("delivery_type").equals("1")) {
////                Input_del_charge.setText(getResources().getString(R.string.free));
////            } else if (loginPrefManager.getStringValue("delivery_type").equals("2")) {
////                Input_del_charge.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.getStringValue("delivery_cost_fixed")));
////            } else {
////                Input_del_charge.setText(loginPrefManager.getFormatCurrencyValue("" + (Integer.parseInt(loginPrefManager.getStringValue("delivery_cost_fixed")) +
////                        Integer.parseInt(loginPrefManager.getStringValue("delivery_km_fixed")))));
////            }
//
//        }

        try {

            Input_pay_type.setText("" + pay_type);

            Input_contact_add.setText(response.getDeliveryAddress());

            // Delivery instructin validation for visible or invisible.
            if (response.getDeliveryInstruction().trim().isEmpty()) {
                ord_conf_deliv_inst_table_lay.setVisibility(View.GONE);
            } else {
                input_del_instruction.setText("" + response.getDeliveryInstruction().trim());
            }

            if (response.getApplyCoupon()) {

//                Float grand_total_amt = Float.parseFloat("" + response.getGrandTotal());
//                Float coupon_promo_amt = Float.parseFloat("" + response.getCouponAmount());
//
//                Float redu_coupon_amt = (grand_total_amt - coupon_promo_amt);
//
//                Input_tot_amt.setText(loginPrefManager.getFormatCurrencyValue(String.format("%.2f", redu_coupon_amt)));
//
                Float total_amt = Float.parseFloat("" + response.getGrandTotal());
                Float service_tax = Float.parseFloat("" + response.getServiceTax());
                Float deliv_charge = Float.parseFloat("" + response.getDeliveryCost());

                Float sub_total = (total_amt - (service_tax + deliv_charge));
                Input_sub_tot.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(sub_total))));

                order_conf_promo_code_table_row_lay.setVisibility(View.VISIBLE);
                order_conf_promo_code_txt.setText("" + loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.getCouponAmount()))));
            }else{
                Input_sub_tot.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.getSubTotal()))));

            }

            Input_tot_amt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.getGrandTotal()))));
            order_conf_deliv_date_table_lay.setVisibility(View.VISIBLE);

            order_conf_deliv_date_txt.setText(""+ response.getDeliveryDate());

            if (response.getPaymentOption() == 1) {
                ord_conf_add_holder_txt_view.setText("" + getString(R.string.my_ord_det_deli_address_txt));

                tv_order_delivery_date.setText(getString(R.string.my_ord_det_deliverd_date_txt));

                Input_contact_add.setText("" + response.getDeliveryAddress());
                tvDeliveryCharge.setText(getString(R.string.deliver_charges));

//                if (!response.getDelivery_date_time().isEmpty()) {
//                    Date start_date = old_date_format.parse("" + response.getDelivery_date_time());
//                }

//                order_conf_deliv_slot_table_lay.setVisibility(View.VISIBLE);
//                order_conf_delivery_slot_time.setText("" + response.getDeliveryTime());

//                ord_conf_add_holder_txt_view.setText("" + getString(R.string.my_ord_det_deli_address_txt));
//
//                for (int i = 0; i < response.getAddressList().size(); i++) {
//                    if (response.getAddressList().get(i).getAddressId().toString().equals("" + response.getDelivery_Addr_id())) {
//                        Input_contact_add.setText("" + response.getAddressList().get(i).getAddress());
//                    }
//                }

            } else {
                tvDeliveryCharge.setText(getString(R.string.platform_charge));
                tv_order_delivery_date.setText(getString(R.string.my_ord_det_pickup_date_txt));

                ord_conf_add_holder_txt_view.setText("" + getString(R.string.my_ord_det_pick_up_address_txt));

                Input_contact_add.setText("" + response.getContactAddress());

//                ord_conf_add_holder_txt_view.setText("" + getString(R.string.my_ord_det_deli_address_txt));
//
//                for (int i = 0; i < response.getAddressList().size(); i++) {
//                    if (response.getAddressList().get(i).getAddressId().toString().equals("" + response.getDelivery_Addr_id())) {
//                        Input_contact_add.setText("" + response.getAddressList().get(i).getAddress());
//                    }
//                }
//                order_conf_deliv_date_table_lay.setVisibility(View.VISIBLE);
//                if (!response.getDelivery_date_time().isEmpty()) {
//                    Date start_date = old_date_format.parse("" + response.getDelivery_date_time());
//                    order_conf_deliv_date_txt.setText("" + new_date_format.format(start_date));
//                }

            }

//            Log.e("del_text", "" + del_text);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            Log.e("ParseException", "" + e.getMessage());
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                productRespository.ClearCart();

                Intent menu_activity = new Intent(OrderConfirmationActivity.this, BaseMenuTabActivity.class);
                menu_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(menu_activity);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        productRespository.ClearCart();
        Intent menu_activity = new Intent(OrderConfirmationActivity.this, BaseMenuTabActivity.class);
        menu_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(menu_activity);
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productRespository.ClearCart();
    }
}
