package com.app.oniontray.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.DB.IngredientRepository;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;


public class MycartAdapter extends RecyclerView.Adapter<MycartAdapter.ProductViewHolder> {

    private final Context context;

    private ProductRespository productRespository;
    private IngredientRepository ingredientRepository;

    private final DDProgressBarDialog progressBarDialog;
    private final LoginPrefManager prefManager;

    private ArrayList<ProductList_Data> myCartListArrayList;

    private int quantity;
    private int price;


    public MycartAdapter(Context context, ArrayList<ProductList_Data> storeListdata) {
        this.context = context;
        this.myCartListArrayList = storeListdata;

        this.progressBarDialog = new DDProgressBarDialog(context);
        this.prefManager = new LoginPrefManager(context);

        this.productRespository = new ProductRespository();
        this.ingredientRepository = new IngredientRepository();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_restaurant_my_cart_row_item, viewGroup, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {

//        Glide.with(context).load(myCartListArrayList.get(position).left_my_cartgetProductImage()).centerCrop().into(((ProductViewHolder) holder).my_cart_prod_img);
        holder.restaurant_item_name.setText("" + myCartListArrayList.get(position).getproductName());

        holder.my_cart_decrease.setBackgroundResource(R.drawable.left_my_cart);
        holder.my_cart_decrease.setTextColor(Color.parseColor(prefManager.getThemeFontColor()));
        holder.my_cart_increase.setTextColor(Color.parseColor(prefManager.getThemeFontColor()));

        GradientDrawable gradientDrawable= (GradientDrawable)holder.my_cart_decrease.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
        gradientDrawable.setStroke(2, Color.parseColor(prefManager.getThemeFontColor()));


        holder.my_cart_increase.setBackgroundResource(R.drawable.right_my_cart);

        GradientDrawable gradientDrawable1= (GradientDrawable)holder.my_cart_increase.getBackground().getCurrent();
//gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
        gradientDrawable1.setStroke(2, Color.parseColor(prefManager.getThemeFontColor()));

        holder.my_cart_quantity.setBackgroundResource(R.drawable.center_my_cart);

        GradientDrawable gradientDrawable2= (GradientDrawable)holder.my_cart_quantity.getBackground().getCurrent();
         gradientDrawable2.setColor(Color.parseColor(prefManager.getThemeFontColor()));
        gradientDrawable2.setStroke(2, Color.parseColor(prefManager.getThemeFontColor()));

        Float cart_count= Float.parseFloat(String.valueOf(myCartListArrayList.get(position).getCartCount()));

        Float total_price = ( cart_count* Float.parseFloat(myCartListArrayList.get(position).getTotal()));
        holder.restaurant_item_total_price.setText(prefManager.getFormatCurrencyValueClosed(prefManager.GetEngDecimalFormatValues( (total_price))));

        holder.restaurant_item_total_price.setText(prefManager.getFormatCurrencyValueClosed(prefManager.GetEngDecimalFormatValues( (float) total_price)));
        holder.my_cart_quantity.setText("" + myCartListArrayList.get(position).getCartCount());
        holder.my_cart_quantity.setTextColor(Color.parseColor(prefManager.getThemeColor()));

        holder.restaurant_item_price.setText(prefManager.getFormatCurrencyValueClosed("" + myCartListArrayList.get(position).getTotal()));

        if (myCartListArrayList.get(position).getIngredTypeList().size() != 0) {

            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < myCartListArrayList.get(position).getIngredTypeList().size(); i++) {
//                Log.e("value",""+i);

                for (int j = 0; j < myCartListArrayList.get(position).getIngredTypeList().get(i).getIngredientList().size(); j++) {

                    stringBuilder.append(myCartListArrayList.get(position).getIngredTypeList().get(i).getIngredientList().get(j).getIngredientName());
                    if (j != myCartListArrayList.get(position).getIngredTypeList().get(i).getIngredientList().size())
                        stringBuilder.append(", ");
//                    Log.e("value j",""+j);
                }

            }

            String str = stringBuilder.toString().trim();
            str = str.replaceAll(",$", "");

//            if (str.endsWith(",")) {
//                str = str.substring(0, str.length() - 1);
//            }

            holder.restaurant_item_ingredient.setText(str);
            holder.restaurant_item_ingredient.setVisibility(View.VISIBLE);

        } else {
            holder.restaurant_item_ingredient.setVisibility(View.GONE);
            holder.restaurant_item_ingredient.setText("");
        }


        holder.my_cart_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = Integer.parseInt(holder.my_cart_quantity.getText().toString());
                IncreaseProductQuentty(count + 1, "" + myCartListArrayList.get(position).getPrimaryID());

            }

        });

        holder.my_cart_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = Integer.parseInt(holder.my_cart_quantity.getText().toString());

                DecreaseProductQuentty(count - 1, myCartListArrayList.get(position));

            }
        });


        holder.restaurant_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DecreaseProductQuentty(0, myCartListArrayList.get(position));
            }
        });

    }

    private void IncreaseProductQuentty(final int cart_count, String PrimaryID) {

        productRespository.updateBasedOnPrimaryID("" + PrimaryID);

        myCartListArrayList = productRespository.getCartProductList();

        notifyDataSetChanged();

        if (myCartsUpdateInterface != null) {
            myCartsUpdateInterface.UpdateCartsDetatils();
        }

    }

    private void DecreaseProductQuentty(final int cart_count, final ProductList_Data productList_data) {

        if (cart_count != 0) {
            productRespository.DecreaseCartBasedOnPrimaryID("" + productList_data.getPrimaryID());
        } else {
            productRespository.deleteBasedOnPrimaryID("" + productList_data.getPrimaryID());
            if (productList_data.getIngredTypeList().size() != 0) {
                ingredientRepository.deleteBasedOnPrimaryID("" + productList_data.getPrimaryID());
            }
        }

        myCartListArrayList = productRespository.getCartProductList();

        notifyDataSetChanged();

        if (myCartsUpdateInterface != null) {
            myCartsUpdateInterface.UpdateCartsDetatils();
        }

    }

    @Override
    public int getItemCount() {
        return myCartListArrayList.size();
    }


    public interface MyCartsUpdateInterface {
        void UpdateCartsDetatils();
    }

    private MyCartsUpdateInterface myCartsUpdateInterface;

    public void MyCartsinterfaceCallMethod(MyCartsUpdateInterface myCartsUpdateInterface) {
        this.myCartsUpdateInterface = myCartsUpdateInterface;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView restaurant_item_name, restaurant_item_ingredient, restaurant_item_price,
                restaurant_item_total_price, my_cart_quantity, my_cart_decrease, my_cart_increase;

        private ImageView restaurant_remove;

        public ProductViewHolder(View view) {
            super(view);

            restaurant_item_name = (TextView) view.findViewById(R.id.restaurant_item_name);
            restaurant_item_ingredient = (TextView) view.findViewById(R.id.restaurant_item_ingredient);
            restaurant_item_price = (TextView) view.findViewById(R.id.restaurant_item_price);
            restaurant_item_total_price = (TextView) view.findViewById(R.id.restaurant_item_total_price);
            my_cart_quantity = (TextView) view.findViewById(R.id.my_cart_quantity);
            my_cart_decrease = (TextView) view.findViewById(R.id.my_cart_decrease);
            my_cart_increase = (TextView) view.findViewById(R.id.my_cart_increase);

            restaurant_remove = (ImageView) view.findViewById(R.id.restaurant_remove);

        }
    }

}