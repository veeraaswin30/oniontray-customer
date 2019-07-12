package com.app.oniontray.Activites;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.CountryBasedCityAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.AutoDetectLocation;
import com.app.oniontray.RequestModels.CityListDa;
import com.app.oniontray.RequestModels.CityRes;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by NEXTBRAIN on 2/5/2017.
 */

public class SelectCityListActivity extends LocalizationActivity implements CountryBasedCityAdapter.CityAdapterinterface,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {

    private Toolbar select_city_toolbar;

    private TextView select_city;

    private TextView select_city_title, select_city_titl;
    private TextView city_loc_empty_txt_view;

    private RecyclerView select_city_recycler_view;
    private Button select_city_next_btn;

    private ArrayList<CityListDa> city_list_items = new ArrayList<>();
    private CountryBasedCityAdapter countryBasedCityAdapter;

    private String country_id = "";
    private String country_name = "";

    private BroadcastReceiver selec_city_receiver;


    private static final String TAG = "SelectCityListActivity";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city_layout);
        select_city = findViewById(R.id.select_city);
        select_city.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            country_id = bundle.getString("country_id");
            country_name = bundle.getString("country_name");
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

    private void initReferMethod() {


        select_city_toolbar = (Toolbar) findViewById(R.id.select_city_toolbar);
        select_city_toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        select_city_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        select_city_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(select_city_toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(SelectCityListActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }


        select_city_title = (TextView) findViewById(R.id.select_city_title);
//        select_city_title.setText("" + getString(R.string.city));
        select_city_title.setText("" + country_name);


        city_loc_empty_txt_view = (TextView) findViewById(R.id.city_loc_empty_txt_view);
        city_loc_empty_txt_view.setText("" + getString(R.string.no_city_found_txt));


        select_city_recycler_view = (RecyclerView) findViewById(R.id.select_city_recycler_view);
        select_city_recycler_view.setHasFixedSize(true);
        select_city_recycler_view.setLayoutManager(new LinearLayoutManager(SelectCityListActivity.this));
        select_city_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(SelectCityListActivity.this, R.dimen.prod_list_item_row_line_height));

        select_city_next_btn = (Button) findViewById(R.id.select_city_next_btn);
        select_city_next_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        select_city_next_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        clickActionMethods();

        CityListRequestMethod();


        selec_city_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        LocalBroadcastManager.getInstance(SelectCityListActivity.this).registerReceiver(selec_city_receiver, new IntentFilter("select_city_receiver"));


    }


    private void clickActionMethods() {

        select_city_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // auto detect location click action
        select_city_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AutoDetectLocationAction();

            }
        });

    }

    private void CityListRequestMethod() {

        if (progressDialog != null) {
            progressDialog.show();
        }

        APIService addService = Webdata.getNoteRetrofit().create(APIService.class);
        addService.LocationList("" + loginPrefManager.getStringValue("Lang_code"), "" + country_id).enqueue(new Callback<CityRes>() {
            @Override
            public void onResponse(Call<CityRes> call, Response<CityRes> response) {

                progressDialog.dismiss();

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {
//                        Log.e("getCityList", "-" + response.body().getResponse().getHttpCode());
                        city_list_items = (ArrayList<CityListDa>) response.body().getResponse().getCityList();
                        CreateCityListAdapter();

                        SetEmptyViewMethod(response.body().getResponse().getCityList().size());

                    } else {
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<CityRes> call, Throwable t) {
                progressDialog.dismiss();
            }

        });

    }

    private void CreateCityListAdapter() {
        countryBasedCityAdapter = new CountryBasedCityAdapter(SelectCityListActivity.this, city_list_items);
        countryBasedCityAdapter.CallCityAdapterInterface(SelectCityListActivity.this);
        select_city_recycler_view.setAdapter(countryBasedCityAdapter);
    }

    @Override
    public void selectedCityvalue() {
        CitySelectAction();
    }

    public void CitySelectAction() {

        if (countryBasedCityAdapter != null) {

            if (countryBasedCityAdapter.getSelectedCity().getCityName() == null) {
                Toast.makeText(SelectCityListActivity.this, "" + getString(R.string.err_msg_select_a_city_txt), Toast.LENGTH_SHORT).show();
            } else {
                Intent city_intent = new Intent(SelectCityListActivity.this, SelectLocationActivity.class);
                city_intent.putExtra("screen_flow", "1");
                city_intent.putExtra("country_id", "" + country_id);
                city_intent.putExtra("country_name", "" + country_name);
                city_intent.putExtra("city_id", "" + countryBasedCityAdapter.getSelectedCity().getId());
                city_intent.putExtra("city_name", "" + countryBasedCityAdapter.getSelectedCity().getCityName());
                city_intent.putExtra("wel_loc_flow", "" + wel_loc_flow);
                startActivity(city_intent);
            }

        }

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
                    status.startResolutionForResult(SelectCityListActivity.this, REQUEST_CHECK_SETTINGS);
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
                        showExitWarningDialog(SelectCityListActivity.this);
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
                Toast.makeText(SelectCityListActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
            } else {
                LocationApiRequest();
            }

        } else {
            Toast.makeText(SelectCityListActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
        }

    }

    private void LocationApiRequest() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.auto_detect_location(loginPrefManager.getStringValue("Lang_code"), String.valueOf(mCurrentLocation.getLatitude()), String.valueOf(mCurrentLocation.getLongitude())).enqueue(new Callback<AutoDetectLocation>() {
                @Override
                public void onResponse(Call<AutoDetectLocation> call, Response<AutoDetectLocation> response) {

                    try {

                        Log.e("auto_detect_location",""+ response.raw().toString());

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (!loginPrefManager.getLocID().equals("" + response.body().getResponse().getLocationId())) {
                                productRespository.ClearCart();
                            }

                            loginPrefManager.clearCartForOtherLocation( "" + response.body().getResponse().getLocationId());
                            loginPrefManager.setCountryIDandName("" + response.body().getResponse().getCountryId(), "" + response.body().getResponse().getCountryName());
                            loginPrefManager.setCityIDandName("" + response.body().getResponse().getCityId(), "" + response.body().getResponse().getCityName());
                            loginPrefManager.setLocIDandName("" + response.body().getResponse().getLocationId(), "" + response.body().getResponse().getLocationName());


                            if(wel_loc_flow.equals("0")){

                                LocalBroadcastManager.getInstance(SelectCityListActivity.this).sendBroadcast(new Intent("select_country_receiver"));
                                LocalBroadcastManager.getInstance(SelectCityListActivity.this).sendBroadcast(new Intent("welco_loc_receiver").putExtra("status", "0"));

                                startActivity(new Intent(SelectCityListActivity.this, BaseMenuTabActivity.class));

                                finish();

                            }else if(wel_loc_flow.equals("1")){

                                LocalBroadcastManager.getInstance(SelectCityListActivity.this).sendBroadcast(new Intent("select_country_receiver"));
                                LocalBroadcastManager.getInstance(SelectCityListActivity.this).sendBroadcast(new Intent("settings_receiver"));

                                Intent welcom_intent = new Intent("base_activity_receiver");
                                welcom_intent.putExtra("page_name", "0");
                                LocalBroadcastManager.getInstance(SelectCityListActivity.this).sendBroadcast(welcom_intent);

                                finish();
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
        LocalBroadcastManager.getInstance(SelectCityListActivity.this).unregisterReceiver(selec_city_receiver);
    }

}
