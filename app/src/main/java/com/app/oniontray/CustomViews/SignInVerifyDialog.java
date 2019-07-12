package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.LoginResponse;
import com.app.oniontray.RequestModels.RegNewOTPReq;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 12/5/17.
 */

public class SignInVerifyDialog extends Dialog {

    private Context context;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    private TextView signin_verify_layout_tit_txt;
    private TextView signin_verify_error_msg_txt_view;


    private Button signup_verify_btn;

    private LoginResponse loginResponse;
    private APIService apiService;


    public SignInVerifyDialog(@NonNull Context context, LoginResponse loginResponse) {
        super(context);
        this.context = context;
        this.loginResponse = loginResponse;
    }

    public SignInVerifyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected SignInVerifyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_sign_up_verify_layout);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.fb_gp_instru_background));
        this.setCanceledOnTouchOutside(false);


        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());
        apiService = Webdata.getRetrofit().create(APIService.class);


        signin_verify_layout_tit_txt = (TextView) findViewById(R.id.signin_verify_layout_tit_txt);
        signin_verify_error_msg_txt_view = (TextView) findViewById(R.id.signin_verify_error_msg_txt_view);

        signup_verify_btn = (Button) findViewById(R.id.signup_verify_btn);
        signup_verify_btn.setBackgroundColor(Color.parseColor(loginManager.getThemeColor()));
        signup_verify_btn.setTextColor(Color.parseColor(loginManager.getThemeFontColor()));
        signup_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SentOTPRequestMethod();

            }
        });

    }


    public void SentOTPRequestMethod() {

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

                                if (signInVerifyInterface != null) {
                                    dismiss();
                                    signInVerifyInterface.SentOTPSuccessMethod(loginResponse);
                                }

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


    @Override
    public void onBackPressed() {

        showDialogOK("" + context.getString(R.string.sign_in_verify_exit_txt), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        });

    }

    private void showDialogOK(String message, OnClickListener okListener) {
        new AlertDialog.Builder(context).setMessage(message).setPositiveButton("" + context.getString(R.string.sign_in_verify_send_btn), okListener)
                .setNegativeButton("" + context.getString(R.string.sign_in_verify_cancel_btn_txt), okListener).create().show();
    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    public SignInVerifyInterface signInVerifyInterface;

    public void CallSignInVerifyInterface(SignInVerifyInterface signInVerifyInterface) {
        this.signInVerifyInterface = signInVerifyInterface;
    }

    public interface SignInVerifyInterface {
        void SentOTPSuccessMethod(LoginResponse loginResponse);
    }


}
