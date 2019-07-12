package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CountryListArray;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;

/**
 * Created by nextbrain on 2/15/2017.
 */

public class SelectLocationListAdapter extends RecyclerView.Adapter<SelectLocationListAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<CountryListArray> countryListArrays;

    String selected_country = "";

    private LoginPrefManager loginPrefManager;

    private boolean sele_country = false;

    public SelectLocationListAdapter(Context context, ArrayList<CountryListArray> countryListArrays) {

        this.context = context;
        this.countryListArrays = countryListArrays;

        this.loginPrefManager = new LoginPrefManager(context);

//        selected_country = loginPrefManager.getCountryID();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectLocationListAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_select_location_manually_row_item, parent, false));
    }

    public CountryListArray getSelectedCountry(){
        if(selected_country.isEmpty()){
            return new CountryListArray();
        }else{
            return countryListArrays.get(Integer.parseInt(selected_country));
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.location_name.setText(countryListArrays.get(position).getCountryName());

        if(!sele_country){

            if(!loginPrefManager.getCountryID().isEmpty()){

                if(Integer.parseInt(loginPrefManager.getCountryID())  == countryListArrays.get(position).getId()){
                    sele_country = true;
                    selected_country = ""+ position;
                }
            }

        }

        if (!selected_country.isEmpty()) {
            if (Integer.parseInt(selected_country) == position) {

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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.radio_btn.setButtonTintList(colorStateList);
                }
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
                    selected_country = "" + position;
                    notifyDataSetChanged();
                }

                if(countryAdapterInterface != null){
                    countryAdapterInterface.selectedCountryValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return countryListArrays.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView location_name;
        private RadioButton radio_btn;

        View rootView;

        public MyViewHolder(View itemView) {
            super(itemView);

            rootView = itemView;
            location_name = (TextView) itemView.findViewById(R.id.location_name);
            radio_btn = (RadioButton) itemView.findViewById(R.id.radio_btn);
        }
    }


    public CountryAdapterInterface countryAdapterInterface;

    public void CallCountryAdapterInterface(CountryAdapterInterface countryAdapterInterface){
        this.countryAdapterInterface = countryAdapterInterface;
    }

    public interface CountryAdapterInterface{
        void selectedCountryValue();
    }


}
