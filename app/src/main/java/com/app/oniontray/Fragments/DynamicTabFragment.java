package com.app.oniontray.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.app.oniontray.Activites.BaseMenuTabActivity;
import com.app.oniontray.Adapters.ProductListAdapter;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.CategoryList;
import com.app.oniontray.RequestModels.ProductList;
import com.app.oniontray.RequestModels.ProductList_Data;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DynamicTabFragment extends Fragment implements ProductListAdapter.ProductListInterface {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private int sectionNumber;
    private RecyclerView recyclerView;

    private CategoryList categoryList;
    private Toolbar prod_list_items_toolbar;

    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog ddProgressBarDialog;
    private ProductListAdapter prodListApater;
    private ArrayList<ProductList_Data> product_list_items;
    private TextView menu_item_empty_text, cart_update;
    private Button view_cart_items;
    private TextView title;

    private String vender_id = "";
    private String outlet_id = "";

    private ProductRespository productRespository;
    private LinearLayout cart_view;

    public DynamicTabFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dynamic_fragment, container, false);

        Bundle bundle = this.getArguments();
        productRespository = new ProductRespository();
        if (bundle != null) {
            categoryList = (CategoryList) bundle.getSerializable("categoryList");

            vender_id = bundle.getString("vendor_id");
            outlet_id = bundle.getString("outlet_id");
        }

        loginPrefManager = new LoginPrefManager(getContext());
        ddProgressBarDialog = Webdata.getProgressBarDialog(getContext());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.menuItemListUnderCategory);
        menu_item_empty_text = (TextView) rootView.findViewById(R.id.menu_item_empty_text);

        cart_view = (LinearLayout) rootView.findViewById(R.id.cart_view);
        cart_view.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));


        VectorMasterView heartVector = (VectorMasterView) rootView.findViewById(R.id.dynamic_cart);

        title = rootView.findViewById(R.id.title);
        title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

// find the correct path using name
        PathModel outline = heartVector.getPathModelByName("outline");

// set the stroke color
        outline.setStrokeColor(Color.parseColor(loginPrefManager.getThemeFontColor()));

// set the fill color (if fill color is not set or is TRANSPARENT, then no fill is drawn)
        outline.setFillColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        cart_update = (TextView) rootView.findViewById(R.id.cart_update);
        cart_update.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
        title = rootView.findViewById(R.id.title);
        title.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        prod_list_items_toolbar = rootView.findViewById(R.id.prod_list_items_toolbar);
        prod_list_items_toolbar.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));

        view_cart_items = (Button) rootView.findViewById(R.id.view_cart_btn);
        view_cart_items.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        view_cart_items.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new ProdListItemOffsetDecor(getContext(), R.dimen.prod_list_item_row_line_height));

        getProductListRequest();

        view_cart_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BaseMenuTabActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

                Intent intent_rece = new Intent("base_activity_receiver");
                intent_rece.putExtra("page_name", "1");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent_rece);

                getActivity().finish();
            }
        });
        return rootView;
    }

    private void getProductListRequest() {

        if (ddProgressBarDialog != null) {
            ddProgressBarDialog.show();
        }

        try {

//            Log.e("language", "" + loginPrefManager.getStringValue("Lang_code"));
//            Log.e("user_id", "" + loginPrefManager.getStringValue("user_id"));
//            Log.e("token", "" + loginPrefManager.getStringValue("user_token"));
//            Log.e("store_id", "" + vender_id);
////            Log.e("category_id", "" + main_Categories_id);
//            Log.e("getCategoryId", "" + categoryList.getCategoryId());
//            Log.e("outlet_id", "" + outlet_id);

            APIService product_list = Webdata.getRetrofit().create(APIService.class);
            product_list.get_store_product_list("" + loginPrefManager.getStringValue("Lang_code"), "" + loginPrefManager.getStringValue("user_id"),
                    "" + loginPrefManager.getStringValue("user_token"), "" + vender_id, "" + categoryList.getCategoryId(), "" + outlet_id, "").enqueue(new Callback<ProductList>() {
                @Override
                public void onResponse(Call<ProductList> call, Response<ProductList> response) {

                    try {

                        ddProgressBarDialog.dismiss();

//                        Log.e("get_store_product_list", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (response.body().getResponse().getProductList_Data().size() > 0) {

                                product_list_items = (ArrayList<ProductList_Data>) response.body().getResponse().getProductList_Data();

                                prodListApater = new ProductListAdapter(getContext(), response.body().getResponse().getProductList_Data(), vender_id, outlet_id);
                                prodListApater.ProdListInterface(DynamicTabFragment.this);

                                recyclerView.setAdapter(prodListApater);
                                setBadgeCount("" + productRespository.getCartCount());
                            }
                            if (response.body().getResponse().getProductList_Data().size() == 0) {
                                menu_item_empty_text.setVisibility(View.VISIBLE);
                            } else {
                                menu_item_empty_text.setVisibility(View.GONE);
                            }

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(getContext(), response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        ddProgressBarDialog.dismiss();
//                        Log.e("Exception Error ", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ProductList> call, Throwable t) {
//                    Log.e("onFailure", "" + t.getMessage());
                    ddProgressBarDialog.dismiss();
                }
            });

        } catch (Exception e) {
            ddProgressBarDialog.dismiss();
//            Log.e("product_list Exception ", "" + e.getMessage());
        }

    }

    private void setBadgeCount(String cart_value) {
        if (Integer.parseInt(cart_value) == 0) {
            cart_view.setVisibility(View.GONE);

        } else {
            cart_view.setVisibility(View.VISIBLE);
            String text = String.format(getResources().getString(R.string.cart_count_update), "" + cart_value, loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));            cart_update.setText(Html.fromHtml(text));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setBadgeCount("" + productRespository.getCartCount());
    }


    public void updateProductQuantity(String quantity, int productID, int primaryID) {

        prodListApater.updateProductQuantity(quantity, productID, primaryID);

    }

    @Override
    public void updateProdListandCartCount() {
        setBadgeCount("" + productRespository.getCartCount());
    }


}
