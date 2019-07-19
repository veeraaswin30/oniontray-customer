package com.app.oniontray.GCM;

/**
 * Created by Invenzo on 03-10-2016.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.app.oniontray.Activites.BaseMenuTabActivity;
import com.app.oniontray.Activites.NotificationsActivity;
import com.app.oniontray.Utils.LoginPrefManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.app.oniontray.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private LoginPrefManager loginPrefManager;
    String content;

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "Message: " + remoteMessage.toString());

        Log.e(TAG, "NotificationItem data id: " + remoteMessage.getData().get("id"));
        Log.e(TAG, "NotificationItem data custom: " + remoteMessage.getData().get("custom"));
        Log.e(TAG, "NotificationItem data title: " + remoteMessage.getData().get("title"));
        Log.e(TAG, "NotificationItem data message: " + remoteMessage.getData().get("message"));
        Log.e(TAG, "NotificationItem: " + remoteMessage.getData().toString());
        content = remoteMessage.getData().get("message");


//
        loginPrefManager = new LoginPrefManager(getBaseContext());
        if (loginPrefManager.getStringValue("login_email").length() == 0) {

            Log.e("App Login status ", "false");

            sendSplashScreenNotification(remoteMessage.getData().get("title"), "Foodboy Offer Message");

        } else {

            Log.e("App Login status ", "true");

            sendOrderNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("id"));

        }


    }


    private void sendSplashScreenNotification(String message, String noti_title) {

        String channelId = getString(R.string.default_notification_channel_id);
        String channel_name = "OnionTray";
        CharSequence charSequence = "OnionTray Channel";

        int importance = NotificationManager.IMPORTANCE_HIGH;

        Intent intent = new Intent(this, BaseMenuTabActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channel_name)
                .setSmallIcon(R.drawable.notification_ic).setColor(getResources().getColor(R.color.colorPrimary))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle("" + noti_title)
                .setContentText(message)
                .setAutoCancel(true)
                .setChannelId(channel_name)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {

            NotificationChannel notificationChannel = new NotificationChannel(channel_name, charSequence, importance);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else {
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }


    private void sendOrderNotification(String message, String Order_id) {

        String channel_name = "OnionTray";
        CharSequence charSequence = "OnionTray Channel";

        int importance = NotificationManager.IMPORTANCE_HIGH;

        String channelId = getString(R.string.default_notification_channel_id);


        Intent intent = new Intent(this, NotificationsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, 0);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channel_name)
                .setSmallIcon(R.drawable.notification_ic).setColor(getResources().getColor(R.color.colorPrimary))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                .setContentTitle(content)
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setChannelId(channel_name)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {

            NotificationChannel notificationChannel = new NotificationChannel(channel_name, charSequence, importance);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

        } else {
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        loginPrefManager.setStringValue("device_token",s);
    }
}