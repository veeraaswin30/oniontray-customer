package com.app.oniontray.Activites;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.app.oniontray.AppControler.onionTray;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.Currency;
import com.app.oniontray.RequestModels.Datum;
import com.app.oniontray.RequestModels.Language;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashScreen extends LocalizationActivity implements onionTray.SplashScreenInterface {


    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    private Runnable runnable;


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "SplashScreen";


    public final static int OVERLAY_PERMISSION_REQUEST_CODE = 12345;


    private static android.app.AlertDialog.Builder alertDialogBuilder = null;
    private static android.app.AlertDialog networkAlertDialog = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        onionTray.CallSplashScreenInterface(SplashScreen.this);

        runnable = new Runnable() {
            @Override
            public void run() {
//check if the app is already launched or not
                if (!loginPrefManager.getCityID().isEmpty() && !loginPrefManager.getLocID().isEmpty()) {
//menu screen
                    Intent signInActivity = new Intent(SplashScreen.this, BaseMenuTabActivity.class);
                    signInActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(signInActivity);
                    finish();

                } else {
//welcome Screen
                    Intent signInActivity = new Intent(SplashScreen.this, WelcomeLocationActivity.class);
                    signInActivity.putExtra("screen_flow", "0");
                    signInActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(signInActivity);
                    finish();

                }

            }
        };

        GeneralSettingsReq();

        if (checkPlayServices()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(task ->
                                    loginPrefManager.setStringValue("device_token",
                                            task.getResult().getToken()));
                }
            });
        }

        String device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        loginPrefManager.setStringValue("device_id", "" + device_id);

        Log.e("device_token", "- " + loginPrefManager.getStringValue("device_token"));

        printHashKey(SplashScreen.this);

    }


    @Override
    public void onResume() {
        super.onResume();

        if (NetworkStatus.isConnectingToInternet(SplashScreen.this)) {
            checkPermision();
        }


    }

    private void sendSplashScreenNotification(String message, String noti_title) {

        Intent intent = new Intent(this, BaseMenuTabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.color.transparent).setColor(getResources().getColor(android.R.color.transparent))
//                .setSmallIcon(R.drawable.notification_small_ic).setColor(getResources().getColor(R.color.colorPrimary))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("" + noti_title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }


    public boolean checkDrawOverlayPermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if (!Settings.canDrawOverlays(SplashScreen.this)) {
            showOverlayPermissionWarningDialog();
            return false;
        } else {
            return true;
        }

    }

    //  Screen Overlay permission dialog;
    private void showOverlayPermissionWarningDialog() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(SplashScreen.this, R.style.MyDialogStyle);
        alertDialog.setTitle("" + getString(R.string.screen_over_lay_title));
        alertDialog.setMessage("" + getString(R.string.screen_over_lay_permission_msg_txt));
        alertDialog.setPositiveButton("" + getApplicationContext().getString(R.string.screen_over_lay_ok_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
            }
        });
        alertDialog.setNegativeButton("" + getApplicationContext().getString(R.string.screen_over_lay_cancel_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                finish();
            }
            return false;
        }
        return true;
    }


    private void checkPermision() {

        int currentapiVers = android.os.Build.VERSION.SDK_INT;
        if (currentapiVers >= android.os.Build.VERSION_CODES.M) {

            if (checkAndRequestPermissions()) {

                new Handler().postDelayed(runnable, 2000);
            }

        } else {
            new Handler().postDelayed(runnable, 2000);
        }

    }


    //  Internet connection alert
    private void showExitWarningDialog() {

        alertDialogBuilder = new android.app.AlertDialog.Builder(SplashScreen.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialogBuilder.setTitle("" + getString(R.string.no_internet_conn_tit_txt))
                .setMessage("" + getString(R.string.no_internet_conn_msg_txt))
                .setPositiveButton("" + getString(R.string.no_internet_conn_ok_btn_txt),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });

        networkAlertDialog = alertDialogBuilder.create();
        networkAlertDialog.show();

    }


    //    Check permission
    private boolean checkAndRequestPermissions() {

        int permissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//
//        int permissionReadMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
//        int permissionReceiveMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
//        int permissionSendMessage = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        int permissionCallPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionReadExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
//        if (permissionReadMessage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
//        }
//        if (permissionReceiveMessage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
//        }
//        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
//            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
//        }
        if (permissionCallPhone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteExternalStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case REQUEST_ID_MULTIPLE_PERMISSIONS: {

                Map<String, Integer> perms = new HashMap<>();
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);

//                perms.put(Manifest.permission.READ_SMS, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.RECEIVE_SMS, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);

                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);

                if (grantResults.length > 0) {

                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);

                    // Check for both permissions
                    if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

//                            && perms.get(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
//                            && perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED

                            && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

//                            && perms.get(Manifest.permission.SYSTEM_ALERT_WINDOW) == PackageManager.PERMISSION_GRANTED

                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        new Handler().postDelayed(runnable, 2000);
//                        Log.e("SplashScreen", "sms & location services permission granted");

                    } else {
//                        Log.e("SplashScreen", "Some permissions are not granted ask again ");

                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)

//                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)
//                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)
//                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)

                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)

//                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SYSTEM_ALERT_WINDOW)

                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            showDialogOK("Call Phone, Read & Write External Storage, SMS and Location Services Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    finish();
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(SplashScreen.this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        }

    }


    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", okListener).create().show();
    }


    private void GeneralSettingsReq() {

        final APIService Languagedata = Webdata.getRetrofit().create(APIService.class);
        Languagedata.getLanguage().enqueue(new Callback<Language>() {
            @Override
            public void onResponse(Call<Language> call, Response<Language> response) {

                try {

                    if (response.body().getResponse().getHttpCode() == 200) {

                        loginPrefManager.setStringValue("Off_Status", "" + response.body().getResponse().getSettOffMod().getActiveStatus());
                        loginPrefManager.setStringValue("currency_side", "" + response.body().getResponse().getGeneralSettings().getCurrencySide());
                        loginPrefManager.setThemeListID("" + response.body().getResponse().getThemeList().getId());
                        loginPrefManager.setThemeColor("" + response.body().getResponse().getThemeList().getThemeColor());
                        loginPrefManager.setThemeColorAccent("" + response.body().getResponse().getThemeList().getColorAcceent());
                        loginPrefManager.setThemeFontColor("" + response.body().getResponse().getThemeList().getFontColor());
                        loginPrefManager.setThemeStatusbarColor("" + response.body().getResponse().getThemeList().getStatusBarColor());
                        loginPrefManager.setThemeType("" + response.body().getResponse().getThemeList().getType());
                        loginPrefManager.setToolbarIconcolor("" + response.body().getResponse().getThemeList().getFontColor());
                        // loginPrefManager.setThemeColor(""+"#dd0014");

                        List<Datum> langugeedata = response.body().getResponse().getData();

                        for (int i = 0; i < langugeedata.size(); i++) {

                            if (i == 0) {
                                loginPrefManager.setStringValue("en_Name", langugeedata.get(i).getName());
                                loginPrefManager.setStringValue("en_Id", langugeedata.get(i).getId());
                            } else if (i == 1) {
                                loginPrefManager.setStringValue("ar_Name", langugeedata.get(i).getName());
                                loginPrefManager.setStringValue("ar_Id", langugeedata.get(i).getId());
                            }


                            if (langugeedata.get(i).getId().equals(response.body().getResponse().getGeneralSettings().getDefaultLanguage())) {

                                if (loginPrefManager.getDefaultLang().isEmpty()) {

                                    loginPrefManager.setStringValue("Lang", "" + langugeedata.get(i).getLanguageCode());
                                    loginPrefManager.setStringValue("Lang_code", "" + langugeedata.get(i).getId());

                                    loginPrefManager.setDefaultLang("false");

                                } else {
                                    loginPrefManager.setDefaultLang("true");
                                }

                            }

                        }

                        List<Currency> currency = response.body().getResponse().getCurrencyList();

                        for (int j = 0; j < currency.size(); j++) {
                            if (currency.get(j).getId() == response.body().getResponse().getGeneralSettings().getDefaultCurrency()) {
                                loginPrefManager.setStringValue("currency_symbol", currency.get(j).getCurrencySymbol().trim());
                                loginPrefManager.setStringValue("currency_name", currency.get(j).getCurrencyName());
                                loginPrefManager.setStringValue("currency_code", currency.get(j).getCurrencyCode());
                                break;
                            }
                        }

                        // Default currency value
                        if (response.body().getResponse().getGeneralSettings().getCurSymbolSpace() == null) {
                            loginPrefManager.setBooleanValue("cur_symbol_space", true);
                        } else {
                            if (response.body().getResponse().getGeneralSettings().getCurSymbolSpace() == 0) {
                                loginPrefManager.setBooleanValue("cur_symbol_space", false);
                            } else {
                                loginPrefManager.setBooleanValue("cur_symbol_space", true);
                            }
                        }

                    }

                } catch (Exception e) {
                    Log.e(TAG, " General Settings Exception Error " + e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<Language> call, Throwable t) {
                Log.e(TAG + " onFailure:", "" + t.getMessage());
            }

        });
    }

    public void printHashKey(Context pContext) {

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.app.OddappzFood", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

//        FQqRSDFEdxmSjT/df6HBIl3/8Pw=    ,   I2F+prM/DVGj7ZV8Yx+8rctHsCc=

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.app.OddappzFood", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (Exception e) {
//            Log.e(TAG, "printHashKey()", e);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // In this method called once network connected reload parmission.
    @Override
    public void ReloadPermissionMethod() {
        checkPermision();
    }


}
