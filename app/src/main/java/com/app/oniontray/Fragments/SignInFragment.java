package com.app.oniontray.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.app.oniontray.Activites.BaseMenuTabActivity;
import com.app.oniontray.CustomViews.RegOTPDialogView;
import com.app.oniontray.Interface.RegOTPInterface;
import com.app.oniontray.RequestModels.RegNewOTPReq;
import com.app.oniontray.RequestModels.SendOTP;
import com.app.oniontray.RequestModels.SignUpresponse;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.graphics.drawable.DrawableCompat;

import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Activites.ForgotPassowordActivity;

import com.app.oniontray.Activites.PrivacyPolicyActivity;
import com.app.oniontray.Activites.TermsAndConditionsActivity;
import com.app.oniontray.CustomViews.SignInOTPDialogView;
import com.app.oniontray.CustomViews.SignInVerifyDialog;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Login;
import com.app.oniontray.RequestModels.LoginResponse;
import com.app.oniontray.RequestModels.VendorDetailForMyCart;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.facebook.CallbackManager;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInFragment extends Fragment implements SignInVerifyDialog.SignInVerifyInterface,
        SignInOTPDialogView.SignInOTPDialogInterface {

    private LoginPrefManager loginPrefMananger;
    private DDProgressBarDialog progressDialog;

    private TextInputLayout input_layout_email, input_layout_password;
    private EditText input_email, input_password;

    private TextView sign_in_reset_passsword;

    private Button sign_in_btn, express_checkout_btn;

    private Context context;
    private CallbackManager callbackManager;

    private String fb_gender;

    private CheckBox showPassword;
    private TextView sign_in_condition;

    private boolean myCart;
    private boolean myAccount;

    private Button signInButtonGplus, btnSignInWithFacebook, mSignInOtpBtn;

    private LinearLayout expressCheckoutLayout;

    private boolean proc_to_check = false;


    private ProductRespository productRespository;
    private ArrayList<String> outletAndVendorID;


    private SignInVerifyDialog signInVerifyDialog;

    private SignInOTPDialogView signInOTPDialogView;

    private SignUpFragment.SignUpInterface signUpInterface;


    private String mMob_num = "";
    private String mOtp = "";
    private String mUserId = "";

    private CountryCodePicker ccpSignup;
    private Dialog mviaOtpDialog;
    private EditText fb_mobile_det_txt;
    private Button mCancelBtn, mDoneBtn;
    private TextInputLayout fb_mobile_input_Layout;


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_signin, container, false);

        context = this.getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {

            if (bundle.containsKey("proc_to_check")) {
                proc_to_check = bundle.getBoolean("proc_to_check");
            }

            myCart = bundle.getBoolean("FromMycart");
            if (bundle.containsKey("myAccount"))
                myAccount = bundle.getBoolean("myAccount");
        }

//        Log.e("mycart", "" + myCart);
//        Log.e("myAccount", "" + myAccount);
//        Log.e("proc_to_check", "" + proc_to_check);

        loginPrefMananger = new LoginPrefManager(context);
        progressDialog = Webdata.getProgressBarDialog(context);


        productRespository = new ProductRespository();
        outletAndVendorID = productRespository.getVendorID();

//        Express  checkout layout
        expressCheckoutLayout = (LinearLayout) rootView.findViewById(R.id.express_checkout_layout);

        if (myCart) {
            expressCheckoutLayout.setVisibility(View.VISIBLE);
        } else {
            expressCheckoutLayout.setVisibility(View.GONE);
        }


        sign_in_btn = (Button) rootView.findViewById(R.id.sign_in_btn);
        sign_in_btn.setBackgroundColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
        sign_in_btn.setTextColor(Color.parseColor(loginPrefMananger.getThemeColor()));
        express_checkout_btn = (Button) rootView.findViewById(R.id.express_checkout_btn);
        mSignInOtpBtn = rootView.findViewById(R.id.mSignInOtpBtn);

        showPassword = (CheckBox) rootView.findViewById(R.id.show_password);

        input_layout_email = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        input_layout_password = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);

        input_email = (EditText) rootView.findViewById(R.id.input_email);

        input_password = (EditText) rootView.findViewById(R.id.input_password);

        sign_in_reset_passsword = (TextView) rootView.findViewById(R.id.sign_in_reset_passsword);
        sign_in_reset_passsword.setTextColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));

        input_email.addTextChangedListener(new MyTextWatcher(input_email));
        input_password.addTextChangedListener(new MyTextWatcher(input_password));

        mviaOtpDialog = new Dialog(context);
        mviaOtpDialog.setContentView(R.layout.dialog_login_via_otp);
        mviaOtpDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        mviaOtpDialog.setCancelable(false);
        fb_mobile_det_txt = mviaOtpDialog.findViewById(R.id.fb_mobile_det_txt);
        fb_mobile_input_Layout = mviaOtpDialog.findViewById(R.id.fb_mobile_input_Layout);
        mCancelBtn = mviaOtpDialog.findViewById(R.id.mCancelBtn);
        mDoneBtn = mviaOtpDialog.findViewById(R.id.mDoneBtn);
        ccpSignup = mviaOtpDialog.findViewById(R.id.ccpSignup);
        ccpSignup.registerCarrierNumberEditText(fb_mobile_det_txt);

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mviaOtpDialog.dismiss();
            }
        });

        mDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidPhoneNumber(fb_mobile_det_txt.getText().toString().trim().replace(" ", ""))) {
                    return;
                } else {
                    ApiRequestForSendOtp(fb_mobile_det_txt.getText().toString().trim().replace(" ", ""));
                }


            }
        });


//        input_layout_email.getEditText().getBackground().mutate().setColorFilter(Color.parseColor(loginPrefMananger.getThemeColor())), PorterDuff.Mode.SRC_ATOP));
//        input_layout_password.getEditText().getBackground().mutate().setColorFilter(loginPrefMananger.getThemeColor()), PorterDuff.Mode.SRC_ATOP);
//        input_layout_email.setBoxStrokeColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_layout_password.setBoxStrokeColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//
//        input_layout_email.setHintTextAppearance(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_layout_password.setHintTextAppearance(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_password.setHintTextColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//
//
//
//        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_layout_email.setBackgroundTintList(colorStateList);
//        input_layout_password.setBackgroundTintList(colorStateList);
//
//        input_layout_email.setDefaultHintTextColor(colorStateList);
//        input_layout_password.setDefaultHintTextColor(colorStateList);
//
//        input_layout_email.setErrorTextColor(colorStateList);
//        input_layout_password.setErrorTextColor(colorStateList);
//
//
//
//
//
//        setStyleForTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_password.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//            } else {
//                if(input_password.getText().length() == 0) {
//                    setStyleForTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//                }
//            }
//        });
//
//        setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//        input_email.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//            } else {
//                if(input_email.getText().length() == 0) {
//                    setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeFontColor()));
//                }
//            }
//        });


        // Set the dimensions of the sign-in button.
        signInButtonGplus = (Button) rootView.findViewById(R.id.btnGooglePlus);
        // Facebook button
        btnSignInWithFacebook = (Button) rootView.findViewById(R.id.btnFacebookLogin);


        input_email.setFilters(new InputFilter[]{new InputFilter.AllCaps() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return String.valueOf(source).toLowerCase();
            }
        }});


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForSignInRequest();
            }
        });

        mSignInOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mviaOtpDialog.show();
                //VerifyOTPDialogMethod(response.body().getResponse());
            }
        });


        express_checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyCartRequestMethod();

//                if (signInInterface != null) {
//                    signInInterface.ExpressCheckOut();
//                }

            }
        });

        sign_in_reset_passsword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset_password = new Intent(context, ForgotPassowordActivity.class);
                context.startActivity(reset_password);
            }
        });

        passwordVisibilityMethod();

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPassword.setText(getString(R.string.hint_hide_password));
                    input_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    showPassword.setText(getString(R.string.hint_show_password));
                    input_password.setInputType(129);

                }
            }
        });

        signInButtonGplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Click Event effect in activity through interface to use between two fragments
                signInInterface.GooglePlusClickEvent();

            }
        });

        btnSignInWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Click event for facebook login
                signInInterface.FacebookClickEvent();
            }
        });

        sign_in_condition = (TextView) rootView.findViewById(R.id.sign_in_condition);
        SpannableString terms_of_service = new SpannableString(Html.fromHtml(getResources().getString(R.string.hint_for_accept)));
        ClickableSpan terms_service = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent SignIntIntent = new Intent(context, TermsAndConditionsActivity.class);
                SignIntIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(SignIntIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan privacy_policy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent SignIntIntent = new Intent(context, PrivacyPolicyActivity.class);
                SignIntIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(SignIntIntent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        String language = String.valueOf(LanguageSetting.getLanguage(getContext()));


        if (language.equals("en")) {

            terms_of_service.setSpan(terms_service, 30, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(new ForegroundColorSpan(R.color.app_background_color), 30, 47, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(privacy_policy, 51, 66, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(new ForegroundColorSpan(R.color.app_background_color), 51, 66, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sign_in_condition.setText(terms_of_service);
            sign_in_condition.setMovementMethod(LinkMovementMethod.getInstance());
            sign_in_condition.setHighlightColor(Color.TRANSPARENT);

        } else {

            terms_of_service.setSpan(terms_service, 25, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(new ForegroundColorSpan(R.color.app_background_color), 25, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(privacy_policy, 46, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            terms_of_service.setSpan(new ForegroundColorSpan(R.color.app_background_color), 46, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sign_in_condition.setText(terms_of_service);
            sign_in_condition.setMovementMethod(LinkMovementMethod.getInstance());
            sign_in_condition.setHighlightColor(Color.TRANSPARENT);

        }

        return rootView;
    }

    private void MyCartRequestMethod() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

//        Log.e("user_id", "-" + loginManager.getStringValue("user_id"));
//        Log.e("user_token", "-" + loginManager.getStringValue("user_token"));
//        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(0));
//        Log.e("outletAndVendorID", "-" + outletAndVendorID.get(1));
//        Log.e("getCityID", "-" + loginManager.getCityID());
//        Log.e("getLocID", "-" + loginManager.getLocID());

            APIService MyCartService = Webdata.getRetrofit().create(APIService.class);

            MyCartService.getOutletDetails("" + loginPrefMananger.getStringValue("Lang_code"),
                    outletAndVendorID.get(0), outletAndVendorID.get(1), "" + loginPrefMananger.getCityID(),
                    "" + loginPrefMananger.getLocID()).enqueue(new Callback<VendorDetailForMyCart>() {
                @Override
                public void onResponse(Call<VendorDetailForMyCart> call, final Response<VendorDetailForMyCart> response) {

                    try {

                        progressDialog.dismiss();

//                        Log.e("getOutletDetails", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getOutletDetails().getOpenStatus() == 0) {
                                Toast.makeText(getContext(), "" + getString(R.string.restarent_was_closed_txt), Toast.LENGTH_SHORT).show();
                            } else {

                                if (signInInterface != null) {
                                    signInInterface.ExpressCheckOut();
                                }

                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(getContext(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("Exception", e.toString());
                    }

                }

                @Override
                public void onFailure(Call<VendorDetailForMyCart> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("Exception", t.toString());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
//            Log.e("Exception", "" + e.getMessage());
        }

    }


    private void passwordVisibilityMethod() {

        if (showPassword.isChecked()) {
            showPassword.setText(getString(R.string.hint_hide_password));
            input_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            input_layout_password.setErrorEnabled(false);
        } else {
            showPassword.setText(getString(R.string.hint_show_password));
            input_password.setInputType(129);

        }
    }

    private void ForSignInRequest() {

        if (!validateEmail() && !validatePassword()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        APIRequestForSignIn();

    }

    private void APIRequestForSignIn() {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

//            Log.e("email", input_email.getText().toString().trim());
//            Log.e("password", input_password.getText().toString().trim());
//            Log.e("login_type", "" + getString(R.string.login_type));
//            Log.e("user_type", "" + getString(R.string.user_type_normal));
//            Log.e("device_id", loginPrefMananger.getStringValue("device_id"));
//            Log.e("device_token", loginPrefMananger.getStringValue("device_token"));
//            Log.e("language", loginPrefMananger.getStringValue("Lang_code"));

            apiService.login_user(input_email.getText().toString().trim(),
                    input_password.getText().toString().trim(), "" + getString(R.string.login_type), getString(R.string.user_type_normal),
                    loginPrefMananger.getStringValue("device_id"), loginPrefMananger.getStringValue("device_token"),
                    loginPrefMananger.getStringValue("Lang_code")).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {

                    try {

                        progressDialog.dismiss();

//                        Log.e("login_user", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            loginPrefMananger.setStringValue("login_email", "" + input_email.getText().toString());
                            loginPrefMananger.setStringValue("login_password", "" + input_password.getText().toString());

                            loginPrefMananger.setStringValue("user_id", "" + response.body().getResponse().getUserId());
                            loginPrefMananger.setStringValue("user_token", "" + response.body().getResponse().getToken());
                            loginPrefMananger.setStringValue("user_email", "" + response.body().getResponse().getEmail());
                            loginPrefMananger.setStringValue("user_name", "" + response.body().getResponse().getName());
                            loginPrefMananger.setStringValue("user_first_name", "" + response.body().getResponse().getFirstName());
                            loginPrefMananger.setStringValue("user_last_name", "" + response.body().getResponse().getLastName());
                            loginPrefMananger.setStringValue("user_mobile", "" + response.body().getResponse().getMobile());
                            loginPrefMananger.setStringValue("user_type", "" + getString(R.string.user_type_normal));
                            loginPrefMananger.setStringValue("login_type", "" + getString(R.string.sign_up_login));

                            mUserId = response.body().getResponse().getUserId().toString();
                            //ApiRequestForSendOtp(response.body().getResponse().getUserId().toString());


                            if (proc_to_check) {

                                if (signInInterface != null) {
                                    signInInterface.ProcedToCheck();
                                }

                            } else {

                                if (signInInterface != null) {
                                    signInInterface.UpdateSignInMethod(1);
                                }

                                if (myAccount)
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("base_activity_receiver").putExtra("page_name", "2"));

                            }

                        } else {

                            if (response.body().getResponse().getPhoneVerify() == 0) {

                                if (response.body().getResponse().getUserId() != 0) {
                                    signInVerifyDialog = new SignInVerifyDialog(getContext(), response.body().getResponse());
                                    signInVerifyDialog.CallSignInVerifyInterface(SignInFragment.this);
                                    signInVerifyDialog.show();
                                } else {
                                    Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
//                        Log.e("Exception", e.getMessage());

                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
        }
    }


    /*RequestForSendOtp*/
    private void ApiRequestForSendOtp(String mobile) {
        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        if (progressDialog != null) {
            progressDialog.show();
        }

        apiService.login_Via_Otp(ccpSignup.getFullNumberWithPlus(),
                "" + getString(R.string.login_type), getString(R.string.user_type_normal),
                loginPrefMananger.getStringValue("device_id"), loginPrefMananger.getStringValue("device_token"),
                loginPrefMananger.getStringValue("Lang_code")).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                try {
                    progressDialog.dismiss();

                    if (response.body().getResponse().getHttpCode() == 200) {
                        mviaOtpDialog.dismiss();
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();
                        //mMob_num = response.body().getResponse().ge();
                        /// mOtp = response.body().getResponse().getOtp();
                        VerifyOTPDialogMethod(response.body().getResponse());
                    } else {
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
//                        Log.e("Exception", e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void mApiVerifyLoginOtp(String mMob_num, String mOtp) {
        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            apiService.mLoginVerifyOtp(mOtp, loginPrefMananger.getStringValue("device_token"),
                    loginPrefMananger.getStringValue("user_id"), "1",
                    loginPrefMananger.getStringValue("Lang_code")
            ).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {

                    try {

                        progressDialog.dismiss();

//                        Log.e("login_user", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {
                            loginPrefMananger.setStringValue("login_email", response.body().getResponse().getEmail());
                            loginPrefMananger.setStringValue("login_password", "" + input_password.getText().toString());

                            loginPrefMananger.setStringValue("user_id", "" + response.body().getResponse().getUserId());
                            loginPrefMananger.setStringValue("user_token", "" + response.body().getResponse().getToken());
                            loginPrefMananger.setStringValue("user_email", "" + response.body().getResponse().getEmail());
                            loginPrefMananger.setStringValue("user_name", "" + response.body().getResponse().getName());
                            loginPrefMananger.setStringValue("user_first_name", "" + response.body().getResponse().getFirstName());
                            loginPrefMananger.setStringValue("user_last_name", "" + response.body().getResponse().getLastName());
                            loginPrefMananger.setStringValue("user_mobile", "" + response.body().getResponse().getMobile());
                            loginPrefMananger.setStringValue("user_type", "" + getString(R.string.user_type_normal));
                            loginPrefMananger.setStringValue("login_type", "" + getString(R.string.sign_up_login));

                            if (proc_to_check) {

                                if (signInInterface != null) {
                                    signInInterface.ProcedToCheck();
                                }

                            } else {

                                if (signInInterface != null) {
                                    signInInterface.UpdateSignInMethod(1);
                                }

                                if (myAccount)
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent("base_activity_receiver").putExtra("page_name", "2"));

                            }

                        } else {

                            if (response.body().getResponse().getPhoneVerify() == 0) {

                                if (response.body().getResponse().getUserId() != 0) {
                                    signInVerifyDialog = new SignInVerifyDialog(getContext(), response.body().getResponse());
                                    signInVerifyDialog.CallSignInVerifyInterface(SignInFragment.this);
                                    signInVerifyDialog.show();
                                } else {
                                    Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                    } catch (Exception e) {
//                        Log.e("Exception", e.getMessage());

                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
        }

    }

    private void VerifyOTPDialogMethod(final LoginResponse signUpresponse) {

        RegOTPDialogView regOTPDialogView = new RegOTPDialogView(getContext(),
                "" + input_email.getText().toString().replaceAll("\\s+", ""),
                "" + input_password.getText().toString().trim(), 0, new RegOTPInterface() {
            @Override
            public void RegOTPReSendMethod() {
            }

            @Override
            public void RegOTPVerifyMethod(String message) {

                if (signUpresponse != null) {

                    loginPrefMananger.setStringValue("login_email", "" + input_email.getText().toString());
                    loginPrefMananger.setStringValue("login_password", "" + input_password.getText().toString());

                    loginPrefMananger.setStringValue("user_id", "" + signUpresponse.getUserId());
                    loginPrefMananger.setStringValue("user_token", "" + signUpresponse.getToken());
                    loginPrefMananger.setStringValue("user_email", "" + signUpresponse.getEmail());
                    loginPrefMananger.setStringValue("user_name", "" + signUpresponse.getName());
                    loginPrefMananger.setStringValue("user_first_name", "" + signUpresponse.getFirstName());
                    loginPrefMananger.setStringValue("user_last_name", "" + signUpresponse.getLastName());
                    loginPrefMananger.setStringValue("user_mobile", "" + signUpresponse.getMobile());
                    loginPrefMananger.setStringValue("user_type", "" + getString(R.string.user_type_normal));
                    loginPrefMananger.setStringValue("login_type", "" + getString(R.string.sign_up_login));

                    //showToast(message);

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(context, BaseMenuTabActivity.class);
                    context.startActivity(intent);
                    getActivity().finish();

                }

            }
        });
        regOTPDialogView.show();

    }


    private boolean validateEmail() {

        String email = input_email.getText().toString().trim();

        if (isValidEmailId(email)) {
            input_layout_email.setErrorEnabled(false);
            return true;
        } else {
            if (isValidPhoneNumber(email)) {
                input_layout_email.setErrorEnabled(false);
                return true;
            } else {
                input_layout_email.setError(getString(R.string.err_msg_email_or_phone_no_txt));
                requestFocus(input_email);
                return false;
            }
        }
    }

    private boolean isValidEmailId(CharSequence email) {
        if (!TextUtils.isEmpty(email)) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
        return false;
    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {

        if (phoneNumber.toString().trim().length() < 10) {
            return false;
        } else if (phoneNumber.toString().trim().length() > 10) {
            return false;
        } else if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }

        return false;
    }


    private boolean validatePassword() {
        if (input_password.getText().toString().trim().isEmpty()) {
            input_layout_password.setError(getString(R.string.err_msg_password));
            requestFocus(input_password);
            showPassword.setVisibility(View.GONE);
            return false;
        } else if (input_password.getText().toString().length() < 5) {
            input_layout_password.setError(getString(R.string.err_msg_password_character));
            requestFocus(input_password);
            showPassword.setVisibility(View.VISIBLE);
            return false;
        } else {
            input_layout_password.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
//            getWindow().getDecorView().clearFocus();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void SentOTPSuccessMethod(LoginResponse loginResponse) {

        signInOTPDialogView = new SignInOTPDialogView(getContext(), loginResponse);
        signInOTPDialogView.CallSignInOTPVerifiedInterface(SignInFragment.this);
        signInOTPDialogView.show();

    }

    @Override
    public void OTPVerifiedSuccessMethod() {
        APIRequestForSignIn();
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
                case R.id.input_password:
                    validatePassword();
                    break;

            }
        }

    }


    public interface SignInInterface {

        void UpdateSignInMethod(int count);

        void GooglePlusClickEvent();

        void FacebookClickEvent();

        void ProcedToCheck();

        void ExpressCheckOut();
    }

    private SignInInterface signInInterface;

    public void UpdateSignInCallMethod(SignInInterface signInInterface) {
        this.signInInterface = signInInterface;
    }


//    hasLatitude=true,latitude=12.9077962,hasLongitude=true,longitude=77.5869782


    public void setStyleForTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_password.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_password.setBackgroundDrawable(wrappedDrawable);
    }

    public void setStyleForemailTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_email.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_email.setBackgroundDrawable(wrappedDrawable);
    }

    private boolean validateMobileNumber() {

        if (fb_mobile_det_txt.getText().toString().trim().length() < 10) {
            fb_mobile_input_Layout.setError(getString(R.string.err_msg_mobile));
            requestFocus(fb_mobile_det_txt);
            return false;
        } else if (fb_mobile_det_txt.getText().toString().trim().length() > 10) {
            fb_mobile_input_Layout.setError(getString(R.string.err_msg_mobile));
            requestFocus(fb_mobile_det_txt);
            return false;
        } else {
            fb_mobile_input_Layout.setErrorEnabled(false);
        }

        return true;
    }


}
