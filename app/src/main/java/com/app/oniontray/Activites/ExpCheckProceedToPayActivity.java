package com.app.oniontray.Activites;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.app.oniontray.CustomViews.CustomViewPager;
import com.app.oniontray.Fragments.ExpCheckCashOnDelFragment;
import com.app.oniontray.Fragments.ExpCheckPayPalFragment;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ExpCheAdd;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nextbrain on 23/6/18.
 */

public class ExpCheckProceedToPayActivity extends LocalizationActivity implements TabLayout.OnTabSelectedListener {


    private TabLayout ec_pro_pay_tl;
    private CustomViewPager ec_pro_pay_vp;


    /*Payment type fragment*/

    private ExpCheckPayPalFragment expCheckPayPalFragment;
    private ExpCheckCashOnDelFragment expCheckCashOnDelFragment;
//    private ExpCheckRazorPayFragment expCheckRazorPayFragment;
    private JSONObject parent;
    /*---------------------*/


    private LoginPrefManager loginPrefManager;


    private ExpCheAdd expCheAdd;
    private OutletDetails outletDetails;
    private String vendor_id;
    private static final String TAG = ProceedToPayment.class.getSimpleName();
    private final Calendar current_date = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_check_proceed_to_pay_lay);

        loginPrefManager = new LoginPrefManager(ExpCheckProceedToPayActivity.this);

        Toolbar ec_pro_pay_tb = (Toolbar) findViewById(R.id.ec_pro_pay_tb);
        ec_pro_pay_tb.setTitle("");
        ec_pro_pay_tb.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(ec_pro_pay_tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);

        ec_pro_pay_tb.setNavigationOnClickListener(new View.OnClickListener() {
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


        TextView ec_pro_pay_tit_tax_tv = (TextView) findViewById(R.id.ec_pro_pay_tit_tax_tv);
        TextView ec_pro_pay_tit_amt_tv = (TextView) findViewById(R.id.ec_pro_pay_tit_amt_tv);

        ec_pro_pay_tit_tax_tv.setText(" (" + getString(R.string.inclusive_of_tax) + ")");
        ec_pro_pay_tit_amt_tv.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));


        Bundle bundle = new Bundle();
        bundle.putSerializable("exp_che_add_det", expCheAdd);
        bundle.putSerializable("out_det", outletDetails);
        bundle.putString("vendor_id", vendor_id);


        expCheckPayPalFragment = new ExpCheckPayPalFragment();
        expCheckPayPalFragment.setArguments(bundle);


        expCheckCashOnDelFragment = new ExpCheckCashOnDelFragment();
        expCheckCashOnDelFragment.setArguments(bundle);
//
//        expCheckRazorPayFragment = new ExpCheckRazorPayFragment();
//        expCheckRazorPayFragment.setArguments(bundle);

        ec_pro_pay_tl = (TabLayout) findViewById(R.id.ec_pro_pay_tl);

        //Initializing viewPager
        ec_pro_pay_vp = (CustomViewPager) findViewById(R.id.ec_pro_pay_vp);
        setupViewPager();
        ec_pro_pay_tl.setupWithViewPager(ec_pro_pay_vp);


    }


    private void setupViewPager() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(expCheckPayPalFragment, getResources().getString(R.string.tab_one_name));
//        viewPagerAdapter.addFragment(expCheckRazorPayFragment, getResources().getString(R.string.tab_two_name));
        viewPagerAdapter.addFragment(expCheckCashOnDelFragment, getResources().getString(R.string.tab_three_name));

        ec_pro_pay_vp.setAdapter(viewPagerAdapter);
        ec_pro_pay_tl.setupWithViewPager(ec_pro_pay_vp);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ec_pro_pay_vp.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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

//    @Override
//    public void paymentProcess() {
//        startPayment();
//
//    }

//    private void startPayment() {
//
//
//        Checkout checkout = new Checkout();
//
//        try {
//
//            JSONObject options = new JSONObject();
//
//            Log.e("amout", "" + Math.round(Float.parseFloat(outletDetails.getGrandTotal())) * 100);
//            Float amount = (Float.parseFloat(outletDetails.getGrandTotal())) * 100;
//            options.put("name", "" + outletDetails.getOutletName());
//            options.put("amount", amount);
//
///*
//You need to pass current activity in order to let Razorpay create CheckoutActivity
//*/
//
//            checkout.open(ExpCheckProceedToPayActivity.this, options);
//
//        } catch (Exception e) {
//
//            Toast.makeText(getApplicationContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
//                    .show();
//            e.printStackTrace();
//        }
//    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
//    @SuppressWarnings("unused")
//    @Override
//    public void onPaymentSuccess(String razorpayPaymentID) {
//        try {
//
//
//// Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
//            GeneratePaymentArrayFormat("", razorpayPaymentID);
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentSuccess", e);
//        }
//    }


    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
//    @SuppressWarnings("unused")
//    @Override
//    public void onPaymentError(int code, String response) {
//        try {
//            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            Log.e(TAG, "Exception in onPaymentError", e);
//        }
//    }

//    private void GeneratePaymentArrayFormat(String razorID, String razorpayPaymentID) {
//
//        try {
//
////            {"admin_commission":13.81,"vendor_commission":251,"store_id":"18","outlet_id":"1","outlet_name":"Mankoshtkom",
////                    "vendor_key":"Mankoshtkom","total":264.81,"sub_total":251,"service_tax":13.81,"order_status":1,
////                    "order_key":"Jn95y0JzclHnPMYPxokCKqZzTWPhGVQ2","invoice_id":"EY7ZdFzJfJzhdnisaBAsb91WNwNaIDhT",
////                    "transaction_id":"leFHQWGVBBl3Y3Meq4eatolmHIlprBHS","transaction_staus":1,"transaction_amount":264.81,
////                    "payer_id":"pzXnIRpFPA3dgXZlg0dIXaUQGG80BdZQ","currency_code":"SAR  ","payment_gateway_id":18,
////                    "delivery_charge":0,"payment_status":0,"payment_gateway_commission":0,"delivery_instructions":"sdsadfsf",
////                    "delivery_date":"NOW()","order_type":"1","coupon_id":0,"coupon_amount":0,"coupon_type":0,"delivery_cost":0,
////                    "items":[{"product_id":"196","quantity":"1","discount_price":"10","special_req":"","ingredients":"","item_offer":0},
////                {"product_id":"220","quantity":"1","discount_price":"205","special_req":"","ingredients":"","item_offer":0},
////                {"product_id":"226","quantity":"1","discount_price":"32","special_req":"","ingredients":{"0":{"ingredient_id":"7","price":"1"},
////                    "1":{"ingredient_id":"10","price":"2"},"2":{"ingredient_id":"4","price":"1"},
////                    "ingredient_names":"Hydrolyzed Animal Protein, Yeast, Herb farm"},"item_offer":0}]}
//
//            JSONObject payment_object = new JSONObject();
//
//            productRespository.totalPrice();
//
//            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
//                    Math.round(Float.parseFloat(outletDetails.getCommission_amount())) / 100));
//
//           /* int admin_commision = (((commission_one) + Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))) +
//                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));*/
//
//            payment_object.put("admin_commission", "" + commission_one);
//            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
//            payment_object.put("vendor_commission", "" + vender_commision);
//
//            payment_object.put("store_id", "" + vendor_id);
//            payment_object.put("outlet_id", "" + outletDetails.getOutletsId());
//
//            payment_object.put("outlet_name", "" + outletDetails.getOutletName());
//            payment_object.put("vendor_key", "" + outletDetails.getOutletName());
//
//            payment_object.put("total", "" + outletDetails.getGrandTotal());
//            payment_object.put("sub_total", "" + outletDetails.getSubTotal());
//            payment_object.put("razor_payment_gateway_id", "" + razorpayPaymentID);
//
//
//            if (outletDetails.getTaxType() == 2) {
//                payment_object.put("service_tax", "" + outletDetails.getServiceTax());
//                payment_object.put("tax_label_name", "");
//                payment_object.put("tax_percentage", "");
//            } else {
//                payment_object.put("service_tax", "" + outletDetails.getServiceTax());
//                payment_object.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
//                payment_object.put("tax_percentage", "" + outletDetails.getTaxPercentage());
//            }
//
////            payment_object.put("service_tax", "" + outletDetails.getServiceTax());
////            payment_object.put("tax_label_name", "" + outletDetails.getServiceTax());
//
//            payment_object.put("contact_address", "" + outletDetails.getContactAddress());
//            payment_object.put("contact_email", "" + outletDetails.getContactEmail());
//
//            payment_object.put("order_status", "1");
//            payment_object.put("currency_code", "" + loginPrefManager.getCurrencySymbole());
//
//            payment_object.put("payment_gateway_id", "" + loginPrefManager.getStringValue("Creditcard_PaymentGateWay_Id"));
//            payment_object.put("delivery_charge", "" + outletDetails.getDeliveryCost());
//
//            payment_object.put("payment_status", "0");
//            payment_object.put("payment_gateway_commission", "" + loginPrefManager.getStringValue("Creditcard_Commision"));
//
//            payment_object.put("delivery_instructions", "");
//
//
//            SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
//
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
//                curr_format.setNumberFormat(nf);
//            }
//            outletDetails.setDeliveryTime("" + curr_format.format(current_date.getTime()));
//            payment_object.put("delivery_date", "" + curr_format.format(current_date.getTime()));
//
//            payment_object.put("order_type", "1");
//            payment_object.put("coupon_id", "0");
//
//            payment_object.put("coupon_amount", "0");
//            payment_object.put("coupon_type", "0");
//
//            payment_object.put("delivery_cost", "0");
//
//
//            JSONArray productJsonArray = new JSONArray();
//
//            for (int i = 0; i < productRespository.getCartProductList().size(); i++) {
//
//                JSONObject productJsonObject = new JSONObject();
//                productJsonObject.put("product_id", productRespository.getCartProductList().get(i).getProductId());
//                productJsonObject.put("quantity", productRespository.getCartProductList().get(i).getCartCount());
//
//                if (productRespository.getCartProductList().get(i).getIngredTypeList().size() != 0) {
//
//                    JSONObject ingredientMainObject = new JSONObject();
//                    Float price = 0f;
//                    int value = 0;
//                    int cartCount = 0;
//                    StringBuilder ingredientName = new StringBuilder();
//
//                    for (int j = 0; j < productRespository.getCartProductList().get(i).getIngredTypeList().size(); j++) {
//
//                        cartCount += productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size();
//
//                        for (int k = 0; k < productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size(); k++) {
//                            JSONObject ingredientInnerObject = new JSONObject();
//
//                            price += Float.parseFloat(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
//                            Log.e("price", "" + price);
//                            ingredientName.append(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientName());
//                            if (j != productRespository.getCartProductList().get(i).getIngredTypeList().size())
//                                ingredientName.append(",");
//
//                            ingredientInnerObject.put("ingredient_id", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientId());
//                            ingredientInnerObject.put("price", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
//
//                            ingredientMainObject.put("" + value, ingredientInnerObject);
//
//                            value++;
//                        }
//
//                    }
//
//                    String str = ingredientName.toString().replaceAll(",$", "");
//                    ingredientMainObject.put("ingredient_names", str);
//
//                    Float total_price_to_fc = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
//                    Log.e("total_price", "" + (total_price_to_fc - price));
//
//                    Float total_price = total_price_to_fc - price;
//
//                    String total_price_sconverted = "" + total_price;
//
//                    Log.e("totla", "" + total_price_sconverted);
//
//
//                    productJsonObject.put("discount_price", "" + total_price_sconverted);
//                    productJsonObject.put("special_req", "");
//                    productJsonObject.put("ingredients", ingredientMainObject);
//                    productJsonObject.put("item_offer", "0");
//
//                } else {
//
//                    productJsonObject.put("ingredients", "");
//                    productJsonObject.put("discount_price", "" + productRespository.getCartProductList().get(i).getTotal());
//                    productJsonObject.put("special_req", "");
//                    productJsonObject.put("item_offer", "0");
//
//                }
//
//                productJsonArray.put(productJsonObject);
//            }
//
//            payment_object.put("items", productJsonArray);
////            Log.e("gust_check_out pay arr", payment_object.toString());
//
//
//            ExpressCheeckOutRequestMethod(payment_object.toString());
//
//
//        } catch (Exception e) {
////            Log.e("GeneratePayArrayFormat", "Exception" + e.getMessage());
//        }
//
//    }

//    private void ExpressCheeckOutRequestMethod(String payment_array) {

//        try {
//
//            if (progressDialog != null) {
//                progressDialog.show();
//            }
//
//            Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
//            Log.e("first_name_edt_txt_view", "" + expCheAdd.getFirst_name());
//            Log.e("last_name_edt_txt_view", "" + expCheAdd.getLast_name().toString().trim());
//            Log.e("email_edt_txt_view", "" + expCheAdd.getEmail().toString().trim());
//            Log.e("mobile_num_edt_txt_view", "" + expCheAdd.getPhone_no().toString().replaceAll("\\s+", ""));
//            Log.e("getCityID", "" + loginPrefManager.getCityID());
//            Log.e("getLocID", "" + loginPrefManager.getLocID());
//            Log.e("address_edt_txt_view", "" + expCheAdd.getAddress().toString());
//            Log.e("bui_flat_no_edt_txtview", "" + expCheAdd.getBuilding_no().toString());
//            Log.e("land_mark_edt_txt_view", "" + expCheAdd.getLand_mark().toString());
//            Log.e("latitude", "" + expCheAdd.getLatitude());
//            Log.e("longitude", "" + expCheAdd.getLongitude());
//            Log.e("address_type", "" + getString(R.string.expr_check_addr_type_value));
//            Log.e("payment_array", "" + payment_array);
//            Log.e("guest_type", "" + getString(R.string.expr_check_guest_type));
//            Log.e("login_type", "" + getString(R.string.expr_check_login_type));
//            Log.e("device_id", "" + getString(R.string.expr_check_login_type));
//            Log.e("device_token", "" + getString(R.string.expr_check_login_type));
//
//            APIService express_check_out = Webdata.getRetrofit().create(APIService.class);
//            express_check_out.razorguestpayonlinnePayment("" + loginPrefManager.getStringValue("Lang_code"),
//                    "" + expCheAdd.getFirst_name().toString().trim(),
//                    "" + expCheAdd.getLast_name().toString().trim(),
//                    "" + expCheAdd.getEmail().toString().trim(),
//                    "" + expCheAdd.getPhone_no().toString().replaceAll("\\s+", ""),
//                    "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(),
//                    "" + expCheAdd.getLand_mark().toString(),
//                    "" + expCheAdd.getLatitude().toString(),
//                    "" + expCheAdd.getLongitude().toString(), "" + expCheAdd.getLatitude().toString(), "" + expCheAdd.getLongitude().toString(),
//                    "" + getString(R.string.expr_check_addr_type_value), "" + payment_array, "" + getString(R.string.expr_check_guest_type), "" + getString(R.string.expr_check_login_type),
//                    "" + loginPrefManager.getStringValue("device_id"),
//                    "" + loginPrefManager.getStringValue("device_token")).enqueue(new Callback<OnlinePayRes>() {
//
//                @Override
//                public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {
//
//                    try {
//
//                        Log.e("getExpressCheckoutReq", "onResponse" + response.raw().toString());
//                        progressDialog.dismiss();
//
//                        if (response.body().getResponse().getHttpCode() == 200) {
//
//                            outletDetails.setDeliveryAddress("" + expCheAdd.getAddress().toString().trim());
//
//                            Intent order_cof = new Intent(ExpCheckProceedToPayActivity.this, OrderConfirmationActivity.class);
//                            order_cof.putExtra("ORDER", outletDetails);
//                            order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction().trim());
//                            order_cof.putExtra("PAYMENT_TYPE", "" + getResources().getString(R.string.tab_two_name));
//                            startActivity(order_cof);
//
//                            finish();
//
//                        } else if (response.body().getResponse().getHttpCode() == 400) {
//                            Toast.makeText(ExpCheckProceedToPayActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (Exception e) {
////                        Log.e("getExpressCheckoutReq", "Exception" + e.getMessage());
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<OnlinePayRes> call, Throwable t) {
//                    progressDialog.dismiss();
////                    Log.e("getExpressCheckoutReq", "onFailure" + t.getMessage());
//                }
//            });
//
//
//        } catch (Exception e) {
//            progressDialog.dismiss();
//        }

//    }


/*    private void PaypalOnlinePaymentRequest(String payFortResponse) {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

//        Log.e("values", " - " + loginManager.getStringValue("user_token") + "," + loginManager.getStringValue("user_id") + "," + loginManager.getStringValue("Lang_code") + " " + parent.toString());

        place_ord_api_ser.razorguestpayonlinnePayment().enqueue(new Callback<OnlinePayRes>() {
            @Override
            public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {

                progressDialog.dismiss();

                Log.e("input", new Gson().toJson(response.raw().request()));
                Log.e("response", response.raw().toString());

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

                        Intent order_cof = new Intent(ExpCheckProceedToPayActivity.this, OrderConfirmationActivity.class);
                        order_cof.putExtra("ORDER", outletDetails);
                        order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction().trim());
                        order_cof.putExtra("PAYMENT_TYPE", "" + getResources().getString(R.string.tab_two_name));
                        startActivity(order_cof);
                        finish();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
//                        Log.e("response", response.body().getResponse().getMessage());
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
//                    Log.e("otpDialogView Exception", "" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<OnlinePayRes> call, Throwable t) {
                progressDialog.dismiss();
//                Log.e("onFailure", t.getMessage());
            }
        });

    }*/


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


}
