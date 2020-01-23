package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Activites.OrderDetailActivity;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.NotifData;
import com.app.oniontray.RequestModels.NotifiDeleteResp;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotifiListRecyAdapter extends RecyclerView.Adapter<NotifiListRecyAdapter.HomeStorViewHolder> {

    private Context context;
    private ArrayList<NotifData> dataList;

    private final LoginPrefManager preferenceAccess;
    private final DDProgressBarDialog ddProgressBarDialog;



    public NotifiListRecyAdapter(Context context, ArrayList<NotifData> arrMessageData) {
        this.context = context;
        this.dataList = arrMessageData;

        preferenceAccess = new LoginPrefManager(context);
        ddProgressBarDialog = new DDProgressBarDialog(context);

    }

    @Override
    public NotifiListRecyAdapter.HomeStorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotifiListRecyAdapter.HomeStorViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_list_row_item_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeStorViewHolder holder, final int position) {

        Glide.with(context).load("" + dataList.get(position).getLogoImage()).centerCrop().bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.notification_img);

        holder.notification_title_tv.setText("" + dataList.get(position).getMessage());
        holder.notification_time.setText("" + dataList.get(position).getCreatedDate());
        holder.notification_time.setTextColor(Color.parseColor(preferenceAccess.getThemeFontColor()));

        holder.notifi_row_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRequestMethod(dataList.get(position).getId(), position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent order_det_intent = new Intent(context, OrderDetailActivity.class);
                order_det_intent.putExtra("Order_id","" + dataList.get(position).getOrderId());
                context.startActivity(order_det_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private void DeleteRequestMethod(int notifi_id, final int pos) {

        if (ddProgressBarDialog != null) {
            ddProgressBarDialog.show();
        }

        try {

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.notification_list_delete_call("" + preferenceAccess.getStringValue("user_id"),
                    "" + preferenceAccess.getStringValue("user_token"), "" + preferenceAccess.getStringValue("Lang_code"),
                    "" + notifi_id).enqueue(new Callback<NotifiDeleteResp>() {
                @Override
                public void onResponse(Call<NotifiDeleteResp> call, Response<NotifiDeleteResp> response) {

                    ddProgressBarDialog.dismiss();

//                    Log.e("act_notifi_list_layout", "-" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        dataList.remove(pos);
                        notifyDataSetChanged();

                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        if(notifDeleteInterface!= null){
                            if(dataList.size() == 0){
                                notifDeleteInterface.UpdateNotifList(0);
                            }
                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<NotifiDeleteResp> call, Throwable t) {
                    ddProgressBarDialog.dismiss();
//                    Log.e("error", t.toString());
                }
            });

        } catch (Exception e) {
            ddProgressBarDialog.dismiss();
//            Log.e("addresListRequestMethod", "Exception :" + e.getMessage());
        }

    }


    public interface NotifDeleteInterface{
        void UpdateNotifList(int count);
    }

    private NotifDeleteInterface notifDeleteInterface;

    public void NotifiInterfaceCallMethod(NotifDeleteInterface notifDeleteInterface){
        this.notifDeleteInterface = notifDeleteInterface;
    }


    public class HomeStorViewHolder extends RecyclerView.ViewHolder {

        TextView notification_title_tv, notification_time;
        ImageView notification_img, notifi_row_del_btn;

        public HomeStorViewHolder(View itemView) {
            super(itemView);

            this.notification_title_tv = (TextView) itemView.findViewById(R.id.notf_title);
            this.notification_time = (TextView) itemView.findViewById(R.id.notf_text);

            this.notification_img = (ImageView) itemView.findViewById(R.id.notf_image);

            this.notifi_row_del_btn = (ImageView) itemView.findViewById(R.id.notifi_row_del_btn);

        }

    }


}
