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
import com.app.oniontray.RequestModels.CityListDa;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/16/2017.
 */

public class CountryBasedCityAdapter extends RecyclerView.Adapter<CountryBasedCityAdapter.CityListViewHolder> {

    private Context context;
    private ArrayList<CityListDa> city_list_items;
    private LoginPrefManager loginPrefManager;

    private String selected_city = "";

    private boolean sele_city = false;

    public CountryBasedCityAdapter(Context context, ArrayList<CityListDa> city_list_items) {
        this.context = context;
        this.city_list_items = city_list_items;
        this.loginPrefManager = new LoginPrefManager(context);

//        this.selected_city = loginPrefManager.getCityID();
    }


    public CityListDa getSelectedCity() {
        if (selected_city.isEmpty()) {
            return new CityListDa();
        } else {
            return city_list_items.get(Integer.parseInt(selected_city));
        }
    }


    @Override
    public CityListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CountryBasedCityAdapter.CityListViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_select_location_manually_row_item, parent, false));
    }


    @Override
    public void onBindViewHolder(final CityListViewHolder holder, final int position) {

        holder.location_name.setText(city_list_items.get(position).getCityName());

        if (!sele_city) {

            if(!loginPrefManager.getCityID().isEmpty()){
                if (Integer.parseInt(loginPrefManager.getCityID()) == city_list_items.get(position).getId()) {
                    sele_city = true;
                    selected_city = "" + position;
                }
            }

        }

        if (!selected_city.isEmpty()) {
            if (Integer.parseInt(selected_city) == position) {

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
                    selected_city = "" + position;
                    notifyDataSetChanged();
                }

                if (cityAdapterinterface != null) {
                    cityAdapterinterface.selectedCityvalue();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return city_list_items.size();
    }


    public class CityListViewHolder extends RecyclerView.ViewHolder {

        private TextView location_name;
        private RadioButton radio_btn;

        View rootView;

        public CityListViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            location_name = (TextView) itemView.findViewById(R.id.location_name);
            radio_btn = (RadioButton) itemView.findViewById(R.id.radio_btn);

        }
    }

    public CityAdapterinterface cityAdapterinterface;

    public void CallCityAdapterInterface(CityAdapterinterface cityAdapterinterface) {
        this.cityAdapterinterface = cityAdapterinterface;
    }

    public interface CityAdapterinterface {
        void selectedCityvalue();
    }


}
