package com.app.oniontray.Activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.MyFavAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.FavListItemOffsetDecor;
import com.app.oniontray.RequestModels.MyFavourites;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFavouritesActivity extends LocalizationActivity implements MyFavAdapter.MyFavAdapterInterface {

    private Toolbar toolbar;

    private RecyclerView mfavRecyclerview;
    private MyFavAdapter mfav_recyclerview_adapter;

    private TextView my_fav_empt_msg_txt_view;

    private TextView fav_title;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav_lay);

        toolbar = (Toolbar) findViewById(R.id.fav_toolbar_id);
//        toolbar.setTitle(getString(R.string.my_fav_title));
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


           String language = String.valueOf(LanguageSetting.getLanguage(MyFavouritesActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        mfavRecyclerview = (RecyclerView) findViewById(R.id.my_fav_recycler_view);
        mfavRecyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        mfavRecyclerview.addItemDecoration(new FavListItemOffsetDecor(MyFavouritesActivity.this, R.dimen.my_fav_list_item_row_margin_size));

        my_fav_empt_msg_txt_view = (TextView) findViewById(R.id.my_fav_empt_msg_txt_view);
        fav_title = (TextView) findViewById(R.id.fav_title);
        fav_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        fav_title.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        FavouriteRequestMethod();

    }

    private void FavouriteRequestMethod() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.get_favourites_list("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getStringValue("user_id"),
                    loginPrefManager.getStringValue("user_token"), "1", "" + loginPrefManager.getCityID(),
                    "" + loginPrefManager.getLocID()).enqueue(new Callback<MyFavourites>() {
                @Override
                public void onResponse(Call<MyFavourites> call, Response<MyFavourites> response) {

                    progressDialog.dismiss();

                    try {

                        if (response.body().getResponse().getHttpCode() == 200) {

                            mfav_recyclerview_adapter = new MyFavAdapter(MyFavouritesActivity.this, response.body().getResponse().getData());
                            mfav_recyclerview_adapter.MyFavAdapterInterfaceCallback(MyFavouritesActivity.this);

                            mfavRecyclerview.setAdapter(mfav_recyclerview_adapter);

                        } else if (response.body().getResponse().getHttpCode() == 400) {

                            if (response.body().getResponse().getData().size() == 0) {
                                my_fav_empt_msg_txt_view.setVisibility(View.VISIBLE);
                            } else {
                                my_fav_empt_msg_txt_view.setVisibility(View.GONE);
                            }

//                            Log.e("failure", response.body().getResponse().toString());
                        }

                    } catch (Exception e) {
//                        Log.e("error", e.toString());
                    }
                }

                @Override
                public void onFailure(Call<MyFavourites> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", t.getMessage());
                }
            });

        } catch (Exception e) {

        }

    }


    @Override
    public void FavAdaptercount(int count) {

//        Log.e("myFavAdapterInterface", "activity" + count);

        if (count == 0) {
            my_fav_empt_msg_txt_view.setVisibility(View.VISIBLE);
        } else {
            my_fav_empt_msg_txt_view.setVisibility(View.GONE);
        }

    }

}


