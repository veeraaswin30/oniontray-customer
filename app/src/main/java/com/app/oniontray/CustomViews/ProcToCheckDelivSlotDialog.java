package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.app.oniontray.Adapters.ProcToCheckDelivSlotDialogAdapter;
import com.app.oniontray.Fragments.DeliveryFragment;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ChoSlotDialogItemOffsetDecor;
import com.app.oniontray.RequestModels.ProcToCheckAvaliableSlotMob;
import com.app.oniontray.RequestModels.ProcToCheckTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProcToCheckDelivSlotDialog extends Dialog {

    private Context context;
    private ProcToCheckAvaliableSlotMob procToCheckAvaliableSlotMob;

    //    SimpleDateFormat old_date_format = new SimpleDateFormat("dd-MM-yyyy");  //27-11-2016
//    SimpleDateFormat new_date_format = new SimpleDateFormat("MMM dd,yyyy");

    private final SimpleDateFormat old_date_format = new SimpleDateFormat("dd-MM-yyyy");  //27-11-2016
    private final SimpleDateFormat sending_format = new SimpleDateFormat("yyyy-MM-dd");  //27-11-2016
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("MMM dd,yyyy");

    private ProcToCheckDelivSlotDialogAdapter procToCheckDelivSlotDialogAdapter;


    public ProcToCheckDelivSlotDialog(Context context, ProcToCheckAvaliableSlotMob procToCheckAvaliableSlotMob) {
        super(context);
        this.context = context;
        this.procToCheckAvaliableSlotMob = procToCheckAvaliableSlotMob;
    }

    public ProcToCheckDelivSlotDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProcToCheckDelivSlotDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.proc_to_check_deliv_slot_dialog);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.setCanceledOnTouchOutside(false);

        RecyclerView procTochecRecyclerView = (RecyclerView) findViewById(R.id.proc_to_che_deliv_dialog_recyc_view);
        procTochecRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));
        procTochecRecyclerView.addItemDecoration(new ChoSlotDialogItemOffsetDecor(context));

        procToCheckDelivSlotDialogAdapter = new ProcToCheckDelivSlotDialogAdapter(context, (ArrayList<ProcToCheckTime>) procToCheckAvaliableSlotMob.getTime());
        procTochecRecyclerView.setAdapter(procToCheckDelivSlotDialogAdapter);

        Button proc_to_che_cancel_btn = (Button) findViewById(R.id.proc_to_che_deliv_dialog_cancel_btn);
        proc_to_che_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button proc_to_che_ok_btn = (Button) findViewById(R.id.proc_to_che_deliv_dialog_done_btn);
        proc_to_che_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (procToCheckDelivSlotDialogAdapter.get_selected_slot_time().isEmpty()) {
                        Toast.makeText(context, ""+context.getString(R.string.proc_to_chec_sele_time_slot), Toast.LENGTH_SHORT).show();
                    } else {
//                        Date start_date = old_date_format.parse("" + procToCheckAvaliableSlotMob.getWeekDate());
//                        DeliveryFragment.setSelectedDatecallMethod("" + new_date_format.format(start_date) + " " + procToCheckDelivSlotDialogAdapter.get_selected_slot_time());
//                        dismiss();

                        Date start_date = old_date_format.parse("" + procToCheckAvaliableSlotMob.getWeekDate());

                        String selectedDate = sending_format.format(start_date);

                        DeliveryFragment.setSelectedDatecallMethod("" + new_date_format.format(start_date) + " " + procToCheckDelivSlotDialogAdapter.get_selected_slot_time(),
                                procToCheckDelivSlotDialogAdapter.get_selected_slot_time(), selectedDate, procToCheckDelivSlotDialogAdapter.getSlotID());

                        dismiss();

                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
