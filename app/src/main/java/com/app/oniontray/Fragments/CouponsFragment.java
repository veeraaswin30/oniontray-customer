package com.app.oniontray.Fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.Adapters.CouponsAdapter;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.OffersItemOffsetDecor;
import com.app.oniontray.RequestModels.CouponsList;
import com.app.oniontray.RequestModels.OffersRespReq;
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

public class CouponsFragment  extends Fragment {


    private View rootView;
    private LoginPrefManager loginPrefMananger;
    private DDProgressBarDialog progressDialog;

    private RecyclerView coupons_recycler_view;
    private CouponsAdapter coupons_recyclerview_adapter;
    private TextView coupons_empt_msg_txt_view;


    public CouponsFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_coupons_layout, container, false);

        loginPrefMananger = new LoginPrefManager(getContext());
        progressDialog = Webdata.getProgressBarDialog(getContext());


        coupons_recycler_view = (RecyclerView) rootView.findViewById(R.id.coupons_recycler_view);
        coupons_empt_msg_txt_view = (TextView) rootView.findViewById(R.id.coupons_empt_msg_txt_view);

        coupons_recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 1));
        coupons_recycler_view.addItemDecoration(new OffersItemOffsetDecor(getContext(), R.dimen.off_list_item_row_line_hight));


        getcouponsReqMethod();

        return rootView;
    }


    private void getcouponsReqMethod(){

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.GetPromotionsOffersList("" + loginPrefMananger.getStringValue("Lang_code"), "" + loginPrefMananger.getStringValue("user_id"),
                    "" + loginPrefMananger.getStringValue("user_token")).enqueue(new Callback<OffersRespReq>() {
                @Override
                public void onResponse(Call<OffersRespReq> call, Response<OffersRespReq> response) {

                    try {

//                        Log.e("getoffers_list", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if(response.body().getResponse().getCouponsList().size() != 0){
                                coupons_recyclerview_adapter = new CouponsAdapter(getContext(), (ArrayList<CouponsList>) response.body().getResponse().getCouponsList());
                                coupons_recycler_view.setAdapter(coupons_recyclerview_adapter);
                            }else{
                                coupons_empt_msg_txt_view.setVisibility(View.VISIBLE);
                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            coupons_empt_msg_txt_view.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
//                        Log.e("Exception error", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<OffersRespReq> call, Throwable t) {
//                    Log.e("onFailure", t.toString());
                    progressDialog.dismiss();
                }
            });


        }catch (Exception e){
//            Log.e("getcouponsReqMethod","Exception" + e.getMessage());
        }

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
