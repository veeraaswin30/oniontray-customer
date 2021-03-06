package com.app.oniontray.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.Utils.LoginPrefManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class DeliveryAdapter extends BaseAdapter {

    private final Context context;
    private final LoginPrefManager loginPrefManager;
    private final LayoutInflater inflater;

    private final ArrayList<ProductList_Data> deliverItems;

    public DeliveryAdapter(Context context, ArrayList<ProductList_Data> cartProductList) {
        this.context = context;
        this.loginPrefManager = new LoginPrefManager(context);
        inflater = LayoutInflater.from(this.context);
        this.deliverItems = cartProductList;
        LoginPrefManager prefManager = new LoginPrefManager(context);

    }

    @Override
    public int getCount() {
        return deliverItems.size();
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
        private ImageView del_prod_image;
        private TextView prod_name, prod_price, prod_quant, prod_ingredient;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.deliver_detail_frag_row_item_lay, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.del_prod_image = (ImageView) vi.findViewById(R.id.del_prod_image);
            holder.prod_name = (TextView) vi.findViewById(R.id.del_product_name);
            holder.prod_ingredient = (TextView) vi.findViewById(R.id.del_product_ingredient);
            holder.prod_price = (TextView) vi.findViewById(R.id.del_product_total);
            holder.prod_quant = (TextView) vi.findViewById(R.id.del_product_quantity);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);

        } else {
            holder = (ViewHolder) vi.getTag();
        }

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
        return vi;
    }

}
