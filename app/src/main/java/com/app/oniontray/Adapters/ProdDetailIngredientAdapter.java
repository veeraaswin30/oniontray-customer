package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.Activites.IngredientListActivity;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.IngredTypeList;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.SelectedIngredient;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/7/2017.
 */

public class ProdDetailIngredientAdapter extends RecyclerView.Adapter<ProdDetailIngredientAdapter.IngredientViewHolder> {


    private Context context;
    private ArrayList<IngredTypeList> ingredTypeListArrayList;
    private LoginPrefManager loginPrefManager;

    public ArrayList<SelectedIngredient> chooseIngredientLists;

    private static boolean verifi_boolean = false;

    private int quantity = 0;

    public ProdDetailIngredientAdapter(Context context, ArrayList<IngredTypeList> ingredTypeLists,LoginPrefManager loginPrefManager) {
        this.context = context;
        this.ingredTypeListArrayList = ingredTypeLists;
        this.loginPrefManager = loginPrefManager;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProdDetailIngredientAdapter.IngredientViewHolder(LayoutInflater.from(context).inflate(R.layout.prod_deta_ingre_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, final int position) {

        holder.prod_detail_ingred_name_txt_view.setText("" + ingredTypeListArrayList.get(position).getIngredientTypeName());

        if (ingredTypeListArrayList.get(position).getRequired() == 1) {
            holder.prod_detail_ingred_optional_txt_view.setText("" + context.getString(R.string.prod_detail_ingred_required_type_txt));
            holder.prod_detail_ingred_optional_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        } else {
            holder.prod_detail_ingred_optional_txt_view.setText("" + context.getString(R.string.prod_detail_ingred_optional_type_txt));
            holder.prod_detail_ingred_optional_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        }

        if (ingredTypeListArrayList.get(position).getType() == 1) {
            holder.prod_detail_ingred_choose_txt_view.setText("");
//            holder.prod_detail_ingred_choose_txt_view.setText("" + context.getString(R.string.prod_detail_ingred_choose_type_txt));
        } else {
            holder.prod_detail_ingred_choose_txt_view.setText("");
        }

//        Log.e("onBindViewHolder", "- "+position);

        holder.rowItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantity == 0) {
                    if (chooseIngredientLists != null) {

                        for (int i = 0; i < chooseIngredientLists.size(); i++) {

                            if (chooseIngredientLists.get(i).getIngredientTypeId() == ingredTypeListArrayList.get(position).getIngredientTypeId()) {

                                Intent intent = new Intent(context, IngredientListActivity.class);
                                intent.putExtra("ingred_list", ingredTypeListArrayList.get(position));
                                intent.putExtra("allready_sele_list", chooseIngredientLists.get(i).getIngredientLists());
                                context.startActivity(intent);

                                return;
                            }

                        }
                    }

                    Intent intent = new Intent(context, IngredientListActivity.class);
                    intent.putExtra("ingred_list", ingredTypeListArrayList.get(position));
                    ArrayList<IngredientList> ingredientLists = new ArrayList<IngredientList>();
                    intent.putExtra("allready_sele_list", ingredientLists);
                    context.startActivity(intent);

                }
            }
        });

        if (chooseIngredientLists != null) {

            for (int i = 0; i < chooseIngredientLists.size(); i++) {

//                Log.e("verifi_boolean ", "-" + verifi_boolean);

                if (chooseIngredientLists.get(i).getIngredientTypeId() == ingredTypeListArrayList.get(position).getIngredientTypeId()) {
                    holder.prod_deta_ingred_type_valid_img.setImageResource(R.drawable.ic_ingred_select_done_green_btn_ic);

                    if (chooseIngredientLists.get(i).getIngredientLists().size() != 0) {

                        if (chooseIngredientLists.size() != 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int j = 0; j < chooseIngredientLists.get(i).getIngredientLists().size(); j++) {
                                stringBuilder.append(chooseIngredientLists.get(i).getIngredientLists().get(j).getIngredientName());
                                stringBuilder.append(",");
                            }

                            if (!stringBuilder.toString().isEmpty()) {
                                String ingredient = stringBuilder.toString().replaceAll(",$", "");
                                holder.ingredient_text_view.setText(ingredient);
                            }

                            holder.ingredient_text_view.setVisibility(View.VISIBLE);

                            holder.ingredient_text_view.setVisibility(View.VISIBLE);
                            holder.prod_deta_ingred_type_valid_img.setVisibility(View.VISIBLE);
                        } else {

                        }

//                    if(chooseIngredientLists.get(i).getRequired() == 1){
//                        holder.prod_deta_ingred_type_valid_img.setVisibility(View.VISIBLE);
//                    }else{
//                        holder.prod_deta_ingred_type_valid_img.setVisibility(View.GONE);
//                    }

                    } else {

                        if (chooseIngredientLists.get(i).getIngredientLists().size() == 0){

                            holder.ingredient_text_view.setText("");
                            holder.ingredient_text_view.setVisibility(View.GONE);

                            holder.prod_deta_ingred_type_valid_img.setVisibility(View.GONE);
                            holder.ingredient_text_view.setVisibility(View.GONE);
                        }


                        if (verifi_boolean) {
                            holder.prod_deta_ingred_type_valid_img.setImageResource(R.drawable.ic_ingred_select_done_green_btn_ic);
                            holder.prod_deta_ingred_type_valid_img.setVisibility(View.VISIBLE);

                        } else {
//                        Log.e("================", "verifi_boolean--------- false ------" + position);
                        }
                    }

                }

            }

        }

        if (position == (ingredTypeListArrayList.size() - 1)) {
            verifi_boolean = false;
        }

    }

    public void UpdateIngredientValitationSucess(ArrayList<SelectedIngredient> chooseIngredientLists) {
        this.chooseIngredientLists = chooseIngredientLists;
        notifyDataSetChanged();
    }

    public void ValidateAddToCartVerifMethod(boolean verifi_boolean) {
        this.verifi_boolean = verifi_boolean;
        notifyDataSetChanged();
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    @Override
    public int getItemCount() {
        return ingredTypeListArrayList.size();
    }


    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private View rowItemView;

        private ImageView prod_deta_ingred_type_valid_img;
        private TextView prod_detail_ingred_name_txt_view,ingredient_text_view;

        private TextView prod_detail_ingred_optional_txt_view;
        private TextView prod_detail_ingred_choose_txt_view;

        public IngredientViewHolder(View itemView) {
            super(itemView);

            rowItemView = itemView;

            prod_deta_ingred_type_valid_img = (ImageView) itemView.findViewById(R.id.prod_deta_ingred_type_valid_img);
            prod_detail_ingred_name_txt_view = (TextView) itemView.findViewById(R.id.prod_detail_ingred_name_txt_view);

            prod_detail_ingred_optional_txt_view = (TextView) itemView.findViewById(R.id.prod_detail_ingred_optional_txt_view);
            prod_detail_ingred_choose_txt_view = (TextView) itemView.findViewById(R.id.prod_detail_ingred_choose_txt_view);
            ingredient_text_view = (TextView) itemView.findViewById(R.id.ingredient_text_view);

        }
    }

}
