package com.app.oniontray.Activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.app.oniontray.Fragments.CouponsFragment;
import com.app.oniontray.Fragments.PromotionsFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nextbrain on 4/4/17.
 */

public class RestaurantOffersActivity extends LocalizationActivity {


    private Toolbar rest_offers_toolbar;
    private TextView rest_offers_tit_txt_view;
    private TabLayout rest_offers_tabs_layout;
    private ViewPager rest_offers_viewpager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_offers_layout);

        rest_offers_tit_txt_view = (TextView) findViewById(R.id.rest_offers_tit_txt_view);


        rest_offers_toolbar = (Toolbar) findViewById(R.id.rest_offers_toolbar);
      //  rest_offers_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        rest_offers_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        rest_offers_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(rest_offers_toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(RestaurantOffersActivity.this));


        if (language.equals("en")) {
          //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        rest_offers_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        rest_offers_tit_txt_view = (TextView) findViewById(R.id.rest_offers_tit_txt_view);
        rest_offers_tit_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));



        rest_offers_viewpager = (ViewPager) findViewById(R.id.rest_offers_viewpager);
        setUpViewPager(rest_offers_viewpager);

        rest_offers_tabs_layout = (TabLayout) findViewById(R.id.rest_offers_tabs_layout);
        rest_offers_tabs_layout.setSelectedTabIndicatorColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        rest_offers_tabs_layout.setupWithViewPager(rest_offers_viewpager);

    }

    private void setUpViewPager(ViewPager restaurant_info_viewpager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
//        bundle.putSerializable("vendor_detail", vendorDetail);

        PromotionsFragment promotionsFragment = new PromotionsFragment();
        promotionsFragment.setArguments(bundle);

        CouponsFragment couponsFragment = new CouponsFragment();
        couponsFragment.setArguments(bundle);

        adapter.addFrag(promotionsFragment, getString(R.string.offers_frag_promo_tit));
        adapter.addFrag(couponsFragment, getString(R.string.offers_frag_coupons_tit));

        restaurant_info_viewpager.setAdapter(adapter);

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
