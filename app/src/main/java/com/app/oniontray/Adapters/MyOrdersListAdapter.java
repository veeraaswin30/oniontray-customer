package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.Activites.TrackOrderStatesActivity;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.Activites.OrderDetailActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OrderList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class MyOrdersListAdapter extends RecyclerView.Adapter<MyOrdersListAdapter.MyOrderViewHolder> {

    private final Context moContext;
    private final LoginPrefManager loginPrefManager;
    private final ArrayList<OrderList> myOrdersArrayList;

    SimpleDateFormat new_format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
    SimpleDateFormat old_format = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);

    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

    public MyOrdersListAdapter(Context context, ArrayList<OrderList> myOrdersArrayList) {
        this.moContext = context;
        loginPrefManager = new LoginPrefManager(context);
        this.myOrdersArrayList = myOrdersArrayList;
    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyOrderViewHolder(LayoutInflater.from(moContext).inflate(R.layout.my_order_list_row, parent, false));
    }

    @Override
    public int getItemCount() {
        return myOrdersArrayList.size();
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, final int position) {

//        <!--1, 10, 12, 14, 19, 18-->

        if (myOrdersArrayList.get(position).getOrderStatusId() == 1) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_initiated_txt));
        } else if (myOrdersArrayList.get(position).getOrderStatusId() == 10) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_processed_txt));
        } else if (myOrdersArrayList.get(position).getOrderStatusId() == 12) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_delivered_txt));
        } else if (myOrdersArrayList.get(position).getOrderStatusId() == 14) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_shipped_txt));
        } else if (myOrdersArrayList.get(position).getOrderStatusId() == 19) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_dispatched_txt));
        } else if (myOrdersArrayList.get(position).getOrderStatusId() == 24) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_packed_txt));
        }else if (myOrdersArrayList.get(position).getOrderStatusId() == 11) {
            holder.my_order_restaurant_status.setText("" + moContext.getString(R.string.order_states_canceld_txt));
        }

        holder.my_order_restaurant_status.setTextColor(Color.parseColor("" + myOrdersArrayList.get(position).getColorCode()));

////        if(myOrdersArrayList.get(position).getOrderType() == 2){
////            holder.my_ord_list_row_devider_deliv_slot_lay.setVisibility(View.GONE);
////            holder.my_ord_list_row_devider_line_lay.setVisibility(View.GONE);
////        }else{
////            holder.my_ord_list_row_devider_deliv_slot_lay.setVisibility(View.VISIBLE);
////            holder.my_ord_list_row_devider_line_lay.setVisibility(View.VISIBLE);
////        }
//
//
//        try {
//
////            Date start_date = old_format.parse(""+myOrdersArrayList.get(position).getStartTime());
////            Date end_date = old_format.parse(""+myOrdersArrayList.get(position).getEndTime());
////            holder.my_ord_list_row_deli_time_txt_view.setText(""+new_format.format(start_date)+" - "+new_format.format(end_date));
//
//            Date order_date = old_date_format.parse(""+myOrdersArrayList.get(position).getCreatedDate());
//            holder.my_ord_list_row_ord_on_txt_view.setText(new_date_format.format(order_date));
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
////        holder.my_ord_list_row_amount_txt_view.setText(myOrdersArrayList.get(position).getTotalAmount()+""+moContext.getString(R.string.cost_type_txt));
//        holder.my_ord_list_row_amount_txt_view.setText(loginPrefManager.getFormatCurrencyValue(String.format("%.2f",Float.valueOf(myOrdersArrayList.get(position).getTotalAmount()))));
//
//        holder.my_ord_list_row_process_txt_view.setText(myOrdersArrayList.get(position).getStatusName());

        holder.my_order_restaurant_name.setText(myOrdersArrayList.get(position).getOutletName());
        holder.my_order_restaurant_amount.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(myOrdersArrayList.get(position).getTotalAmount().replace(",","")))));
        holder.my_order_restaurant_city_and_location_name.setText(myOrdersArrayList.get(position).getCityName() + " ," + myOrdersArrayList.get(position).getLocationName());

//        if (!myOrdersArrayList.get(position).getCouponAmount().equals("0")) {
//
//            int grand_total_amt = Integer.parseInt("" + myOrdersArrayList.get(position).getTotalAmount()));
//            int coupon_promo_amt = Integer.parseInt("" + response.body().getResponse().getOrderItems().get(0).getCouponAmount());
//
//            int redu_coupon_amt = (grand_total_amt - coupon_promo_amt);
//
//            my_ord_det_total_txt.setText(loginPrefManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf("" + redu_coupon_amt))));
//
//            my_ord_det_promo_code_table_row_lay.setVisibility(View.VISIBLE);
//            my_ord_det_promo_code_amt_txt_view.setText(""+loginPrefManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf(response.body().getResponse().getOrderItems().get(0).getCouponAmount()))));
//
//        } else {
//            my_ord_det_total_txt.setText(loginPrefManager.getFormatCurrencyValue(String.format("%.2f", Float.valueOf(response.body().getResponse().getOrderItems().get(0).getTotalAmount()))));
//        }

        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (myOrdersArrayList.get(position).getOrderStatusId()  == 19){

                    Intent delivery_boy_intent = new Intent(moContext, TrackOrderStatesActivity.class);
                    delivery_boy_intent.putExtra("order_id", "" + myOrdersArrayList.get(position).getId());
                    delivery_boy_intent.putExtra("Screen_Flow", "0");
                    moContext.startActivity(delivery_boy_intent);

                }else{

                    Intent order_det_intent = new Intent(moContext, OrderDetailActivity.class);
                    order_det_intent.putExtra("Order_id","" + myOrdersArrayList.get(position).getId());
                    moContext.startActivity(order_det_intent);

                }

//                Intent order_det_intent = new Intent(moContext, OrderDetailActivity.class);
//                order_det_intent.putExtra("Order_id", "" + myOrdersArrayList.get(position).getId());
//                moContext.startActivity(order_det_intent);

            }
        });


    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder {


        TextView my_order_restaurant_name, my_order_restaurant_amount, my_order_restaurant_city_and_location_name;

        final TextView my_order_restaurant_status;


        final View rowView;

        public MyOrderViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;

            my_order_restaurant_amount = (TextView) rowView.findViewById(R.id.my_order_restaurant_amount);
            my_order_restaurant_status = (TextView) itemView.findViewById(R.id.my_order_restaurant_status);

            my_order_restaurant_name = (TextView) rowView.findViewById(R.id.my_order_restaurant_name);
            my_order_restaurant_city_and_location_name = (TextView) rowView.findViewById(R.id.my_order_restaurant_city_and_location_name);

        }

    }


}
