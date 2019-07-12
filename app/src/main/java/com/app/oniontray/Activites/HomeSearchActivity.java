package com.app.oniontray.Activites;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.oniontray.Adapters.HomeSearchItemViewAdapter;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.HomeSearchItemOffsetDecor;
import com.app.oniontray.RequestModels.StoreList_Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NEXTBRAIN on 3/4/2017.
 */

public class HomeSearchActivity extends AppCompatActivity {

    private ImageView home_search_back_img;
    private EditText home_banner_search_edt_txt_view;


    private RecyclerView home_search_recyc_view;
    private HomeSearchItemViewAdapter homeSearchItemViewAdapter;


    private ArrayList<StoreList_Data> storeList_datas;
    private int array_list_count = 0;
    private TextView home_search_empty_txt_view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);

        if (getIntent() != null) {
            storeList_datas = (ArrayList<StoreList_Data>) getIntent().getSerializableExtra("resta_list");
            Log.e("storeList_datas", "" + storeList_datas.size());

            for (int i = 0; i < storeList_datas.size(); i++) {
                Log.e("size1", storeList_datas.get(i).getCuisineName());
            }
        }

        InitView();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void InitView() {

        home_search_back_img = (ImageView) findViewById(R.id.home_search_back_img);
        home_search_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        home_search_recyc_view = (RecyclerView) findViewById(R.id.home_search_recyc_view);
        home_search_recyc_view.setLayoutManager(new LinearLayoutManager(HomeSearchActivity.this));
        home_search_recyc_view.addItemDecoration(new HomeSearchItemOffsetDecor(HomeSearchActivity.this, R.dimen.ho_search_divider_line_size));
        homeSearchItemViewAdapter = new HomeSearchItemViewAdapter(HomeSearchActivity.this, new ArrayList<StoreList_Data>());
        home_search_recyc_view.setAdapter(homeSearchItemViewAdapter);

        home_search_empty_txt_view = (TextView) findViewById(R.id.home_search_empty_txt_view);

        home_banner_search_edt_txt_view = (EditText) findViewById(R.id.home_banner_search_edt_txt_view);
        home_banner_search_edt_txt_view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (!home_banner_search_edt_txt_view.getText().toString().isEmpty()) {
                        SearchStringQueryMethod(home_banner_search_edt_txt_view.getText().toString());
                    }

                }

                return false;
            }
        });

    }

    private void SearchStringQueryMethod(String query) {

        final List<StoreList_Data> filteredModelList = searchFilter(storeList_datas, query);
        if (homeSearchItemViewAdapter != null) {
            array_list_count = filteredModelList.size();
            homeSearchItemViewAdapter.SetFilterSearchArrayItems((ArrayList<StoreList_Data>) filteredModelList);

            if (array_list_count == 0) {
                home_search_empty_txt_view.setVisibility(View.VISIBLE);
            } else {
                home_search_empty_txt_view.setVisibility(View.GONE);
            }

        }
    }

    private List<StoreList_Data> searchFilter(List<StoreList_Data> models, String query) {
        query = query.toLowerCase();
        final List<StoreList_Data> filteredModelList = new ArrayList<>();
        for (StoreList_Data model : models) {

            String text = "";

            text = model.getOutletsName().toLowerCase();

            if (text.contains(query)) {
                filteredModelList.add(model);
            } else {

                text = model.getCuisineName().toLowerCase();

                if (text.contains(query)) {
                    filteredModelList.add(model);
                }

            }
        }
        return filteredModelList;
    }

}
