package com.app.oniontray.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OpenTime;


public class StoInfoTakeWayView extends LinearLayout {

    private Context context;
    private View StoreInfoTakeWayView;

    private OpenTime openTime;

    private TextView sto_info_take_way_hint_txt_view;
    private TextView sto_info_take_way_value_txt_view;

    public StoInfoTakeWayView(Context context, OpenTime openTime) {
        super(context);
        this.context = context;
        this.openTime = openTime;

        init();
    }

    public StoInfoTakeWayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoInfoTakeWayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

        StoreInfoTakeWayView = inflate(getContext(), R.layout.sto_info_take_way_view_layout, this);

        sto_info_take_way_hint_txt_view = (TextView)StoreInfoTakeWayView.findViewById(R.id.sto_info_take_way_hint_txt_view);
        sto_info_take_way_value_txt_view = (TextView)StoreInfoTakeWayView.findViewById(R.id.sto_info_take_way_value_txt_view);


        sto_info_take_way_hint_txt_view.setText(""+openTime.getDay());
        sto_info_take_way_value_txt_view.setText(""+openTime.getStartTime()+" - "+openTime.getEndTime());

    }

}
