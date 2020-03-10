package com.app.oniontray.Fragments;

import android.content.Context;
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
import com.app.oniontray.CustomViews.OTPDialogView;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.invokeOfflinePaymentMethod;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.VendorDetailForMyCart;
import com.app.oniontray.RequestModels.WalPaymReq;
import com.app.oniontray.RequestModels.WalletReq;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

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
 * Created by nextbrain on 17/5/17.
 */

public class WalletFragment extends Fragment {


    private Context context;
    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog progressBarDialog;

    private TextView wallet_balance_amount_txt_view;
    private TextView wallet_pay_amount_txt;
    private Button wallet_place_order_btn;


    private OutletDetails outletDetails;
    private String Vendor_id;

    private final Calendar current_date = Calendar.getInstance();

    private ProductRespository productRespository;
    private ArrayList<String> outletAndVendorID;

    private JSONObject payment_parms;
    private JSONObject parent;



    /*-------------------Wallet Amount-----------------*/

    private String wallet_amount;

    /*-------------------------------------------------*/


    public WalletFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wallet_fragment_layout, container, false);

        this.context = getContext();

        outletDetails = (OutletDetails) getArguments().getSerializable("outlet_details");
        Vendor_id = getArguments().getString("vendor_id");

        loginPrefManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());

        productRespository = new ProductRespository();
        outletAndVendorID = productRespository.getVendorID();


        wallet_balance_amount_txt_view = (TextView) rootView.findViewById(R.id.wallet_balance_amount_txt_view);
        wallet_pay_amount_txt = (TextView) rootView.findViewById(R.id.wallet_pay_amount_txt);

        wallet_place_order_btn = (Button) rootView.findViewById(R.id.wallet_place_order_btn);
        wallet_place_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceOrderClickEverntMthod();
            }
        });

        Init();

        return rootView;
    }


    public void Init() {

        String text = String.format(getResources().getString(R.string.welcome_online_wallet_messages), loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));
        wallet_pay_amount_txt.setText(Html.fromHtml(text));

        WalletDetailsRequestMethod();

    }

    private void WalletDetailsRequestMethod() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.UserWalletRequest("" + loginPrefManager.getStringValue("Lang_code"), loginPrefManager.getStringValue("user_id"),
                    loginPrefManager.getStringValue("user_token")).enqueue(new Callback<WalletReq>() {
                @Override
                public void onResponse(Call<WalletReq> call, Response<WalletReq> response) {

                    try {

                        progressBarDialog.dismiss();
//                        Log.e("UserWalletRequest", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            wallet_amount = "" + response.body().getResponse().getUserWalletAmount();

                            wallet_balance_amount_txt_view.setText(loginPrefManager.getFormatCurrencyValue("" + response.body().getResponse().getUserWalletAmount()));

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("UserWalletRequestMethod", "Exception :" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<WalletReq> call, Throwable t) {
                    progressBarDialog.dismiss();
//                    Log.e("onFailure", "" + t.toString());
                }
            });

        } catch (Exception e) {
//            Log.e("UserWalletRequestMethod", "Exception :" + e.getMessage());
        }
    }


    private void PlaceOrderClickEverntMthod() {

        try {

            Float wallet_amt = Float.parseFloat(wallet_amount);
            Float total_pay_amt = Float.parseFloat(outletDetails.getGrandTotal());

            if (wallet_amt < total_pay_amt) {
                Toast.makeText(context, "" + context.getString(R.string.less_wallet_amt_msg_txt), Toast.LENGTH_SHORT).show();
                return;
            }

            MyCartRequestMethod();

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
        }

    }

    private void MyCartRequestMethod() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

//            Log.e("user_id", "-" + loginPrefManager.getStringValue("user_id"));
//            Log.e("user_token", "-" + loginPrefManager.getStringValue("user_token"));
//            Log.e("outletAndVendorID", "-" + outletAndVendorID.get(0));
//            Log.e("outletAndVendorID", "-" + outletAndVendorID.get(1));
//            Log.e("getCityID", "-" + loginPrefManager.getCityID());
//            Log.e("getLocID", "-" + loginPrefManager.getLocID());

            APIService MyCartService = Webdata.getRetrofit().create(APIService.class);

            MyCartService.getOutletDetails("" + loginPrefManager.getStringValue("Lang_code"),
                    outletAndVendorID.get(0), outletAndVendorID.get(1), "" + loginPrefManager.getCityID(),
                    "" + loginPrefManager.getLocID()).enqueue(new Callback<VendorDetailForMyCart>() {
                @Override
                public void onResponse(Call<VendorDetailForMyCart> call, final Response<VendorDetailForMyCart> response) {

                    try {

                        progressBarDialog.dismiss();

//                        Log.e("getOutletDetails", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getOutletDetails().getOpenStatus() == 0) {
                                Toast.makeText(getContext(), "" + getString(R.string.restarent_was_closed_txt), Toast.LENGTH_SHORT).show();
                            } else {
                                GenerateJsonData();
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

//            {"payment_method":"wallet","payer_id":"0","payment_id":"0","country_code":""}

            payment_parms = new JSONObject();
            payment_parms.put("payment_method", "wallet");
            payment_parms.put("payer_id", "0");
            payment_parms.put("payment_id", "0");
            payment_parms.put("country_code", "");


            parent = new JSONObject();

            parent.put("user_id", "" + loginPrefManager.getStringValue("user_id"));
            parent.put("store_id", "" + Vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());

            parent.put("vendor_key", "" + outletDetails.getOutletName());
            parent.put("total", "" + outletDetails.getGrandTotal());
            parent.put("sub_total", "" + outletDetails.getSubTotal());
            parent.put("gst",outletDetails.getGst());
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
            parent.put("order_key", "");//static
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + outletDetails.getGrandTotal());
            parent.put("currency_code", "" + loginPrefManager.getCurrencySymbole());
            parent.put("payment_gateway_id", "0"); //static

            parent.put("delivery_charge", "" + outletDetails.getDeliveryCost());//Rightnow static
            parent.put("payment_status", "0");  //static


            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat("0")) / 100));

            int admin_commision = (((commission_one) + Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));
            parent.put("admin_commission", "" + admin_commision);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "0"); // static
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());

            parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
            parent.put("delivery_slot", "0");
            parent.put("delivery_cost", "0");

            SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                curr_format.setNumberFormat(nf);
            }

            parent.put("delivery_date", "" + curr_format.format(current_date.getTime()));
            parent.put("order_type", "1");

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
                    int price = 0;
                    int value = 0;
                    int cartCount = 0;
                    StringBuilder ingredientName = new StringBuilder();
                    for (int j = 0; j < productRespository.getCartProductList().get(i).getIngredTypeList().size(); j++) {

                        cartCount += productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size();

                        for (int k = 0; k < productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size(); k++) {
                            JSONObject ingredientInnerObject = new JSONObject();

                            price += Integer.parseInt(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
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

                    Float total_price=Float.parseFloat(""+productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price",""+(total_price-price));


                    productJsonObject.put("discount_price", ""+(total_price - price));                    productJsonObject.put("ingredients", ingredientMainObject);
                    productJsonObject.put("item_offer", "0");

                } else {

                    productJsonObject.put("ingredients", "");
                    productJsonObject.put("discount_price", productRespository.getCartProductList().get(i).getTotal());
                    productJsonObject.put("item_offer", "0");

                }

                productJsonArray.put(productJsonObject);
            }

            parent.put("items", productJsonArray);

            PlaceOrderRequestMethod();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void PlaceOrderRequestMethod() {

        OTPDialogView otpDialogView = new OTPDialogView(getActivity(), new invokeOfflinePaymentMethod() {
            @Override
            public void offlinePaymentMethod() {

                try {

                    if (progressBarDialog != null) {
                        progressBarDialog.show();
                    }

//                    Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
//                    Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//                    Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));
//                    Log.e("parent", "" + parent.toString());
//                    Log.e("payment_parms", "" + payment_parms.toString());

                    APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

                    place_ord_api_ser.WalletPaymentReq("" + loginPrefManager.getStringValue("Lang_code"),
                            "" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("user_token"),
                            "" + parent.toString(), "" + payment_parms.toString()).enqueue(new Callback<WalPaymReq>() {
                        @Override
                        public void onResponse(Call<WalPaymReq> call, Response<WalPaymReq> response) {

                            try {

                                progressBarDialog.dismiss();
//                                Log.e("response", "" + response.raw().toString());

                                if (response.body().getResponse().getHttpCode() == 200) {

                                    Intent order_cof = new Intent(getContext(), OrderConfirmationActivity.class);
                                    order_cof.putExtra("ORDER", outletDetails);
                                    order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction().trim());
                                    order_cof.putExtra("PAYMENT_TYPE", "" + getContext().getString(R.string.proc_to_pay_wallet_txt));
                                    startActivity(order_cof);

                                    getActivity().finish();

                                } else if (response.body().getResponse().getHttpCode() == 400) {
                                    Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                                    Log.e("response", response.body().getResponse().getMessage());
                                }

                            } catch (Exception e) {
                                progressBarDialog.dismiss();
//                                Log.e("otpDialogView Exception", "" + e.getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<WalPaymReq> call, Throwable t) {
                            progressBarDialog.dismiss();
//                            Log.e("onFailure", t.getMessage());
                        }
                    });

                } catch (Exception e) {
//                    Log.e("Exception", "" + e.getMessage());
                }

            }
        });
        otpDialogView.setCanceledOnTouchOutside(false);
        otpDialogView.show();

    }


}
