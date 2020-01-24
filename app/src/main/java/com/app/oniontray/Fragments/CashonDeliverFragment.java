package com.app.oniontray.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Activites.OrderConfirmationActivity;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OfflinePayment;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.ProcToCheckResp;
import com.app.oniontray.RequestModels.VendorDetailForMyCart;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CashonDeliverFragment extends Fragment {

    private View cashOnDelivFragView;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;


    private JSONObject parent;


    private ProcToCheckResp result_response_;

//    public String Delivery_inst;
//    public String Delivery_type;

    private OutletDetails outletDetails;
    private String Vendor_id;

    private final Calendar current_date = Calendar.getInstance();

    private ProductRespository productRespository;
    private ArrayList<String> outletAndVendorID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cashOnDelivFragView = inflater.inflate(R.layout.cash_on_delivery_fragment, container, false);

//        result_response_ = (ProcToCheckResp) getArguments().getSerializable("PAYMENT_DETAILS");
        outletDetails = (OutletDetails) getArguments().getSerializable("outlet_details");
        Vendor_id = getArguments().getString("vendor_id");

        productRespository = new ProductRespository();
        outletAndVendorID = productRespository.getVendorID();

        TextView textView = (TextView) cashOnDelivFragView.findViewById(R.id.cash_on_deliv_pay_amount_txt);
        Button placeOrder = (Button) cashOnDelivFragView.findViewById(R.id.cash_on_deliv_place_order_btn);

        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());

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
//            String text = String.format(getResources().getString(R.string.welcome_messages), loginManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf(total))));
//            textView.setText(Html.fromHtml(text));
//
//        } else {

        String text = String.format(getResources().getString(R.string.welcome_messages), loginManager.getFormatCurrencyValue(loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));
        textView.setText(Html.fromHtml(text));

//        }

//        String text = String.format(getResources().getString(R.string.welcome_messages), loginManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf(outletDetails.getGrandTotal()))));
//        textView.setText(Html.fromHtml(text));

        GenerateJsonData();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                PlaceOrderRequestMethod();
                MyCartRequestMethod();

            }
        });

        return cashOnDelivFragView;
    }


    private void MyCartRequestMethod() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

//        Log.e("user_id", "-" + loginManager.getStringValue("user_id"));
//        Log.e("user_token", "-" + loginManager.getStringValue("user_token"));
//        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(0));
//        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(1));
//        Log.e("getCityID", "-" + loginManager.getCityID());
//        Log.e("getLocID", "-" + loginManager.getLocID());

            APIService MyCartService = Webdata.getRetrofit().create(APIService.class);

            MyCartService.getOutletDetails("" + loginManager.getStringValue("Lang_code"),
                    outletAndVendorID.get(0), outletAndVendorID.get(1), "" + loginManager.getCityID(),
                    "" + loginManager.getLocID()).enqueue(new Callback<VendorDetailForMyCart>() {
                @Override
                public void onResponse(Call<VendorDetailForMyCart> call, final Response<VendorDetailForMyCart> response) {

                    try {

                        Log.e("req", new Gson().toJson(response.raw().request()));
                        Log.e("resp", new Gson().toJson(response.body()));
                        progressBarDialog.dismiss();

//                        Log.e("getOutletDetails", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getOutletDetails().getOpenStatus() == 0) {
                                Toast.makeText(getContext(), "" + getString(R.string.restarent_was_closed_txt), Toast.LENGTH_SHORT).show();
                            } else {
                                PlaceOrderRequestMethod();
                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(getContext(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("Exception", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<VendorDetailForMyCart> call, Throwable t) {
                    progressBarDialog.dismiss();
//                    Log.e("Exception", t.toString());
                }
            });

        } catch (Exception e) {
            progressBarDialog.dismiss();
//            Log.e("Exception", "" + e.getMessage());
        }

    }


    private void GenerateJsonData() {

        try {

            parent = new JSONObject();

            parent.put("user_id", "" + loginManager.getStringValue("user_id"));
            parent.put("store_id", "" + Vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());

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

//            parent.put("service_tax", "" + outletDetails.getServiceTax());

            parent.put("order_status", "1");//static
            parent.put("order_key", "");//static
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + outletDetails.getGrandTotal());
            parent.put("currency_code", "" + loginManager.getCurrencySymbole());
            parent.put("payment_gateway_id", "" + loginManager.getStringValue("Cash_PaymentGateWay_Id"));

            parent.put("payment_status", "0");  //static

//            Log.e("getSubTotal", "- " + outletDetails.getSubTotal());
//            Log.e("Cash_Delivery_Commision", " - " + loginManager.getStringValue("Cash_Delivery_Commision"));
//
//            Log.e("getServiceTax", "-" + outletDetails.getServiceTax());
//            Log.e("getDeliveryCost", "-" + outletDetails.getDeliveryCost());

            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat("" + outletDetails.getCommissionAmount())) / 100));


            parent.put("admin_commission", "" + commission_one);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginManager.getStringValue("Cash_Delivery_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());


            parent.put("delivery_date", "" + outletDetails.getDeliveryDate());
            outletDetails.setDeliveryTime("" + outletDetails.getDeliveryDate());
            if (outletDetails.getPaymentOption() == 1) {

                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
                parent.put("delivery_slot", "0");
                parent.put("delivery_cost", "" + outletDetails.getDeliveryCost());
                parent.put("delivery_charge", "" + outletDetails.getDeliveryCost());//Rightnow static


                parent.put("order_type", "1");

            } else {

                parent.put("delivery_address", "");
                parent.put("delivery_slot", "0");
                parent.put("delivery_cost", "" + outletDetails.getPlatformCharge());
                parent.put("delivery_charge", "0");

                parent.put("order_type", "2");

            }

//            if (result_response_.getDeliveryCouponId().isEmpty()) {

            if (outletDetails.getApplyCoupon()) {

                parent.put("coupon_type", "" + outletDetails.getCouponType());  //static
                parent.put("coupon_id", "" + outletDetails.getCouponId());  //static
                parent.put("coupon_amount", "" + outletDetails.getCouponAmount()); //static

            } else {

                parent.put("coupon_type", "0");  //static
                parent.put("coupon_id", "0");  //static
                parent.put("coupon_amount", "0"); //static

            }

//            parent.put("coupon_type", "0");  //static
//            parent.put("coupon_id", "0");
//            parent.put("coupon_amount", "0");


//            } else {
//                parent.put("coupon_type", "" + result_response_.getCouponType());
//                parent.put("coupon_id", "" + result_response_.getCouponId());
//                parent.put("coupon_amount", "" + result_response_.getDeliveryPromoCode());
//            }

//            JSONArray jsonArray = new JSONArray();
//
//            for (int i = 0; i < result_response_.getCartItems().size(); i++) {
//
//                JSONObject jsonObject = new JSONObject();
//
//                jsonObject.put("product_id", "" + result_response_.getCartItems().get(i).getProductId());
//                jsonObject.put("quantity", "" + result_response_.getCartItems().get(i).getQuantity());
//                jsonObject.put("discount_price", "" + result_response_.getCartItems().get(i).getDiscountPrice());
//                jsonObject.put("item_offer", "0");
//
//                jsonArray.put(jsonObject);
//            }

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
//                            Log.e("price", "" + price);
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
                    ingredientMainObject.put("ingredient_name", str);
                    Float total_price_to_fc = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price", "" + (total_price_to_fc - price));

                    Float total_price = total_price_to_fc - price;

                    String total_price_sconverted = "" + total_price;

                    Log.e("totla", "" + total_price_sconverted);

                    productJsonObject.put("discount_price", "" + total_price_sconverted);
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

            Log.e("offline pay array", parent.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void PlaceOrderRequestMethod() {

//        OTPDialogView otpDialogView = new OTPDialogView(getActivity(), new invokeOfflinePaymentMethod() {
//            @Override
//            public void offlinePaymentMethod() {
//
        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

//                Log.e("values", " - " + loginManager.getStringValue("user_token") + "," + loginManager.getStringValue("user_id") + "," + loginManager.getStringValue("Lang_code") + " " + parent.toString());

        place_ord_api_ser.offlinePayment(loginManager.getStringValue("user_token"),
                loginManager.getStringValue("user_id"), loginManager.getStringValue("Lang_code"),
                parent.toString()).enqueue(new Callback<OfflinePayment>() {
            @Override
            public void onResponse(Call<OfflinePayment> call, Response<OfflinePayment> response) {
               //u Log.e("OUTPOUT", p);
                try {

                    progressBarDialog.dismiss();
                    Log.e("INPUT", new Gson().toJson(response.raw().request().toString()));
                    Log.e("OUTPOUT", new Gson().toJson(response.body().toString()));


                    if (response.body().getResponse().getHttpCode() == 200) {

//                                outletDetails.setDeliveryAddress(outletDetails.getLocationName());
                        Intent order_cof = new Intent(getContext(), OrderConfirmationActivity.class);
                        order_cof.putExtra("ORDER", outletDetails);
                        order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction().trim());
                        order_cof.putExtra("PAYMENT_TYPE", "" + getContext().getString(R.string.cash_on_delivery_txt));
                        startActivity(order_cof);
                        getActivity().finish();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
//                                Log.e("response", response.body().getResponse().getMessage());
                    }

                } catch (Exception e) {
                    progressBarDialog.dismiss();
                    Log.e("otpDialogView Exception", "" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<OfflinePayment> call, Throwable t) {
                progressBarDialog.dismiss();
                Log.e("onFailure", t.getMessage());
            }
        });
    }
//        });
//        otpDialogView.setCanceledOnTouchOutside(false);
//        otpDialogView.show();

//    }


}
