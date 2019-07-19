package com.app.oniontray.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.oniontray.Adapters.MycartAdapter;
import com.app.oniontray.Fragments.DeliveryFragment;
import com.app.oniontray.Fragments.PickUpFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.ProcToCheck;
import com.app.oniontray.RequestModels.VendorDetailForMyCart;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantMycartActivity extends LocalizationActivity implements MycartAdapter.MyCartsUpdateInterface {

    private Toolbar restaurant_mycart_toolbar;

    private RecyclerView restaurant_mycart_recycler_view;
    private TextView restaurant_mycart_title, restaurant_subtotal, my_cart_empty_text_view;

    private Button restaurant_add_more, restaurant_check_out;
    private Button restaurant_take_away, restaurant_self_pickup;
    private ArrayList<String> outletAndVendorID;

    private OutletDetails outletDetails;
    private MycartAdapter myCartAdapter;
    private String mini_order_amt = "";
    private TextView review_title;

    private LinearLayout overallView;

    private LinearLayout my_cart_add_more_check_out_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_my_cart);

        intializeView();

        toolbarBackClickEvent();

        AddMoreClickEvent();

        CheckOutClickEvent();

        takeAwaySelfPickupClickEvent();
    }

    private void takeAwaySelfPickupClickEvent() {

        restaurant_take_away.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectInto("takeaway");
            }
        });

        restaurant_self_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectInto("pickup");
            }
        });
    }

    private void redirectInto(String type) {
        if (myCartAdapter != null && myCartAdapter.getItemCount() != 0) {
            Float mtotal_value = Float.valueOf(productRespository.totalPrice());
            Float min_order_amouont = Float.valueOf(mini_order_amt);

            int mproduct_total_price = Math.round(mtotal_value);
            int min_order_amt = Math.round(min_order_amouont);
            if (min_order_amt > mproduct_total_price) {
                showExitWarningDialog(RestaurantMycartActivity.this);
            } else {

                if (loginPrefManager.getStringValue("user_id").isEmpty()) {

                    Intent signin_intent = new Intent(RestaurantMycartActivity.this, RestaurantSignInSignUpActivity.class);
                    signin_intent.putExtra("proc_to_check", true);
                    signin_intent.putExtra("outlet_details", outletDetails);
                    signin_intent.putExtra("vendor_id", outletAndVendorID.get(0));
                    signin_intent.putExtra("FromMycart", true);
                    startActivity(signin_intent);

                } else {
                    Intent checkout;
                    if (type.equals("takeaway"))
                        checkout = new Intent(RestaurantMycartActivity.this, DeliveryActivity.class);
                    else
                        checkout = new Intent(RestaurantMycartActivity.this, PickUpActivity.class);
                    checkout.putExtra("outlet_details", outletDetails);
                    checkout.putExtra("vendor_id", outletAndVendorID.get(0));
                    startActivity(checkout);

                }

            }

        } else {
            Toast.makeText(RestaurantMycartActivity.this, "" + getString(R.string.my_carts_err_msg_when_cart_empty_txt), Toast.LENGTH_SHORT).show();
        }
    }


    private void intializeView() {

        restaurant_mycart_toolbar = findViewById(R.id.restaurant_mycart_toolbar);

        setSupportActionBar(restaurant_mycart_toolbar);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(RestaurantMycartActivity.this));


        if (language.equals("en")) {

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        outletAndVendorID = productRespository.getVendorID();

        overallView = findViewById(R.id.overall_view);
        my_cart_empty_text_view = findViewById(R.id.my_cart_empty_text_view);

        restaurant_mycart_title = findViewById(R.id.restaurant_mycart_title);
        restaurant_subtotal = findViewById(R.id.restaurant_subtotal);


        restaurant_take_away = findViewById(R.id.restaurant_take_away);
        restaurant_self_pickup = findViewById(R.id.restaurant_self_pickup);
        restaurant_add_more = findViewById(R.id.restaurant_add_more);
        restaurant_check_out = findViewById(R.id.restaurant_check_out);
        restaurant_check_out.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        restaurant_check_out.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        review_title = findViewById(R.id.review_title);
        review_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        restaurant_mycart_recycler_view = findViewById(R.id.restaurant_mycart_recycler_view);

        restaurant_mycart_recycler_view.setHasFixedSize(true);
        restaurant_mycart_recycler_view.setLayoutManager(new LinearLayoutManager(RestaurantMycartActivity.this));
        restaurant_mycart_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(RestaurantMycartActivity.this, R.dimen.prod_list_item_row_line_height));

        my_cart_add_more_check_out_layout = findViewById(R.id.my_cart_add_more_check_out_layout);

    }


    private void toolbarBackClickEvent() {
        restaurant_mycart_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseBackMethod();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        MyCartReloadMethod();
    }

    @Override
    public void onBackPressed() {
        BaseBackMethod();
    }

    public void BaseBackMethod() {
//        Intent intent_rece = new Intent("base_activity_receiver");
//        intent_rece.putExtra("page_name", "0");
//        LocalBroadcastManager.getInstance(RestaurantMycartActivity.this).sendBroadcast(intent_rece);
        BaseMenuTabActivity.base_menu_tabhost.setCurrentTab(0);
    }

    public void MyCheckOutCallMethod() {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            startActivity(new Intent(RestaurantMycartActivity.this, MyFavouritesActivity.class));
        } else {
            LoginConfirmationDialog();
        }
    }

    public void MyCartReloadMethod() {

        outletAndVendorID = productRespository.getVendorID();

        if (outletAndVendorID.size() != 0) {

            MyCartRequestMethod();

//            Log.e("size", "" + productRespository.getCartProductList().size());
//            Log.e("total", "" + productRespository.totalPrice());

            overallView.setVisibility(View.VISIBLE);
            my_cart_empty_text_view.setVisibility(View.GONE);

            myCartAdapter = new MycartAdapter(RestaurantMycartActivity.this, productRespository.getCartProductList());
            myCartAdapter.MyCartsinterfaceCallMethod(RestaurantMycartActivity.this);
            restaurant_mycart_recycler_view.setAdapter(myCartAdapter);
            restaurant_subtotal.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues((Float.parseFloat(productRespository.totalPrice())))));

        } else {
            overallView.setVisibility(View.GONE);
            my_cart_empty_text_view.setVisibility(View.VISIBLE);
        }

    }


    private void MyCartRequestMethod() {

        if (!isFinishing()) {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }

        Log.e("user_id", "-" + loginPrefManager.getStringValue("user_id"));
        Log.e("user_token", "-" + loginPrefManager.getStringValue("user_token"));
        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(0));
        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(1));
        Log.e("getCityID", "-" + loginPrefManager.getCityID());
        Log.e("getLocID", "-" + loginPrefManager.getLocID());

        APIService MyCartService = Webdata.getRetrofit().create(APIService.class);

        MyCartService.getOutletDetails("" + loginPrefManager.getStringValue("Lang_code"),
                "" + outletAndVendorID.get(0), "" + outletAndVendorID.get(1), "" + loginPrefManager.getCityID(),
                "" + loginPrefManager.getLocID()).enqueue(new Callback<VendorDetailForMyCart>() {
            @Override
            public void onResponse(Call<VendorDetailForMyCart> call, final Response<VendorDetailForMyCart> response) {

                try {


                    progressDialog.dismiss();


                    if (response.body().getResponse().getHttpCode() == 200) {

                        outletDetails = response.body().getResponse().getOutletDetails();

                        mini_order_amt = "" + response.body().getResponse().getOutletDetails().getMinimumOrderAmount();

                        restaurant_mycart_title.setText("" + response.body().getResponse().getOutletDetails().getOutletName() + " - " + response.body().getResponse().getOutletDetails().getLocationName());


                        if (response.body().getResponse().getOutletDetails().getOpenStatus() == 0) {
                            my_cart_add_more_check_out_layout.setVisibility(View.GONE);
                        } else {
                            my_cart_add_more_check_out_layout.setVisibility(View.VISIBLE);
                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {

                        Toast.makeText(RestaurantMycartActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        my_cart_add_more_check_out_layout.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<VendorDetailForMyCart> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("Exception", t.toString());
            }
        });

    }


    private void AddMoreClickEvent() {

        restaurant_add_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent productIntent = new Intent(RestaurantMycartActivity.this, ProductMenuItemListWithDynamicTabs.class);
                productIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                productIntent.putExtra("vender_name", outletAndVendorID.get(0));
                productIntent.putExtra("vender_id", outletAndVendorID.get(0));
                productIntent.putExtra("outlet_id", outletAndVendorID.get(1));
                startActivity(productIntent);

            }
        });
    }


    private void CheckOutClickEvent() {

        restaurant_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCartAdapter != null && myCartAdapter.getItemCount() != 0) {
                    Float mtotal_value = Float.valueOf(productRespository.totalPrice());
                    Float min_order_amouont = Float.valueOf(mini_order_amt);

                    int mproduct_total_price = Math.round(mtotal_value);
                    int min_order_amt = Math.round(min_order_amouont);
                    if (min_order_amt > mproduct_total_price) {
                        showExitWarningDialog(RestaurantMycartActivity.this);
                    } else {

                        if (loginPrefManager.getStringValue("user_id").isEmpty()) {

                            Intent signin_intent = new Intent(RestaurantMycartActivity.this, RestaurantSignInSignUpActivity.class);
                            signin_intent.putExtra("proc_to_check", true);
                            signin_intent.putExtra("outlet_details", outletDetails);
                            signin_intent.putExtra("vendor_id", outletAndVendorID.get(0));
                            signin_intent.putExtra("FromMycart", true);
                            startActivity(signin_intent);

                        } else {

                            Intent proceedToCheck = new Intent(RestaurantMycartActivity.this, ProceedToCheckActivity.class);
                            proceedToCheck.putExtra("outlet_details", outletDetails);
                            proceedToCheck.putExtra("vendor_id", outletAndVendorID.get(0));
                            startActivity(proceedToCheck);

                        }

                    }

                } else {
                    Toast.makeText(RestaurantMycartActivity.this, "" + getString(R.string.my_carts_err_msg_when_cart_empty_txt), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void CheckOutDetailAPICall(JSONArray jsonArray) {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

//            Log.e("userID", loginPrefManager.getStringValue("user_id"));
//            Log.e("lang", loginPrefManager.getStringValue("Lang_code"));
//            Log.e("jsonArray", jsonArray.toString());

            apiService.checkout_detail(loginPrefManager.getStringValue("user_id"),
                    loginPrefManager.getStringValue("Lang_code"), jsonArray.toString(),
                    outletAndVendorID.get(0)).enqueue(new Callback<ProcToCheck>() {
                @Override
                public void onResponse(Call<ProcToCheck> call, Response<ProcToCheck> response) {
                    progressDialog.dismiss();
                    try {
                        if (response.body().getResponse().getHttpCode() == 200) {

                            Intent proceedToCheck = new Intent(RestaurantMycartActivity.this, ProceedToCheckActivity.class);
                            proceedToCheck.putExtra("outlet_details", outletDetails);
                            proceedToCheck.putExtra("vendor_id", outletAndVendorID.get(0));
                            startActivity(proceedToCheck);

                        }

                    } catch (Exception e) {
//                        Log.e("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ProcToCheck> call, Throwable t) {

                    progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
        }

    }

    //    Minimum order aleart dialog
    private void showExitWarningDialog(Context context) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context, R.style.MyDialogStyle);
        alertDialog.setTitle("" + getString(R.string.my_cart_mini_amt_dialog_txt) + " " + loginPrefManager.getFormatCurrencyValue("" + mini_order_amt));
        alertDialog.setPositiveButton("" + getApplicationContext().getString(R.string.ok_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void UpdateCartsDetatils() {

        if (myCartAdapter != null) {

            if (myCartAdapter.getItemCount() == 0) {
                MyCartTitleCountUpdateFromAdapter(0);
            } else {
                MyCartTitleCountUpdateFromAdapter(productRespository.getCartCount());
            }

        }
    }

    private void MyCartTitleCountUpdateFromAdapter(int count) {
        if (count == 0) {
            overallView.setVisibility(View.GONE);
            my_cart_empty_text_view.setVisibility(View.VISIBLE);

        } else {
            restaurant_subtotal.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));
        }
    }

    private JSONArray FormJsonData() {

        JSONArray jsonArray = new JSONArray();

        JSONObject VendorJsonObject = new JSONObject();

        try {

            VendorJsonObject.put("vendors_id", outletAndVendorID.get(0));

            JSONObject innerOutletJsonObject = new JSONObject();

            innerOutletJsonObject.put("outlet_id", outletDetails.getOutletsId());
            innerOutletJsonObject.put("outlet_name", outletDetails.getOutletName());
            innerOutletJsonObject.put("minimum_order_amount", outletDetails.getMinimumOrderAmount());

            JSONArray productJsonArray = new JSONArray();

            for (int i = 0; i < productRespository.getCartProductList().size(); i++) {
                JSONObject productJsonObject = new JSONObject();

                productJsonObject.put("product_id", productRespository.getCartProductList().get(i).getProductId());

                JSONObject innerJsonObject = new JSONObject();

                innerJsonObject.put("quantity", productRespository.getCartProductList().get(i).getCartCount());


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

                    Float total_price = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price", "" + (total_price - price));


                    innerJsonObject.put("discount_price", "" + (total_price - price));
                    innerJsonObject.put("ingredient_count", cartCount);
                    innerJsonObject.put("ingredient_price", price);
                    innerJsonObject.put("ingredients", ingredientMainObject);
                } else {

                    innerJsonObject.put("discount_price", productRespository.getCartProductList().get(i).getTotal());
                    innerJsonObject.put("ingredient_count", "0");
                    innerJsonObject.put("ingredient_price", productRespository.getCartProductList().get(i).getIngredTypeList().size());

                }
                productJsonObject.put("" + productRespository.getCartProductList().get(i).getProductId(), innerJsonObject);

                productJsonArray.put(productJsonObject);
            }

            innerOutletJsonObject.put("" + outletDetails.getOutletsId(), productJsonArray);

            VendorJsonObject.put("" + outletAndVendorID.get(0), innerOutletJsonObject);

            jsonArray.put(VendorJsonObject);

//            Log.e("json", jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return jsonArray;
    }


}
