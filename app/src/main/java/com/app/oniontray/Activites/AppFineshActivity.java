package com.app.oniontray.Activites;

import android.app.LocalActivityManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.app.oniontray.LocalizationActivity.LocalizationActivity;


public class AppFineshActivity extends LocalizationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LocalActivityManager localActivityManager = new LocalActivityManager(this, true);
        localActivityManager.removeAllActivities();

//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);

        finish();

    }
}
