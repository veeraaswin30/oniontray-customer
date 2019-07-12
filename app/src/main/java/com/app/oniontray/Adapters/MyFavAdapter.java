package com.app.oniontray.Adapters;


import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.app.oniontray.Activites.ProductMenuItemListWithDynamicTabs;
import com.bumptech.glide.Glide;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddFavourites;
import com.app.oniontray.RequestModels.MyFavouritesList;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("CanBeFinal")
public class MyFavAdapter extends RecyclerView.Adapter<MyFavAdapter.MyViewHolder> {

    private final Context context;
    private final DDProgressBarDialog progressBarDialog;
    private final LoginPrefManager prefManager;

    private ArrayList<MyFavouritesList> My_Fav_list;

    private final LayoutInflater inflater;


    public MyFavAdapter(Context context, ArrayList<MyFavouritesList> my_fav_list) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.My_Fav_list = my_fav_list;

        this.progressBarDialog = new DDProgressBarDialog(context);
        this.prefManager = new LoginPrefManager(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_fav_row_item_lay, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        Glide.with(context).load(My_Fav_list.get(position).getFeaturedImage()).centerCrop().into(holder.my_fav_sto_image);

        holder.my_fav_fav_toogle_btn.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_heart_color));
        holder.my_fav_fav_toogle_btn.setChecked(true);
        holder.my_fav_fav_toogle_btn.setTag(position);
        holder.my_fav_fav_toogle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreRemoveFavoriteRequest("" + My_Fav_list.get(position).getVendorId(), "" + My_Fav_list.get(position).getOutletId(), position);
            }
        });

        holder.my_fav_deli_time_dura_btn.setText(My_Fav_list.get(position).getOutletsDeliveryTime() + "\n" + context.getString(R.string.mins_txt));
        holder.my_fav_sto_name.setText(My_Fav_list.get(position).getOutletName());

        holder.my_fav_catego_name.setText(My_Fav_list.get(position).getCuisineName());

        if (My_Fav_list.get(position).getOutletsAverageRating() != null) {
            holder.my_fav_ratting_bar.setRating(Float.parseFloat(String.valueOf(My_Fav_list.get(position).getOutletsAverageRating())));
        }

        holder.my_fav_ratting_value_txt.setText((String.valueOf(My_Fav_list.get(position).getOutletsAverageRating())));
        holder.my_fav_deli_dura_txt.setText(My_Fav_list.get(position).getOutletsDeliveryTime() + " " + context.getString(R.string.mins_txt));

        holder.my_fav_min_deli_coast_txt.setText("" + context.getString(R.string.mini_deli_cost_txt) + " "
                + prefManager.getFormatCurrencyValue("" + My_Fav_list.get(position).getMinimumOrderAmount()));

        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_rece = new Intent("dynamic_tabs");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent_rece);

                Intent out_let = new Intent(context, ProductMenuItemListWithDynamicTabs.class);
                out_let.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                out_let.putExtra("vender_name", "" + My_Fav_list.get(position).getVendorName());
                out_let.putExtra("outlet_id", "" + My_Fav_list.get(position).getOutletId());
                out_let.putExtra("vender_id", "" + My_Fav_list.get(position).getVendorId());
                context.startActivity(out_let);

            }
        });

    }

    private void StoreRemoveFavoriteRequest(String vender_id, String outlet_id, final int position) {

        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        APIService storeList = Webdata.getRetrofit().create(APIService.class);
        storeList.addto_favourite("" + prefManager.getStringValue("user_id"), "" + vender_id, "" + outlet_id,
                "" + prefManager.getStringValue("user_token"),
                "" + prefManager.getStringValue("Lang_code"))
                .enqueue(new Callback<AddFavourites>() {
                             @Override
                             public void onResponse(Call<AddFavourites> call, Response<AddFavourites> response) {

                                 try {
                                     progressBarDialog.dismiss();

                                     if (response.body().getResponse().getHttpCode() == 200) {

                                         if (response.body().getResponse().getStatus() == 0) {
                                             My_Fav_list.remove(position);
                                             notifyDataSetChanged();
                                         }

                                         if (myFavAdapterInterface != null) {
//                                             Log.e("myFavAdapterInterface", "adapter" + My_Fav_list.size());
                                             myFavAdapterInterface.FavAdaptercount(My_Fav_list.size());
                                         }

                                     } else if (response.body().getResponse().getHttpCode() == 400) {
//                                         Log.e("failure", response.body().getResponse().getMessage());
                                     }

                                 } catch (Exception e) {
//                                     Log.e("StoreRemFavoriteRequest", "Exception" + e.getMessage());
                                 }

                             }

                             @Override
                             public void onFailure(Call<AddFavourites> call, Throwable t) {
                                 progressBarDialog.dismiss();
//                                 Log.e("StoreRemFavoriteRequest", "onFailure" + t.getMessage());
                             }
                         }

                );

    }


    @Override
    public int getItemCount() {
        return My_Fav_list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final ImageView my_fav_sto_image;
        public final ToggleButton my_fav_fav_toogle_btn;

        public final Button my_fav_deli_time_dura_btn;
        public final TextView my_fav_sto_name;

        public final TextView my_fav_catego_name;
        public final RatingBar my_fav_ratting_bar;

        public final TextView my_fav_ratting_value_txt;
        public final TextView my_fav_deli_dura_txt;

        public final TextView my_fav_min_deli_coast_txt;

        public final View itemview;

        public MyViewHolder(View itemView) {
            super(itemView);

            itemview = itemView;

            my_fav_sto_image = (ImageView) itemView.findViewById(R.id.my_fav_sto_image);
            my_fav_fav_toogle_btn = (ToggleButton) itemView.findViewById(R.id.my_fav_fav_toogle_btn);

            my_fav_deli_time_dura_btn = (Button) itemView.findViewById(R.id.my_fav_deli_time_dura_btn);
            my_fav_sto_name = (TextView) itemView.findViewById(R.id.my_fav_sto_name);

            my_fav_catego_name = (TextView) itemView.findViewById(R.id.my_fav_catego_name);
            my_fav_ratting_bar = (RatingBar) itemView.findViewById(R.id.my_fav_ratting_bar);

            my_fav_ratting_value_txt = (TextView) itemView.findViewById(R.id.my_fav_ratting_value_txt);
            my_fav_deli_dura_txt = (TextView) itemView.findViewById(R.id.my_fav_deli_dura_txt);

            my_fav_min_deli_coast_txt = (TextView) itemView.findViewById(R.id.my_fav_min_deli_coast_txt);

        }
    }


    public interface MyFavAdapterInterface {
        void FavAdaptercount(int count);
    }

    private MyFavAdapterInterface myFavAdapterInterface;

    public void MyFavAdapterInterfaceCallback(MyFavAdapterInterface myFavAdapInterface) {
        myFavAdapterInterface = myFavAdapInterface;
    }


}


