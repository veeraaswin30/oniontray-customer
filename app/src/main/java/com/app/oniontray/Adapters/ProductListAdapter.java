package com.app.oniontray.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Activites.RestaurantSignInSignUpActivity;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.Activites.ProductDetailActivity;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.AddToCart;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.RequestModels.SelectedIngredient;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("CanBeFinal")
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> implements ProductRespository.primaryIDCallback {

    private final LayoutInflater inflater;

    private Context context;

    private final DDProgressBarDialog progressBarDialog;
    private final LoginPrefManager loginPrefManager;

    private ProductRespository productRespository;

    private ArrayList<SelectedIngredient> chooseIngredientLists = new ArrayList<>();

    private List<ProductList_Data> product_list_item;
    private String vendor_id = "";
    private String outlet_id = "";
    private int quantity = 0;
    int pos;

    public ProductListAdapter(Context context, List<ProductList_Data> product_list_item, String vender_id, String outlet_id) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.product_list_item = product_list_item;

        this.vendor_id = vender_id;
        this.outlet_id = outlet_id;

        this.progressBarDialog = new DDProgressBarDialog(context);
        this.loginPrefManager = new LoginPrefManager(context);
        this.productRespository = new ProductRespository();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.restaurant_menu_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        pos = position;

        holder.product_name_text.setText(product_list_item.get(position).getproductName());
        holder.product_price_text.setText(""+loginPrefManager.getFormatCurrencyValue("" + product_list_item.get(position).getDiscountPrice()));

        if (product_list_item.get(position).getOpenRestaurant() == 0) {
            holder.product_add_btn.setVisibility(View.GONE);
        } else {
//            if(product_list_item.get(position).getCartCount() != 0 && product_list_item.get(position).getIngredTypeList().size() == 0)
//            {
//                holder.my_cart_quantity.setText(""+product_list_item.get(position).getCartCount());
//                holder.include_layout.setVisibility(View.VISIBLE);
                holder.product_add_btn.setVisibility(View.VISIBLE);
                holder.product_add_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

            holder.product_add_btn.setBackgroundResource(R.drawable.product_add_rectangle);

            GradientDrawable gradientDrawable= (GradientDrawable)holder.product_add_btn.getBackground().getCurrent();
            //gradientDrawable.setColor(Color.parseColor(loginPrefManager.getThemeColor()));
            gradientDrawable.setStroke(2,Color.parseColor(loginPrefManager.getThemeFontColor()));




//            }else
//            {
//                holder.include_layout.setVisibility(View.GONE);
//                holder.product_add_btn.setVisibility(View.VISIBLE);
//            }

        }


        holder.product_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if(product_list_item.get(position).getIngredTypeList().size() == 0)
//                {
//                    pos = position ;
//                    quantity = Integer.parseInt(holder.my_cart_quantity.getText().toString());
//                    AddProductIntoDB(product_list_item.get(position),quantity,Integer.parseInt(product_list_item.get(position).getDiscountPrice()),vendor_id,0,holder);
//                    productRespository.setListener(ProductListAdapter.this);
//                }else {
                    ProductDetailsIngridientMethod(product_list_item.get(position));

//                }


//                if (!loginPrefManager.getStringValue("user_id").isEmpty()) {
//                    int count = Integer.parseInt(holder.prod_list_quentity_text.getText().toString());
//                    AddToCartRequestMethod(position , ""+(count+1));
//                }else {
//                    LoginConfirmationDialog();
//                }

            }
        });

        if (product_list_item.get(position).getPreparationTime()==null||product_list_item.get(position).getPreparationTime().isEmpty()) {
            holder.product_prepare_time.setText(context.getString(R.string.preparation_time)+": "+"-");
        } else {
            //restaurant_delivery_mins.setText(Html.fromHtml(String.format(getString(R.string.mins), vendorDetail.getDeliveryTime())));
            holder.product_prepare_time.setText(context.getString(R.string.preparation_time)+": "+Html.fromHtml(String.format(context.getString(R.string.mins), product_list_item.get(position).getPreparationTime())));

        }

        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (product_list_item.get(position).getOpenRestaurant() != 0) {

                    ProductDetailsIngridientMethod(product_list_item.get(position));

                }

            }
        });

        int mPreviousPosition = position;

    }


    private void ProductDetailsIngridientMethod(ProductList_Data productList_data) {



            Intent product_detail_intent = new Intent(context, ProductDetailActivity.class);

            product_detail_intent.putExtra("Prod_details", productList_data);

            product_detail_intent.putExtra("product_id", "" + productList_data.getProductId());
            product_detail_intent.putExtra("product_name", "" + productList_data.getproductName());
            product_detail_intent.putExtra("outlet_name", "" + productList_data.getOutletName());
            product_detail_intent.putExtra("average_rating", "" + productList_data.getAverageRating());
            product_detail_intent.putExtra("original_price", "" + productList_data.getOriginalPrice());
            product_detail_intent.putExtra("discount_price", "" + productList_data.getDiscountPrice());
            product_detail_intent.putExtra("unit", "" + productList_data.getUnit());
            product_detail_intent.putExtra("product_url", "" + productList_data.getProductUrl());
            product_detail_intent.putExtra("description", "" + productList_data.getDescription());
            product_detail_intent.putExtra("product_image", productList_data.getProductImage());
            product_detail_intent.putExtra("Weight", productList_data.getWeight());
            product_detail_intent.putExtra("vendor_id", vendor_id);
            product_detail_intent.putExtra("outlet_id", outlet_id);
            product_detail_intent.putExtra("quantity", ""+quantity);
//        product_detail_intent.putExtra("prod_count", ""+holder.prod_list_quentity_text.getText().toString());
            product_detail_intent.putExtra("prod_count", "0");

           context.startActivity(product_detail_intent);
        }



    private void AddProductIntoDB(ProductList_Data productList_data, int quantity, String totalPrice, String vendor_id, int count, MyViewHolder holder) {

        if (productRespository.checkForTables()) {

            insertOrupdate(productList_data, chooseIngredientLists, vendor_id, totalPrice, count, holder);


        } else {

            quantity += 1;

            productList_data.setCartCount(1);

            productRespository.insert(productList_data, chooseIngredientLists, vendor_id, totalPrice, count);

            productList_data.setCartCount(quantity);
            holder.my_cart_quantity.setText(""+productList_data.getCartCount());
            if (quantity > 0) {
                holder.product_add_btn.setVisibility(View.GONE);
                holder.include_layout.setVisibility(View.VISIBLE);
            } else {
                holder.product_add_btn.setVisibility(View.VISIBLE);
                holder.include_layout.setVisibility(View.GONE);
            }

            if (productListInterface != null) {
                productListInterface.updateProdListandCartCount();
            }

        }

    }

    private void insertOrupdate(ProductList_Data productList_data, ArrayList<SelectedIngredient> chooseIngredientLists, String vendor_id, String totalPrice, int count, MyViewHolder holder) {
        if (productRespository.getOutLetID("" + productList_data.getOutletId())) {

            quantity += 1;

            productRespository.insertProductData(productList_data, 1, chooseIngredientLists, vendor_id, totalPrice, count);


            productList_data.setCartCount(quantity);
            holder.my_cart_quantity.setText(""+productList_data.getCartCount());

            if (quantity > 0) {
                holder.product_add_btn.setVisibility(View.GONE);
                holder.include_layout.setVisibility(View.VISIBLE);
            } else {
                holder.product_add_btn.setVisibility(View.VISIBLE);
                holder.include_layout.setVisibility(View.GONE);
            }

            if (productListInterface != null) {
                productListInterface.updateProdListandCartCount();
            }

        } else {
// Cart clear request method
            CartClearConfDialog(productList_data, chooseIngredientLists, vendor_id, totalPrice, holder);
        }
    }
    private void CartClearConfDialog(final ProductList_Data productList_data, final ArrayList<SelectedIngredient> chooseIngredientLists, final String vendor_id, final String totalPrice, final MyViewHolder holder) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + context.getString(R.string.prod_det_cart_conf_dialog_tit_txt));

        String message = String.format("" + context.getString(R.string.prod_det_cart_conf_dialog_msg_txt), "" + productRespository.getOutletName("" + vendor_id));
        alertDialog.setMessage(message);

        alertDialog.setNegativeButton("" + context.getString(R.string.prod_det_cart_conf_dialog_cancel_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alertDialog.setPositiveButton("" + context.getString(R.string.prod_det_cart_conf_dialog_new_ord_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        productRespository.ClearCart();

                        AddProductIntoDB(productList_data, quantity, totalPrice, vendor_id, 0, holder);

                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


    private void AddToCartRequestMethod(final int pos, String countValue) {

        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        APIService addtocart = Webdata.getRetrofit().create(APIService.class);
        addtocart.add_to_cart("" + loginPrefManager.getStringValue("Lang_code"), "" + vendor_id, "" + loginPrefManager.getStringValue("user_id"), "" + product_list_item.get(pos).getProductId(), "" + countValue, "" + product_list_item.get(pos).getOutletId(), "" + loginPrefManager.getStringValue("user_token")).enqueue(new Callback<AddToCart>() {
            @Override
            public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                try {
                    progressBarDialog.dismiss();
                    if (response.body().getResponse().getHttpCode() == 200) {
                        Log.e(" add to cart_response", response.body().getResponse().getMessage());
                        Toast.makeText(context, "" + response.body().getResponse().getMessage(), Toast.LENGTH_LONG).show();

                        if (productListInterface != null) {
                            productListInterface.updateProdListandCartCount();
                        }

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                    }
                } catch (Exception e) {
                    progressBarDialog.dismiss();
                    Log.e("Exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<AddToCart> call, Throwable t) {
                progressBarDialog.dismiss();
                Log.e("failure", t.toString());
            }
        });

    }

    private void LoginConfirmationDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + context.getString(R.string.message));

        alertDialog.setMessage("" + context.getString(R.string.home_login_access_it_txt));

        alertDialog.setNegativeButton("" + context.getString(R.string.cancel_btn_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("" + context.getString(R.string.login_btn_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent signInIntent = new Intent(context, RestaurantSignInSignUpActivity.class);
                        signInIntent.putExtra("login_type", "others");
                        context.startActivity(signInIntent);
                    }
                });
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return product_list_item.size();
    }

    public void searchFilter(List<ProductList_Data> filterdata) {
        product_list_item = new ArrayList<>();
        product_list_item.addAll(filterdata);
        notifyDataSetChanged();
    }

    @Override
    public void setPrimaryID(int primaryID) {

        product_list_item.get(pos).setPrimaryID(primaryID);
    }

    public void updateProductQuantity(String quantity, int productID, int primaryID) {

        for(int i= 0 ; i < product_list_item.size() ; i++)
        {
            if(product_list_item.get(i).getProductId() == productID )
            {
                product_list_item.get(i).setCartCount(Integer.parseInt(quantity));
                product_list_item.get(i).setPrimaryID(primaryID);
                notifyDataSetChanged();
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        final ImageView recycle_image;
//        final ImageView prod_list_plus_img_btn;
//        final ImageView prod_list_minus_img_btn;
//        final TextView recycle_title_text;
//        final TextView recycle_ml_text;
//        final TextView recycle_price_txt;
//        final TextView recycle_weight_txt;
//        final TextView recycle_del_price;
//        final TextView prod_list_quentity_text;

        final View rowView;

        private TextView product_name_text,product_prepare_time, product_price_text, my_cart_quantity, my_cart_decrease, my_cart_increase;

        private View include_layout;

        private Button product_add_btn;

        public MyViewHolder(View itemView) {
            super(itemView);

            rowView = itemView;

//            recycle_weight_txt = (TextView) itemView.findViewById(R.id.product_list_recycler_weight);
//            recycle_image = (ImageView) itemView.findViewById(R.id.product_list_recycler_view_img);
//            recycle_title_text = (TextView) itemView.findViewById(R.id.product_list_recycler_view_name);
//            recycle_ml_text = (TextView) itemView.findViewById(R.id.product_list_recycler_view_ml);
//            recycle_price_txt = (TextView) itemView.findViewById(R.id.product_list_recycler_view_price_);
//            recycle_del_price = (TextView) itemView.findViewById(R.id.product_list_recycler_view_price_deleted);
//
//
//            prod_list_minus_img_btn = (ImageView) itemView.findViewById(R.id.prod_list_item_decr_img_btn);
//            prod_list_quentity_text = (TextView) itemView.findViewById(R.id.prod_list_item_prod_quantity_txt);
//            prod_list_plus_img_btn = (ImageView) itemView.findViewById(R.id.prod_list_item_incre_img_btn);

            product_name_text = (TextView) rowView.findViewById(R.id.product_name_text);
            product_price_text = (TextView) rowView.findViewById(R.id.product_price_text);
            product_add_btn = (Button) rowView.findViewById(R.id.product_add_btn);
            product_prepare_time =  rowView.findViewById(R.id.product_prepare_time);

            my_cart_quantity = (TextView) rowView.findViewById(R.id.my_cart_quantity);
            my_cart_decrease = (TextView) rowView.findViewById(R.id.my_cart_decrease);
            my_cart_increase = (TextView) rowView.findViewById(R.id.my_cart_increase);

            include_layout = (View) rowView.findViewById(R.id.include_layout);

        }
    }


    public interface ProductListInterface {
        void updateProdListandCartCount();
    }

    private ProductListInterface productListInterface;

    public void ProdListInterface(ProductListInterface productListInterface) {
        this.productListInterface = productListInterface;
    }

}

