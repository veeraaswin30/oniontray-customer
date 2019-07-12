package com.app.oniontray.Activites;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CustomViews.CircleImageView;
import com.app.oniontray.CustomViews.WorkaroundMapFragment;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.DriverLocDetRes;
import com.app.oniontray.Utils.DirectionsJSONParser;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.Glide;
import com.directions.route.AbstractRouting;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.asin;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;


/**
 * Created by nextbrain on 20/6/17.
 */

public class TrackOrderStatesActivity extends LocalizationActivity implements RoutingListener {


    private Toolbar track_order_tool_bar;


    private String Order_id = "";
    private String Screen_Flow = "";


    private String Driver_Mobile_No = "";


    private String distance = "";
    private String duration = "";


    private ArrayList<LatLng> markerPoints;
    private LatLng origin = new LatLng(12.925007, 79.593803);
    private LatLng destination = new LatLng(37.7814432, -122.4460177);

    private double driv_latitude;
    private double driv_longitude;


    private double user_latitude;
    private double user_longtitude;


    private LatLng driverLatLng=null;
    private LatLng userLatLng;

    private LatLng driverPreviousLatLng=null;
//    private Runnable runnable;


    private WorkaroundMapFragment track_order_map;
    private GoogleMap googleMap;
    private PolylineOptions polylineOptions, redPolylineOptions;
    private Polyline lightPolyline, redPolyLine;


    private CircleImageView track_order_driver_pic_cir_img_view;
    private TextView track_order_states_msg_txt_view;
    private ImageView track_order_msg_ic_img_view;


    private TextView track_order_items_count_txt_view;
    private TextView track_order_delv_time_txt_view;
    private TextView track_order_payable_amt_txt_view;


    private DirectionsJSONParser parser;

    Handler h = new Handler();
    int delay = 5 * 1000; //1 second=1000 milisecond, 15*1000=15seconds
    Runnable runnable;
    private Marker originMarker, destinationMarker,carMarker;
    private List<LatLng> polyLineList;
    private String TAG=TrackOrderStatesActivity.class.getSimpleName();
    private Handler handler1=new Handler();
    private LatLng startPosition, endPosition;
    private int index, next;
    private float v;
    private double lat, lng;
    LatLng currentLatLng, oldLatLng,startLAtLng;
    int start_time = 0;
    private List<Polyline> polylines;
    private static final int[] COLORS = new int[]{R.color.colorPrimary};
    private Boolean firstime=true;
    private Marker driverMarker; // Marker to display driver's location
    Bitmap mMarkerIcon;
    private int mIndexCurrentPoint=0;
    Runnable DrawPathRunnable =new Runnable() {
        @Override
        public void run() {
            if (index < polyLineList.size() - 1) {
                index++;
                next = index + 1;
            }
            if (index < polyLineList.size() - 1) {
                startPosition = polyLineList.get(index);
                endPosition = polyLineList.get(next);
            }
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(3000);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    v = valueAnimator.getAnimatedFraction();
                    lng = v * endPosition.longitude + (1 - v)
                            * startPosition.longitude;
                    lat = v * endPosition.latitude + (1 - v)
                            * startPosition.latitude;
                    LatLng newPos = new LatLng(lat, lng);
                    carMarker.setPosition(newPos);
                    carMarker.setAnchor(0.5f, 0.5f);
                    carMarker.setRotation(getBearing(startPosition, newPos));
                    googleMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition
                                    (new CameraPosition.Builder()
                                            .target(newPos)
                                            .zoom(15.5f)
                                            .build()));
                }
            });
            valueAnimator.start();
        }
    };
    private PolylineOptions polyOptions;
    private Polyline polyline;
    private long TURN_ANIMATION_DURATION=1000;
    private long MOVE_ANIMATION_DURATION=5000;
    private AlertDialog show;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_order_delivery_status_layout);

        polylines = new ArrayList<>();
        polyLineList = new ArrayList<>();
        track_order_tool_bar = (Toolbar) findViewById(R.id.track_order_toolbar);
        track_order_tool_bar.setTitle("");

        track_order_tool_bar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        track_order_tool_bar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        setSupportActionBar(track_order_tool_bar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(TrackOrderStatesActivity.this));


        if (language.equals("en")) {
           // getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);

        } else {
/*
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);
*/
        }

        track_order_tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageScreenFlowMethod();
            }
        });


        if (getIntent() != null) {
            if (getIntent().hasExtra("order_id")) {
                this.Order_id = getIntent().getStringExtra("order_id");
                this.Screen_Flow = getIntent().getStringExtra("Screen_Flow");
                getSupportActionBar().setTitle("" + getString(R.string.order_id_txt) + " " + Order_id);
            }
        }


        MapsInitializer.initialize(TrackOrderStatesActivity.this);


        track_order_driver_pic_cir_img_view = (CircleImageView) findViewById(R.id.track_order_driver_pic_cir_img_view);
        track_order_states_msg_txt_view = (TextView) findViewById(R.id.track_order_states_msg_txt_view);
        track_order_msg_ic_img_view = (ImageView) findViewById(R.id.track_order_msg_ic_img_view);

        track_order_items_count_txt_view = (TextView) findViewById(R.id.track_order_items_count_txt_view);
        track_order_delv_time_txt_view = (TextView) findViewById(R.id.track_order_delv_time_txt_view);
        track_order_payable_amt_txt_view = (TextView) findViewById(R.id.track_order_payable_amt_txt_view);

//        DriverLocationCaReqCallMethod();
        initMap();


        track_order_msg_ic_img_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverMessageMethod();
            }
        });

    }

    @Override
    public void onBackPressed() {

        PageScreenFlowMethod();

    }

    public void PageScreenFlowMethod() {

        if (Screen_Flow.equals("0")) {

            Intent order_det_intent = new Intent(TrackOrderStatesActivity.this, OrderDetailActivity.class);
            order_det_intent.putExtra("Order_id", "" + Order_id);
            startActivity(order_det_intent);

            finish();

        } else {

            finish();

        }

    }

    @Override
    public void onResume() {
        //start handler as activity become visible
        super.onResume();
        driverMarker=null;
        polyOptions=null;
        DriverLocationCaReqCallMethod();
        initMap();
        Log.e("onresume","onresume");
    }

    @Override
    protected void onPause() {
        h.removeCallbacks(runnable); //stop handler when activity not visible
        if (googleMap!=null){
            googleMap.clear();
        }

//        handler.removeCallbacks(DrawPathRunnable);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.track_order_status_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.track_reload_menu_opt:
                h.postDelayed(runnable, delay);
                break;

            case R.id.track_call_menu_opt:
                DriverCallMethod();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void DriverCallMethod() {

        if (Driver_Mobile_No.isEmpty()) {
            Toast.makeText(getApplicationContext(), "" + getString(R.string.track_order_phone_no_err_msg), Toast.LENGTH_SHORT).show();
            return;
        }


        try {

            Intent phoneIntent = new Intent(Intent.ACTION_CALL);
            phoneIntent.setData(Uri.parse("tel:" + Driver_Mobile_No));
            if (ActivityCompat.checkSelfPermission(TrackOrderStatesActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivity(phoneIntent);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    public void DriverMessageMethod() {

        if (Driver_Mobile_No.isEmpty()) {
            Toast.makeText(getApplicationContext(), "" + getString(R.string.track_order_phone_no_err_msg), Toast.LENGTH_SHORT).show();
            return;
        }


        try {

            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "" + Driver_Mobile_No);
            smsIntent.putExtra("sms_body", "");
            startActivity(smsIntent);

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    public void initMap() {

        // Initializing
        markerPoints = new ArrayList<LatLng>();

        track_order_map = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.track_order_map));

        if (track_order_map != null) {
            track_order_map.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMaps) {

                    googleMap = googleMaps;

                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    googleMap.setTrafficEnabled(false);
                    googleMap.setIndoorEnabled(false);
                    googleMap.setBuildingsEnabled(false);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                    if (ActivityCompat.checkSelfPermission(TrackOrderStatesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TrackOrderStatesActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    h.postDelayed(runnable = new Runnable() {
                        public void run() {


                            DriverLocationCaReqCallMethod();



                            h.postDelayed(runnable, delay);

                        }
                    }, delay);
                }

            });
        }





    }





    private void DriverLocationCaReqCallMethod() {

        try {

//            if (progressDialog != null) {
//                progressDialog.show();
//            }

            Log.e("Lang_code", "-" + loginPrefManager.getStringValue("Lang_code"));
            Log.e("Order_id", "-" + Order_id);
            Log.e("user_id", "-" + loginPrefManager.getStringValue("user_id"));
            Log.e("user_token", "-" + loginPrefManager.getStringValue("user_token"));

            APIService order_driver_detail_api = Webdata.getRetrofit().create(APIService.class);
            order_driver_detail_api.Order_driver_loc_req_method("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + Order_id, "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<DriverLocDetRes>() {
                @Override
                public void onResponse(Call<DriverLocDetRes> call, Response<DriverLocDetRes> response) {

                    try {

//                        progressDialog.dismiss();
                        Log.e("onResponse", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getOrderStatus()==12) {
                                createAlert();
                            }else {


                                driv_latitude = Double.parseDouble(response.body().getResponse().getDriverDetails().getDriverLatitude());
                                driv_longitude = Double.parseDouble(response.body().getResponse().getDriverDetails().getDriverLongitude());

                                user_latitude = Double.parseDouble(response.body().getResponse().getDriverDetails().getUserLatitude());
                                user_longtitude = Double.parseDouble(response.body().getResponse().getDriverDetails().getUserLongtitude());
                                if (driverLatLng != null) {
                                    driverPreviousLatLng = driverLatLng;
                                } else {
                                    driverPreviousLatLng = new LatLng(driv_latitude, driv_longitude);
                                }

                                origin = new LatLng(driv_latitude, driv_longitude);
                                destination = new LatLng(user_latitude, user_longtitude);

                                driverLatLng = new LatLng(driv_latitude, driv_longitude);
                                userLatLng = new LatLng(user_latitude, user_longtitude);
                                Log.e("first_time", "" + firstime);


                                draw_route(driverLatLng, destination);



                                track_order_items_count_txt_view.setText("" + response.body().getResponse().getDriverDetails().getOrder_items());


                                String string_format = "" + getString(R.string.delivery_exe_txt) + " %s%s " + getString(R.string.picked_order_txt);
                                String order_track_msg = String.format(string_format, response.body().getResponse().getDriverDetails().getDriverFirstName(), response.body().getResponse().getDriverDetails().getDriverLastName());
                                track_order_states_msg_txt_view.setText(order_track_msg);


                                Driver_Mobile_No = "" + response.body().getResponse().getDriverDetails().getDriverMobileNumber();
                                if (firstime)
                                Glide.with(getApplicationContext()).load(response.body().getResponse().getDriverDetails().getDriver_profile_image()).into(track_order_driver_pic_cir_img_view);

                                track_order_payable_amt_txt_view.setText(loginPrefManager.getFormatCurrencyValue("" + response.body().getResponse().getDriverDetails().getTotal_amount()));
                            }

                        } else {
//                            progressDialog.dismiss();
                            Log.e("getHttpCode", "-" + response.body().getResponse().getHttpCode());
                        }

                    } catch (Exception e) {
//                        progressDialog.dismiss();
                        Log.e("Exception", "" + e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<DriverLocDetRes> call, Throwable t) {
                    progressDialog.dismiss();
                    Log.e("onFailure", "-" + t.getMessage());
                }
            });

        } catch (Exception e) {
            progressDialog.dismiss();
            Log.e("Exception", "" + e.getMessage());
        }

    }

    private void createAlert() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(TrackOrderStatesActivity.this);

        builder.setTitle(getString(R.string.message));
        builder.setMessage(getString(R.string.order_delivered));
        builder.setCancelable(false);
        builder.setPositiveButton(getString(R.string.ok_btn_txt), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.create();
        show = builder.show();


    }

    /*
            This method gets the new location of driver and calls method animateCar
            to move the marker slowly along linear path to this location.
            Also moves camera, if marker is outside of map bounds.
         */
    private void updateUI(final LatLng driverLatLng) {
        LatLng newLocation =driverLatLng;
        Log.e("driverMarker",""+driverMarker);
        if (driverMarker != null) {
            Log.e("driverMarker","not null");
            Log.e("driverLatLng",""+driverLatLng.latitude);
            Log.e("driverLatLng",""+driverLatLng.longitude);



            animateCar(newLocation);

            boolean contains = googleMap.getProjection()
                    .getVisibleRegion()
                    .latLngBounds
                    .contains(newLocation);
            if (!contains) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
            }

        } else {

            if (driverLatLng.latitude != 0) {
                        draw_route(origin,destination);
                    }
//            mMarkerIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bike);  // for your car icon in file the_car.png in drawable folder

            Log.e("driverMarker","null");
//            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                    newLocation, 15.5f));
            driverMarker = googleMap.addMarker(new MarkerOptions().position(driverLatLng).anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bick_ic)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(driverLatLng));


            //move camera to that position
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(googleMap.getCameraPosition().target)
                    .zoom(16.5f)
                    .bearing(30)
                    .tilt(45)
                    .build()));
//            originMarker = googleMap.addMarker(new MarkerOptions().position(origin).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
////                                //move camera to that position
//                                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
//                                        .target(googleMap.getCameraPosition().target)
//                                        .zoom(15.5f)
//                                        .bearing(30)
//                                        .tilt(45)
//                                        .build()));

        }
    }



    /*
        Animates car by moving it by fractions of the full path and finally moving it to its
        destination in a duration of 5 seconds.
     */
    private void animateCar(final LatLng destination) {

        final LatLng startPosition = driverMarker.getPosition();
        final LatLng endPosition = new LatLng(destination.latitude, destination.longitude);
//        final LatLngInterpolator latLngInterpolator = new LatLngInterpolator.LinearFixed();
        final LatLngInterpolatorSphericalNew latLngInterpolator = new LatLngInterpolatorSphericalNew.Spherical();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(5000); // duration 5 seconds
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {

                    float v = animation.getAnimatedFraction();
                    LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                    driverMarker.setPosition(newPosition);
//                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(newPosition));
//                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition
//                            (new CameraPosition.Builder().target(newPosition)
//                                    .zoom(16.5f).build()));

//                    driverMarker.setRotation(getBearing(startPosition, endPosition));
                } catch (Exception ex) {
                }
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.e("animationend","animationend");

            }
        });
        valueAnimator.start();
    }



    private void draw_route(LatLng start, LatLng end) {

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(start, end)
                .key("AIzaSyDZKSvUVvQSxLFMX0Tfardrko2ix6fgglI")
                .build();
        routing.execute();

    }

    @Override
    public void onRoutingFailure(RouteException e) {

        Log.e("Route Failure" ,""+ e.getMessage());


    }

    @Override
    public void onRoutingStart() {

        Log.e("route","onRoutingStart");
    }



    @Override
    public void onRoutingSuccess(final ArrayList<com.directions.route.Route> route, int shortestRouteIndex) {


        CameraUpdate center = CameraUpdateFactory.newLatLng(origin);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15.5f);


        googleMap.moveCamera(center);





    /*When polylineOptions_path is null it means the polyline is not drawn.*/
    /*If the polylineOptions_path is not null it means the polyline is drawn on map*/
        if(polyOptions == null){
            for (com.directions.route.Route route1 : route) {
                polyOptions = new PolylineOptions().
                        geodesic(true).
                        color(ContextCompat.getColor(TrackOrderStatesActivity.this, R.color.colorPrimary)).
                        width(10);

                for (int i = 0; i < route1.getPoints().size(); i++)
                    polyOptions.add(route1.getPoints().get(i));
                polyline = googleMap.addPolyline(polyOptions);
                updateUI(new LatLng(driv_latitude, driv_longitude));
            }
        }
        else {
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < route.size(); i++) {
                        final com.directions.route.Route route1 = route.get(i);
                        if(polyline != null){
                                    polyline.setPoints(route1.getPoints());


                        }
                    }
                }
            },5000);
            updateUI(new LatLng(driv_latitude, driv_longitude));
        }

//
        for (int i = 0; i < route.size(); i++) {

            //In case of more than 5 alternative routes
//            int colorIndex = i % COLORS.length;
//            polylines = new ArrayList<>();
//            polyOptions = new PolylineOptions();
//            polyOptions.color(getResources().getColor(COLORS[colorIndex]));
//            polyOptions.width(10 + i * 3);
//            polyOptions.addAll(route.get(i).getPoints());
//            polyline = googleMap.addPolyline(polyOptions);
//            polylines.add(polyline);


            try {
                track_order_delv_time_txt_view.setText(route.get(i).getDurationText());

            } catch (Exception e) {
                Log.e("Exception text add", e.getMessage());
            }

            if (firstime)
            {
                ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
            polylineAnimator.setDuration(3000);
            polylineAnimator.setInterpolator(new LinearInterpolator());
            polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    List<LatLng> points = polyOptions.getPoints();
                    int percentValue = (int) valueAnimator.getAnimatedValue();
                    int size = points.size();
                    int newPoints = (int) (size * (percentValue / 100.0f));
                    List<LatLng> p = points.subList(0, newPoints);
                    polyline.setPoints(p);
                }
            });
            polylineAnimator.start();
            firstime=false;
        }
        }




        // Start marker
        MarkerOptions options = new MarkerOptions();
//        options.position(origin);
////
//        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bike));
//        googleMap.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(destination);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        googleMap.addMarker(options);

    }




    @Override
    public void onRoutingCancelled() {
          Log.e("route","cancelled");

    }

//    @Override
//    public void onDirectionSuccess(Direction direction, String rawBody) {
//        Log.e("direction",""+direction.getStatus());
//        if (direction.isOK()) {
////            googleMap.clear();
//            CameraUpdate center = CameraUpdateFactory.newLatLng(origin);
//            CameraUpdate zoom = CameraUpdateFactory.zoomTo(2);
//            googleMap.moveCamera(center);
//
//
//
//            if (polyLineList.size()>0){
//                for (int i=0;i<polyLineList.size();i++){
//                    polyLineList.remove(i);
//                }
//            }
//
//
//            Route route = direction.getRouteList().get(0);
//            Leg leg = route.getLegList().get(0);
//            //time sec
//            track_order_delv_time_txt_view.setText("" + leg.getDuration().getText());
//
//
//
//            List<Route> routeList=direction.getRouteList();
//
//            for (int i = 0; i < routeList.size(); i++) {
//                RoutePolyline poly = routeList.get(i).getOverviewPolyline();
//                polyLineList = decodePoly(poly.getRawPointList());
//                Log.d(TAG, polyLineList + "");
//            }
//            //Adjusting bounds
////           LatLngBounds.Builder builder=new LatLngBounds.Builder();
////            //include latlng
////            for (LatLng latLng:polyLineList){
////                builder.include(latLng);
////            }
////            //build the bound
////            LatLngBounds bounds=builder.build();
////            //update camera
////            CameraUpdate mCameraUpdate=CameraUpdateFactory.newLatLngBounds(bounds,2);
////            //animate the camera
////            googleMap.animateCamera(mCameraUpdate);
//            //crate polyline option
//            polylineOptions = new PolylineOptions();
//            polylineOptions.color(Color.RED);
//            polylineOptions.width(5);
//            polylineOptions.startCap(new SquareCap());
//            polylineOptions.endCap(new SquareCap());
//            polylineOptions.jointType(ROUND);
//            polylineOptions.addAll(polyLineList);
//            lightPolyline = googleMap.addPolyline(polylineOptions);
//
//            redPolylineOptions = new PolylineOptions();
//            redPolylineOptions.width(5);
//            redPolylineOptions.color(Color.RED);
//            redPolylineOptions.startCap(new SquareCap());
//            redPolylineOptions.endCap(new SquareCap());
//            redPolylineOptions.jointType(ROUND);
//            redPolyLine = googleMap.addPolyline(redPolylineOptions);
//
//            //destination
//            destinationMarker =    googleMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//                    .position(polyLineList.get(polyLineList.size() - 1)));
////            originMarker = googleMap.addMarker(new MarkerOptions().position(origin)
////                    .flat(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
////          googleMap.addMarker(new MarkerOptions()
////                  .position(polyLineList.get(polyLineList.size() - 1)));
//            //start animation
////
//            ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 0);
//            polylineAnimator.setDuration(2000);
//            polylineAnimator.setInterpolator(new LinearInterpolator());
//            polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    List<LatLng> points = lightPolyline.getPoints();
//                    int percentValue = (int) valueAnimator.getAnimatedValue();
//                    int size = points.size();
//                    int newPoints = (int) (size * (percentValue / 100.0f));
//                    List<LatLng> p = points.subList(0, newPoints);
//                    redPolyLine.setPoints(p);
//                }
//            });
//            polylineAnimator.start();
//
////            //add car icon
////            carMarker = googleMap.addMarker(new MarkerOptions().position(origin)
////                    .flat(true)
////                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));
////
////            //handler
////            handler = new Handler();
////            index = -1;
////            next = 1;
////            handler.postDelayed(DrawPathRunnable,3000);
//
//
//        }
//    }

    private void getDistance(LatLng start, LatLng end) {
        float[] results = new float[1];
        Location.distanceBetween(start.latitude, start.longitude,
                end.latitude, end.longitude, results);
        float distance = results[0];

        Log.e("distance",""+distance);

        if (distance > 3) {


            float rotation = (float) bearingBetweenLocations(start, currentLatLng);

            animateMarker(oldLatLng, currentLatLng, false, rotation);

            start_time = 0;

        }
    }
    private interface LatLngInterpolatorSphericalNew {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

         class Spherical implements LatLngInterpolatorSphericalNew {

            /* From github.com/googlemaps/android-maps-utils */
            @Override
            public LatLng interpolate(float fraction, LatLng from, LatLng to) {
                // http://en.wikipedia.org/wiki/Slerp
                double fromLat = toRadians(from.latitude);
                double fromLng = toRadians(from.longitude);
                double toLat = toRadians(to.latitude);
                double toLng = toRadians(to.longitude);
                double cosFromLat = cos(fromLat);
                double cosToLat = cos(toLat);

                // Computes Spherical interpolation coefficients.
                double angle = computeAngleBetween(fromLat, fromLng, toLat, toLng);
                double sinAngle = sin(angle);
                if (sinAngle < 1E-6) {
                    return from;
                }
                double a = sin((1 - fraction) * angle) / sinAngle;
                double b = sin(fraction * angle) / sinAngle;

                // Converts from polar to vector and interpolate.
                double x = a * cosFromLat * cos(fromLng) + b * cosToLat * cos(toLng);
                double y = a * cosFromLat * sin(fromLng) + b * cosToLat * sin(toLng);
                double z = a * sin(fromLat) + b * sin(toLat);

                // Converts interpolated vector back to polar.
                double lat = atan2(z, sqrt(x * x + y * y));
                double lng = atan2(y, x);
                return new LatLng(toDegrees(lat), toDegrees(lng));
            }

            private double computeAngleBetween(double fromLat, double fromLng, double toLat, double toLng) {
                // Haversine's formula
                double dLat = fromLat - toLat;
                double dLng = fromLng - toLng;
                return 2 * asin(sqrt(pow(sin(dLat / 2), 2) +
                        cos(fromLat) * cos(toLat) * pow(sin(dLng / 2), 2)));
            }
        }
    }



    /*
        This interface defines the interpolate method that allows us to get LatLng coordinates for
        a location a fraction of the way between two points. It also utilizes a Linear method, so
        that paths are linear, as they should be in most streets.
     */
    private interface LatLngInterpolator {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolator {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }


    public void animateMarker(final LatLng startPosition, final LatLng toPosition, final boolean hideMarker, final float rotation) {

        Log.e("map clear", "4");

        googleMap.clear();
//        int height = 80;
//        int width = 80;
//        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_bike);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
//
//        final Marker marker= googleMap.addMarker(new MarkerOptions().position(startPosition)
//                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin3))
//                .icon(BitmapDescriptorFactory.fromBitmap((smallMarker))));
        final Marker marker = googleMap.addMarker(new MarkerOptions().position(startPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bick_ic)).flat(true));
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(toPosition));
//        animateMarker(destination,marker);

        final long duration = 10000;
        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;

                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation(rotation);
//                marker.setRotation(getBearing(startPosition, toPosition));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    private double bearingBetweenLocations(LatLng latLng1, LatLng latLng2) {

        double PI = 3.14159;
        double lat1 = latLng1.latitude * PI / 180;
        double long1 = latLng1.longitude * PI / 180;
        double lat2 = latLng2.latitude * PI / 180;
        double long2 = latLng2.longitude * PI / 180;

        double dLon = (long2 - long1);

        double y = sin(dLon) * cos(lat2);
        double x = cos(lat1) * sin(lat2) - sin(lat1)
                * cos(lat2) * cos(dLon);

        double brng = atan2(y, x);

        brng = toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }









    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (show!=null&& show.isShowing()){
            show.dismiss();
        }
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void animateMarkerNew(final LatLng startPosition, final LatLng destination, final Marker marker) {

        if (marker != null) {

            final LatLng endPosition = new LatLng(destination.latitude, destination.longitude);

            final float startRotation = marker.getRotation();
            final LatLngInterpolatorNew latLngInterpolator = new LatLngInterpolatorNew.LinearFixed();

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
            valueAnimator.setDuration(2000); // duration 3 second
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    try {
                        float v = animation.getAnimatedFraction();
                        LatLng newPosition = latLngInterpolator.interpolate(v, startPosition, endPosition);
                        marker.setPosition(newPosition);
                        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                                .target(newPosition)
                                .zoom(18f)
                                .build()));

                        marker.setRotation(getBearing(startPosition, new LatLng(destination.latitude, destination.longitude)));
                    } catch (Exception ex) {
                        //I don't care atm..
                    }
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    // if (mMarker != null) {
                    // mMarker.remove();
                    // }
                    // mMarker = googleMap.addMarker(new MarkerOptions().position(endPosition).icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_car)));

                }
            });
            valueAnimator.start();
        }
    }

    private interface LatLngInterpolatorNew {
        LatLng interpolate(float fraction, LatLng a, LatLng b);

        class LinearFixed implements LatLngInterpolatorNew {
            @Override
            public LatLng interpolate(float fraction, LatLng a, LatLng b) {
                double lat = (b.latitude - a.latitude) * fraction + a.latitude;
                double lngDelta = b.longitude - a.longitude;
                // Take the shortest path across the 180th meridian.
                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * fraction + a.longitude;
                return new LatLng(lat, lng);
            }
        }
    }


    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }
    private void animateCarMove(final Marker marker, final LatLng beginLatLng, final LatLng endLatLng, final long duration) {
        final Handler handler = new Handler();
        final long startTime = SystemClock.uptimeMillis();

        final Interpolator interpolator = new LinearInterpolator();

        // set car bearing for current part of path
        float angleDeg = (float)(180 * getAngle(beginLatLng, endLatLng) / Math.PI);
        Matrix matrix = new Matrix();
        matrix.postRotate(angleDeg);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(mMarkerIcon, 0, 0, mMarkerIcon.getWidth(), mMarkerIcon.getHeight(), matrix, true)));

        handler.post(new Runnable() {
            @Override
            public void run() {
                // calculate phase of animation
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                // calculate new position for marker
                double lat = (endLatLng.latitude - beginLatLng.latitude) * t + beginLatLng.latitude;
                double lngDelta = endLatLng.longitude - beginLatLng.longitude;

                if (Math.abs(lngDelta) > 180) {
                    lngDelta -= Math.signum(lngDelta) * 360;
                }
                double lng = lngDelta * t + beginLatLng.longitude;

                marker.setPosition(new LatLng(lat, lng));

                // if not end of line segment of path
                if (t < 1.0) {
                    // call next marker position
                    handler.postDelayed(this, 16);
                } else {
                    // call turn animation
                    nextTurnAnimation();
                }
            }
        });
    }
    private void nextTurnAnimation() {
        mIndexCurrentPoint++;

        if (mIndexCurrentPoint < polyLineList.size() - 1) {
            LatLng prevLatLng = polyLineList.get(mIndexCurrentPoint - 1);
            LatLng currLatLng = polyLineList.get(mIndexCurrentPoint);
            LatLng nextLatLng = polyLineList.get(mIndexCurrentPoint + 1);

            float beginAngle = (float)(180 * getAngle(prevLatLng, currLatLng) / Math.PI);
            float endAngle = (float)(180 * getAngle(currLatLng, nextLatLng) / Math.PI);

            animateCarTurn(driverMarker, beginAngle, endAngle, TURN_ANIMATION_DURATION);
        }
    }
    private double getAngle(LatLng beginLatLng, LatLng endLatLng) {
        double f1 = Math.PI * beginLatLng.latitude / 180;
        double f2 = Math.PI * endLatLng.latitude / 180;
        double dl = Math.PI * (endLatLng.longitude - beginLatLng.longitude) / 180;
        return Math.atan2(Math.sin(dl) * Math.cos(f2) , Math.cos(f1) * Math.sin(f2) - Math.sin(f1) * Math.cos(f2) * Math.cos(dl));
    }
    private void animateCarTurn(final Marker marker, final float startAngle, final float endAngle, final long duration) {
        final Handler handler = new Handler();
        final long startTime = SystemClock.uptimeMillis();
        final Interpolator interpolator = new LinearInterpolator();

        final float dAndgle = endAngle - startAngle;

        Matrix matrix = new Matrix();
        matrix.postRotate(startAngle);
        Bitmap rotatedBitmap = Bitmap.createBitmap(mMarkerIcon, 0, 0, mMarkerIcon.getWidth(), mMarkerIcon.getHeight(), matrix, true);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(rotatedBitmap));

        handler.post(new Runnable() {
            @Override
            public void run() {

                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);

                Matrix m = new Matrix();
                m.postRotate(startAngle + dAndgle * t);
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(mMarkerIcon, 0, 0, mMarkerIcon.getWidth(), mMarkerIcon.getHeight(), m, true)));

                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                } else {
                    nextMoveAnimation();
                }
            }
        });
    }
    private void nextMoveAnimation() {
        if (mIndexCurrentPoint <  polyLineList.size() - 1) {
            animateCarMove(driverMarker, polyLineList.get(mIndexCurrentPoint), polyLineList.get(mIndexCurrentPoint+1), MOVE_ANIMATION_DURATION);
        }
    }

}
