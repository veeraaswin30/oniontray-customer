package com.app.oniontray.Utils;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.WindowManager;


public class NetworkChangeReceiver extends BroadcastReceiver {

    private AlertDialog.Builder alertDialogBuilder = null;
    private AlertDialog networkAlertDialog = null;

    private static boolean firstConnect = true;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

            Log.e("myLogs", "Network connectivity change");

            if (intent.getExtras() != null) {
                NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                    Log.e("myLogs", "Network " + ni.getTypeName() + " connected");
                } else {
                    Log.e("myLogs", "Network " + ni.getTypeName() + " connected");
                }
            }

            if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                Log.e("myLogs", "There's no network connectivity");
            }

        }

    }


    private void CallNetWorkStatusDialog(final Context context, int status) {

        alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder
                .setMessage("No internet connection on your device. Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton(" Enable Internet ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(dialogIntent);
                            }
                        });

        networkAlertDialog = alertDialogBuilder.create();
        networkAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        if (status == 0 && !networkAlertDialog.isShowing()) {
            networkAlertDialog.show();
        }

    }


    private void CallNetWorkStatusDialog(final Context context, boolean status) {

        alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder
                .setMessage("No internet connection on your device. Would you like to enable it?")
                .setTitle("No Internet Connection")
                .setCancelable(false)
                .setPositiveButton(" Enable Internet ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(dialogIntent);
                            }
                        });

        networkAlertDialog = alertDialogBuilder.create();
        networkAlertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        if (!status && !networkAlertDialog.isShowing()) {
            networkAlertDialog.show();
        }

    }


}
