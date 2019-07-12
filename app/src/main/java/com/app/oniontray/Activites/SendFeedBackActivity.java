package com.app.oniontray.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CountrySpinner.Country;
import com.app.oniontray.CountrySpinner.CountryAdapter;
import com.app.oniontray.CountrySpinner.CountryCodes;
import com.app.oniontray.CountrySpinner.CustomPhoneNumberFormattingTextWatcher;
import com.app.oniontray.CountrySpinner.OnPhoneChangedListener;
import com.app.oniontray.CountrySpinner.PhoneUtils;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CityListDa;
import com.app.oniontray.RequestModels.CityRes;
import com.app.oniontray.RequestModels.SendFeedBackReq;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 29/3/17.
 */

public class SendFeedBackActivity extends LocalizationActivity {


    private Toolbar send_feed_back_toolbar;


    private TextInputLayout send_feed_name_txt_input_lay;
    private EditText send_feed_name_edt_txt;

    private TextView feed_back_title;

    private TextInputLayout send_feed_mobile_txt_input_lay;
    private EditText send_feed_mobile_edt_txt;

    private TextInputLayout send_feed_email_txt_input_lay;
    private EditText send_feed_email_edt_txt;

    private TextInputLayout send_feed_description_txt_input_lay;
    private EditText send_feed_description_edt_txt;

    private Spinner send_feed_city_spinner;
    private ArrayAdapter<CityListDa> cityListDaArrayAdapter;
    private ArrayList<CityListDa> cityListDaArrayList;


    private Spinner send_feed_enquiry_type_spinner;
    private ArrayAdapter<String> enquiryAdapter;
    private ArrayList<String> enquiryStringArrayList;


    private Button send_feed_send_btn;

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

            send_feed_mobile_edt_txt.getText().clear();
            send_feed_mobile_edt_txt.getText().insert(send_feed_mobile_edt_txt.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            send_feed_mobile_edt_txt.setSelection(send_feed_mobile_edt_txt.length());

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
        setContentView(R.layout.activity_send_feed_back);

        send_feed_back_toolbar = (Toolbar) findViewById(R.id.send_feed_back_toolbar);
        send_feed_back_toolbar.setTitle("");
        //send_feed_back_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        send_feed_back_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        send_feed_back_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(send_feed_back_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(SendFeedBackActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        send_feed_back_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        init();

    }

    private void init() {

        send_feed_name_txt_input_lay = (TextInputLayout) findViewById(R.id.send_feed_name_txt_input_lay);
        send_feed_name_edt_txt = (EditText) findViewById(R.id.send_feed_name_edt_txt);
        send_feed_name_edt_txt.addTextChangedListener(new MyTextWatcher(send_feed_name_edt_txt));

        send_feed_send_btn = (Button) findViewById(R.id.send_feed_send_btn);

        feed_back_title = findViewById(R.id.feed_back_title);
        feed_back_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        send_feed_mobile_txt_input_lay = (TextInputLayout) findViewById(R.id.send_feed_mobile_txt_input_lay);
        send_feed_mobile_edt_txt = (EditText) findViewById(R.id.send_feed_mobile_edt_txt);
//        send_feed_mobile_edt_txt.addTextChangedListener(new MyTextWatcher(send_feed_mobile_edt_txt));

        send_feed_email_txt_input_lay = (TextInputLayout) findViewById(R.id.send_feed_email_txt_input_lay);
        send_feed_email_edt_txt = (EditText) findViewById(R.id.send_feed_email_edt_txt);
        send_feed_email_edt_txt.addTextChangedListener(new MyTextWatcher(send_feed_email_edt_txt));

        send_feed_description_txt_input_lay = (TextInputLayout) findViewById(R.id.send_feed_description_txt_input_lay);
        send_feed_description_edt_txt = (EditText) findViewById(R.id.send_feed_description_edt_txt);
        send_feed_description_edt_txt.addTextChangedListener(new MyTextWatcher(send_feed_description_edt_txt));



        send_feed_name_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
        send_feed_mobile_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
        send_feed_email_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
        send_feed_description_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));

//        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            send_feed_mobile_edt_txt.setBackgroundTintList(colorStateList);
//            send_feed_email_edt_txt.setBackgroundTintList(colorStateList);
//            send_feed_description_edt_txt.setBackgroundTintList(colorStateList);
//            send_feed_name_edt_txt.setBackgroundTintList(colorStateList);
//
//        }
//        send_feed_name_txt_input_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_mobile_txt_input_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_email_txt_input_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_description_txt_input_lay.setHintTextAppearance(Color.parseColor(loginPrefManager.getThemeColor()));
//
//        send_feed_mobile_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_email_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_description_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_name_edt_txt.setHintTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
//
//        send_feed_name_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_mobile_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_email_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
//        send_feed_description_txt_input_lay.setBoxStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
//
//        send_feed_name_txt_input_lay.setDefaultHintTextColor(colorStateList);
//        send_feed_mobile_txt_input_lay.setDefaultHintTextColor(colorStateList);
//        send_feed_email_txt_input_lay.setDefaultHintTextColor(colorStateList);
//        send_feed_description_txt_input_lay.setDefaultHintTextColor(colorStateList);
//
//        send_feed_name_txt_input_lay.setErrorTextColor(colorStateList);
//        send_feed_mobile_txt_input_lay.setErrorTextColor(colorStateList);
//        send_feed_email_txt_input_lay.setErrorTextColor(colorStateList);
//        send_feed_description_txt_input_lay.setErrorTextColor(colorStateList);





        send_feed_city_spinner = (Spinner) findViewById(R.id.send_feed_city_spinner);
        CityListRequestMethod();

        send_feed_enquiry_type_spinner = (Spinner) findViewById(R.id.send_feed_enquiry_type_spinner);
        GenerateEnquiryTypeSpinnerValues();

        //ViewCompat.setBackgroundTintList(send_feed_city_spinner, ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor())));
       // ViewCompat.setBackgroundTintList(send_feed_enquiry_type_spinner, ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor())));



        send_feed_send_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        send_feed_send_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        ClickeventMethod();

        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {

            send_feed_name_edt_txt.setText("" + loginPrefManager.getStringValue("user_first_name") + " " + loginPrefManager.getStringValue("user_last_name"));
//            send_feed_mobile_edt_txt.setText("" + loginPrefManager.getStringValue("user_mobile").replaceAll("\\s+", ""));
            send_feed_email_edt_txt.setText("" + loginPrefManager.getStringValue("user_email"));

        }


        Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
        Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mAdapter = new CountryAdapter(SendFeedBackActivity.this);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);

        initCodes(SendFeedBackActivity.this);

        send_feed_mobile_edt_txt.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
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


    protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
    }

    protected class AsyncPhoneInitTask extends AsyncTask<Void, Void, ArrayList<Country>> {

        private int mSpinnerPosition = 1;
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
            if (!TextUtils.isEmpty(send_feed_mobile_edt_txt.getText())) {
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


            if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
                Log.e("user_mobile", "" + loginPrefManager.getStringValue("user_mobile").replaceAll("\\s+", ""));
                send_feed_mobile_edt_txt.setText("" + loginPrefManager.getStringValue("user_mobile").replaceAll("\\s+", ""));
            }

        }
    }



    private void CityListRequestMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService addService = Webdata.getNoteRetrofit().create(APIService.class);
        addService.LocationList("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getCountryID()).enqueue(new Callback<CityRes>() {
            @Override
            public void onResponse(Call<CityRes> call, Response<CityRes> response) {

                try {

                    progressDialog.dismiss();

//                    Log.e("onResponse", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {
                        cityListDaArrayList = (ArrayList<CityListDa>) response.body().getResponse().getCityList();
                        CreateCityAdapter(cityListDaArrayList);
                    }

                } catch (Exception e) {
//                    Log.e("Exception", "" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<CityRes> call, Throwable t) {
                progressDialog.dismiss();
            }

        });

    }


    private void CreateCityAdapter(final ArrayList<CityListDa> cityListDaArrayList) {

        CityListDa cityListDa = new CityListDa();
        cityListDa.setId(0);
        cityListDa.setCityName("" + getString(R.string.send_feed_city_spinner_hint_txt));
        cityListDaArrayList.add(0, cityListDa);


        cityListDaArrayAdapter = new ArrayAdapter<CityListDa>(this, android.R.layout.simple_spinner_dropdown_item, cityListDaArrayList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(cityListDaArrayList.get(position).getCityName());

                if(isTablet(SendFeedBackActivity.this)){
                    text.setTextSize(18);
                }else{
                    text.setTextSize(14);
                }

                text.setTextColor(Color.BLACK);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(cityListDaArrayList.get(position).getCityName());

                if(isTablet(SendFeedBackActivity.this)){
                    text.setTextSize(18);
                }else{
                    text.setTextSize(14);
                }

                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        send_feed_city_spinner.setAdapter(cityListDaArrayAdapter);

//        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
//            for (int i = 0; i < cityListDaArrayAdapter.getCount(); i++) {
//                if(cityListDaArrayAdapter.getItem(i).getId() == Integer.parseInt(loginPrefManager.getCityID())){
//                    send_feed_city_spinner.setSelection(i);
//                }
//            }
//        }

        for (int i = 0; i < cityListDaArrayAdapter.getCount(); i++) {
            if(cityListDaArrayAdapter.getItem(i).getId() == Integer.parseInt(loginPrefManager.getCityID())){
                send_feed_city_spinner.setSelection(i);
            }
        }


    }

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private void GenerateEnquiryTypeSpinnerValues() {

        enquiryStringArrayList = new ArrayList<String>();
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_type));
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_general));
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_product));
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_delivery));
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_payment));
        enquiryStringArrayList.add("" + getString(R.string.send_feed_enq_restaurant));


        EnquiryTypeSpinnerList(enquiryStringArrayList);

    }

    private void EnquiryTypeSpinnerList(final List<String> addressType) {

        enquiryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, addressType) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(addressType.get(position).toString());
                if(isTablet(SendFeedBackActivity.this)){
                    text.setTextSize(18);
                }else{
                    text.setTextSize(14);
                }
                text.setTextColor(Color.BLACK);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(addressType.get(position).toString());
                if(isTablet(SendFeedBackActivity.this)){
                    text.setTextSize(18);
                }else{
                    text.setTextSize(14);
                }
                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        send_feed_enquiry_type_spinner.setAdapter(enquiryAdapter);


    }

    private void ClickeventMethod() {

        send_feed_send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendFeedbackMethod();
            }
        });

    }

    private void SendFeedbackMethod() {

        if (!validateName()) {
            return;
        }

        if (!validateMobile()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (cityListDaArrayAdapter != null) {

            if (send_feed_city_spinner.getSelectedItemPosition() == 0) {
                Toast.makeText(SendFeedBackActivity.this, "" + getString(R.string.send_feed_err_msg_city), Toast.LENGTH_SHORT).show();
                return;
            }

        } else {
            Toast.makeText(SendFeedBackActivity.this, "" + getString(R.string.send_feed_err_msg_city), Toast.LENGTH_SHORT).show();
            return;
        }


        if (send_feed_enquiry_type_spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(SendFeedBackActivity.this, "" + getString(R.string.send_feed_err_msg_enquiry_type), Toast.LENGTH_SHORT).show();
            return;
        }


        if (!validateDescription()) {
            return;
        }

        SendFeedBackRequestCall();

    }

    private void SendFeedBackRequestCall() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            Log.e("send_feed_name_edt_txt", "" + send_feed_name_edt_txt.getText().toString());
            Log.e("send_feed_mob_edt_txt", "" + send_feed_mobile_edt_txt.getText().toString());
            Log.e("send_feed_email_edt_txt", "" + send_feed_email_edt_txt.getText().toString());
            Log.e("cityListDaArrayList", "" + cityListDaArrayList.get(send_feed_city_spinner.getSelectedItemPosition()).getId());
            Log.e("enquiryStringArrayList", "" + enquiryStringArrayList.get(send_feed_enquiry_type_spinner.getSelectedItemPosition()).toString());
            Log.e("send_feed_enqu_type_spi", "" + send_feed_enquiry_type_spinner.getSelectedItemPosition());
            Log.e("send_feed_descr_edt_txt", "" + send_feed_description_edt_txt.getText().toString());
            Log.e("language code", "" + loginPrefManager.getStringValue("Lang_code"));

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.SendFeedBackReqMethod("" + send_feed_name_edt_txt.getText().toString().trim(),
                    "" + send_feed_mobile_edt_txt.getText().toString().trim(), "" + send_feed_email_edt_txt.getText().toString().trim(),
                    "" + cityListDaArrayList.get(send_feed_city_spinner.getSelectedItemPosition()).getId(),
                    "" + send_feed_enquiry_type_spinner.getSelectedItemPosition(),
                    "" + send_feed_description_edt_txt.getText().toString(),
                    "" + loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<SendFeedBackReq>() {
                @Override
                public void onResponse(Call<SendFeedBackReq> call, Response<SendFeedBackReq> response) {

                    try {

                        progressDialog.dismiss();

                        Log.e("onResponseee", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            Toast.makeText(SendFeedBackActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                            finish();

                        }else{

                            Toast.makeText(SendFeedBackActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.e("Exception","" +e.getMessage());
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<SendFeedBackReq> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("Failure", "--------------");
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.e("Exceptionee", "" + e.getMessage().toString());
        }


    }


    private boolean validateName() {
        if (send_feed_name_edt_txt.getText().toString().trim().isEmpty()) {
            send_feed_name_txt_input_lay.setError(getString(R.string.send_feed_err_msg_first_name));
            requestFocus(send_feed_name_edt_txt);
            return false;
        } else {
            send_feed_name_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {

        if (send_feed_mobile_edt_txt.getText().toString().replaceAll("\\s+", "").isEmpty()) {
            send_feed_mobile_txt_input_lay.setError(getString(R.string.send_feed_err_msg_mobile_no));
            requestFocus(send_feed_mobile_edt_txt);
            return false;
        } else if (send_feed_mobile_edt_txt.getText().toString().replaceAll("\\s+", "").length() < 10) {
            send_feed_mobile_txt_input_lay.setError(getString(R.string.send_feed_err_msg_valid_mobile_no));
            requestFocus(send_feed_mobile_edt_txt);
            return false;
        }else if (send_feed_mobile_edt_txt.getText().toString().replaceAll("\\s+", "").length() > 15) {
            send_feed_mobile_txt_input_lay.setError(getString(R.string.send_feed_err_msg_valid_mobile_no));
            requestFocus(send_feed_mobile_edt_txt);
            return false;
        }else {
            send_feed_mobile_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {

        if (send_feed_email_edt_txt.getText().toString().trim().isEmpty()) {
            send_feed_email_txt_input_lay.setError(getString(R.string.send_feed_err_msg_email));
            requestFocus(send_feed_email_edt_txt);
            return false;
        } else if (!isValidEmail(send_feed_email_edt_txt.getText().toString().trim())) {
            send_feed_email_txt_input_lay.setError(getString(R.string.send_feed_err_msg_valid_email));
            requestFocus(send_feed_email_edt_txt);
            return false;
        } else {
            send_feed_email_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDescription() {

        if (send_feed_description_edt_txt.getText().toString().trim().isEmpty()) {
            send_feed_description_txt_input_lay.setError(getString(R.string.send_feed_err_msg_description));
            requestFocus(send_feed_description_edt_txt);
            return false;
        } else {
            send_feed_description_txt_input_lay.setErrorEnabled(false);
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

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.send_feed_name_edt_txt:
                    validateName();
                    break;
                case R.id.send_feed_mobile_edt_txt:
                    validateMobile();
                    break;
                case R.id.send_feed_email_edt_txt:
                    validateEmail();
                    break;
                case R.id.send_feed_description_edt_txt:
                    validateDescription();
                    break;

            }
        }

    }

}
