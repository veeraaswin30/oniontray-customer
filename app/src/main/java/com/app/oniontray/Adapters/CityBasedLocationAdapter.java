package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.LocaListData;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEXTBRAIN on 2/16/2017.
 */

public class CityBasedLocationAdapter extends RecyclerView.Adapter<CityBasedLocationAdapter.MyLocationViewHolder> {

    private Context context;
    private ArrayList<LocaListData> locaListDatas;
    private LoginPrefManager loginPrefManager;

    private String selected_location = "";

    private boolean sele_country = false;

    LocationAdapterInterface locationAdapterInterface;

    public CityBasedLocationAdapter(Context context, ArrayList<LocaListData> locaListDatas) {
        this.context = context;
        this.locaListDatas = locaListDatas;
        this.loginPrefManager = new LoginPrefManager(context);

        this.selected_location = loginPrefManager.getLocID();
    }


    public LocaListData getSelectedLocation() {
        if (selected_location.isEmpty()) {
            return new LocaListData();
        } else {
            return locaListDatas.get(Integer.parseInt(selected_location));
        }
    }


    @Override
    public MyLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityBasedLocationAdapter.MyLocationViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_select_location_manually_row_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyLocationViewHolder holder, final int position) {

        holder.location_name.setText(locaListDatas.get(position).getZoneName());

        if (!sele_country) {

            if(!loginPrefManager.getLocID().isEmpty()){
                if (Integer.parseInt(loginPrefManager.getLocID()) == locaListDatas.get(position).getId()) {
                    sele_country = true;
                    selected_location = "" + position;
                }
            }

        }

        if (!selected_location.isEmpty()) {
            if (Integer.parseInt(selected_location) == position) {
                ColorStateList colorStateList = new ColorStateList(
                        new int[][]{

                                new int[]{-android.R.attr.state_enabled}, //disabled
                                new int[]{android.R.attr.state_enabled} //enabled
                        },
                        new int[] {

                                Color.BLACK //disabled
                                ,Color.parseColor(loginPrefManager.getThemeFontColor()) //enabled

                        }
                );
                holder.radio_btn.setChecked(true);
                holder.radio_btn.setButtonTintList(colorStateList);
                holder.location_name.setTextColor(context.getResources().getColor(R.color.colorAccent));
                holder.location_name.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
            } else {
                holder.radio_btn.setChecked(false);
                holder.location_name.setTextColor(context.getResources().getColor(R.color.dark_txt_color));
            }
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.radio_btn.isChecked()) {
                    selected_location = "" + position;
                    notifyDataSetChanged();
                }

                if (locationAdapterInterface != null) {
                    locationAdapterInterface.selectedCountryValue();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return locaListDatas.size();
    }

    public void setFilter(List<LocaListData> filterdata) {
        locaListDatas = new ArrayList<>();
        locaListDatas.addAll(filterdata);
        notifyDataSetChanged();
    }


    public class MyLocationViewHolder extends RecyclerView.ViewHolder {

        private TextView location_name;
        private RadioButton radio_btn;

        View rootView;

        public MyLocationViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;

            location_name = (TextView) itemView.findViewById(R.id.location_name);
            radio_btn = (RadioButton) itemView.findViewById(R.id.radio_btn);

        }
    }

    public void CallLocationAdapterInterface(LocationAdapterInterface locationAdapterInterface) {
        this.locationAdapterInterface = locationAdapterInterface;
    }

    public interface LocationAdapterInterface {
        void selectedCountryValue();
    }

}
