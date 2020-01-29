package com.app.oniontray.Activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CountrySpinner.Country;
import com.app.oniontray.CountrySpinner.CountryAdapter;
import com.app.oniontray.CountrySpinner.CountryCodes;
import com.app.oniontray.CountrySpinner.CustomPhoneNumberFormattingTextWatcher;
import com.app.oniontray.CountrySpinner.OnPhoneChangedListener;
import com.app.oniontray.CountrySpinner.PhoneUtils;
import com.app.oniontray.CustomViews.RegOTPDialogView;
import com.app.oniontray.Interface.RegOTPInterface;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.RequestModels.ProfImgUpdate;
import com.app.oniontray.RequestModels.RegNewOTPReq;
import com.bumptech.glide.Glide;
import com.app.oniontray.CustomViews.CircleImageView;
import com.app.oniontray.CustomViews.ImagePicker;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProfUpdResponse;
import com.app.oniontray.RequestModels.UserProfDet;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends LocalizationActivity {

    private Toolbar my_profile_toolbar;
    private TextView profile_title;

    private CircleImageView prof_image_view;

    private TextInputLayout prof_name_txt_in_lay, prof_email_txt_in_lay,
            prof_mobile_txt_in_lay;

    private EditText prof_name_edt_txt, prof_email_edt_txt, prof_mobile_edt_txt;


    private EditText prof_deta_email_txt_view;

    private RadioButton prof_male_radio_btn, prof_female_radio_btn;

    private String firstNeme = "";
    private String lastName = "";
    private String email = "";

    private String gender = "";


    private Button prof_change_pass_btn;
    private Button prof_update_btn;
    private CountryCodePicker ccpSignup;

    protected String mLastEnteredPhone;
    protected Spinner mSpinner;
    protected PhoneNumberUtil mPhoneNumberUtil = PhoneNumberUtil.getInstance();
    protected SparseArray<ArrayList<Country>> mCountriesMap = new SparseArray<ArrayList<Country>>();
    protected CountryAdapter mAdapter;

    static CountryCodes countryCodes = new CountryCodes();

    protected static final TreeSet<String> CANADA_CODES = countryCodes.getCanadaCodes();
    protected static final TreeSet<String> US_CODES = countryCodes.getUsCodes();
    protected static final TreeSet<String> DO_CODES = countryCodes.getDoCodes();
    protected static final TreeSet<String> PR_CODES = countryCodes.getPrCodes();

    protected static final ArrayList<String> ARABIC_COUNTRY_NAME = countryCodes.getArabicCountryNames();


    protected AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Country c = (Country) mSpinner.getItemAtPosition(position);
            if (mLastEnteredPhone != null && mLastEnteredPhone.startsWith(c.getCountryCodeStr())) {
                return;
            }

            prof_mobile_edt_txt.getText().clear();
            prof_mobile_edt_txt.getText().insert(prof_mobile_edt_txt.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            prof_mobile_edt_txt.setSelection(prof_mobile_edt_txt.length());
            mLastEnteredPhone = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    protected OnPhoneChangedListener mOnPhoneChangedListener = new OnPhoneChangedListener() {
        @Override
        public void onPhoneChanged(String phone) {
            try {
                mLastEnteredPhone = phone;
                Phonenumber.PhoneNumber p = mPhoneNumberUtil.parse(phone, null);
                ArrayList<Country> list = mCountriesMap.get(p.getCountryCode());
                Country country = null;
                if (list != null) {
                    if (p.getCountryCode() == 1) {
                        String num = String.valueOf(p.getNationalNumber());
                        if (num.length() >= 3) {
                            String code = num.substring(0, 3);
                            if (CANADA_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Canada has priority 1, US has priority 0
                                    if (c.getPriority() == 1) {
                                        country = c;
                                        break;
                                    }
                                }
                            } else if (DO_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Dominican Republic has priority 2
                                    if (c.getPriority() == 2) {
                                        country = c;
                                        break;
                                    }
                                }
                            } else if (PR_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Puerto Rico has priority 3
                                    if (c.getPriority() == 3) {
                                        country = c;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (country == null) {
                        for (Country c : list) {
                            if (c.getPriority() == 0) {
                                country = c;
                                break;
                            }
                        }
                    }
                }
                if (country != null) {
                    final int position = country.getNum();
                    mSpinner.post(new Runnable() {
                        @Override
                        public void run() {
                            mSpinner.setSelection(position);
                        }
                    });
                }
            } catch (NumberParseException ignore) {
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        my_profile_toolbar = (Toolbar) findViewById(R.id.my_profile_toolbar);
        my_profile_toolbar.setTitle("");
        my_profile_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        my_profile_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        my_profile_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(my_profile_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(ProfileActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        my_profile_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        prof_image_view = (CircleImageView) findViewById(R.id.prof_profile_image_view);
        CircleImageView my_prof_add_prof_pic_btn = (CircleImageView) findViewById(R.id.my_prof_add_prof_pic_btn);

        my_prof_add_prof_pic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.setMinQuality(200, 200);
                ImagePicker.pickImage(ProfileActivity.this, "" + getString(R.string.pick_image_intent_text));
            }
        });

        profile_title = (TextView) findViewById(R.id.profile_title);
        profile_title.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        profile_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        prof_name_txt_in_lay = (TextInputLayout) findViewById(R.id.prof_name_txt_in_lay);
        prof_name_edt_txt = (EditText) findViewById(R.id.prof_name_edt_txt);
        prof_name_edt_txt.addTextChangedListener(new ProfileActivity.MyProfTextWatcher(prof_name_edt_txt));

        prof_email_txt_in_lay = (TextInputLayout) findViewById(R.id.prof_email_txt_in_lay);
        prof_email_edt_txt = (EditText) findViewById(R.id.prof_email_edt_txt);
        prof_email_edt_txt.addTextChangedListener(new ProfileActivity.MyProfTextWatcher(prof_email_edt_txt));

        prof_deta_email_txt_view = (EditText) findViewById(R.id.prof_deta_email_txt_view);

        prof_mobile_txt_in_lay = (TextInputLayout) findViewById(R.id.prof_mobile_txt_in_lay);
        prof_mobile_edt_txt = (EditText) findViewById(R.id.prof_mobile_edt_txt);
//        prof_mobile_edt_txt.addTextChangedListener(new ProfileActivity.MyProfTextWatcher(prof_mobile_edt_txt));

        prof_male_radio_btn = (RadioButton) findViewById(R.id.prof_male_radio_btn);
        prof_female_radio_btn = (RadioButton) findViewById(R.id.prof_female_radio_btn);

        prof_change_pass_btn = (Button) findViewById(R.id.prof_change_pass_btn);
        prof_change_pass_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        prof_change_pass_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        prof_update_btn = (Button) findViewById(R.id.prof_update_btn);
        prof_update_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        prof_update_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        ccpSignup = findViewById(R.id.ccpSignup);
        ccpSignup.registerCarrierNumberEditText(prof_mobile_edt_txt);


        prof_change_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });

        prof_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndVerifyProfile();
            }
        });


        if (!loginPrefManager.getStringValue("user_type").equals("3")) {
            prof_change_pass_btn.setVisibility(View.GONE);
        }

//        ProfileImageUpdateMethod();

        ProfileInfoRequest();

//        Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//        Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mAdapter = new CountryAdapter(ProfileActivity.this);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);

        //initCodes(ProfileActivity.this);

        prof_mobile_edt_txt.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart > 0 && !Character.isDigit(c)) {
                        return "";
                    }
                }
                return null;
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void CheckAndVerifyProfile() {

        if (!validateName()) {
            return;
        }

        if (!validateLastName()) {
            return;
        }

        lastName = prof_email_edt_txt.getText().toString();

        if (!validateMobileNumber()) {
            return;
        }

        gender = "";

        if (prof_mobile_edt_txt.getText().toString().replaceAll("\\s+", "").equals("" + loginPrefManager.getStringValue("user_mobile"))) {
            profileUpdateRequest();
        } else {
            VerifyMobileNo(prof_mobile_edt_txt.getText().toString().replaceAll("\\s+", ""));
        }


    }


    private void VerifyMobileNo(final String phone_no) {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

//            Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
//            Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//            Log.e("prof_name", "" + prof_name_edt_txt.getText().toString());
//            Log.e("lastName", "" + lastName);
//            Log.e("phone_no", "" + phone_no);
//            Log.e("gender", "" + gender);
//            Log.e("device_id", "" + loginPrefManager.getStringValue("device_id"));
//            Log.e("device_token", "" + loginPrefManager.getStringValue("device_token"));
//            Log.e("email", "" + email);
//            Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.ProfEditReSendOTPReq(loginPrefManager.getStringValue("Lang_code"), loginPrefManager.getStringValue("user_id"),
                    "" + prof_name_edt_txt.getText().toString(), "" + lastName, phone_no, "" + gender,
                    loginPrefManager.getStringValue("device_id"), "" + loginPrefManager.getStringValue("device_token"),
                    "" + email, "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<RegNewOTPReq>() {
                @Override
                public void onResponse(Call<RegNewOTPReq> call, Response<RegNewOTPReq> response) {

                    try {

//                        Log.e("onResponse", "" + response.raw().toString());
                        progressDialog.dismiss();

                        if (response != null) {

                            if (response.body().getResponse().getHttpCode() == 200) {

                                showToast(response.body().getResponse().getMessage());

                                VerifyOTPDialogMethod(phone_no);

                            } else {
                                showToast(response.body().getResponse().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        progressDialog.dismiss();
//                        Log.e("Exception", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<RegNewOTPReq> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
        }

    }


    private void VerifyOTPDialogMethod(String phone_no) {

        loginPrefManager.setStringValue("temp_user_id", "" + loginPrefManager.getStringValue("user_id"));

        RegOTPDialogView regOTPDialogView = new RegOTPDialogView(ProfileActivity.this,
                "" + phone_no, "" + loginPrefManager.getStringValue("login_password"), 1, new RegOTPInterface() {
            @Override
            public void RegOTPReSendMethod() {
            }

            @Override
            public void RegOTPVerifyMethod(String message) {
                profileUpdateRequest();
            }
        });
        regOTPDialogView.show();

    }


    private void ProfileImageUpdateMethod() {

        if (loginPrefManager.getStringValue("user_image").length() != 0) {
            Glide.with(ProfileActivity.this).load("" + loginPrefManager.getStringValue("user_image")).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).error(R.color.app_background_color).into(prof_image_view);
        } else {
//            Log.e("user_image","-"+ loginPrefManager.getStringValue("user_image"));
            Glide.with(ProfileActivity.this).load(R.drawable.nav_menu_empty_user_prof).error(R.color.app_background_color).into(prof_image_view);
        }

    }


    // Profile information request call
    private void ProfileInfoRequest() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.user_Profile_Details("" + loginPrefManager.getStringValue("user_token"), "" + loginPrefManager.getStringValue("user_id")).enqueue(new Callback<UserProfDet>() {
            @Override
            public void onResponse(Call<UserProfDet> call, Response<UserProfDet> response) {
                progressDialog.dismiss();
//                Log.e("Profile Activity Det:", "" + response.raw().toString());
                if (response.body().getResponse().getHttpCode() == 200) {

                    prof_name_edt_txt.setText("" + response.body().getResponse().getUserData().get(0).getFirstName());
                    lastName = "" + response.body().getResponse().getUserData().get(0).getLastName();
                    email = "" + response.body().getResponse().getUserData().get(0).getEmail();

                    prof_email_edt_txt.setText("" + response.body().getResponse().getUserData().get(0).getLastName());

                    prof_deta_email_txt_view.setText("" + response.body().getResponse().getUserData().get(0).getEmail());

                    if (response.body().getResponse().getUserData().get(0).getMobile() != null) {
                        String mobile_no = "" + response.body().getResponse().getUserData().get(0).getMobile().replaceAll("\\s+", "");
                        prof_mobile_edt_txt.setText("" + mobile_no);
//                        validateMobile();
                    }

//                    String gen = "" + response.body().getResponse().getUserData().get(0).getGender();
//
//                    if (gen.equals("M")) {
//                        prof_male_radio_btn.setChecked(true);
////                        Log.e("gen", "" + gen);
//                    } else if (gen.equals("F")) {
//                        prof_female_radio_btn.setChecked(true);
////                        Log.e("gen", "" + gen);
//                    }

                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(ProfileActivity.this, response.body().getResponse().getStatus().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserProfDet> call, Throwable t) {
                progressDialog.dismiss();
//                Log.e("error", t.toString());
            }
        });
    }


    // Profile update request call
    private void profileUpdateRequest() {

//        Log.e("prof_mobile_edt_txt", "" + prof_mobile_edt_txt.getText().toString().replaceAll("\\s+", ""));

        if (!validateName()) {
            return;
        }

        if (!validateLastName()) {
            return;
        }

        lastName = prof_email_edt_txt.getText().toString();

        if (!validateMobileNumber()) {
            return;
        }

//        if (prof_male_radio_btn.isChecked()) {
//            gender = "M";
//        }
//
//        if (prof_female_radio_btn.isChecked()) {
//            gender = "F";
//        }

        gender = "";

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.update_profile("" + loginPrefManager.getStringValue("user_id"),
                "" + prof_name_edt_txt.getText().toString(), "" + lastName,
                "" + prof_mobile_edt_txt.getText().toString().replaceAll("\\s+", ""), "" + gender,
                "" + loginPrefManager.getStringValue("device_id"),
                "" + loginPrefManager.getStringValue("device_token"), "" + loginPrefManager.getStringValue("Lang_code"),
                "" + email, "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<ProfUpdResponse>() {
            @Override
            public void onResponse(Call<ProfUpdResponse> call, Response<ProfUpdResponse> response) {
                progressDialog.dismiss();
//                Log.e("Prof Act Upd", "" + response.raw().toString());
                if (response.body().getResponse().getHttpCode() == 200) {

                    loginPrefManager.setStringValue("user_first_name", "" + prof_name_edt_txt.getText().toString());
                    loginPrefManager.setStringValue("user_last_name", "" + prof_email_edt_txt.getText().toString());
                    loginPrefManager.setStringValue("user_mobile", "" + prof_mobile_edt_txt.getText().toString().replaceAll("\\s+", ""));

                    Toast.makeText(ProfileActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(ProfileActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ProfUpdResponse> call, Throwable t) {
                progressDialog.dismiss();
//                Log.e("error", t.toString());
            }
        });

    }


    // Profile image path getting intent activity result.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 234) {
            Bitmap bitmap = ImagePicker.getImageFromResult(this, requestCode, resultCode, data);
            File Imagefile = getOutputMediaFile(bitmap);

            if (bitmap != null) {
                prof_image_view.setImageBitmap(bitmap);
            }

            UpdateProfileImage(Imagefile);

        }

    }

    private File getOutputMediaFile(Bitmap photo) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/FoodBoy/profile/");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "profileimage-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            photo.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    // Profile image only update multipart request call
    private void UpdateProfileImage(File image_file) {

        if (progressDialog != null) {
            progressDialog.show();
        }

        try {

            if (!image_file.exists() && !image_file.canRead()) {
                return;
            }

            // add another part within the multipart request
            RequestBody language = RequestBody.create(MediaType.parse("multipart/form-data"), "" + loginPrefManager.getStringValue("Lang_code"));
            // add another part within the multipart request
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), "" + loginPrefManager.getStringValue("user_token"));
            // add another part within the multipart request
            RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), "" + loginPrefManager.getStringValue("user_id"));

            // create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            // MultipartBody.Part is used to send also the actual file name
            final MultipartBody.Part body = MultipartBody.Part.createFormData("image", image_file.getName(), requestFile);

            APIService profile_pic_update = Webdata.getRetrofit().create(APIService.class);
            profile_pic_update.UpdateProfilePicRequest(language, token, id, body).enqueue(new Callback<ProfImgUpdate>() {
                @Override
                public void onResponse(Call<ProfImgUpdate> call, Response<ProfImgUpdate> response) {

                    progressDialog.dismiss();
//                    Log.e("onResponse", "" + response.raw().toString());
                    if (response.body().getResponse().getHttpCode() == 200) {

                        loginPrefManager.setStringValue("user_image", "" + response.body().getResponse().getImage());

                        hideKeyboard();

                        Toast.makeText(ProfileActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getResponse().getHttpCode() == 400) {

                        hideKeyboard();

                        Toast.makeText(ProfileActivity.this, response.body().getResponse().getHttpCode(), Toast.LENGTH_SHORT).show();
                    }

                }


                @Override
                public void onFailure(Call<ProfImgUpdate> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", "" + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
//            Log.e("profilepic Excep", "" + e.getMessage());
        }

    }


    private class MyProfTextWatcher implements TextWatcher {
        private final View view;

        private MyProfTextWatcher(View view) {
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
                case R.id.prof_name_edt_txt:
                    validateName();
                    break;
                case R.id.prof_email_edt_txt:
                    validateLastName();
                    break;
                case R.id.prof_mobile_edt_txt:
                    validateMobile();
                    break;
            }
        }
    }

    private boolean validateName() {
        if (prof_name_edt_txt.getText().toString().trim().isEmpty()) {
            prof_name_txt_in_lay.setError(getString(R.string.err_msg_first_name));
            requestFocus(prof_name_edt_txt);
            return false;
        } else {
            prof_name_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLastName() {

        if (prof_email_edt_txt.getText().toString().trim().isEmpty()) {
            prof_email_txt_in_lay.setError(getString(R.string.err_msg_last_name));
            requestFocus(prof_email_edt_txt);
            return false;
        } else {
            prof_email_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobileNumber() {

        if (prof_mobile_edt_txt.getText().toString().trim().length() < 7) {
            prof_mobile_txt_in_lay.setError(getString(R.string.err_msg_mobile));
            requestFocus(prof_mobile_edt_txt);
            return false;
        } else {
            prof_mobile_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {

//        if (prof_mobile_edt_txt.getText().toString().trim().isEmpty()) {
//            prof_mobile_txt_in_lay.setError(getString(R.string.err_msg_mobile));
//            requestFocus(prof_mobile_edt_txt);
//            return false;
//        } else {
//            prof_mobile_txt_in_lay.setErrorEnabled(false);
//        }

        prof_mobile_edt_txt.setError(null);

        String phone = validate();

        if (phone == null) {
            requestFocus(prof_mobile_edt_txt);
            prof_mobile_txt_in_lay.setError(getString(R.string.label_error_incorrect_phone));
            return false;
        } else {
            prof_mobile_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }


    protected String validate() {

        String region = null;
        String phone = null;

        if (mLastEnteredPhone != null) {
            try {
                Phonenumber.PhoneNumber p = mPhoneNumberUtil.parse(mLastEnteredPhone, null);
                StringBuilder sb = new StringBuilder(16);
                sb.append('+').append(p.getCountryCode()).append(p.getNationalNumber());
                phone = sb.toString();
                region = mPhoneNumberUtil.getRegionCodeForNumber(p);
            } catch (NumberParseException ignore) {
            }
        }

        if (region != null) {
            return phone;
        } else {
            return null;
        }

    }


   /* protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
    }

    protected class AsyncPhoneInitTask extends AsyncTask<Void, Void, ArrayList<Country>> {

        private int mSpinnerPosition = 181;
        private Context mContext;

        public AsyncPhoneInitTask(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<Country> doInBackground(Void... params) {
            ArrayList<Country> data = new ArrayList<Country>(233);
            BufferedReader reader = null;
            try {

//                if(loginPrefManager.getStringValue("Lang").equals("en")){
//                    reader = new BufferedReader(new InputStreamReader(mContext.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));
//                }else{
//                    reader = new BufferedReader(new InputStreamReader(mContext.getApplicationContext().getAssets().open("countries_ar.dat"), "UTF-8"));
//                }

                reader = new BufferedReader(new InputStreamReader(mContext.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

                // do reading, usually loop until end of file reading
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    //process line
                    Country c = new Country(mContext, line, i);
                    data.add(c);
                    ArrayList<Country> list = mCountriesMap.get(c.getCountryCode());
                    if (list == null) {
                        list = new ArrayList<Country>();
                        mCountriesMap.put(c.getCountryCode(), list);
                    }
                    list.add(c);
                    i++;
                }
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
            if (!TextUtils.isEmpty(prof_mobile_edt_txt.getText())) {
                return data;
            }
            String countryRegion = PhoneUtils.getCountryRegionFromPhone(mContext);
            int code = mPhoneNumberUtil.getCountryCodeForRegion(countryRegion);
            ArrayList<Country> list = mCountriesMap.get(code);
            if (list != null) {
                for (Country c : list) {
                    if (c.getPriority() == 0) {
                        mSpinnerPosition = c.getNum();
                        break;
                    }
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Country> data) {
            mAdapter.addAll(data);
            if (mSpinnerPosition > 0) {
                mSpinner.setSelection(mSpinnerPosition);
            }

//            Log.e("mAdapter", "" + mAdapter.getCount());
//            Log.e("mCountriesMap", "-" + mCountriesMap.size());
//            Log.e("ARABIC_COUNTRY_NAME", "-" + ARABIC_COUNTRY_NAME.size());

        }
    }*/


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

    private void showToast(String message) {
        Toast.makeText(ProfileActivity.this, message, Toast.LENGTH_LONG).show();
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
