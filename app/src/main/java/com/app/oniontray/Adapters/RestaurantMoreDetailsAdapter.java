package com.app.oniontray.Adapters;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.R;

import java.util.List;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantMoreDetailsAdapter extends RecyclerView.Adapter<RestaurantMoreDetailsAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private Context context;
    private List<String> stringArray, vendorDetail;

    public RestaurantMoreDetailsAdapter(Context context, List<String> vendorDetail, List<String> stringArray) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.stringArray = stringArray;
        this.vendorDetail = vendorDetail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_restaurant_more_details_row_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.more_details_title.setText(stringArray.get(position));
        holder.more_details_title_content.setText(vendorDetail.get(position));

        if (position == 3) {
            if (vendorDetail.get(position).equals(context.getString(R.string.home_store_status_open_txt)))
                holder.more_details_title_content.setTextColor(Color.parseColor(context.getString(R.string.open_store_status_text_view_color)));
            else
                holder.more_details_title_content.setTextColor(Color.parseColor(context.getString(R.string.closse_store_text_view_color)));
        }
    }

    @Override
    public int getItemCount() {
        return stringArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView more_details_title, more_details_title_content;

        public MyViewHolder(View itemView) {
            super(itemView);

            more_details_title = (TextView) itemView.findViewById(R.id.more_details_title);
            more_details_title_content = (TextView) itemView.findViewById(R.id.more_details_title_content);
        }
    }
}
