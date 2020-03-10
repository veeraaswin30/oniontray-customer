package com.app.oniontray.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import com.app.oniontray.DB.IngredientRepository;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Models.PaypalRes;
import com.app.oniontray.PayPalModule.OnlinePayRes;
import com.app.oniontray.PayPalModule.PayPalPaymentRes;
import com.app.oniontray.PayPalModule.PaypalSdkPayment;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ExpCheAdd;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.PaypalServiceGenerator;
import com.app.oniontray.WebService.Webdata;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 23/6/18.
 */

public class ExpCheckPayPalFragment extends Fragment {


    private ExpCheAdd expCheAdd;
    private OutletDetails outletDetails;
    private String vendor_id;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    public ProductRespository productRespository;
    public IngredientRepository ingredientRepository;


    private final Calendar current_date = Calendar.getInstance();

    private TextView cash_on_deliv_pay_amount_txt;
    private Button placeOrder;
    String currentDate = "";


    /*PayPal Configuration fields*/

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    //    private static final String CONFIG_CLIENT_ID = "Ae4-LW2brXUZqNuydM7g8GJf-U8MOn4pgttWC63QALgDiUozRUwvtmLM7iz7mlmuL_jD4udRz_lFlkd0";  // Sandbox
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AUj-HJJjkJ3g6sfbbUMiA2XWuxi0xO4ep2w4xTVQaOz6A5JZcEVzZP3yH_KQ7G3SapeZAVhhypBc3La0";


    private static final PayPalConfiguration config = new PayPalConfiguration()
            .acceptCreditCards(false)
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("vasansrini824-facilitator@gmail.com")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    private final String Currency = "USD";

    private PayPalPayment thingToBuy;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private JSONObject parent;
    private JSONObject paymenyArray;

    private PaypalSdkPayment paypalSdkPayment;
    private PayPalPaymentRes payPalPaymentRes;


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
        ingredientRepository = new IngredientRepository();


        cash_on_deliv_pay_amount_txt = (TextView) rootView.findViewById(R.id.cash_on_deliv_pay_amount_txt);
        placeOrder = (Button) rootView.findViewById(R.id.cash_on_deliv_place_order_btn);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        currentDate = formatter.format(date);
        Log.e("currentDate", currentDate);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String text = String.format(getResources().getString(R.string.welcome_online_pay_messages), "" + loginManager.getFormatCurrencyValue(loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal()))));
        cash_on_deliv_pay_amount_txt.setText(Html.fromHtml(text));

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayPalOTPMethod();
            }
        });

    }


    private void PayPalOTPMethod() {

        try {

            Intent intent = new Intent(getActivity(), PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            getActivity().startService(intent);

            if (loginManager.getStringValue("Lang_code").equals("1")) {
                thingToBuy = new PayPalPayment(new BigDecimal(outletDetails.getGrandTotal()), "USD", getString(R.string.paypal_purchase), PayPalPayment.PAYMENT_INTENT_SALE);
            } else {
                thingToBuy = new PayPalPayment(new BigDecimal(outletDetails.getGrandTotal()), "MXN", getString(R.string.paypal_purchase), PayPalPayment.PAYMENT_INTENT_SALE);
            }

//            thingToBuy = new PayPalPayment(new BigDecimal(outletDetails.getGrandTotal()), Currency, "Purchase", PayPalPayment.PAYMENT_INTENT_SALE);

            Intent payment_activity = new Intent(getActivity(), PaymentActivity.class);
            payment_activity.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            startActivityForResult(payment_activity, REQUEST_CODE_PAYMENT);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        PayPal request code

        if (requestCode == REQUEST_CODE_PAYMENT) {

            if (resultCode == Activity.RESULT_OK) {

                if (resultCode == Activity.RESULT_OK) {

                    PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                    if (confirm != null) {
                        try {

                            String paymentDetails = confirm.toJSONObject().toString(4);
//                        Log.e("paymentExample", paymentDetails);

                            JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));

                            if (jsonObject != null) {

                                paypalSdkPayment = new PaypalSdkPayment();

                                JSONObject JsonClientObject = jsonObject.getJSONObject("client");

                                paypalSdkPayment.setEnvironment("" + JsonClientObject.getString("environment"));
                                paypalSdkPayment.setPaypalSdkVersion("" + JsonClientObject.getString("paypal_sdk_version"));
                                paypalSdkPayment.setPlatform("" + JsonClientObject.getString("platform"));
                                paypalSdkPayment.setProductName("" + JsonClientObject.getString("product_name"));


                                JSONObject JsonRespObject = jsonObject.getJSONObject("response");

                                paypalSdkPayment.setCreateTime("" + JsonRespObject.getString("create_time"));
                                paypalSdkPayment.setId("" + JsonRespObject.getString("id"));
                                paypalSdkPayment.setIntent("" + JsonRespObject.getString("intent"));
                                paypalSdkPayment.setState("" + JsonRespObject.getString("state"));


                                paypalSdkPayment.setResponseType("" + jsonObject.getString("response_type"));

                                PayPalTokenRequest();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getContext(), "Future Payment code received from PayPal", Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }


    private void PayPalTokenRequest() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }


            //  Sandbox

//            String client_id = "Ae4-LW2brXUZqNuydM7g8GJf-U8MOn4pgttWC63QALgDiUozRUwvtmLM7iz7mlmuL_jD4udRz_lFlkd0";
//            String secrite_id = "ENvJe8nYY8ueLt6vJgXbuY0_Als8nMI6ycTMbcQhF-3OGRBjkyeh_OjITBCwLd-LQOFUQENjQan563aP";

            //  Live
            //  Live
            String client_id = "AQIGW8aWyr9X8OrvcINBrJZDPiO0aT-AvGI0qApvlIEVAgvSnCgQHlNIPtTEOhtl8ynM_X3ghM8pZyKY";
            String secrite_id = "EOe3jhWtxFeha1p3p6ojQx8BvgjhFV7OLYazVWW1vD-sshFdRAKwz9N3lV5ObZm3iT7Wd574maIQWfLY";

            APIService apiService = PaypalServiceGenerator.createService(APIService.class, client_id, secrite_id);

            Call<PaypalRes> call = apiService.basicLogin("client_credentials");
            call.enqueue(new Callback<PaypalRes>() {
                @Override
                public void onResponse(Call<PaypalRes> call, Response<PaypalRes> response) {

                    try {
                        Log.e("Token onResponse", "" + response.raw().toString());
                        progressBarDialog.dismiss();

                        if (response.isSuccessful()) {
                            getPaypalPayment("" + paypalSdkPayment.getId(), response.body().getAccessToken());
                        } else {
//                            Log.e("Token Resp Failure", "" + response.raw().toString());
                        }

                    } catch (Exception e) {
                        Log.e("Token Resp Exception", " - " + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<PaypalRes> call, Throwable t) {
                    Log.e("onFailure", t.getMessage());
                    progressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {
            Log.e("Token Exception", " - " + e.getMessage());
            progressBarDialog.dismiss();
        }

    }


    private void getPaypalPayment(String payment_id, String acces_Token) {

        Log.e("payment_id", "" + payment_id);
        Log.e("acces_Token", "" + acces_Token);

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

//            InputStream instream = getActivity().getResources().openRawResource(R.raw.root);

            APIService paypal_payment_Service = PaypalServiceGenerator.createPaymentService(APIService.class, acces_Token);

            paypal_payment_Service.getPaymentDetails(payment_id).enqueue(new Callback<PayPalPaymentRes>() {
                @Override
                public void onResponse(Call<PayPalPaymentRes> call, Response<PayPalPaymentRes> response) {

                    try {

                        Log.e("payment onResponse", "" + response.raw().toString());
                        progressBarDialog.dismiss();

                        if (response.isSuccessful()) {
                            payPalPaymentRes = response.body();
                            GenerateJsonData();
                        } else {
//                            Log.e("payment Resp Failure", "" + response.raw().toString());
                        }

                    } catch (Exception e) {
//                        Log.e("paypal_payment_Service", "Exception " + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<PayPalPaymentRes> call, Throwable t) {
//                    Log.e("payment onFailure", "" + t.getMessage());
                    progressBarDialog.dismiss();
                }

            });

        } catch (Exception e) {
//            Log.e("payment Exception", " - " + e.getMessage());
            progressBarDialog.dismiss();
        }

    }


    private void GenerateJsonData() {

        try {

            paymenyArray = new JSONObject();
            paymenyArray.put("payment_method", "" + payPalPaymentRes.getPayer().getPaymentMethod());
            paymenyArray.put("payer_id", "" + payPalPaymentRes.getPayer().getPayerInfo().getPayerId());
            paymenyArray.put("payment_id", "" + payPalPaymentRes.getTransactions().get(0).getRelatedResources().get(0).getSale().getId());
            paymenyArray.put("country_code", "" + payPalPaymentRes.getPayer().getPayerInfo().getCountryCode());
            paymenyArray.put("cart_id", "" + payPalPaymentRes.getCart());

            parent = new JSONObject();


            parent.put("store_id", "" + vendor_id);
            parent.put("outlet_id", "" + outletDetails.getOutletsId());

            parent.put("vendor_key", "" + outletDetails.getOutletName());
            parent.put("total", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal())));
            parent.put("sub_total", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getSubTotal())));

            parent.put("contact_address", "" + outletDetails.getContactAddress());
            parent.put("contact_email", "" + outletDetails.getContactEmail());
            parent.put("outlet_name", "" + outletDetails.getOutletName());
            parent.put("gst",outletDetails.getGst());

            if (outletDetails.getTaxType() == 2) {
                parent.put("service_tax", "0" /*+ outletDetails.getServiceTax()*/);
                parent.put("tax_label_name", "");
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            } else {
                parent.put("service_tax", "0" /*+ outletDetails.getServiceTax()*/);
                parent.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
                parent.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            }

            parent.put("order_status", "1");//static
            parent.put("order_key", "");
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + loginManager.GetEngDecimalFormatValues(Float.valueOf(outletDetails.getGrandTotal())));
            parent.put("currency_code", "" + loginManager.getCurrencySymbole());

            parent.put("payment_gateway_id", "" + loginManager.getStringValue("Paypal_PaymentGateWay_Id"));
            parent.put("payment_method", "" + getResources().getString(R.string.tab_one_name));

            parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); // Right now static
            parent.put("payment_status", "0");  // static

            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat(outletDetails.getCommissionAmount())) / 100));

          /*  int admin_commision = (((commission_one) *//*+ Math.round(Float.parseFloat("" + outletDetails.getServiceTax()))*//*) +
                    Math.round(Float.parseFloat("" + outletDetails.getDeliveryCost())));*/

            parent.put("admin_commission", "" + commission_one);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginManager.getStringValue("Paypal_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());


            if (outletDetails.getDeliveryType() == 1) {
                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
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

                }

                productJsonArray.put(productJsonObject);
            }

            parent.put("items", productJsonArray);

            PayPalOnlinePaymentRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void PayPalOnlinePaymentRequest() {

        try {

            String country_code = expCheAdd.getCountry_code().trim();
            String mobile_no = expCheAdd.getPhone_no().toString().replaceAll("\\s+", "");

            Log.e("Lang_code", "" + loginManager.getStringValue("Lang_code"));
            Log.e("first_name", "" + expCheAdd.getFirst_name().trim());
            Log.e("last_name", "" + expCheAdd.getLast_name().trim());
            Log.e("email", "" + expCheAdd.getEmail().trim());

            Log.e("country_code", "- " + country_code);
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

            Log.e("PayPal app data array", "" + parent.toString());
            Log.e("PayPal pay data array", "" + paymenyArray.toString());

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

            place_ord_api_ser.guestPayPalOnlinePayment(loginManager.getStringValue("Lang_code"),
                    expCheAdd.getFirst_name().trim(), expCheAdd.getLast_name().trim(),
                    expCheAdd.getEmail().trim(), mobile_no, expCheAdd.getAddress().trim(),
                    expCheAdd.getBuilding_no(), expCheAdd.getLand_mark(), expCheAdd.getLatitude(),
                    expCheAdd.getLongitude(), loginManager.getCityID(), loginManager.getLocID(),
                    expCheAdd.getAddress_type(), getString(R.string.expr_check_guest_type),
                    getString(R.string.expr_check_login_type), loginManager.getStringValue("device_id"),
                    loginManager.getStringValue("device_token"), parent.toString(),
                    paymenyArray.toString()).enqueue(new Callback<OnlinePayRes>() {
                @Override
                public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {

                    try {

                        progressBarDialog.dismiss();
                        Log.e("response", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            Intent order_cof = new Intent(getContext(), OrderConfirmationActivity.class);
                            outletDetails.setDeliveryType(1); //  delivery method
                            outletDetails.setExp_check_out(true);
                            outletDetails.setDeliveryAddress(expCheAdd.getAddress());
                            order_cof.putExtra("ORDER", outletDetails);
                            order_cof.putExtra("DELIVERY_TEXT", "" + outletDetails.getDeliveryInstruction().trim());
                            order_cof.putExtra("PAYMENT_TYPE", "" + getString(R.string.tab_one_name));
                            startActivity(order_cof);

                            getActivity().finish();

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Log.e("response", response.body().getResponse().getMessage());
                        }

                    } catch (Exception e) {
                        progressBarDialog.dismiss();
                        Log.e("otpDialogView Exception", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<OnlinePayRes> call, Throwable t) {
                    progressBarDialog.dismiss();
                    Log.e("onFailure", t.getMessage());
                }

            });

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }


    }


    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
    }


    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
