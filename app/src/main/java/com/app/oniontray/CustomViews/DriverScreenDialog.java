package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.oniontray.Activites.OrderDetailActivity;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.LoginResponse;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

/**
 * Created by nextbrain on 12/12/18.
 */

public class DriverScreenDialog extends Dialog {
    private Context context;

    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;

    private TextView signin_verify_layout_tit_txt;
    private TextView signin_verify_error_msg_txt_view;


    private Button btn_ok;

    private LoginResponse loginResponse;
    private APIService apiService;


    public DriverScreenDialog(@NonNull Context context, LoginResponse loginResponse) {
        super(context);
        this.context = context;
        this.loginResponse = loginResponse;
    }

    public DriverScreenDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected DriverScreenDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_driver_screen);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.fb_gp_instru_background));
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);


        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());
        apiService = Webdata.getRetrofit().create(APIService.class);


        signin_verify_layout_tit_txt = (TextView) findViewById(R.id.signin_verify_layout_tit_txt);
        signin_verify_error_msg_txt_view = (TextView) findViewById(R.id.signin_verify_error_msg_txt_view);

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SentOTPRequestMethod();

            }
        });

    }


    public void SentOTPRequestMethod() {

        context.startActivity(new Intent(context, OrderDetailActivity.class));


    }






}

