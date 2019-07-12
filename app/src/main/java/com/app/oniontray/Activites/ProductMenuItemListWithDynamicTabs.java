package com.app.oniontray.Activites;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.ProdMenuCategItemListAdapter;
import com.app.oniontray.Fragments.DynamicTabFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.AddFavourites;
import com.app.oniontray.RequestModels.CategoryList;
import com.app.oniontray.RequestModels.StoInfoOutletDetails;
import com.app.oniontray.RequestModels.StoProdVendorInfo;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductMenuItemListWithDynamicTabs extends LocalizationActivity implements ProdMenuCategItemListAdapter.PrdMenuCategItemInterface {

    private Toolbar toolbar;

    private Menu mOptionsMenu;

    //    private ViewPager viewPager;
//    private TabLayout tabLayout;
    private LayerDrawable icon;


    private RecyclerView prod_menu_categ_list_recy_view;
    private ProdMenuCategItemListAdapter prodMenuCategItemListAdapter;


    private String vender_name = "";
    private String vender_id = "";
    private String outlet_id = "";

    private StoInfoOutletDetails vendorDetail;

    private TextView no_categories_found, restaurant_name, restaurant_open_status, restaurant_rating,
            restaurant_minimum_order_amout, restaurant_delivery_mins, restaurant_delivery_type,
            minimum_order_amount, delivery_time_text;

    private TextView delivery_text, preparation_time_text;
    private TextView title;

    private RatingBar restaurant_ratingBar;
    private ImageView restaurant_image;

    public static Activity productActivity;

    private BroadcastReceiver productBroadcastReceiver;
    ViewPagerAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_list_with_dynamic_tabs);

        productActivity = this;

        toolbar = (Toolbar) findViewById(R.id.prod_menu_items_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(ProductMenuItemListWithDynamicTabs.this));


        if (language.equals("en")) {
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vender_name = bundle.getString("vender_name");
            vender_id = bundle.getString("vender_id");
            outlet_id = bundle.getString("outlet_id");
        }


        initializeView();


        productBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent != null) {

//                    Log.e("dta", intent.getStringExtra("quantity") + "" + intent.getStringExtra("productID"));

//                    DynamicTabFragment fragment = adapter.getItem(viewPager.getCurrentItem());
//                    fragment.updateProductQuantity(intent.getStringExtra("quantity"), Integer.parseInt(intent.getStringExtra("productID")), Integer.parseInt(intent.getStringExtra("primaryID")));

                }

            }
        };

        LocalBroadcastManager.getInstance(ProductMenuItemListWithDynamicTabs.this).registerReceiver(productBroadcastReceiver, new IntentFilter("quantity_update"));

    }


    private void initializeView() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().hasExtra("backOption")) {
                    finish();
                } else {
                    Intent intent = new Intent(ProductMenuItemListWithDynamicTabs.this, BaseMenuTabActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                }


            }
        });

        restaurant_image = (ImageView) findViewById(R.id.restaurant_image);

        restaurant_name = (TextView) findViewById(R.id.restaurant_name);
        preparation_time_text = (TextView) findViewById(R.id.preparation_time_text);

//        restaurant_name.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));text_color
        restaurant_name.setTextColor(getResources().getColor(R.color.text_color));
        restaurant_open_status = (TextView) findViewById(R.id.restaurant_open_status);

        restaurant_ratingBar = (RatingBar) findViewById(R.id.restaurant_ratingBar);
        restaurant_rating = (TextView) findViewById(R.id.restaurant_rating);
        restaurant_rating.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        restaurant_rating.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        no_categories_found = (TextView) findViewById(R.id.no_categories_found);

        restaurant_minimum_order_amout = (TextView) findViewById(R.id.restaurant_minimum_order_amout);
        restaurant_minimum_order_amout.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        restaurant_delivery_mins = (TextView) findViewById(R.id.restaurant_delivery_mins);
        restaurant_delivery_type = (TextView) findViewById(R.id.restaurant_delivery_type);

        minimum_order_amount = (TextView) findViewById(R.id.minimun_order_text);
        delivery_time_text = (TextView) findViewById(R.id.delivery_time_text);
        delivery_text = (TextView) findViewById(R.id.delivery_text);

        title = findViewById(R.id.title);
        title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        minimum_order_amount.setVisibility(View.VISIBLE);
        delivery_time_text.setVisibility(View.VISIBLE);
        delivery_text.setVisibility(View.VISIBLE);



//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        tabLayout = (TabLayout) findViewById(R.id.prod_menu_items_tabs_layout);
//        tabLayout.setupWithViewPager(viewPager);

        prod_menu_categ_list_recy_view = (RecyclerView) findViewById(R.id.prod_menu_categ_list_recy_view);
        prod_menu_categ_list_recy_view.setLayoutManager(new LinearLayoutManager(ProductMenuItemListWithDynamicTabs.this));
        prod_menu_categ_list_recy_view.addItemDecoration(new ProdListItemOffsetDecor(ProductMenuItemListWithDynamicTabs.this, R.dimen.prod_list_item_row_line_height));


        StoreProdCategoListRequestMethod();

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void StoreProdCategoListRequestMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        Log.e("Lang_code", "" + "" + loginPrefManager.getStringValue("Lang_code"));
        Log.e("user_id", "" + "" + loginPrefManager.getStringValue("user_id"));
        Log.e("user_token", "" + "" + loginPrefManager.getStringValue("user_token"));
        Log.e("getCityID", "" + loginPrefManager.getCityID());
        Log.e("getLocID", "" + loginPrefManager.getLocID());
        Log.e("vender_id", "" + vender_id);
        Log.e("outlet_id", "" + outlet_id);

        APIService storeVenderInfo = Webdata.getRetrofit().create(APIService.class);

        storeVenderInfo.get_store_Prod_VenderInfo("" + loginPrefManager.getStringValue("Lang_code"), "" + outlet_id,
                "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(),
                "" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<StoProdVendorInfo>() {
            @Override
            public void onResponse(Call<StoProdVendorInfo> call, Response<StoProdVendorInfo> response) {

//                try {

                    progressDialog.dismiss();

                    Log.e("get_store_Prod_Vend", "" + response.raw().request());
                    Log.e("get_store_Prod_Vend", new Gson().toJson(response.raw().body()));


                    if (response.body().getResponse().getHttpCode() == 200) {

                        setupRestaurantDetail(response.body().getResponse().getStoInfoOutletDetails());

                        vendorDetail = response.body().getResponse().getStoInfoOutletDetails();

                        final Drawable info = getResources().getDrawable(R.drawable.ic_menu_info);
                        info.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                        mOptionsMenu.findItem(R.id.action_info).setIcon(info);



                        if (response.body().getResponse().getStoInfoOutletDetails().getUserFavourite() == 1) {
                            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_my_fav_select_ic);
                            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                            mOptionsMenu.findItem(R.id.action_favorites).setIcon(upArrow);
                        } else if (response.body().getResponse().getStoInfoOutletDetails().getUserFavourite() == 0) {
                            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_favorite);
                            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                            mOptionsMenu.findItem(R.id.action_favorites).setIcon(upArrow);
                        }else{
                            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_favorite);
                            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                            mOptionsMenu.findItem(R.id.action_favorites).setIcon(upArrow);

                        }


//                        setupViewPager(viewPager, (ArrayList<CategoryList>) response.body().getResponse().getStoInfoOutletDetails().getCategoryList(), response.body().getResponse().getStoInfoOutletDetails());
                        UpdateCategListRecyView((ArrayList<CategoryList>) response.body().getResponse().getStoInfoOutletDetails().getCategoryList());


                    } else {

                        no_categories_found.setVisibility(View.VISIBLE);
                        no_categories_found.setText(response.body().getResponse().getMessage());
                        Toast.makeText(ProductMenuItemListWithDynamicTabs.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

//                } catch (Exception e) {
//                    Log.e("Exception Error", "" + e.getMessage());
//                    progressDialog.dismiss();
//                }
            }

            @Override
            public void onFailure(Call<StoProdVendorInfo> call, Throwable t) {
                Log.e("StoreVenderInfo ", "onFailure" + t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void setupRestaurantDetail(StoInfoOutletDetails vendorDetail) {

        Glide.with(ProductMenuItemListWithDynamicTabs.this).load(vendorDetail.getFeaturedImage()).bitmapTransform(new CenterCrop(ProductMenuItemListWithDynamicTabs.this), new RoundedCornersTransformation(ProductMenuItemListWithDynamicTabs.this, 5, 0)).into(restaurant_image);

        restaurant_name.setText(vendorDetail.getOutletName());

        if (vendorDetail.getDeliveryType() == 1) {
            restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
        } else if (vendorDetail.getDeliveryType() == 2) {

            if (String.valueOf(Float.valueOf(vendorDetail.getDeliveryCostFixed())).equals("0.0")) {
                restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
            } else {
                restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getDeliveryCostFixed()))));
            }

        } else if (vendorDetail.getDeliveryType() == 3) {

            if (String.valueOf(Float.valueOf(vendorDetail.getDeliveryCostFixed())).equals("0.0")) {
                restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
            } else {
                restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getDeliveryCostFixed()))));
            }

        }

        if (vendorDetail.getOpenRestaurant() == 0) {

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            restaurant_image.setColorFilter(filter);

            restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getMinimumOrderAmount()))));

            restaurant_open_status.setText("" + getString(R.string.home_store_status_close_txt));
            restaurant_open_status.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

            restaurant_name.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

            restaurant_minimum_order_amout.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));
//            restaurant_delivery_mins.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

            restaurant_delivery_type.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

            if (vendorDetail.getAverageRating() != null && !vendorDetail.getAverageRating().equals("")) {
                if (!vendorDetail.getAverageRating().equals("")) {
                    restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(vendorDetail.getAverageRating()));
                    //restaurant_rating.setBackgroundResource(Color.parseColor(loginPrefManager.getThemeColor()));
                    restaurant_ratingBar.setRating(Float.parseFloat("" + vendorDetail.getAverageRating()));
                }
            } else {
                restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(0));
            }

            if (vendorDetail.getDeliveryTime().isEmpty()) {
                restaurant_delivery_mins.setText("-");
            } else {
               // restaurant_delivery_mins.setTextpublic void setIcon(Drawable icon) {

                restaurant_delivery_mins.setText(Html.fromHtml(loginPrefManager.getdeltimeWithDynamicColor(loginPrefManager.getThemeFontColor(),String.format(getString(R.string.close_mins), vendorDetail.getDeliveryTime()))));

            }


            if (vendorDetail.getPreparationTime().isEmpty()) {
                preparation_time_text.setText("-");
            } else {
                // restaurant_delivery_mins.setTextpublic void setIcon(Drawable icon) {

                preparation_time_text.setText(Html.fromHtml(loginPrefManager.getdeltimeWithDynamicColor(loginPrefManager.getThemeFontColor(),String.format(getString(R.string.close_mins), vendorDetail.getPreparationTime()))));

            }

        } else {

            //restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getMinimumOrderAmount()))));
            restaurant_minimum_order_amout.setText(Html.fromHtml(loginPrefManager.getCurrecncyWithDynamicColor(loginPrefManager.getThemeFontColor(),loginPrefManager.GetEngDecimalFormatValues( Float.valueOf(vendorDetail.getMinimumOrderAmount())))));


            restaurant_open_status.setText("" + getString(R.string.home_store_status_open_txt));
            restaurant_open_status.setTextColor(Color.parseColor("" + getString(R.string.open_store_status_text_view_color)));

            if (vendorDetail.getAverageRating() != null && !vendorDetail.getAverageRating().equals("")) {
                if (!vendorDetail.getAverageRating().equals("")) {
                    restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(vendorDetail.getAverageRating()));
                   // restaurant_rating.setBackgroundResource(Color.parseColor(loginPrefManager.getThemeColor()));
                    restaurant_ratingBar.setRating(Float.parseFloat("" + vendorDetail.getAverageRating()));
                }
            } else {
                restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(0));
            }

            if (vendorDetail.getDeliveryTime().isEmpty()) {
                restaurant_delivery_mins.setText("-");
            } else {
                //restaurant_delivery_mins.setText(Html.fromHtml(String.format(getString(R.string.mins), vendorDetail.getDeliveryTime())));
                restaurant_delivery_mins.setText(Html.fromHtml(loginPrefManager.getdeltimeWithDynamicColor(loginPrefManager.getThemeFontColor(),String.format(getString(R.string.mins), vendorDetail.getDeliveryTime()))));

            }

            if (vendorDetail.getPreparationTime().isEmpty()) {
                preparation_time_text.setText("-");
            } else {
                //restaurant_delivery_mins.setText(Html.fromHtml(String.format(getString(R.string.mins), vendorDetail.getDeliveryTime())));
                preparation_time_text.setText(Html.fromHtml(loginPrefManager.getdeltimeWithDynamicColor(loginPrefManager.getThemeFontColor(),String.format(getString(R.string.mins), vendorDetail.getPreparationTime()))));

            }

        }

    }


    private void UpdateCategListRecyView(ArrayList<CategoryList> categoryLists) {

        try {

            prodMenuCategItemListAdapter = new ProdMenuCategItemListAdapter(ProductMenuItemListWithDynamicTabs.this, categoryLists);
            prodMenuCategItemListAdapter.callPrdMenuCategItemInterface(ProductMenuItemListWithDynamicTabs.this);
            prod_menu_categ_list_recy_view.setAdapter(prodMenuCategItemListAdapter);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    /*Product Menu Categ Item List Adapter Interface*/

    @Override
    public void ProdMenuCategItemClickInterface(CategoryList categoryList) {


        Intent out_let = new Intent(ProductMenuItemListWithDynamicTabs.this, ProductListActivity.class);
        out_let.putExtra("categoryList", categoryList);
        out_let.putExtra("vendor_id", "" + vender_id);
        out_let.putExtra("outlet_id", "" + outlet_id);
        startActivity(out_let);
    }


    private void setupViewPager(ViewPager viewPager, ArrayList<CategoryList> categoryList, StoInfoOutletDetails vendorDetail) {

        if (categoryList.size() != 0) {
            no_categories_found.setVisibility(View.GONE);
            adapter = new ViewPagerAdapter(getSupportFragmentManager());

            for (int i = 0; i < categoryList.size(); i++) {

                DynamicTabFragment fragment = new DynamicTabFragment();
                Bundle args = new Bundle();
                args.putSerializable("categoryList", categoryList.get(i));
                args.putString("vendor_id", "" + vendorDetail.getVendorsId());
                args.putString("outlet_id", "" + vendorDetail.getOutletsId());

                fragment.setArguments(args);

                adapter.addFrag(fragment, categoryList.get(i));
            }

            viewPager.setAdapter(adapter);
        } else {
            no_categories_found.setVisibility(View.VISIBLE);
        }

//        adapter.getItem(viewPager.getCurrentItem()).updateProdListandCartCount();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_option_menu, menu);
        mOptionsMenu = menu;
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                if (vendorDetail != null) {
                    Bundle args = new Bundle();
                    args.putSerializable("vendor_detail", vendorDetail);
                    startActivityForResult(new Intent(ProductMenuItemListWithDynamicTabs.this, RestaurantInfoActivity.class).putExtras(args), 100);
                }
                break;
            case R.id.action_favorites:
                if (vendorDetail != null) {
                    FavouritesCallMethod();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void FavouritesCallMethod() {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            StoreAddtoFavoriteRequest();
        } else {
            LoginConfirmationDialog();
        }
    }


    private void StoreAddtoFavoriteRequest() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

          Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
          Log.e("vender_id", "" + vender_id);
           Log.e("outlet_id", "" + outlet_id);
           Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));
           Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));

            APIService storeList = Webdata.getRetrofit().create(APIService.class);
            storeList.addto_favourite("" + loginPrefManager.getStringValue("user_id"), "" + vender_id, "" + outlet_id,
                    "" + loginPrefManager.getStringValue("user_token"),
                    "" + loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<AddFavourites>() {
                @Override
                public void onResponse(Call<AddFavourites> call, Response<AddFavourites> response) {

                    progressDialog.dismiss();

                    if (response.body().getResponse().getStatus() == 1) {

                        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_my_fav_select_ic);
                        upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                        mOptionsMenu.findItem(R.id.action_favorites).setIcon(upArrow);

                    } else if (response.body().getResponse().getStatus() == 0) {
                        //mOptionsMenu.findItem(R.id.action_favorites).setIcon(R.drawable.ic_menu_favorite);
                    final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu_favorite);
                    upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
                    mOptionsMenu.findItem(R.id.action_favorites).setIcon(upArrow);
//                    sto_prod_banner_fav_img.setImageResource(R.drawable.favorite_empty);
                    }

                    showToast(response.body().getResponse().getMessage());

                }

                @Override
                public void onFailure(Call<AddFavourites> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("StoreVenderInfo ", "onFailure" + t.getMessage());
                }

            });

        } catch (Exception e) {
//            Log.e("Exception ", "onFailure" + e.getMessage());
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        Log.e("requestCode", "-" + requestCode);
//        Log.e("resultCode", "" + resultCode);

        if (requestCode == 100) {

            if (data != null) {

                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    vender_name = bundle.getString("vender_name");
                    vender_id = bundle.getString("vender_id");
                    outlet_id = bundle.getString("outlet_id");
                }

                StoreProdCategoListRequestMethod();

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void showToast(String message) {
        Toast.makeText(ProductMenuItemListWithDynamicTabs.this, message, Toast.LENGTH_SHORT).show();
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<DynamicTabFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public DynamicTabFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(DynamicTabFragment fragment, CategoryList categoryList) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(categoryList.getCategoryName());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(ProductMenuItemListWithDynamicTabs.this).unregisterReceiver(productBroadcastReceiver);

    }


}

