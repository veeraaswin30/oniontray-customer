package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.oniontray.CustomViews.CustomViewPager;
import com.app.oniontray.Fragments.CashonDeliverFragment;
import com.app.oniontray.Fragments.PaypalFragment;
import com.app.oniontray.Fragments.RazorPayFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.PayPalModule.OnlinePayRes;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.ProcToCheckResp;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.android.material.tabs.TabLayout;
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

public class PickUpProceedToPayment extends LocalizationActivity implements TabLayout.OnTabSelectedListener,
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


    /*------------------------------------------------------*/
    private JSONObject parent;


    private OutletDetails outletDetails;

    private final Calendar current_date = Calendar.getInstance();

    private Toolbar pro_pay_toolbar_id;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proc_to_payment_layout);

        LoginPrefManager loginPrefManager = new LoginPrefManager(PickUpProceedToPayment.this);


        pro_pay_toolbar_id = (Toolbar) findViewById(R.id.pro_pay_toolbar_id);
        pro_pay_toolbar_id.setTitle("");
        pro_pay_toolbar_id.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(pro_pay_toolbar_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


           String language = String.valueOf(LanguageSetting.getLanguage(PickUpProceedToPayment.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
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


        paypalFragment = new PaypalFragment();
        razorPayFragment = new RazorPayFragment();


        Bundle bundle = new Bundle();
        bundle.putSerializable("outlet_details", outletDetails);


        paypalFragment.setArguments(bundle);
        razorPayFragment.setArguments(bundle);



        TextView pay_opt_tit_tax_text = (TextView) findViewById(R.id.pay_opt_tit_tax_text);
        TextView pay_opt_tit_amount_txt = (TextView) findViewById(R.id.pay_opt_tit_amount_txt);

        pay_opt_tit_tax_text.setText(" (" + getString(R.string.inclusive_of_tax) + ")");

        pay_opt_tit_amount_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));


        pay_opt_tabLayout = (TabLayout) findViewById(R.id.pay_opt_tabLayout);

        //Initializing viewPager
        pay_opt_view_pager = (CustomViewPager) findViewById(R.id.pay_opt_view_pager);
        setupViewPager();
        pay_opt_tabLayout.setupWithViewPager(pay_opt_view_pager);



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


        adapter.addFragment(paypalFragment, getResources().getString(R.string.tab_one_name));
        adapter.addFragment(razorPayFragment, getResources().getString(R.string.tab_two_name));


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

    @Override
    public void paymentProcess() {
        startPayment();
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



    private void startPayment() {


        Checkout checkout = new Checkout();

        try {

            JSONObject options = new JSONObject();

            Log.e("amout","" + Math.round(Float.parseFloat(outletDetails.getGrandTotal())) * 100);
            Float amount = (Float.parseFloat(outletDetails.getGrandTotal())) * 100;
            options.put("name", "" + outletDetails.getOutletName());
            options.put("amount",  amount);

            /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

            checkout.open(PickUpProceedToPayment.this, options);

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
            Log.e( "Exception", ""+e.getMessage());
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
            Log.e( "Exception", ""+e.getMessage());
        }
    }


    private void GenerateRazorPay(String razorId) {

        try {
            parent = new JSONObject();

            parent.put("user_id", "" + loginPrefManager.getStringValue("user_id"));
            parent.put("store_id", "" + outletDetails.getVendorsId());
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

            parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); //Rightnow static
            parent.put("payment_status", "0");  //static
            int commission_one;
            if (!loginPrefManager.getStringValue("Razor_Commision").isEmpty()) {
                commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                        Math.round(Float.parseFloat("" + loginPrefManager.getStringValue("Razor_Commision"))) / 100));
            }else{
                commission_one=0;
            }
            int admin_commision = (((commission_one) + Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));

            parent.put("admin_commission", "" + admin_commision);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginPrefManager.getStringValue("Razor_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());
            parent.put("delivery_date", "" +outletDetails.getDeliveryDate());
            outletDetails.setDeliveryTime("" +outletDetails.getDeliveryDate());

            if (outletDetails.getPaymentOption()==1) {
                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
//            parent.put("delivery_slot", "1"); //static
                parent.put("delivery_cost", ""+outletDetails.getDeliveryCost());

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }
                parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); //Rightnow static


                parent.put("order_type", "1");
            }else{
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

                        Intent order_cof = new Intent(PickUpProceedToPayment.this, OrderConfirmationActivity.class);
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

