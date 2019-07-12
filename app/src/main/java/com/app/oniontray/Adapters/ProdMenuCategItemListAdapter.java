package com.app.oniontray.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CategoryList;

import java.util.ArrayList;

/**
 * Created by nextbrain on 20/11/17.
 */

public class ProdMenuCategItemListAdapter extends RecyclerView.Adapter<ProdMenuCategItemListAdapter.ProdMenuCategItemListAdaptViewHolder> {


    private Context context;
    private ArrayList<CategoryList> categoryList;


    public ProdMenuCategItemListAdapter(Context context, ArrayList<CategoryList> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }


    @Override
    public ProdMenuCategItemListAdaptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProdMenuCategItemListAdaptViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_prod_menu_categ_item_layout, parent, false));
    }


    @Override
    public void onBindViewHolder(ProdMenuCategItemListAdaptViewHolder holder, final int position) {

        try {

            holder.adapt_prod_menu_categ_name_txt_view.setText(categoryList.get(position).getCategoryName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (prdMenuCategItemInterface != null) {
                        prdMenuCategItemInterface.ProdMenuCategItemClickInterface(categoryList.get(position));
                    }

                }
            });

        } catch (Exception e) {
            Log.e("Exception", "" + e.getMessage());
        }

    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    /*Prod Metnu Categ item adapter interface*/

    public PrdMenuCategItemInterface prdMenuCategItemInterface;

    public void callPrdMenuCategItemInterface(PrdMenuCategItemInterface prdMenuCategItemInterface) {
        this.prdMenuCategItemInterface = prdMenuCategItemInterface;
    }

    public interface PrdMenuCategItemInterface {
        void ProdMenuCategItemClickInterface(CategoryList categoryList);
    }


    /*Product menu page categ item list recycler view adapter view holder*/

    public class ProdMenuCategItemListAdaptViewHolder extends RecyclerView.ViewHolder {

        private TextView adapt_prod_menu_categ_name_txt_view;

        public ProdMenuCategItemListAdaptViewHolder(View itemView) {
            super(itemView);

            adapt_prod_menu_categ_name_txt_view = (TextView) itemView.findViewById(R.id.adapt_prod_menu_categ_name_txt_view);

        }

    }


}
