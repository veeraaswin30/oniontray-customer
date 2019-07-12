package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/8/2017.
 */

public class IngredListAdapter extends RecyclerView.Adapter<IngredListAdapter.IngredListViewHolder> {

    private Context context;

    private LoginPrefManager loginPrefManager;

    private ArrayList<IngredientList> ingredientLists;
    int required;
    int type;

    private String checkbox_pos = "";

    public ArrayList<IngredientList> chooseIngredientLists = new ArrayList<>();

//    public ArrayList<IngredientList> sele_list;


    public IngredListAdapter(Context context, ArrayList<IngredientList> arrayList, int required, int type, ArrayList<IngredientList> sele_lists) {
        this.context = context;

        loginPrefManager = new LoginPrefManager(context);

        this.ingredientLists = arrayList;
        this.required = required;
        this.type = type;

        this.chooseIngredientLists = sele_lists;

//        this.sele_list = sele_lists;

//        Log.e("sele_list", "-" + sele_list.size());
    }

    @Override
    public int getItemCount() {
        return ingredientLists.size();
    }

    @Override
    public IngredListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredListAdapter.IngredListViewHolder(LayoutInflater.from(context).inflate(R.layout.proc_to_che_deliv_slot_dialog_item_row_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(final IngredListViewHolder holder, final int position) {

        if (ingredientLists.get(position).getPrice().equals("0")) {
            holder.proc_to_check_dialog_row_txt_view.setText("" + ingredientLists.get(position).getIngredientName());
        } else {
            holder.proc_to_check_dialog_row_txt_view.setText("" + ingredientLists.get(position).getIngredientName() + " (" + "" + loginPrefManager.getFormatCurrencyValue("" + ingredientLists.get(position).getPrice()) + ")");
        }

        holder.proc_to_check_dialog_row_check_box_view.setChecked(false);

        for (int i = 0; i < chooseIngredientLists.size(); i++) {
            if (ingredientLists.get(position).getIngredientId() == chooseIngredientLists.get(i).getIngredientId()) {
                holder.proc_to_check_dialog_row_check_box_view.setChecked(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.proc_to_check_dialog_row_check_box_view.setButtonTintList(ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeFontColor())));
                }


            }
        }

//        if (sele_list != null) {
//            for (int i = 0; i < sele_list.size(); i++) {
//
//                Log.e("getIngredSeleId", ""+ sele_list.get(i).getIngredientId());
//                Log.e("getIngredCurrId", ""+ ingredientLists.get(position).getIngredientId());
//
//                if (sele_list.get(i).getIngredientId() == ingredientLists.get(position).getIngredientId()) {
//                    holder.proc_to_check_dialog_row_check_box_view.setChecked(true);
//                    Log.e("getIngredientId", " true");
//                }
//
//            }
//        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.proc_to_check_dialog_row_check_box_view.isChecked()) {
                    holder.proc_to_check_dialog_row_check_box_view.setButtonTintList(ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeFontColor())));


                    // Allready cheeckbox checked - un checked

                    if (required == 1) {

                        // Selection type - (Required)

                        if (type == 1) {

                            // Selection type - (Choose only one)

//                            Log.e("Selected chec Size" + chooseIngredientLists.size(), "- 1");


                            if (chooseIngredientLists.size() > 1) {

                                RemoveSelectedData(ingredientLists.get(position).getIngredientId()); // Remove previous seection

                            }

                        } else {

                            // Selection type = (Choose multiple)

                            if (chooseIngredientLists.size() > 1) {

                                RemoveSelectedData(ingredientLists.get(position).getIngredientId()); // Remove previous seection

                            }

                        }

                    } else {

                        // Selection type - (Optional)

                        if (type == 1) {

                            // Selection type - (Choose only one)

                            if (chooseIngredientLists.size() > 1) {

                                RemoveSelectedData(ingredientLists.get(position).getIngredientId()); // Remove previous seection

                            }

                        } else {

                            // Selection type = (Choose multiple)

                            RemoveSelectedData(ingredientLists.get(position).getIngredientId()); // Remove previous seection

                        }

                    }

                } else {

                    // cheeckbox un checked - checked

                    if (required == 1) {

                        // Selection type - (Required)

                        if (type == 1) {

                            // Selection type - (Choose only one)

                            if (chooseIngredientLists.size() == 0) {

//                                Log.e("Selected un che Size :" + chooseIngredientLists.size(), "- 1");

                                AddSelectedData(ingredientLists.get(position)); // Add new selection

                            } else if (chooseIngredientLists.size() == 1) {

//                                Log.e("Selected un che Size :" + chooseIngredientLists.size(), "- 1");

                                RemoveSelectedData(chooseIngredientLists.get(0).getIngredientId()); // Remove previous seection

                                AddSelectedData(ingredientLists.get(position)); // Add new selection

                            }

                        } else {

                            // Selection type = (Choose multiple)

                            AddSelectedData(ingredientLists.get(position)); // Add new selection

                        }

                    } else {

                        // Selection type - (Optional)

                        if (type == 1) {

                            // Selection type - (Choose only one)

                            if (chooseIngredientLists.size() == 0) {

                                AddSelectedData(ingredientLists.get(position)); // Add new selection

                            } else if (chooseIngredientLists.size() == 1) {

                                RemoveSelectedData(chooseIngredientLists.get(0).getIngredientId()); // Remove previous seection

                                AddSelectedData(ingredientLists.get(position)); // Add new selection

                            }

                        } else {

                            // Selection type = (Choose multiple)

                            AddSelectedData(ingredientLists.get(position)); // Add new selection

                        }
                    }

                }

            }
        });

    }


    private void RemoveSelectedData(int select_id) {

        String equal_position = "";

        for (int i = 0; i < chooseIngredientLists.size(); i++) {
            if (chooseIngredientLists.get(i).getIngredientId() == select_id) {
                equal_position = "" + i;
            }
        }

        if (!equal_position.isEmpty()) {
            chooseIngredientLists.remove(Integer.parseInt(equal_position));
            notifyDataSetChanged();
        }

        notifyDataSetChanged();
    }


    private void AddSelectedData(IngredientList ingredientList) {
        chooseIngredientLists.add(ingredientList);
        notifyDataSetChanged();
    }


    public ArrayList<IngredientList> GetValidateSelectedDatas() {
        return chooseIngredientLists;
    }


    public class IngredListViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public TextView proc_to_check_dialog_row_txt_view;
        public CheckBox proc_to_check_dialog_row_check_box_view;

        public IngredListViewHolder(View itemView) {
            super(itemView);

            this.itemView = itemView;

            this.proc_to_check_dialog_row_txt_view = (TextView) itemView.findViewById(R.id.proc_to_check_dialog_row_txt_view);
            this.proc_to_check_dialog_row_check_box_view = (CheckBox) itemView.findViewById(R.id.proc_to_check_dialog_row_check_box_view);

        }
    }

}
