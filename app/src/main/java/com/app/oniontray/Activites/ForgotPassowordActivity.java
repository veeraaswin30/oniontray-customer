package com.app.oniontray.Activites;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ForgotPassword;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassowordActivity extends LocalizationActivity {

    private Toolbar order_details_toolber;
    private TextView vender_title_txt;

    private TextInputLayout inputLayoutEmail;
    private EditText inputEmail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        order_details_toolber = (Toolbar) findViewById(R.id.order_details_toolber);
        order_details_toolber.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(order_details_toolber);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(ForgotPassowordActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        order_details_toolber.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        vender_title_txt = findViewById(R.id.vender_title_txt);
        vender_title_txt.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        inputEmail = (EditText) findViewById(R.id.input_email);

        inputEmail.addTextChangedListener(new ForgotPassowordActivity.MyTextWatcher(inputEmail));


        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inputEmail.setBackgroundTintList(colorStateList);

        }
        inputLayoutEmail.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
        inputEmail.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        inputLayoutEmail.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));

        inputLayoutEmail.setDefaultHintTextColor(colorStateList);

        inputLayoutEmail.setErrorTextColor(colorStateList);



//        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        Button btnSignIn = (Button) findViewById(R.id.btn_submit);
        btnSignIn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        btnSignIn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
                hideKeyboard();
            }
        });

//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

    }

    /**
     * Validating data
     */
    private void submit() {

        if (!validateEmail()) {
            return;
        }

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.forgot_password(inputEmail.getText().toString(), loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<ForgotPassword>() {
            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {

                progressDialog.dismiss();
                if (response.body().getResponse().getHttpCode() == 200) {
                    Toast.makeText(ForgotPassowordActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                    Intent ForgotPassword = new Intent(ForgotPassowordActivity.this, RestaurantSignInSignUpActivity.class);
//                    startActivity(ForgotPassword);
                    finish();
                } else if (response.body().getResponse().getHttpCode() == 400) {
                    hideKeyboard();
                    Toast.makeText(ForgotPassowordActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }


    private boolean validateEmail() {

        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private final View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;

            }
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();
        if (focusedView != null)

        {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }


}
