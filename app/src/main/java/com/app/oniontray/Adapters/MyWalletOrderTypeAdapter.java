package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.oniontray.R;

/**
 * Created by NEXTBRAIN on 3/12/2017.
 */

public class MyWalletOrderTypeAdapter extends RecyclerView.Adapter<MyWalletOrderTypeAdapter.WalletViewHolder>{

    private Context context;


    public MyWalletOrderTypeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public WalletViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyWalletOrderTypeAdapter.WalletViewHolder(LayoutInflater.from(context).inflate(R.layout.my_wallet_hist_row_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(WalletViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder{

        public WalletViewHolder(View itemView) {
            super(itemView);
        }

    }


}
