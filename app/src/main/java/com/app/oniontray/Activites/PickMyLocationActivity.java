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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.PicMyLocAddressAdapter;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.PickMyLocAddItemDecoration;
import com.app.oniontray.RequestModels.AddressList;
import com.app.oniontray.RequestModels.AddressListing;
import com.app.oniontray.RequestModels.AutoDetectLocation;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NEXTBRAIN on 3/12/2017.
 */

public class PickMyLocationActivity extends LocalizationActivity implements PicMyLocAddressAdapter.PicMyLocAddressAdapterInterface,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ResultCallback<LocationSettingsResult> {


    private final String TAG = "PickMyLocationActivity";

    private final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    private final int REQUEST_CHECK_SETTINGS = 0x1;

    private final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    private final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

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


    private ImageView home_search_back_img;

    private TextView pic_my_loc_search_edt_txt_view;

    private TextView pick_my_loc_use_my_loca_txt_view;

    private LinearLayout pic_my_loc_saved_addr_root_layout;

    private RecyclerView pick_my_loc_saved_addre_recycler_view;
    private PicMyLocAddressAdapter picMyLocAddressAdapter;

    private TextView saved_address_emty_txt_view;

    private Button pic_my_loc_change_country_btn;

    private String Latitude = "";
    private String Longtitude = "";

    private BroadcastReceiver pic_my_loc_BroadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_my_loc_layout);

        Init();


        mRequestingLocationUpdates = false;
        mLastUpdateTime = "";

        updateValuesFromBundle(savedInstanceState);

        buildGoogleApiClient();

        createLocationRequest();

        buildLocationSettingsRequest();

        checkLocationSettings();

        pic_my_loc_BroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        LocalBroadcastManager.getInstance(PickMyLocationActivity.this).registerReceiver(pic_my_loc_BroadcastReceiver, new IntentFilter("pic_my_loc_receiver"));

    }

    private void Init() {

        home_search_back_img = (ImageView) findViewById(R.id.home_search_back_img);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
        upArrow.setColorFilter(Color.parseColor(loginPrefManager.getToolbarIconcolor()), PorterDuff.Mode.SRC_ATOP);
        home_search_back_img.setImageDrawable(upArrow);

        pic_my_loc_search_edt_txt_view = (TextView) findViewById(R.id.pic_my_loc_search_edt_txt_view);

        pick_my_loc_use_my_loca_txt_view = (TextView) findViewById(R.id.pick_my_loc_use_my_loca_txt_view);
       // pick_my_loc_use_my_loca_txt_view.setTextColor(getResources().getColor(R.color.colorPrimary));
        pick_my_loc_use_my_loca_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));



        //pick_my_loc_use_my_loca_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //pick_my_loc_use_my_loca_txt_view.setCompoundDrawableTintList(colorStateList);
            pick_my_loc_use_my_loca_txt_view.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        pic_my_loc_saved_addr_root_layout = (LinearLayout) findViewById(R.id.pic_my_loc_saved_addr_root_layout);

        pick_my_loc_saved_addre_recycler_view = (RecyclerView) findViewById(R.id.pick_my_loc_saved_addre_recycler_view);
        pick_my_loc_saved_addre_recycler_view.setLayoutManager(new LinearLayoutManager(PickMyLocationActivity.this));
        pick_my_loc_saved_addre_recycler_view.setHasFixedSize(true);
        pick_my_loc_saved_addre_recycler_view.addItemDecoration(new PickMyLocAddItemDecoration(PickMyLocationActivity.this, R.dimen.ho_search_divider_line_size));

        picMyLocAddressAdapter = new PicMyLocAddressAdapter(PickMyLocationActivity.this, new ArrayList<AddressList>());
        picMyLocAddressAdapter.PicMyLocAddrAddapterInterfaceMethod(PickMyLocationActivity.this);
        pick_my_loc_saved_addre_recycler_view.setAdapter(picMyLocAddressAdapter);

        saved_address_emty_txt_view = (TextView) findViewById(R.id.saved_address_emty_txt_view);

        pic_my_loc_change_country_btn = (Button) findViewById(R.id.pic_my_loc_change_country_btn);
        pic_my_loc_change_country_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        pic_my_loc_change_country_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        if (!loginPrefManager.getStringValue("user_id").isEmpty()) {

            pic_my_loc_saved_addr_root_layout.setVisibility(View.VISIBLE);

            GetMySavedAddressRequestMethod();

        } else {
            pic_my_loc_saved_addr_root_layout.setVisibility(View.GONE);
        }

        ClickEventMethod();

    }

    private void ClickEventMethod() {

        home_search_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pic_my_loc_search_edt_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PickMyLocationActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });

        pick_my_loc_use_my_loca_txt_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoDetectLocationAction();
            }
        });

        pic_my_loc_change_country_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity = new Intent(PickMyLocationActivity.this, SelectLocationActivity.class);
                signInActivity.putExtra("screen_flow", "2");
                signInActivity.putExtra("country_id", "" + loginPrefManager.getCountryID());
                signInActivity.putExtra("country_name", "" + loginPrefManager.getCountryName());
                signInActivity.putExtra("city_id", "" + loginPrefManager.getCityID());
                signInActivity.putExtra("city_name", "" + loginPrefManager.getCityName());
                signInActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(signInActivity);
            }
        });

    }

    private void GetMySavedAddressRequestMethod() {

        try {

//            Log.e("user_id", loginPrefManager.getStringValue("user_id"));
//            Log.e("user_token", loginPrefManager.getStringValue("user_token"));

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.get_address("" + loginPrefManager.getStringValue("user_id"), "" + loginPrefManager.getStringValue("Lang_code"),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<AddressListing>() {
                @Override
                public void onResponse(Call<AddressListing> call, Response<AddressListing> response) {

                    try {

                        progressDialog.dismiss();

//                        Log.e("onResponse", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            picMyLocAddressAdapter.UpdateMyAddressAdapter(response.body().getResponse().getAddressList());

                            if (response.body().getResponse().getAddressList().size() == 0) {
                                saved_address_emty_txt_view.setVisibility(View.VISIBLE);
                            } else {
                                saved_address_emty_txt_view.setVisibility(View.GONE);
                            }

                        } else {
                            if (response.body().getResponse().getAddressList().size() == 0) {
                                saved_address_emty_txt_view.setVisibility(View.VISIBLE);
                            } else {
                                saved_address_emty_txt_view.setVisibility(View.GONE);
                            }
                        }

                    } catch (Exception e) {
//                        Log.e("Exception", "-" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AddressListing> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("onFailure", "-" + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
//            Log.e("get_address Api ", "Exception " + e.getMessage());
        }


    }

    @Override
    public void PicMyLocAddEmptyViewInterface(int array_size) {

    }

    @Override
    public void PicMyLocClickEventInterface(AddressList addressList) {

        loginPrefManager.clearCartForOtherLocation("" + addressList.getLocationId());
        loginPrefManager.setCountryIDandName("" + addressList.getCountryId(), "" + addressList.getCountryName());
        loginPrefManager.setCityIDandName("" + addressList.getCityId(), "" + addressList.getCityName());
        loginPrefManager.setLocIDandName("" + addressList.getLocationId(), "" + addressList.getZoneName());

        finish();

    }

    private void AutoDetectLocationAction() {

        if (mCurrentLocation != null) {

            if (Double.isNaN(mCurrentLocation.getLongitude())) {
                Toast.makeText(PickMyLocationActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
            } else {

                Latitude = String.valueOf(mCurrentLocation.getLatitude());
                Longtitude = String.valueOf(mCurrentLocation.getLongitude());

                LocationApiRequest();
            }

        } else {
            Toast.makeText(PickMyLocationActivity.this, "" + getString(R.string.curr_loc_not_upd_msg_txt), Toast.LENGTH_SHORT).show();
        }

    }


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

        mGoogleApiClient.connect();
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
//                Log.e(TAG, "Location settings are not satisfied. Show the user a dialog to" + "upgrade location settings ");
                try {
                    status.startResolutionForResult(PickMyLocationActivity.this, REQUEST_CHECK_SETTINGS);
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

                pic_my_loc_search_edt_txt_view.setText("" + place.getAddress());

                Latitude = String.valueOf(place.getLatLng().latitude);
                Longtitude = String.valueOf(place.getLatLng().longitude);

                LocationApiRequest();

//                marker.setPosition(place.getLatLng());
//                Mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
//
//                Log.e("place", "" + place);
//                Log.e("data", "Place: " + place.getAddress());
//
//                addr_edt_txt.setText(place.getAddress());

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
                        showExitWarningDialog(PickMyLocationActivity.this);
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
            public void onResult(@NonNull Status status) {
                mRequestingLocationUpdates = true;
            }
        });
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
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

//        Log.e("getLatitude() - " + mCurrentLocation.getLatitude(), "getLongitude() - " + mCurrentLocation.getLongitude());

//        LocationUpdateMethod("" + mCurrentLocation.getLatitude(), "" + mCurrentLocation.getLongitude());

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

//        Log.e(TAG, "Connected to GoogleApiClient");

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
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.e(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
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
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }


    private void LocationApiRequest() {

        APIService apiService = Webdata.getRetrofit().create(APIService.class);

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            apiService.auto_detect_location(loginPrefManager.getStringValue("Lang_code"), Latitude, Longtitude).enqueue(new Callback<AutoDetectLocation>() {
                @Override
                public void onResponse(Call<AutoDetectLocation> call, Response<AutoDetectLocation> response) {

                    try {

                        progressDialog.dismiss();

                        if (response.body().getResponse().getHttpCode() == 200) {
//                            Log.e("city and location id", "" + response.body().getResponse().getCityId());
//                            Log.e("location id", "" + response.body().getResponse().getLocationId());

                            loginPrefManager.clearCartForOtherLocation( "" + response.body().getResponse().getLocationId());
                            loginPrefManager.setCountryIDandName("" + response.body().getResponse().getCountryId(), "" + response.body().getResponse().getCountryName());
                            loginPrefManager.setCityIDandName("" + response.body().getResponse().getCityId(), "" + response.body().getResponse().getCityName());
                            loginPrefManager.setLocIDandName("" + response.body().getResponse().getLocationId(), "" + response.body().getResponse().getLocationName());
                            Intent welcom_intent = new Intent("base_activity_receiver");
                            welcom_intent.putExtra("page_name", "0");
                            LocalBroadcastManager.getInstance(PickMyLocationActivity.this).sendBroadcast(welcom_intent);

//                            startActivity(new Intent(PickMyLocationActivity.this, BaseMenuTabActivity.class));

                            finish();

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            NoRestaurantFound();
                        }
                    } catch (Exception e) {
//                        Log.e("res", e.toString());
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
                    }
                });
        alertDialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
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
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
//        Log.e("LocationService", "onDestroy");

        LocalBroadcastManager.getInstance(PickMyLocationActivity.this).unregisterReceiver(pic_my_loc_BroadcastReceiver);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
