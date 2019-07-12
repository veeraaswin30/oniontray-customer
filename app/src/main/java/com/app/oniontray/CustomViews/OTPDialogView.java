package com.app.oniontray.CustomViews;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.invokeOfflinePaymentMethod;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.SendOTP;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OTPDialogView extends Dialog {

    private Context context;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    private TextView otp_dialog_title_txt_view;
    private TextView otp_dialog_content_txt_view;

    private EditText otp_edt_txt_view;
    private Button otp_resend_btn;
    private Button otp_verify_btn;
    private Button otp_send_btn;

    private LinearLayout otp_dialog_resend_verify_layout;
    private LinearLayout otp_dialog_send_btn_layout;
    private RadioGroup otp_dialog_radio_group;

    private RadioButton otp_dialog_email_radioButton;
    private RadioButton otp_dialog_mobile_radioButton;
    private RadioButton otp_dialog_both_radioButton;
    private String OTPOption = "";

    private APIService apiService;

    private invokeOfflinePaymentMethod offlinePaymentMethodinterface;


    public OTPDialogView(Context context, invokeOfflinePaymentMethod offlinePaymentMethodinterface) {
        super(context);
        this.context = context;
        this.offlinePaymentMethodinterface = offlinePaymentMethodinterface;
    }

    public OTPDialogView(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected OTPDialogView(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.otp_dialog_view_layout);

        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());
//        requestProgress = Webdata.getProgressDialog(getContext());

        otp_edt_txt_view = (EditText) findViewById(R.id.otp_edt_txt_view);
        otp_resend_btn = (Button) findViewById(R.id.otp_resend_btn);
        otp_verify_btn = (Button) findViewById(R.id.otp_verify_btn);

        otp_send_btn = (Button) findViewById(R.id.otp_dialog_back_btn);
        otp_send_btn.setTextColor(Color.parseColor(loginManager.getThemeFontColor()));
        otp_send_btn.setBackgroundColor(Color.parseColor(loginManager.getThemeColor()));
        otp_dialog_title_txt_view = (TextView) findViewById(R.id.otp_dialog_title_txt_view);
        otp_dialog_content_txt_view = (TextView) findViewById(R.id.otp_dialog_content_txt_view);

        otp_dialog_radio_group = (RadioGroup) findViewById(R.id.otp_dialog_radio_group);
        otp_dialog_email_radioButton = (RadioButton) findViewById(R.id.otp_dialog_email_radioButton);
        otp_dialog_mobile_radioButton = (RadioButton) findViewById(R.id.otp_dialog_mobile_radioButton);
        otp_dialog_both_radioButton = (RadioButton) findViewById(R.id.otp_dialog_both_radioButton);

        otp_dialog_send_btn_layout = (LinearLayout) findViewById(R.id.otp_dialog_back_send_btn_layout);
        otp_dialog_resend_verify_layout = (LinearLayout) findViewById(R.id.otp_dialog_resend_verify_btn_layout);


        apiService = Webdata.getRetrofit().create(APIService.class);

        otp_resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPSendRequestMethod();
            }
        });

        otp_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPVerifyRequestMethod();
            }
        });

        otp_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPSendRequestMethod();
            }
        });

    }

    public int getInt(String s) {
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public static String getOnlyNumerics(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder strBuff = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    private void OTPSendRequestMethod() {

        try {

            if (!NetworkStatus.isConnectingToInternet(getContext())) {
                Toast.makeText(getContext(), "" + getContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
                return;
            }

            if (!otp_dialog_email_radioButton.isChecked() && !otp_dialog_mobile_radioButton.isChecked() && !otp_dialog_both_radioButton.isChecked()) {
                Toast.makeText(getContext(), "" + getContext().getString(R.string.otp_dialog_sele_any_one_option_txt), Toast.LENGTH_SHORT).show();
                return;
            }

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }


            if (otp_dialog_email_radioButton.isChecked()) {
                OTPOption = "1";
//            Log.e("OTP Option", "1");
            }

            if (otp_dialog_mobile_radioButton.isChecked()) {
                OTPOption = "2";
//            Log.e("OTP Option", "2");
            }

            if (otp_dialog_both_radioButton.isChecked()) {
                OTPOption = "3";
//            Log.e("OTP Option", "3");
            }


            Log.e("user_token", "-" + loginManager.getStringValue("user_token"));
            Log.e("user_id", "-" + loginManager.getStringValue("user_id"));
            Log.e("Lang_code", "-" + loginManager.getStringValue("Lang_code"));
            Log.e("OTPOption", "-" + OTPOption);


            apiService.sendOTP(loginManager.getStringValue("user_token"), loginManager.getStringValue("user_id"),
                    loginManager.getStringValue("Lang_code"), OTPOption).enqueue(new Callback<SendOTP>() {
                @Override
                public void onResponse(Call<SendOTP> call, Response<SendOTP> response) {

                    try {

                        progressBarDialog.dismiss();
                        if (response != null) {

                            if (response.body().getResponse().getHttpCode() == 200) {

                                otp_dialog_radio_group.setVisibility(View.GONE);
                                otp_dialog_send_btn_layout.setVisibility(View.GONE);
                                otp_dialog_title_txt_view.setText("" + context.getString(R.string.otp_dialog_tit_txt));
                                otp_dialog_content_txt_view.setVisibility(View.VISIBLE);
                                otp_edt_txt_view.setVisibility(View.VISIBLE);
                                otp_dialog_resend_verify_layout.setVisibility(View.VISIBLE);

                                showToast(response.body().getResponse().getMessage());

                            } else {
                                showToast(response.body().getResponse().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        progressBarDialog.dismiss();
                        Log.e("Exception", "" + e.getMessage());
                        showToast(e.getMessage());
                    }


                }

                @Override
                public void onFailure(Call<SendOTP> call, Throwable t) {

                    progressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {

        }

    }


    private void OTPVerifyRequestMethod() {


        if (otp_edt_txt_view.getText().toString().isEmpty()) {
            return;
        }

        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        apiService.checkOTP(loginManager.getStringValue("user_token"), loginManager.getStringValue("user_id"),
                loginManager.getStringValue("Lang_code"), otp_edt_txt_view.getText().toString()).enqueue(new Callback<SendOTP>() {
            @Override
            public void onResponse(Call<SendOTP> call, Response<SendOTP> response) {

                try {
                    progressBarDialog.dismiss();

                    if (response != null) {

                        if (response.body().getResponse().getHttpCode() == 200) {

                            Log.e("sucess", "" + response.body().getResponse().getMessage());
                            otp_edt_txt_view.setText("");

                            if (offlinePaymentMethodinterface != null) {
                                offlinePaymentMethodinterface.offlinePaymentMethod();
                            }

                            dismiss();

                        } else {
                            Log.e("sucess", "" + response.body().getResponse().getMessage());
                            otp_edt_txt_view.setText("");
                            showToast(response.body().getResponse().getMessage());
                        }
                    }
                } catch (Exception e) {
                    progressBarDialog.dismiss();
                    showToast(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SendOTP> call, Throwable t) {
                Log.e("onFailure", "" + t.getMessage());
                progressBarDialog.dismiss();
            }
        });


    }

    @Override
    public void onBackPressed() {

        showDialogOK("" + context.getString(R.string.otp_dialog_exit_confi_dial_cont_txt), new OnClickListener() {
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
