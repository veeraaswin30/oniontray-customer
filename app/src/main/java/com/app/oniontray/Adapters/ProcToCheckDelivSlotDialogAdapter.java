package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProcToCheckTime;

import java.util.ArrayList;


public class ProcToCheckDelivSlotDialogAdapter extends RecyclerView.Adapter<ProcToCheckDelivSlotDialogAdapter.MyDialogViewHolder> {


    private final Context mContext;
    private final ArrayList<ProcToCheckTime> myOutletList;

    private String checkbox_pos = "";


    public ProcToCheckDelivSlotDialogAdapter(Context context, ArrayList<ProcToCheckTime> outletList) {
        this.mContext = context;
        this.myOutletList = outletList;
    }

    @Override
    public ProcToCheckDelivSlotDialogAdapter.MyDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProcToCheckDelivSlotDialogAdapter.MyDialogViewHolder(LayoutInflater.from(mContext).inflate(R.layout.proc_to_che_deliv_slot_dialog_item_row_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(ProcToCheckDelivSlotDialogAdapter.MyDialogViewHolder holder, final int position) {

        holder.proc_to_check_dialog_row_txt_view.setText("" + myOutletList.get(position).getWeekMobTime());

        holder.proc_to_check_dialog_row_check_box_view.setChecked(false);

        if (!checkbox_pos.isEmpty()) {
            if (Integer.parseInt(checkbox_pos) == position) {
                holder.proc_to_check_dialog_row_check_box_view.setChecked(true);
            }
        }

        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox_pos = "" + position;
                notifyDataSetChanged();
            }
        });
    }

    public String get_selected_slot_time() {

        if(!checkbox_pos.isEmpty()){
            return myOutletList.get(Integer.parseInt(checkbox_pos)).getWeekMobTime();
        }else {
            return "";
        }

    }

    public String getSlotID(){
        return ""+myOutletList.get(Integer.parseInt(checkbox_pos)).getSlotId();
    }

    public class MyDialogViewHolder extends RecyclerView.ViewHolder {

        final View rowView;

        public final TextView proc_to_check_dialog_row_txt_view;
        public final CheckBox proc_to_check_dialog_row_check_box_view;

        public MyDialogViewHolder(View view) {
            super(view);

            rowView = view;

            proc_to_check_dialog_row_txt_view = (TextView) view.findViewById(R.id.proc_to_check_dialog_row_txt_view);
            proc_to_check_dialog_row_check_box_view = (CheckBox) view.findViewById(R.id.proc_to_check_dialog_row_check_box_view);

        }

    }

    @Override
    public int getItemCount() {
        return myOutletList.size();
    }

}
