package com.app.oniontray.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OrderItem;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class OrderDetailAdapter extends BaseAdapter {

    private final Context context;
    private final LoginPrefManager loginPrefManager;

    private final LayoutInflater inflater;
    private final ArrayList<OrderItem> orderItems;

    private int products_sub_total_amt = 0;


    public OrderDetailAdapter(Context context, ArrayList<OrderItem> orderItems) {

        this.context = context;
        this.loginPrefManager = new LoginPrefManager(context);
        inflater = LayoutInflater.from(this.context);
        this.orderItems = orderItems;

    }

    @Override
    public int getCount() {
        return orderItems.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        private ImageView prod_image;
        private TextView prod_name, prod_qty, prod_price, order_det_row_unit_txt,
                order_det_product_ingredient;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/

            vi = inflater.inflate(R.layout.order_detail_row, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();

            holder.prod_image = (ImageView) vi.findViewById(R.id.order_sum_prod_image);
            holder.prod_name = (TextView) vi.findViewById(R.id.order_sum_prod_title);
            holder.order_det_row_unit_txt = (TextView) vi.findViewById(R.id.order_det_row_unit_txt);
            holder.prod_qty = (TextView) vi.findViewById(R.id.order_sum_prod_quan);
            holder.prod_price = (TextView) vi.findViewById(R.id.order_sum_prod_amt);
            holder.order_det_product_ingredient = (TextView) vi.findViewById(R.id.order_det_product_ingredient);

            /************  Set holder with LayoutInflater ************/

            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        Glide.with(context).load(orderItems.get(position).getProductImage()).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 5, 0)).error(R.color.app_background_color).into(holder.prod_image);

        holder.prod_name.setText(orderItems.get(position).getProductName());
        holder.order_det_row_unit_txt.setText("" + orderItems.get(position).getWeight() + orderItems.get(position).getUnitCode());
        holder.prod_qty.setText("" + orderItems.get(position).getItemUnit());


        if (orderItems.get(position).getIngredientDetail().size() != 0) {

            StringBuilder stringBuilder = new StringBuilder();

//            for (int i = 0; i < orderItems.get(position).getIngredientDetail().size(); i++) {
//                Log.e("value",""+i);

            for (int j = 0; j < orderItems.get(position).getIngredientDetail().size(); j++) {
                stringBuilder.append(orderItems.get(position).getIngredientDetail().get(j).getIngredientName());
                if (j != orderItems.get(position).getIngredientDetail().size())
                    stringBuilder.append(", ");
//                Log.e("value j", "" + j);
            }
//            }
            String str = stringBuilder.toString().trim();
            str = str.replaceAll(",$", "");
            holder.order_det_product_ingredient.setText(str.trim());
            holder.order_det_product_ingredient.setVisibility(View.VISIBLE);

        } else {
            holder.order_det_product_ingredient.setVisibility(View.GONE);
            holder.order_det_product_ingredient.setText("");
        }


        int price = 0;

        if (orderItems.get(position).getIngredientDetail().size() != 0) {

            int ingr_price = 0;

            for (int i = 0; i < orderItems.get(position).getIngredientDetail().size(); i++) {

//                ingr_price = (ingr_price + orderItems.get(position).getIngredientDetail().get(i).getIngredientPrice());

            }

            ingr_price = ((Integer.parseInt("" + orderItems.get(position).getItemUnit()) * ingr_price));

            price = (((Integer.parseInt("" + orderItems.get(position).getItemUnit()) * (Integer.parseInt("" + orderItems.get(position).getItemCost())))) + ingr_price);
        } else {
            price = ((Integer.parseInt("" + orderItems.get(position).getItemUnit()) * (Integer.parseInt("" + orderItems.get(position).getItemCost()))));
        }

//        Log.e("products_sub_total_amt", "-" + products_sub_total_amt);

        holder.prod_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues( (float) price)));


        return vi;
    }


    public int GetSubtotalAmount() {
        return products_sub_total_amt;
    }

}
