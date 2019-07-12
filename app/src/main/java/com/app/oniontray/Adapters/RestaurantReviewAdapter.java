package com.app.oniontray.Adapters;

import android.content.Context;
import android.os.Build;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ReviewListArray;
import com.app.oniontray.Utils.LoginPrefManager;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantReviewAdapter extends RecyclerView.Adapter<RestaurantReviewAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private Context context;

    private List<ReviewListArray> reviewListArray;

    private LoginPrefManager loginPrefManager;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.ENGLISH);
    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH);

    public RestaurantReviewAdapter(Context context, List<ReviewListArray> reviewList) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.reviewListArray = reviewList;

        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_restaurant_review_row_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.restaurant_review_title.setText(reviewListArray.get(position).getName());
        holder.restaurant_review_description.setText(reviewListArray.get(position).getComments());

        if (!reviewListArray.get(position).getCreatedDate().equals("")) {

            Date start_date = null;
            try {

                if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                    old_date_format.setNumberFormat(nf);
                    new_date_format.setNumberFormat(nf);
                }

                start_date = old_date_format.parse("" + reviewListArray.get(position).getCreatedDate());
                String date = "" + new_date_format.format(start_date);
//                Log.e("Review date", "" + date);
//                holder.restaurant_review_date_and_name.setText( loginPrefManager.GetNuberstringFormatValue(date) + " | " + reviewListArray.get(position).getName());
                holder.restaurant_review_date_and_name.setText(date + " | " + reviewListArray.get(position).getName());
            } catch (ParseException e) {
//                Log.e("ParseException", "" + e.getMessage());
                e.printStackTrace();
            }

        }

        holder.restaurant_review_ratingBar.setRating(reviewListArray.get(position).getRatings());

//        Log.e("rating", "" + reviewListArray.get(position).getRatings());

    }

    @Override
    public int getItemCount() {
        return reviewListArray.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurant_review_description, restaurant_review_date_and_name, restaurant_review_title;
        private RatingBar restaurant_review_ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            restaurant_review_title = (TextView) itemView.findViewById(R.id.restaurant_review_title);
            restaurant_review_description = (TextView) itemView.findViewById(R.id.restaurant_review_description);
            restaurant_review_date_and_name = (TextView) itemView.findViewById(R.id.restaurant_review_date_and_name);

            restaurant_review_ratingBar = (RatingBar) itemView.findViewById(R.id.restaurant_review_ratingBar);
        }

    }
}
