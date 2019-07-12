package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.PromotionProduct;
import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nextbrain on 6/4/17.
 */

public class Promo_Product_Adapter extends RecyclerView.Adapter<Promo_Product_Adapter.Promo_productViewHolder> {


    private Context context;
    private ArrayList<PromotionProduct> promotionProductArrayList;

    private LoginPrefManager loginPrefManager;


    public Promo_Product_Adapter(Context context, ArrayList<PromotionProduct> promotionProductArrayList) {
        this.context = context;
        this.promotionProductArrayList = promotionProductArrayList;

        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public Promo_productViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Promo_Product_Adapter.Promo_productViewHolder(LayoutInflater.from(context).inflate(R.layout.promo_product_adapter_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(Promo_productViewHolder holder, final int position) {

        try {

            Glide.with(context).load(promotionProductArrayList.get(position).getProductImage()).fitCenter().bitmapTransform(new CenterCrop(context),
                    new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.promo_img_view);

            holder.promo_name_txt_view.setText(promotionProductArrayList.get(position).getProductName());

            if(!promotionProductArrayList.get(position).getDiscountPrice().isEmpty()){
                holder.promo_price_name_txt_view.setText(loginPrefManager.getFormatCurrencyValue("" + promotionProductArrayList.get(position).getDiscountPrice()));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (myPromoProdAdapterInterface != null) {
                        myPromoProdAdapterInterface.PromoProdAdapterItemClickEvent(promotionProductArrayList.get(position));
                    }

                }
            });

        } catch (Exception e) {
//            Log.e("onBindViewHolder", "Exception" + e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return promotionProductArrayList.size();
    }

    public class Promo_productViewHolder extends RecyclerView.ViewHolder {

        private ImageView promo_img_view;
        private TextView promo_name_txt_view;
        private TextView promo_price_name_txt_view;

        public Promo_productViewHolder(View itemView) {
            super(itemView);

            this.promo_img_view = (ImageView) itemView.findViewById(R.id.promo_img_view);
            this.promo_name_txt_view = (TextView) itemView.findViewById(R.id.promo_name_txt_view);
            this.promo_price_name_txt_view = (TextView) itemView.findViewById(R.id.promo_price_name_txt_view);

        }
    }


    public MyPromoProdAdapterInterface myPromoProdAdapterInterface;

    public void MyProProdAdapterInterfaceReqMethod(MyPromoProdAdapterInterface myPromoProdAdapterInterface) {
        this.myPromoProdAdapterInterface = myPromoProdAdapterInterface;
    }

    public interface MyPromoProdAdapterInterface {
        void PromoProdAdapterItemClickEvent(PromotionProduct promotionProduct);
    }


}
