package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.PromotionList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nextbrain on 6/4/17.
 */

public class PromotionsAdapter extends RecyclerView.Adapter<PromotionsAdapter.MyPromotionsHolder> {


    private Context context;
    private ArrayList<PromotionList> promotionLists;


    public PromotionsAdapter(Context context, ArrayList<PromotionList> promotionList) {

        this.context = context;
        this.promotionLists = promotionList;

    }

    @Override
    public MyPromotionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PromotionsAdapter.MyPromotionsHolder(LayoutInflater.from(context).inflate(R.layout.promotions_adapter_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MyPromotionsHolder holder, final int position) {

        try {

            Glide.with(context).load(promotionLists.get(position).getFeaturedImage()).fitCenter().bitmapTransform(new CenterCrop(context),
                    new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.promo_rest_img_view);

            holder.promo_rest_name_txt_view.setText(promotionLists.get(position).getOutletName());


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (myPromotionInterface != null) {
                        myPromotionInterface.MyPromotionItemClickEvent(promotionLists.get(position));
                    }

                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return promotionLists.size();
    }


    public class MyPromotionsHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ImageView promo_rest_img_view;
        private TextView promo_rest_name_txt_view;

        public MyPromotionsHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            this.promo_rest_img_view = (ImageView) itemView.findViewById(R.id.promo_rest_img_view);
            this.promo_rest_name_txt_view = (TextView) itemView.findViewById(R.id.promo_rest_name_txt_view);

        }

    }

    public MyPromotionInterface myPromotionInterface;

    public void MyPromotionsInterfaceCallMethod(MyPromotionInterface myPromotionInterface) {
        this.myPromotionInterface = myPromotionInterface;
    }

    public interface MyPromotionInterface {
        void MyPromotionItemClickEvent(PromotionList promotion);
    }


}
