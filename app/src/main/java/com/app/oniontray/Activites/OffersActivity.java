package com.app.oniontray.Activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.OfferAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OffersItemOffsetDecor;
import com.app.oniontray.RequestModels.Offerlist;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OffersActivity extends LocalizationActivity {

    private RecyclerView mOfferRecyclerview;
    private OfferAdapter mOffer_recyclerview_adapter;

    private TextView my_offers_empt_msg_txt_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.offer_toolbar_id);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(OffersActivity.this));


        if (language.equals("en")) {
// getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        }

//           String language = String.valueOf(LanguageSetting.getLanguage(AddAddressActivity.this));


        if (language.equals("en")) {
//
//
//            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
//            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
//            getSupportActionBar().setHomeAsUpIndicator(upArrow);
//        } else {
///*
//            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
//
//*/
//
//        }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    finish();
                }
            });

            if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
                Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }


            mOfferRecyclerview = (RecyclerView) findViewById(R.id.offer_recycler_view);
            my_offers_empt_msg_txt_view = (TextView) findViewById(R.id.my_offers_empt_msg_txt_view);

            mOfferRecyclerview.setLayoutManager(new GridLayoutManager(OffersActivity.this, 1));
            mOfferRecyclerview.addItemDecoration(new OffersItemOffsetDecor(OffersActivity.this, R.dimen.off_list_item_row_line_hight));

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.getoffers_list("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<Offerlist>() {
                @Override
                public void onResponse(Call<Offerlist> call, Response<Offerlist> response) {

                    try {

//                    Log.e("getoffers_list", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            mOffer_recyclerview_adapter = new OfferAdapter(OffersActivity.this, response.body().getResponse().getData());
                            mOfferRecyclerview.setAdapter(mOffer_recyclerview_adapter);

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            my_offers_empt_msg_txt_view.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
//                    Log.e("Exception error", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<Offerlist> call, Throwable t) {
//                Log.e("onFailure", t.toString());
                    progressDialog.dismiss();
                }
            });

        }


    }
}
