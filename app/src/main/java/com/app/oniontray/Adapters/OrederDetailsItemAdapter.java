package com.app.oniontray.Adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OrderItem;
import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nextbrain on 13/4/17.
 */

public class OrederDetailsItemAdapter extends RecyclerView.Adapter<OrederDetailsItemAdapter.OrderItemViewHolder> {

    private final Context context;
    private final LoginPrefManager loginPrefManager;

    private final LayoutInflater inflater;
    private final ArrayList<OrderItem> orderItems;

    private int products_sub_total_amt = 0;


    public OrederDetailsItemAdapter(Context context, ArrayList<OrderItem> orderItems) {

        this.context = context;
        this.loginPrefManager = new LoginPrefManager(context);
        inflater = LayoutInflater.from(this.context);
        this.orderItems = orderItems;

    }

    public int getCount() {
        return orderItems.size();
    }

    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public OrderItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrederDetailsItemAdapter.OrderItemViewHolder(LayoutInflater.from(context).inflate(R.layout.order_detail_row, parent, false));
    }

    @Override
    public void onBindViewHolder(OrderItemViewHolder holder, int position) {

        Glide.with(context).load(orderItems.get(position).getProductImage()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.prod_image);

        holder.prod_name.setText(orderItems.get(position).getProductName().trim());
       // holder.prod_name.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        holder.order_det_row_unit_txt.setText("" + orderItems.get(position).getWeight() + orderItems.get(position).getUnitCode());
        holder.prod_qty.setText("" + orderItems.get(position).getItemUnit());


        if (orderItems.get(position).getIngredientDetail().size() != 0) {

            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < orderItems.get(position).getIngredientDetail().size(); j++) {
                stringBuilder.append(orderItems.get(position).getIngredientDetail().get(j).getIngredientName().trim());
                if (j != orderItems.get(position).getIngredientDetail().size())
                    stringBuilder.append(", ");
//                Log.e("value j", "" + j);
            }

            String str = stringBuilder.toString().trim();
            str = str.replaceAll(",$", "");
            holder.order_det_product_ingredient.setText(str.trim());
            holder.order_det_product_ingredient.setVisibility(View.VISIBLE);

        } else {
            holder.order_det_product_ingredient.setVisibility(View.GONE);
            holder.order_det_product_ingredient.setText("");
        }



        Float price = 0f;

        if (orderItems.get(position).getIngredientDetail().size() != 0) {

            Float ingr_price = 0f;

            for (int i = 0; i < orderItems.get(position).getIngredientDetail().size(); i++) {
                ingr_price = (ingr_price + Float.parseFloat( orderItems.get(position).getIngredientDetail().get(i).getIngredientPrice()));
            }

            ingr_price = ((Float.parseFloat("" + orderItems.get(position).getItemUnit()) * ingr_price));

            price = (((Float.parseFloat("" + orderItems.get(position).getItemUnit()) * (Float.parseFloat("" + orderItems.get(position).getItemCost())))) + ingr_price);
        } else {
            price = ((Float.parseFloat("" + orderItems.get(position).getItemUnit()) * (Float.parseFloat("" + orderItems.get(position).getItemCost()))));
        }


//        Log.e("products_sub_total_amt", "-" + products_sub_total_amt);

        holder.prod_price.setText(Html.fromHtml(loginPrefManager.getCurrecncyWithDynamicColor(loginPrefManager.getThemeFontColor(),loginPrefManager.GetEngDecimalFormatValues(  price))));


    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView prod_image;
        private TextView prod_name, prod_qty, prod_price,
                order_det_row_unit_txt,
                order_det_product_ingredient;

        public OrderItemViewHolder(View itemView) {
            super(itemView);

            prod_image = (ImageView) itemView.findViewById(R.id.order_sum_prod_image);
            prod_name = (TextView) itemView.findViewById(R.id.order_sum_prod_title);
            order_det_row_unit_txt = (TextView) itemView.findViewById(R.id.order_det_row_unit_txt);
            prod_qty = (TextView) itemView.findViewById(R.id.order_sum_prod_quan);
            prod_price = (TextView) itemView.findViewById(R.id.order_sum_prod_amt);
            order_det_product_ingredient = (TextView) itemView.findViewById(R.id.order_det_product_ingredient);

        }
    }


}
