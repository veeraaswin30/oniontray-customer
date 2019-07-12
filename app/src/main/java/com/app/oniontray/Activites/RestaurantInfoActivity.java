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
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.app.oniontray.Fragments.RestaurantMoreDetailsFragment;
import com.app.oniontray.Fragments.RestaurantReviewFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.StoInfoOutletDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantInfoActivity extends LocalizationActivity {

    private Toolbar restaurant_info_toolbar;
    private TextView info_title;
    private ViewPager restaurant_info_viewpager;
    private TabLayout restaurant_info_tabs_layout;

    private StoInfoOutletDetails vendorDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        info_title = findViewById(R.id.info_title);
        info_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            vendorDetail = (StoInfoOutletDetails) bundle.getSerializable("vendor_detail");
        }

        intializeView();

        toolbarBackPress();
    }


    private void intializeView() {

        restaurant_info_toolbar = (Toolbar) findViewById(R.id.restaurant_info_toolbar);
        restaurant_info_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(restaurant_info_toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(RestaurantInfoActivity.this));


        if (language.equals("en")) {
           // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        restaurant_info_viewpager = (ViewPager) findViewById(R.id.restaurant_info_viewpager);
        setUpViewPager(restaurant_info_viewpager);

        restaurant_info_tabs_layout = (TabLayout) findViewById(R.id.restaurant_info_tabs_layout);
        restaurant_info_tabs_layout.setSelectedTabIndicatorColor(Color.parseColor(loginPrefManager.getThemeColor()));
        restaurant_info_tabs_layout.setupWithViewPager(restaurant_info_viewpager);

    }


    private void toolbarBackPress() {

        restaurant_info_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

    }

    private void setUpViewPager(ViewPager restaurant_info_viewpager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putSerializable("vendor_detail", vendorDetail);

        RestaurantMoreDetailsFragment moreDetailsFragment = new RestaurantMoreDetailsFragment();
        moreDetailsFragment.setArguments(bundle);
        adapter.addFrag(moreDetailsFragment, getString(R.string.restaurant_info_tab_one));


        RestaurantReviewFragment restaurantReviewFragment = new RestaurantReviewFragment();
        restaurantReviewFragment.setArguments(bundle);
        adapter.addFrag(restaurantReviewFragment, getString(R.string.restaurant_info_tab_two));


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
