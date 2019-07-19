package com.app.oniontray.Activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.HomeParallaxStoreListAdapter;
import com.app.oniontray.BannersView.MenuStoresBannerView;
import com.app.oniontray.CustomViews.LoginPostReviewDialog;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.HomeMenuStoresItemOffsetDecor;
import com.app.oniontray.RequestModels.CheckReviewReq;
import com.app.oniontray.RequestModels.StoreList;
import com.app.oniontray.RequestModels.StoreList_Data;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEXTBRAIN on 2/15/2017.
 */

public class MenuRestaurantsActivity extends LocalizationActivity implements SearchView.OnQueryTextListener,
        MenuStoresBannerView.HomeMenuBannerInterface {

    private Toolbar toolbar;

    private TextView menu_rest_title_txt,home_banner_search_txt_view;

    private SearchView restaurent_list_search_view;

    private RecyclerView restaurent_list_recycler_view;
    private MenuStoresBannerView menuStoresBannerView;

    private HomeParallaxStoreListAdapter<StoreList_Data> datumHomeParallaxStoreListAdapter;
    private int array_list_count = 0;
    private ArrayList<StoreList_Data> storeListArrayList = new ArrayList<>();
    private BroadcastReceiver Filter_broadcastReceiver,baseMenuBroadcastReceiver;
    SwipeRefreshLayout swipe_view;


    private TextView restaurant_empty_txt_view;

    String openRestaurant = "", openOffer = "";

    public static String review_status = "";

//    private DecimalFormat decimal_format = new DecimalFormat("0.00");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restaurent_layout);

        toolbar = (Toolbar) findViewById(R.id.menu_restaurant_toolbar);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_loc_ic);
        upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_loc_ic);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity = new Intent(MenuRestaurantsActivity.this, PickMyLocationActivity.class);
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(signInActivity);
            }
        });

        home_banner_search_txt_view = (TextView) findViewById(R.id.home_banner_search_txt_view);
        home_banner_search_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickSearchViewAction();
            }
        });
        menu_rest_title_txt = (TextView) findViewById(R.id.menu_rest_title_txt);
        menu_rest_title_txt.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        Drawable drawable = ContextCompat.getDrawable(MenuRestaurantsActivity.this,
                R.drawable.ic_home_down_arrow);
        drawable.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
        menu_rest_title_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);


        menu_rest_title_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInActivity = new Intent(MenuRestaurantsActivity.this, PickMyLocationActivity.class);
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(signInActivity);

            }
        });

        intial();

        if (datumHomeParallaxStoreListAdapter != null) {
            datumHomeParallaxStoreListAdapter = null;

            HomePageStoreList("", "asc", "", "");
        } else {
            HomePageStoreList("", "asc", "", "");
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        setLocationaddress();


    }

    @Override
    public void onBackPressed() {
        BaseBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_restararunt_menu, menu);


        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_menu_filter_ic);
        upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
        menu.findItem(R.id.home_filter_ic).setIcon(upArrow);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home_filter_ic:
                Intent filter_intent = new Intent(MenuRestaurantsActivity.this, StoreFilterActivity.class);
//                filter_intent.putExtra("loc_id",""+location_id);
//                filter_intent.putExtra("city_id",""+city_id);
//                filter_intent.putExtra("catg_id",""+category_id);
//                filter_intent.putExtra("sto_img_path",""+sto_image_path);
                startActivity(filter_intent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void intial() {

//

        restaurent_list_search_view = (SearchView) findViewById(R.id.restaurent_list_search_view);

        restaurant_empty_txt_view = (TextView) findViewById(R.id.restaurant_empty_txt_view);

        restaurent_list_recycler_view = (RecyclerView) findViewById(R.id.restaurent_list_recycler_view);
        restaurent_list_recycler_view.setLayoutManager(new LinearLayoutManager(MenuRestaurantsActivity.this));
        restaurent_list_recycler_view.addItemDecoration(new HomeMenuStoresItemOffsetDecor(MenuRestaurantsActivity.this, R.dimen.home_store_list_item_devider_height));


        swipe_view = findViewById(R.id.swipe_refresh);

        swipe_view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipe_view.setRefreshing(false);
                if (datumHomeParallaxStoreListAdapter != null) {
                    datumHomeParallaxStoreListAdapter = null;

                    HomePageStoreList("", "asc", "", "");
                } else {
                    HomePageStoreList("", "asc", "", "");
                }

            }
        });

        baseMenuBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    if (intent.getStringExtra("page_name").equals("0")) {
                        if (datumHomeParallaxStoreListAdapter != null) {
                            datumHomeParallaxStoreListAdapter = null;

                            HomePageStoreList("", "asc", "", "");
                        } else {
                            HomePageStoreList("", "asc", "", "");
                        }

                    }
                }
            }
        };
        LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).registerReceiver(baseMenuBroadcastReceiver, new IntentFilter("base_activity_receiver"));



        Filter_broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.hasExtra(getString(R.string.filter_key_open_restaurant))) {
                    openRestaurant = intent.getStringExtra(getString(R.string.filter_key_open_restaurant));
                }

                if (intent.hasExtra(getString(R.string.filter_key_offer))) {
                    openOffer = intent.getStringExtra(getString(R.string.filter_key_offer));
                }

                HomePageStoreList(intent.getStringExtra("sortBy"),
                        intent.getStringExtra("orderBy"), "", intent.getStringExtra("cuisineId"));

            }
        };
        LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).registerReceiver(Filter_broadcastReceiver, new IntentFilter("filter_data"));


        SearchView searchView = (SearchView) findViewById(R.id.restaurent_list_search_view);

        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.DKGRAY);
        }

        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        searchEditText.setTextColor(getResources().getColor(R.color.disable_txt_color));
        searchEditText.setHint(getString(R.string.store_list_search_hint));
        searchEditText.setHintTextColor(getResources().getColor(R.color.disable_txt_color));
        searchView.setIconifiedByDefault(false);

        searchView.setQueryHint(getString(R.string.store_list_search_hint));
        searchEditText.setTextColor(getResources().getColor(R.color.disable_txt_color));
        searchEditText.setTextSize(15);

        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchEditText, 0);
        } catch (Exception e) {
//            Log.e("Exception Cursor", "" + e.getMessage());
        }

        searchView.setOnQueryTextListener(MenuRestaurantsActivity.this);


    }


    private void HomePageStoreList(String sortBy, String orderBy, String pay_method, String cuisineID) {

        if (!isFinishing()) {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }

        Log.e("location", "" + loginPrefManager.getLocID());
        Log.e("city", "" + loginPrefManager.getCityID());
        Log.e("language", "" + loginPrefManager.getStringValue("Lang_code"));
        Log.e("sortBy", ".........." + sortBy);
        Log.e("orderBy", ".........." + orderBy);
        Log.e("cuisineID", ".........." + cuisineID);
        Log.e("pay_method", ".........." + pay_method);

        final APIService storeList = Webdata.getRetrofit().create(APIService.class);
        storeList.get_home_page_storeList("" + loginPrefManager.getLocID(), "" + loginPrefManager.getCityID(),
                "" + loginPrefManager.getStringValue("Lang_code"), openRestaurant, openOffer,
                sortBy, orderBy, pay_method, cuisineID).enqueue(new Callback<StoreList>() {
            @Override
            public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                Log.e("input", new Gson().toJson(response.raw().request()));

                try {

                    progressDialog.dismiss();

                    Log.e("get_home_page_storeList", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        storeListArrayList = new ArrayList<StoreList_Data>();
                        storeListArrayList.addAll(response.body().getResponse().getOpenStoreList());
                        storeListArrayList.addAll(response.body().getResponse().getStoreList());
                        array_list_count = storeListArrayList.size();

//                        Log.e("count", "" + array_list_count);

                        if (array_list_count == 0) {
                            restaurant_empty_txt_view.setVisibility(View.VISIBLE);
                        } else {
                            restaurant_empty_txt_view.setVisibility(View.GONE);
                        }

                        createMenuStoreListAdapter(restaurent_list_recycler_view, storeListArrayList);


                        if (!loginPrefManager.getStringValue("user_id").isEmpty()) {

//                            Log.e("first_store_review", "" + loginPrefManager.getStringValue("first_store_review"));

                            // first time app store review dialog view
                            if (review_status.isEmpty()) {
                                CheckReviewStatesReqMethod();
                                review_status = "1";
                            }

                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(MenuRestaurantsActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
//                    Log.e("Exception Error", "" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<StoreList> call, Throwable t) {
//                Log.e("onFailure", "" + t.getMessage());
                progressDialog.dismiss();
            }

        });

    }

    private void CheckReviewStatesReqMethod() {

        try {

//            Log.e("user_id","" + loginPrefManager.getStringValue("user_id"));
//            Log.e("user_token","" + loginPrefManager.getStringValue("user_token"));

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService restarunt_review = Webdata.getRetrofit().create(APIService.class);
            restarunt_review.CheckUserLatestOrderReview("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<CheckReviewReq>() {
                @Override
                public void onResponse(Call<CheckReviewReq> call, Response<CheckReviewReq> response) {

                    try {

                        progressDialog.dismiss();
//                        Log.e("onResponse", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {
                            if (response.body().getResponse().getReview() == 0) {
                                new LoginPostReviewDialog(MenuRestaurantsActivity.this, response.body().getResponse().getOrderId()).show();
                            }
                        } else {
//                            Toast.makeText(MenuRestaurantsActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("onResponse Exception", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<CheckReviewReq> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", "" + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
//            Log.e("Exception", "" + e.getMessage());
        }

    }

    private void createMenuStoreListAdapter(RecyclerView recyclerView, final ArrayList<StoreList_Data> reviewList) {

        datumHomeParallaxStoreListAdapter = new HomeParallaxStoreListAdapter<StoreList_Data>(reviewList, MenuRestaurantsActivity.this) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, HomeParallaxStoreListAdapter<StoreList_Data> store_list_adapter, int i) {

                try {

                    if (store_list_adapter.getData().get(i).getAverageRating() != null) {
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(store_list_adapter.getData().get(i).getAverageRating()));
                    }

                    if (store_list_adapter.getData().get(i).getOpenRestaurant() == 0) {

                        if (store_list_adapter.getData().get(i).getPromotion() == 0) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setVisibility(View.GONE);
                        } else {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setVisibility(View.VISIBLE);
                        }

                        Glide.with(MenuRestaurantsActivity.this).load(store_list_adapter.getData().get(i).getFeaturedImage()).bitmapTransform(new CenterCrop(MenuRestaurantsActivity.this), new RoundedCornersTransformation(MenuRestaurantsActivity.this, 0, 0)).error(R.color.app_background_color).into(((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_image);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_name.setText(store_list_adapter.getData().get(i).getOutletsName());

                        if (store_list_adapter.getData().get(i).getAverageRating() != null && !store_list_adapter.getData().get(i).getAverageRating().equals("")) {
                            if (!store_list_adapter.getData().get(i).getAverageRating().equals("")) {
                                ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).close_restaurant_ratingBar.setRating(Float.parseFloat("" + store_list_adapter.getData().get(i).getAverageRating()));
                            }
                        }

//                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValueClosed(String.format("%.2f", Float.valueOf("" + store_list_adapter.getData().get(i).getMinimumOrderAmount()))));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat("" + store_list_adapter.getData().get(i).getMinimumOrderAmount()))));

                        if (store_list_adapter.getData().get(i).getDeliveryTime().isEmpty()) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText("-");
                        } else {

//                            Log.e("Float Value",  formatFigureTwoPlaces(Float.parseFloat("" + store_list_adapter.getData().get(i).getDeliveryTime())));
//                            Log.e("Float Value","" + String.format("%1$.2f", Float.parseFloat("" + store_list_adapter.getData().get(i).getDeliveryTime())));

                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText("" + Html.fromHtml(String.format(getString(R.string.rest_list_mins), "" + store_list_adapter.getData().get(i).getDeliveryTime())));
//                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText("" + Html.fromHtml(String.format(getString(R.string.mins), String.format("%.2f", Float.parseFloat("" + store_list_adapter.getData().get(i).getDeliveryTime())))));
                        }

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_open_status.setText("" + getString(R.string.home_store_status_close_txt));

                        ColorMatrix matrix = new ColorMatrix();
                        matrix.setSaturation(0);
                        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setColorFilter(filter);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_image.setColorFilter(filter);
                        //((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).delivery_img_view.setColorFilter(filter);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_name.setTextColor(getResources().getColor(R.color.text_color));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_open_status.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

//                    ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setBackgroundColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setBackgroundResource(Color.parseColor(loginPrefManager.getThemeFontColor()));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).close_restaurant_ratingBar.setVisibility(View.VISIBLE);
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_ratingBar.setVisibility(View.GONE);

                    } else {

                        if (store_list_adapter.getData().get(i).getPromotion() == 0) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setVisibility(View.GONE);
                        } else {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setVisibility(View.VISIBLE);
                        }

                        Glide.with(MenuRestaurantsActivity.this).load(store_list_adapter.getData().get(i).getFeaturedImage()).bitmapTransform(new CenterCrop(MenuRestaurantsActivity.this), new RoundedCornersTransformation(MenuRestaurantsActivity.this, 0, 0)).error(R.color.app_background_color).into(((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_image);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_name.setText(store_list_adapter.getData().get(i).getOutletsName());


                        if (store_list_adapter.getData().get(i).getAverageRating() != null && !store_list_adapter.getData().get(i).getAverageRating().equals("")) {
                            if (!store_list_adapter.getData().get(i).getAverageRating().equals("")) {
                                ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_ratingBar.setRating(Float.parseFloat("" + store_list_adapter.getData().get(i).getAverageRating()));
                            }
                        }

                       // ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(store_list_adapter.getData().get(i).getMinimumOrderAmount()))));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setText(Html.fromHtml(loginPrefManager.getCurrecncyWithDynamicColor(loginPrefManager.getThemeFontColor(),loginPrefManager.GetEngDecimalFormatValues( Float.valueOf(store_list_adapter.getData().get(i).getMinimumOrderAmount())))));

                        if (store_list_adapter.getData().get(i).getDeliveryTime().isEmpty()) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText("-");
                        } else {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText(Html.fromHtml(String.format(getString(R.string.rest_list_mins), "" + store_list_adapter.getData().get(i).getDeliveryTime())));
                           // ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setText(Html.fromHtml(String.format(getString(R.string.rest_list_mins),loginPrefManager.getCurrecncyWithDynamicColor(loginPrefManager.getThemeFontColor(),"" + store_list_adapter.getData().get(i).getDeliveryTime()))));
                        }


                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_open_status.setText("" + getString(R.string.home_store_status_open_txt));

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_promo_offer_lab_img_view.setColorFilter(null);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_image.setColorFilter(null);
                        // ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).delivery_img_view.setColorFilter(null);

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_name.setTextColor(getResources().getColor(R.color.text_color));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_open_status.setTextColor(Color.parseColor("" + getString(R.string.open_store_status_text_view_color)));

//                    ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setBackgroundColor(Color.parseColor("" + getString(R.string.open_store_rating_text_view_color)));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_rating.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_minimum_order_amout.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_mins.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setTextColor(Color.parseColor("" + getString(R.string.closse_store_text_view_color)));


                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).close_restaurant_ratingBar.setVisibility(View.GONE);
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_ratingBar.setVisibility(View.VISIBLE);

//                    LayerDrawable open_stars = (LayerDrawable) ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_ratingBar.getProgressDrawable();
//                    open_stars.getDrawable(2).setColorFilter(null);

                    }

                    if (store_list_adapter.getData().get(i).getDeliveryType() == 1) {
                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
                    } else if (store_list_adapter.getData().get(i).getDeliveryType() == 2) {

//                        Log.e("Delivery Cost", "" + String.valueOf(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed())));

                        if (String.valueOf(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed())).equals("0.0")) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
                        } else {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed()))));
                        }

//                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues( Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed()))));

                    } else if (store_list_adapter.getData().get(i).getDeliveryType() == 3) {

//                        Log.e("Delivery Cost", "" + String.valueOf(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed())));

                        if (String.valueOf(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed())).equals("0.0")) {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(getString(R.string.store_listfree_txt));
                        } else {
                            ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed()))));
                        }

//                        ((MenuRestaurantsActivity.MenuRestaurantHolder) viewHolder).restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(store_list_adapter.getData().get(i).getDeliveryCostFixed()))));

                    }

                } catch (Exception e) {
                    Log.e("Exception", "" + e.getMessage());
                }

            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, final HomeParallaxStoreListAdapter<StoreList_Data> adapter, int i) {
                return new MenuRestaurantsActivity.MenuRestaurantHolder(getLayoutInflater().inflate(R.layout.activity_restaurant_menu_listing, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(HomeParallaxStoreListAdapter<StoreList_Data> adapter) {
                return array_list_count;
            }

        };

        datumHomeParallaxStoreListAdapter.setOnClickEvent(new HomeParallaxStoreListAdapter.OnClickEvent() {
            @Override
            public void onClick(View v, int position) {

                StoreList_Data storeList_data = reviewList.get(position);

                Intent intent_rece = new Intent("dynamic_tabs");
                LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).sendBroadcast(intent_rece);

                Intent out_let = new Intent(MenuRestaurantsActivity.this, ProductMenuItemListWithDynamicTabs.class);
//                    Intent out_let = new Intent(MenuRestaurantsActivity.this, StoreProductCategoriesListActivity.class);
                out_let.putExtra("vender_name", "" + storeList_data.getOutletsName());
                out_let.putExtra("vender_id", "" + storeList_data.getVendorsId());
                out_let.putExtra("outlet_id", "" + storeList_data.getOutletsId());
                startActivity(out_let);

            }
        });

        // Create a grid layout with two columns

        menuStoresBannerView = new MenuStoresBannerView(MenuRestaurantsActivity.this);
        menuStoresBannerView.HomeMenuBannerInterfaceMethod(this);

        TextView textView = (TextView) menuStoresBannerView.findViewById(R.id.home_menu_offers_banner_empty_txt_view);
        textView.setText("---");

        datumHomeParallaxStoreListAdapter.setParallaxHeader(menuStoresBannerView, recyclerView);
        datumHomeParallaxStoreListAdapter.setData(reviewList);
        recyclerView.setAdapter(datumHomeParallaxStoreListAdapter);

        menuStoresBannerView.SetHomeMenuBannerStoreCount(reviewList.size());

    }

    public String formatFigureTwoPlaces(float value) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("##0.00");
        return df.format(value);
    }


    private void setLocationaddress() {

        Intent intent_rece = new Intent("dynamic_tabs");
        LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).sendBroadcast(intent_rece);

        Intent outlet_rece = new Intent("outlet_list");
        LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).sendBroadcast(outlet_rece);

        if (!loginPrefManager.getLocName().isEmpty()) {

            menu_rest_title_txt.setText("" + loginPrefManager.getCityName() + ", " + loginPrefManager.getLocName());
        } else {
            menu_rest_title_txt.setText("" + getString(R.string.select_your_loc_txt));
        }

    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        final List<StoreList_Data> filteredModelList = searchFilter(storeListArrayList, query);
        if (datumHomeParallaxStoreListAdapter != null && datumHomeParallaxStoreListAdapter.getItemCount() != 0) {
            array_list_count = filteredModelList.size();
            datumHomeParallaxStoreListAdapter.setFilter(filteredModelList);

            if (array_list_count == 0) {
                restaurant_empty_txt_view.setVisibility(View.VISIBLE);
            } else {
                restaurant_empty_txt_view.setVisibility(View.GONE);
            }

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText.isEmpty()) {
            if (datumHomeParallaxStoreListAdapter != null && datumHomeParallaxStoreListAdapter.getItemCount() != 0) {
                array_list_count = storeListArrayList.size();
                datumHomeParallaxStoreListAdapter.setFilter(storeListArrayList);

                if (array_list_count == 0) {
                    restaurant_empty_txt_view.setVisibility(View.VISIBLE);
                } else {
                    restaurant_empty_txt_view.setVisibility(View.GONE);
                }

            }
        }

        return false;
    }

    private List<StoreList_Data> searchFilter(List<StoreList_Data> models, String query) {
        query = query.toLowerCase();
        final List<StoreList_Data> filteredModelList = new ArrayList<>();
        for (StoreList_Data model : models) {

            String text = "";

            text = model.getOutletsName().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void ClickSearchViewAction() {
        Intent home_search_intent = new Intent(MenuRestaurantsActivity.this, HomeSearchActivity.class);
        home_search_intent.putExtra("resta_list", storeListArrayList);
        startActivity(home_search_intent);
    }


    public class MenuRestaurantHolder extends RecyclerView.ViewHolder {

        public View Item_Row_View;

        public final ImageView restaurant_image;
        public final ImageView restaurant_promo_offer_lab_img_view;
        public final TextView restaurant_name;

        public final TextView restaurant_open_status;

        public final RatingBar restaurant_ratingBar;
        public final RatingBar close_restaurant_ratingBar;

        public final TextView restaurant_rating;

        public final TextView restaurant_minimum_order_amout;
        public final TextView restaurant_delivery_mins;

        public final TextView restaurant_delivery_type;

        public final TextView delivery_img_view;

        public MenuRestaurantHolder(View view) {
            super(view);

            Item_Row_View = view;
            restaurant_image = (ImageView) view.findViewById(R.id.restaurant_image);

            restaurant_promo_offer_lab_img_view = (ImageView) view.findViewById(R.id.restaurant_promo_offer_lab_img_view);
            restaurant_name = (TextView) view.findViewById(R.id.restaurant_name);
            restaurant_name.setTextColor(Color.parseColor(loginPrefManager.getThemeColorAccent()));

            restaurant_open_status = (TextView) view.findViewById(R.id.restaurant_open_status);

            restaurant_ratingBar = (RatingBar) view.findViewById(R.id.restaurant_ratingBar);
            close_restaurant_ratingBar = (RatingBar) view.findViewById(R.id.close_restaurant_ratingBar);

            restaurant_rating = (TextView) view.findViewById(R.id.restaurant_rating);
            restaurant_rating.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
            restaurant_rating.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


            restaurant_minimum_order_amout = (TextView) view.findViewById(R.id.restaurant_minimum_order_amout);
            restaurant_minimum_order_amout.setTextColor(Color.parseColor(loginPrefManager.getThemeColorAccent()));
            restaurant_delivery_mins = (TextView) view.findViewById(R.id.restaurant_delivery_mins);

            restaurant_delivery_type = (TextView) view.findViewById(R.id.restaurant_delivery_type);

            delivery_img_view = (TextView) view.findViewById(R.id.delivery_img_view);

        }

    }


    public void BaseBackPressed() {

        new AlertDialog.Builder(MenuRestaurantsActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("" + getString(R.string.confirm_alert_tit_txt))
                .setMessage("" + getString(R.string.confirm_alert_msg_txt))
                .setPositiveButton("" + getString(R.string.confirm_alert_yes_btn_txt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent signInActivity = new Intent(MenuRestaurantsActivity.this, AppFineshActivity.class);
                        signInActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(signInActivity);
                        finish();

                    }
                })
                .setNegativeButton("" + getString(R.string.confirm_alert_no_btn_txt), null)
                .show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
                LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).unregisterReceiver(baseMenuBroadcastReceiver);
                LocalBroadcastManager.getInstance(MenuRestaurantsActivity.this).unregisterReceiver(Filter_broadcastReceiver);

    }
}
