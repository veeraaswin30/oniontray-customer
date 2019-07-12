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
 * Created by nextbrain on 24/5/17.
 */

public class DelivFragProductAdapter extends RecyclerView.Adapter<DelivFragProductAdapter.MyViewHolder> {


    private final Context context;

    private final LoginPrefManager loginPrefManager;

    private final ArrayList<ProductList_Data> deliverItems;


    public DelivFragProductAdapter(Context context, ArrayList<ProductList_Data> cartProductList) {
        this.context = context;
        this.loginPrefManager = new LoginPrefManager(context);
        this.deliverItems = cartProductList;
        LoginPrefManager prefManager = new LoginPrefManager(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DelivFragProductAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.deliver_detail_frag_row_item_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Glide.with(context).load("" + deliverItems.get(position).getProductImage()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.del_prod_image);
        holder.prod_name.setText("" + deliverItems.get(position).getproductName().trim());

        if (deliverItems.get(position).getIngredTypeList().size() != 0) {

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < deliverItems.get(position).getIngredTypeList().size(); i++) {
//                Log.e("value", "" + i);

                for (int j = 0; j < deliverItems.get(position).getIngredTypeList().get(i).getIngredientList().size(); j++) {
                    stringBuilder.append(deliverItems.get(position).getIngredTypeList().get(i).getIngredientList().get(j).getIngredientName());
                    if (j != deliverItems.get(position).getIngredTypeList().get(i).getIngredientList().size())
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

        int quantity = deliverItems.get(position).getCartCount();
        holder.prod_quant.setText("" + quantity);

//        Log.e("quantity", String.valueOf(quantity));
        // Log.e("quantity", String.valueOf(quantity));
        Float price = Float.parseFloat(deliverItems.get(position).getTotal());
        Float total_price = quantity * price;
        holder.prod_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues( (float) total_price)));
    }

    @Override
    public int getItemCount() {
        return deliverItems.size();
    }


    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView del_prod_image;
        private TextView prod_name, prod_price, prod_quant, prod_ingredient;

        public MyViewHolder(final View itemView) {
            super(itemView);

            del_prod_image = (ImageView) itemView.findViewById(R.id.del_prod_image);
            prod_name = (TextView) itemView.findViewById(R.id.del_product_name);
            prod_ingredient = (TextView) itemView.findViewById(R.id.del_product_ingredient);
            prod_price = (TextView) itemView.findViewById(R.id.del_product_total);
            prod_quant = (TextView) itemView.findViewById(R.id.del_product_quantity);

        }
    }


}
