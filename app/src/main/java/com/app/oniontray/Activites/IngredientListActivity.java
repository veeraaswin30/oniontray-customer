package com.app.oniontray.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.Adapters.IngredListAdapter;
import com.app.oniontray.LocalizationActivity.LanguageSetting;
import com.app.oniontray.LocalizationActivity.LocalizationActivity;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.CityAreaLocItemOffsetDecor;
import com.app.oniontray.RequestModels.IngredTypeList;
import com.app.oniontray.RequestModels.IngredientList;
import com.app.oniontray.RequestModels.SelectedIngredient;

import java.util.ArrayList;

/**
 * Created by NEXTBRAIN on 2/8/2017.
 */

public class IngredientListActivity extends LocalizationActivity {


    private Toolbar ingredient_list_toolber;

    private RecyclerView ingredient_list_recycler_view;
    private IngredTypeList ingredTypeList;

    private IngredListAdapter ingredListAdapter;

    private TextView ingredient_list_empty_txt_view;

    private Button ingredient_done_btn;


    public SelectedIngredient selectedIngredient = new SelectedIngredient();


    public ArrayList<IngredientList> ingredientLists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_list_layout);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ingredTypeList = (IngredTypeList) bundle.getSerializable("ingred_list");
            ingredientLists = (ArrayList<IngredientList>) bundle.getSerializable("allready_sele_list");
        }

        initReferMethod();
    }


    private void initReferMethod() {

        ingredient_list_toolber = (Toolbar) findViewById(R.id.ingredient_list_toolber);

        ingredient_list_toolber.setTitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        ingredient_list_toolber.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        ingredient_list_toolber.setSubtitleTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        setSupportActionBar(ingredient_list_toolber);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

           String language = String.valueOf(LanguageSetting.getLanguage(IngredientListActivity.this));


        if (language.equals("en")) {
            //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_bar_en_back_ic);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_action_bar_en_back_ic);
            upArrow.setColorFilter(Color.parseColor(loginPrefManager.getThemeFontColor()), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        } else {
            /*getSupportActionBar().setHomeAsUpIndicator(R.drawable.action_bar_ar_back_ic);*/
        }


        if (ingredTypeList != null) {

            if (ingredTypeList.getType() == 1) {

                getSupportActionBar().setTitle("" + ingredTypeList.getIngredientTypeName() + " " + getString(R.string.prod_detail_ingred_choose_type_txt));

                if (ingredTypeList.getRequired() == 1) {
                    getSupportActionBar().setSubtitle("" + getString(R.string.prod_detail_ingred_required_type_txt));
                } else {
                    getSupportActionBar().setSubtitle("" + getString(R.string.prod_detail_ingred_optional_type_txt));
                }

            } else {

                getSupportActionBar().setTitle("" + ingredTypeList.getIngredientTypeName());

                if (ingredTypeList.getRequired() == 1) {
                    getSupportActionBar().setSubtitle("" + getString(R.string.prod_detail_ingred_required_type_txt));
                } else {
                    getSupportActionBar().setSubtitle("" + getString(R.string.prod_detail_ingred_optional_type_txt));
                }
            }

        }


        ingredient_list_recycler_view = (RecyclerView) findViewById(R.id.ingredient_list_recycler_view);
        ingredient_list_recycler_view.setLayoutManager(new LinearLayoutManager(IngredientListActivity.this));
        ingredient_list_recycler_view.setHasFixedSize(true);
        ingredient_list_recycler_view.addItemDecoration(new CityAreaLocItemOffsetDecor(IngredientListActivity.this, R.dimen.sele_city_area_loc_item_devi_height_size));

        ingredient_list_empty_txt_view = (TextView) findViewById(R.id.ingredient_list_empty_txt_view);

        ingredient_done_btn = (Button) findViewById(R.id.ingredient_done_btn);
        ingredient_done_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeFontColor()));
        ingredient_done_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeColor()));

        clickActionMethods();

        CreateIngredListAdapter((ArrayList<IngredientList>) ingredTypeList.getIngredientList());


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void clickActionMethods() {

        ingredient_list_toolber.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ingredient_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ingredListAdapter != null) {
                    CheckDoneButtonMethod(ingredListAdapter.GetValidateSelectedDatas());
                }

            }
        });


    }


    private void CreateIngredListAdapter(ArrayList<IngredientList> arrayList) {

        ingredListAdapter = new IngredListAdapter(IngredientListActivity.this, arrayList, ingredTypeList.getRequired(), ingredTypeList.getType(), ingredientLists);
        ingredient_list_recycler_view.setAdapter(ingredListAdapter);

    }

    private void CheckDoneButtonMethod(ArrayList<IngredientList> ingredientLists) {

        if (ingredTypeList.getRequired() == 1) {

            // Selection type - (Required)

            if (ingredTypeList.getType() == 1) {

                // Selection type - (Choose only one)

                if (ingredientLists.size() == 1) {
                    // Validation Success

                    selectedIngredient.setIngredientTypeId(ingredTypeList.getIngredientTypeId());
                    selectedIngredient.setIngredientTypeName(ingredTypeList.getIngredientTypeName());
                    selectedIngredient.setRequired(ingredTypeList.getRequired());
                    selectedIngredient.setType(ingredTypeList.getType());

                    selectedIngredient.setIngredientLists(ingredientLists);

                    Intent intent = new Intent("ingredient_receiver");
                    intent.putExtra("array_list", selectedIngredient);
                    LocalBroadcastManager.getInstance(IngredientListActivity.this).sendBroadcast(intent);

                    finish();

                } else {
                    // Validation Faild

                    Toast.makeText(IngredientListActivity.this, "" + getString(R.string.prod_detail_ingred_selec_any_one_txt), Toast.LENGTH_SHORT).show();

                }


            } else {

                // Selection type = (Choose multiple)

                if (ingredientLists.size() > 0) {
                    // Validation Success

                    selectedIngredient.setIngredientTypeId(ingredTypeList.getIngredientTypeId());
                    selectedIngredient.setIngredientTypeName(ingredTypeList.getIngredientTypeName());
                    selectedIngredient.setRequired(ingredTypeList.getRequired());
                    selectedIngredient.setType(ingredTypeList.getType());

                    selectedIngredient.setIngredientLists(ingredientLists);

                    Intent intent = new Intent("ingredient_receiver");
                    intent.putExtra("array_list", selectedIngredient);
                    LocalBroadcastManager.getInstance(IngredientListActivity.this).sendBroadcast(intent);

                    finish();

                } else {
                    // Validation Faild

                    Toast.makeText(IngredientListActivity.this, "" + getString(R.string.prod_detail_ingred_selec_any_one_txt), Toast.LENGTH_SHORT).show();

                }

            }

        } else {

            // Selection type - (Optional)

            if (ingredientLists.size() > 0) {

                if (ingredTypeList.getType() == 1) {

                    // Selection type - (Choose only one)

                    if (ingredientLists.size() == 1) {

                        // Validation Success

                        selectedIngredient.setIngredientTypeId(ingredTypeList.getIngredientTypeId());
                        selectedIngredient.setIngredientTypeName(ingredTypeList.getIngredientTypeName());
                        selectedIngredient.setRequired(ingredTypeList.getRequired());
                        selectedIngredient.setType(ingredTypeList.getType());

                        selectedIngredient.setIngredientLists(ingredientLists);

                        Intent intent = new Intent("ingredient_receiver");
                        intent.putExtra("array_list", selectedIngredient);
                        LocalBroadcastManager.getInstance(IngredientListActivity.this).sendBroadcast(intent);

                        finish();


                    } else {

                        // Validation Failed

                        Toast.makeText(IngredientListActivity.this, "" + getString(R.string.prod_detail_ingred_selec_any_one_txt), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    // Selection type = (Choose multiple)

                    // Validation Success

                    selectedIngredient.setIngredientTypeId(ingredTypeList.getIngredientTypeId());
                    selectedIngredient.setIngredientTypeName(ingredTypeList.getIngredientTypeName());
                    selectedIngredient.setRequired(ingredTypeList.getRequired());
                    selectedIngredient.setType(ingredTypeList.getType());

                    selectedIngredient.setIngredientLists(ingredientLists);

                    Intent intent = new Intent("ingredient_receiver");
                    intent.putExtra("array_list", selectedIngredient);
                    LocalBroadcastManager.getInstance(IngredientListActivity.this).sendBroadcast(intent);

                    finish();

                }

            } else {

//                Log.e("optional array", "Success");

                // Validation Success

                selectedIngredient.setIngredientTypeId(ingredTypeList.getIngredientTypeId());
                selectedIngredient.setIngredientTypeName(ingredTypeList.getIngredientTypeName());
                selectedIngredient.setRequired(ingredTypeList.getRequired());
                selectedIngredient.setType(ingredTypeList.getType());

                selectedIngredient.setIngredientLists(ingredientLists);

                Intent intent = new Intent("ingredient_receiver");
                intent.putExtra("array_list", selectedIngredient);
                LocalBroadcastManager.getInstance(IngredientListActivity.this).sendBroadcast(intent);

                finish();

            }

        }

    }


}
