package com.app.oniontray.Activites;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.oniontray.CustomViews.FaceBook_Instruction_Dialog;
import com.app.oniontray.CustomViews.G_Plus_phone_no_Dialog;
import com.app.oniontray.Fragments.SignInFragment;
import com.app.oniontray.Fragments.SignUpFragment;
import com.app.oniontray.Fragments.SignUpFragment.SignUpInterface;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.FaceBookRegReq;
import com.app.oniontray.RequestModels.FaceBookReq;
import com.app.oniontray.RequestModels.GPlusCheckReq;
import com.app.oniontray.RequestModels.Login;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.SocUserCredReqResp;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 2/17/2017.
 */

public class RestaurantSignInSignUpActivity extends LocalizationActivity implements GoogleApiClient.OnConnectionFailedListener,
        SignUpInterface, SignInFragment.SignInInterface, FaceBook_Instruction_Dialog.FB_Login_Dialog_Interface,
        G_Plus_phone_no_Dialog.GP_Login_Dialog_Interface {

    private Toolbar sign_in_toolbar;

    public static RadioGroup radio_group;
    public static RadioButton login_btn, sign_btn;

    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction = null;

    private Intent intent;

    private boolean mycart = false;
    private boolean myAccount = false;

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    CallbackManager callbackManager;
    LoginManager mLoginManager;
    AccessTokenTracker mAccessTokenTracker;
    private String fb_gender;


    private boolean proc_to_check = false;
    private OutletDetails outletDetails;
    private String vendor_id;


    private JSONObject face_book_JsonObject;
    private FaceBook_Instruction_Dialog faceBook_instruction_dialog;


    private GoogleSignInAccount acct;
    private G_Plus_phone_no_Dialog g_plus_phone_no_dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_sign_in);

        intializeView();

        toolbarBackPressEvent();

        googlePlusClientSetUp();

        facebookStuff();

        FaceBookRegisterCallBackMethod();

    }

    private void facebookStuff() {

        mLoginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            }
        };

    }

    private void googlePlusClientSetUp() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }


    private void intializeView() {

        radio_group = (RadioGroup) findViewById(R.id.radio_group);

        sign_in_toolbar = (Toolbar) findViewById(R.id.sign_in_toolbar);
        sign_in_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(sign_in_toolbar);

        intent = getIntent();

        if (intent.hasExtra("proc_to_check")) {
            proc_to_check = intent.getBooleanExtra("proc_to_check", true);
            outletDetails = (OutletDetails) getIntent().getSerializableExtra("outlet_details");
            vendor_id = getIntent().getStringExtra("vendor_id");
        }

        if (intent.hasExtra("FromMycart")) {
            mycart = intent.getBooleanExtra("FromMycart", true);
        }

        if (intent.hasExtra("from_my_account")) {
            myAccount = intent.getBooleanExtra("from_my_account", true);
        }

//        Log.e("mycart", "" + mycart);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


           String language = String.valueOf(LanguageSetting.getLanguage(RestaurantSignInSignUpActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        login_btn = (RadioButton) findViewById(R.id.login_btn);
       // login_btn.setBackgroundResource(R.drawable.btn_selector_for_login);
      //  sign_btn.setBackgroundResource(R.drawable.btn_selector_for_sign_up);
//        sign_btn.setChecked(false);
//        login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        login_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        sign_btn = (RadioButton) findViewById(R.id.sign_btn);
        sign_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        sign_btn.setBackgroundResource(R.drawable.btn_selector_for_sign_up);
        GradientDrawable gradientDrawable2= (GradientDrawable)sign_btn.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
        gradientDrawable2.setStroke(2, Color.parseColor(loginPrefManager.getThemeFontColor()));

        login_btn.setBackgroundResource(R.drawable.btn_selector_for_login);
        GradientDrawable gradientDrawable3= (GradientDrawable)login_btn.getBackground().getCurrent();
        gradientDrawable3.setColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        gradientDrawable3.setStroke(2, Color.parseColor(loginPrefManager.getThemeFontColor()));
        login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));






        if (login_btn.isChecked()) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{

                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    new int[] {

                            Color.BLACK //disabled
                            ,Color.parseColor(loginPrefManager.getThemeFontColor()) //enabled

                    }
            );
            login_btn.setButtonTintList(colorStateList);
            login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
            login_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
            login_btn.setBackgroundResource(R.drawable.btn_selector_for_login);
            sign_btn.setBackgroundResource(R.drawable.btn_selector_for_sign_up);
            GradientDrawable gradientDrawable= (GradientDrawable)sign_btn.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
            gradientDrawable.setStroke(2, Color.parseColor(loginPrefManager.getThemeFontColor()));

            signInFragmentCall();
        }

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.login_btn) {
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{

                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {

                                    Color.BLACK //disabled
                                    ,Color.parseColor(loginPrefManager.getThemeFontColor()) //enabled

                            }
                    );
                    login_btn.setButtonTintList(colorStateList);
                    sign_btn.setChecked(false);
                    login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    sign_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
                    login_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
//                    sign_btn.setTextColor(Color.BLACK);
//                    sign_btn.setBackgroundColor(Color.WHITE);
                    sign_btn.setBackgroundResource(R.drawable.btn_selector_for_sign_up);
                    GradientDrawable gradientDrawable= (GradientDrawable)sign_btn.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    gradientDrawable.setStroke(2, Color.parseColor(loginPrefManager.getThemeFontColor()));
                    signInFragmentCall();

                } else {
                    ColorStateList colorStateList = new ColorStateList(
                            new int[][]{

                                    new int[]{-android.R.attr.state_enabled}, //disabled
                                    new int[]{android.R.attr.state_enabled} //enabled
                            },
                            new int[] {

                                    Color.BLACK //disabled
                                    ,Color.parseColor(loginPrefManager.getThemeFontColor()) //enabled

                            }
                    );
                    sign_btn.setButtonTintList(colorStateList);
                    login_btn.setChecked(false);
                    login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
                    sign_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    sign_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
                    login_btn.setBackgroundResource(R.drawable.btn_selector_for_login);
                    GradientDrawable gradientDrawable= (GradientDrawable)login_btn.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    gradientDrawable.setStroke(2, Color.parseColor(loginPrefManager.getThemeFontColor()));
                    signUpFragmentCall();
                }
            }
        });


    }

    // Express Checkout Click Event interface
    @Override
    public void ExpressCheckOut() {
        Intent exp_Intent = new Intent(RestaurantSignInSignUpActivity.this, ExpressCheckoutActivity.class);
        exp_Intent.putExtra("outlet_details", outletDetails);
        exp_Intent.putExtra("vendor_id", vendor_id);
        startActivity(exp_Intent);
        finish();

    }

    private void toolbarBackPressEvent() {
        sign_in_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void signInFragmentCall() {
        SignInFragment signInFragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putBoolean("proc_to_check", proc_to_check);
        args.putBoolean("FromMycart", mycart);
        args.putBoolean("myAccount", myAccount);
//        Log.e("myCart", "" + mycart);
//        Log.e("myCart", "" + myAccount);
        signInFragment.setArguments(args);
        signInFragment.UpdateSignInCallMethod(RestaurantSignInSignUpActivity.this);
        getFragment(signInFragment);

    }

    private void signUpFragmentCall() {
        SignUpFragment signUpFragment = new SignUpFragment();
        signUpFragment.UpdateSignUpCallMethod(RestaurantSignInSignUpActivity.this);
        getFragment(signUpFragment);
    }

    public void getFragment(Fragment currentFragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, currentFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .disallowAddToBackStack().commit();
    }

    @Override
    public void UpdateSignUpMethod(int count) {
        login_btn.setChecked(true);
    }

    @Override
    public void ProcedToCheck() {

       /* Intent proceedToCheck = new Intent(RestaurantSignInSignUpActivity.this, ProceedToCheckActivity.class);
        proceedToCheck.putExtra("outlet_details", outletDetails);
        proceedToCheck.putExtra("vendor_id", vendor_id);
        startActivity(proceedToCheck);*/

        finish();

    }

    @Override
    public void UpdateSignInMethod(int count) {
        onBackPressed();
    }


    /////////////////////////////////////////   Face Book Login   //////////////////////////////////////


    // FaceBook Click Event Method
    @Override
    public void FacebookClickEvent() {
        handleFacebookLogin();
    }

    private void
    handleFacebookLogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            mLoginManager.logOut();
        } else {
            mAccessTokenTracker.startTracking();
            mLoginManager.logInWithReadPermissions(RestaurantSignInSignUpActivity.this, Arrays.asList("public_profile","email"));
        }

    }

    private void FaceBookRegisterCallBackMethod() {

        mLoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                try {

                                  Log.e("fb object", "response: " + object.toString());

                                    face_book_JsonObject = object;

                                    FaceBookAccVerifyReqMethod(object);

                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    Log.e("btnSignInWithFacebook", "" + e.getMessage());
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "picture.type(large), id, first_name, last_name, email, name, gender, link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("FacebookException", exception.getMessage());
            }
        });

    }

    private void FaceBookAccVerifyReqMethod(final JSONObject object) {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.CheckFaceBookLoginReqMethod("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + getString(R.string.user_type_fb), "" + object.getString("id")).enqueue(new Callback<FaceBookReq>() {
                @Override
                public void onResponse(Call<FaceBookReq> call, Response<FaceBookReq> response) {

                    try {

                        Log.e("CheckFaceBookLogReqMet", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {


                           /* if (object.getString("gender").equals("male")) {
                                fb_gender = "M";
                            } else {
                                fb_gender = "F";
                            }
*/
//                            Log.e("Facebook getCode", "- " + response.body().getResponse().getCode());


                            if (response.body().getResponse().getCode() == 0) {

                                if (!object.has("email")) {
                                    getEmailFromUser("" + object.getString("id"), "", "" + object.getString("name"), "" + object.getString("first_name"),
                                            "" + object.getString("last_name"), "" + fb_gender, "" + object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                } else {

                                    getEmailFromUser("" + object.getString("id"), "" + object.getString("email"), "" + object.getString("name"), "" + object.getString("first_name"),
                                            "" + object.getString("last_name"), "" + fb_gender, "" + object.getJSONObject("picture").getJSONObject("data").getString("url"));

                                }

                            } else {

                                /*if (object.getString("gender").equals("male")) {
                                    fb_gender = "M";
                                } else {
                                    fb_gender = "F";
                                }*/

//                                Log.e("gender = ", "" + fb_gender);

                                Bundle fb_bundle = new Bundle();
                                fb_bundle.putString("id", "" + object.getString("id"));
                                fb_bundle.putString("email_id", "" + response.body().getResponse().getEmail());
                                fb_bundle.putString("name", "" + object.getString("name"));
                                fb_bundle.putString("firstName", "" + object.getString("first_name"));
                                fb_bundle.putString("lastName", "" + object.getString("last_name"));
                                fb_bundle.putString("gender", "" + fb_gender);
                                fb_bundle.putString("piictureURL", "" + object.getJSONObject("picture").getJSONObject("data").getString("url"));
                                fb_bundle.putString("phone_no", "" + response.body().getResponse().getMobile());

                                FB_Ok_Button_Method(fb_bundle);

                            }

                        } else {

                            Toast.makeText(RestaurantSignInSignUpActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
//                        Log.e("onResponse", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FaceBookReq> call, Throwable t) {

                }
            });


        } catch (JSONException e) {
//            Log.e("CheckFaBoLoginReqMet", "" + e.getMessage());
        }

    }

    private void getEmailFromUser(final String id, final String email_id, final String name, final String firstName,
                                  final String lastName, final String gender, final String piictureURL) {

        Bundle bundle = new Bundle();
        bundle.putString("id", "" + id);
        bundle.putString("email_id", "" + email_id);
        bundle.putString("name", "" + name);
        bundle.putString("firstName", "" + firstName);
        bundle.putString("lastName", "" + lastName);
        bundle.putString("gender", "" + gender);
        bundle.putString("piictureURL", "" + piictureURL);

        faceBook_instruction_dialog = new FaceBook_Instruction_Dialog(RestaurantSignInSignUpActivity.this, bundle, false, 0, "");
        faceBook_instruction_dialog.FB_Login_Call_Back_Method(RestaurantSignInSignUpActivity.this);
        faceBook_instruction_dialog.show();

    }


    private void CheckFaceBookUserCredientials() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.CheckSocialUserCredientialsReqMethod("", "", "", "").enqueue(new Callback<SocUserCredReqResp>() {
                @Override
                public void onResponse(Call<SocUserCredReqResp> call, Response<SocUserCredReqResp> response) {

                    try {

                        if (response.body().getResponse().getHttpCode() == 200) {

                        } else {

                        }

                    } catch (Exception e) {
//                        Log.e("onResponse", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<SocUserCredReqResp> call, Throwable t) {

                }
            });

        } catch (Exception e) {
//            Log.e("CheckFaceBookUserCredi", "" + e.getMessage());
        }

    }


    @Override
    public void FB_Login_Cancel_Btn_Method() {

        if (AccessToken.getCurrentAccessToken() != null) {
            mLoginManager.logOut();
        }

        faceBook_instruction_dialog.dismiss();

    }

    @Override
    public void FB_Ok_Button_Method(Bundle bundle) {

//        Log.e("id", "-" + bundle.getString("id"));
//        Log.e("email_id", "-" + bundle.getString("email_id"));
//        Log.e("phone_no", "-" + bundle.getString("phone_no"));

        FBRegisterationRequest("" + bundle.getString("id"), "" + bundle.getString("email_id"),
                "" + bundle.getString("name"), "" + bundle.getString("firstName"), "" + bundle.getString("lastName"),
                "" + bundle.getString("gender"), "" + bundle.getString("piictureURL"), loginPrefManager.getStringValue("device_id"),
                loginPrefManager.getStringValue("device_token"), loginPrefManager.getStringValue("Lang_code"),
                "" + getString(R.string.login_type), "" + bundle.getString("phone_no"));

        faceBook_instruction_dialog.dismiss();

    }

    @Override
    public void FB_Verify_Email_Phone_Error_Method(Bundle bundle, Integer Error_Code, String error_msg) {

        faceBook_instruction_dialog.dismiss();

        faceBook_instruction_dialog = new FaceBook_Instruction_Dialog(RestaurantSignInSignUpActivity.this, bundle, true, Error_Code, "" + error_msg);
        faceBook_instruction_dialog.FB_Login_Call_Back_Method(RestaurantSignInSignUpActivity.this);
        faceBook_instruction_dialog.show();

    }

    private void FBRegisterationRequest(String facebook_id, String email, String name, String first_name,
                                        String last_name, String gender, String image_url, String device_id,
                                        String device_token, String language, String login_type_data, String phone_no) {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.facebook_signup_user("" + facebook_id, "" + email, "" + name, "" + first_name,
                    "" + last_name, "" + gender, image_url, "" + device_id, "" + device_token,
                    "" + language, getString(R.string.user_type_fb), "" + login_type_data, "" + phone_no).enqueue(new Callback<FaceBookRegReq>() {
                @Override
                public void onResponse(Call<FaceBookRegReq> call, Response<FaceBookRegReq> response) {

                    try {

                       Log.e("facebook_signup_user", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            loginPrefManager.setStringValue("login_email", "" + response.body().getResponse().getEmail());
                            loginPrefManager.setStringValue("login_password", "");

                            loginPrefManager.setStringValue("user_id", "" + response.body().getResponse().getUserId());
                            loginPrefManager.setStringValue("user_token", "" + response.body().getResponse().getToken());
                            loginPrefManager.setStringValue("user_email", "" + response.body().getResponse().getEmail());
                            loginPrefManager.setStringValue("user_name", "" + response.body().getResponse().getName());
                            loginPrefManager.setStringValue("user_social_title", "" + response.body().getResponse().getSocialTitle());
                            loginPrefManager.setStringValue("user_first_name", "" + response.body().getResponse().getFirstName());
                            loginPrefManager.setStringValue("user_last_name", "" + response.body().getResponse().getLastName());
                            loginPrefManager.setStringValue("user_image", "" + response.body().getResponse().getImage());
                            loginPrefManager.setStringValue("user_mobile", "" + response.body().getResponse().getMobile());
                            loginPrefManager.setStringValue("user_type", "" + getString(R.string.user_type_fb));

                            if (proc_to_check) {

                               /* Intent proceedToCheck = new Intent(RestaurantSignInSignUpActivity.this, ProceedToCheckActivity.class);
                                proceedToCheck.putExtra("outlet_details", outletDetails);
                                proceedToCheck.putExtra("vendor_id", vendor_id);
                                startActivity(proceedToCheck);*/

                                finish();

                            } else {
                                if (myAccount)
                                    LocalBroadcastManager.getInstance(RestaurantSignInSignUpActivity.this).sendBroadcast(new Intent("base_activity_receiver").putExtra("page_name", "2"));

                                finish();
                            }


                        } else if (response.body().getResponse().getHttpCode() == 400) {


                            if (AccessToken.getCurrentAccessToken() != null) {
                                mLoginManager.logOut();
                            }

                            loginPrefManager.setStringValue("login_email", "");
                            loginPrefManager.setStringValue("login_password", "");

                            loginPrefManager.setStringValue("user_id", "");
                            loginPrefManager.setStringValue("user_token", "");
                            loginPrefManager.setStringValue("user_email", "");
                            loginPrefManager.setStringValue("user_name", "");
                            loginPrefManager.setStringValue("user_social_title", "");
                            loginPrefManager.setStringValue("user_first_name", "");
                            loginPrefManager.setStringValue("user_last_name", "");
                            loginPrefManager.setStringValue("user_image", "");
                            loginPrefManager.setStringValue("user_mobile", "");
                            loginPrefManager.setStringValue("user_type", "");

                            showToast(response.body().getResponse().getMessage());

                        }

                    } catch (Exception e) {
                        Log.e("Facebook_signup_user", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<FaceBookRegReq> call, Throwable t) {
                    progressDialog.dismiss();
                    showToast(t.getMessage());
                }
            });

        } catch (Exception e) {
//            Log.e("FBRegisterationRequest", "" + e.getMessage().toString());
        }

    }


    ////////////////////////////////////////       Google Plus Login         //////////////////////////////////////


    @Override
    public void LoginSucess() {
        onBackPressed();
    }

    // Google Plus Click Event Method
    @Override
    public void GooglePlusClickEvent() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
//        Log.e("google plus", "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.e("googlePlus", "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();

            GooPlusAccVerifyReqMethod(acct);
        }
    }


    private void GooPlusAccVerifyReqMethod(final GoogleSignInAccount acct) {

        try {

//            Log.e("getId", "" + acct.getId());
//            Log.e("getEmail", "" + acct.getEmail());
//            Log.e("getDisplayName", "" + acct.getDisplayName());
//            Log.e("getGivenName", "" + acct.getGivenName());
//            Log.e("getFamilyName", "" + acct.getFamilyName());
//            Log.e("getIdToken", "" + acct.getIdToken());
//            Log.e("getPhotoUrl", "" + acct.getPhotoUrl());
//
//            Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
//            Log.e("device_id", "" + loginPrefManager.getStringValue("device_id"));
//            Log.e("device_token", "" + loginPrefManager.getStringValue("device_token"));
//            Log.e("user_type_google", "" + getString(R.string.user_type_google));
//            Log.e("login_type", "" + getString(R.string.login_type));


            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.CheckGooglePlusLoginReqMethod("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + getString(R.string.user_type_google), "" + acct.getId()).enqueue(new Callback<GPlusCheckReq>() {
                @Override
                public void onResponse(Call<GPlusCheckReq> call, Response<GPlusCheckReq> response) {

                    try {

                        Log.e("CheckGLogReqMet", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getCode() == 0) {

                                g_plus_phone_no_dialog = new G_Plus_phone_no_Dialog(RestaurantSignInSignUpActivity.this, 0, acct.getEmail(), "", "");
                                g_plus_phone_no_dialog.FB_Login_Call_Back_Method(RestaurantSignInSignUpActivity.this);
                                g_plus_phone_no_dialog.show();

                            } else {

                                loginWithGPlus(acct, "" + response.body().getResponse().getEmail(), "" + response.body().getResponse().getMobile());

                            }

                        } else {

                            Toast.makeText(RestaurantSignInSignUpActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        Log.e("onResponse", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GPlusCheckReq> call, Throwable t) {

                }
            });


        } catch (Exception e) {
            Log.e("CheckFaBoLoginReqMet", "" + e.getMessage());
        }

    }

    @Override
    public void GP_Login_Cancel_Btn_Method() {

        g_plus_phone_no_dialog.dismiss();

    }

    @Override
    public void GP_Ok_Button_Method(String email_id, String mobile_no) {

        loginWithGPlus(acct, email_id, mobile_no);
        g_plus_phone_no_dialog.dismiss();

    }

    @Override
    public void GP_Verify_Email_Phone_Error_Method(Integer error_coder, String email_id, String phone_no, String error_msg) {

        g_plus_phone_no_dialog.dismiss();

        g_plus_phone_no_dialog = new G_Plus_phone_no_Dialog(RestaurantSignInSignUpActivity.this, error_coder, email_id, "" + phone_no, error_msg);
        g_plus_phone_no_dialog.FB_Login_Call_Back_Method(RestaurantSignInSignUpActivity.this);
        g_plus_phone_no_dialog.show();

    }

    private void loginWithGPlus(GoogleSignInAccount acct, String email_id, String phone_no) {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.login_with_google_plus(loginPrefManager.getStringValue("Lang_code"),
                    acct.getId(), acct.getIdToken(), email_id, acct.getDisplayName(), getString(R.string.user_type_google),
                    acct.getGivenName(), acct.getFamilyName(), getString(R.string.login_type), loginPrefManager.getStringValue("device_id"),
                    loginPrefManager.getStringValue("device_token"), "" + acct.getPhotoUrl(), "" + phone_no).enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {

                    try {

                        Log.e("login_with_google_plus", "response" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            loginPrefManager.setStringValue("login_email", "" + response.body().getResponse().getEmail());
                            loginPrefManager.setStringValue("login_password", "");

                            loginPrefManager.setStringValue("user_id", "" + response.body().getResponse().getUserId());
                            loginPrefManager.setStringValue("user_token", "" + response.body().getResponse().getToken());
                            loginPrefManager.setStringValue("user_email", "" + response.body().getResponse().getEmail());
                            loginPrefManager.setStringValue("user_name", "" + response.body().getResponse().getName());
                            loginPrefManager.setStringValue("user_social_title", "" + response.body().getResponse().getSocialTitle());
                            loginPrefManager.setStringValue("user_first_name", "" + response.body().getResponse().getFirstName());
                            loginPrefManager.setStringValue("user_last_name", "" + response.body().getResponse().getLastName());
                            loginPrefManager.setStringValue("user_image", "" + response.body().getResponse().getImage());
                            loginPrefManager.setStringValue("user_mobile", "" + response.body().getResponse().getMobile());
                            loginPrefManager.setStringValue("user_type", "" + getString(R.string.user_type_google));


                            if (proc_to_check) {

                         /*       Intent proceedToCheck = new Intent(RestaurantSignInSignUpActivity.this, ProceedToCheckActivity.class);
                                proceedToCheck.putExtra("outlet_details", outletDetails);
                                proceedToCheck.putExtra("vendor_id", vendor_id);
                                startActivity(proceedToCheck);*/

                                finish();

                            } else {

                                if (myAccount)
                                    LocalBroadcastManager.getInstance(RestaurantSignInSignUpActivity.this).sendBroadcast(new Intent("base_activity_receiver").putExtra("page_name", "2"));

                                finish();

                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {

                            loginPrefManager.setStringValue("login_email", "");
                            loginPrefManager.setStringValue("login_password", "");

                            loginPrefManager.setStringValue("user_id", "");
                            loginPrefManager.setStringValue("user_token", "");
                            loginPrefManager.setStringValue("user_email", "");
                            loginPrefManager.setStringValue("user_name", "");
                            loginPrefManager.setStringValue("user_social_title", "");
                            loginPrefManager.setStringValue("user_first_name", "");
                            loginPrefManager.setStringValue("user_last_name", "");
                            loginPrefManager.setStringValue("user_image", "");
                            loginPrefManager.setStringValue("user_type", "");

                            showToast(response.body().getResponse().getMessage());

                        }


                    } catch (Exception e) {
                        Log.e("login_with_google_plus", "Exception" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });

        } catch (Exception e) {

        }
    }

    private void showToast(String message) {
        Toast.makeText(RestaurantSignInSignUpActivity.this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }


}
