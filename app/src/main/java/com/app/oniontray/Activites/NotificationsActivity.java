package com.app.oniontray.Activites;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.app.oniontray.Adapters.NotifiListRecyAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.Models.MessageData;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.NotificationListItemOffsetDecor;
import com.app.oniontray.RequestModels.NotifData;
import com.app.oniontray.RequestModels.NotifiListResp;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationsActivity extends LocalizationActivity implements NotifiListRecyAdapter.NotifDeleteInterface {

    private Toolbar myToolbar;

    private TextView notifi_list_empty_txt_view;
//    private ListView notification_listview;
      private TextView notification;
    private RecyclerView notifi_Recycler_view;
    private NotifiListRecyAdapter notifiListRecyAdapter;

    //    private NotificationAdapter notificationAdapter;
    ArrayList<MessageData> arrMessageData = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list_layout);

        myToolbar = (Toolbar) findViewById(R.id.notification_toolbar_id);
        myToolbar.setTitle("");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        myToolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));


        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(NotificationsActivity.this));


        if (language.equals("en")) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });


        notifi_list_empty_txt_view = (TextView) findViewById(R.id.notifi_list_empty_txt_view);
//        notification_listview = (ListView) findViewById(R.id.notification_list);
        notification = (TextView) findViewById(R.id.notification);
        notification.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

        notifi_Recycler_view = (RecyclerView) findViewById(R.id.notifi_Recycler_view);
        notifi_Recycler_view.setLayoutManager(new LinearLayoutManager(NotificationsActivity.this));
        notifi_Recycler_view.setHasFixedSize(true);
        notifi_Recycler_view.addItemDecoration(new NotificationListItemOffsetDecor(NotificationsActivity.this, R.dimen.notifications_list_item_row_line_hight));

        NotifiListRequestMethod();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void NotifiListRequestMethod() {

        try {

            if (progressDialog != null) {
                progressDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);

            apiService.get_notification_list_call(loginPrefManager.getStringValue("user_id"), loginPrefManager.getStringValue("user_token"),
                    loginPrefManager.getStringValue("Lang_code")).enqueue(new Callback<NotifiListResp>() {
                @Override
                public void onResponse(Call<NotifiListResp> call, Response<NotifiListResp> response) {
                    try {
                        progressDialog.dismiss();

//                        Log.e("get_notifi_list_call", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            notifiListRecyAdapter = new NotifiListRecyAdapter(NotificationsActivity.this, (ArrayList<NotifData>) response.body().getResponse().getData());
                            notifiListRecyAdapter.NotifiInterfaceCallMethod(NotificationsActivity.this);
                            notifi_Recycler_view.setAdapter(notifiListRecyAdapter);

                            UpdateNotifList(response.body().getResponse().getData().size());

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            UpdateNotifList(0);
                            Toast.makeText(NotificationsActivity.this, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<NotifiListResp> call, Throwable t) {
                    progressDialog.dismiss();
//                    Log.e("error", t.toString());
                }
            });

        } catch (Exception e) {
//            Log.e("addresListRequestMethod", "Exception :" + e.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void UpdateNotifList(int count) {
        if (count == 0) {
            notifi_list_empty_txt_view.setVisibility(View.VISIBLE);
        } else {
            notifi_list_empty_txt_view.setVisibility(View.GONE);
        }
    }


}





