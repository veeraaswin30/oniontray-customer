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
import com.app.oniontray.RequestModels.PassWordUpdResponse;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChangePasswordActivity extends LocalizationActivity {

    private Toolbar change_password_toolbar;

    private TextInputLayout chang_old_pass_txt_in_lay, chang_new_pass_txt_in_lay, chang_confirm_pass_txt_in_lay;

    private EditText chang_old_pass_edt_txt, chang_new_pass_edt_txt, chang_confirm_pass_edt_txt;

    private TextView change_password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        change_password = findViewById(R.id.change_password);
        change_password.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        change_password_toolbar = (Toolbar) findViewById(R.id.change_password_toolbar);
//        change_password_toolbar.setTitle("" + getString(R.string.change_password_txt));
        change_password_toolbar.setTitle("");
       // change_password_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        change_password_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        change_password_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(change_password_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        String language = String.valueOf(LanguageSetting.getLanguage(ChangePasswordActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        change_password_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        chang_old_pass_txt_in_lay = (TextInputLayout) findViewById(R.id.chang_old_pass_txt_in_lay);
        chang_old_pass_edt_txt = (EditText) findViewById(R.id.chang_old_pass_edt_txt);
        chang_old_pass_edt_txt.addTextChangedListener(new MyChangePassTextWatcher(chang_old_pass_edt_txt));

        chang_new_pass_txt_in_lay = (TextInputLayout) findViewById(R.id.chang_new_pass_txt_in_lay);
        chang_new_pass_edt_txt = (EditText) findViewById(R.id.chang_new_pass_edt_txt);
        chang_new_pass_edt_txt.addTextChangedListener(new MyChangePassTextWatcher(chang_new_pass_edt_txt));

        chang_confirm_pass_txt_in_lay = (TextInputLayout) findViewById(R.id.chang_confirm_pass_txt_in_lay);
        chang_confirm_pass_edt_txt = (EditText) findViewById(R.id.chang_confirm_pass_edt_txt);
        chang_confirm_pass_edt_txt.addTextChangedListener(new MyChangePassTextWatcher(chang_confirm_pass_edt_txt));

        Button change_pass_cancel_btn = (Button) findViewById(R.id.change_pass_cancel_btn);
        Button change_pass_update_btn = (Button) findViewById(R.id.change_pass_update_btn);

        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            chang_old_pass_edt_txt.setBackgroundTintList(colorStateList);
            chang_new_pass_edt_txt.setBackgroundTintList(colorStateList);
            chang_confirm_pass_edt_txt.setBackgroundTintList(colorStateList);
        }


        chang_old_pass_txt_in_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_new_pass_txt_in_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_confirm_pass_txt_in_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_old_pass_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_new_pass_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_confirm_pass_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        chang_confirm_pass_txt_in_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_new_pass_txt_in_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
        chang_old_pass_txt_in_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));





        change_pass_update_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        change_pass_update_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        change_pass_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        change_pass_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePasswordUpdateRequest();
            }
        });

    }


    private void changePasswordUpdateRequest() {

        hideKeyboard();

        if (!validateOldPassword()) {
            return;
        }

        if (!validateNewPassword()) {
            return;
        }

        if (!validateConfirmPassword()) {
            return;
        }

        if (progressDialog != null) {
            progressDialog.show();
        }

//        Log.e("user_token",""+prefManager.getStringValue("user_token"));
//        Log.e("user_id",""+prefManager.getStringValue("user_id"));
//        Log.e("chang_old_pass_edt_txt",""+chang_old_pass_edt_txt.getText().toString());
//        Log.e("chang_new_pass_edt_txt",""+chang_new_pass_edt_txt.getText().toString());
//        Log.e("chang_conf_pass_edt_txt",""+chang_confirm_pass_edt_txt.getText().toString());
//        Log.e("Lang_code",""+prefManager.getStringValue("Lang_code"));

        APIService update_password_request = Webdata.getRetrofit().create(APIService.class);
        update_password_request.update_password("" + loginPrefManager.getStringValue("user_token"),
                "" + loginPrefManager.getStringValue("user_id"),
                "" + chang_old_pass_edt_txt.getText().toString(),
                "" + chang_new_pass_edt_txt.getText().toString(),
                "" + chang_confirm_pass_edt_txt.getText().toString(),
                "" + loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<PassWordUpdResponse>() {
            @Override
            public void onResponse(Call<PassWordUpdResponse> call, Response<PassWordUpdResponse> response) {

//                Log.e("change passw_response", response.raw().toString());

                progressDialog.dismiss();

                try {
                    if (response.body().getResponse().getHttpCode() == 200) {

                        Toast.makeText(ChangePasswordActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        finish();

                    } else if (response.body().getResponse().getHttpCode() == 400) {

                        hideKeyboard();

                        Toast.makeText(ChangePasswordActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<PassWordUpdResponse> call, Throwable t) {
//                Log.e("onFailure", "" + t.getMessage());
                Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }

        });

    }

    private class MyChangePassTextWatcher implements TextWatcher {

        private final View view;

        private MyChangePassTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.chang_old_pass_edt_txt:
                    validateOldPassword();
                    break;
                case R.id.chang_new_pass_edt_txt:
                    validateNewPassword();
                    break;
                case R.id.chang_confirm_pass_edt_txt:
                    validateConfirmPassword();
                    break;
            }
        }
    }

    private boolean validateOldPassword() {
        if (chang_old_pass_edt_txt.getText().toString().trim().isEmpty()) {
            chang_old_pass_txt_in_lay.setError(getString(R.string.old_password_error_txt));
            requestFocus(chang_old_pass_edt_txt);
            return false;
        } else {

            if (!loginPrefManager.getStringValue("login_password").equals("" + chang_old_pass_edt_txt.getText().toString())) {
                chang_old_pass_txt_in_lay.setError(getString(R.string.old_password_miss_match_txt));
                requestFocus(chang_old_pass_edt_txt);
                return false;
            } else {
                chang_old_pass_txt_in_lay.setErrorEnabled(false);
            }
        }

        return true;
    }

    private boolean validateNewPassword() {
        if (chang_new_pass_edt_txt.getText().toString().trim().isEmpty()) {
            chang_new_pass_txt_in_lay.setError(getString(R.string.new_password_error_txt));
            requestFocus(chang_new_pass_edt_txt);
            return false;
        } else {
            chang_new_pass_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        if (chang_confirm_pass_edt_txt.getText().toString().trim().isEmpty()) {
            chang_confirm_pass_txt_in_lay.setError(getString(R.string.confirm_password_error_txt));
            requestFocus(chang_confirm_pass_edt_txt);
            return false;
        } else {
            if (!chang_confirm_pass_edt_txt.getText().toString().equals("" + chang_new_pass_edt_txt.getText().toString())) {
                chang_confirm_pass_txt_in_lay.setError(getString(R.string.confirm_password_miss_match_txt));
                requestFocus(chang_confirm_pass_edt_txt);
                return false;
            } else {
                chang_confirm_pass_txt_in_lay.setErrorEnabled(false);
            }
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
