package com.app.oniontray.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.app.oniontray.R;
import com.app.oniontray.RequestModels.CuisineList;
import com.app.oniontray.RequestModels.FilterCategoryList;
import com.app.oniontray.Utils.CuisinesSingleton;
import com.app.oniontray.Utils.LoginPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by NEXTBRAIN on 1/30/2017.
 */

public class FilterQuickCuisionAdapters extends RecyclerView.Adapter<FilterQuickCuisionAdapters.FilterViewHolder> {

    private Context context;

    private List<CuisineList> filterCuisineList;
    private List<FilterCategoryList> filterCategoryList;
    private String type;

    public LoginPrefManager loginPrefManager;
    private ColorStateList colorStateList;

    private HashMap<String, CuisineList> CusineListHashMap = new HashMap<String, CuisineList>();
    private HashMap<String, FilterCategoryList> CategoryListHashMap = new HashMap<String, FilterCategoryList>();


    public FilterQuickCuisionAdapters(Context context, List<CuisineList> cuisineList, String type) {
        this.context = context;
//        All cuisines
//        cuisineList.add(0,list);

        this.filterCuisineList = new ArrayList<>();


        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cuisineList.size(); i++) {
            stringBuilder.append(cuisineList.get(i).getId());
            if (i != cuisineList.size() - 1) {
                stringBuilder.append(",");
            }
        }

//        Log.e("string", stringBuilder.toString());

        CuisineList cuisine = new CuisineList();
        cuisine.setName("" + context.getString(R.string.filter_all_cuisines));
        cuisine.setId(stringBuilder.toString());
        this.filterCuisineList.add(cuisine);
        this.filterCuisineList.addAll(cuisineList);
        this.type = type;
    }

    public FilterQuickCuisionAdapters(Context context, List<FilterCategoryList> categoryList) {

        this.context = context;
        this.filterCategoryList = new ArrayList<>();
        this.filterCategoryList = categoryList;
    }

    @Override
    public FilterQuickCuisionAdapters.FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FilterQuickCuisionAdapters.FilterViewHolder(LayoutInflater.from(context).inflate(R.layout.filter_quick_and_cuision_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final FilterQuickCuisionAdapters.FilterViewHolder holder, final int position) {


        loginPrefManager = new LoginPrefManager(context);
        if (type != null) {
            setfilterCuisineList(holder, position);
        } else {
            setfilterCategoryList(holder, position);
        }

        colorStateList = new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{

                        Color.parseColor(loginPrefManager.getThemeColor()) //disabled
                        , Color.parseColor(loginPrefManager.getThemeColor()) //enabled

                }
        );

    }

    private void setfilterCuisineList(final FilterViewHolder holder, final int position) {

        if (holder.cuisinesSingleton.getArray() != null &&
                !holder.cuisinesSingleton.getArray().isEmpty()) {
            for (int i = 0; i < holder.cuisinesSingleton.getArray().size(); i++) {
                if (holder.cuisinesSingleton.getArray().get(i).contains(filterCuisineList.get(position).getId().toString())) {
                    holder.filter_quick_name_check_box_view.setChecked(true);
                }
            }

        }


        holder.filter_quick_name_txt_view.setText(filterCuisineList.get(position).getName());

        if (CusineListHashMap.containsKey("" + position)) {
            holder.filter_quick_name_check_box_view.setChecked(true);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//            }
            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.filter_quick_name_check_box_view.setChecked(false);
            //holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.disable_txt_color));
        }

        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.e("hashmap", "" + CusineListHashMap.containsKey("0"));

                if (holder.filter_quick_name_check_box_view.isChecked()) {
                    holder.filter_quick_name_check_box_view.setChecked(false);
                    holder.cuisinesSingleton.removeToArr(filterCuisineList.get(position).getId().toString());
                    //mTempList.remove(cuisineList.id.toString())
                    Log.e("ArrToStr", filterCuisineList.get(position).getId().toString());

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//                    }
                    // holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.disable_txt_color));
                    CusineListHashMap.remove("" + position);

                } else {

                    if (position == 0) {
                        if (CusineListHashMap.size() != 0) {
                            CusineListHashMap.clear();
                            holder.filter_quick_name_check_box_view.setChecked(true);
                            holder.cuisinesSingleton.addToArray(filterCuisineList.get(position).getId().toString());
                            Log.e("ArrToStr", filterCuisineList.get(position).getId().toString());
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//                            }
                            // holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
                            //  holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
                            // holder.filter_quick_name_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));
                            CusineListHashMap.put("" + position, filterCuisineList.get(position));
                            notifyDataSetChanged();
                        }
                    }
                    if (!CusineListHashMap.containsKey("0")) {
                        holder.filter_quick_name_check_box_view.setChecked(true);
                        // holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//                        }
//                        holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
//                        holder.filter_quick_name_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

                        CusineListHashMap.put("" + position, filterCuisineList.get(position));
                    }

                }

            }
        });
    }

    private void setfilterCategoryList(final FilterViewHolder holder, final int position) {

        if (holder.cuisinesSingleton.getFilterArray() != null &&
                !holder.cuisinesSingleton.getFilterArray().isEmpty()) {
            for (int i = 0; i < holder.cuisinesSingleton.getFilterArray().size(); i++) {
                if (holder.cuisinesSingleton.getFilterArray().get(i).contains(filterCuisineList.get(position).getId().toString())) {
                    holder.filter_quick_name_check_box_view.setChecked(true);
                }
            }

        }


        if (CategoryListHashMap.containsKey("" + position)) {
            holder.filter_quick_name_check_box_view.setChecked(true);
            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
            //holder.filter_quick_name_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        } else {
            holder.filter_quick_name_check_box_view.setChecked(false);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//            }
//            holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.disable_txt_color));
        }

        holder.filter_quick_name_txt_view.setText(filterCategoryList.get(position).getName());
        // Log.e("CategoryName",filterCategoryList.get(position).getName());

//        if(CategoryListHashMap.containsKey(position)){
//            holder.filter_quick_name_check_box_view.setChecked(true);
//            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
//        }else{
//            holder.filter_quick_name_check_box_view.setChecked(false);
//            holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.dark_txt_color));
//        }

        holder.root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.filter_quick_name_check_box_view.isChecked()) {
                    holder.filter_quick_name_check_box_view.setChecked(false);
                    holder.cuisinesSingleton.removeToFilterArr(filterCuisineList.get(position).getId().toString());
                    //mTempList.remove(cuisineList.id.toString())
                    Log.e("ArrToStr", filterCuisineList.get(position).getId().toString());
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//                    }
//                    holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.disable_txt_color));
                    CategoryListHashMap.remove("" + position);

                } else {
                    //holder.cuisinesSingleton.addToFilterArray(filterCuisineList.get(position).getId().toString());
                    // Log.e("ArrToStr",filterCuisineList.get(position).getId().toString());
                    holder.filter_quick_name_check_box_view.setChecked(true);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        holder.filter_quick_name_check_box_view.setButtonTintList(colorStateList);
//                    }
//                    holder.filter_quick_name_check_box_view.setHighlightColor(Color.parseColor(loginPrefManager.getThemeColor()));
                    // holder.filter_quick_name_txt_view.setTextColor(context.getResources().getColor(R.color.colorAccent));
                    //holder.filter_quick_name_txt_view.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

                    CategoryListHashMap.put("" + position, filterCategoryList.get(position));
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        if (type != null) {
            return filterCuisineList.size();
        } else {
            return filterCategoryList.size();
        }
    }


    public class FilterViewHolder extends RecyclerView.ViewHolder {

        //        private CheckedTextView filter_selecter_check_txt_view;
        private CuisinesSingleton cuisinesSingleton;

        private View root_view;

        private TextView filter_quick_name_txt_view;

        private CheckBox filter_quick_name_check_box_view;


        public FilterViewHolder(View itemView) {
            super(itemView);
            cuisinesSingleton = CuisinesSingleton.Companion.getInstance();
            root_view = itemView;

//            filter_selecter_check_txt_view = (CheckedTextView) itemView.findViewById(R.id.filter_selecter_check_txt_view);

            filter_quick_name_txt_view = (TextView) itemView.findViewById(R.id.filter_quick_name_txt_view);
            filter_quick_name_check_box_view = (CheckBox) itemView.findViewById(R.id.filter_quick_name_check_box_view);

        }

    }

    public HashMap<String, CuisineList> cusineList() {
        return CusineListHashMap;
    }

    public HashMap<String, FilterCategoryList> categoryList() {
        return CategoryListHashMap;
    }

    public void clearHashMapCuisine() {
        CusineListHashMap.clear();
        notifyDataSetChanged();

    }

    public void clearHashMapCategory() {
        CategoryListHashMap.clear();
        notifyDataSetChanged();

    }


}

