package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProcToCheckAvaliableSlotMob;

import java.util.ArrayList;


public class TestDelivSlotAdapter extends RecyclerView.Adapter<TestDelivSlotAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<ProcToCheckAvaliableSlotMob> deliverItems;

    public TestDelivSlotAdapter(Context context, ArrayList<ProcToCheckAvaliableSlotMob> Offers) {
        Context context1 = context;
        inflater = LayoutInflater.from(context1);
        this.deliverItems = Offers;
    }

    @Override
    public TestDelivSlotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.proc_to_chec_expand_deliv_slot_addr_lay, parent, false);
        TestDelivSlotAdapter.MyViewHolder holder = new TestDelivSlotAdapter.MyViewHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(TestDelivSlotAdapter.MyViewHolder holder, int position) {

        holder.deliv_date_txt.setText(""+ deliverItems.get(position).getDay());
    }

    @Override
    public int getItemCount() {
        return deliverItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView deliv_date_txt;

        public MyViewHolder(final View itemView) {
            super(itemView);

            deliv_date_txt = (TextView) itemView.findViewById(R.id.proc_to_chec_exp_row_item_date_txt);

        }

    }

}
