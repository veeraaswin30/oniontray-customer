package com.app.oniontray.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.app.oniontray.RequestModels.CurrConvRes;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.PaypalServiceGenerator;
import com.app.oniontray.WebService.Webdata;
import com.google.gson.Gson;
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

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 25/5/17.
 */

public class PaypalFragment extends Fragment {


    private View paypal_frag_view;

    private TextInputLayout pay_opt_card_num_txt_lay, pay_opt_card_name_on_card_txt_lay, pay_opt_card_cvv_num_txt_lay;
    private EditText pay_opt_card_num_edt_txt, pay_opt_card_name_on_card_edt_txt, pay_opt_card_cvv_num_edt_txt;

    Spinner pay_opt_card_month_spinner, pay_opt_card_year_spinner;
    private ArrayAdapter<String> monthSpinnerArrayAdapter, yearSpinnerArrayAdapter;
    private Button pay_opt_card_pay_btn;

    ArrayList<String> yearArrayList = new ArrayList<>();

    List<String> monthArrayList = new ArrayList<>();

    private final static String TEST_TOKEN_URL = "https://sbpaymentservices.payfort.com/FortAPI/paymentApi";

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "AUj-HJJjkJ3g6sfbbUMiA2XWuxi0xO4ep2w4xTVQaOz6A5JZcEVzZP3yH_KQ7G3SapeZAVhhypBc3La0";


    private static final PayPalConfiguration config = new PayPalConfiguration()
            .acceptCreditCards(false)
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("hariprasath.s_api1.nextbrainitech.com")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

    private final String Currency = "USD";

    private PayPalPayment thingToBuy;
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    private JSONObject parent;
    private JSONObject paymenyArray;


    private PaypalSdkPayment paypalSdkPayment;
    private PayPalPaymentRes payPalPaymentRes;

    private final Calendar current_date = Calendar.getInstance();

    private String currency_converted_amt = "";


    private OutletDetails outletDetails;
    private String Vendor_id;


    private TextView cash_on_deliv_pay_amount_txt;
    private Button placeOrder;


    public ProductRespository productRespository;
    public IngredientRepository ingredientRepository;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        paypal_frag_view = inflater.inflate(R.layout.cash_on_delivery_fragment, container, false);

        // Outlet Details get in Frangment arguments.
        outletDetails = (OutletDetails) getArguments().getSerializable("outlet_details");
        Vendor_id = getArguments().getString("vendor_id");


        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());


        productRespository = new ProductRespository();
        ingredientRepository = new IngredientRepository();


        cash_on_deliv_pay_amount_txt = (TextView) paypal_frag_view.findViewById(R.id.cash_on_deliv_pay_amount_txt);

        String text = String.format(getResources().getString(R.string.welcome_online_pay_messages), "" + loginManager.getFormatCurrencyValue("" + outletDetails.getGrandTotal()));
        cash_on_deliv_pay_amount_txt.setText(Html.fromHtml(text));


        placeOrder = (Button) paypal_frag_view.findViewById(R.id.cash_on_deliv_place_order_btn);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalOTPMethod();
            }
        });

        CurrencyConverterRequestMethod(outletDetails.getGrandTotal());

        return paypal_frag_view;
    }

    private void PayPalOTPMethod() {

//        Nextbrain Paypal Account Deatils:-
//                buyer acc saran-buyer@nextbrainitech.com Saran123!
//                seller acc saran-facilitator@nextbrainitech.com Saran123!

        if (currency_converted_amt.isEmpty()) {
            return;
        }


        try {

            Intent intent = new Intent(getActivity(), PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            getActivity().startService(intent);

            thingToBuy = new PayPalPayment(new BigDecimal(currency_converted_amt), Currency, "Purchase", PayPalPayment.PAYMENT_INTENT_SALE);

            Intent payment_activity = new Intent(getActivity(), PaymentActivity.class);
            payment_activity.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
            startActivityForResult(payment_activity, REQUEST_CODE_PAYMENT);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    private void CurrencyConverterRequestMethod(String CurrencyAmount) {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }


            Log.e("user_id", "" + loginManager.getStringValue("user_id"));
            Log.e("user_token", "" + loginManager.getStringValue("user_token"));
            Log.e("CurrencyAmount", "" + CurrencyAmount);
            Log.e("CurrencyCode", "" + loginManager.getCurrencyCode());


            APIService curreConvertRequest = Webdata.getRetrofit().create(APIService.class);

            curreConvertRequest.CurrentyConverterRequest(loginManager.getStringValue("user_id"),
                    loginManager.getStringValue("user_token"), "" + CurrencyAmount, "" +loginManager.getStringValue("currency_code")).enqueue(new Callback<CurrConvRes>() {
                @Override
                public void onResponse(Call<CurrConvRes> call, Response<CurrConvRes> response) {

                    try {

                        progressBarDialog.dismiss();
                        Log.e("response", new Gson().toJson(response.raw().request()));
                        Log.e("o/p", new Gson().toJson(response.body()));

                        if (response.body().getResponse().getHttpCode() == 200) {

                            currency_converted_amt = response.body().getResponse().getConvertedAmount();

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Log.e("response", response.body().getResponse().getMessage());
                        }

                    } catch (Exception e) {
                        progressBarDialog.dismiss();
                        Log.e("otpDialogView Exception", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CurrConvRes> call, Throwable t) {
                    progressBarDialog.dismiss();
//                    Log.e("onFailure", t.getMessage());
                }
            });

        } catch (Exception e) {
//            Log.e("CurrencyConverterReq", "Exception" + e.getMessage());
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

//        paypal request code
        if (requestCode == REQUEST_CODE_PAYMENT) {

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
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
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

            String client_id = "AQIGW8aWyr9X8OrvcINBrJZDPiO0aT-AvGI0qApvlIEVAgvSnCgQHlNIPtTEOhtl8ynM_X3ghM8pZyKY";
            String secrite_id = "EOe3jhWtxFeha1p3p6ojQx8BvgjhFV7OLYazVWW1vD-sshFdRAKwz9N3lV5ObZm3iT7Wd574maIQWfLY";

//            String client_id = "AQo2YG4E0f5xyXm6DcNBDB7bYu7KkW4h0xogHAVFRkw0ic3NYT8cv_jnS";
//            String secrite_id = "EKU9ldXDwnM68zBJeQGlGpo9NLAywPMwY0iopDccVg9SOAqMnHeKaE7PuBneGp2JnBB8UkkS0TPi-sKd";

//            InputStream instream = getActivity().getResources().openRawResource(R.raw.api_sandbox);

            APIService apiService = PaypalServiceGenerator.createService(APIService.class, client_id, secrite_id);

            Call<PaypalRes> call = apiService.basicLogin("client_credentials");
            call.enqueue(new Callback<PaypalRes>() {
                @Override
                public void onResponse(Call<PaypalRes> call, Response<PaypalRes> response) {

                    try {
//                        Log.e("Token onResponse", "" + response.raw().toString());
                        progressBarDialog.dismiss();

                        if (response.isSuccessful()) {
                            getPaypalPayment("" + paypalSdkPayment.getId(), response.body().getAccessToken());
                        } else {
//                            Log.e("Token Resp Failure", "" + response.raw().toString());
                        }

                    } catch (Exception e) {
//                        Log.e("Token Resp Exception", " - " + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<PaypalRes> call, Throwable t) {
//                    Log.e("onFailure", t.getMessage());
                    progressBarDialog.dismiss();
                }
            });


        } catch (Exception e) {
//            Log.e("Token Exception", " - " + e.getMessage());
            progressBarDialog.dismiss();
        }

    }

    private void getPaypalPayment(String payment_id, String acces_Token) {

//        Log.e("payment_id", "" + payment_id);
//        Log.e("acces_Token", "" + acces_Token);

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

//            Log.e("PayPal paymenyArray", "" + paymenyArray.toString());


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

            parent.put("order_status", "1");//static
            parent.put("order_key", "");
            parent.put("transaction_staus", "1"); //static

            parent.put("transaction_amount", "" + outletDetails.getGrandTotal());
            parent.put("currency_code", "" + loginManager.getCurrencySymbole());

            parent.put("payment_gateway_id", "" + loginManager.getStringValue("Paypal_PaymentGateWay_Id"));
            parent.put("payment_method", "" + getResources().getString(R.string.tab_one_name));

            parent.put("payment_status", "0");  //static

            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat("" + outletDetails.getCommissionAmount())) / 100));


            parent.put("admin_commission", "" + commission_one);

            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            parent.put("vendor_commission", "" + vender_commision);

            parent.put("payment_gateway_commission", "" + loginManager.getStringValue("Paypal_Commision"));
            parent.put("delivery_instructions", "" + outletDetails.getDeliveryInstruction().trim());


            parent.put("delivery_date", "" +outletDetails.getDeliveryDate());
            outletDetails.setDeliveryTime("" +outletDetails.getDeliveryDate());
            if (outletDetails.getPaymentOption()==1) {

                parent.put("delivery_address", "" + outletDetails.getDeliveryAddressID());
                parent.put("delivery_slot", "1"); //static
                parent.put("delivery_cost", ""+outletDetails.getDeliveryCost());

                SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                    curr_format.setNumberFormat(nf);
                }

                parent.put("delivery_charge", "" + outletDetails.getDeliveryCost()); //Rightnow static

                parent.put("order_type", "1");
            }else{

                parent.put("delivery_address", "");
                parent.put("delivery_slot", "1"); //static
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

                    Float total_price_to_fc = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price", "" + (total_price_to_fc - price));

                    Float total_price = total_price_to_fc - price;

                    String total_price_sconverted = "" + total_price;

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

//            Log.e("PayPal prod array", "" + parent.toString());

            PaypalOnlinePaymentRequest();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void PaypalOnlinePaymentRequest() {

        try {

            Log.e("PayPal app data array", "" + parent.toString());
            Log.e("PayPal pay data array", "" + paymenyArray.toString());

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService place_ord_api_ser = Webdata.getRetrofit().create(APIService.class);

            place_ord_api_ser.onlinnePayment(loginManager.getStringValue("user_token"), loginManager.getStringValue("user_id"),
                    loginManager.getStringValue("Lang_code"), parent.toString(),
                    paymenyArray.toString()).enqueue(new Callback<OnlinePayRes>() {
                @Override
                public void onResponse(Call<OnlinePayRes> call, Response<OnlinePayRes> response) {

                    try {
                        Log.e("req", new Gson().toJson(response.raw().request()));
                        Log.e("resp", new Gson().toJson(response.body()));

                        progressBarDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            Intent order_cof = new Intent(getContext(), OrderConfirmationActivity.class);
                            order_cof.putExtra("ORDER", outletDetails);
                            order_cof.putExtra("DELIVERY_TEXT", outletDetails.getDeliveryInstruction().trim());
                            order_cof.putExtra("PAYMENT_TYPE", "" + getResources().getString(R.string.tab_one_name));
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
//                    Log.e("onFailure", t.getMessage());
                }
            });

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
        }


    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
    }

    @Override
    public void onDestroy() {
        getActivity().stopService(new Intent(getContext(), PayPalService.class));
        super.onDestroy();
    }

    public String getBase64String(String value) throws UnsupportedEncodingException {
        return Base64.encodeToString(value.getBytes("UTF-8"), Base64.NO_WRAP);
    }


}
