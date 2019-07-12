package com.app.oniontray.Activites;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CountrySpinner.Country;
import com.app.oniontray.CountrySpinner.CountryAdapter;
import com.app.oniontray.CountrySpinner.CountryCodes;
import com.app.oniontray.CountrySpinner.CustomPhoneNumberFormattingTextWatcher;
import com.app.oniontray.CountrySpinner.OnPhoneChangedListener;
import com.app.oniontray.CountrySpinner.PhoneUtils;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddressType;
import com.app.oniontray.RequestModels.AddressTypeList;
import com.app.oniontray.RequestModels.ExpCheAdd;
import com.app.oniontray.RequestModels.GustCheckOutReq;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 2/27/2017.
 */

public class ExpressCheckoutActivity extends LocalizationActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult>, OnMapReadyCallback {

    private Toolbar express_checkout_toolbar;

    private TextView expr_check_title_txt_view;
    private ScrollView expr_check_scroll_view;

    private TextInputLayout expr_check_first_name_txt_input_lay;
    private EditText expr_check_first_name_edt_txt_view;

    private TextInputLayout expr_check_last_name_txt_input_lay;
    private EditText expr_check_last_name_edt_txt_view;

    private TextInputLayout expr_check_email_txt_input_lay;
    private EditText expr_check_email_edt_txt_view;

    private TextInputLayout expr_check_mobile_number_txt_input_lay;
    private EditText expr_check_mobile_number_edt_txt_view;

    private TextInputLayout expr_check_building_flat_no_txt_input_lay;
    private EditText expr_check_building_flat_no_edt_txt_view;

    private TextInputLayout expr_check_land_mark_txt_input_lay;
    private EditText expr_check_land_mark_edt_txt_view;

    private TextInputLayout expr_check_address_type_spin_txt_input_lay;
    private Spinner expr_check_address_type_spinner;
    private ArrayAdapter<AddressTypeList> inputSelectiveAddress;
    private String Address_type = "";

    private TextInputLayout expr_check_address_txt_input_lay;
    private EditText expr_check_address_edt_txt_view;
    String country_code;

//    private TextInputLayout expr_check_landline_number_txt_input_lay;
//    private EditText expr_check_landline_number_edt_txt_view;
//
//    private TextInputLayout expr_check_area_txt_input_lay;
//    private EditText expr_check_area_edt_txt_view;
//
//    private TextInputLayout expr_check_address_type_txt_input_lay;
//    private EditText expr_check_address_type_edt_txt_view;
//
//    private TextInputLayout expr_check_street_txt_input_lay;
//    private EditText expr_check_street_edt_txt_view;
//
//    private TextInputLayout expr_check_building_txt_input_lay;
//    private EditText expr_check_building_edt_txt_view;
//
//    private TextInputLayout expr_check_floor_txt_input_lay;
//    private EditText expr_check_floor_edt_txt_view;
//
//    private TextInputLayout expr_check_apartment_no_txt_input_lay;
//    private EditText expr_check_apartment_no_edt_txt;
//
//    private TextInputLayout expr_check_addit_direc_txt_input_lay;
//    private EditText expr_check_addit_direc_edt_txt;

    private Button expr_check_continue_btn;


    protected String mLastEnteredPhone;
    protected Spinner expr_county_code_spinner;
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

            Country c = (Country) expr_county_code_spinner.getItemAtPosition(position);
            if (mLastEnteredPhone != null && mLastEnteredPhone.startsWith(c.getCountryCodeStr())) {
                return;
            }
            country_code = String.valueOf(c.getCountryCode());

            expr_check_mobile_number_edt_txt_view.getText().clear();
            expr_check_mobile_number_edt_txt_view.getText().insert(expr_check_mobile_number_edt_txt_view.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            expr_check_mobile_number_edt_txt_view.setSelection(expr_check_mobile_number_edt_txt_view.length());
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
                    expr_county_code_spinner.post(new Runnable() {
                        @Override
                        public void run() {
                            expr_county_code_spinner.setSelection(position);
                        }
                    });
                }

//                ValidateMobileNumber();
//                Log.e("mOnPhoneChangedListener", "" + mLastEnteredPhone);

            } catch (NumberParseException ignore) {
//                Log.e("NumberParseException", "" + ignore.getMessage());
            }

        }
    };


    private static final String TAG = "ExpressCheckoutActivity";

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;


    private GoogleMap google_map;
    private Marker google_marker;
    private StringBuilder stringBuilder;
    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    Location mLastLocation;

    private double latitude;
    private double longitude;

    private boolean first_time_loc_update = true;


    // My Cart Details
    private OutletDetails outletDetails;
    private String vendor_id;

    private String grand_total_at = "";
    private final Calendar current_date = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_checkout);

        express_checkout_toolbar = (Toolbar) findViewById(R.id.express_checkout_toolbar);
        express_checkout_toolbar.setTitle("");
        express_checkout_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(express_checkout_toolbar);


        if (getIntent().hasExtra("outlet_details")) {
            outletDetails = (OutletDetails) getIntent().getSerializableExtra("outlet_details");
            vendor_id = getIntent().getStringExtra("vendor_id");
        }


        expr_check_title_txt_view = (TextView) findViewById(R.id.expr_check_title_txt_view);
        expr_check_title_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

//        google_map = ((MapFragment) getFragmentManager().findFragmentById(R.id.expr_check_map_fragment)).getMap();
//        google_map.setMyLocationEnabled(true);


        InitializeMapView();

        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        updateValuesFromBundle(savedInstanceState);
        buildGoogleApiClient();

        createLocationRequest();
        buildLocationSettingsRequest();

        checkLocationSettings();


        InitView();


    }


    private void InitializeMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.expr_check_map_fragment);
        mapFragment.getMapAsync(this);


    }


    private void InitView() {

        expr_check_scroll_view = (ScrollView) findViewById(R.id.expr_check_scroll_view);

        expr_check_first_name_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_first_name_txt_input_lay);
        expr_check_first_name_edt_txt_view = (EditText) findViewById(R.id.expr_check_first_name_edt_txt_view);
        expr_check_first_name_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_first_name_edt_txt_view));

        expr_check_last_name_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_last_name_txt_input_lay);
        expr_check_last_name_edt_txt_view = (EditText) findViewById(R.id.expr_check_last_name_edt_txt_view);
        expr_check_last_name_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_last_name_edt_txt_view));

        expr_check_email_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_email_txt_input_lay);
        expr_check_email_edt_txt_view = (EditText) findViewById(R.id.expr_check_email_edt_txt_view);
        expr_check_email_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_email_edt_txt_view));

        expr_check_mobile_number_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_mobile_number_txt_input_lay);
        expr_check_mobile_number_edt_txt_view = (EditText) findViewById(R.id.expr_check_mobile_number_edt_txt_view);
//        expr_check_mobile_number_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_mobile_number_edt_txt_view));

        expr_check_building_flat_no_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_building_flat_no_txt_input_lay);
        expr_check_building_flat_no_edt_txt_view = (EditText) findViewById(R.id.expr_check_building_flat_no_edt_txt_view);
        expr_check_building_flat_no_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_building_flat_no_edt_txt_view));

        expr_check_land_mark_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_land_mark_txt_input_lay);
        expr_check_land_mark_edt_txt_view = (EditText) findViewById(R.id.expr_check_land_mark_edt_txt_view);
        expr_check_land_mark_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_land_mark_edt_txt_view));


        expr_check_address_type_spin_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_address_type_spin_txt_input_lay);
        expr_check_address_type_spinner = (Spinner) findViewById(R.id.expr_check_address_type_spinner);
        AddresTypeRequestMethod();

        expr_check_address_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_address_txt_input_lay);
        expr_check_address_edt_txt_view = (EditText) findViewById(R.id.expr_check_address_edt_txt_view);
//        expr_check_address_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_address_edt_txt_view));

        expr_check_address_edt_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(ExpressCheckoutActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });


//        expr_check_landline_number_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_landline_number_txt_input_lay);
//        expr_check_landline_number_edt_txt_view = (EditText) findViewById(R.id.expr_check_landline_number_edt_txt_view);
//        expr_check_landline_number_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_landline_number_edt_txt_view));
//
//        expr_check_area_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_area_txt_input_lay);
//        expr_check_area_edt_txt_view = (EditText) findViewById(R.id.expr_check_area_edt_txt_view);
//        expr_check_area_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_area_edt_txt_view));
//
//        expr_check_address_type_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_address_type_txt_input_lay);
//        expr_check_address_type_edt_txt_view = (EditText) findViewById(R.id.expr_check_address_type_edt_txt_view);
//        expr_check_address_type_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_address_type_edt_txt_view));
//
//        expr_check_street_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_street_txt_input_lay);
//        expr_check_street_edt_txt_view = (EditText) findViewById(R.id.expr_check_street_edt_txt_view);
//        expr_check_street_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_street_edt_txt_view));
//
//        expr_check_building_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_building_txt_input_lay);
//        expr_check_building_edt_txt_view = (EditText) findViewById(R.id.expr_check_building_edt_txt_view);
//        expr_check_building_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_building_edt_txt_view));
//
//        expr_check_floor_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_floor_txt_input_lay);
//        expr_check_floor_edt_txt_view = (EditText) findViewById(R.id.expr_check_floor_edt_txt_view);
//        expr_check_floor_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(expr_check_floor_edt_txt_view));
//
//        expr_check_apartment_no_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_apartment_no_txt_input_lay);
//        expr_check_apartment_no_edt_txt = (EditText) findViewById(R.id.expr_check_apartment_no_edt_txt);
//        expr_check_apartment_no_edt_txt.addTextChangedListener(new MyProfTextWatcher(expr_check_apartment_no_edt_txt));
//
//        expr_check_addit_direc_txt_input_lay = (TextInputLayout) findViewById(R.id.expr_check_addit_direc_txt_input_lay);
//        expr_check_addit_direc_edt_txt = (EditText) findViewById(R.id.expr_check_addit_direc_edt_txt);
//        expr_check_addit_direc_edt_txt.addTextChangedListener(new MyProfTextWatcher(expr_check_addit_direc_edt_txt));


        expr_check_continue_btn = (Button) findViewById(R.id.expr_check_continue_btn);
        expr_check_continue_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        expr_check_continue_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        expr_county_code_spinner = (Spinner) findViewById(R.id.expr_county_code_spinner);
        mAdapter = new CountryAdapter(ExpressCheckoutActivity.this);
        expr_county_code_spinner.setAdapter(mAdapter);
        expr_county_code_spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        initCodes(ExpressCheckoutActivity.this);

        expr_check_mobile_number_edt_txt_view.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
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

        ButonClickEvents();

    }

    private void AddresTypeRequestMethod() {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.address_type().enqueue(new Callback<AddressType>() {
            @Override
            public void onResponse(Call<AddressType> call, Response<AddressType> response) {

                if (response.body().getResponse().getHttpCode() == 200) {

                    AddressTypeSpinnerList(response.body().getResponse().getAddressType());

                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(ExpressCheckoutActivity.this, response.body().getResponse().getAddressType().toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddressType> call, Throwable t) {
//                Log.e("AddAddressActivity", "onFailure :" + t.getMessage());
            }
        });

    }

    private void AddressTypeSpinnerList(final List<AddressTypeList> addressType) {

        AddressTypeList addressTypeList = new AddressTypeList();
        addressTypeList.setId("");
        addressTypeList.setName("" + getString(R.string.select_add_type_txt));

        addressType.add(addressTypeList);

        inputSelectiveAddress = new ArrayAdapter<AddressTypeList>(this, android.R.layout.simple_spinner_dropdown_item, addressType) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(addressType.get(position).getName());
//                text.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceLarge);
                text.setTextSize(14);
                text.setTextColor(Color.BLACK);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);

                if (position == getCount()) {
                    text.setText(addressType.get(position).getName());
                    text.setTextSize(14);
                    text.setTextColor(Color.BLACK);
                } else {
                    text.setHint(getItem(position).toString());
                    text.setText(addressType.get(position).getName());
                    text.setTextSize(14);
                    text.setTextColor(Color.BLACK);
                }

                return view;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };

        expr_check_address_type_spinner.setAdapter(inputSelectiveAddress);
        expr_check_address_type_spinner.setSelection(inputSelectiveAddress.getCount());

        expr_check_address_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (inputSelectiveAddress.getCount() != 0) {
//                    Address_type = String.valueOf(inputSelectiveAddress.getItem(position).getId());
                    Address_type = "" + inputSelectiveAddress.getItem(position).getId();
//                    ValidateAddressType();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }

    protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        google_map = map;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        google_map.setMyLocationEnabled(true);
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

            if (!TextUtils.isEmpty(expr_check_mobile_number_edt_txt_view.getText())) {
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
                expr_county_code_spinner.setSelection(mSpinnerPosition);
            }

//            Log.e("mAdapter","" + mAdapter.getCount());
//            Log.e("mCountriesMap", "-" + mCountriesMap.size());
//            Log.e("ARABIC_COUNTRY_NAME", "-" + ARABIC_COUNTRY_NAME.size());

        }
    }

    private boolean validateMobile() {

        expr_check_mobile_number_edt_txt_view.setError(null);

        String phone = validate();

        if (phone == null) {
            requestFocus(expr_check_mobile_number_edt_txt_view);
            expr_check_mobile_number_txt_input_lay.setError(getString(R.string.label_error_incorrect_phone));
            return false;
        } else {
            expr_check_mobile_number_txt_input_lay.setErrorEnabled(false);
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

    private void ButonClickEvents() {

        expr_check_continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExpressCheckOut();
            }
        });

    }

    private void ExpressCheckOut() {

//        Log.e("GenerateRandomString", "" + GenerateRandomString());

//        if (!ValidateAddressType()) {
//            return;
//        }

        if (!ValidateFirstName()) {
            return;
        }

        if (!ValidateLastName()) {
            return;
        }

        if (!ValidateEmail()) {
            return;
        }

        if (!ValidateMobileNumber()) {
            return;
        }

        if (!ValidateBuildingNumber()) {
            return;
        }

        if (!ValidateLandMark()) {
            return;
        }

        if (!ValidateAddressType()) {
            return;
        }

        if (!ValidateAddress()) {
            return;
        }


        // Delivery details Calculations
        setGrandTotalAmount();
        setServiceTaxAmount();
        DeliveryCost();

        callExpCheckProToCheckMethod();
        // GeneratePaymentArrayFormat();


    }

    private void callExpCheckProToCheckMethod() {

        try {

            ExpCheAdd expCheAdd = new ExpCheAdd();

            expCheAdd.setFirst_name(expr_check_first_name_edt_txt_view.getText().toString().trim());
            expCheAdd.setLast_name(expr_check_last_name_edt_txt_view.getText().toString().trim());
            expCheAdd.setEmail(expr_check_email_edt_txt_view.getText().toString().trim());

            expCheAdd.setCountry_code("+44");

//            String mobile_no = expr_check_mobile_number_std.getText().toString() + expr_check_mobile_number_edt_txt_view.getText().toString().replaceAll("\\s+", "");
            String mobile_no = expr_check_mobile_number_edt_txt_view.getText().toString().trim();
            expCheAdd.setPhone_no(mobile_no);

            expCheAdd.setBuilding_no(expr_check_building_flat_no_edt_txt_view.getText().toString().trim());
            expCheAdd.setLand_mark(expr_check_land_mark_edt_txt_view.getText().toString().trim());

            expCheAdd.setAddress_type(Address_type);
            expCheAdd.setAddress(expr_check_address_edt_txt_view.getText().toString().trim());

            expCheAdd.setLatitude("" + latitude);
            expCheAdd.setLongitude("" + longitude);


            loginPrefManager.setStringValue("express_check_out", "1");

            Intent exp_che_intent = new Intent(ExpressCheckoutActivity.this, ExpCheckProceedToCheckActivity.class);
            exp_che_intent.putExtra("exp_che_add_det", expCheAdd);
            exp_che_intent.putExtra("out_det", outletDetails);
            exp_che_intent.putExtra("vendor_id", vendor_id);
            startActivity(exp_che_intent);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }

    private void GeneratePaymentArrayFormat() {

        try {

//            {"admin_commission":13.81,"vendor_commission":251,"store_id":"18","outlet_id":"1","outlet_name":"Mankoshtkom",
//                    "vendor_key":"Mankoshtkom","total":264.81,"sub_total":251,"service_tax":13.81,"order_status":1,
//                    "order_key":"Jn95y0JzclHnPMYPxokCKqZzTWPhGVQ2","invoice_id":"EY7ZdFzJfJzhdnisaBAsb91WNwNaIDhT",
//                    "transaction_id":"leFHQWGVBBl3Y3Meq4eatolmHIlprBHS","transaction_staus":1,"transaction_amount":264.81,
//                    "payer_id":"pzXnIRpFPA3dgXZlg0dIXaUQGG80BdZQ","currency_code":"SAR  ","payment_gateway_id":18,
//                    "delivery_charge":0,"payment_status":0,"payment_gateway_commission":0,"delivery_instructions":"sdsadfsf",
//                    "delivery_date":"NOW()","order_type":"1","coupon_id":0,"coupon_amount":0,"coupon_type":0,"delivery_cost":0,
//                    "items":[{"product_id":"196","quantity":"1","discount_price":"10","special_req":"","ingredients":"","item_offer":0},
//                {"product_id":"220","quantity":"1","discount_price":"205","special_req":"","ingredients":"","item_offer":0},
//                {"product_id":"226","quantity":"1","discount_price":"32","special_req":"","ingredients":{"0":{"ingredient_id":"7","price":"1"},
//                    "1":{"ingredient_id":"10","price":"2"},"2":{"ingredient_id":"4","price":"1"},
//                    "ingredient_names":"Hydrolyzed Animal Protein, Yeast, Herb farm"},"item_offer":0}]}

            JSONObject payment_object = new JSONObject();

            productRespository.totalPrice();

            int commission_one = ((Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) *
                    Math.round(Float.parseFloat("" + outletDetails.getCommissionAmount())) / 100));


            payment_object.put("admin_commission", "" + commission_one);
            int vender_commision = (Math.round(Float.parseFloat("" + outletDetails.getSubTotal())) - (commission_one));
            payment_object.put("vendor_commission", "" + vender_commision);

            payment_object.put("store_id", "" + vendor_id);
            payment_object.put("outlet_id", "" + outletDetails.getOutletsId());

            payment_object.put("outlet_name", "" + outletDetails.getOutletName());
            payment_object.put("vendor_key", "" + outletDetails.getOutletName());

            payment_object.put("total", "" + outletDetails.getGrandTotal());
            payment_object.put("sub_total", "" + outletDetails.getSubTotal());


            if (outletDetails.getTaxType() == 2) {
                payment_object.put("service_tax", "" + outletDetails.getServiceTax());
                payment_object.put("tax_label_name", "");
                payment_object.put("tax_percentage", "");
            } else {
                payment_object.put("service_tax", "" + outletDetails.getServiceTax());
                payment_object.put("tax_label_name", "" + outletDetails.getTaxLabelName().trim());
                payment_object.put("tax_percentage", "" + outletDetails.getTaxPercentage());
            }

//            payment_object.put("service_tax", "" + outletDetails.getServiceTax());
//            payment_object.put("tax_label_name", "" + outletDetails.getServiceTax());

            payment_object.put("contact_address", "" + outletDetails.getContactAddress());
            payment_object.put("contact_email", "" + outletDetails.getContactEmail());

            payment_object.put("order_status", "1");
            payment_object.put("currency_code", "" + loginPrefManager.getCurrencySymbole());

            payment_object.put("payment_gateway_id", "" + loginPrefManager.getStringValue("Cash_PaymentGateWay_Id"));
            payment_object.put("delivery_charge", "" + outletDetails.getDeliveryCost());

            payment_object.put("payment_status", "0");
            payment_object.put("payment_gateway_commission", "" + loginPrefManager.getStringValue("Cash_Delivery_Commision"));

            payment_object.put("delivery_instructions", "");


            SimpleDateFormat curr_format = new SimpleDateFormat("yyyy-MM-dd");

            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
                curr_format.setNumberFormat(nf);
            }
            outletDetails.setDeliveryTime("" + curr_format.format(current_date.getTime()));
            payment_object.put("delivery_date", "" + curr_format.format(current_date.getTime()));

            payment_object.put("order_type", "1");
            payment_object.put("coupon_id", "0");

            payment_object.put("coupon_amount", "0");
            payment_object.put("coupon_type", "0");

            payment_object.put("delivery_cost", "0");


            JSONArray productJsonArray = new JSONArray();

            for (int i = 0; i < productRespository.getCartProductList().size(); i++) {

                JSONObject productJsonObject = new JSONObject();
                productJsonObject.put("product_id", productRespository.getCartProductList().get(i).getProductId());
                productJsonObject.put("quantity", productRespository.getCartProductList().get(i).getCartCount());

                if (productRespository.getCartProductList().get(i).getIngredTypeList().size() != 0) {

                    JSONObject ingredientMainObject = new JSONObject();
                    Float price = 0f;
                    int value = 0;
                    int cartCount = 0;
                    StringBuilder ingredientName = new StringBuilder();

                    for (int j = 0; j < productRespository.getCartProductList().get(i).getIngredTypeList().size(); j++) {

                        cartCount += productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size();

                        for (int k = 0; k < productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().size(); k++) {
                            JSONObject ingredientInnerObject = new JSONObject();

                            price += Float.parseFloat(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());
                            Log.e("price", "" + price);
                            ingredientName.append(productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientName());
                            if (j != productRespository.getCartProductList().get(i).getIngredTypeList().size())
                                ingredientName.append(",");

                            ingredientInnerObject.put("ingredient_id", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getIngredientId());
                            ingredientInnerObject.put("price", productRespository.getCartProductList().get(i).getIngredTypeList().get(j).getIngredientList().get(k).getPrice());

                            ingredientMainObject.put("" + value, ingredientInnerObject);

                            value++;
                        }

                    }

                    String str = ingredientName.toString().replaceAll(",$", "");
                    ingredientMainObject.put("ingredient_names", str);

                    Float total_price_to_fc = Float.parseFloat("" + productRespository.getCartProductList().get(i).getTotal());
                    Log.e("total_price", "" + (total_price_to_fc - price));

                    Float total_price = total_price_to_fc - price;

                    String total_price_sconverted = "" + total_price;

                    Log.e("totla", "" + total_price_sconverted);


                    productJsonObject.put("discount_price", "" + total_price_sconverted);
                    productJsonObject.put("special_req", "");
                    productJsonObject.put("ingredients", ingredientMainObject);
                    productJsonObject.put("item_offer", "0");

                } else {

                    productJsonObject.put("ingredients", "");
                    productJsonObject.put("discount_price", "" + productRespository.getCartProductList().get(i).getTotal());
                    productJsonObject.put("special_req", "");
                    productJsonObject.put("item_offer", "0");

                }

                productJsonArray.put(productJsonObject);
            }

            payment_object.put("items", productJsonArray);
//            Log.e("gust_check_out pay arr", payment_object.toString());


            ExpressCheeckOutRequestMethod(payment_object.toString());


        } catch (Exception e) {
//            Log.e("GeneratePayArrayFormat", "Exception" + e.getMessage());
        }

    }

    private void ExpressCheeckOutRequestMethod(String payment_array) {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
            Log.e("first_name_edt_txt_view", "" + expr_check_first_name_edt_txt_view.getText().toString().trim());
            Log.e("last_name_edt_txt_view", "" + expr_check_last_name_edt_txt_view.getText().toString().trim());
            Log.e("email_edt_txt_view", "" + expr_check_email_edt_txt_view.getText().toString().trim());
            Log.e("mobile_num_edt_txt_view", "" + expr_check_mobile_number_edt_txt_view.getText().toString().replaceAll("\\s+", ""));
            Log.e("getCityID", "" + loginPrefManager.getCityID());
            Log.e("getLocID", "" + loginPrefManager.getLocID());
            Log.e("address_edt_txt_view", "" + expr_check_address_edt_txt_view.getText().toString());
            Log.e("bui_flat_no_edt_txtview", "" + expr_check_building_flat_no_edt_txt_view.getText().toString());
            Log.e("land_mark_edt_txt_view", "" + expr_check_land_mark_edt_txt_view.getText().toString());
            Log.e("latitude", "" + latitude);
            Log.e("longitude", "" + longitude);
            Log.e("address_type", "" + getString(R.string.expr_check_addr_type_value));
            Log.e("payment_array", "" + payment_array);
            Log.e("guest_type", "" + getString(R.string.expr_check_guest_type));
            Log.e("login_type", "" + getString(R.string.expr_check_login_type));
            Log.e("device_id", "" + loginPrefManager.getStringValue("device_id"));
            Log.e("device_token", loginPrefManager.getStringValue("device_token"));

            APIService express_check_out = Webdata.getRetrofit().create(APIService.class);
            express_check_out.getExpressCheckoutRequest("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + expr_check_first_name_edt_txt_view.getText().toString().trim(),
                    "" + expr_check_last_name_edt_txt_view.getText().toString().trim(),
                    "" + expr_check_email_edt_txt_view.getText().toString().trim(),
                    "" + expr_check_mobile_number_edt_txt_view.getText().toString().replaceAll("\\s+", ""),
                    "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID(),
                    "" + expr_check_address_edt_txt_view.getText().toString(),
                    "" + expr_check_building_flat_no_edt_txt_view.getText().toString(),
                    "" + expr_check_land_mark_edt_txt_view.getText().toString(), "" + latitude, "" + longitude,
                    "" + getString(R.string.expr_check_addr_type_value), "" + payment_array, "" + getString(R.string.expr_check_guest_type), "" + getString(R.string.expr_check_login_type),
                    "" + loginPrefManager.getStringValue("device_id"),
                    "" + loginPrefManager.getStringValue("device_token")).enqueue(new Callback<GustCheckOutReq>() {

                @Override
                public void onResponse(Call<GustCheckOutReq> call, Response<GustCheckOutReq> response) {

                    try {

                        Log.e("getExpressCheckoutReq", ".." + new Gson().toJson(response.raw().request()));
                        Log.e("getExpressCheckoutResp", ".." + new Gson().toJson(response.body()));
                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            outletDetails.setDeliveryAddress("" + expr_check_address_edt_txt_view.getText().toString().trim());

                            Intent order_cof = new Intent(ExpressCheckoutActivity.this, OrderConfirmationActivity.class);
                            order_cof.putExtra("ORDER", outletDetails);
                            order_cof.putExtra("DELIVERY_TEXT", "");
                            order_cof.putExtra("PAYMENT_TYPE", "" + getString(R.string.cash_on_delivery_txt));
                            startActivity(order_cof);

                            finish();

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(ExpressCheckoutActivity.this, "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("getExpressCheckoutReq", "Exception" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<GustCheckOutReq> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("getExpressCheckoutReq", "onFailure" + t.getMessage());
                }
            });


        } catch (Exception e) {
            progressDialog.dismiss();
        }

    }


    private void setGrandTotalAmount() {
        Log.e("delivery_type", "" + outletDetails.getDeliveryType());

        if (outletDetails.getDeliveryType() == 1) {

            if (outletDetails.getTaxType() == 2) {
                grand_total_at = "" + productRespository.totalPrice();
            } else {
                grand_total_at = "" + (Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount());
            }

        } else if (outletDetails.getDeliveryType() == 2) {

            if (outletDetails.getTaxType() == 2) {
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            } else {
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            }

        } else if (outletDetails.getDeliveryType() == 3) {

            if (outletDetails.getTaxType() == 2) {
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            } else {
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount() + (Float.parseFloat(outletDetails.getDeliveryCostFixed())));
            }

        }

        outletDetails.setSubTotal("" + productRespository.totalPrice());
        outletDetails.setDeliveryInstruction("");

        if (!grand_total_at.equals("0")) {
            outletDetails.setGrandTotal("" + grand_total_at);
        }

    }

    private float setServiceTaxAmount() {

        float serviceTax = 0f;
        if (outletDetails.getTaxType() == 1) {
//            Log.e("total", "" + productRespository.totalPrice());
            serviceTax = Float.parseFloat("" + productRespository.totalPrice()) * Float.parseFloat("" + outletDetails.getTaxPercentage()) / 100;
            serviceTax = round(serviceTax, 2);
        }
        outletDetails.setServiceTax("" + serviceTax);

        return serviceTax;
    }

    private void DeliveryCost() {

        if (outletDetails.getDeliveryType() == 1) {
            outletDetails.setDeliveryCost("0");
        } else if (outletDetails.getDeliveryType() == 2) {
            outletDetails.setDeliveryCost("" + outletDetails.getDeliveryCostFixed());
        } else if (outletDetails.getDeliveryType() == 3) {
            outletDetails.setDeliveryCost("" + outletDetails.getDeliveryCostFixed());
        }

    }


    public static Float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public String GenerateRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
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
                case R.id.expr_check_first_name_edt_txt_view:
                    ValidateFirstName();
                    break;
                case R.id.expr_check_last_name_edt_txt_view:
                    ValidateLastName();
                    break;
                case R.id.expr_check_email_edt_txt_view:
                    ValidateEmail();
                    break;
                case R.id.expr_check_mobile_number_edt_txt_view:
                    validateMobile();
                    break;
                case R.id.expr_check_building_flat_no_edt_txt_view:
                    ValidateBuildingNumber();
                    break;
                case R.id.expr_check_land_mark_edt_txt_view:
                    ValidateLandMark();
                    break;

                case R.id.expr_check_address_edt_txt_view:
                    ValidateAddress();
                    break;
            }
        }
    }

    private boolean ValidateFirstName() {

        if (expr_check_first_name_edt_txt_view.getText().toString().trim().isEmpty()) {
            expr_check_first_name_txt_input_lay.setError(getString(R.string.expr_err_msg_first_name_txt));
            requestFocus(expr_check_first_name_edt_txt_view);
            return false;
        } else {
            expr_check_first_name_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateLastName() {

        if (expr_check_last_name_edt_txt_view.getText().toString().trim().isEmpty()) {
            expr_check_last_name_txt_input_lay.setError(getString(R.string.expr_err_msg_last_name_txt));
            requestFocus(expr_check_last_name_edt_txt_view);
            return false;
        } else {
            expr_check_last_name_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateEmail() {
        String email = expr_check_email_edt_txt_view.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            expr_check_email_txt_input_lay.setError(getString(R.string.expr_err_msg_email_txt));
            requestFocus(expr_check_email_edt_txt_view);
            return false;
        } else {
            expr_check_email_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean ValidateMobileNumber() {

        if (expr_check_mobile_number_edt_txt_view.getText().toString().trim().length() < 7) {
            expr_check_mobile_number_txt_input_lay.setError(getString(R.string.err_msg_mobile));
            requestFocus(expr_check_mobile_number_edt_txt_view);
            return false;
        } else {
            expr_check_mobile_number_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateBuildingNumber() {

        if (expr_check_building_flat_no_edt_txt_view.getText().toString().trim().isEmpty()) {
            expr_check_building_flat_no_txt_input_lay.setError(getString(R.string.expr_err_msg_build_no_flat_no_txt));
            requestFocus(expr_check_building_flat_no_edt_txt_view);
            return false;
        } else {
            expr_check_building_flat_no_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateLandMark() {

        if (expr_check_land_mark_edt_txt_view.getText().toString().trim().isEmpty()) {
            expr_check_land_mark_txt_input_lay.setError(getString(R.string.expr_err_msg_land_mark_txt));
            requestFocus(expr_check_land_mark_edt_txt_view);
            return false;
        } else {
            expr_check_land_mark_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateAddressType() {

        if (Address_type.trim().isEmpty()) {
            expr_check_address_type_spin_txt_input_lay.setError(getString(R.string.err_msg_select_address_type));
//            Toast.makeText(ExpressCheckoutActivity.this, "" + getString(R.string.err_msg_select_address_type), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            expr_check_address_type_spin_txt_input_lay.setErrorEnabled(false);
        }

        return true;
    }

    private boolean ValidateAddress() {

        if (expr_check_address_edt_txt_view.getText().toString().trim().isEmpty()) {
            expr_check_address_txt_input_lay.setError(getString(R.string.expr_err_msg_enter_addr_txt));
            requestFocus(expr_check_address_edt_txt_view);
            return false;
        } else {
            expr_check_address_txt_input_lay.setErrorEnabled(false);
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


    // Google Api Client Location Updates Method.

    private void updateValuesFromBundle(Bundle savedInstanceState) {

        if (savedInstanceState != null) {

            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(KEY_REQUESTING_LOCATION_UPDATES);
            }

            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }

        }

    }

    private synchronized void buildGoogleApiClient() {
//        Log.e(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);
        result.setResultCallback(this);
    }

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
//                Log.e(TAG, "All location settings are satisfied.");
                startLocationUpdates();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                Log.e(TAG, "Location settings are not satisfied. Show the user a dialog to" +
//                        "upgrade location settings ");

                try {
                    // Show the dialog by calling startResolutionForResult(), and check the result
                    // in onActivityResult().
                    status.startResolutionForResult(ExpressCheckoutActivity.this, REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
//                    Log.e(TAG, "PendingIntent unable to execute request.");
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                Log.e(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " + "not created.");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Place place = PlaceAutocomplete.getPlace(this, data);

                this.latitude = place.getLatLng().latitude;
                this.longitude = place.getLatLng().longitude;

                google_marker.setPosition(place.getLatLng());
                google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));

//                Log.e("place", "" + place);
//                Log.e("data", "Place: " + place.getAddress());

                expr_check_address_edt_txt_view.setText(place.getAddress());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

                Status status = PlaceAutocomplete.getStatus(this, data);
//                Log.e("data", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }

        }


        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
//                        Log.e(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        showExitWarningDialog(ExpressCheckoutActivity.this);
//                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }

    private void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
            }
        });
    }

    /**
     * Sets the value of the UI fields for the location latitude, longitude and last update time.
     */
    private void LocationUpdateMethod() {

        if (mCurrentLocation == null) {
            return;
        } else if (mCurrentLocation != null) {

            this.latitude = mCurrentLocation.getLatitude();
            this.longitude = mCurrentLocation.getLongitude();

//            Log.e("getLatitude", "" + mCurrentLocation.getLatitude());
//            Log.e("getLongitude", "" + mCurrentLocation.getLongitude());

            getAddressFromLatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }

        LatLng currentpos = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        google_map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 15));

        if (google_marker != null) {
            google_marker.remove();
        }

        google_marker = google_map.addMarker(new MarkerOptions().position(currentpos).draggable(true));

        google_map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

//                LatLng centerOfMap = Mmap.getCameraPosition().target;
//                marker.setPosition(centerOfMap);
//                LatLng markerLocation = marker.getPosition();
//                latitude = markerLocation.latitude;
//                longitude = markerLocation.longitude;
//
//                getAddressFromLatLng(latitude, longitude);

            }
        });

//        }

        google_map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//                LatLng markerLocation = marker.getPosition();
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                google_marker.setPosition(latLng);

                getAddressFromLatLng(latitude, longitude);
            }
        });

//        Mmap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
//            @Override
//            public void onMarkerDrag(Marker arg0) {
//                // TODO Auto-generated method stub
//                Log.d("Marker", "Dragging");
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker arg0) {
//                // TODO Auto-generated method stub
//                LatLng markerLocation = marker.getPosition();
//                latitude = markerLocation.latitude;
//                longitude = markerLocation.longitude;
//            }
//
//            @Override
//            public void onMarkerDragStart(Marker arg0) {
//                // TODO Auto-generated method stub
//                Log.e("Marker", "Started");
//            }
//        });

    }

    private void getAddressFromLatLng(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        stringBuilder = new StringBuilder();
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            Log.e("address", addresses.toString());
            if (addresses.size() != 0) {
                int address = addresses.get(0).getMaxAddressLineIndex(); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                for (int i = 0; i <= address; i++) {
                    String character = ",";
                    if (i == address) {
                        character = "";
                    }
                    stringBuilder.append(addresses.get(0).getAddressLine(i)).append(character);
                    expr_check_address_edt_txt_view.setText(stringBuilder.toString());
//                    Log.e("sb", "" + sb);
                }
                if (stringBuilder.length() != 0) {

                }
//                    Log.e("address====>", "" + address);
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void updateLocationUI() {

        if (mCurrentLocation != null) {
//            Log.e("getLatitude", "" + mCurrentLocation.getLatitude());
//            Log.e("getLongitude", "" + mCurrentLocation.getLongitude());
        }

    }

    private void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop location updates to save battery, but don't disconnect the GoogleApiClient object.
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (first_time_loc_update) {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }

        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (first_time_loc_update) {
            LocationUpdateMethod();
            first_time_loc_update = false;
            progressDialog.dismiss();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.e(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
//        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(KEY_REQUESTING_LOCATION_UPDATES, mRequestingLocationUpdates);
        savedInstanceState.putParcelable(KEY_LOCATION, mCurrentLocation);
        savedInstanceState.putString(KEY_LAST_UPDATED_TIME_STRING, mLastUpdateTime);
        super.onSaveInstanceState(savedInstanceState);
    }

    //    Gps location alert
    private void showExitWarningDialog(Context context) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context, R.style.MyDialogStyle);
        alertDialog.setTitle("" + getString(R.string.loc_aleart_content_txt));
        alertDialog.setPositiveButton("" + getApplicationContext().getString(R.string.ok_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                checkLocationSettings();
            }
        });
        alertDialog.setNegativeButton("" + getApplicationContext().getString(R.string.cancel_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        LocalBroadcastManager.getInstance(WelcomeLocationActivity.this).unregisterReceiver(welcom_loc_receiver);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
