package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by nextbrain on 24/4/17.
 */

public class OrdConfiRecyadapter extends RecyclerView.Adapter<OrdConfiRecyadapter.OrdConfViewHolder> {

    private Context context;
    private ArrayList<ProductList_Data> orderItems;
    private LoginPrefManager loginPrefManager;

    public OrdConfiRecyadapter(Context context, ArrayList<ProductList_Data> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
        this.loginPrefManager = new LoginPrefManager(context);
    }

    @Override
    public OrdConfViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrdConfiRecyadapter.OrdConfViewHolder(LayoutInflater.from(context).inflate(R.layout.order_confirmation_row_item_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(OrdConfViewHolder holder, int position) {

        holder.prod_name.setText(orderItems.get(position).getproductName());

        Glide.with(context).load("" + orderItems.get(position).getProductImage()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.prod_image);

        holder.prod_qty.setText("" + orderItems.get(position).getCartCount());


        if (orderItems.get(position).getIngredTypeList().size() != 0) {

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < orderItems.get(position).getIngredTypeList().size(); i++) {

//                Log.e("value", "" + i);

                for (int j = 0; j < orderItems.get(position).getIngredTypeList().get(i).getIngredientList().size(); j++) {
                    stringBuilder.append(orderItems.get(position).getIngredTypeList().get(i).getIngredientList().get(j).getIngredientName());
                    if (j != orderItems.get(position).getIngredTypeList().get(i).getIngredientList().size())
                        stringBuilder.append(", ");
//                    Log.e("value j", "" + j);
                }
            }

            String str = stringBuilder.toString().trim();
            str = str.replaceAll(",$", "");
            holder.prod_ingredient.setText(str.trim());
            holder.prod_ingredient.setVisibility(View.VISIBLE);

        } else {
            holder.prod_ingredient.setVisibility(View.GONE);
            holder.prod_ingredient.setText("");
        }


        int quantity = orderItems.get(position).getCartCount();
//        Log.e("quantity", String.valueOf(quantity));
        // Log.e("quantity", String.valueOf(quantity));
        Float price = Float.parseFloat(orderItems.get(position).getTotal());
        Float total_price = quantity * price;
        holder.prod_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues( (float) total_price)));
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrdConfViewHolder extends RecyclerView.ViewHolder {

        private ImageView prod_image;
        private TextView prod_name;
        private TextView prod_qty;
        private TextView prod_price;
        private TextView prod_ingredient;

        public OrdConfViewHolder(View itemView) {
            super(itemView);

            prod_image = (ImageView) itemView.findViewById(R.id.order_conf_prod_image);
            prod_name = (TextView) itemView.findViewById(R.id.order_conf_prod_title);
            prod_ingredient = (TextView) itemView.findViewById(R.id.del_product_ingredient);
            prod_qty = (TextView) itemView.findViewById(R.id.order_conf_prod_quan);
            prod_price = (TextView) itemView.findViewById(R.id.order_conf_prod_amt);

        }
    }


}
