package com.app.oniontray.Activites;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.ProdDetailIngredientAdapter;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.RecyclerView.CityAreaLocItemOffsetDecor;
import com.app.oniontray.RequestModels.IngredTypeList;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.RequestModels.SelectedIngredient;
import com.bumptech.glide.Glide;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailActivity extends LocalizationActivity implements ProductRespository.primaryIDCallback {

    private TextView Input_quantity;

    private TextView product_detail_del_price, prod_detail_ingridient_total_txt_view, total_price;
    private TextView prod_details_deliv_by_txt, cart_update;

    private String vendor_id;
    private String outlet_id;
    private String product_id;
    private String product_name;
    private String Weight;
    private String outlet_name;
    private String average_rating;
    private String original_price;
    private String discount_price;
    private String unit;
    private String description;
    private String product_image;
    private String prod_count;

    private Button view_cart_items;


    private int quantity;
    private LayerDrawable icon;
    private TextView title;

    private LinearLayout cart_view;

    private LinearLayout view_cart;

    private ProductList_Data productList_data;

    private LinearLayout prod_detail_ingridient_layout;

    private RecyclerView prod_detail_ingridient_recycler_view;
    private ProdDetailIngredientAdapter prodDetailIngredientAdapter;

    private ProdIngredientBrodcoastReceiver prodIngredientBrodcoastReceiver;

    private List<IngredTypeList> ingredTypeList = new ArrayList<>();

    public ArrayList<SelectedIngredient> chooseIngredientLists = new ArrayList<SelectedIngredient>();

    private Float priceValue = 0f;

    private String itemQuantity;

    int count = 0;


    TextView product_detail_rating_text;
    TextView prod_detail_sto_name_txt;

    TextView prod_list_quentity_txt_view;
    ImageView pro_image;
    TextView pro_name;
    View decor_view;
    TextView pro_price;
    TextView prod_details_desc_data_txt;


//    VectorMasterView input_plus;
//    VectorMasterView input_minus;
    private Float product_price_to_float = 0f;

    private NestedScrollView prod_detail_nested_scroll_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar product_details_toolbar = (Toolbar) findViewById(R.id.product_details_toolbar);
        product_details_toolbar.setTitle("");
        product_details_toolbar.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        product_details_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        setSupportActionBar(product_details_toolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(ProductDetailActivity.this));


        if (language.equals("en")) {

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }

        product_details_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            productList_data = (ProductList_Data) getIntent().getSerializableExtra("Prod_details");

            ingredTypeList = productList_data.getIngredTypeList();

// productList_data = (ProductList_Data)bundle.getSerializable("Prod_details");

            vendor_id = bundle.getString("vendor_id");
            outlet_id = bundle.getString("outlet_id");
            product_id = bundle.getString("product_id");
            product_name = bundle.getString("product_name");
            outlet_name = bundle.getString("outlet_name");
            average_rating = bundle.getString("average_rating");
            original_price = bundle.getString("original_price");
            discount_price = bundle.getString("discount_price");
            unit = bundle.getString("unit");
            String product_url = bundle.getString("product_url");
            description = bundle.getString("description");
            product_image = bundle.getString("product_image");
            Weight = bundle.getString("Weight");
            prod_count = bundle.getString("prod_count");
            itemQuantity = bundle.getString("quantity");

        }

        product_price_to_float = Float.valueOf(discount_price);

//        VectorMasterView heartVector = (VectorMasterView) findViewById(R.id.dynamic_cart);
//
//// find the correct path using name
//        PathModel outline = heartVector.getPathModelByName("outline");
//
//// set the stroke color
//        outline.setStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));
//
//// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
//        outline.setFillColor(Color.parseColor(loginPrefManager.getThemeColor()));



        prod_detail_nested_scroll_view = (NestedScrollView) findViewById(R.id.prod_detail_nested_scroll_view);



        prod_details_deliv_by_txt = (TextView) findViewById(R.id.prod_details_deliv_by_txt);
        prod_detail_ingridient_total_txt_view = (TextView) findViewById(R.id.prod_detail_ingridient_total_txt_view);
        total_price = (TextView) findViewById(R.id.total_price);
        String deliverd_by_txt = String.format("%s <font color='%s'>%s</font>", "" + getString(R.string.my_cart_deliverd_by_txt), "" + getString(R.string.my_progress_loader_dot_color), "" + getString(R.string.app_name));
        prod_details_deliv_by_txt.setText(Html.fromHtml("" + deliverd_by_txt));

        prod_detail_sto_name_txt = (TextView) findViewById(R.id.prod_detail_sto_name_txt);
        product_detail_rating_text = (TextView) findViewById(R.id.product_detail_rating_text);
        cart_update = (TextView) findViewById(R.id.cart_update);

        decor_view = findViewById(R.id.decor_view);
        //decor_view.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        product_detail_del_price = (TextView) findViewById(R.id.product_detail_ignored_price);
        view_cart_items = (Button) findViewById(R.id.view_cart_btn);
        view_cart_items.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        view_cart_items.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        cart_view = (LinearLayout) findViewById(R.id.cart_view);
        cart_view.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        title = findViewById(R.id.title);
        title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        view_cart = findViewById(R.id.view_cart);
        view_cart.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

       // input_plus = findViewById(R.id.product_detail_plus_image);
        VectorMasterView input_plus = (VectorMasterView) findViewById(R.id.product_detail_plus_image);
        PathModel plus = input_plus.getPathModelByName("outline");

// set the stroke color
        plus.setStrokeColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
        plus.setFillColor(Color.parseColor(loginPrefManager.getThemeFontColor()));



        //input_minus =  findViewById(R.id.product_detail_minus_image);
        VectorMasterView input_minus = (VectorMasterView) findViewById(R.id.product_detail_minus_image);
        PathModel minus = input_minus.getPathModelByName("outline");

// set the stroke color
        minus.setStrokeColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
        minus.setFillColor(Color.parseColor(loginPrefManager.getThemeFontColor()));



        VectorMasterView dynamic_cart = (VectorMasterView) findViewById(R.id.dynamic_cart);
        PathModel outline = dynamic_cart.getPathModelByName("outline");

// set the stroke color
        outline.setStrokeColor(Color.parseColor(loginPrefManager.getThemeColor()));

// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
        outline.setFillColor(Color.parseColor(loginPrefManager.getThemeColor()));





        Input_quantity = (TextView) findViewById(R.id.product_detail_quantity);

        prod_list_quentity_txt_view = (TextView) findViewById(R.id.prod_list_quentity_txt_view);

        pro_image = (ImageView) findViewById(R.id.product_detail_view_pager_imageview);
        pro_name = (TextView) findViewById(R.id.product_detail_product_name);
        //pro_name.setTextColor(Color.parseColor(getResources().getColor(R.color.text_color));
        pro_price = (TextView) findViewById(R.id.product_detail_price);
        prod_details_desc_data_txt = (TextView) findViewById(R.id.prod_details_desc_data_txt);

        prod_detail_sto_name_txt.setText(outlet_name);

        product_detail_rating_text.setText("" + average_rating);

        pro_name.setText(product_name);

       // pro_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(discount_price))));

        pro_price.setText(Html.fromHtml(loginPrefManager.getCurrecncyWithDynamicColor(loginPrefManager.getThemeColor(),loginPrefManager.GetEngDecimalFormatValues( Float.valueOf(Float.valueOf(discount_price))))));

        prod_list_quentity_txt_view.setText("" + Weight + "" + unit);

        product_detail_del_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(original_price))));

        product_detail_del_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(this).load("" + product_image).centerCrop().error(R.color.app_background_color).into(pro_image);

        prod_details_desc_data_txt.setText("" + description);


        input_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = Integer.parseInt(Input_quantity.getText().toString());
                productRespository.setListener(ProductDetailActivity.this);

                ValidateAddToCartIngreMethod();

            }
        });
        input_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                quantity = Integer.parseInt(Input_quantity.getText().toString());
                if (quantity != 0) {
                    decreaseProductQuantity(productList_data.getPrimaryID());
                    setBadgeCount("" + productRespository.getCartCount());

                }

            }
        });

        view_cart_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ProductMenuItemListWithDynamicTabs.productActivity != null) {
                    ProductMenuItemListWithDynamicTabs.productActivity.finish();
                }

                Intent intent = new Intent(ProductDetailActivity.this, BaseMenuTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                Intent intent_rece = new Intent("base_activity_receiver");
                intent_rece.putExtra("page_name", "1");
                LocalBroadcastManager.getInstance(ProductDetailActivity.this).sendBroadcast(intent_rece);

                finish();

            }
        });

        prod_detail_ingridient_layout = (LinearLayout) findViewById(R.id.prod_detail_ingridient_layout);

        prod_detail_ingridient_recycler_view = (RecyclerView) findViewById(R.id.prod_detail_ingridient_recycler_view);
        prod_detail_ingridient_recycler_view.setLayoutManager(new LinearLayoutManager(ProductDetailActivity.this));
        prod_detail_ingridient_recycler_view.setHasFixedSize(true);
        prod_detail_ingridient_recycler_view.addItemDecoration(new CityAreaLocItemOffsetDecor(ProductDetailActivity.this, R.dimen.sele_city_area_loc_item_devi_height_size));

        if (productList_data.getIngredTypeList().size() == 0) {
            prod_detail_ingridient_layout.setVisibility(View.GONE);
        } else {
            CreateIngredientAdapter((ArrayList<IngredTypeList>) productList_data.getIngredTypeList());
        }

        prodIngredientBrodcoastReceiver = new ProdIngredientBrodcoastReceiver();
        LocalBroadcastManager.getInstance(ProductDetailActivity.this).registerReceiver(prodIngredientBrodcoastReceiver, new IntentFilter("ingredient_receiver"));


    }

    private void decreaseProductQuantity(int primaryID) {

        if (quantity != 0) {
            quantity--;
            productRespository.DecreaseCartBasedOnPrimaryID("" + primaryID);
            Input_quantity.setText(String.valueOf(quantity));
            float total = product_price_to_float + priceValue;
            total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(total)));
            total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) total)));
            prod_detail_ingridient_total_txt_view.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) priceValue)));
            if (productList_data.getIngredTypeList().size() != 0) {
                prodDetailIngredientAdapter.setQuantity(quantity);
            }
        }

    }

    private void AddProductIntoDB(ProductList_Data productList_data, int quantity, String totalPrice, String vendor_id, int count) {

        if (productRespository.checkForTables()) {

            insertOrupdate(productList_data, chooseIngredientLists, vendor_id, totalPrice, count);

        } else {

            quantity += 1;

            increamentSet(quantity);
            productList_data.setCartCount(1);

            productRespository.insert(productList_data, chooseIngredientLists, vendor_id, totalPrice, count);

// Toast.makeText(ProductDetailActivity.this, "" + getString(R.string.prod_det_cart_add_sucess_txt), Toast.LENGTH_SHORT).show();

            setBadgeCount("" + productRespository.getCartCount());
        }

    }

    private void insertOrupdate(ProductList_Data productList_data, ArrayList<SelectedIngredient> chooseIngredientLists, String vendor_id, String totalPrice, int count) {

        if (productRespository.getOutLetID("" + productList_data.getOutletId())) {

            quantity += 1;

            increamentSet(quantity);

            productRespository.insertProductData(productList_data, 1, chooseIngredientLists, vendor_id, totalPrice, count);

// Toast.makeText(ProductDetailActivity.this, "" + getString(R.string.prod_det_cart_add_sucess_txt), Toast.LENGTH_SHORT).show();

            setBadgeCount("" + productRespository.getCartCount());


        } else {
// Cart clear request method
            CartClearConfDialog(productList_data, chooseIngredientLists, vendor_id, totalPrice);
        }

    }

    private void increamentSet(int quantity) {

        Input_quantity.setText(String.valueOf(quantity));

        float quantity_convert = Float.parseFloat(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(""+quantity)));



        Log.e("product_price",""+product_price_to_float);
        Log.e("priceValue",""+priceValue);
        Log.e("quantity_convert",""+quantity_convert);


        float total = (product_price_to_float + priceValue);

        float overall_total=total*quantity_convert;

        total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(overall_total)));
        prod_detail_ingridient_total_txt_view.setText(loginPrefManager.getFormatCurrencyValue((loginPrefManager.GetEngDecimalFormatValues(priceValue))));

        if (quantity != 0 && productList_data.getIngredTypeList().size() != 0) {
            prodDetailIngredientAdapter.setQuantity(quantity);
        }

    }

    private void CreateIngredientAdapter(ArrayList<IngredTypeList> ingredTypeLists) {

        prodDetailIngredientAdapter = new ProdDetailIngredientAdapter(ProductDetailActivity.this, ingredTypeLists,loginPrefManager);
        prod_detail_ingridient_recycler_view.setAdapter(prodDetailIngredientAdapter);

// Log.e("getIngredTypeList", " - " + ingredTypeLists.size());

    }

    @Override
    public void onBackPressed() {

// To pass the quantity back to the previous activity
        if (productList_data.getIngredTypeList().size() == 0) {
            Intent intentQuantityUpdate = new Intent("quantity_update");
            intentQuantityUpdate.putExtra("quantity", "" + quantity);
            intentQuantityUpdate.putExtra("productID", "" + productList_data.getProductId());
            intentQuantityUpdate.putExtra("primaryID", "" + productList_data.getPrimaryID());
            LocalBroadcastManager.getInstance(ProductDetailActivity.this).sendBroadcast(intentQuantityUpdate);
        }

// Intent intent=new Intent();
// intent.putExtra("quantity",""+quantity);
// intent.putExtra("productID",""+productList_data.getProductId());
// setResult(2,intent);
// finish();//finishing activity

        finish();

    }


    @Override
    public void onResume() {
        super.onResume();
        setBadgeCount("" + productRespository.getCartCount());

    }

    private void CartClearConfDialog(final ProductList_Data productList_data, final ArrayList<SelectedIngredient> chooseIngredientLists, final String vendor_id, final String totalPrice) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductDetailActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        alertDialog.setTitle("" + getString(R.string.prod_det_cart_conf_dialog_tit_txt));

        String message = String.format("" + getString(R.string.prod_det_cart_conf_dialog_msg_txt), "" + productRespository.getOutletName("" + vendor_id));
        alertDialog.setMessage(message);

        alertDialog.setNegativeButton("" + getString(R.string.prod_det_cart_conf_dialog_cancel_txt), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alertDialog.setPositiveButton("" + getString(R.string.prod_det_cart_conf_dialog_new_ord_txt),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        productRespository.ClearCart();

                        AddProductIntoDB(productList_data, quantity, totalPrice, vendor_id, count);

                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void setBadgeCount(String cart_value) {

        if (Integer.parseInt(cart_value) == 0) {
            cart_view.setVisibility(View.GONE);

        } else {
            cart_view.setVisibility(View.VISIBLE);
            String text = String.format(getResources().getString(R.string.cart_count_update), "" + cart_value, loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((Float.parseFloat(productRespository.totalPrice())))));
            cart_update.setText(Html.fromHtml(text));
            cart_update.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        }

    }

    @Override
    public void setPrimaryID(int primaryID) {
        productList_data.setPrimaryID(primaryID);
    }


    public class ProdIngredientBrodcoastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            priceValue = 0f;
            float quantity_convert = Float.parseFloat("" + quantity);

            if (intent != null) {

                if (intent.hasExtra("array_list")) {

                    SelectedIngredient selectedIngredient = (SelectedIngredient) intent.getSerializableExtra("array_list");

                    if (chooseIngredientLists.size() == 0) {

                        chooseIngredientLists.add(selectedIngredient);

                        UpdateSeletedIngredType(selectedIngredient);

                        UpdateIngredTypeList();

                        for (int j = 0; j < chooseIngredientLists.size(); j++) {
                            ArrayList<IngredientList> selectedIngredient1 = chooseIngredientLists.get(j).getIngredientLists();
                            for (int i = 0; i < selectedIngredient1.size(); i++) {
                                priceValue += Float.parseFloat(selectedIngredient1.get(i).getPrice());
                            }
                        }

                        float total = product_price_to_float + priceValue;

                        total = total * quantity_convert;

                        total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(total)));
                        prod_detail_ingridient_total_txt_view.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(priceValue)));


                    } else {

                        for (int i = 0; i < chooseIngredientLists.size(); i++) {
                            if (chooseIngredientLists.get(i).getIngredientTypeId() == selectedIngredient.getIngredientTypeId()) {
                                chooseIngredientLists.remove(i);
                            }
                        }


                        chooseIngredientLists.add(selectedIngredient);

// if(selectedIngredient.getIngredientLists().size() != 0){
// }

                        UpdateSeletedIngredType(selectedIngredient);

                        UpdateIngredTypeList();


                        for (int j = 0; j < chooseIngredientLists.size(); j++) {
                            ArrayList<IngredientList> selectedIngredient1 = chooseIngredientLists.get(j).getIngredientLists();
                            for (int i = 0; i < selectedIngredient1.size(); i++) {
                                priceValue += Float.parseFloat(selectedIngredient1.get(i).getPrice());
                            }
                        }


                        Float total = product_price_to_float + priceValue;
                        if (quantity != 0)
                            total = total * quantity_convert;


                        total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(total)));
                        prod_detail_ingridient_total_txt_view.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(priceValue)));

                    }

                }

            }

        }
    }

    private void UpdateIngredTypeList() {
        if (prodDetailIngredientAdapter != null) {
            prodDetailIngredientAdapter.UpdateIngredientValitationSucess(chooseIngredientLists);
        }
    }

    private void UpdateSeletedIngredType(SelectedIngredient selectedIngredient) {

        for (int i = 0; i < ingredTypeList.size(); i++) {

            if (ingredTypeList.get(i).getIngredientTypeId() == selectedIngredient.getIngredientTypeId()) {
                IngredTypeList ingredTypeListss = ingredTypeList.get(i);
                ingredTypeListss.setBooleanValidation(true);
                ingredTypeList.set(i, ingredTypeListss);
            }

        }

    }

    private void ValidateAddToCartIngreMethod() {

        float price = 0f;
        float totalPrice;
        count = 0;
        Float quantity_convert = Float.parseFloat("" + quantity);


        if (ingredTypeList.size() != 0) {

            for (int i = 0; i < ingredTypeList.size(); i++) {
                if (!ingredTypeList.get(i).getBooleanValidation()) {

                    if (ingredTypeList.get(i).getRequired() == 1) {
                        Toast.makeText(ProductDetailActivity.this, "" + getString(R.string.produ_deta_select_all_reqired_fields_txt), Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }

            for (int i = 0; i < chooseIngredientLists.size(); i++) {
                count += chooseIngredientLists.get(i).getIngredientLists().size();
                for (int j = 0; j < chooseIngredientLists.get(i).getIngredientLists().size(); j++) {
                    price += Float.parseFloat(chooseIngredientLists.get(i).getIngredientLists().get(j).getPrice());
                }
            }

            prod_detail_ingridient_total_txt_view.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues((float) price)));
        }

        totalPrice = (product_price_to_float + price);

        total_price.setText(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(totalPrice * quantity_convert)));

        AddProductIntoDB(productList_data, quantity,  ""+loginPrefManager.GetEngDecimalFormatValues(totalPrice), vendor_id, count);


    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(ProductDetailActivity.this).unregisterReceiver(prodIngredientBrodcoastReceiver);
    }
}