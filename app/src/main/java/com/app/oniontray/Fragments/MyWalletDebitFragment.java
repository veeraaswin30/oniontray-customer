package com.app.oniontray.Fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.MyWalletDebitAdapter;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.MyWalletOrderItemDecoration;
import com.app.oniontray.RequestModels.DebitWalletData;
import com.app.oniontray.RequestModels.WalletReq;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 16/5/17.
 */

public class MyWalletDebitFragment extends Fragment {


    private Context context;

    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog progressDialog;

    private RecyclerView my_wal_cred_debi_recy_view;
    private MyWalletDebitAdapter myWalletDebitAdapter;

    private TextView my_wal_cred_debi_embty_msg_txt_view;


    public MyWalletDebitFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_wallet_credit_debeit_fragment_layout, container, false);

        context = this.getActivity();

        loginPrefManager = new LoginPrefManager(context);
        progressDialog = Webdata.getProgressBarDialog(context);

        my_wal_cred_debi_recy_view = (RecyclerView) rootView.findViewById(R.id.my_wal_cred_debi_recy_view);
        my_wal_cred_debi_recy_view.setLayoutManager(new LinearLayoutManager(context));
        my_wal_cred_debi_recy_view.addItemDecoration(new MyWalletOrderItemDecoration(context, R.dimen.my_wallet_devider_line_size));


        my_wal_cred_debi_embty_msg_txt_view = (TextView) rootView.findViewById(R.id.my_wal_cred_debi_embty_msg_txt_view);

        WalletDetailsRequestMethod();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void WalletDetailsRequestMethod() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.UserWalletRequest("" + loginPrefManager.getStringValue("Lang_code"), loginPrefManager.getStringValue("user_id"),
                    loginPrefManager.getStringValue("user_token")).enqueue(new Callback<WalletReq>() {
                @Override
                public void onResponse(Call<WalletReq> call, Response<WalletReq> response) {

                    try {

                        progressDialog.dismiss();
//                        Log.e("UserWalletRequest", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getDebitWalletList().size() != 0) {

                                myWalletDebitAdapter = new MyWalletDebitAdapter(context, (ArrayList<DebitWalletData>) response.body().getResponse().getDebitWalletList());
                                my_wal_cred_debi_recy_view.setAdapter(myWalletDebitAdapter);

                                my_wal_cred_debi_embty_msg_txt_view.setVisibility(View.GONE);

                            } else {
                                my_wal_cred_debi_embty_msg_txt_view.setVisibility(View.VISIBLE);
                                my_wal_cred_debi_embty_msg_txt_view.setText("" + context.getString(R.string.my_wallet_debit_err_msg_txt));
                            }

                            if (myWallDebitFragInterface != null) {
                                myWallDebitFragInterface.MyWalletDebitFragment("" + response.body().getResponse().getUserWalletAmount());
                            }


                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("UserWalletRequestMethod", "Exception :" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<WalletReq> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", "" + t.toString());
                }
            });

        } catch (Exception e) {
//            Log.e("UserWalletRequestMethod", "Exception :" + e.getMessage());
        }
    }


    public MyWallDebitFragInterface myWallDebitFragInterface;

    public void CallMyWalletDebitFragInterface(MyWallDebitFragInterface myWallDebitFragInterface) {
        this.myWallDebitFragInterface = myWallDebitFragInterface;
    }

    public interface MyWallDebitFragInterface {
        void MyWalletDebitFragment(String wallet_amt);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
