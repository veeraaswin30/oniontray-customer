package com.app.oniontray.CustomViews;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.LoginResponse;
import com.app.oniontray.RequestModels.RegNewOTPReq;
import com.app.oniontray.RequestModels.SendOTP;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 12/5/17.
 */

public class SignInOTPDialogView extends Dialog {

    private Context context;
    private LoginResponse loginResponse;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;


    private TextView reg_otp_dialog_title_txt_view;
    private TextView reg_otp_dialog_content_txt_view;

    private TextInputLayout reg_otp_txt_input_layout;
    private EditText reg_otp_edt_txt_view;
    private Button reg_otp_resend_btn;
    private Button reg_otp_verify_btn;

    private APIService apiService;


    public SignInOTPDialogView(@NonNull Context context, LoginResponse loginResponse) {
        super(context);
        this.context = context;
        this.loginResponse = loginResponse;
    }

    public SignInOTPDialogView(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SignInOTPDialogView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reg_otp_dialog_view_layout);
        setCanceledOnTouchOutside(false);

        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());

        reg_otp_txt_input_layout = (TextInputLayout) findViewById(R.id.reg_otp_txt_input_layout);
        reg_otp_edt_txt_view = (EditText) findViewById(R.id.reg_otp_edt_txt_view);
        reg_otp_resend_btn = (Button) findViewById(R.id.reg_otp_resend_btn);
        reg_otp_verify_btn = (Button) findViewById(R.id.reg_otp_verify_btn);



        reg_otp_dialog_title_txt_view = (TextView) findViewById(R.id.reg_otp_dialog_title_txt_view);
        reg_otp_dialog_content_txt_view = (TextView) findViewById(R.id.reg_otp_dialog_content_txt_view);
        reg_otp_resend_btn.setBackgroundColor(Color.parseColor(loginManager.getThemeColor()));
        reg_otp_resend_btn.setTextColor(Color.parseColor(loginManager.getThemeFontColor()));
        reg_otp_verify_btn.setTextColor(Color.parseColor(loginManager.getThemeFontColor()));
        reg_otp_verify_btn.setBackgroundColor(Color.parseColor(loginManager.getThemeColor()));

        apiService = Webdata.getRetrofit().create(APIService.class);

        reg_otp_resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPSendRequestMethod();
            }
        });

        reg_otp_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OTPVerifyRequestMethod();
            }
        });

    }


    private void OTPSendRequestMethod() {

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            apiService.RegReSendOTPReq(loginManager.getStringValue("Lang_code"),
                    "" + loginResponse.getUserId(), "" + loginResponse.getMobile()).enqueue(new Callback<RegNewOTPReq>() {
                @Override
                public void onResponse(Call<RegNewOTPReq> call, Response<RegNewOTPReq> response) {

                    try {

                        Log.e("onResponse", "" + response.raw().toString());

                        progressBarDialog.dismiss();

                        if (response != null) {

                            if (response.body().getResponse().getHttpCode() == 200) {

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
                public void onFailure(Call<RegNewOTPReq> call, Throwable t) {
                    progressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }

    private void OTPVerifyRequestMethod() {

        try {

            if (!ValidateOTPCode()) {
                return;
            }

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            apiService.SignUpOTPVerifyReq(loginManager.getStringValue("Lang_code"),
                    "" + loginResponse.getUserId(), "" + loginResponse.getMobile(),
                    reg_otp_edt_txt_view.getText().toString().trim()).enqueue(new Callback<SendOTP>() {
                @Override
                public void onResponse(Call<SendOTP> call, Response<SendOTP> response) {

                    try {

                        Log.e("RegOTPVerifyReq", "" + response.raw().toString());
                        progressBarDialog.dismiss();

                        if (response != null) {

                            if (response.body().getResponse().getHttpCode() == 200) {

                                Log.e("Success", "" + response.body().getResponse().getMessage());
                                reg_otp_edt_txt_view.setText("");

                                if (signInOTPDialogInterface != null) {
                                    signInOTPDialogInterface.OTPVerifiedSuccessMethod();
                                }

                                dismiss();

                            } else {
                                Log.e("sucess", "" + response.body().getResponse().getMessage());
                                reg_otp_edt_txt_view.setText("");
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

        } catch (Exception e) {
            Log.e("Exception", "-" + e.getMessage());
        }

    }

    private boolean ValidateOTPCode() {

        if (reg_otp_edt_txt_view.getText().toString().trim().isEmpty()) {
            reg_otp_txt_input_layout.setError(context.getString(R.string.reg_otp_err_msg_));
            requestFocus(reg_otp_edt_txt_view);
            return false;
        } else {
            reg_otp_txt_input_layout.setErrorEnabled(false);
        }

        return true;
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


    private SignInOTPDialogInterface signInOTPDialogInterface;

    public void CallSignInOTPVerifiedInterface(SignInOTPDialogInterface signInOTPDialogInterface) {
        this.signInOTPDialogInterface = signInOTPDialogInterface;
    }

    public interface SignInOTPDialogInterface {
        void OTPVerifiedSuccessMethod();
    }


}
