package com.app.oniontray.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
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
import com.app.oniontray.CustomViews.VisitorsOtpDailogView;
import com.app.oniontray.DB.IngredientRepository;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.invokeOfflinePaymentMethod;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ExpCheAdd;
import com.app.oniontray.RequestModels.GustCheckOutReq;
import com.app.oniontray.RequestModels.OutletDetails;
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

/**
 * Created by nextbrain on 23/6/18.
 */

public class ExpCheckCashOnDelFragment extends Fragment {


    private ExpCheAdd expCheAdd;
    private OutletDetails outletDetails;
    private String vendor_id;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    public ProductRespository productRespository;
    private ArrayList<String> outletAndVendorID;
    public IngredientRepository ingredientRepository;


    private final Calendar current_date = Calendar.getInstance();

    private TextView cash_on_deliv_pay_amount_txt;
    private Button placeOrder;

    private JSONObject parent;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cash_on_delivery_fragment, container, false);

        expCheAdd = (ExpCheAdd) getArguments().getSerializable("exp_che_add_det");
        outletDetails = (OutletDetails) getArguments().getSerializable("out_det");
        vendor_id = getArguments().getString("vendor_id");

        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());


        productRespository = new ProductRespository();
        outletAndVendorID = productRespository.getVendorID();
        ingredientRepository = new IngredientRepository();


        cash_on_deliv_pay_amount_txt = (TextView) rootView.findViewById(R.id.cash_on_deliv_pay_amount_txt);
        placeOrder = (Button) rootView.findViewById(R.id.cash_on_deliv_place_order_btn);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String text = String.format(getResources().getString(R.string.welcome_messages), loginManager.getFormatCurrencyValue(loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));
        cash_on_deliv_pay_amount_txt.setText(Html.fromHtml(text));


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                VisitorsOtpDailogView visitorsOtpDailogView = new VisitorsOtpDailogView(getActivity(), expCheAdd, new invokeOfflinePaymentMethod() {
//                    @Override
//                    public void offlinePaymentMethod() {

                        MyCartRequestMethod();

//                    }
//                });
//                visitorsOtpDailogView.setCancelable(false);
//                visitorsOtpDailogView.show();

//                PlaceOrderRequestMethod();


            }
        });


        GenerateJsonData();

    }


    private void MyCartRequestMethod() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            Log.e("user_id", "-" + loginManager.getStringValue("user_id"));
            Log.e("user_token", "-" + loginManager.getStringValue("user_token"));
            Log.e("vendorID", "-" + outletAndVendorID.get(0));
            Log.e("outletID", "-" + outletAndVendorID.get(1));
            Log.e("getCityID", "-" + loginManager.getCityID());
            Log.e("getLocID", "-" + loginManager.getLocID());

            APIService MyCartService = Webdata.getRetrofit().create(APIService.class);

            MyCartService.getOutletDetails("" + loginManager.getStringValue("Lang_code"),
                    outletAndVendorID.get(0), outletAndVendorID.get(1), "" + loginManager.getCityID(),
                    "" + loginManager.getLocID()).enqueue(new Callback<VendorDetailForMyCart>() {
                @Override
                public void onResponse(Call<VendorDetailForMyCart> call, final Response<VendorDetailForMyCart> response) {

                    try {


                    } catch (Exception e) {
                        Log.e("Exception 1", e.toString());
                    }

                    progressBarDialog.dismiss();
                    Log.e("getOutletDetails", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        if (response.body().getResponse().getOutletDetails().getOpenStatus() == 0) {
                            Toast.makeText(getContext(), "" + getString(R.string.restarent_was_closed_txt), Toast.LENGTH_SHORT).show();
                        } else {
                            PlaceOrderRequestMethod();
                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(getContext(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<VendorDetailForMyCart> call, Throwable t) {
                    progressBarDialog.dismiss();
                    Log.e("Exception 2", t.toString());
                }
            });

        } catch (Exception e) {
            progressBarDialog.dismiss();
            Log.e("Exception 3", "" + e.getMessage());
        }

    }


    private void GenerateJsonData() {

        try {

            parent = new JSONObject();

            parent.put("store_id", "" + vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());

            parent.put("vendor_key", "" + outletDetails.getOutletName());
            parent.put("total", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal())));
            parent.put("sub_total", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getSubTotal())));


            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat(outletDetails.getCommissionAmount())) / 100));

            /* int admin_commision = (((commission_one) *//*+ Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))*//*) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));*/

            parent.put("admin_commission", "" + commission_one);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);


            if (outletDetails.getTaxType() == 2) {
                parent.put("service_tax", "" + outletDetails.getServiceTax());
                parent.put("tax_label_name", "");
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            } else {
                parent.put("service_tax", "0");
                parent.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            }

            parent.put("contact_address", "" + outletDetails.getContactAddress());
            parent.put("contact_email", "" + outletDetails.getContactEmail());
            parent.put("outlet_name", "" + outletDetails.getOutletName());

            parent.put("order_status", "1");//static
            parent.put("order_key", "");//static
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal())));
            parent.put("currency_code", "" + loginManager.getCurrencySymbole());
            parent.put("payment_gateway_id", "" + loginManager.getStringValue("Cash_PaymentGateWay_Id"));

            parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); // Right now static
            parent.put("payment_status", "0");  //static


            parent.put("payment_gateway_commission", "" + loginManager.getStringValue("Cash_Delivery_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());


            if (outletDetails.getDeliveryType() == 1) {
                parent.put("delivery_address", "0");
                parent.put("delivery_cost", "" + outletDetails.getDeliveryCost());
                parent.put("delivery_slot", "0");

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }

                outletDetails.setDeliveryTime("" + curr_format.format(current_date.getTime()));
                parent.put("delivery_date", "" + curr_format.format(current_date.getTime()));
                parent.put("order_type", "1");
            } else {
                parent.put("delivery_address", "");
                parent.put("delivery_slot", "0");
                parent.put("delivery_cost", "0");

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");
                parent.put("delivery_date", "" + curr_format.format(current_date.getTime()));
                outletDetails.setDeliveryTime("");
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
                productJsonObject.put("special_req", "");

                if (productRespository.getCartProductList().get(i).getIngredTypeList().size() != 0) {

                    JSONObject ingredientMainObject = new JSONObject();
                    int price = 0;
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
                    ingredientMainObject.put("ingredient_names", str);

                    Float total_price = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
//                    Log.e("total_price", "" + (total_price - price));
                    productJsonObject.put("discount_price", "" + (total_price - price));
                    productJsonObject.put("ingredients", ingredientMainObject);
                    productJsonObject.put("item_offer", "0");

                } else {
                    productJsonObject.put("ingredients", "");
                    productJsonObject.put("discount_price", productRespository.getCartProductList().get(i).getTotal());
                    productJsonObject.put("item_offer", "0");
                    productJsonObject.put("special_req", "");
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


        String country_code = expCheAdd.getCountry_code().trim();
        String mobile_no = expCheAdd.getPhone_no().toString().replaceAll("\\s+", "");

        Log.e("Lang_code", "" + loginManager.getStringValue("Lang_code"));
        Log.e("first_name", "" + expCheAdd.getFirst_name().trim());
        Log.e("last_name", "" + expCheAdd.getLast_name().trim());
        Log.e("email", "" + expCheAdd.getEmail().trim());

        Log.e("country_code", "- " + "+" + country_code);
        Log.e("mobile_num", mobile_no);

        Log.e("getCityID", "" + loginManager.getCityID());
        Log.e("getLocID", "" + loginManager.getLocID());

        Log.e("address", "" + expCheAdd.getAddress());
        Log.e("bui_flat_no", "" + expCheAdd.getBuilding_no());
        Log.e("land_mark", "" + expCheAdd.getLand_mark());

        Log.e("latitude", "" + expCheAdd.getLatitude());
        Log.e("longitude", "" + expCheAdd.getLongitude());
        Log.e("address_type", "" + expCheAdd.getAddress_type());

        Log.e("guest_type", "" + getString(R.string.expr_check_guest_type));
        Log.e("login_type", "" + getString(R.string.expr_check_login_type));
        Log.e("device_id", "" + loginManager.getStringValue("device_id"));
        Log.e("device_token", "" + loginManager.getStringValue("device_token"));

        Log.e("payment_array", "" + parent.toString());


        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        //vk call Otp here and after sucess Resp call getExpressCheckOutReq

        APIService express_check_out = Webdata.getRetrofit().create(APIService.class);
        express_check_out.getExpressCheckoutRequest(loginManager.getStringValue("Lang_code"),
                "" + expCheAdd.getFirst_name().trim(), "" + expCheAdd.getLast_name().trim(),
                "" + expCheAdd.getEmail().trim(), /*country_code,*/ "" + mobile_no,
                "" + loginManager.getCityID(), "" + loginManager.getLocID(),
                "" + expCheAdd.getAddress(), "" + expCheAdd.getBuilding_no(),
                "" + expCheAdd.getLand_mark(), "" + expCheAdd.getLongitude(),
                "" + expCheAdd.getLongitude(), "" + expCheAdd.getAddress_type(),
                "" + parent.toString(), "" + getString(R.string.expr_check_guest_type),
                "" + getString(R.string.expr_check_login_type), "" + loginManager.getStringValue("device_id"),
                "" + loginManager.getStringValue("device_token")).enqueue(new Callback<GustCheckOutReq>() {

            @Override
            public void onResponse(Call<GustCheckOutReq> call, Response<GustCheckOutReq> response) {

                try {

                    Log.e("getExpressCheckoutReq", "input" +new Gson().toJson(response.raw().request()));
                    Log.e("getExpressCheckoutReq", "Resp" +response.body());
                    progressBarDialog.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {

                        Intent order_cof = new Intent(getContext(), OrderConfirmationActivity.class);
                        outletDetails.setDeliveryType(1); //  delivery method
//                            outletDetails.setExp_check_out(true);
                        outletDetails.setDeliveryAddress(expCheAdd.getAddress());
                        order_cof.putExtra("ORDER", outletDetails);
                        order_cof.putExtra("DELIVERY_TEXT", "" + outletDetails.getDeliveryInstruction().trim());
                        order_cof.putExtra("PAYMENT_TYPE", "" + getString(R.string.cash_on_delivery_txt));
                        startActivity(order_cof);

                        getActivity().finish();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(getContext(), "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("getExpressCheckoutReq", "Exception" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<GustCheckOutReq> call, Throwable t) {
                progressBarDialog.dismiss();
                Log.e("getExpressCheckoutReq", "onFailure" + t.getMessage());
            }
        });

        try {


        } catch (Exception e) {
            Log.e("Exception 4", "- " + e.getMessage());
        }

    }


}
