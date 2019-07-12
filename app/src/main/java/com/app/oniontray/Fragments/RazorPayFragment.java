package com.app.oniontray.Fragments;


import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

import com.app.oniontray.DB.IngredientRepository;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.Utils.LoginPrefManager;
import com.razorpay.Checkout;

/**
 * Created by nextbrain on 19/3/18.
 */

public class RazorPayFragment extends Fragment {
    private  View razor_pay_view;

    private OutletDetails outletDetails;
    private String Vendor_id;


    private TextView cash_on_deliv_pay_amount_txt;
    private Button placeOrder;


    public ProductRespository productRespository;
    public IngredientRepository ingredientRepository;
    private LoginPrefManager loginManager;
    private DDProgressBarDialog progressBarDialog;
    public RazorPayFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        razor_pay_view = inflater.inflate(R.layout.cash_on_delivery_fragment, container, false);


        Checkout.preload(getContext());

        outletDetails = (OutletDetails) getArguments().getSerializable("outlet_details");
        Vendor_id = getArguments().getString("vendor_id");


        loginManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());


        productRespository = new ProductRespository();
        ingredientRepository = new IngredientRepository();


        cash_on_deliv_pay_amount_txt = (TextView) razor_pay_view.findViewById(R.id.cash_on_deliv_pay_amount_txt);

        String text = String.format(getResources().getString(R.string.welcome_online_pay_messages), "" + loginManager.getFormatCurrencyValue("" + outletDetails.getGrandTotal()));
        cash_on_deliv_pay_amount_txt.setText(Html.fromHtml(text));


        placeOrder = (Button) razor_pay_view.findViewById(R.id.cash_on_deliv_place_order_btn);
        placeOrder.setOnClickListener(v -> ((RazorInterface) getActivity()).paymentProcess());

        return razor_pay_view;
    }

    public interface RazorInterface {

        void paymentProcess();
    }
}