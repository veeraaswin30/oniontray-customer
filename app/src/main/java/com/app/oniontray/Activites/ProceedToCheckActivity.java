package com.app.oniontray.Activites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.app.oniontray.Fragments.PickUpFragment;
import com.google.android.material.tabs.TabLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.oniontray.CustomViews.CustomViewPager;
import com.app.oniontray.Fragments.DeliveryFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OutletDetails;


import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProceedToCheckActivity extends LocalizationActivity {

    private Toolbar pro_check_toolbar_id;

    private TabLayout tabLayout;
    private OutletDetails outletDetails;
    private String deliveryMode = "";
    DeliveryFragment deliveryFragment;

    TextView payment_title;

    private ProceedToCheckBroadcastReceiver proceedToCheckBroadcastReceiver;
    private String vendor_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceed_to_checkout_page);

        pro_check_toolbar_id = (Toolbar) findViewById(R.id.pro_check_toolbar_id);
        pro_check_toolbar_id.setTitle("");
        pro_check_toolbar_id.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        pro_check_toolbar_id.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        pro_check_toolbar_id.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(pro_check_toolbar_id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(ProceedToCheckActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        if (getIntent().hasExtra("outlet_details")) {
            outletDetails = (OutletDetails) getIntent().getSerializableExtra("outlet_details");
            vendor_id = getIntent().getStringExtra("vendor_id");
            deliveryMode = "" + outletDetails.getPaymentMode();
//            Log.e("deliveryMode", "" + outletDetails.getPaymentMode());
        }

        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

//        viewPager.setPagingEnabled(false);
//        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
//        tabStrip.setEnabled(false);
//        tabStrip.getChildAt(1).setClickable(false);

        proceedToCheckBroadcastReceiver = new ProceedToCheckBroadcastReceiver();
        LocalBroadcastManager.getInstance(ProceedToCheckActivity.this).registerReceiver(proceedToCheckBroadcastReceiver, new IntentFilter("address_update"));


    }

    private void setupViewPager(CustomViewPager viewPager) {

        deliveryFragment = new DeliveryFragment();
        PickUpFragment pickUpFragment = new PickUpFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("outlet_details", outletDetails);
        bundle.putString("vendor_id", vendor_id);
        deliveryFragment.setArguments(bundle);
        pickUpFragment.setArguments(bundle);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(deliveryFragment, "" + getString(R.string.proc_to_che_out_delivery_txt));
        adapter.addFragment(pickUpFragment, "" + getString(R.string.proc_to_che_out_pickup_txt));
        viewPager.setAdapter(adapter);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ProceedToCheckBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            deliveryFragment.addresListRequestMethod();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(ProceedToCheckActivity.this).unregisterReceiver(proceedToCheckBroadcastReceiver);
    }
}