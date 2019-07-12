package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CreditWalletData;
import com.app.oniontray.Utils.LoginPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by nextbrain on 16/5/17.
 */

public class MyWalletCreditAdapter extends RecyclerView.Adapter<MyWalletCreditAdapter.WalletViewHolder> {

    private Context context;
    private ArrayList<CreditWalletData> creditWalletDatas;

    private LoginPrefManager loginPrefManager;


    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat deliv_date_new_format = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);


    public MyWalletCreditAdapter(Context context, ArrayList<CreditWalletData> creditWalletDatas) {
        this.context = context;
        this.creditWalletDatas = creditWalletDatas;

        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public MyWalletCreditAdapter.WalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyWalletCreditAdapter.WalletViewHolder(LayoutInflater.from(context).inflate(R.layout.my_wallet_hist_row_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyWalletCreditAdapter.WalletViewHolder holder, int position) {

        try {

            //        holder.my_wallet_addapter_tit_txt.setText("" + creditWalletDatas.get(position).getWalletId());
            holder.my_wallet_addapter_tit_txt.setText("" + context.getString(R.string.my_wallet_added_to_food_acco_txt));

            if (!creditWalletDatas.get(position).getCreatedDate().isEmpty()) {
                Date order_date = old_date_format.parse("" + creditWalletDatas.get(position).getCreatedDate().trim());
                holder.my_wallet_adapter_date_txt.setText("" + deliv_date_new_format.format(order_date));
            }

//            holder.my_wallet_adapter_date_txt.setText("" + creditWalletDatas.get(position).getCreatedDate());
            holder.my_wallet_adapter_amount_txt.setText(loginPrefManager.getFormatCurrencyValue("" + creditWalletDatas.get(position).getTotalAmount()));


        }catch (Exception e){
//            Log.e("Exception", "" + e.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return creditWalletDatas.size();
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder {

        private TextView my_wallet_addapter_tit_txt;
        private TextView my_wallet_adapter_date_txt;
        private TextView my_wallet_adapter_amount_txt;

        public WalletViewHolder(View itemView) {
            super(itemView);

            my_wallet_addapter_tit_txt = (TextView) itemView.findViewById(R.id.my_wallet_addapter_tit_txt);
            my_wallet_adapter_date_txt = (TextView) itemView.findViewById(R.id.my_wallet_adapter_date_txt);
            my_wallet_adapter_amount_txt = (TextView) itemView.findViewById(R.id.my_wallet_adapter_amount_txt);

        }

    }

}
