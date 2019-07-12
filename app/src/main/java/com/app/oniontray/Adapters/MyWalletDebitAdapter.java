package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.Activites.OrderDetailActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.DebitWalletData;
import com.app.oniontray.Utils.LoginPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nextbrain on 16/5/17.
 */

public class MyWalletDebitAdapter extends RecyclerView.Adapter<MyWalletDebitAdapter.WalletViewHolder> {

    private Context context;
    private ArrayList<DebitWalletData> debitWalletDatas;

    private LoginPrefManager loginPrefManager;

    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat deliv_date_new_format = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);

//    2017-05-16 14:39:08


    public MyWalletDebitAdapter(Context context, ArrayList<DebitWalletData> DebitWalletData) {
        this.context = context;
        this.debitWalletDatas = DebitWalletData;

        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public MyWalletDebitAdapter.WalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WalletViewHolder(LayoutInflater.from(context).inflate(R.layout.my_wallet_hist_row_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyWalletDebitAdapter.WalletViewHolder holder, final int position) {

        try {

            holder.my_wallet_addapter_view_btn_txt.setVisibility(View.VISIBLE);

//            holder.my_wallet_addapter_tit_txt.setText("" + debitWalletDatas.get(position).getOrderId());
            holder.my_wallet_addapter_tit_txt.setText("" + context.getString(R.string.my_wallet_deducted_to_account));

            if (!debitWalletDatas.get(position).getCreatedDate().isEmpty()) {
                Date order_date = old_date_format.parse("" + debitWalletDatas.get(position).getCreatedDate().trim());
                holder.my_wallet_adapter_date_txt.setText("" + deliv_date_new_format.format(order_date));
            }

            holder.my_wallet_adapter_amount_txt.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf("" + debitWalletDatas.get(position).getTotalAmount()))));
//            holder.my_wallet_adapter_date_txt.setText("" + debitWalletDatas.get(position).getCreatedDate());

            holder.my_wallet_addapter_view_btn_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent order_det_intent = new Intent(context, OrderDetailActivity.class);
                    order_det_intent.putExtra("Order_id", "" + debitWalletDatas.get(position).getOrderId());
                    context.startActivity(order_det_intent);

                }
            });

        } catch (Exception e) {
//            Log.e("Exception", "" + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return debitWalletDatas.size();
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder {

        private TextView my_wallet_addapter_tit_txt;
        private TextView my_wallet_addapter_view_btn_txt;

        private TextView my_wallet_adapter_date_txt;
        private TextView my_wallet_adapter_amount_txt;

        public WalletViewHolder(View itemView) {
            super(itemView);

            my_wallet_addapter_tit_txt = (TextView) itemView.findViewById(R.id.my_wallet_addapter_tit_txt);
            my_wallet_addapter_view_btn_txt = (TextView) itemView.findViewById(R.id.my_wallet_addapter_view_btn_txt);

            my_wallet_adapter_date_txt = (TextView) itemView.findViewById(R.id.my_wallet_adapter_date_txt);
            my_wallet_adapter_amount_txt = (TextView) itemView.findViewById(R.id.my_wallet_adapter_amount_txt);

        }

    }

}
