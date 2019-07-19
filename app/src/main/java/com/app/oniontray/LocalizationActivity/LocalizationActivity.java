package com.app.oniontray.LocalizationActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.app.oniontray.Activites.RestaurantSignInSignUpActivity;
import com.app.oniontray.AppControler.CustomFontStyle;
import com.app.oniontray.DB.IngredientRepository;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.Interface.InternetConnectionListener;
import com.app.oniontray.R;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.Utils.NetworkStatus;
import com.app.oniontray.WebService.Webdata;

import java.util.Locale;

import retrofit2.Retrofit;


public abstract class LocalizationActivity extends AppCompatActivity implements OnLocaleChangedListener, InternetConnectionListener {

        private LocalizationActivityDelegate localizationDelegate = new LocalizationActivityDelegate(this);
        public ProductRespository productRespository;
        public IngredientRepository ingredientRepository;

        public LoginPrefManager loginPrefManager;
        public DDProgressBarDialog progressDialog;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            localizationDelegate.addOnLocaleChangedListener(this);
            localizationDelegate.onCreate(savedInstanceState);
            super.onCreate(savedInstanceState);

            CustomFontStyle.replaceDefaultFount(LocalizationActivity.this);

            productRespository = new ProductRespository();
            ingredientRepository = new IngredientRepository();

            loginPrefManager = new LoginPrefManager(LocalizationActivity.this);
            progressDialog = Webdata.getProgressBarDialog(LocalizationActivity.this);

            Webdata.setInternetConnectionListener(LocalizationActivity.this);

        }

    @Override
    protected void onPause() {
        super.onPause();
        Webdata.removeInternetConnectionListener();
    }

    @Override
        public void onResume() {
            super.onResume();
            localizationDelegate.onResume(this);
        }

        @Override
        protected void attachBaseContext(Context newBase) {
            super.attachBaseContext(localizationDelegate.attachBaseContext(newBase));
        }

        @Override
        public Context getApplicationContext() {
            return localizationDelegate.getApplicationContext(super.getApplicationContext());
        }

        @Override
        public Resources getResources() {
            return localizationDelegate.getResources(super.getResources());
        }

        public final void setLanguage(String language) {
            localizationDelegate.setLanguage(this, language);
        }

        public final void setLanguage(Locale locale) {
            localizationDelegate.setLanguage(this, locale);
        }

        public final void setDefaultLanguage(String language) {
            localizationDelegate.setDefaultLanguage(language);
        }

        public final void setDefaultLanguage(Locale locale) {
            localizationDelegate.setDefaultLanguage(locale);
        }

        public final Locale getCurrentLanguage() {
            return localizationDelegate.getLanguage(this);
        }

        // Just override method locale change event
        @Override
        public void onBeforeLocaleChanged() {
        }

        @Override
        public void onAfterLocaleChanged() {
        }


    public void LoginConfirmationDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.message));

        alertDialog.setMessage("" + getString(R.string.add_to_favorite_it_txt));

        alertDialog.setNegativeButton("" + getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.setPositiveButton("" + getString(R.string.login_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CallSignInActivityMethod();
                    }
                });
        alertDialog.show();
    }


    public void CallSignInActivityMethod() {
        Intent signInIntent = new Intent(LocalizationActivity.this, RestaurantSignInSignUpActivity.class);
//        signInIntent.putExtra("login_type", "menu_page");
        startActivity(signInIntent);
    }

    @Override
    public void onInternetUnavailable() {
        runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LocalizationActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                alertDialog.setTitle("" + getString(R.string.no_internet));

                alertDialog.setMessage("" + getString(R.string.no_internet_conn_msg_txt));

                alertDialog.setNegativeButton("" + getString(R.string.ok_btn_txt), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (NetworkStatus.getConnectivityStatusBoolean(LocalizationActivity.this)){
                            dialog.dismiss();
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);

                        }else{

                        }
                    }
                });
                alertDialog.show();

            }

        });
    }

    @Override
    public void onCacheUnavailable() {



    }
}

