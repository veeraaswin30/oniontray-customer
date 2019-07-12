package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddressList;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nextbrain on 24/5/17.
 */

public class DelivFragDeliveryAddressAdapter extends RecyclerView.Adapter<DelivFragDeliveryAddressAdapter.MyViewHolder> {


    private Context context;
    private int quantity;
    private int price;
    private int total_price;

    private final ArrayList<AddressList> deliverItems;

    private String checkbox_pos = "";

    private DelivFragDeliveryAddressAdapter.deliveryChargeUpdateInterface callBack;


    public DelivFragDeliveryAddressAdapter(Context context, List<AddressList> deliverItems, DelivFragDeliveryAddressAdapter.deliveryChargeUpdateInterface callBack) {

        this.context = context;
        this.deliverItems = (ArrayList<AddressList>) deliverItems;
        LoginPrefManager prefManager = new LoginPrefManager(context);
        this.callBack = callBack;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DelivFragDeliveryAddressAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.proc_to_check_expand_deliver_addr_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        if (deliverItems.get(position).getAddressType().equals("Home")) {
            holder.address_type_img.setImageResource(R.drawable.ic_home_orange_24dp);
        } else if (deliverItems.get(position).getAddressType().equals("residence")) {
            holder.address_type_img.setImageResource(R.drawable.residence);
        } else if (deliverItems.get(position).getAddressType().equals("Office")) {
            holder.address_type_img.setImageResource(R.drawable.ic_office);
        } else {
            holder.address_type_img.setImageResource(R.drawable.ic_menu);
        }

        holder.address_type_name_txt.setText("" + deliverItems.get(position).getAddressType());

        System.out.println("," + deliverItems.get(position).getAddressType());
        holder.user_address_txt.setText("" + deliverItems.get(position).getAddress());

        holder.posCheckBox.setChecked(false);

        if (!checkbox_pos.isEmpty()) {
            if (Integer.parseInt(checkbox_pos) == position) {
                holder.posCheckBox.setChecked(true);
            }
        }

        holder.expandView.setOnClickListener(new View.OnClickListener() {
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


    }

    @Override
    public int getItemCount() {
        return deliverItems.size();
    }

    public AddressList getItem(int position) {
        return deliverItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getCheckedPosition() {
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


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View expandView;

        public ImageView address_type_img;
        public TextView address_type_name_txt, user_address_txt;
        public RadioButton posCheckBox;

        public MyViewHolder(final View itemView) {
            super(itemView);

            expandView = itemView;

            address_type_img = (ImageView) itemView.findViewById(R.id.proc_to_check_exp_list_add_row_img);
            address_type_name_txt = (TextView) itemView.findViewById(R.id.proc_to_check_exp_list_row_type_txt);
            user_address_txt = (TextView) itemView.findViewById(R.id.proc_to_check_exp_list_add_row_txt_view);
            posCheckBox = (RadioButton) itemView.findViewById(R.id.proc_to_check_exp_list_add_row_check_box);

            posCheckBox.setChecked(false);

        }
    }


    public interface deliveryChargeUpdateInterface {
        void deliveryChargeUpdate(int position);
    }


}
