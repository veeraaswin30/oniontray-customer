package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.StoRettingRes;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateStoreReviewDialog extends Dialog {

    private Context context;

    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog progressDialog;

    private String user_id = "";
    private String store_id = "";
    private String outlet_id = "";
    private String order_id = "";

    private RatingBar sto_review_dial_rating_bar;
    private TextInputLayout my_ord_det_update_comm_txt_in_lay;
    private EditText sto_review_dial_comm_edt_text;
    private Button sto_rew_dial_done_btn;

    public UpdateStoreReviewDialog(Context context) {
        super(context);
    }

    public UpdateStoreReviewDialog(Context context, String user_id, String store_id, String outlet_id, String order_id) {
        super(context);

        this.context = context;

        loginPrefManager = new LoginPrefManager(context);
        progressDialog = Webdata.getProgressBarDialog(context);

        this.user_id = user_id;
        this.store_id = store_id;
        this.outlet_id = outlet_id;
        this.order_id = order_id;

    }

    public UpdateStoreReviewDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public UpdateStoreReviewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.store_review_dialog_layout);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.setCanceledOnTouchOutside(false);

        sto_review_dial_rating_bar = (RatingBar) findViewById(R.id.sto_review_dial_rating_bar);
        my_ord_det_update_comm_txt_in_lay = (TextInputLayout) findViewById(R.id.my_ord_det_update_comm_txt_in_lay);
        sto_review_dial_comm_edt_text = (EditText) findViewById(R.id.sto_review_dial_comm_edt_text);
        sto_rew_dial_done_btn = (Button) findViewById(R.id.sto_rew_dial_done_btn);

        sto_rew_dial_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateStoReviewReqMethod();
            }
        });


    }


    private void UpdateStoReviewReqMethod() {

        String comments_txt = "";

//        if (sto_review_dial_comm_edt_text.getText().toString().isEmpty()) {
//            Toast.makeText(context, "" + context.getString(R.string.stor_review_comm_err_msg_txt), Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            comments_txt = sto_review_dial_comm_edt_text.getText().toString();
//        }

        String rattings = String.valueOf((int) sto_review_dial_rating_bar.getRating());

        if (!validateComments()) {
            return;
        } else {
            comments_txt = sto_review_dial_comm_edt_text.getText().toString();
        }

        Log.e("user_id", "-" + user_id);
        Log.e("store_id", "-" + store_id);
        Log.e("outlet_id", "-" + outlet_id);
        Log.e("order_id", "-" + order_id);

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService store_reviews = Webdata.getRetrofit().create(APIService.class);
        store_reviews.update_store_review_call("" + store_id, "" + outlet_id, "" + user_id,
                "" + order_id, "" + rattings, "" + comments_txt).enqueue(new Callback<StoRettingRes>() {
            @Override
            public void onResponse(Call<StoRettingRes> call, Response<StoRettingRes> response) {

                progressDialog.dismiss();
                Log.e("store_rev onResponse", response.raw().toString());

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

                        Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        if (myorderReviewReloadInterface != null) {
                            myorderReviewReloadInterface.ReloadOrderReviewDetatils();
                        }

                        dismiss();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
//                    Log.e("store_reviews Exception", e.toString());
                }

            }

            @Override
            public void onFailure(Call<StoRettingRes> call, Throwable t) {
//                Log.e("store_reviews onFailure", t.toString());
                progressDialog.dismiss();
            }
        });

    }

    private boolean validateComments() {
        if (sto_review_dial_comm_edt_text.getText().toString().trim().isEmpty()) {
            my_ord_det_update_comm_txt_in_lay.setError(context.getString(R.string.stor_review_comm_err_msg_txt));
            requestFocus(sto_review_dial_comm_edt_text);
            return false;
        } else {
            my_ord_det_update_comm_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }


    public interface MyorderReviewReloadInterface {
        void ReloadOrderReviewDetatils();
    }

    private UpdateStoreReviewDialog.MyorderReviewReloadInterface myorderReviewReloadInterface;

    public void MyOrderinterfaceCallMethod(UpdateStoreReviewDialog.MyorderReviewReloadInterface myorderReviewReloadInterface) {
        this.myorderReviewReloadInterface = myorderReviewReloadInterface;
    }

}
