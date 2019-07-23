package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.app.oniontray.Adapters.DelivFragProductAdapter;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.NotificationListItemOffsetDecor;
import com.app.oniontray.RequestModels.ExpCheAdd;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.PromoCode;
import com.app.oniontray.RequestModels.PromoCodeResponse;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 22/6/18.
 */

public class ExpCheckProceedToCheckActivity extends LocalizationActivity {


    private TextView ec_p_to_c_fn_tv;
    private TextView ec_p_to_c_ln_tv;
    private TextView ec_p_to_c_add_tv;
    private TextView ec_p_to_c_lm_tv;
    private TextView ec_p_to_c_mob_tv;


    private TextInputLayout ec_p_to_c_del_inst_til;
    private EditText ec_p_to_c_del_inst_et;

    private RecyclerView ec_p_to_c_prod_list_rv;
    private DelivFragProductAdapter delivFragProductAdapter;


    private TextView ec_p_to_c_sub_total_tv;

    private TextView ec_p_to_c_ser_tax_lab_tv;
    private TextView ec_p_to_c_ser_tax_tv;


    private TextView ec_p_to_c_del_charges_tv;

//    private TableRow ec_p_to_c_del_promo_code_apply_tr;
//    private TextInputLayout ec_p_to_c_coupon_code_til;
//    private EditText ec_p_to_c_coupon_code_et;
//    private TextView ec_p_to_c_apply_btn_tv;

    private TableRow ec_p_to_c_del_val_promo_code_tr;
    private TextView ec_p_to_c_del_val_promo_code_tv;
    private TextView ec_p_to_c_del_val_promo_code_amt_tv;
    private TextView ec_p_to_c_promo_code_remove_btn_tv;

    private TextView ec_p_to_c_grand_tot_tv;

    private TableRow ec_p_to_c_coupon_apply_tr;
    private EditText ec_p_to_c_coupon_et;

    private Button ec_p_to_c_coupon_apply_btn;

    private TextView ec_p_to_c_coupon_code_err_msg_tv;
    private TableRow ec_p_to_c_coupon_dis_tr;
    private TextView ec_p_to_c_coupon_disc_amt_holder_tv;
    private TextView ec_p_to_c_coupon_dis_amt_tv;


    private TableRow ec_p_to_c_coupon_remove_tr;
    private TextView ec_p_to_c_coupon_tv;

    private Button ec_p_to_c_coupon_remove_btn;

    private TableRow ec_p_to_c_coupon_amt_pay_tr;
    private TextView ec_p_to_c_amt_pay_holder_tv;
    private TextView ec_p_to_c_amt_pay_tv;


    private Button ec_p_to_c_pro_pay_but;

    private ExpCheAdd expCheAdd;
    private OutletDetails outletDetails;
    private String vendor_id;

    private String grand_total_at = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_check_proceed_to_check_lay);

        Toolbar ec_p_to_c_tb = findViewById(R.id.ec_p_to_c_tb);
        ec_p_to_c_tb.setTitle("");
        ec_p_to_c_tb.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(ec_p_to_c_tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
        upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        ec_p_to_c_tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getIntent().hasExtra("exp_che_add_det") && getIntent().hasExtra("out_det") && getIntent().hasExtra("vendor_id")) {
            expCheAdd = (ExpCheAdd) getIntent().getSerializableExtra("exp_che_add_det");
            outletDetails = (OutletDetails) getIntent().getSerializableExtra("out_det");
            vendor_id = getIntent().getStringExtra("vendor_id");
        }


        initView();
        setServiceTaxAmount();
    }


    private void initView() {

        ec_p_to_c_fn_tv = findViewById(R.id.ec_p_to_c_fn_tv);
        ec_p_to_c_ln_tv = findViewById(R.id.ec_p_to_c_ln_tv);
        ec_p_to_c_add_tv = findViewById(R.id.ec_p_to_c_add_tv);
        ec_p_to_c_lm_tv = findViewById(R.id.ec_p_to_c_lm_tv);
        ec_p_to_c_mob_tv = findViewById(R.id.ec_p_to_c_mob_tv);

        ec_p_to_c_del_inst_til = findViewById(R.id.ec_p_to_c_del_inst_til);
        ec_p_to_c_del_inst_et = findViewById(R.id.ec_p_to_c_del_inst_et);
        ec_p_to_c_del_inst_et.setHint(getString(R.string.del_frag_del_inst_txt));
        ec_p_to_c_del_inst_et.addTextChangedListener(new MyTextWatcher(ec_p_to_c_del_inst_et));

        ec_p_to_c_prod_list_rv = findViewById(R.id.ec_p_to_c_prod_list_rv);
        ec_p_to_c_prod_list_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ec_p_to_c_prod_list_rv.setHasFixedSize(true);
        ec_p_to_c_prod_list_rv.addItemDecoration(new NotificationListItemOffsetDecor(this, R.dimen.notifications_list_item_row_line_hight));


        ec_p_to_c_sub_total_tv = findViewById(R.id.ec_p_to_c_sub_total_tv);

        ec_p_to_c_ser_tax_lab_tv = findViewById(R.id.ec_p_to_c_ser_tax_lab_tv);
        ec_p_to_c_ser_tax_tv = findViewById(R.id.ec_p_to_c_ser_tax_tv);

        ec_p_to_c_del_charges_tv = findViewById(R.id.ec_p_to_c_del_charges_tv);

//        ec_p_to_c_del_promo_code_apply_tr = (TableRow) findViewById(R.id.ec_p_to_c_del_promo_code_apply_tr);
//        ec_p_to_c_coupon_code_til = (TextInputLayout) findViewById(R.id.ec_p_to_c_coupon_code_til);
//        ec_p_to_c_coupon_code_et = (EditText) findViewById(R.id.ec_p_to_c_coupon_code_et);
//        ec_p_to_c_coupon_code_et.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        ec_p_to_c_coupon_code_et.addTextChangedListener(new MyTextWatcher(ec_p_to_c_coupon_code_et));
//        ec_p_to_c_apply_btn_tv = (TextView) findViewById(R.id.ec_p_to_c_apply_btn_tv);

        ec_p_to_c_del_val_promo_code_tr = findViewById(R.id.ec_p_to_c_del_val_promo_code_tr);
        ec_p_to_c_del_val_promo_code_tv = findViewById(R.id.ec_p_to_c_del_val_promo_code_tv);

        ec_p_to_c_del_val_promo_code_amt_tv = findViewById(R.id.ec_p_to_c_del_val_promo_code_amt_tv);
        ec_p_to_c_promo_code_remove_btn_tv = findViewById(R.id.ec_p_to_c_promo_code_remove_btn_tv);

        ec_p_to_c_grand_tot_tv = findViewById(R.id.ec_p_to_c_grand_tot_tv);

        ec_p_to_c_coupon_apply_tr = findViewById(R.id.ec_p_to_c_coupon_apply_tr);
        ec_p_to_c_coupon_et = findViewById(R.id.ec_p_to_c_coupon_et);
        ec_p_to_c_coupon_et.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        ec_p_to_c_coupon_apply_btn = findViewById(R.id.ec_p_to_c_coupon_apply_btn);
        ec_p_to_c_coupon_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ec_p_to_c_coupon_et.getText().toString().trim().length() == 0) {
                    ec_p_to_c_coupon_code_err_msg_tv.setVisibility(View.VISIBLE);
                    return;
                } else {
                    ec_p_to_c_coupon_code_err_msg_tv.setVisibility(View.GONE);
                }

                ApplyCouponCodeMethod();
            }
        });

        ec_p_to_c_coupon_code_err_msg_tv = findViewById(R.id.ec_p_to_c_coupon_code_err_msg_tv);

        ec_p_to_c_coupon_dis_tr = findViewById(R.id.ec_p_to_c_coupon_dis_tr);
        ec_p_to_c_coupon_disc_amt_holder_tv = findViewById(R.id.ec_p_to_c_coupon_disc_amt_holder_tv);
        ec_p_to_c_coupon_dis_amt_tv = findViewById(R.id.ec_p_to_c_coupon_dis_amt_tv);

        ec_p_to_c_coupon_remove_tr = findViewById(R.id.ec_p_to_c_coupon_remove_tr);

        ec_p_to_c_coupon_tv = findViewById(R.id.ec_p_to_c_coupon_tv);

        ec_p_to_c_coupon_remove_btn = findViewById(R.id.ec_p_to_c_coupon_remove_btn);
        ec_p_to_c_coupon_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveCouponRequestMethod();
            }
        });

        ec_p_to_c_coupon_amt_pay_tr = findViewById(R.id.ec_p_to_c_coupon_amt_pay_tr);
        ec_p_to_c_amt_pay_holder_tv = findViewById(R.id.ec_p_to_c_amt_pay_holder_tv);
        ec_p_to_c_amt_pay_tv = findViewById(R.id.ec_p_to_c_amt_pay_tv);

        ec_p_to_c_pro_pay_but = findViewById(R.id.ec_p_to_c_pro_pay_but);

        ec_p_to_c_pro_pay_but.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        ec_p_to_c_pro_pay_but.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        ec_p_to_c_pro_pay_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProceedToPaymentMethod();
            }
        });

        if (expCheAdd != null && outletDetails != null) {
            updateExpCheckOutValues();
        }

        if (outletDetails.getTaxType() == 2) {
            ec_p_to_c_ser_tax_lab_tv.setVisibility(View.GONE);
            ec_p_to_c_ser_tax_tv.setVisibility(View.GONE);
        } else {
            ec_p_to_c_ser_tax_lab_tv.setVisibility(View.VISIBLE);
            ec_p_to_c_ser_tax_tv.setText(outletDetails.getTaxLabelName().trim());
            ec_p_to_c_ser_tax_tv.setVisibility(View.VISIBLE);
        }

    }


    private void updateExpCheckOutValues() {
        try {

            ec_p_to_c_fn_tv.setText(expCheAdd.getFirst_name());
            ec_p_to_c_ln_tv.setText(expCheAdd.getLast_name());
            ec_p_to_c_add_tv.setText(expCheAdd.getAddress());
            ec_p_to_c_lm_tv.setText(expCheAdd.getLand_mark());
            ec_p_to_c_mob_tv.setText(expCheAdd.getPhone_no());

            delivFragProductAdapter = new DelivFragProductAdapter(ExpCheckProceedToCheckActivity.this, productRespository.getCartProductList());
            ec_p_to_c_prod_list_rv.setAdapter(delivFragProductAdapter);

            ec_p_to_c_sub_total_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));

            if (outletDetails.getDeliveryType() == 1) {
                ec_p_to_c_del_charges_tv.setText(getResources().getString(R.string.free));
                outletDetails.setDeliveryCost("0");
            } else if (outletDetails.getDeliveryType() == 2) {
                ec_p_to_c_del_charges_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(outletDetails.getDeliveryCostFixed()))));
                outletDetails.setDeliveryCost("" + outletDetails.getDeliveryCostFixed());
            } else if (outletDetails.getDeliveryType() == 3) {
                ec_p_to_c_del_charges_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(outletDetails.getDeliveryCostFixed()))));
                outletDetails.setDeliveryCost("" + outletDetails.getDeliveryCostFixed());
            }

            setGrandTotalAmount();

        } catch (Exception e) {
            Log.e("Exception", "- " + e.getMessage());
        }
    }


    private void setGrandTotalAmount() {

        if (outletDetails.getDeliveryType() == 1) {

            if (outletDetails.getTaxType() == 2) {
                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));
                grand_total_at = "" + productRespository.totalPrice();
            } else {
                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((Float.parseFloat(productRespository.totalPrice()) /*+ setServiceTaxAmount()*/))));
                grand_total_at = "" + (Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount());

            }

        } else if (outletDetails.getDeliveryType() == 2) {

            if (outletDetails.getTaxType() == 2) {

                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(String.valueOf((Float.parseFloat(productRespository.totalPrice()) + (Float.parseFloat(outletDetails.getDeliveryCostFixed()))))))));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            } else {
                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(String.valueOf(Float.parseFloat(productRespository.totalPrice()) /*+ setServiceTaxAmount()*/ + (Float.parseFloat(outletDetails.getDeliveryCostFixed())))))));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + (setServiceTaxAmount()) + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            }

        } else if (outletDetails.getDeliveryType() == 3) {

            if (outletDetails.getTaxType() == 2) {

                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((Float.parseFloat(productRespository.totalPrice()) + (Float.parseFloat(outletDetails.getDeliveryCostFixed()))))));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            } else {

                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()) /*+ setServiceTaxAmount()*/ + (Float.parseFloat(outletDetails.getDeliveryCostFixed())))));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            }

        }

    }


    private float setServiceTaxAmount() {
        float serviceTax = 0;
        if (outletDetails.getTaxType() == 1) {
            Log.e("productRep", "++__" + productRespository.totalPrice());
            Log.e("outletDetails", "++__" + outletDetails.getTaxPercentage());
            serviceTax = Float.parseFloat("" + productRespository.totalPrice()) * Float.parseFloat("" + outletDetails.getTaxPercentage()) / 100;
            serviceTax = Float.parseFloat(loginPrefManager.GetEngDecimalFormatValues(serviceTax));
            ec_p_to_c_ser_tax_tv.setText(loginPrefManager.getFormatCurrencyValue(String.valueOf(serviceTax)));
        }
        outletDetails.setServiceTax("" + serviceTax);
        return serviceTax;
    }


    public Float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


    private class MyTextWatcher implements TextWatcher {

        private final View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
                case R.id.ec_p_to_c_del_inst_et:
                    validateDelivery();
                    break;
//                case R.id.ec_p_to_c_coupon_code_et:
//                    validatePromo();
//                    break;
            }

        }
    }


    private boolean validateDelivery() {
        if (ec_p_to_c_del_inst_et.getText().toString().trim().isEmpty()) {
            ec_p_to_c_del_inst_til.setError(getString(R.string.err_delivery_detail));
            return false;
        } else {
            ec_p_to_c_del_inst_til.setErrorEnabled(false);
        }
        return true;
    }


    private boolean validatePromo() {
        if (ec_p_to_c_coupon_et.getText().toString().trim().isEmpty()) {
//            ec_p_to_c_coupon_code_til.setError(getString(R.string.err_promo_detail));
            return false;
        } else {
//            ec_p_to_c_coupon_code_til.setErrorEnabled(false);
        }
        return true;
    }


    private void ApplyCouponCodeMethod() {

        try {

            Log.e("Lang_code", "- " + loginPrefManager.getStringValue("Lang_code"));
            Log.e("getEmail", "- " + expCheAdd.getEmail());
            Log.e("getPhone_no", "- " + expCheAdd.getPhone_no());
            Log.e("getOutletsId", "- " + outletDetails.getOutletsId());
            Log.e("grand_total_at", "- " + grand_total_at);
            Log.e("ec_p_to_c_coupon_et", "- " + ec_p_to_c_coupon_et.getText().toString());

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService promoCodeService = Webdata.getRetrofit().create(APIService.class);
            promoCodeService.guestUpdatePromoCodeCall(loginPrefManager.getStringValue("Lang_code"),
                    expCheAdd.getEmail(), expCheAdd.getPhone_no(),
                    "" + outletDetails.getOutletsId(), "" + grand_total_at,
                    ec_p_to_c_coupon_et.getText().toString()).enqueue(new Callback<PromoCode>() {
                @Override
                public void onResponse(Call<PromoCode> call, Response<PromoCode> response) {

                    try {

                        progressDialog.dismiss();
                        Log.e("promo_code_call", "- " + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {
                            CouponCodeSuccessMethod(response.body().getResponse());
                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(ExpCheckProceedToCheckActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("DelFrag Promo Exc", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<PromoCode> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("DelFrag Promo onFail", "" + t.getMessage());
                }
            });

        } catch (Exception e) {
//            Log.e("ApplyPromoCode", "Exception" + e.getMessage());
        }

    }


    private void CouponCodeSuccessMethod(PromoCodeResponse promoCodeResponse) {

        try {

            ec_p_to_c_coupon_apply_tr.setVisibility(View.GONE);
            ec_p_to_c_coupon_remove_tr.setVisibility(View.VISIBLE);

            ec_p_to_c_coupon_dis_tr.setVisibility(View.VISIBLE);
            ec_p_to_c_coupon_amt_pay_tr.setVisibility(View.VISIBLE);

            ec_p_to_c_coupon_tv.setText("" + promoCodeResponse.getCouponDetails().getCouponCode());


            if (promoCodeResponse.getCouponDetails().getOfferType() == 1) {

                ec_p_to_c_del_val_promo_code_tv.setText("" + getString(R.string.promo_code_applied_succ_txt) + " " + promoCodeResponse.getCouponDetails().getCouponCode());
                ec_p_to_c_del_val_promo_code_amt_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(promoCodeResponse.getCouponDetails().getOfferAmount().trim()))));

                double promo_code_amt = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferAmount());
                double total_amt = Double.parseDouble("" + grand_total_at);
                double subtract_anount = (total_amt - promo_code_amt);

                grand_total_at = "" + subtract_anount;
                ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) total_amt))); // new

                ec_p_to_c_coupon_dis_amt_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) promo_code_amt)));
                ec_p_to_c_amt_pay_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) subtract_anount)));

                outletDetails.setApplyCoupon(true);
                outletDetails.setCouponAmount("" + promoCodeResponse.getCouponDetails().getOfferAmount());
                outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
                outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

            } else {

                double grand_total = Double.parseDouble("" + grand_total_at);
                double average_value = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferPercentage());
                double subtract_amount = (grand_total * average_value) / 100;
                double final_amt = (grand_total - subtract_amount);
                grand_total_at = "" + final_amt;

                ec_p_to_c_amt_pay_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) grand_total))); // new

                ec_p_to_c_coupon_dis_amt_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) subtract_amount)));
                ec_p_to_c_amt_pay_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) final_amt)));

                outletDetails.setApplyCoupon(true);
                outletDetails.setCouponAmount("" + Double.toString(subtract_amount));
                outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
                outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

            }

        } catch (Exception e) {
//            Log.e("CouponCodeSuccessMethod", "" + e.getMessage().toString());
        }

    }


    private void RemoveCouponRequestMethod() {

        ec_p_to_c_coupon_apply_tr.setVisibility(View.VISIBLE);
        ec_p_to_c_coupon_remove_tr.setVisibility(View.GONE);

        ec_p_to_c_coupon_et.setText("");

        ec_p_to_c_coupon_dis_tr.setVisibility(View.GONE);
        ec_p_to_c_coupon_amt_pay_tr.setVisibility(View.GONE);

        double promo_code_amt = Double.parseDouble("" + outletDetails.getCouponAmount());
        double old_grand_total_amt = Double.parseDouble("" + grand_total_at);
        double new_grand_total_amt = (promo_code_amt + old_grand_total_amt);

        grand_total_at = "" + new_grand_total_amt;
        ec_p_to_c_grand_tot_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) new_grand_total_amt))); // new

        outletDetails.setApplyCoupon(false);
        outletDetails.setCouponAmount(null);
        outletDetails.setCouponId(null);
        outletDetails.setCouponType(null);

    }


    private void ProceedToPaymentMethod() {

        if (ec_p_to_c_del_inst_et.getText().toString().isEmpty()) {
            outletDetails.setDeliveryInstruction("");
        } else {
            outletDetails.setDeliveryInstruction("" + ec_p_to_c_del_inst_et.getText().toString().trim());
        }


        if (!grand_total_at.equals("0")) {
            outletDetails.setGrandTotal(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat("" + grand_total_at)));
        }

        SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
            curr_format.setNumberFormat(nf);
        }
        outletDetails.setDeliveryDate(curr_format.format(new Date()));



//        if (promo_offer_amount != 0) {
//            outletDetails.setDeliveryPromoCode("" + promo_offer_amount);
//        }

        outletDetails.setSubTotal("" + productRespository.totalPrice());

        outletDetails.setDeliveryType(1); //  delivery method

        Intent pro_pay = new Intent(this, ExpCheckProceedToPayActivity.class);
        pro_pay.putExtra("exp_che_add_det", expCheAdd);
        pro_pay.putExtra("out_det", outletDetails);
        pro_pay.putExtra("vendor_id", vendor_id);
        startActivity(pro_pay);

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
