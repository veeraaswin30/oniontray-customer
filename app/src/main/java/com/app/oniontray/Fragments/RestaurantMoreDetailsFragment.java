package com.app.oniontray.Fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.oniontray.Adapters.RestaurantMoreDetailsAdapter;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.ProdListItemOffsetDecor;
import com.app.oniontray.RequestModels.StoInfoOutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by nextbrain on 2/16/2017.
 */

public class RestaurantMoreDetailsFragment extends Fragment {

    private RecyclerView fragment_restaurant_more_details_recycler_view;

    private StoInfoOutletDetails vendorDetail;

    String dayOfTheWeek;
    String openTime;

    private LoginPrefManager loginPrefManager;

    public RestaurantMoreDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_more_details, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            vendorDetail = (StoInfoOutletDetails) bundle.getSerializable("vendor_detail");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        Date d = new Date();
        dayOfTheWeek = sdf.format(d);

//        Log.e("dayoftheWeek", dayOfTheWeek);

        loginPrefManager = new LoginPrefManager(getContext());

        fragment_restaurant_more_details_recycler_view = (RecyclerView) rootView.findViewById(R.id.fragment_restaurant_more_details_recycler_view);

        fragment_restaurant_more_details_recycler_view.setHasFixedSize(true);
        fragment_restaurant_more_details_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        fragment_restaurant_more_details_recycler_view.addItemDecoration(new ProdListItemOffsetDecor(getContext(), R.dimen.prod_list_item_row_line_height));

        setAdapter();

        return rootView;
    }

    private void setAdapter() {

        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < vendorDetail.getOpenTime().size(); j++) {
            if (vendorDetail.getOpenTime().get(j).getDay().equalsIgnoreCase(dayOfTheWeek)) {
                stringBuilder.append(vendorDetail.getOpenTime().get(j).getStartTime() + " - " + vendorDetail.getOpenTime().get(j).getEndTime());
                if (j != vendorDetail.getOpenTime().size()){
                    stringBuilder.append(", ");
                }
            }
        }

        String str = stringBuilder.toString().trim();
        str = str.replaceAll(",$", "");
        openTime = str.trim();

//        for (int i = 0; i < vendorDetail.getOpenTime().size(); i++) {
//            if (vendorDetail.getOpenTime().get(i).getDay().equalsIgnoreCase(dayOfTheWeek)) {
//                openTime = vendorDetail.getOpenTime().get(i).getStartTime() + " - " + vendorDetail.getOpenTime().get(i).getEndTime();
//                break;
//            }
//        }

        List<String> stringArray = Arrays.asList(getResources().getStringArray(R.array.more_details));
        List<String> Values = new ArrayList<>();

        Values.add(vendorDetail.getOutletName());
        Values.add(vendorDetail.getCityName());
        Values.add(vendorDetail.getLocationName());

        if (vendorDetail.getOpenRestaurant() == 1) {
            Values.add("" + getString(R.string.home_store_status_open_txt));

        } else {
            Values.add("" + getString(R.string.home_store_status_close_txt));
        }

        Values.add(vendorDetail.getCuisineName());


        if (openTime != null) {
            Values.add(openTime);
        } else {
            Values.add(getString(R.string.more_details_leave));
        }

        Values.add(vendorDetail.getDeliveryTime() + " " + getString(R.string.mins_txt));

        Values.add(String.valueOf(loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getMinimumOrderAmount())))));

        if (vendorDetail.getDeliveryType() == 1) {
            Values.add(getString(R.string.free));
            Values.add("No");

            if (vendorDetail.getPaymentMode() == 1) {
                Values.add("" + getString(R.string.more_det_cash_on_delivery));
            } else if (vendorDetail.getPaymentMode() == 2) {
                Values.add("" + getString(R.string.more_det_credit_cart));
            } else if (vendorDetail.getPaymentMode() == 3) {
                String both = "" + getString(R.string.more_det_cash_on_delivery) + ", " + getString(R.string.more_det_credit_cart);
                Values.add("" + both);
            }

        } else if (vendorDetail.getDeliveryType() == 2) {

            if (String.valueOf(Float.valueOf(vendorDetail.getDeliveryCostFixed())).equals("0.0")) {
                Values.add(getString(R.string.free));
            } else {
                Values.add(("" + loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getDeliveryCostFixed())))));
            }

            Values.add("No");

            if (vendorDetail.getPaymentMode() == 1) {
                Values.add("" + getString(R.string.more_det_cash_on_delivery));
            } else if (vendorDetail.getPaymentMode() == 2) {
                Values.add("" + getString(R.string.more_det_credit_cart));
            } else if (vendorDetail.getPaymentMode() == 3) {
                String both = "" + getString(R.string.more_det_cash_on_delivery) + ", " + getString(R.string.more_det_credit_cart);
                Values.add("" + both);
            }

        } else if (vendorDetail.getDeliveryType() == 3) {
//            int deliveryCost = Integer.parseInt(vendorDetail.getDeliveryCostFixed());
//            Values.add("" + loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues( (float) deliveryCost)));
            if (String.valueOf(Float.valueOf(vendorDetail.getDeliveryCostFixed())).equals("0.0")) {
                Values.add(getString(R.string.free));
            } else {
                Values.add(("" + loginPrefManager.getFormatCurrencyValue(loginPrefManager.GetEngDecimalFormatValues(Float.valueOf(vendorDetail.getDeliveryCostFixed())))));
            }
            Values.add("No");

            if (vendorDetail.getPaymentMode() == 1) {
                Values.add("" + getString(R.string.more_det_cash_on_delivery));
            } else if (vendorDetail.getPaymentMode() == 2) {
                Values.add("" + getString(R.string.more_det_credit_cart));
            } else if (vendorDetail.getPaymentMode() == 3) {
                String both = "" + getString(R.string.more_det_cash_on_delivery) + ", " + getString(R.string.more_det_credit_cart);
                Values.add("" + both);
            }

        }

        for (int i = 0; i < stringArray.size(); i++) {
//            Log.e("string", stringArray.get(i));
        }

        for (int j = 0; j < Values.size(); j++) {
//            Log.e("values", Values.get(j));
        }

        RestaurantMoreDetailsAdapter moreDetailsAdapter = new RestaurantMoreDetailsAdapter(getContext(), Values, stringArray);

        fragment_restaurant_more_details_recycler_view.setAdapter(moreDetailsAdapter);
    }

}
