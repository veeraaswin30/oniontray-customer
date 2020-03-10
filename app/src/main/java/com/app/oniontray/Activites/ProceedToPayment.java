package com.app.oniontray.Activites;

import android.content.Intent;
import android.drm.ProcessedData;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.oniontray.Fragments.RazorPayFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CustomViews.CustomViewPager;
import com.app.oniontray.Fragments.CashonDeliverFragment;
import com.app.oniontray.Fragments.PaypalFragment;
import com.app.oniontray.Fragments.WalletFragment;
import com.app.oniontray.Interface.IPaymentRequestCallBack;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.PayPalModule.OnlinePayRes;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.ProcToCheckResp;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProceedToPayment extends LocalizationActivity implements TabLayout.OnTabSelectedListener,
        RazorPayFragment.RazorInterface, PaymentResultListener {

    //This is our tablayout
    private TabLayout pay_opt_tabLayout;

    //This is our viewPager
    private CustomViewPager pay_opt_view_pager;

    Bundle bundle;
    private ProcToCheckResp procToCheckresponse;


//    suganshree@mailinator.com , 1234567


    /*---------------  Payment Type Fragment ---------------*/

    private PaypalFragment paypalFragment;

    private RazorPayFragment razorPayFragment;
    private CashonDeliverFragment cashonDeliverFragment;
    private WalletFragment walletFragment;

    /*------------------------------------------------------*/


    private OutletDetails outletDetails;
    private String vendor_id;


    private String KEY_COMMAND = "command";
    private String KEY_MERCHANT_REFERENCE = "merchant_reference";
    private String KEY_AMOUNT = "amount";
    private String KEY_CURRENCY = "currency";
    private String KEY_LANGUAGE = "language";
    private String KEY_CUSTOMER_EMAIL = "customer_email";
    private String KEY_PAYMENT_OPTION = "payment_option";
    private String KEY_ECI = "eci";
    private String KEY_ORDER_DESCRIPTION = "order_description";
    private String KEY_CUSTOMER_IP = "customer_ip";
    private String KEY_SDK_TOKEN = "sdk_token";


    private IPaymentRequestCallBack iPaymentRequestCallBack;
    private Gson gson;
    private JSONObject parent;
    private final Calendar current_date = Calendar.getInstance();
    private Toolbar pro_pay_toolbar_id;


    private JSONObject payment_signature;
    private String TAG = ProceedToPayment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc_to_payment_layout);

        LoginPrefManager loginPrefManager = new LoginPrefManager(ProceedToPayment.this);
        gson = new Gson();

        pro_pay_toolbar_id = (Toolbar) findViewById(R.id.pro_pay_toolbar_id);
        pro_pay_toolbar_id.setTitle("");
        //   pro_pay_toolbar_id.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        pro_pay_toolbar_id.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        pro_pay_toolbar_id.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(pro_pay_toolbar_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(ProceedToPayment.this));


        if (language.equals("en")) {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        pro_pay_toolbar_id.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        outletDetails = (OutletDetails) intent.getExtras().getSerializable("outlet_details");
        vendor_id = intent.getStringExtra("vendor_id");


        paypalFragment = new PaypalFragment();
        razorPayFragment = new RazorPayFragment();

        cashonDeliverFragment = new CashonDeliverFragment();
//        walletFragment = new WalletFragment();


        Bundle bundle = new Bundle();
        bundle.putSerializable("outlet_details", outletDetails);
        bundle.putString("vendor_id", vendor_id);


        paypalFragment.setArguments(bundle);
        razorPayFragment.setArguments(bundle);

        cashonDeliverFragment.setArguments(bundle);
//        walletFragment.setArguments(bundle);

        TextView pay_title = (TextView) findViewById(R.id.pay_title);
        pay_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        TextView pay_opt_tit_tax_text = (TextView) findViewById(R.id.pay_opt_tit_tax_text);
        TextView pay_opt_tit_amount_txt = (TextView) findViewById(R.id.pay_opt_tit_amount_txt);

        pay_opt_tit_tax_text.setText(" (" + getString(R.string.inclusive_of_tax) + ")");

//        if (outletDetails.getApplyCoupon()) {
//
//            int g_total = new Double(outletDetails.getGrandTotal()).intValue();
//            int c_amt = new Double(outletDetails.getCouponAmount()).intValue();
//
//            Log.e("g_total", "" + g_total);
//            Log.e("c_amt", "" + c_amt);
//
//            Float aFloat = Float.parseFloat("" + g_total);
//            Float bFloat = Float.parseFloat("" + c_amt);
//            Float cFloat = (aFloat - bFloat);
//            int total = (int) Math.round(cFloat);
//
//            pay_opt_tit_amount_txt.setText(loginPrefManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf(total))));
//
//        } else {
        pay_opt_tit_amount_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));
//        }

        pay_opt_tabLayout = (TabLayout) findViewById(R.id.pay_opt_tabLayout);
        //pay_opt_tabLayout.setSelectedTabIndicatorColor(Color.parseColor(loginPrefManager.getThemeColor()));


        //Initializing viewPager
        pay_opt_view_pager = (CustomViewPager) findViewById(R.id.pay_opt_view_pager);
        // pay_opt_view_pager.settextco(Color.parseColor(loginPrefManager.getThemeFontColor()));
        // pay_opt_view_pager.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setupViewPager();
        pay_opt_tabLayout.setupWithViewPager(pay_opt_view_pager);

//        pay_opt_view_pager.setPagingEnabled(false);
//        LinearLayout tabStrip = ((LinearLayout)pay_opt_tabLayout.getChildAt(0));
//        tabStrip.setEnabled(false);
//        tabStrip.getChildAt(0).setClickable(false);
//        pay_opt_view_pager.setCurrentItem(1);

//        pay_opt_tabLayout.addTab(pay_opt_tabLayout.newTab().setText(getResources().getString(R.string.tab_two_name)));
//        pay_opt_tabLayout.addTab(pay_opt_tabLayout.newTab().setText(getResources().getString(R.string.tab_three_name)));
//        pay_opt_tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
////        TabLayout.Tab paypaltablayout = pay_opt_tabLayout.getTabAt(0);
////        View tabView = paypaltablayout.getCustomView();
//
//        pay_opt_tabLayout.setLayoutParams(new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
//
//
//        pay_opt_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//            }
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//
//        });
//
//        pay_opt_tabLayout.setOnTabSelectedListener(this);


    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        pay_opt_view_pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

//        adapter.addFragment(new DebitCardFragment(), getResources().getString(R.string.tab_one_name));
//        adapter.addFragment(new CreditCardFragment(), getResources().getString(R.string.tab_two_name));
//        adapter.addFragment(cashonDeliverFragment, getResources().getString(R.string.tab_three_name));

//        adapter.addFragment(paypalPayment, getResources().getString(R.string.tab_two_name));

        adapter.addFragment(paypalFragment, getResources().getString(R.string.tab_one_name));
        adapter.addFragment(razorPayFragment, getResources().getString(R.string.tab_two_name));
        adapter.addFragment(cashonDeliverFragment, getResources().getString(R.string.tab_three_name));
//        adapter.addFragment(walletFragment, getResources().getString(R.string.proc_to_pay_wallet_txt));

        pay_opt_view_pager.setAdapter(adapter);
        pay_opt_tabLayout.setupWithViewPager(pay_opt_view_pager);

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

    private void showToast(String message) {
        Toast.makeText(ProceedToPayment.this, message, Toast.LENGTH_LONG).show();
    }

    private void GenerateJsonData(String payFortResponse) {

        try {

            parent = new JSONObject();

            parent.put("user_id", "" + loginPrefManager.getStringValue("user_id"));
            parent.put("store_id", "" + vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());

            parent.put("vendor_key", "" + outletDetails.getOutletName());
            parent.put("total", "" + outletDetails.getGrandTotal());
            parent.put("sub_total", "" + outletDetails.getSubTotal());

            parent.put("contact_address", "" + outletDetails.getContactAddress());
            parent.put("contact_email", "" + outletDetails.getContactEmail());
            parent.put("outlet_name", "" + outletDetails.getOutletName());
            parent.put("gst",outletDetails.getGst());

            if (outletDetails.getTaxType() == 2) {
                parent.put("service_tax", "" + outletDetails.getServiceTax());
                parent.put("tax_label_name", "");
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            } else {
                parent.put("service_tax", "" + outletDetails.getServiceTax());
                parent.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            }

            parent.put("order_status", "1");//static
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + outletDetails.getGrandTotal());
            parent.put("currency_code", "" + loginPrefManager.getCurrencySymbole());
//            parent.put("payment_gateway_id", "20" + loginPrefManager.getStringValue("Paypal_PaymentGateWay_Id"));
            parent.put("payment_gateway_id", "" + loginPrefManager.getStringValue("Creditcard_PaymentGateWay_Id"));
//            parent.put("payment_method", "" + getResources().getString(R.string.tab_two_name));

            parent.put("payment_status", "0");  //static

            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat("" + loginPrefManager.getStringValue("Creditcard_Commision"))) / 100));

            int admin_commision = (((commission_one) + Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));

            parent.put("admin_commission", "" + admin_commision);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginPrefManager.getStringValue("Creditcard_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());


            Log.e("delivery_date", "-----" + outletDetails.getDeliveryDate());
            parent.put("delivery_date", "" + outletDetails.getDeliveryDate());
            outletDetails.setDeliveryTime("" + outletDetails.getDeliveryDate());

            if (outletDetails.getPaymentOption() == 1) {
                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
//            parent.put("delivery_slot", "1"); //static
                parent.put("delivery_cost", "0");

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }
                parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); //Rightnow static


                parent.put("order_type", "1");
            } else {
                parent.put("delivery_address", "0");
//            parent.put("delivery_slot", "1"); //static
                parent.put("delivery_charge", "0");

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }

                parent.put("delivery_cost", "" + outletDetails.getPlatformCharge()); //Rightnow static

                parent.put("order_type", "2");
            }

            if (outletDetails.getApplyCoupon()) {

                parent.put("coupon_type", "" + outletDetails.getCouponType());  //static
                parent.put("coupon_id", "" + outletDetails.getCouponId());  //static
                parent.put("coupon_amount", "" + outletDetails.getCouponAmount()); //static

            } else {

                parent.put("coupon_type", "0");  //static
                parent.put("coupon_id", "0");  //static
                parent.put("coupon_amount", "0"); //static

            }

            JSONArray productJsonArray = new JSONArray();

            for (int i = 0; i < productRespository.getCartProductList().size(); i++) {
                JSONObject productJsonObject = new JSONObject();

                productJsonObject.put("product_id", productRespository.getCartProductList().get(i).getProductId());

                productJsonObject.put("quantity", productRespository.getCartProductList().get(i).getCartCount());


                if (productRespository.getCartProductList().get(i).getIngredTypeList().size() != 0) {
                    JSONObject ingredientMainObject = new JSONObject();
                    Float price = 0f;
                    int value = 0;
                    int cartCount = 0;
                    StringBuilder ingredientName = new StringBuilder();
                    for (int j = 0; j < productRespository.getCartProductList().get(i).getIngredTypeList().size(); j++) {

                        cartCount += productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size();

                        for (int k = 0; k < productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size(); k++) {
                            JSONObject ingredientInnerObject = new JSONObject();

                            price += Float.parseFloat(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
                            Log.e("price", "" + price);
                            ingredientName.append(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientName());
                            if (j != productRespository.getCartProductList().get(i).getIngredTypeList().size())
                                ingredientName.append(",");

                            ingredientInnerObject.put("ingredient_id", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientId());
                            ingredientInnerObject.put("price", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());

                            ingredientMainObject.put("" + value, ingredientInnerObject);

                            value++;
                        }

                    }

                    String str = ingredientName.toString().replaceAll(",$", "");
                    ingredientMainObject.put("ingredient_names", str);


                    Float total_price = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price", "" + (total_price - price));


                    productJsonObject.put("discount_price", "" + (total_price - price));
                    productJsonObject.put("ingredients", ingredientMainObject);
                    productJsonObject.put("item_offer", "0");

                } else {

                    productJsonObject.put("ingredients", "");
                    productJsonObject.put("discount_price", productRespository.getCartProductList().get(i).getTotal());
                    productJsonObject.put("item_offer", "0");

                }

                productJsonArray.put(productJsonObject);
            }

            parent.put("items", productJsonArray);

//            Log.e("PayPal app data array", "" + parent.toString());
//            Log.e("PayPal pay data array", "" + payFortResponse.toString());

            PaypalOnlinePaymentRequest(payFortResponse);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PaypalOnlinePaymentRequest(String payFortResponse) {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

//        Log.e("values", " - " + loginManager.getStringValue("user_token") + "," + loginManager.getStringValue("user_id") + "," + loginManager.getStringValue("Lang_code") + " " + parent.toString());

        place_ord_api_ser.onlinnePayment(loginPrefManager.getStringValue("user_token"), loginPrefManager.getStringValue("user_id"),
                loginPrefManager.getStringValue("Lang_code"), parent.toString(),
                payFortResponse).enqueue(new Callback<OnlinePayRes>() {
            @Override
            public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {

                progressDialog.dismiss();

                Log.e("req", new Gson().toJson(response.raw().request()));
                Log.e("resp", new Gson().toJson(response.body()));

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

                        Intent order_cof = new Intent(ProceedToPayment.this, OrderConfirmationActivity.class);
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

    }


    /* razor pay interface method */

    @Override
    public void paymentProcess() {
        startPayment();

    }

    private void startPayment() {


        Checkout checkout = new Checkout();

        try {

            JSONObject options = new JSONObject();

            Log.e("amout", "" + Math.round(Float.parseFloat(outletDetails.getGrandTotal())) * 100);
            Float amount = (Float.parseFloat(outletDetails.getGrandTotal())) * 100;
            options.put("name", "" + outletDetails.getOutletName());
            options.put("amount", amount);

            /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

            checkout.open(ProceedToPayment.this, options);

        } catch (Exception e) {

            Toast.makeText(getApplicationContext(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    /**
     * The name of the function has to be
     * onPaymentSuccess
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
//            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            GenerateRazorPay(razorpayPaymentID);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }


    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


    private void GenerateRazorPay(String razorId) {

        try {
            parent = new JSONObject();

            parent.put("user_id", "" + loginPrefManager.getStringValue("user_id"));
            parent.put("store_id", "" + vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());
            parent.put("razor_payment_gateway_id", "" + razorId);

            parent.put("vendor_key", "" + outletDetails.getOutletName());
            parent.put("total", "" + outletDetails.getGrandTotal());
            parent.put("sub_total", "" + outletDetails.getSubTotal());
            parent.put("contact_address", "" + outletDetails.getContactAddress());
            parent.put("contact_email", "" + outletDetails.getContactEmail());
            parent.put("outlet_name", "" + outletDetails.getOutletName());

            if (outletDetails.getTaxType() == 2) {
                parent.put("service_tax", "" + outletDetails.getServiceTax());
                parent.put("tax_label_name", "");
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            } else {
                parent.put("service_tax", "" + outletDetails.getServiceTax());
                parent.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            }

            parent.put("order_status", "1");//static
            parent.put("order_key", "");
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + outletDetails.getGrandTotal());
            parent.put("currency_code", "" + loginPrefManager.getCurrencySymbole());

            parent.put("payment_gateway_id", "" + loginPrefManager.getStringValue("Razor_PaymentGateWay_Id"));
            parent.put("payment_method", "" + getResources().getString(R.string.tab_two_name));

            parent.put("payment_status", "0");  //static
            int commission_one;
            if (!loginPrefManager.getStringValue("Razor_Commision").isEmpty()) {
                commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                        Math.round(Float.parseFloat("" + loginPrefManager.getStringValue("Razor_Commision"))) / 100));
            } else {
                commission_one = 0;
            }
            int admin_commision = (((commission_one) + Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));

            parent.put("admin_commission", "" + admin_commision);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginPrefManager.getStringValue("Razor_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());

            Log.e("delivery_date", "-----" + outletDetails.getDeliveryDate());
            parent.put("delivery_date", "" + outletDetails.getDeliveryDate());
            outletDetails.setDeliveryTime("" + outletDetails.getDeliveryDate());

            if (outletDetails.getPaymentOption() == 1) {
                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
//            parent.put("delivery_slot", "1"); //static
                parent.put("delivery_cost", "" + outletDetails.getDeliveryCost());

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }
                parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); //Rightnow static


                parent.put("order_type", "1");
            } else {
                parent.put("delivery_address", "");
//            parent.put("delivery_slot", "1"); //static
                parent.put("delivery_charge", "0");

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }

                parent.put("delivery_cost", "" + outletDetails.getPlatformCharge()); //Rightnow static

                parent.put("order_type", "2");
            }


            if (outletDetails.getApplyCoupon()) {

                parent.put("coupon_type", "" + outletDetails.getCouponType());  //static
                parent.put("coupon_id", "" + outletDetails.getCouponId());  //static
                parent.put("coupon_amount", "" + outletDetails.getCouponAmount()); //static

            } else {

                parent.put("coupon_type", "0");  //static
                parent.put("coupon_id", "0");  //static
                parent.put("coupon_amount", "0"); //static

            }

            JSONArray productJsonArray = new JSONArray();

            for (int i = 0; i < productRespository.getCartProductList().size(); i++) {
                JSONObject productJsonObject = new JSONObject();

                productJsonObject.put("product_id", productRespository.getCartProductList().get(i).getProductId());
                productJsonObject.put("quantity", productRespository.getCartProductList().get(i).getCartCount());


                if (productRespository.getCartProductList().get(i).getIngredTypeList().size() != 0) {

                    JSONObject ingredientMainObject = new JSONObject();
                    Float price = 0f;
                    int value = 0;
                    int cartCount = 0;

                    StringBuilder ingredientName = new StringBuilder();
                    for (int j = 0; j < productRespository.getCartProductList().get(i).getIngredTypeList().size(); j++) {

                        cartCount += productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size();

                        for (int k = 0; k < productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size(); k++) {
                            JSONObject ingredientInnerObject = new JSONObject();

                            price += Float.parseFloat(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
                            Log.e("price", "" + price);
                            ingredientName.append(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientName());
                            if (j != productRespository.getCartProductList().get(i).getIngredTypeList().size())
                                ingredientName.append(",");

                            ingredientInnerObject.put("ingredient_id", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientId());
                            ingredientInnerObject.put("price", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());

                            ingredientMainObject.put("" + value, ingredientInnerObject);

                            value++;
                        }

                    }

                    String str = ingredientName.toString().replaceAll(",$", "");
                    ingredientMainObject.put("ingredient_names", str);

                    productJsonObject.put("discount_price", "" + (Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal()) - price));
                    productJsonObject.put("ingredients", ingredientMainObject);
                    productJsonObject.put("item_offer", "0");

                } else {

                    productJsonObject.put("ingredients", "");
                    productJsonObject.put("discount_price", productRespository.getCartProductList().get(i).getTotal());
                    productJsonObject.put("item_offer", "0");

                }

                productJsonArray.put(productJsonObject);
            }

            parent.put("items", productJsonArray);

            Log.e("online_array", "" + parent.toString());

            RazorPayOnlinePaymentRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void RazorPayOnlinePaymentRequest() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);


        place_ord_api_ser.razorpayonlinnePayment(loginPrefManager.getStringValue("user_token"), loginPrefManager.getStringValue("user_id"),
                loginPrefManager.getStringValue("Lang_code"), parent.toString()).enqueue(new Callback<OnlinePayRes>() {
            @Override
            public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {

                progressDialog.dismiss();

                Log.e("razor response", response.raw().toString());

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

                        Intent order_cof = new Intent(ProceedToPayment.this, OrderConfirmationActivity.class);
                        order_cof.putExtra("ORDER", outletDetails);
                        order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction());
                        order_cof.putExtra("PAYMENT_TYPE", getResources().getString(R.string.tab_two_name));
                        startActivity(order_cof);
                        finish();
                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Log.e("razor response", response.body().getResponse().getMessage());
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("razor Exception", "" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<OnlinePayRes> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("razor onFailure", t.getMessage());
            }
        });


    }

}

