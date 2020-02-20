package com.app.oniontray.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.app.oniontray.Activites.RestaurantSignInSignUpActivity;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.Fragment;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.app.oniontray.Activites.PrivacyPolicyActivity;
import com.app.oniontray.Activites.TermsAndConditionsActivity;

import com.app.oniontray.CountrySpinner.Country;
import com.app.oniontray.CountrySpinner.CountryAdapter;
import com.app.oniontray.CountrySpinner.CustomPhoneNumberFormattingTextWatcher;
import com.app.oniontray.CountrySpinner.OnPhoneChangedListener;
import com.app.oniontray.CountrySpinner.PhoneUtils;
import com.app.oniontray.CustomViews.RegOTPDialogView;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.RegOTPInterface;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.SignUpresponse;
import com.app.oniontray.RequestModels.Signup;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 2/17/2017.
 */

public class SignUpFragment extends Fragment {

    private LoginPrefManager loginPrefMananger;
    private DDProgressBarDialog progressDialog;

    private TextInputLayout input_layout_email, input_layout_password, input_layout_first_name, input_layout_last_name, input_layout_phone;
    private EditText input_email, input_password, input_phone, input_first_name, input_last_name;

    private TextView terms_and_condition;
    private TextView sign_in_condition;

    private CheckBox input_i_agree;

    private RadioGroup signup_gender_radio_group;
    private RadioButton signup_female_radio_btn, signup_male_radio_btn;

    private Button sign_in_btn, btnSignInWithFacebook, signInButtonGplus;
    private String gender_value;

    private CheckBox showPassword;

    private Context context;
    private String fb_gender;

    protected String mLastEnteredPhone;
    protected Spinner mSpinner;
    protected PhoneNumberUtil mPhoneNumberUtil = PhoneNumberUtil.getInstance();
    protected SparseArray<ArrayList<Country>> mCountriesMap = new SparseArray<ArrayList<Country>>();
    protected CountryAdapter mAdapter;
    private CountryCodePicker ccpSignup;
    private FragmentManager fragmentManager = null;
    private boolean proc_to_check = false;
    private boolean mycart = false;
    private boolean myAccount = false;


    protected static final TreeSet<String> CANADA_CODES = new TreeSet<String>();
    protected static final TreeSet<String> US_CODES = new TreeSet<String>();
    protected static final TreeSet<String> DO_CODES = new TreeSet<String>();
    protected static final TreeSet<String> PR_CODES = new TreeSet<String>();

    static {
        //USA
        US_CODES.add("201");
        US_CODES.add("202");
        US_CODES.add("203");
        US_CODES.add("205");
        US_CODES.add("206");
        US_CODES.add("207");
        US_CODES.add("208");
        US_CODES.add("209");
        US_CODES.add("210");
        US_CODES.add("212");
        US_CODES.add("213");
        US_CODES.add("214");
        US_CODES.add("215");
        US_CODES.add("216");
        US_CODES.add("217");
        US_CODES.add("218");
        US_CODES.add("219");
        US_CODES.add("224");
        US_CODES.add("225");
        US_CODES.add("228");
        US_CODES.add("229");
        US_CODES.add("231");
        US_CODES.add("234");
        US_CODES.add("239");
        US_CODES.add("240");
        US_CODES.add("248");
        US_CODES.add("251");
        US_CODES.add("252");
        US_CODES.add("253");
        US_CODES.add("254");
        US_CODES.add("256");
        US_CODES.add("260");
        US_CODES.add("262");
        US_CODES.add("267");
        US_CODES.add("269");
        US_CODES.add("270");
        US_CODES.add("276");
        US_CODES.add("281");
        US_CODES.add("301");
        US_CODES.add("302");
        US_CODES.add("303");
        US_CODES.add("304");
        US_CODES.add("305");
        US_CODES.add("307");
        US_CODES.add("308");
        US_CODES.add("309");
        US_CODES.add("310");
        US_CODES.add("312");
        US_CODES.add("313");
        US_CODES.add("314");
        US_CODES.add("315");
        US_CODES.add("316");
        US_CODES.add("317");
        US_CODES.add("318");
        US_CODES.add("319");
        US_CODES.add("320");
        US_CODES.add("321");
        US_CODES.add("323");
        US_CODES.add("325");
        US_CODES.add("330");
        US_CODES.add("334");
        US_CODES.add("336");
        US_CODES.add("337");
        US_CODES.add("339");
        US_CODES.add("347");
        US_CODES.add("351");
        US_CODES.add("352");
        US_CODES.add("360");
        US_CODES.add("361");
        US_CODES.add("386");
        US_CODES.add("401");
        US_CODES.add("402");
        US_CODES.add("404");
        US_CODES.add("405");
        US_CODES.add("406");
        US_CODES.add("407");
        US_CODES.add("408");
        US_CODES.add("409");
        US_CODES.add("410");
        US_CODES.add("412");
        US_CODES.add("413");
        US_CODES.add("414");
        US_CODES.add("415");
        US_CODES.add("417");
        US_CODES.add("419");
        US_CODES.add("423");
        US_CODES.add("425");
        US_CODES.add("430");
        US_CODES.add("432");
        US_CODES.add("434");
        US_CODES.add("435");
        US_CODES.add("440");
        US_CODES.add("443");
        US_CODES.add("469");
        US_CODES.add("478");
        US_CODES.add("479");
        US_CODES.add("480");
        US_CODES.add("484");
        US_CODES.add("501");
        US_CODES.add("502");
        US_CODES.add("503");
        US_CODES.add("504");
        US_CODES.add("505");
        US_CODES.add("507");
        US_CODES.add("508");
        US_CODES.add("509");
        US_CODES.add("510");
        US_CODES.add("512");
        US_CODES.add("513");
        US_CODES.add("515");
        US_CODES.add("516");
        US_CODES.add("517");
        US_CODES.add("518");
        US_CODES.add("520");
        US_CODES.add("530");
        US_CODES.add("540");
        US_CODES.add("541");
        US_CODES.add("551");
        US_CODES.add("559");
        US_CODES.add("561");
        US_CODES.add("562");
        US_CODES.add("563");
        US_CODES.add("567");
        US_CODES.add("570");
        US_CODES.add("571");
        US_CODES.add("573");
        US_CODES.add("574");
        US_CODES.add("575");
        US_CODES.add("580");
        US_CODES.add("585");
        US_CODES.add("586");
        US_CODES.add("601");
        US_CODES.add("602");
        US_CODES.add("603");
        US_CODES.add("605");
        US_CODES.add("606");
        US_CODES.add("607");
        US_CODES.add("608");
        US_CODES.add("609");
        US_CODES.add("610");
        US_CODES.add("612");
        US_CODES.add("614");
        US_CODES.add("615");
        US_CODES.add("616");
        US_CODES.add("617");
        US_CODES.add("618");
        US_CODES.add("619");
        US_CODES.add("620");
        US_CODES.add("623");
        US_CODES.add("626");
        US_CODES.add("630");
        US_CODES.add("631");
        US_CODES.add("636");
        US_CODES.add("641");
        US_CODES.add("646");
        US_CODES.add("650");
        US_CODES.add("651");
        US_CODES.add("660");
        US_CODES.add("661");
        US_CODES.add("662");
        US_CODES.add("678");
        US_CODES.add("682");
        US_CODES.add("701");
        US_CODES.add("702");
        US_CODES.add("703");
        US_CODES.add("704");
        US_CODES.add("706");
        US_CODES.add("707");
        US_CODES.add("708");
        US_CODES.add("712");
        US_CODES.add("713");
        US_CODES.add("714");
        US_CODES.add("715");
        US_CODES.add("716");
        US_CODES.add("717");
        US_CODES.add("718");
        US_CODES.add("719");
        US_CODES.add("720");
        US_CODES.add("724");
        US_CODES.add("727");
        US_CODES.add("731");
        US_CODES.add("732");
        US_CODES.add("734");
        US_CODES.add("740");
        US_CODES.add("754");
        US_CODES.add("757");
        US_CODES.add("760");
        US_CODES.add("763");
        US_CODES.add("765");
        US_CODES.add("770");
        US_CODES.add("772");
        US_CODES.add("773");
        US_CODES.add("774");
        US_CODES.add("775");
        US_CODES.add("781");
        US_CODES.add("785");
        US_CODES.add("786");
        US_CODES.add("801");
        US_CODES.add("802");
        US_CODES.add("803");
        US_CODES.add("804");
        US_CODES.add("805");
        US_CODES.add("806");
        US_CODES.add("808");
        US_CODES.add("810");
        US_CODES.add("812");
        US_CODES.add("813");
        US_CODES.add("814");
        US_CODES.add("815");
        US_CODES.add("816");
        US_CODES.add("817");
        US_CODES.add("818");
        US_CODES.add("828");
        US_CODES.add("830");
        US_CODES.add("831");
        US_CODES.add("832");
        US_CODES.add("843");
        US_CODES.add("845");
        US_CODES.add("847");
        US_CODES.add("848");
        US_CODES.add("850");
        US_CODES.add("856");
        US_CODES.add("857");
        US_CODES.add("858");
        US_CODES.add("859");
        US_CODES.add("860");
        US_CODES.add("862");
        US_CODES.add("863");
        US_CODES.add("864");
        US_CODES.add("865");
        US_CODES.add("866");
        US_CODES.add("870");
        US_CODES.add("901");
        US_CODES.add("903");
        US_CODES.add("904");
        US_CODES.add("906");
        US_CODES.add("907");
        US_CODES.add("908");
        US_CODES.add("909");
        US_CODES.add("910");
        US_CODES.add("912");
        US_CODES.add("913");
        US_CODES.add("914");
        US_CODES.add("915");
        US_CODES.add("916");
        US_CODES.add("917");
        US_CODES.add("918");
        US_CODES.add("919");
        US_CODES.add("920");
        US_CODES.add("925");
        US_CODES.add("928");
        US_CODES.add("931");
        US_CODES.add("936");
        US_CODES.add("937");
        US_CODES.add("940");
        US_CODES.add("941");
        US_CODES.add("947");
        US_CODES.add("949");
        US_CODES.add("951");
        US_CODES.add("952");
        US_CODES.add("954");
        US_CODES.add("956");
        US_CODES.add("970");
        US_CODES.add("971");
        US_CODES.add("972");
        US_CODES.add("973");
        US_CODES.add("978");
        US_CODES.add("979");
        US_CODES.add("980");
        US_CODES.add("985");
        US_CODES.add("989");

        //Dominican Republic
        DO_CODES.add("809");
        DO_CODES.add("829");
        DO_CODES.add("849");

        //Puerto Rico
        PR_CODES.add("787");
        PR_CODES.add("939");

        //Canada
        CANADA_CODES.add("204");
        CANADA_CODES.add("226");
        CANADA_CODES.add("236");
        CANADA_CODES.add("249");
        CANADA_CODES.add("250");
        CANADA_CODES.add("289");
        CANADA_CODES.add("306");
        CANADA_CODES.add("343");
        CANADA_CODES.add("365");
        CANADA_CODES.add("387");
        CANADA_CODES.add("403");
        CANADA_CODES.add("416");
        CANADA_CODES.add("418");
        CANADA_CODES.add("431");
        CANADA_CODES.add("437");
        CANADA_CODES.add("438");
        CANADA_CODES.add("450");
        CANADA_CODES.add("506");
        CANADA_CODES.add("514");
        CANADA_CODES.add("519");
        CANADA_CODES.add("548");
        CANADA_CODES.add("579");
        CANADA_CODES.add("581");
        CANADA_CODES.add("587");
        CANADA_CODES.add("604");
        CANADA_CODES.add("613");
        CANADA_CODES.add("639");
        CANADA_CODES.add("647");
        CANADA_CODES.add("672");
        CANADA_CODES.add("705");
        CANADA_CODES.add("709");
        CANADA_CODES.add("742");
        CANADA_CODES.add("778");
        CANADA_CODES.add("780");
        CANADA_CODES.add("782");
        CANADA_CODES.add("807");
        CANADA_CODES.add("819");
        CANADA_CODES.add("825");
        CANADA_CODES.add("867");
        CANADA_CODES.add("873");
        CANADA_CODES.add("902");
        CANADA_CODES.add("905");
    }

    protected AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//            Log.e("mOnItemSelectedListener", "" + position);

            Country c = (Country) mSpinner.getItemAtPosition(position);
            if (mLastEnteredPhone != null && mLastEnteredPhone.startsWith(c.getCountryCodeStr())) {
                return;
            }

            input_phone.getText().clear();
            // input_phone.getText().insert(input_phone.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            input_phone.setSelection(input_phone.length());
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


    private SignUpresponse signUpresponse;


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.restaurant_signup, container, false);

        context = this.getActivity();

        mAdapter = new CountryAdapter(getActivity());

        loginPrefMananger = new LoginPrefManager(context);
        progressDialog = Webdata.getProgressBarDialog(context);

        Bundle bundle = getArguments();
        if (bundle != null) {

            if (bundle.containsKey("proc_to_check")) {
                proc_to_check = bundle.getBoolean("proc_to_check");
            }

            mycart = bundle.getBoolean("FromMycart");
            if (bundle.containsKey("myAccount"))
                myAccount = bundle.getBoolean("myAccount");
        }

        /*mSpinner = (Spinner) rootView.findViewById(R.id.spinner);
        mAdapter = new CountryAdapter(getActivity());
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);*/


        // initCodes(getActivity());

        sign_in_btn = (Button) rootView.findViewById(R.id.sign_in_btn);
        sign_in_btn.setBackgroundColor(Color.parseColor(loginPrefMananger.getThemeFontColor()));
        sign_in_btn.setTextColor(Color.parseColor(loginPrefMananger.getThemeColor()));

        input_i_agree = (CheckBox) rootView.findViewById(R.id.input_i_agree);

        signup_female_radio_btn = (RadioButton) rootView.findViewById(R.id.signup_female_radio_btn);
        signup_male_radio_btn = (RadioButton) rootView.findViewById(R.id.signup_male_radio_btn);

        showPassword = (CheckBox) rootView.findViewById(R.id.show_password);

        terms_and_condition = (TextView) rootView.findViewById(R.id.terms_and_condition);
        sign_in_condition = (TextView) rootView.findViewById(R.id.sign_in_condition);

        input_layout_email = (TextInputLayout) rootView.findViewById(R.id.input_layout_email);
        input_layout_password = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        input_layout_first_name = (TextInputLayout) rootView.findViewById(R.id.input_layout_first_name);
        input_layout_last_name = (TextInputLayout) rootView.findViewById(R.id.input_layout_last_name);
        input_layout_phone = (TextInputLayout) rootView.findViewById(R.id.input_layout_phone);

        input_email = (EditText) rootView.findViewById(R.id.input_email);
        input_password = (EditText) rootView.findViewById(R.id.input_password);

        input_phone = (EditText) rootView.findViewById(R.id.input_phone);

        input_first_name = (EditText) rootView.findViewById(R.id.input_first_name);
        input_last_name = (EditText) rootView.findViewById(R.id.input_last_name);

        ccpSignup = rootView.findViewById(R.id.ccpSignup);
        ccpSignup.registerCarrierNumberEditText(input_phone);



        input_email.addTextChangedListener(new MyTextWatcher(input_email));
        input_password.addTextChangedListener(new MyTextWatcher(input_password));
//        input_phone.addTextChangedListener(new MyTextWatcher(input_phone));
        input_first_name.addTextChangedListener(new MyTextWatcher(input_first_name));
        input_last_name.addTextChangedListener(new MyTextWatcher(input_last_name));


        input_email.setFilters(new InputFilter[]{new InputFilter.AllCaps() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return String.valueOf(source).toLowerCase();
            }
        }});


        SpannableString spannableString = new SpannableString(Html.fromHtml(getResources().getString(R.string.hint_i_agree_the_terms_and_conditions_txt)));
        ClickableSpan clickableSpan = new ClickableSpan() {
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

        spannableString.setSpan(clickableSpan, 12, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms_and_condition.setText(spannableString);
        terms_and_condition.setMovementMethod(LinkMovementMethod.getInstance());
        terms_and_condition.setHighlightColor(Color.TRANSPARENT);
//        input_password.setHintTextColor(Color.parseColor(loginPrefMananger.getThemeColor()));
//
//
//        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefMananger.getThemeColor()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            input_layout_email.setBackgroundTintList(colorStateList);
//            input_layout_password.setBackgroundTintList(colorStateList);
//            input_layout_first_name.setBackgroundTintList(colorStateList);
//            input_layout_last_name.setBackgroundTintList(colorStateList);
//            input_layout_phone.setBackgroundTintList(colorStateList);
//
//
//
//        }
//
//        input_layout_email.setDefaultHintTextColor(colorStateList);
//        input_layout_password.setDefaultHintTextColor(colorStateList);
//        input_layout_first_name.setDefaultHintTextColor(colorStateList);
//        input_layout_last_name.setDefaultHintTextColor(colorStateList);
//        input_layout_phone.setDefaultHintTextColor(colorStateList);
//
//        input_layout_email.setErrorTextColor(colorStateList);
//        input_layout_password.setErrorTextColor(colorStateList);
//        input_layout_first_name.setErrorTextColor(colorStateList);
//        input_layout_last_name.setErrorTextColor(colorStateList);
//        input_layout_phone.setErrorTextColor(colorStateList);
//
//
//
//
//
//
//
//        setStyleForfnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//        input_first_name.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForfnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//            } else {
//                if(input_first_name.getText().length() == 0) {
//                    setStyleForfnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//                }
//            }
//        });
//
//        setStyleForlnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//        input_last_name.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForlnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//            } else {
//                if(input_last_name.getText().length() == 0) {
//                    setStyleForlnameTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//                }
//            }
//        });
//
//
//        setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//        input_email.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//            } else {
//                if(input_email.getText().length() == 0) {
//                    setStyleForemailTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//                }
//            }
//        });
//        setStyleForpassTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//        input_password.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleForpassTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//            } else {
//                if(input_password.getText().length() == 0) {
//                    setStyleForpassTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//                }
//            }
//        });
//
//        setStyleFormobTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//        input_phone.setOnFocusChangeListener((v, hasFocus) -> {
//            if(hasFocus) {
//                setStyleFormobTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//            } else {
//                if(input_phone.getText().length() == 0) {
//                    setStyleFormobTextForAutoComplete(Color.parseColor(loginPrefMananger.getThemeColor()));
//                }
//            }
//        });


        // Facebook button
        btnSignInWithFacebook = (Button) rootView.findViewById(R.id.btnFacebookLogin);


        // Set the dimensions of the sign-in button.
        signInButtonGplus = (Button) rootView.findViewById(R.id.btnGooglePlus);


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

        String language = String.valueOf(LanguageSetting.getLanguage(getActivity()));


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


        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                VerifyOTPDialogMethod();
                ForSignUpRequest();

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

       /* input_phone.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart > 0 && !Character.isDigit(c)) {
                        return "";
                    }
                }
                return null;
            }
        };*/

        signInButtonGplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Click Event effect in activity through interface to use between two fragments
                signUpInterface.GooglePlusClickEvent();


            }
        });

        btnSignInWithFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click Event for facebook through interface in Activity
                signUpInterface.FacebookClickEvent();
            }
        });

        return rootView;
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

 /*   protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
    }

    protected class AsyncPhoneInitTask extends AsyncTask<Void, Void, ArrayList<Country>> {

        private int mSpinnerPosition = 181;

        private Context mContext;

        public AsyncPhoneInitTask(Context context) {
            mContext = context;
        }

        @SuppressLint("WrongThread")
        @Override
        protected ArrayList<Country> doInBackground(Void... params) {
            ArrayList<Country> data = new ArrayList<Country>(233);
            BufferedReader reader = null;
            try {
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

            if (!TextUtils.isEmpty(input_phone.getText())) {
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
        }
    }*/

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

    private void ForSignUpRequest() {

        if (!validateMobileNumber() && !validatePassword() && !validateEmail() && !validateLastName() && !validateFirstName()) {
            return;
        }

        if (!validateFirstName()) {
            return;
        }

        if (!validateLastName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateMobileNumber()) {
            return;
        }

//        if (!validateCheckbox()) {
//            return;
//        }
//        if (signup_male_radio_btn.isChecked()) {
//            gender_value = "M";
//        }
//        if (signup_female_radio_btn.isChecked()) {
//            gender_value = "F";
//        }

        gender_value = "";

        APIRequestForSignUp();

    }

    private void APIRequestForSignUp() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }


//            Log.e("first_name", "" + input_first_name.getText().toString().trim());
//            Log.e("last_name", "" + input_last_name.getText().toString().trim());
//            Log.e("email", "" + input_email.getText().toString().trim());
//            Log.e("password", "" + input_password.getText().toString().trim());
//            Log.e("phone", "" + input_phone.getText().toString().replaceAll("\\s+", ""));
//            Log.e("gender", "" + gender_value);
//            Log.e("terms_condition", "1");
//            Log.e("login_type", "" + getString(R.string.login_type));
//            Log.e("language", "" + loginPrefMananger.getStringValue("Lang_code"));
//            Log.e("guest_type", "0");
//            Log.e("user_type", "" + getString(R.string.user_type_normal));
//            Log.e("device_id", "" + loginPrefMananger.getStringValue("device_id"));
//            Log.e("device_token", "" + loginPrefMananger.getStringValue("device_token"));


            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.signup_user_without_prof_img_call(input_first_name.getText().toString().trim(),
                    input_last_name.getText().toString().trim(), input_email.getText().toString().trim(),
                    input_password.getText().toString().trim(), ccpSignup.getFullNumberWithPlus(),
                    gender_value, "1", getString(R.string.login_type), loginPrefMananger.getStringValue("Lang_code"), "0",
                    "" + getString(R.string.user_type_normal), loginPrefMananger.getStringValue("device_id"),
                    loginPrefMananger.getStringValue("device_token")).enqueue(new Callback<Signup>() {
                @Override
                public void onResponse(Call<Signup> call, final Response<Signup> response) {

                    try {
                        Log.e("ip", new Gson().toJson(response.raw().request()));
                        Log.e("signup_user_api", new Gson().toJson(response.body()));

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            signUpresponse = response.body().getResponse();
                            loginPrefMananger.setStringValue("temp_user_id", "" + response.body().getResponse().getUserId());


//                            Log.e("getUserId", "" + response.body().getResponse().getUserId());
                            showToast(response.body().getResponse().getMessage());
                            SignInFragment signInFragment = new SignInFragment();
                            Bundle args = new Bundle();
                            args.putBoolean("proc_to_check", proc_to_check);
                            args.putBoolean("FromMycart", mycart);
                            args.putBoolean("myAccount", myAccount);
//        Log.e("myCart", "" + mycart);
//        Log.e("myCart", "" + myAccount);
                            signInFragment.setArguments(args);
                            //signInFragment.UpdateSignInCallMethod(context);
                            getFragment(signInFragment);


                            // VerifyOTPDialogMethod(response.body().getResponse());

                        } else {
                            showToast(response.body().getResponse().getMessage());
                        }

                    } catch (Exception e) {
//                        Log.e("errorResponse", e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<Signup> call, Throwable t) {

                    progressDialog.dismiss();
                }
            });


        } catch (Exception e) {
//            Log.e("Exception", e.getMessage());
        }
    }


    private void VerifyOTPDialogMethod(final SignUpresponse signUpresponse) {

        RegOTPDialogView regOTPDialogView = new RegOTPDialogView(getContext(),
                "" + input_phone.getText().toString().replaceAll("\\s+", ""),
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

                    showToast(message);

                    if (signUpInterface != null) {
                        signUpInterface.LoginSucess();
                    }

                }

            }
        });
        regOTPDialogView.show();

    }


    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
                case R.id.input_first_name:
                    validateFirstName();
                    break;
                case R.id.input_last_name:
                    validateLastName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_phone:
                    validatePhone();
                    break;

            }
        }

    }

    private boolean validateLastName() {
        if (input_last_name.getText().toString().isEmpty()) {
            input_layout_last_name.setError(getString(R.string.err_msg_last_name));
            requestFocus(input_last_name);
            return false;

        } else {
            input_layout_last_name.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateFirstName() {
        if (input_first_name.getText().toString().isEmpty()) {
            input_layout_first_name.setError(getString(R.string.err_msg_first_name));
            requestFocus(input_first_name);
            return false;
        } else {
            input_layout_first_name.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateMobileNumber() {

        if (input_phone.getText().toString().trim().length() < 10) {
            input_layout_phone.setError(getString(R.string.err_msg_mobile));
            requestFocus(input_phone);
            return false;
        } else if (input_phone.getText().toString().trim().length() > 10) {
            input_layout_phone.setError(getString(R.string.err_msg_mobile));
            requestFocus(input_phone);
            return false;
        } else {
            input_layout_phone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {

        String phone = validate();

        if (phone == null) {
            requestFocus(input_phone);
            input_layout_phone.setError(getString(R.string.label_error_incorrect_phone));
            return false;
        } else {
            input_layout_phone.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = input_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            input_layout_email.setError(getString(R.string.err_msg_email));
            requestFocus(input_email);
            return false;
        } else {
            input_layout_email.setErrorEnabled(false);
        }

        return true;
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

    private boolean validateCheckbox() {
        if (!input_i_agree.isChecked()) {
            input_i_agree.setError(getString(R.string.err_msg_checkbox));
            return false;
        } else {
            input_i_agree.setError(null);
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

    public interface SignUpInterface {

        void LoginSucess();

        void GooglePlusClickEvent();

        void UpdateSignUpMethod(int count);

        void FacebookClickEvent();
    }

    private SignUpInterface signUpInterface;

    public void UpdateSignUpCallMethod(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }


    public void setStyleForfnameTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_first_name.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_first_name.setBackgroundDrawable(wrappedDrawable);
    }

    public void setStyleForlnameTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_last_name.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_last_name.setBackgroundDrawable(wrappedDrawable);
    }

    public void setStyleForemailTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_email.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_email.setBackgroundDrawable(wrappedDrawable);
    }

    public void setStyleForpassTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_password.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_password.setBackgroundDrawable(wrappedDrawable);
    }

    public void setStyleFormobTextForAutoComplete(int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(input_phone.getBackground());
        DrawableCompat.setTint(wrappedDrawable, color);
        input_phone.setBackgroundDrawable(wrappedDrawable);
    }

    public void getFragment(Fragment currentFragment) {
        fragmentManager.beginTransaction()
                .replace(R.id.frameContainer, currentFragment)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .disallowAddToBackStack().commit();
    }


}
