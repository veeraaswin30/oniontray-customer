package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.oniontray.Activites.ProductMenuItemListWithDynamicTabs;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.StoreList_Data;
import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.app.oniontray.AppControler.onionTray.getContext;

/**
 * Created by NEXTBRAIN on 3/4/2017.
 */

public class HomeSearchItemViewAdapter extends RecyclerView.Adapter<HomeSearchItemViewAdapter.HomeSearchViewHolder> {

    private Context context;
    private ArrayList<StoreList_Data> storeList_datas;
    private LoginPrefManager loginPrefManager;
    private  LayerDrawable stars;

    public HomeSearchItemViewAdapter(Context context, ArrayList<StoreList_Data> storeList_datas) {
        this.context = context;
        this.storeList_datas = storeList_datas;
        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public HomeSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeSearchItemViewAdapter.HomeSearchViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_restaurant_menu_listing, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeSearchViewHolder holder, final int position) {

        Glide.with(context).load(storeList_datas.get(position).getFeaturedImage()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 0, 0)).error(R.color.app_background_color).into(holder.restaurant_image);

        holder.restaurant_name.setText(storeList_datas.get(position).getOutletsName());

        holder.restaurant_rating.setText("" + loginPrefManager.getDecimalRattingValue(storeList_datas.get(position).getAverageRating()));

        if (storeList_datas.get(position).getOpenRestaurant() == 0) {

            if (storeList_datas.get(position).getPromotion() == 0) {
                holder.restaurant_promo_offer_lab_img_view.setVisibility(View.GONE);
            } else
                {
                holder.restaurant_promo_offer_lab_img_view.setVisibility(View.VISIBLE);
            }

            if (storeList_datas.get(position).getAverageRating() != null && !storeList_datas.get(position).getAverageRating().equals("")) {
                if (!storeList_datas.get(position).getAverageRating().equals("")) {
                    holder.close_restaurant_ratingBar.setRating(Float.parseFloat("" + storeList_datas.get(position).getAverageRating()));

                    LayerDrawable starss = (LayerDrawable) holder.close_restaurant_ratingBar.getProgressDrawable();

                    // Filled stars
                    setRatingStarColor(starss.getDrawable(2), ContextCompat.getColor(getContext(),Color.parseColor(loginPrefManager.getThemeColor())));
                    // Half filled stars
                    setRatingStarColor(starss.getDrawable(1), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));
                    // Empty stars
                    setRatingStarColor(starss.getDrawable(0), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));
                }
            }

            holder.restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(storeList_datas.get(position).getMinimumOrderAmount()))));

            if (storeList_datas.get(position).getDeliveryTime().isEmpty()) {
                holder.restaurant_delivery_mins.setText("-");
            } else
                {
                holder.restaurant_delivery_mins.setText("" + Html.fromHtml(String.format(context.getString(R.string.rest_list_mins), "" + storeList_datas.get(position).getDeliveryTime())));
            }

            holder.restaurant_open_status.setText("" + context.getString(R.string.home_store_status_close_txt));

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            holder.restaurant_promo_offer_lab_img_view.setColorFilter(filter);

            holder.restaurant_image.setColorFilter(filter);
//            holder.delivery_img_view.setColorFilter(filter);

            holder.restaurant_name.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));
            holder.restaurant_open_status.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));

            holder.restaurant_rating.setBackgroundResource(R.drawable.rating_drawable_grey);
            holder.restaurant_minimum_order_amout.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));
            holder.restaurant_delivery_mins.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));

            holder.restaurant_delivery_type.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));

            holder.close_restaurant_ratingBar.setVisibility(View.VISIBLE);
            holder.restaurant_ratingBar.setVisibility(View.GONE);

        } else {

            if (storeList_datas.get(position).getPromotion() == 0) {
                holder.restaurant_promo_offer_lab_img_view.setVisibility(View.GONE);
            } else {
                holder.restaurant_promo_offer_lab_img_view.setVisibility(View.VISIBLE);
            }

            if (storeList_datas.get(position).getAverageRating() != null && !storeList_datas.get(position).getAverageRating().equals("")) {
                if (!storeList_datas.get(position).getAverageRating().equals("")) {
                    holder.restaurant_ratingBar.setRating(Float.parseFloat("" + storeList_datas.get(position).getAverageRating()));

//                    LayerDrawable starsss = (LayerDrawable) holder.restaurant_ratingBar.getProgressDrawable();

                    // Filled stars
//                    setRatingStarColor(starsss.getDrawable(2), ContextCompat.getColor(getContext(),Color.parseColor(loginPrefManager.getThemeColor())));
                    // Half filled stars
//                    setRatingStarColor(starsss.getDrawable(1), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));
                    // Empty stars
//                    setRatingStarColor(starsss.getDrawable(0), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));
//                    stars = (LayerDrawable) holder.restaurant_ratingBar.getProgressDrawable();
//                    stars.getDrawable(5).setColorFilter(Color.parseColor(loginPrefManager.getThemeColor()), PorterDuff.Mode.SRC_ATOP);
                }
            }

            holder.restaurant_minimum_order_amout.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(storeList_datas.get(position).getMinimumOrderAmount()))));

            if (storeList_datas.get(position).getDeliveryTime().isEmpty()) {
                holder.restaurant_delivery_mins.setText("-");
            } else {
                holder.restaurant_delivery_mins.setText(Html.fromHtml(String.format(context.getString(R.string.rest_list_mins), "" + storeList_datas.get(position).getDeliveryTime())));
            }

            holder.restaurant_open_status.setText("" + context.getString(R.string.home_store_status_open_txt));

            holder.restaurant_promo_offer_lab_img_view.setColorFilter(null);

            holder.restaurant_image.setColorFilter(null);
//            holder.delivery_img_view.setColorFilter(null);

            holder.restaurant_name.setTextColor(Color.parseColor("" + context.getString(R.string.open_store_name_text_view_color)));
            holder.restaurant_open_status.setTextColor(Color.parseColor("" + context.getString(R.string.open_store_status_text_view_color)));

            holder.restaurant_rating.setBackgroundResource(R.drawable.rating_drawable_green);

            holder.restaurant_minimum_order_amout.setTextColor(Color.parseColor("" + context.getString(R.string.open_store_min_amt_text_view_color)));
            holder.restaurant_delivery_mins.setTextColor(Color.parseColor("" + context.getString(R.string.open_store_min_amt_text_view_color)));

            holder.restaurant_delivery_type.setTextColor(Color.parseColor("" + context.getString(R.string.closse_store_text_view_color)));

            holder.close_restaurant_ratingBar.setVisibility(View.GONE);
            holder.restaurant_ratingBar.setVisibility(View.VISIBLE);

//            LayerDrawable starssss = (LayerDrawable) holder.restaurant_ratingBar.getProgressDrawable();

            // Filled stars
//            setRatingStarColor(starssss.getDrawable(2), ContextCompat.getColor(getContext(),Color.parseColor(loginPrefManager.getThemeColor())));
            // Half filled stars
//            setRatingStarColor(starssss.getDrawable(1), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));
            // Empty stars
//            setRatingStarColor(starssss.getDrawable(0), ContextCompat.getColor(getContext(), Color.parseColor(loginPrefManager.getThemeColor())));


        }


        if (storeList_datas.get(position).getDeliveryType() == 1) {
            holder.restaurant_delivery_type.setText(context.getString(R.string.store_listfree_txt));
        } else if (storeList_datas.get(position).getDeliveryType() == 2)
        {
            if (String.valueOf(Float.valueOf(storeList_datas.get(position).getDeliveryCostFixed())).equals("0.0")) {
                holder.restaurant_delivery_type.setText(context.getString(R.string.store_listfree_txt));
            } else {
                holder.restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(storeList_datas.get(position).getDeliveryCostFixed()))));
            }
        } else if (storeList_datas.get(position).getDeliveryType() == 3)
        {
            if (String.valueOf(Float.valueOf(storeList_datas.get(position).getDeliveryCostFixed())).equals("0.0")) {
                holder.restaurant_delivery_type.setText(context.getString(R.string.store_listfree_txt));
            } else {
                holder.restaurant_delivery_type.setText(loginPrefManager.getFormatCurrencyValueClosed(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(storeList_datas.get(position).getDeliveryCostFixed()))));
            }
        }


        holder.Item_Row_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_rece = new Intent("dynamic_tabs");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent_rece);

                Intent out_let = new Intent(context, ProductMenuItemListWithDynamicTabs.class);
                out_let.putExtra("vender_name", "" + storeList_datas.get(position).getOutletsName());
                out_let.putExtra("backOption", true);
                out_let.putExtra("outlet_id", "" + storeList_datas.get(position).getOutletsId());
                out_let.putExtra("vender_id", "" + storeList_datas.get(position).getVendorsId());
                context.startActivity(out_let);

            }
        });

    }

    @Override
    public int getItemCount() {
        return storeList_datas.size();
    }

    public void SetFilterSearchArrayItems(ArrayList<StoreList_Data> storeList_datas) {
        this.storeList_datas = storeList_datas;
        notifyDataSetChanged();
    }


    public class HomeSearchViewHolder extends RecyclerView.ViewHolder {

        public View Item_Row_View;

        public final ImageView restaurant_image;
        public final ImageView restaurant_promo_offer_lab_img_view;
        public final TextView restaurant_name;

        public final TextView restaurant_open_status;

        public final RatingBar restaurant_ratingBar;
        public final RatingBar close_restaurant_ratingBar;

        public final TextView restaurant_rating;

        public final TextView restaurant_minimum_order_amout;
        public final TextView restaurant_delivery_mins;

        public final TextView restaurant_delivery_type;

        public final TextView delivery_img_view;

        public HomeSearchViewHolder(View itemView) {
            super(itemView);

            Item_Row_View = itemView;
            restaurant_image = (ImageView) itemView.findViewById(R.id.restaurant_image);

            restaurant_promo_offer_lab_img_view = (ImageView) itemView.findViewById(R.id.restaurant_promo_offer_lab_img_view);
            restaurant_name = (TextView) itemView.findViewById(R.id.restaurant_name);

            restaurant_open_status = (TextView) itemView.findViewById(R.id.restaurant_open_status);

            restaurant_ratingBar = (RatingBar) itemView.findViewById(R.id.restaurant_ratingBar);
            close_restaurant_ratingBar = (RatingBar) itemView.findViewById(R.id.close_restaurant_ratingBar);

            restaurant_rating = (TextView) itemView.findViewById(R.id.restaurant_rating);

            restaurant_minimum_order_amout = (TextView) itemView.findViewById(R.id.restaurant_minimum_order_amout);
            restaurant_delivery_mins = (TextView) itemView.findViewById(R.id.restaurant_delivery_mins);

            restaurant_delivery_type = (TextView) itemView.findViewById(R.id.restaurant_delivery_type);

            delivery_img_view = (TextView) itemView.findViewById(R.id.delivery_img_view);

        }
    }


    private void setRatingStarColor(Drawable drawable, @ColorInt int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            DrawableCompat.setTint(drawable, color);
        }
        else
        {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

}
