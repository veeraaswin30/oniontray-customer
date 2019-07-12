package com.app.oniontray.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.MobReturnReason;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.List;


public class CustomListAdapterDialog extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<MobReturnReason> mobReturnReasons;
    private LoginPrefManager preferenceAccess;
    private DDProgressBarDialog progress;

    public CustomListAdapterDialog(Context context, List<MobReturnReason> mobReturnReasons) {
        Context context1 = context;
        inflater = LayoutInflater.from(context1);
        this.mobReturnReasons = mobReturnReasons;
    }

    @Override
    public int getCount() {
        return mobReturnReasons.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.return_box_text, null);
            holder = new ViewHolder();
            holder.return_reason = (TextView) convertView.findViewById(R.id.return_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.return_reason.setText("" + mobReturnReasons.get(position).getName());


        return convertView;
    }

    static class ViewHolder {
        TextView return_reason;

    }

}


