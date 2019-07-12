package com.app.oniontray.Activites;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Addaddress;
import com.app.oniontray.RequestModels.AddressType;
import com.app.oniontray.RequestModels.AddressTypeList;
import com.app.oniontray.RequestModels.CityListDa;
import com.app.oniontray.RequestModels.CityRes;
import com.app.oniontray.RequestModels.LocationbasedCity;
import com.app.oniontray.RequestModels.LocationbasedCityDatum;
import com.app.oniontray.Utils.NetworkStatus;
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

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAddressActivity extends LocalizationActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult>, OnMapReadyCallback {

    private Toolbar toolbar;

    TextView add_address_title;

    private static final int FILTER_ID = 1;
    private EditText addr_edt_txt;
    private EditText build_name_edt_txt;
    private EditText land_mark_edt_txt;
    private EditText building_name_edt_txt;

    private TextInputLayout input_layout_name, build_name_txt_input_layout,
            building_name_txt_input_layout, land_mark_txt_input_layout;

    private double latitude;
    private double longitude;

    private ArrayAdapter<AddressTypeList> inputSelectiveAddress;
    private ArrayAdapter<CityListDa> citySpinnerArrayAdapter;
    private ArrayAdapter<LocationbasedCityDatum> locationSpinnerArrayAdapter;
    private Spinner input_Address, citySpinner, locationSpinner;
    private String Address_type = "", cityID = "", locationID = "";
    private GoogleMap Mmap;
    private Marker marker;
    private StringBuilder sb;

    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    Location mLastLocation;

    private static final String TAG = "AddAddressActivity";

    // Location updates intervals in sec
    private static final int UPDATE_INTERVAL = 10000; // 10 sec
    private static final int FATEST_INTERVAL = 5000; // 5 sec
    private static final int DISPLACEMENT = 10; // 10 meters


    private LocationManager location_manager;

    Runnable runnable;

    private boolean edit_add_condi = false;


//    protected static final String TAG = "AddAddressActivity";

    /**
     * Constant used in the location settings dialog.
     */
    private static final int REQUEST_CHECK_SETTINGS = 0x1;

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    // Keys for storing activity state in the Bundle.
    private final static String KEY_REQUESTING_LOCATION_UPDATES = "requesting-location-updates";
    private final static String KEY_LOCATION = "location";
    private final static String KEY_LAST_UPDATED_TIME_STRING = "last-updated-time-string";

    /**
     * Provides the entry point to Google Play services.
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Stores the types of location services the client is interested in using. Used for checking
     * settings to determine if the device has optimal location settings.
     */
    private LocationSettingsRequest mLocationSettingsRequest;

    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the
     * Start Updates and Stop Updates buttons.
     */
    private Boolean mRequestingLocationUpdates;

    /**
     * Time when the location was updated represented as a String.
     */
    private String mLastUpdateTime;

    private boolean first_time_loc_update = true;

    String Screen_flow = "";


    SupportMapFragment mapFragment;

    private boolean firstTimePlaceSearch = false;
    private Place place;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_page);


        add_address_title = findViewById(R.id.add_address_title);
        add_address_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar = (Toolbar) findViewById(R.id.My_add_toolbar_id);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        String language = String.valueOf(LanguageSetting.getLanguage(AddAddressActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Screen_flow = bundle.getString("screen_flow");
        }

        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!NetworkStatus.isConnectingToInternet(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "" + getApplicationContext().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }

        input_Address = (Spinner) findViewById(R.id.inputAddress);
        citySpinner = (Spinner) findViewById(R.id.city_spinner);
        locationSpinner = (Spinner) findViewById(R.id.location_spinner);

        input_layout_name = (TextInputLayout) findViewById(R.id.addr_input_layout);
        addr_edt_txt = (EditText) findViewById(R.id.inputName);
        addr_edt_txt.addTextChangedListener(new MyTextWatcher(addr_edt_txt));


//        build_name_txt_input_layout = (TextInputLayout) findViewById(R.id.build_name_txt_input_layout);
//        build_name_edt_txt = (EditText) findViewById(R.id.build_name_edt_txt);
//        build_name_edt_txt.addTextChangedListener(new MyTextWatcher(build_name_edt_txt));


        building_name_txt_input_layout = (TextInputLayout) findViewById(R.id.building_name_txt_input_layout);
        building_name_edt_txt = (EditText) findViewById(R.id.building_name_edt_txt);
        building_name_edt_txt.addTextChangedListener(new MyTextWatcher(building_name_edt_txt));


        land_mark_txt_input_layout = (TextInputLayout) findViewById(R.id.land_mark_txt_input_layout);
        land_mark_edt_txt = (EditText) findViewById(R.id.land_mark_edt_txt);
        land_mark_edt_txt.addTextChangedListener(new MyTextWatcher(land_mark_edt_txt));

//        Mmap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        InitializeMapView();
        addr_edt_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    firstTimePlaceSearch = true;
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(AddAddressActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        AddresTypeRequestMethod();

//        LoadCitySpinner();


        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";
        // Update values using data stored in the Bundle.
        updateValuesFromBundle(savedInstanceState);

        // Kick off the process of building the GoogleApiClient, LocationRequest, and
        // LocationSettingsRequest objects.
        buildGoogleApiClient();
        createLocationRequest();
        buildLocationSettingsRequest();

        checkLocationSettings();

    }

    public boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    private void InitializeMapView() {
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private void AddresTypeRequestMethod() {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);
        apiService.address_type().enqueue(new Callback<AddressType>() {
            @Override
            public void onResponse(Call<AddressType> call, Response<AddressType> response) {

                if (response.body().getResponse().getHttpCode() == 200) {

                    AddressTypeSpinnerList(response.body().getResponse().getAddressType());

                } else if (response.body().getResponse().getHttpCode() == 400) {
                    Toast.makeText(AddAddressActivity.this, response.body().getResponse().getAddressType().toString(), Toast.LENGTH_SHORT).show();
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
                if (isTablet(AddAddressActivity.this)) {
                    text.setTextSize(18);
                } else {
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

                if (position == getCount()) {
                    text.setText(addressType.get(position).getName());
                    if (isTablet(AddAddressActivity.this)) {
                        text.setTextSize(18);
                    } else {
                        text.setTextSize(14);
                    }
                    text.setTextColor(Color.BLACK);
                } else {
                    text.setHint(getItem(position).toString());
                    text.setText(addressType.get(position).getName());
                    if (isTablet(AddAddressActivity.this)) {
                        text.setTextSize(18);
                    } else {
                        text.setTextSize(14);
                    }
                    text.setTextColor(Color.BLACK);
                }

                return view;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };

        input_Address.setAdapter(inputSelectiveAddress);
        input_Address.setSelection(inputSelectiveAddress.getCount());

        input_Address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (inputSelectiveAddress.getCount() != 0) {
//                    Address_type = String.valueOf(inputSelectiveAddress.getItem(position).getId());
                    Address_type = "" + inputSelectiveAddress.getItem(position).getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

    }


    private void LoadCitySpinner() {

//        Loading city values

        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        apiService.LocationList(loginPrefManager.getStringValue("Lang_code"), getIntent().getStringExtra("country_id")).enqueue(new Callback<CityRes>() {
            @Override
            public void onResponse(Call<CityRes> call, Response<CityRes> response) {
                if (response.body().getResponse().getHttpCode() == 200)
                    CitySpinnerData(response.body().getResponse().getCityList());
            }


            @Override
            public void onFailure(Call<CityRes> call, Throwable t) {

            }
        });

    }

    private void CitySpinnerData(final List<CityListDa> cityList) {

//        setting spinner data for city

        citySpinnerArrayAdapter = new ArrayAdapter<CityListDa>(this, android.R.layout.simple_spinner_dropdown_item, cityList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(cityList.get(position).getCityName());
//                text.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceLarge);
                if (isTablet(AddAddressActivity.this)) {
                    text.setTextSize(18);
                } else {
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

                text.setText(cityList.get(position).getCityName());
                if (isTablet(AddAddressActivity.this)) {
                    text.setTextSize(18);
                } else {
                    text.setTextSize(14);
                }
                text.setTextColor(Color.BLACK);

                return view;
            }

            @Override
            public int getCount() {
                return super.getCount();
            }

        };

        citySpinner.setAdapter(citySpinnerArrayAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                cityID = String.valueOf(citySpinnerArrayAdapter.getItem(i).getId());

                LoadLocationSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void LoadLocationSpinner() {

        try {

            if (progressDialog != null)
                progressDialog.show();

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.locationBasedCityList(loginPrefManager.getStringValue("Lang_code"),
                    cityID).enqueue(new Callback<LocationbasedCity>() {
                @Override
                public void onResponse(Call<LocationbasedCity> call, Response<LocationbasedCity> response) {

                    progressDialog.dismiss();
                    try {
                        if (response.body().getResponse().getHttpCode() == 200) {
                            LocationSpinnerData(response.body().getResponse().getData());
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<LocationbasedCity> call, Throwable t) {

                    progressDialog.dismiss();
                }
            });
        } catch (Exception e) {

        }
    }

    private void LocationSpinnerData(final List<LocationbasedCityDatum> data) {

//        setting spinner data for location

        locationSpinnerArrayAdapter = new ArrayAdapter<LocationbasedCityDatum>(this, android.R.layout.simple_spinner_dropdown_item, data) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(data.get(position).getZoneName());
//                text.setTextAppearance(getApplicationContext(), android.R.attr.textAppearanceLarge);
                text.setTextSize(14);
                text.setTextColor(Color.BLACK);
                return view;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);

                text.setText(data.get(position).getZoneName());
                text.setTextSize(14);
                text.setTextColor(Color.BLACK);


                return view;
            }
        };

        locationSpinner.setAdapter(locationSpinnerArrayAdapter);


        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String locationName = locationSpinnerArrayAdapter.getItem(i).getZoneName();

                locationID = locationSpinnerArrayAdapter.getItem(i).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if (!edit_add_condi) {

            if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
                startLocationUpdates();
            }

        } else {
            edit_add_condi = false;
        }

    }

    private void getAddressFromLatLng(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        sb = new StringBuilder();
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

                    sb.append(addresses.get(0).getAddressLine(i)).append(character);
                    if (firstTimePlaceSearch) {
                        addr_edt_txt.setText(place.getAddress());
                        firstTimePlaceSearch = false;
                    } else {
                        addr_edt_txt.setText(addresses.get(0).getAddressLine(0));
                    }
//                    Log.e("sb", "" + sb);
                }
                if (sb.length() != 0) {

                }
//                    Log.e("address====>", "" + address);
                // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TextView tv = new TextView(this);
        tv.setText("" + getString(R.string.add_addr_done_btn) + "  ");
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        tv.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        tv.setOnClickListener(this);
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.NORMAL);
        tv.setTextSize(16);
        menu.add(0, FILTER_ID, 1, "" + getString(R.string.add_addr_done_btn)).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public void onClick(View v) {
        submit();
        hideKeyboard();
    }

    private void submit() {

        if (!validateAddressType()) {
            return;
        }

        if (!validateAddress()) {
            return;
        }

        if (!validateBuildingName()) {
            return;
        }

        if (progressDialog != null) {
            progressDialog.show();
        }


//        Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//        Log.e("user_token", "" + loginPrefManager.getStringValue("user_token"));
//        Log.e("Lang_code", "" + loginPrefManager.getStringValue("Lang_code"));
//        Log.e("Address_type", "" + Address_type);
//        Log.e("latitude", "" + latitude);
//        Log.e("longitude", "" + longitude);
//        Log.e("addr_edt_txt", "" + addr_edt_txt.getText().toString());
//        Log.e("building_name_edt_txt", "" + building_name_edt_txt.getText().toString().trim());
//        Log.e("getCountryID", "" + loginPrefManager.getCountryID());
//        Log.e("getCityID", "" + loginPrefManager.getCityID());
//        Log.e("getLocID", "" + loginPrefManager.getLocID());

        APIService addService = Webdata.getRetrofit().create(APIService.class);
        addService.store_address(loginPrefManager.getStringValue("user_id"), loginPrefManager.getStringValue("user_token"),
                loginPrefManager.getStringValue("Lang_code"), Address_type, "" + latitude, "" + longitude, addr_edt_txt.getText().toString(),
                "" + building_name_edt_txt.getText().toString().trim(), "" + land_mark_edt_txt.getText().toString().trim(), "" + loginPrefManager.getCountryID(),
                "" + loginPrefManager.getCityID(), "" + loginPrefManager.getLocID()).enqueue(new Callback<Addaddress>() {
            @Override
            public void onResponse(Call<Addaddress> call, Response<Addaddress> response) {

                try {

                    progressDialog.dismiss();
                    if (response.body().getResponse().getHttpCode() == 200) {

                        if (Screen_flow.equals("1")) {

//                            Log.e("LocalBroadcastManager ", "1");

                            Intent address_list = new Intent(AddAddressActivity.this, AddressListingActivity.class);
                            startActivity(address_list);
                            finish();
                        } else if (Screen_flow.equals("3")) {

//                            Log.e("LocalBroadcastManager ", "3");

                            LocalBroadcastManager.getInstance(AddAddressActivity.this).sendBroadcast(new Intent("my_address_receiv"));
                            finish();

                        } else {

//                            Log.e("LocalBroadcastManager ", "else");

                            LocalBroadcastManager.getInstance(AddAddressActivity.this).sendBroadcast(new Intent("address_update"));
                            finish();
                        }


                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(AddAddressActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    Log.e("store_address", "Exception" + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Addaddress> call, Throwable t) {
                progressDialog.dismiss();
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

//            Log.e("getLatitude", "" + mCurrentLocation.getLatitude());
//            Log.e("getLongitude", "" + mCurrentLocation.getLongitude());

            getAddressFromLatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }


        LatLng currentpos = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 15));

        if (marker != null) {
            marker.remove();
        }

        marker = Mmap.addMarker(new MarkerOptions().position(currentpos).draggable(true));

        Mmap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
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

        Mmap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//                LatLng markerLocation = marker.getPosition();
                latitude = latLng.latitude;
                longitude = latLng.longitude;

                marker.setPosition(latLng);

                if (!firstTimePlaceSearch) {
                    getAddressFromLatLng(latitude, longitude);
                } else {
                    firstTimePlaceSearch = false;
                }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

//        Log.e(TAG, "Connected to GoogleApiClient");

        if (first_time_loc_update) {
            if (progressDialog != null) {
                progressDialog.show();
            }
        }


        if (mCurrentLocation != null) {
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
//            LocationUpdateMethod();
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

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        if (first_time_loc_update) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            LocationUpdateMethod();
            first_time_loc_update = false;
            progressDialog.dismiss();
        }

    }


    /**
     * The callback invoked when
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} is called. Examines the
     * {@link com.google.android.gms.location.LocationSettingsResult} object and determines if
     * location settings are adequate. If they are not, begins the process of presenting a location
     * settings dialog to the user.
     */
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
                    status.startResolutionForResult(AddAddressActivity.this, REQUEST_CHECK_SETTINGS);
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

                 place = PlaceAutocomplete.getPlace(this, data);
                 latitude=place.getLatLng().latitude;
                 longitude=place.getLatLng().longitude;

                addr_edt_txt.setText(place.getAddress());

                marker.setPosition(place.getLatLng());
                Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));

//                Log.e("place", "" + place);
//                Log.e("data", "Place: " + place.getAddress());


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
                        showExitWarningDialog(AddAddressActivity.this);
//                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }

    }

    /**
     * Requests location updates from the FusedLocationApi.
     */
    private void startLocationUpdates() {
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = true;
            }
        });
    }

    //      Gps Location Cancel alert
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

    /**
     * Updates fields based on data stored in the bundle.
     *
     * @param savedInstanceState The activity state saved in the Bundle.
     */
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of mRequestingLocationUpdates from the Bundle, and make sure that
            // the Start Updates and Stop Updates buttons are correctly enabled or disabled.
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
            }

            // Update the value of mCurrentLocation from the Bundle and update the UI to show the
            // correct latitude and longitude.
            if (savedInstanceState.keySet().contains(KEY_LOCATION)) {
                // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation
                // is not null.
                mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            }

            // Update the value of mLastUpdateTime from the Bundle and update the UI.
            if (savedInstanceState.keySet().contains(KEY_LAST_UPDATED_TIME_STRING)) {
                mLastUpdateTime = savedInstanceState.getString(KEY_LAST_UPDATED_TIME_STRING);
            }
        }
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    private synchronized void buildGoogleApiClient() {
//        Log.e(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Check if the device's location settings are adequate for the app's needs using the
     * {@link com.google.android.gms.location.SettingsApi#checkLocationSettings(GoogleApiClient,
     * LocationSettingsRequest)} method, with the results provided through a {@code PendingResult}.
     */
    private void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, mLocationSettingsRequest);
        result.setResultCallback(this);
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                mRequestingLocationUpdates = false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap = googleMap;
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
        Mmap.setMyLocationEnabled(true);
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

        @Override
        public void afterTextChanged(Editable s) {

            switch (view.getId()) {
                case R.id.inputName:
                    validateAddress();
                    break;
                case R.id.building_name_edt_txt:
                    validateBuildingName();
                    break;
//                case R.id.land_mark_edt_txt:
//                    validateLandMark();
//                    break;
            }

        }
    }

    private boolean validateAddressType() {

        if (Address_type.trim().isEmpty()) {
            Toast.makeText(AddAddressActivity.this, "" + getString(R.string.err_msg_select_address_type), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateAddress() {

        if (addr_edt_txt.getText().toString().trim().isEmpty()) {
            input_layout_name.setError(getString(R.string.err_msg_address));
            requestFocus(addr_edt_txt);
            return false;
        } else {
            input_layout_name.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateBuildingName() {

        if (building_name_edt_txt.getText().toString().trim().isEmpty()) {
            building_name_txt_input_layout.setError(getString(R.string.build_name_required));
            requestFocus(building_name_edt_txt);
            return false;
        } else {
            building_name_txt_input_layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLandMark() {

        if (land_mark_edt_txt.getText().toString().trim().isEmpty()) {
            land_mark_txt_input_layout.setError(getString(R.string.err_msg_land_mark));
            requestFocus(land_mark_edt_txt);
            return false;
        } else {
            land_mark_txt_input_layout.setErrorEnabled(false);
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
        if (focusedView != null)

        {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
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
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("" + getString(R.string.add_loc_err_txt));
        // Setting Dialog Message
        alertDialog.setMessage("" + getString(R.string.add_loc_ple_enable_loc_txt));
        // On pressing Settings button
        alertDialog.setPositiveButton(getResources().getString(R.string.ok_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton(getResources().getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        alertDialog.show();

    }

//    public AddAddressActivityInterface addAddressActivityInterface;
//
//    public void AddAddressActivityInterfaceMethod(AddAddressActivityInterface addAddressActivityInterface){
//        this.addAddressActivityInterface = addAddressActivityInterface;
//    }
//
//    public interface AddAddressActivityInterface{
//        void ReloadMyAddressMethod();
//    }

}
