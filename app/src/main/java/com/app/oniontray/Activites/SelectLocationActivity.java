package com.app.oniontray.Activites;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.app.oniontray.Adapters.CityBasedLocationAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.AutoDetectLocation;
import com.app.oniontray.RequestModels.LocListApiResp;
import com.app.oniontray.RequestModels.LocaListData;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.android.gms.common.ConnectionResult;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLocationActivity extends LocalizationActivity implements CityBasedLocationAdapter.LocationAdapterInterface,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {

    private Toolbar select_city_toolbar;

    private TextView select_city_title, select_area1;

    private TextView city_loc_empty_txt_view;

    private RecyclerView select_city_recycler_view;

    private Button select_city_next_btn;

    private ArrayList<LocaListData> loc_list_items;

    private CityBasedLocationAdapter city_list_BasedLocationAdapter;


    private String country_id = "";
    private String country_name = "";

    private String city_id = "";
    private String city_name = "";

    private String screen_flow = "";

    SearchView searchView;


    private static final String TAG = "SelectLocationActivity";

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


    private String wel_loc_flow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city_layout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            screen_flow = bundle.getString("screen_flow");

            country_id = bundle.getString("country_id");
            country_name = bundle.getString("country_name");

            city_id = bundle.getString("city_id");
            city_name = bundle.getString("city_name");

            wel_loc_flow = bundle.getString("wel_loc_flow");

        }


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


        initReferMethod();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);


        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ImageView iconClose = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        iconClose.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()));
        //change search icon color
        ImageView iconSearch = searchView.findViewById(androidx.appcompat.R.id.search_button);
        iconSearch.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()));


        EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditText.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        searchEditText.setHint(getString(R.string.search_area_loc_hint_txt));
        searchEditText.setHintTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        // searchEditText.setHintTextColor(getResources().getColor(R.color.colorPrimary));
        searchEditText.setTextSize(15);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        final List<LocaListData> filteredModelList = searchFilter(loc_list_items, query);
                        if (city_list_BasedLocationAdapter != null) {
                            city_list_BasedLocationAdapter.setFilter(filteredModelList);
                            SetEmptyViewMethod(filteredModelList.size());
                        }

                        return false;

                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if (newText.isEmpty()) {
                            if (city_list_BasedLocationAdapter != null) {
                                city_list_BasedLocationAdapter.setFilter(loc_list_items);
                                SetEmptyViewMethod(loc_list_items.size());
                            }
                        } else {

                            final List<LocaListData> filteredModelList = searchFilter(loc_list_items, newText);
                            if (city_list_BasedLocationAdapter != null) {
                                city_list_BasedLocationAdapter.setFilter(filteredModelList);
                                SetEmptyViewMethod(filteredModelList.size());
                            }
                        }
                        return false;
                    }
                });

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
//                searchView.setIconified(false);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initReferMethod() {

        select_city_toolbar = (Toolbar) findViewById(R.id.select_city_toolbar);
        select_city_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        select_city_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        select_city_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        setSupportActionBar(select_city_toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(SelectLocationActivity.this));


        if (language.equals("en")) {
            //  getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        select_city_title = (TextView) findViewById(R.id.select_city_title);


        TextView select_city = findViewById(R.id.select_city);
        select_city.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        select_city.setText(R.string.select_area_manulay_txt);


        select_city_title.setText(country_name + "/ " + city_name);
        //  select_area.setText(getString(R.string.select_area_manulay_txt));


        city_loc_empty_txt_view = (TextView) findViewById(R.id.city_loc_empty_txt_view);
        city_loc_empty_txt_view.setText(getString(R.string.no_location_found_txt));


        select_city_next_btn = (Button) findViewById(R.id.select_city_next_btn);
        select_city_next_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        select_city_next_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        select_city_recycler_view = (RecyclerView) findViewById(R.id.select_city_recycler_view);
        select_city_recycler_view.setHasFixedSize(true);
        select_city_recycler_view.setLayoutManager(new LinearLayoutManager(SelectLocationActivity.this));
        select_city_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(SelectLocationActivity.this, R.dimen.prod_list_item_row_line_height));

        clickActionMethods();

        CityListRequestMethod();

    }

    private void clickActionMethods() {

        select_city_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        select_city_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));
//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("welco_loc_receiver").putExtra("status", "1"));

                AutoDetectLocationAction();

            }
        });

    }

    private void CityListRequestMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService addService = Webdata.getNoteRetrofit().create(APIService.class);
        addService.CityBasedLocationList("" + loginPrefManager.getStringValue("Lang_code"), "" + country_id, "" + city_id).enqueue(new Callback<LocListApiResp>() {
            @Override
            public void onResponse(Call<LocListApiResp> call, Response<LocListApiResp> response) {

                progressDialog.dismiss();

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

//                        Log.e("getCityList", "-" + response.body().getResponse().getHttpCode());
                        loc_list_items = (ArrayList<LocaListData>) response.body().getResponse().getLocationList();
                        CreateLocationListAdapter();

                        SetEmptyViewMethod(response.body().getResponse().getLocationList().size());
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<LocListApiResp> call, Throwable t) {
                progressDialog.dismiss();
            }

        });

    }

    private void CreateLocationListAdapter() {
        city_list_BasedLocationAdapter = new CityBasedLocationAdapter(SelectLocationActivity.this, loc_list_items);
        select_city_recycler_view.setAdapter(city_list_BasedLocationAdapter);
        city_list_BasedLocationAdapter.CallLocationAdapterInterface(SelectLocationActivity.this);
    }


    private void SetEmptyViewMethod(int count) {
        if (count == 0) {
            city_loc_empty_txt_view.setVisibility(View.VISIBLE);
            select_city_next_btn.setVisibility(View.GONE);
        } else {
            city_loc_empty_txt_view.setVisibility(View.GONE);
            select_city_next_btn.setVisibility(View.VISIBLE);
        }
    }


    private List<LocaListData> searchFilter(List<LocaListData> models, String query) {
        query = query.toLowerCase().toLowerCase();
        final List<LocaListData> filteredModelList = new ArrayList<>();
        for (LocaListData model : models) {
            final String text = model.getZoneName().toLowerCase().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void selectedCountryValue() {
        LocationSelectAction();
    }

    public void LocationSelectAction() {

        if (city_list_BasedLocationAdapter != null) {

            if (city_list_BasedLocationAdapter.getSelectedLocation().getZoneName() == null) {
                Toast.makeText(SelectLocationActivity.this, "" + getString(R.string.err_msg_select_a_location_txt), Toast.LENGTH_SHORT).show();
            } else {

                Log.e("screen_flow", "" + screen_flow);
                Log.e("wel_loc_flow", "" + wel_loc_flow);

                loginPrefManager.clearCartForOtherLocation("" + city_list_BasedLocationAdapter.getSelectedLocation().getId());

                loginPrefManager.setCountryIDandName("" + country_id, "" + country_name);
                loginPrefManager.setCityIDandName("" + city_id, "" + city_name);
                loginPrefManager.setLocIDandName("" + city_list_BasedLocationAdapter.getSelectedLocation().getId(), "" + city_list_BasedLocationAdapter.getSelectedLocation().getZoneName());


                if (screen_flow.equals("2")) {

                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("pic_my_loc_receiver"));
                    finish();

                } else if (screen_flow.equals("1")) {

                    if (wel_loc_flow.equals("0")) {

                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));

                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("welco_loc_receiver").putExtra("status", "0"));

                        startActivity(new Intent(SelectLocationActivity.this, BaseMenuTabActivity.class));

                        finish();

                    } else if (wel_loc_flow.equals("1")) {

                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));

                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("settings_receiver"));

                        Intent welcom_intent = new Intent("base_activity_receiver");
                        welcom_intent.putExtra("page_name", "0");
                        LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(welcom_intent);

                        finish();

                    }

                }

//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("welco_loc_receiver").putExtra("status", "0"));
//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));
//                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
//
//                Intent city_intent = new Intent(SelectLocationActivity.this, BaseMenuTabActivity.class);
//                startActivity(city_intent);
//                finish();

            }

        }

    }


    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.keySet().contains(KEY_REQUESTING_LOCATION_UPDATES)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        KEY_REQUESTING_LOCATION_UPDATES);
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
                    status.startResolutionForResult(SelectLocationActivity.this, REQUEST_CHECK_SETTINGS);
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
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
//                        Log.e(TAG, "User agreed to make required location settings changes.");
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        showExitWarningDialog(SelectLocationActivity.this);
//                        Log.e(TAG, "User chose not to make required location settings changes.");
                        break;
                }
                break;
        }
    }


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
    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.e(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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


    private void AutoDetectLocationAction() {

        if (mCurrentLocation != null) {

            if (Double.isNaN(mCurrentLocation.getLongitude())) {
                Toast.makeText(SelectLocationActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
            } else {
                LocationApiRequest();
            }

        } else {
            Toast.makeText(SelectLocationActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
        }

    }

    private void LocationApiRequest() {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            apiService.auto_detect_location(loginPrefManager.getStringValue("Lang_code"), String.valueOf(mCurrentLocation.getLatitude()),
                    String.valueOf(mCurrentLocation.getLongitude())).enqueue(new Callback<AutoDetectLocation>() {
                @Override
                public void onResponse(Call<AutoDetectLocation> call, Response<AutoDetectLocation> response) {

                    try {

                        Log.e("auto_detect_location", "" + response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (screen_flow.equals("2")) {

                                if (!loginPrefManager.getLocID().equals("" + response.body().getResponse().getLocationId())) {
                                    productRespository.ClearCart();
                                }

                                loginPrefManager.setCountryIDandName("" + response.body().getResponse().getCountryId(), "" + response.body().getResponse().getCountryName());
                                loginPrefManager.setCityIDandName("" + response.body().getResponse().getCityId(), "" + response.body().getResponse().getCityName());
                                loginPrefManager.setLocIDandName("" + response.body().getResponse().getLocationId(), "" + response.body().getResponse().getLocationName());

                                LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("pic_my_loc_receiver"));
                                finish();

                            } else if (screen_flow.equals("1")) {

                                if (wel_loc_flow.equals("0")) {

                                    Log.e("city and location id", "" + response.body().getResponse().getCityId());
                                    Log.e("location id", "" + response.body().getResponse().getLocationId());

                                    if (!loginPrefManager.getLocID().equals("" + response.body().getResponse().getLocationId())) {
                                        productRespository.ClearCart();
                                    }

                                    loginPrefManager.setCountryIDandName("" + response.body().getResponse().getCountryId(), "" + response.body().getResponse().getCountryName());
                                    loginPrefManager.setCityIDandName("" + response.body().getResponse().getCityId(), "" + response.body().getResponse().getCityName());
                                    loginPrefManager.setLocIDandName("" + response.body().getResponse().getLocationId(), "" + response.body().getResponse().getLocationName());


                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));

                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("welco_loc_receiver").putExtra("status", "0"));

                                    startActivity(new Intent(SelectLocationActivity.this, BaseMenuTabActivity.class));

                                    finish();

                                } else if (wel_loc_flow.equals("1")) {

                                    if (!loginPrefManager.getLocID().equals("" + response.body().getResponse().getLocationId())) {
                                        productRespository.ClearCart();
                                    }

                                    loginPrefManager.setCountryIDandName("" + response.body().getResponse().getCountryId(), "" + response.body().getResponse().getCountryName());
                                    loginPrefManager.setCityIDandName("" + response.body().getResponse().getCityId(), "" + response.body().getResponse().getCityName());
                                    loginPrefManager.setLocIDandName("" + response.body().getResponse().getLocationId(), "" + response.body().getResponse().getLocationName());


                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_city_receiver"));
                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("select_country_receiver"));

                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(new Intent("settings_receiver"));

                                    Intent welcom_intent = new Intent("base_activity_receiver");
                                    welcom_intent.putExtra("page_name", "0");
                                    LocalBroadcastManager.getInstance(SelectLocationActivity.this).sendBroadcast(welcom_intent);

                                    finish();

                                }

                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            NoRestaurantFound();
                        }
                    } catch (Exception e) {
                        Log.e("Exception", e.toString());
                    }
                }

                @Override
                public void onFailure(Call<AutoDetectLocation> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
            progressDialog.dismiss();
        }
    }

    public void NoRestaurantFound() {

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));
        alertDialog.setMessage("" + getString(R.string.no_restaurant_available));

        alertDialog.setPositiveButton("" + getString(R.string.ok_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
//                        CallSignInActivityMethod();
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
    }


}
