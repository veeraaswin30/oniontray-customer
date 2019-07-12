package com.app.oniontray.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.oniontray.Adapters.RestaurantReviewAdapter;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.Review;
import com.app.oniontray.RequestModels.StoInfoOutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantReviewFragment extends Fragment {

    private RecyclerView fragment_restaurant_review_recycler_view;
    private TextView restaurant_review_overall_rating, restaurant_review_rating_text, review_empty_text_view;
    private RatingBar restaurant_review_ratingBar;

    private LoginPrefManager loginPrefMananger;
    private DDProgressBarDialog progressDialog;

    private RestaurantReviewAdapter restaurantReviewAdaper;

    private StoInfoOutletDetails vendorDetail;

    public RestaurantReviewFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_review, container, false);

        loginPrefMananger = new LoginPrefManager(getContext());
        progressDialog = Webdata.getProgressBarDialog(getContext());

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            vendorDetail = (StoInfoOutletDetails) bundle.getSerializable("vendor_detail");
        }

        restaurant_review_overall_rating = (TextView) rootView.findViewById(R.id.restaurant_review_overall_rating);
        restaurant_review_rating_text = (TextView) rootView.findViewById(R.id.restaurant_review_rating_text);
        review_empty_text_view = (TextView) rootView.findViewById(R.id.review_empty_text_view);

        restaurant_review_ratingBar = (RatingBar) rootView.findViewById(R.id.restaurant_review_ratingBar);

        fragment_restaurant_review_recycler_view = (RecyclerView) rootView.findViewById(R.id.fragment_restaurant_review_recycler_view);

        fragment_restaurant_review_recycler_view.setHasFixedSize(true);
        fragment_restaurant_review_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        fragment_restaurant_review_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(getContext(), R.dimen.prod_list_item_row_line_height));

        RestaurantReviewList();

        if (vendorDetail.getOpenRestaurant() == 0) {
            restaurant_review_rating_text.setText("" + loginPrefMananger.getDecimalRattingValue(vendorDetail.getAverageRating()));
            restaurant_review_rating_text.setTextColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
            restaurant_review_rating_text.setBackgroundColor(Color.parseColor(loginPrefMananger.getThemeColor()));
            restaurant_review_rating_text.setBackgroundResource(R.drawable.rating_drawable_grey);
        } else {
            restaurant_review_rating_text.setText("" + loginPrefMananger.getDecimalRattingValue(vendorDetail.getAverageRating()));
            restaurant_review_rating_text.setTextColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
            restaurant_review_rating_text.setBackgroundColor(Color.parseColor(loginPrefMananger.getThemeColor()));
            //restaurant_review_rating_text.setBackgroundResource(R.drawable.rating_drawable_green);
        }


        restaurant_review_ratingBar.setRating(vendorDetail.getAverageRating());

        return rootView;
    }

    private void RestaurantReviewList() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            Log.e("getVendorsId", "" + vendorDetail.getVendorsId());
            Log.e("getOutletsId", "" + vendorDetail.getOutletsId());
            Log.e("Lang_code", "" + loginPrefMananger.getStringValue("Lang_code"));

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.store_review("" + vendorDetail.getVendorsId(), "" + vendorDetail.getOutletsId(),
                    loginPrefMananger.getStringValue("Lang_code")).enqueue(new Callback<Review>() {
                @Override
                public void onResponse(Call<Review> call, Response<Review> response) {

                    try {

                        progressDialog.dismiss();
                        Log.e("onResponse", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {
                            restaurantReviewAdaper = new RestaurantReviewAdapter(getContext(), response.body().getResponse().getReviewList());
                            fragment_restaurant_review_recycler_view.setAdapter(restaurantReviewAdaper);

                            Log.e("count", "......." + response.body().getResponse().getReviewCount());

                            restaurant_review_overall_rating.setText("" + response.body().getResponse().getReviewCount() + " " + getString(R.string.restaurant_rating));
                        }

                        if (response.body().getResponse().getReviewList().size() == 0) {
                            fragment_restaurant_review_recycler_view.setVisibility(View.GONE);
                            review_empty_text_view.setVisibility(View.VISIBLE);
                        } else {
                            fragment_restaurant_review_recycler_view.setVisibility(View.VISIBLE);
                            review_empty_text_view.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();

                        Log.e("Exception", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Review> call, Throwable t) {
                    progressDialog.dismiss();

                }
            });


        } catch (Exception e) {
            progressDialog.dismiss();

            Log.e("Exception", e.getMessage());
        }
    }
}
