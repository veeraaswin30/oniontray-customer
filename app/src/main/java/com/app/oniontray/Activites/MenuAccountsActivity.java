package com.app.oniontray.Activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.app.oniontray.Adapters.MyAccountsAdapter;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.razorpay.Checkout;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/14/2017.
 */

public class MenuAccountsActivity extends LocalizationActivity implements GoogleApiClient.OnConnectionFailedListener,
        MyAccountsAdapter.MyAccountsInterface {

    Toolbar menu_accounts_toolbar;

    private LinearLayout user_details_layout;

    private TextView my_accounts_name_txt_view;
    private TextView my_accounts_email_txt_view;
    private TextView my_accounts_phone_no_txt_view;
    private  TextView my_account_title;

    private ImageView my_accounts_edit_profile_btn;

    private VectorMasterView my_accounts_edit_profile_btnn;
    private Button account_login_btn;

    private RecyclerView my_account_recycler_view;
    private MyAccountsAdapter myAccountsAdapter;


    ArrayList<Drawable> imgArr_res;
    ArrayList<String> imgArr_name;


    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;

    CallbackManager callbackManager;
    LoginManager mLoginManager;
    AccessTokenTracker mAccessTokenTracker;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_my_accounts_layout);

        menu_accounts_toolbar = (Toolbar) findViewById(R.id.menu_accounts_toolbar);
        menu_accounts_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        menu_accounts_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(menu_accounts_toolbar);

        my_account_title = findViewById(R.id.title);

        my_account_title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));



        user_details_layout = (LinearLayout) findViewById(R.id.user_details_layout);

        my_accounts_name_txt_view = (TextView) findViewById(R.id.my_accounts_name_txt_view);

        my_accounts_email_txt_view = (TextView) findViewById(R.id.my_accounts_email_txt_view);

        my_accounts_phone_no_txt_view = (TextView) findViewById(R.id.my_accounts_phone_no_txt_view);


        account_login_btn = (Button) findViewById(R.id.account_login_btn);
        account_login_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        account_login_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        my_accounts_edit_profile_btn = (ImageView) findViewById(R.id.my_accounts_edit_profile_btn);

        my_accounts_edit_profile_btnn = (VectorMasterView) findViewById(R.id.my_accounts_edit_profile_btnn);
        // find the correct path using name
        PathModel outline = my_accounts_edit_profile_btnn.getPathModelByName("outline");

// set the stroke color
        outline.setStrokeColor(Color.parseColor(loginPrefManager.getThemeColorAccent()));




// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
        outline.setFillColor(Color.parseColor(loginPrefManager.getThemeColorAccent()));


        my_accounts_edit_profile_btnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyProfileCallMethod();
            }
        });


        my_account_recycler_view = (RecyclerView) findViewById(R.id.my_account_recycler_view);
        my_account_recycler_view.setHasFixedSize(true);
        my_account_recycler_view.setLayoutManager(new LinearLayoutManager(MenuAccountsActivity.this));
        my_account_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(MenuAccountsActivity.this, R.dimen.prod_list_item_row_line_height));

//        CreateMyAccountsAdapter();

        googlePlusClientSetUp();

        LoginButtonClickEvent();

    }

    private void googlePlusClientSetUp() {

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.e("google plus", "onConnectionFailed:" + connectionResult);
    }

    private void LoginButtonClickEvent() {

        account_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuAccountsActivity.this, RestaurantSignInSignUpActivity.class));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        if (mGoogleApiClient != null) {
            if (!mGoogleApiClient.isConnected()) {
//                Log.e("mGoogleApiClient", "onResume :is not connected");
                mGoogleApiClient.reconnect();
            } else {
//                Log.e("mGoogleApiClient", "onResume :is connected");
            }
        }

        updateUserLoginMethod();
        CreateMyAccountsAdapter();
    }


    private void updateUserLoginMethod() {

        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            VisitorInfo visitorData = new VisitorInfo.Builder()
                    .name("" + loginPrefManager.getStringValue("user_first_name"))
                    .email("" + loginPrefManager.getStringValue("user_email"))
//                    .phoneNumber("0123456789")
                    .build();

            ZopimChat.setVisitorInfo(visitorData);
            user_details_layout.setVisibility(View.VISIBLE);
            account_login_btn.setVisibility(View.GONE);
            my_accounts_name_txt_view.setText("" + loginPrefManager.getStringValue("user_first_name") + " " + loginPrefManager.getStringValue("user_last_name"));
            my_accounts_email_txt_view.setText("" + loginPrefManager.getStringValue("user_email"));

//            Log.e("user_mobile", "" + loginPrefManager.getStringValue("user_mobile"));

            my_accounts_phone_no_txt_view.setText("" + loginPrefManager.getStringValue("user_mobile"));

        } else {
            user_details_layout.setVisibility(View.GONE);
            account_login_btn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        BaseBackMethod();
    }

    public void BaseBackMethod() {
        Intent intent_rece = new Intent("base_activity_receiver");
        intent_rece.putExtra("page_name", "0");
        LocalBroadcastManager.getInstance(MenuAccountsActivity.this).sendBroadcast(intent_rece);
    }

    private void CreateMyAccountsAdapter() {

        imgArr_name = new ArrayList<String>();
        imgArr_res = new ArrayList<Drawable>();

        Drawable offers = ContextCompat.getDrawable(MenuAccountsActivity.this,
                R.drawable.ic_my_accounts_offers_ic);
        offers.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);

        if (!loginPrefManager.getStringValue("user_id").isEmpty()) {

            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_speech_bubble));


            imgArr_res.add(offers);
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_notification_ic));
// imgArr_res.add(R.drawable.my_accounts_wallet_ic);
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_settings_ic));
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_address_ic));
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_aboutus_ic));
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_menu_favorite));
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_feed_back_ic));
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_logout_ic));



            imgArr_name.add("" + getString(R.string.my_account_live_chat));
            imgArr_name.add("" + getString(R.string.my_account_offers));
            imgArr_name.add("" + getString(R.string.my_account_notifications));
//            imgArr_name.add("" + getString(R.string.my_account_food_boy_wallet));
            imgArr_name.add("" + getString(R.string.my_account_Settings));
            imgArr_name.add("" + getString(R.string.my_account_my_address));
            imgArr_name.add("" + getString(R.string.my_account_about_us));
            imgArr_name.add("" + getString(R.string.my_account_my_favourites));
            imgArr_name.add("" + getString(R.string.my_account_send_feed_back));
            imgArr_name.add("" + getString(R.string.my_account_log_out));

        } else {

//            imgArr_res.add(R.drawable.my_accounts_chat_ic);
//            imgArr_res.add(R.drawable.ic_my_accounts_offers_ic);
//            imgArr_res.add(R.drawable.ic_my_accounts_settings_ic);
//            imgArr_res.add(R.drawable.ic_my_accounts_feed_back_ic);


            imgArr_res.add(offers);
            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_settings_ic));

            imgArr_res.add(loginPrefManager.changeColor(MenuAccountsActivity.this,
                    loginPrefManager.getThemeFontColor(),R.drawable.ic_my_accounts_feed_back_ic));


//            imgArr_name.add("" + getString(R.string.my_account_live_chat));
            imgArr_name.add("" + getString(R.string.my_account_offers));
            imgArr_name.add("" + getString(R.string.my_account_Settings));
            imgArr_name.add("" + getString(R.string.my_account_send_feed_back));

        }

        myAccountsAdapter = new MyAccountsAdapter(MenuAccountsActivity.this, imgArr_res, imgArr_name);
        myAccountsAdapter.MyAccountsInterfaceMethod(MenuAccountsActivity.this);
        my_account_recycler_view.setAdapter(myAccountsAdapter);
    }


    @Override
    public void SelectedAccountsMethod(int position) {

        switch (position) {

            case 0:
                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    // start chat
                    startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));
                } else {
                    OffersCallMethod();
                }
                break;
            case 1:
//                OffersCallMethod();
                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    OffersCallMethod();
                } else {
                    MySettingsCallMethod();
                }
                break;
            case 2:
                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    NotificationCallMethod();
                } else {
                    SendFeedBackMethod();
//                    MySettingsCallMethod();
                }
                break;

//            case 3:
//                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//                    MyWalletCallMethod();
//                }
//                break;
//            case 4:
//                MySettingsCallMethod();
//                break;
//            case 5:
//                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//                    MyAddressCallMethod();
//                }
//                break;
//            case 6:
//                AboutUsCallMethod();
//                break;
//            case 7:
//                MyFavouritesCallMethod();
//                break;
//            case 8:
//                SendFeedBackMethod();
//                break;
//            case 9:
//                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//                    LogoutDialog();
//                }
//                break;

            case 3:
//                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//                    MySettingsCallMethod();
//                    SendFeedBackMethod();
//                } else {
//                    SendFeedBackMethod();
                    MySettingsCallMethod();
//                }
                break;
            case 4:
                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    MyAddressCallMethod();
                }
                break;
            case 5:
                AboutUsCallMethod();
                break;
            case 6:
                MyFavouritesCallMethod();
                break;
            case 7:
                SendFeedBackMethod();
                break;
            case 8:
                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
                    LogoutDialog();
                }
                break;

        }

    }

    private void LogoutDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));

        alertDialog.setMessage("" + getString(R.string.exit_dialog));

        alertDialog.setNegativeButton("" + getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });

        alertDialog.setPositiveButton("" + getString(R.string.ok_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Checkout.clearUserData(MenuAccountsActivity.this);

                        if (loginPrefManager.getStringValue("user_type").equals(getString(R.string.user_type_normal))) {

                            loginPrefManager.LogOutClearDataMethod();
                            onResume();

                        } else if (loginPrefManager.getStringValue("user_type").equals(getString(R.string.user_type_fb))) {
                            LoginManager.getInstance().logOut();

                            loginPrefManager.LogOutClearDataMethod();
                            onResume();

                        } else if (loginPrefManager.getStringValue("user_type").equals(getString(R.string.user_type_google))) {

                            if (mGoogleApiClient != null) {

//                                Log.e("mGoogleApiClient", "is not null");

                                if (mGoogleApiClient.isConnected()) {
                                    signOut();
                                } else {
//                                    Log.e("mGoogleApiClient", "is not connected");
                                }

                            } else {
//                                Log.e("mGoogleApiClient", "is null");
                            }
                        }

                        dialog.dismiss();

//                        loginPrefManager.LogOutClearDataMethod();
////                        updateUserLoginMethod();
//                        onResume();

                    }
                });
        alertDialog.show();
    }

    private void signOut()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                        if (status.isSuccess()) {
                            loginPrefManager.LogOutClearDataMethod();
                            onResume();

//                            if(!mGoogleApiClient.isConnected()){
//                                mGoogleApiClient.reconnect();
//                                Log.e("mGoogleApiClient","is not connected");
//                                mGoogleApiClient.connect();
//                            }else{
//                                Log.e("mGoogleApiClient","is connected");
//                            }

                        } else {
//                            Log.e("GoogleSignInApi.signOut", "-" + status.getStatusMessage());
                            Toast.makeText(MenuAccountsActivity.this, "" + status.getStatusMessage(), Toast.LENGTH_SHORT);
                        }

                    }
                });
    }


    private void OffersCallMethod()
    {
        Intent offersIntent = new Intent(MenuAccountsActivity.this, RestaurantOffersActivity.class);
        startActivity(offersIntent);
    }

    private void NotificationCallMethod()
    {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            startActivity(new Intent(MenuAccountsActivity.this, NotificationsActivity.class));
        } else {
            LoginConfirmationDialog();
        }
    }


    private void MySettingsCallMethod() {
        Intent my_wallet_intent = new Intent(MenuAccountsActivity.this, SettingsActivity.class);
        startActivity(my_wallet_intent);
    }

    private void MyAddressCallMethod() {
        Intent my_wallet_intent = new Intent(MenuAccountsActivity.this, MyAddressListActivity.class);
        startActivity(my_wallet_intent);
    }

    private void AboutUsCallMethod() {
        startActivity(new Intent(MenuAccountsActivity.this, AboutUs.class));
    }


    public void MyFavouritesCallMethod() {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            startActivity(new Intent(MenuAccountsActivity.this, MyFavouritesActivity.class));
        } else {
            LoginConfirmationDialog();
        }
    }


    public void MyProfileCallMethod() {
        if (loginPrefManager.getStringValue("user_id") != null && !loginPrefManager.getStringValue("user_id").isEmpty()) {
            startActivity(new Intent(MenuAccountsActivity.this, ProfileActivity.class));
        } else {
            LoginConfirmationDialog();
        }
    }

    public void SendFeedBackMethod() {
        Intent send_feed_Intent = new Intent(MenuAccountsActivity.this, SendFeedBackActivity.class);
        startActivity(send_feed_Intent);
    }


    @Override
    public void onPause() {
        super.onPause();

        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage(this);
            mGoogleApiClient.disconnect();
        }
    }


}
