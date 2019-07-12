package com.app.oniontray.CustomViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OrderDetail;
import com.app.oniontray.RequestModels.StoRettingRes;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 22/5/17.
 */

public class LoginPostReviewDialog extends Dialog {


    private Context context;
    private int order_id;

    private int store_id;
    private int outlet_id;

    private DDProgressBarDialog progressBarDialog;
    private LoginPrefManager loginPrefManager;


    private TextView log_review_dialog_tit_txt_view;
    private ImageView log_review_dialog_icon_img_view;

    private TextView log_review_dialog_sub_tit_txt_view;
    private TextView log_review_dialog_deliv_date_txt_view;

    private TextView log_review_dialog_sto_name_txt_view;
    private TextView log_review_dialog_price_txt_view;

    private TextView log_review_dialog_ingredient_txt_view;
    private TextView log_review_dialog_ratings_tit_txt_view;

    private RatingBar log_review_dialog_rating_bar;

    private TextInputLayout log_review_dialog_comm_txt_input_lay;
    private EditText log_review_dialog_comm_edt_txt_view;

    private Button log_review_dialog_submit_button;


    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat deliv_date_new_format = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);


    public LoginPostReviewDialog(Context context, int order_id) {
        super(context);
        this.context = context;
        this.order_id = order_id;
    }

    protected LoginPostReviewDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_post_review_dialog_layout);

        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.setCanceledOnTouchOutside(false);

        loginPrefManager = new LoginPrefManager(context);
        progressBarDialog = new DDProgressBarDialog(context);

        InitlizeMethod();

    }

    private void InitlizeMethod() {

        log_review_dialog_tit_txt_view = (TextView) findViewById(R.id.log_review_dialog_tit_txt_view);
        log_review_dialog_icon_img_view = (ImageView) findViewById(R.id.log_review_dialog_icon_img_view);

        log_review_dialog_sub_tit_txt_view = (TextView) findViewById(R.id.log_review_dialog_sub_tit_txt_view);
        log_review_dialog_deliv_date_txt_view = (TextView) findViewById(R.id.log_review_dialog_deliv_date_txt_view);

        log_review_dialog_sto_name_txt_view = (TextView) findViewById(R.id.log_review_dialog_sto_name_txt_view);
        log_review_dialog_price_txt_view = (TextView) findViewById(R.id.log_review_dialog_price_txt_view);

        log_review_dialog_ingredient_txt_view = (TextView) findViewById(R.id.log_review_dialog_ingredient_txt_view);
        log_review_dialog_ratings_tit_txt_view = (TextView) findViewById(R.id.log_review_dialog_ratings_tit_txt_view);

        log_review_dialog_rating_bar = (RatingBar) findViewById(R.id.log_review_dialog_rating_bar);

        log_review_dialog_comm_txt_input_lay = (TextInputLayout) findViewById(R.id.log_review_dialog_comm_txt_input_lay);
        log_review_dialog_comm_edt_txt_view = (EditText) findViewById(R.id.log_review_dialog_comm_edt_txt_view);
        log_review_dialog_comm_edt_txt_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        log_review_dialog_submit_button = (Button) findViewById(R.id.log_review_dialog_submit_button);
        log_review_dialog_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitMethod();
            }
        });

        MyOrderDetailsRequestMethod();


    }


    private void MyOrderDetailsRequestMethod() {

        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        Log.e("language", loginPrefManager.getStringValue("Lang_code"));
        Log.e("user_id", loginPrefManager.getStringValue("user_id"));
        Log.e("token", loginPrefManager.getStringValue("user_token"));
        Log.e("CityID", "" + loginPrefManager.getCityID());
        Log.e("LocID", "" + loginPrefManager.getLocID());
        Log.e("order_id", "-" + order_id);

        APIService order_detail_api = Webdata.getRetrofit().create(APIService.class);
        order_detail_api.Order_detail_call(loginPrefManager.getStringValue("Lang_code"), "" + order_id, loginPrefManager.getStringValue("user_id"),
                "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(),
                loginPrefManager.getStringValue("user_token")).enqueue(new Callback<OrderDetail>() {
            @Override
            public void onResponse(Call<OrderDetail> call, final Response<OrderDetail> response) {

                try {

                    Log.e("onResponse", "" + response.raw().toString());

                    progressBarDialog.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {

                        store_id = response.body().getResponse().getVendorInfo().get(0).getVendorId();
                        outlet_id = response.body().getResponse().getVendorInfo().get(0).getOutletId();

                        if (!response.body().getResponse().getVendorInfo().get(0).getCreatedDate().isEmpty()) {
                            Date order_date = old_date_format.parse("" + response.body().getResponse().getVendorInfo().get(0).getCreatedDate());
                            log_review_dialog_deliv_date_txt_view.setText("" + deliv_date_new_format.format(order_date));
                        }

                        log_review_dialog_sto_name_txt_view.setText("" + response.body().getResponse().getVendorInfo().get(0).getOutletName());

                        log_review_dialog_price_txt_view.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(response.body().getResponse().getOrderItems().get(0).getTotalAmount()))));

                        if (response.body().getResponse().getOrderItems().size() != 0) {

                            StringBuilder stringBuilder = new StringBuilder();

                            for (int j = 0; j < response.body().getResponse().getOrderItems().size(); j++) {
                                stringBuilder.append(response.body().getResponse().getOrderItems().get(j).getProductName().trim());
                                if (j != response.body().getResponse().getOrderItems().size())
                                    stringBuilder.append(", ");
                            }

                            String str = stringBuilder.toString().trim();
                            str = str.replaceAll(",$", "");
                            log_review_dialog_ingredient_txt_view.setText(str.trim());

                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                    progressBarDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<OrderDetail> call, Throwable t) {
                Log.e("Failure", t.toString());
                progressBarDialog.dismiss();

            }
        });

    }


    private void SubmitMethod() {

        try {

            String comments_txt = "";

            String rattings = String.valueOf((int) log_review_dialog_rating_bar.getRating());

            if (!validateComments()) {
                return;
            } else {
                comments_txt = log_review_dialog_comm_edt_txt_view.getText().toString();
            }

            Log.e("user_id", "-" + loginPrefManager.getStringValue("user_id"));
            Log.e("store_id", "-" + store_id);
            Log.e("outlet_id", "-" + outlet_id);
            Log.e("order_id", "-" + order_id);

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService store_reviews = Webdata.getRetrofit().create(APIService.class);
            store_reviews.update_store_review_call("" + store_id, "" + outlet_id,
                    "" + loginPrefManager.getStringValue("user_id"), "" + order_id,
                    "" + rattings, "" + comments_txt).enqueue(new Callback<StoRettingRes>() {
                @Override
                public void onResponse(Call<StoRettingRes> call, Response<StoRettingRes> response) {

                    try {

                        progressBarDialog.dismiss();
                        Log.e("store_rev onResponse", response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            dismiss();

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        progressBarDialog.dismiss();
                        Log.e("Exception", "" + e.toString());
                    }

                }

                @Override
                public void onFailure(Call<StoRettingRes> call, Throwable t) {
                    Log.e("onFailure", t.toString());
                    progressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage().toString());
        }

    }


    private boolean validateComments() {
        if (log_review_dialog_comm_edt_txt_view.getText().toString().trim().isEmpty()) {
            log_review_dialog_comm_txt_input_lay.setError(context.getString(R.string.stor_review_comm_err_msg_txt));
            requestFocus(log_review_dialog_comm_edt_txt_view);
            return false;
        } else {
            log_review_dialog_comm_txt_input_lay.setErrorEnabled(false);
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

        showDialogOK("" + context.getString(R.string.log_review_dialog_exit_dialig_msg), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        hideKeyboard();
                        break;
                }
            }
        });

    }

    private void showDialogOK(String message, OnClickListener okListener) {
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton("" + context.getString(R.string.otp_dialog_exit_confi_ok_btn), okListener)
                .setNegativeButton("" + context.getString(R.string.otp_dialog_exit_confi_cancel_btn), okListener).create().show();
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }


}
