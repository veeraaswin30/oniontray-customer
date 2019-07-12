package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.Promo_Product_Adapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OffersItemOffsetDecor;
import com.app.oniontray.RequestModels.PromotionList;
import com.app.oniontray.RequestModels.PromotionProduct;
import com.app.oniontray.RequestModels.StoInfoOutletDetails;
import com.app.oniontray.RequestModels.StoProdVendorInfo;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 6/4/17.
 */

public class PromotionsListActivity extends LocalizationActivity implements Promo_Product_Adapter.MyPromoProdAdapterInterface {


    private Toolbar promo_toolbar_id;
    private TextView promo_title_txt;


    private RecyclerView promo_list_recycler_view;
    private PromotionList promotionList;
    private ArrayList<PromotionProduct> promotionProductArrayList;

    private Promo_Product_Adapter promo_product_adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions_list_layout);


        if (getIntent() != null) {
            this.promotionList = (PromotionList) getIntent().getSerializableExtra("promo_list");
            this.promotionProductArrayList = (ArrayList<PromotionProduct>) promotionList.getPromotionProducts();
        }


        promo_toolbar_id = (Toolbar) findViewById(R.id.promo_toolbar_id);
        promo_toolbar_id.setTitle("");
        promo_toolbar_id.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(promo_toolbar_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        promo_toolbar_id.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

           String language = String.valueOf(LanguageSetting.getLanguage(PromotionsListActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_ic_action_bar_en_back_ic);*/
        }


        promo_title_txt = (TextView) findViewById(R.id.promo_title_txt);
        promo_title_txt.setText("" + promotionList.getOutletName());

        promo_list_recycler_view = (RecyclerView) findViewById(R.id.promo_list_recycler_view);
        promo_list_recycler_view.setLayoutManager(new LinearLayoutManager(PromotionsListActivity.this));
        promo_list_recycler_view.addItemDecoration(new OffersItemOffsetDecor(PromotionsListActivity.this, R.dimen.off_list_item_row_line_hight));

        promo_product_adapter = new Promo_Product_Adapter(PromotionsListActivity.this, promotionProductArrayList);
        promo_product_adapter.MyProProdAdapterInterfaceReqMethod(PromotionsListActivity.this);
        promo_list_recycler_view.setAdapter(promo_product_adapter);


    }

    @Override
    public void PromoProdAdapterItemClickEvent(PromotionProduct promotionProduct) {

        Intent out_let = new Intent(PromotionsListActivity.this, ProductMenuItemListWithDynamicTabs.class);
        out_let.putExtra("vender_name", "" + promotionList.getOutletName());
        out_let.putExtra("vender_id", "" + promotionList.getVendorId());
        out_let.putExtra("outlet_id", "" + promotionList.getOutletId());
        out_let.putExtra("backOption", true);
        startActivity(out_let);

//        if (promotionProduct != null) {
//            StoreProdCategoListRequestMethod(promotionProduct);
//        }

    }


    private void StoreProdCategoListRequestMethod(PromotionProduct promotionProduct) {

        if (progressDialog != null) {
            progressDialog.show();
        }

//        Log.e("Lang_code", "" + "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("user_id", "" + "" + loginPrefManager.getStringValue("user_id"));
//        Log.e("user_token", "" + "" + loginPrefManager.getStringValue("user_token"));
//        Log.e("vender_id", "" + promotionList.getVendorId());
//        Log.e("outlet_id", "" + promotionList.getOutletId());

        APIService storeVenderInfo = Webdata.getRetrofit().create(APIService.class);

        storeVenderInfo.get_store_Prod_VenderInfo("" + loginPrefManager.getStringValue("Lang_code"), "" + promotionList.getOutletId(),
                "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(), "" + loginPrefManager.getStringValue("user_id"),
                "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<StoProdVendorInfo>() {
            @Override
            public void onResponse(Call<StoProdVendorInfo> call, Response<StoProdVendorInfo> response) {

                try {

                    progressDialog.dismiss();

//                    Log.e("get_store_Prod_Vend", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        StoreInfoCallMethod(response.body().getResponse().getStoInfoOutletDetails());

                    } else {
                        Toast.makeText(PromotionsListActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    Log.e("Exception Error", "" + e.getMessage());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<StoProdVendorInfo> call, Throwable t) {
//                Log.e("StoreVenderInfo ", "onFailure" + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void StoreInfoCallMethod(StoInfoOutletDetails stoProdVendorInfo) {

        if (stoProdVendorInfo != null) {

//            Bundle args = new Bundle();
//            args.putSerializable("vendor_detail", stoProdVendorInfo);
//            startActivityForResult(new Intent(PromotionsListActivity.this, RestaurantInfoActivity.class).putExtras(args), 100);

            Intent out_let = new Intent(PromotionsListActivity.this, ProductMenuItemListWithDynamicTabs.class);
            out_let.putExtra("vender_name", "" + promotionList.getOutletName());
            out_let.putExtra("vender_id", "" + promotionList.getVendorId());
            out_let.putExtra("outlet_id", "" + promotionList.getOutletId());
            out_let.putExtra("backOption", true);
            startActivity(out_let);

        }

    }


}
