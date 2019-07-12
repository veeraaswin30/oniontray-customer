package com.app.oniontray.Activites;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.oniontray.Adapters.FilterQuickCuisionAdapters;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CuisineList;
import com.app.oniontray.RequestModels.FilterCategoryList;
import com.app.oniontray.RequestModels.FilterSettings;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StoreFilterActivity extends LocalizationActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    Toolbar toolbar;

    private static final int FILTER_ID = 1;

    private RecyclerView filter_by_category_recycler_view, cuisines_filter_recycler_view;

    private StaggeredGridLayoutManager quickstaggeredGridLayoutManager, cuisionstaggeredGridLayoutManager;
    private FilterQuickCuisionAdapters quickfilterQuickCuisionAdapters, cuisionfilterQuickCuisionAdapters;

    private LinearLayout showRestaurantClick;
    private RadioButton store_filter_recommended_radio_btn, store_filter_rating_radio_btn, store_filter_deli_time_radio_btn, store_filter_deli_prize_radio_btn;
    private RadioGroup store_filter_rat_deli_time_radio_group;
    private String relvence_value, rat_time_value, rat_pay_value;

   private ColorStateList colorStateList;
   private TextView store_filter_done_txt_view_txt;




    private RadioGroup store_filter_pay_method_radio_group;

    private RadioButton store_filter_pay_meth_all_radio_btn;
    private RadioButton store_filter_pay_meth_cash_radio_btn;
    private RadioButton store_filter_pay_meth_card_radio_btn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_filter_layout);

        toolbar = (Toolbar) findViewById(R.id.store_filter_tool_bar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(toolbar);

        TextView filter_title=findViewById(R.id.filter_title);
        filter_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

           String language = String.valueOf(LanguageSetting.getLanguage(StoreFilterActivity.this));


        if (language.equals("en")) {
          //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
          //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        store_filter_done_txt_view_txt = findViewById(R.id.store_filter_done_txt_view_txt);

        colorStateList = radioButtonColor();

        store_filter_recommended_radio_btn = (RadioButton) findViewById(R.id.store_filter_recommended_radio_btn);


        store_filter_rat_deli_time_radio_group = (RadioGroup) findViewById(R.id.store_filter_rat_deli_time_radio_group);

        store_filter_rating_radio_btn = (RadioButton) findViewById(R.id.store_filter_rating_radio_btn);
        store_filter_deli_time_radio_btn = (RadioButton) findViewById(R.id.store_filter_deli_time_radio_btn);
        store_filter_deli_prize_radio_btn = (RadioButton) findViewById(R.id.store_filter_deli_prize_radio_btn);







        store_filter_pay_method_radio_group = (RadioGroup) findViewById(R.id.store_filter_pay_method_radio_group);


        store_filter_pay_meth_all_radio_btn = (RadioButton) findViewById(R.id.store_filter_pay_meth_all_radio_btn);

        store_filter_pay_meth_cash_radio_btn = (RadioButton) findViewById(R.id.store_filter_pay_meth_cash_radio_btn);

        store_filter_pay_meth_card_radio_btn = (RadioButton) findViewById(R.id.store_filter_pay_meth_card_radio_btn);

        //ColorStateList colorStateList1 = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////            store_filter_pay_meth_all_radio_btn.setCompoundDrawableTintList(colorStateList);
////            store_filter_pay_meth_cash_radio_btn.setCompoundDrawableTintList(colorStateList);
////            store_filter_pay_meth_card_radio_btn.setCompoundDrawableTintList(colorStateList);
////            store_filter_recommended_radio_btn.setCompoundDrawableTintList(colorStateList);
//
//
//            store_filter_pay_meth_all_radio_btn.setTextColor(colorStateList);
//            store_filter_pay_meth_cash_radio_btn.setTextColor(colorStateList);
//            store_filter_pay_meth_card_radio_btn.setTextColor(colorStateList);
//            store_filter_recommended_radio_btn.setTextColor(colorStateList);
//
//
////            store_filter_rating_radio_btn.setCompoundDrawableTintList(colorStateList);
////            store_filter_deli_time_radio_btn.setCompoundDrawableTintList(colorStateList);
////            store_filter_deli_prize_radio_btn.setCompoundDrawableTintList(colorStateList);
//
//            store_filter_rating_radio_btn.setTextColor(colorStateList);
//            store_filter_deli_time_radio_btn.setTextColor(colorStateList);
//            store_filter_deli_prize_radio_btn.setTextColor(colorStateList);
//
//            if(store_filter_deli_prize_radio_btn.isChecked()){
//                store_filter_deli_prize_radio_btn.setButtonTintList(colorStateList);
//            }
//
//
//            if(store_filter_rating_radio_btn.isChecked()){
//                store_filter_rating_radio_btn.setButtonDrawable(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_ratting_select));
//            }
//
//            if(store_filter_deli_time_radio_btn.isChecked()){
//                store_filter_deli_time_radio_btn.setButtonDrawable(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_time_select));
//            }
//
//            if(store_filter_deli_prize_radio_btn.isChecked()){
//                store_filter_deli_prize_radio_btn.setBackground(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_min_order_select));
//            }
//
//            if(store_filter_deli_prize_radio_btn.isChecked()){
//                store_filter_pay_meth_all_radio_btn.setBackground(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_filter_pay_meth_all_select_ic));
//            }
//            if(store_filter_pay_meth_cash_radio_btn.isChecked()){
//                store_filter_pay_meth_cash_radio_btn.setBackground(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_filter_pay_meth_cash_select_ic));
//            }
//            if(store_filter_pay_meth_card_radio_btn.isChecked()){
//                store_filter_pay_meth_card_radio_btn.setBackground(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_filter_pay_meth_cart_select_ic));
//            }
//            if(store_filter_recommended_radio_btn.isChecked()){
//                store_filter_recommended_radio_btn.setBackground(loginPrefManager.changeColor(StoreFilterActivity.this,
//                        loginPrefManager.getThemeFontColor(),R.drawable.ic_recommented));
//            }








       // }



        showRestaurantClick = (LinearLayout) findViewById(R.id.show_restaurant);
        showRestaurantClick.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        store_filter_done_txt_view_txt.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        filterSettingsData();

        filter_by_category_recycler_view = (RecyclerView) findViewById(R.id.filter_by_category_recycler_view);
        filter_by_category_recycler_view.setHasFixedSize(true);
        quickstaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        quickstaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        filter_by_category_recycler_view.setLayoutManager(quickstaggeredGridLayoutManager);


        cuisines_filter_recycler_view = (RecyclerView) findViewById(R.id.cuisines_filter_recycler_view);
        cuisines_filter_recycler_view.setHasFixedSize(true);
        cuisionstaggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        cuisionstaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        cuisines_filter_recycler_view.setLayoutManager(cuisionstaggeredGridLayoutManager);


        store_filter_rat_deli_time_radio_group.setOnCheckedChangeListener(this);

        showRestaurantClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRestaurantClickMethod();
            }
        });


    }

    private void showRestaurantClickMethod() {

        if (store_filter_recommended_radio_btn.isChecked()) {
            relvence_value = "desc";
        } else {
            relvence_value = "asc";
        }


        if (store_filter_rating_radio_btn.isChecked()) {
            rat_time_value = "rating";
        }

        if (store_filter_deli_time_radio_btn.isChecked()) {
            rat_time_value = "delivery_time";
        }

        if (store_filter_deli_prize_radio_btn.isChecked()) {
            rat_time_value = "minimum_order_amount";
        }


        if (store_filter_pay_meth_all_radio_btn.isChecked()) {

            rat_pay_value = "";
        }

        if (store_filter_pay_meth_cash_radio_btn.isChecked()) {

            rat_pay_value = "1";
        }

        if (store_filter_pay_meth_card_radio_btn.isChecked()) {
            rat_pay_value = "2";
        }


        HashMap<String, CuisineList> filterCuisineList = cuisionfilterQuickCuisionAdapters.cusineList();
        HashMap<String, FilterCategoryList> filterCategoryList = quickfilterQuickCuisionAdapters.categoryList();

        StringBuilder filterCuisineID = new StringBuilder();
        StringBuilder filterCategoryID = new StringBuilder();

        if (filterCuisineList.size() != 0) {
            Iterator iterator = filterCuisineList.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = "" + filterCuisineList.get(key).getId();
                filterCuisineID.append(value);
                if (iterator.hasNext())
                    filterCuisineID.append(",");

            }
//            Log.e("cuisineId", filterCuisineID.toString());
        }

//        Log.e("relevance", relvence_value);
        if (rat_time_value != null){

        }
//            Log.e("ratvalue", ",,,,,,,," + rat_time_value);


        Intent filterData = new Intent("filter_data");


        if (rat_time_value != null) {
            filterData.putExtra("sortBy", rat_time_value);
        } else {
            filterData.putExtra("sortBy", "");
        }


        filterData.putExtra("orderBy", relvence_value);


        if (rat_pay_value != null) {
            filterData.putExtra("pay_method", rat_pay_value);
        } else {
            filterData.putExtra("pay_method", "");
        }


        if (filterCuisineList.size() != 0) {
            filterData.putExtra("cuisineId", filterCuisineID.toString());
        } else {
            filterData.putExtra("cuisineId", "");
        }

        if (filterCategoryList.size() != 0)
        {
            Iterator iterator = filterCategoryList.keySet().iterator();
            while (iterator.hasNext())
            {
                String key = (String) iterator.next();
                filterData.putExtra(filterCategoryList.get(key).getUrlKey(), "" + filterCategoryList.get(key).getId());
            }

        }


        LocalBroadcastManager.getInstance(StoreFilterActivity.this).sendBroadcast(filterData);

        finish();

    }

    private void filterSettingsData() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.filter_settings_api(loginPrefManager.getStringValue("user_id"),
                loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<FilterSettings>() {
            @Override
            public void onResponse(Call<FilterSettings> call, Response<FilterSettings> response) {
                progressDialog.dismiss();

                try {
                    if (response.body().getResponse() != null) {
                        if (response.body().getResponse().getHttpCode() == 200) {
                            cuisionfilterQuickCuisionAdapters = new FilterQuickCuisionAdapters(StoreFilterActivity.this, response.body().getResponse().getCuisineList(), "1");
                            cuisines_filter_recycler_view.setAdapter(cuisionfilterQuickCuisionAdapters);

                            ArrayList<FilterCategoryList> filterCategoryListArrayList = new ArrayList<FilterCategoryList>();

                            FilterCategoryList filterCategoryListForOffers = new FilterCategoryList();
                            filterCategoryListForOffers.setId(1);
                            filterCategoryListForOffers.setName(getString(R.string.filter_offer));
                            filterCategoryListForOffers.setUrlKey(getString(R.string.filter_key_offer));

                            FilterCategoryList filterCategoryList = new FilterCategoryList();
                            filterCategoryList.setId(1);
                            filterCategoryList.setName(getString(R.string.filter_open_restaurant));
                            filterCategoryList.setUrlKey(getString(R.string.filter_key_open_restaurant));

                            filterCategoryListArrayList.add(filterCategoryListForOffers);
                            filterCategoryListArrayList.add(filterCategoryList);

                            quickfilterQuickCuisionAdapters = new FilterQuickCuisionAdapters(StoreFilterActivity.this, filterCategoryListArrayList);
                            filter_by_category_recycler_view.setAdapter(quickfilterQuickCuisionAdapters);

                        } else {

                        }
                    }
                } catch (Exception e) {
//                    Log.e("exception error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<FilterSettings> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        TextView tv = new TextView(this);
        tv.setText(getString(R.string.filter_menu_clear_all_txt) + "  ");
        tv.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        tv.setOnClickListener(this);
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(16);
        menu.add(0, FILTER_ID, 1, "" + getString(R.string.filter_menu_clear_all_txt)).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public void onClick(View v) {
        clearAllFilterData();
    }

    private void clearAllFilterData() {

        quickfilterQuickCuisionAdapters.clearHashMapCategory();

        cuisionfilterQuickCuisionAdapters.clearHashMapCuisine();


        store_filter_rat_deli_time_radio_group.clearCheck();

        store_filter_pay_method_radio_group.clearCheck();


        store_filter_recommended_radio_btn.setChecked(true);

        store_filter_pay_meth_all_radio_btn.setChecked(false);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.store_filter_rating_radio_btn) {
            rat_time_value = "rating";
        } else if (checkedId == R.id.store_filter_deli_time_radio_btn) {
            rat_time_value = "delivery_time";
        } else if (checkedId == R.id.store_filter_recommended_radio_btn) {
            relvence_value = "desc";
        } else {
            rat_time_value = "minimum_order_amount";
        }
    }

    public ColorStateList radioButtonColor(){
        ColorStateList   colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[] {

                        Color.parseColor(loginPrefManager.getThemeFontColor()) //disabled
                        ,Color.parseColor(loginPrefManager.getThemeColor()) //enabled

                }
        );

        return colorStateList;
    }

}
