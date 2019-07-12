package com.app.oniontray.BannersView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.oniontray.CustomViews.CustomSliderView;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Offer;
import com.app.oniontray.RequestModels.Offerlist;
import com.app.oniontray.RequestModels.StoProdBanner;
import com.app.oniontray.RequestModels.StoProdBannerList_Data;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEXTBRAIN on 1/30/2017.
 */

public class MenuStoresBannerView extends LinearLayout {

    private Context context;
    private View menuStoreBannerView;

    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog progressDialog;


    private RelativeLayout home_menu_offers_banner_lay_out;
    private SliderLayout home_menu_banner_stores_slider;
    private TextView home_menu_offers_banner_empty_txt_view;


    private TextView home_menu_banner_deliv_store_count_txt_view;

    private TextView home_banner_search_txt_view;
    private SwipeRefreshLayout swipeRefreshLayout;


    public MenuStoresBannerView(Context context) {
        super(context);
        this.context = context;

        loginPrefManager = new LoginPrefManager(context);
        progressDialog = Webdata.getProgressBarDialog(context);

        init();
    }

    public MenuStoresBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuStoresBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {

        menuStoreBannerView = inflate(getContext(), R.layout.menu_stores_banner_view_layout, this);

        home_menu_offers_banner_lay_out = (RelativeLayout) menuStoreBannerView.findViewById(R.id.home_menu_offers_banner_lay_out);
        home_menu_banner_stores_slider = (SliderLayout) menuStoreBannerView.findViewById(R.id.home_menu_banner_stores_slider);
        home_menu_offers_banner_empty_txt_view = (TextView) menuStoreBannerView.findViewById(R.id.home_menu_offers_banner_empty_txt_view);

        home_menu_banner_deliv_store_count_txt_view = (TextView) menuStoreBannerView.findViewById(R.id.home_menu_banner_deliv_store_count_txt_view);
//        swipeRefreshLayout = (SwipeRefreshLayout) menuStoreBannerView.findViewById(R.id.swipe_refresh);

        home_banner_search_txt_view = (TextView) menuStoreBannerView.findViewById(R.id.home_banner_search_txt_view);
        home_banner_search_txt_view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (homeMenuBannerInterface != null) {
                    homeMenuBannerInterface.ClickSearchViewAction();
                }
            }
        });

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.e("onRefresh","called");
//            }
//        });


        if (loginPrefManager.getStringValue("Off_Status").equals("1")) {
            home_menu_offers_banner_lay_out.setVisibility(View.VISIBLE);
        } else {
            home_menu_offers_banner_lay_out.setVisibility(View.GONE);
        }


//        StoreOffersBannerRequest();
        RestaurantsBannerRequest();

        SetHomeMenuBannerStoreCount(0);

    }

    private void RestaurantsBannerRequest() {

        try {


            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.getStoProdBanner().enqueue(new Callback<StoProdBanner>() {
                @Override
                public void onResponse(Call<StoProdBanner> call, Response<StoProdBanner> response) {

                    try {

                        Log.e("getStoProdBanner", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            final ArrayList<StoProdBannerList_Data> offerArrayList = (ArrayList<StoProdBannerList_Data>) response.body().getResponse().getBannerList();
                            HashMap<String, String> file_map = new HashMap<>();

                            for (int i = 0; i < offerArrayList.size(); i++) {
                                file_map.put(" " + offerArrayList.get(i).getBannerTitle().substring(0, 1).toUpperCase(), "" + offerArrayList.get(i).getBannerImage());
                            }

                            for (String name : file_map.keySet()) {

                                CustomSliderView textSliderView = new CustomSliderView(context);
                                textSliderView.image(file_map.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
                                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                    @Override
                                    public void onSliderClick(BaseSliderView slider) {

                                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("" + offerArrayList.get(home_menu_banner_stores_slider.getCurrentPosition()).getBannerLink()));
                                        context.startActivity(i);

                                    }
                                });

                                home_menu_banner_stores_slider.addSlider(textSliderView);

                            }

                            home_menu_banner_stores_slider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
                            home_menu_banner_stores_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            home_menu_banner_stores_slider.setCustomAnimation(new DescriptionAnimation());
                            home_menu_banner_stores_slider.setDuration(5000);

                        } else if (response.body().getResponse().getHttpCode() == 400) {

                            home_menu_offers_banner_empty_txt_view.setVisibility(View.VISIBLE);
                            home_menu_offers_banner_empty_txt_view.setText("" + context.getString(R.string.my_offers_no_offers_found_txt));

                        }

                    } catch (Exception e) {
                        Log.e("Exception error", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<StoProdBanner> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.e("", "" + e.getMessage());
        }


    }

    private void StoreOffersBannerRequest() {

        Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
        Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
        Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
//        Log.e("lang ", "" + loginPrefManager.getStringValue("Lang_code"));
        apiService.getoffers_list("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getStringValue("user_id"),
                "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<Offerlist>() {
            @Override
            public void onResponse(Call<Offerlist> call, Response<Offerlist> response) {

                try {

                    Log.e("getoffers_list", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        ArrayList<Offer> offerArrayList = response.body().getResponse().getData();
                        HashMap<String, String> file_map = new HashMap<>();

                        for (int i = 0; i < offerArrayList.size(); i++) {
                            file_map.put(" " + offerArrayList.get(i).getCouponTitle().substring(0, 1).toUpperCase() + offerArrayList.get(i).getCouponTitle().substring(1) + " \n " + context.getString(R.string.promo_code_txt) + " " + offerArrayList.get(i).getCouponCode() + " ", "" + offerArrayList.get(i).getCouponImage());
                        }

                        for (String name : file_map.keySet()) {
                            CustomSliderView textSliderView = new CustomSliderView(context);
                            // initialize a SliderLayout
                            textSliderView.image(file_map.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
//                            textSliderView.description(name).image(file_map.get(name)).setScaleType(BaseSliderView.ScaleType.Fit);
                            //add your extra information
//                            textSliderView.bundle(new Bundle());
//                            textSliderView.getBundle().putString("extra", name);

                            home_menu_banner_stores_slider.addSlider(textSliderView);

                        }

                        home_menu_banner_stores_slider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
                        home_menu_banner_stores_slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                        home_menu_banner_stores_slider.setCustomAnimation(new DescriptionAnimation());
                        home_menu_banner_stores_slider.setDuration(5000);

                    } else if (response.body().getResponse().getHttpCode() == 400) {

                        home_menu_offers_banner_empty_txt_view.setVisibility(View.VISIBLE);
//                        home_menu_offers_banner_empty_txt_view.setText("" + response.body().getResponse().getMessage());
                        home_menu_offers_banner_empty_txt_view.setText("" + context.getString(R.string.my_offers_no_offers_found_txt));

                    }

                } catch (Exception e) {
                    Log.e("Exception error", e.toString());
                }

            }

            @Override
            public void onFailure(Call<Offerlist> call, Throwable t) {
                Log.e("onFailure error", t.toString());
            }

        });

    }

    public void SetHomeMenuBannerStoreCount(int storeCount) {
        home_menu_banner_deliv_store_count_txt_view.setText("" + storeCount + " " + context.getString(R.string.home_banner_store_count_txt));
    }


    public HomeMenuBannerInterface homeMenuBannerInterface;

    public interface HomeMenuBannerInterface {
        void ClickSearchViewAction();
    }

    public void HomeMenuBannerInterfaceMethod(HomeMenuBannerInterface homeMenuBannerInterface) {
        this.homeMenuBannerInterface = homeMenuBannerInterface;
    }

}
