package com.app.oniontray.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.oniontray.Interface.ProcToCheckDelivSlotInterface;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProcToCheckAvaliableSlotMob;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;
import java.util.List;


public class ProcExpandDeliverySlotAdapter extends BaseAdapter {

    private final LayoutInflater inflater;

    private ProcToCheckDelivSlotInterface procToCheckDelivSlotInter;

    public void procToCheckDelivSlotInterface(ProcToCheckDelivSlotInterface procToCheckDelivSlotInterface) {
        this.procToCheckDelivSlotInter = procToCheckDelivSlotInterface;
    }

    private final ArrayList<ProcToCheckAvaliableSlotMob> deliverItems;

    public ProcExpandDeliverySlotAdapter(Context context, List<ProcToCheckAvaliableSlotMob> deliverItems) {

        Context context1 = context;
        inflater = LayoutInflater.from(context1);
        this.deliverItems = (ArrayList<ProcToCheckAvaliableSlotMob>) deliverItems;
        LoginPrefManager prefManager = new LoginPrefManager(context);

    }

    @Override
    public int getCount() {
        return deliverItems.size();
    }

    @Override
    public ProcToCheckAvaliableSlotMob getItem(int position) {
        return deliverItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView deliv_date_txt;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View expandView = convertView;

        final ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            expandView = inflater.inflate(R.layout.proc_to_chec_expand_deliv_slot_addr_lay, parent, false);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();

            holder.deliv_date_txt = (TextView) expandView.findViewById(R.id.proc_to_chec_exp_row_item_date_txt);

            /************  Set holder with LayoutInflater ************/
            expandView.setTag(holder);

        } else {
            holder = (ViewHolder) expandView.getTag();
        }

//        Log.e("getDay",""+ deliverItems.get(position).getDay());
//        Log.e("getDay",""+ deliverItems.get(position).getWeekDate());

        holder.deliv_date_txt.setText("" + deliverItems.get(position).getDay() + " " + deliverItems.get(position).getWeekDate());

        expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (procToCheckDelivSlotInter != null) {
                    procToCheckDelivSlotInter.proctoCheckdelivslotinterface(deliverItems.get(position));
                }
            }
        });


//        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT);
//            params.weight = 1;
//            params.width = 1000;
//
//            expandView.setLayoutParams(params);
//        }


        return expandView;
    }

}
