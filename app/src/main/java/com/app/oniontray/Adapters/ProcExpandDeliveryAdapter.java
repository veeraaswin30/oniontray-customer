package com.app.oniontray.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddressList;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;
import java.util.List;

public class ProcExpandDeliveryAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    private int quantity;
    private int price;
    private int total_price;

    private final ArrayList<AddressList> deliverItems;

    private String checkbox_pos = "";

    private deliveryChargeUpdateInterface callBack;

    public ProcExpandDeliveryAdapter(Context context, List<AddressList> deliverItems, deliveryChargeUpdateInterface callBack) {

        Context context1 = context;
        inflater = LayoutInflater.from(context1);
        this.deliverItems = (ArrayList<AddressList>) deliverItems;
        LoginPrefManager prefManager = new LoginPrefManager(context);
        this.callBack = callBack;

    }

    @Override
    public int getCount() {
        return deliverItems.size();
    }

    @Override
    public AddressList getItem(int position) {
        return deliverItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCheckedPosition(){
        return Integer.parseInt(checkbox_pos);
    }

    public String getselectedAddressID() {
        if (!checkbox_pos.isEmpty()) {
            return "" + this.deliverItems.get(Integer.parseInt(checkbox_pos)).getAddressId();
        } else {
            return "";
        }
    }

    public String getselectedAddress() {
        if (!checkbox_pos.isEmpty()) {
            return "" + this.deliverItems.get(Integer.parseInt(checkbox_pos)).getAddress();
        } else {
            return "";
        }
    }

    public static class ViewHolder {

        public ImageView address_type_img;
        public TextView address_type_name_txt, user_address_txt;
        public RadioButton posCheckBox;

    }

    public AddressList GetSelectedAddressVaues(int position){
        return deliverItems.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View expandView = convertView;

        final ProcExpandDeliveryAdapter.ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            expandView = inflater.inflate(R.layout.proc_to_check_expand_deliver_addr_row_layout, parent, false);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ProcExpandDeliveryAdapter.ViewHolder();

            holder.address_type_img = (ImageView) expandView.findViewById(R.id.proc_to_check_exp_list_add_row_img);
            holder.address_type_name_txt = (TextView) expandView.findViewById(R.id.proc_to_check_exp_list_row_type_txt);
            holder.user_address_txt = (TextView) expandView.findViewById(R.id.proc_to_check_exp_list_add_row_txt_view);
            holder.posCheckBox = (RadioButton) expandView.findViewById(R.id.proc_to_check_exp_list_add_row_check_box);

            holder.posCheckBox.setChecked(false);

            /************  Set holder with LayoutInflater ************/
            expandView.setTag(holder);

        } else {
            holder = (ProcExpandDeliveryAdapter.ViewHolder) expandView.getTag();
        }

        if (deliverItems.get(position).getAddressType().equals("Home")) {
            holder.address_type_img.setImageResource(R.drawable.poc_to_che_home_ic);
        } else if (deliverItems.get(position).getAddressType().equals("residence")) {
            holder.address_type_img.setImageResource(R.drawable.poc_to_che_residence_ic);
        } else if (deliverItems.get(position).getAddressType().equals("Office")) {
            holder.address_type_img.setImageResource(R.drawable.poc_to_che_office_ic);
        } else {
            holder.address_type_img.setImageResource(R.drawable.poc_to_che_general_ic);
        }

        holder.address_type_name_txt.setText("" + deliverItems.get(position).getAddressType());
        holder.user_address_txt.setText("" + deliverItems.get(position).getAddress());

        holder.posCheckBox.setChecked(false);

        if (!checkbox_pos.isEmpty()) {
            if (Integer.parseInt(checkbox_pos) == position) {
                holder.posCheckBox.setChecked(true);
            }
        }

        expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox_pos = "" + position;
                holder.posCheckBox.setChecked(true);
                notifyDataSetChanged();

                if (callBack != null) {
                    callBack.deliveryChargeUpdate(position);
                }
            }
        });

        return expandView;
    }

    public interface deliveryChargeUpdateInterface {
        void deliveryChargeUpdate(int position);
    }

}

