package com.app.oniontray.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.Activites.PromotionsListActivity;
import com.app.oniontray.Adapters.PromotionsAdapter;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OffersItemOffsetDecor;
import com.app.oniontray.RequestModels.PromotionList;
import com.app.oniontray.RequestModels.PromotionReq;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 4/4/17.
 */

public class PromotionsFragment extends Fragment implements PromotionsAdapter.MyPromotionInterface {

    private View rootView;
    private LoginPrefManager loginPrefMananger;
    private DDProgressBarDialog progressDialog;

    private RecyclerView promo_recycler_view;
    private PromotionsAdapter promotionsAdapter;
    private TextView promo_empt_msg_txt_view;


    public PromotionsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_promotions_layout, container, false);

        loginPrefMananger = new LoginPrefManager(getContext());
        progressDialog = Webdata.getProgressBarDialog(getContext());

        promo_recycler_view = (RecyclerView) rootView.findViewById(R.id.promo_recycler_view);
        promo_empt_msg_txt_view = (TextView) rootView.findViewById(R.id.promo_empt_msg_txt_view);

//        promo_empt_msg_txt_view.setVisibility(View.VISIBLE);
        promo_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        promo_recycler_view.addItemDecoration(new OffersItemOffsetDecor(getContext(), R.dimen.off_list_item_row_line_hight));


        getPromotionsReqMethod();


        return rootView;
    }


    private void getPromotionsReqMethod() {

        try {

//            Log.e("Lang_code", "" + loginPrefMananger.getStringValue("Lang_code"));
//            Log.e("getCityID", "" + loginPrefMananger.getCityID());
//            Log.e("getLocID", "" + loginPrefMananger.getLocID());

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.GetPromotionsList("" + loginPrefMananger.getStringValue("Lang_code"), "" + loginPrefMananger.getCityID(),
                    "" + loginPrefMananger.getLocID()).enqueue(new Callback<PromotionReq>() {
                @Override
                public void onResponse(Call<PromotionReq> call, Response<PromotionReq> response) {

                    try {

//                        Log.e("getoffers_list", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getPromotionList().size() != 0) {
                                promotionsAdapter = new PromotionsAdapter(getContext(), (ArrayList<PromotionList>) response.body().getResponse().getPromotionList());
                                promotionsAdapter.MyPromotionsInterfaceCallMethod(PromotionsFragment.this);
                                promo_recycler_view.setAdapter(promotionsAdapter);
                            } else {
                                promo_empt_msg_txt_view.setVisibility(View.VISIBLE);
                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            promo_empt_msg_txt_view.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
//                        Log.e("Exception error", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<PromotionReq> call, Throwable t) {
//                    Log.e("onFailure", t.toString());
                    progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
//            Log.e("getcouponsReqMethod", "Exception" + e.getMessage());
        }

    }

    @Override
    public void MyPromotionItemClickEvent(PromotionList promotion) {

//        Log.e("PromotionList", "-" + promotion.getPromotionProducts().size());

        Intent promotion_Intent = new Intent(getContext(), PromotionsListActivity.class);
        promotion_Intent.putExtra("promo_list", promotion);
        startActivity(promotion_Intent);

    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
