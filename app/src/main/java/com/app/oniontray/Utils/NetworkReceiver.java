package com.app.oniontray.Utils;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.app.oniontray.AppControler.onionTray;

import java.util.List;

public class NetworkReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {

        ((onionTray)context.getApplicationContext()).CallNetworkDialogMethod();


    }


    public boolean isAppForground(Context mContext) {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }
        return true;
    }


}
