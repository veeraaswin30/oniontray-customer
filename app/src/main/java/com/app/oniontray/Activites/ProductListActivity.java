package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.ProductListAdapter;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.CategoryList;
import com.app.oniontray.RequestModels.ProductList;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 21/11/17.
 */

public class ProductListActivity extends LocalizationActivity implements ProductListAdapter.ProductListInterface {
    private RecyclerView recyclerView;
    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog ddProgressBarDialog;
    private ProductListAdapter prodListApater;
    private ProductRespository productRespository;
    private LinearLayout cart_view;
    private TextView menu_item_empty_text, cart_update,titleitem;
    private Button view_cart_items;
    private String vender_id = "";
    private String outlet_id = "";
    private CategoryList categoryList;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dynamic_fragment);
        InitView();

    }

    private void InitView() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            categoryList = (CategoryList) bundle.getSerializable("categoryList");
            vender_id = bundle.getString("vendor_id");
            outlet_id = bundle.getString("outlet_id");
        }
        toolbar = (Toolbar) findViewById(R.id.prod_list_items_toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
//        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));



           String language = String.valueOf(LanguageSetting.getLanguage(ProductListActivity.this));


        if (language.equals("en")) {

//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
//            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
        } else {
//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
//            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);

            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        productRespository = new ProductRespository();
        loginPrefManager = new LoginPrefManager(ProductListActivity.this);
        ddProgressBarDialog = Webdata.getProgressBarDialog(ProductListActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.menuItemListUnderCategory);
        menu_item_empty_text = (TextView) findViewById(R.id.menu_item_empty_text);

        cart_view = (LinearLayout) findViewById(R.id.cart_view);
        cart_update = (TextView) findViewById(R.id.cart_update);

        view_cart_items = (Button) findViewById(R.id.view_cart_btn);
        titleitem = findViewById(R.id.titleitem);
        titleitem.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductListActivity.this));
        recyclerView.addItemDecoration(new ProdListItemOffsetDecor(ProductListActivity.this, R.dimen.prod_list_item_row_line_height));

        getProductListRequest();

        view_cart_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListActivity.this, BaseMenuTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                Intent intent_rece = new Intent("base_activity_receiver");
                intent_rece.putExtra("page_name", "1");
                LocalBroadcastManager.getInstance(ProductListActivity.this).sendBroadcast(intent_rece);

               finish();
            }
        });


    }

    private void getProductListRequest() {
        if (ddProgressBarDialog != null) {
            ddProgressBarDialog.show();
        }

        try {

            Log.e("language", "" + loginPrefManager.getStringValue("Lang_code"));
            Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
            Log.e("token", "" + loginPrefManager.getStringValue("user_token"));
            Log.e("store_id", "" + vender_id);
            Log.e("getCategoryId", "" + categoryList.getCategoryId());
            Log.e("outlet_id", "" + outlet_id);

            APIService product_list = Webdata.getRetrofit().create(APIService.class);
            product_list.get_store_product_list("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("user_token"), "" + vender_id, "" + categoryList.getCategoryId(), "" + outlet_id, "").enqueue(new Callback<ProductList>() {
                @Override
                public void onResponse(Call<ProductList> call, Response<ProductList> response) {

                    try {

                        ddProgressBarDialog.dismiss();

                        Log.e("get_store_product_list", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getProductList_Data().size() > 0) {

//                                product_list_items = (ArrayList<ProductList_Data>) response.body().getResponse().getProductList_Data();

                                prodListApater = new ProductListAdapter(ProductListActivity.this, response.body().getResponse().getProductList_Data(), vender_id, outlet_id);
                                prodListApater.ProdListInterface(ProductListActivity.this);

                                recyclerView.setAdapter(prodListApater);
                                setBadgeCount("" + productRespository.getCartCount());
                            }
                            if (response.body().getResponse().getProductList_Data().size() == 0) {
                                menu_item_empty_text.setVisibility(View.VISIBLE);
                            } else {
                                menu_item_empty_text.setVisibility(View.GONE);
                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(ProductListActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        ddProgressBarDialog.dismiss();
                        Log.e("Exception Error ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ProductList> call, Throwable t) {
                    Log.e("onFailure", "" + t.getMessage());
                    ddProgressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {
            ddProgressBarDialog.dismiss();
            Log.e("product_list Exception ", "" + e.getMessage());
        }

    }

    private void setBadgeCount(String cart_value) {
        if (Integer.parseInt(cart_value) == 0) {
            cart_view.setVisibility(View.GONE);

        } else {
            cart_view.setVisibility(View.GONE);
            String text = String.format(getResources().getString(R.string.cart_count_update), "" + cart_value, loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));            cart_update.setText(Html.fromHtml(text));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setBadgeCount("" + productRespository.getCartCount());
    }


    public void updateProductQuantity(String quantity, int productID, int primaryID) {

        prodListApater.updateProductQuantity(quantity, productID, primaryID);

    }

    @Override
    public void updateProdListandCartCount() {
        setBadgeCount("" + productRespository.getCartCount());
    }




    }



