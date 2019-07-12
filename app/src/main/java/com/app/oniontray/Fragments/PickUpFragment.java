package com.app.oniontray.Fragments;

/**
 * Created by nextbrain on 2/1/18.
 */


import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.oniontray.Activites.PickUpProceedToPayment;
import com.app.oniontray.Adapters.DelivFragProductAdapter;
import com.app.oniontray.CustomViews.ExpandableHeightListView;
import com.app.oniontray.DB.ProductRespository;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RecyclerView.NotificationListItemOffsetDecor;
import com.app.oniontray.RequestModels.OutletDetails;
import com.app.oniontray.RequestModels.ProcToCheckResp;
import com.app.oniontray.RequestModels.PromoCode;
import com.app.oniontray.RequestModels.PromoCodeResponse;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PickUpFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener{

    private LoginPrefManager prefManager;
    private DDProgressBarDialog progressBarDialog;

    private View pickUpView;

    private TextView pickup_outlet_add_txt;
    private TextInputLayout pickup_txt_input_layout;
    private EditText pickup_deliv_instru_edit_txt;

    private Button proceed_to_pay;

    private ProcToCheckResp response_result;

    private TextView add_address;

    private TextView sub_total_txt;
    private TextView service_tax_txt;

    private TextView grand_total_txt;

    private TableRow deliv_promo_code_apply_row, deliv_valide_promo_code_table_row;
    private TextView deliv_vali_promo_code_txt, deliv_vali_promo_code_amt_txt;

    private ExpandableHeightListView Product_list;
    private TextView coupon;
    private EditText coupon_code_edt_txt;
    private EditText InputDelivery;

    private TableRow proc_to_check_coupon_apply_table_lay;
    private EditText proc_to_check_coupon_edt_txt_view;
    private Button proc_to_check_coupon_apply_btn;
    private TextView copoun_code_err_msg_txt;

    private TableRow proc_to_check_coupon_remove_table_lay;
    private TextView proc_to_check_coupon_txt_view;
    private Button proc_to_check_coupon_romve_btn;


    private TableRow proc_to_check_coupon_discount_table_lay;
    private TextView proc_to_chec_coupon_disc_amt_holder_txt_view;
    private TextView proc_to_chec_coupon_disc_amt_txt_view;

    private TableRow proc_to_check_coupon_amt_pay_table_lay;
    private TextView proc_to_chec_amt_pay_holder_txt_view;
    private TextView proc_to_chec_amt_pay_txt_view;
    private TextView platformChargesText;
    private int promo_offer_amount;

    //    public String total_value = "";
    private String grand_total_at = "";

    DecimalFormat decimalFormat = new DecimalFormat("#");
    private RecyclerView prod_list_recycler_view;
    private DelivFragProductAdapter delivFragProductAdapter;
    private ProductRespository productRespository;
    private static Button proceed_payment_but;
    private static TextView pro_to_check_exp_lay_deli_slot_select_date_txt,
            proc_to_check_service_tax_label;

    private TextInputLayout input_layout_delivery, input_layout_promocode;
    private OutletDetails outletDetails;
    private String vendor_id = "";
    private  TextView date_call ,time_call;
    Calendar c;
    private int mYear, mMonth, mDay,selected_year,selected_month,day_month,mHour, mMinute, mseconds;
    private final SimpleDateFormat old_date_format = new SimpleDateFormat("yyyy/MM/dd hh:mm aa", Locale.ENGLISH);
    private final SimpleDateFormat new_date_format = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.ENGLISH);

    String time_and_sec;
    String date_and_time;
    private TextView ed_date, ed_time;
    private DatePickerDialog dpd;
    private com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd;
    public PickUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pickUpView = inflater.inflate(R.layout.pick_up_fragment, container, false);

        prefManager = new LoginPrefManager(getContext());
        progressBarDialog = new DDProgressBarDialog(getContext());

        productRespository = new ProductRespository();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            outletDetails = (OutletDetails) bundle.getSerializable("outlet_details");
            vendor_id = bundle.getString("vendor_id");
//            Log.e("vendorID", vendor_id);
        }

        pickup_outlet_add_txt = (TextView) pickUpView.findViewById(R.id.pickup_outlet_add_txt);

        pickup_outlet_add_txt.setText(outletDetails.getContactAddress());


        prod_list_recycler_view = (RecyclerView) pickUpView.findViewById(R.id.prod_list_recycler_view);
        prod_list_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        prod_list_recycler_view.setHasFixedSize(true);
        prod_list_recycler_view.addItemDecoration(new NotificationListItemOffsetDecor(getContext(), R.dimen.notifications_list_item_row_line_hight));


        proceed_payment_but = (Button) pickUpView.findViewById(R.id.proceed_payment_but);
        coupon = (TextView) pickUpView.findViewById(R.id.apply_but);
        coupon_code_edt_txt = (EditText) pickUpView.findViewById(R.id.coupon_code_edt_txt);
        coupon_code_edt_txt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        proc_to_check_service_tax_label = (TextView) pickUpView.findViewById(R.id.service_tax_label);
        platformChargesText = (TextView) pickUpView.findViewById(R.id.delivery_charges_txt);


        proc_to_check_coupon_apply_table_lay = (TableRow) pickUpView.findViewById(R.id.proc_to_check_coupon_apply_table_lay);
        proc_to_check_coupon_edt_txt_view = (EditText) pickUpView.findViewById(R.id.proc_to_check_coupon_edt_txt_view);
        proc_to_check_coupon_edt_txt_view.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        proc_to_check_coupon_apply_btn = (Button) pickUpView.findViewById(R.id.proc_to_check_coupon_apply_btn);
        copoun_code_err_msg_txt = (TextView) pickUpView.findViewById(R.id.copoun_code_err_msg_txt);

        proc_to_check_coupon_edt_txt_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (proc_to_check_coupon_edt_txt_view.getText().toString().trim().length() == 0) {
                    copoun_code_err_msg_txt.setVisibility(View.VISIBLE);
                } else {
                    copoun_code_err_msg_txt.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        proc_to_check_coupon_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (proc_to_check_coupon_edt_txt_view.getText().toString().trim().length() == 0) {
                    copoun_code_err_msg_txt.setVisibility(View.VISIBLE);
                    return;
                } else {
                    copoun_code_err_msg_txt.setVisibility(View.GONE);
                }

                ApplyCopouncode();

            }
        });

        proc_to_check_coupon_remove_table_lay = (TableRow) pickUpView.findViewById(R.id.proc_to_check_coupon_remove_table_lay);
        proc_to_check_coupon_txt_view = (TextView) pickUpView.findViewById(R.id.proc_to_check_coupon_txt_view);
        proc_to_check_coupon_romve_btn = (Button) pickUpView.findViewById(R.id.proc_to_check_coupon_romve_btn);
        proc_to_check_coupon_romve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveCouponRequestMethod();
            }
        });


        proc_to_check_coupon_discount_table_lay = (TableRow) pickUpView.findViewById(R.id.proc_to_check_coupon_discount_table_lay);
        proc_to_chec_coupon_disc_amt_holder_txt_view = (TextView) pickUpView.findViewById(R.id.proc_to_chec_coupon_disc_amt_holder_txt_view);
        proc_to_chec_coupon_disc_amt_txt_view = (TextView) pickUpView.findViewById(R.id.proc_to_chec_coupon_disc_amt_txt_view);


        proc_to_check_coupon_amt_pay_table_lay = (TableRow) pickUpView.findViewById(R.id.proc_to_check_coupon_amt_pay_table_lay);
        proc_to_chec_amt_pay_holder_txt_view = (TextView) pickUpView.findViewById(R.id.proc_to_chec_amt_pay_holder_txt_view);
        proc_to_chec_amt_pay_txt_view = (TextView) pickUpView.findViewById(R.id.proc_to_chec_amt_pay_txt_view);


        sub_total_txt = (TextView) pickUpView.findViewById(R.id.sub_total_txt);
        service_tax_txt = (TextView) pickUpView.findViewById(R.id.service_tax_txt);
        grand_total_txt = (TextView) pickUpView.findViewById(R.id.grand_total_txt);

        deliv_promo_code_apply_row = (TableRow) pickUpView.findViewById(R.id.deliv_promo_code_apply_row);
        deliv_valide_promo_code_table_row = (TableRow) pickUpView.findViewById(R.id.deliv_valide_promo_code_table_row);

        deliv_vali_promo_code_txt = (TextView) pickUpView.findViewById(R.id.deliv_vali_promo_code_txt);
        deliv_vali_promo_code_amt_txt = (TextView) pickUpView.findViewById(R.id.deliv_vali_promo_code_amt_txt);

        input_layout_delivery = (TextInputLayout) pickUpView.findViewById(R.id.input_Layout_Delivery);
        input_layout_promocode = (TextInputLayout) pickUpView.findViewById(R.id.input_Layout_Promo);
        InputDelivery = (EditText) pickUpView.findViewById(R.id.del_instruction);

        InputDelivery.addTextChangedListener(new MyTextWatcher(InputDelivery));
        coupon_code_edt_txt.addTextChangedListener(new MyTextWatcher(coupon_code_edt_txt));
        ed_date = pickUpView.findViewById(R.id.ed_date);
        ed_time = pickUpView.findViewById(R.id.ed_time);


        if (outletDetails.getTaxType() == 2) {
            proc_to_check_service_tax_label.setVisibility(View.GONE);
            service_tax_txt.setVisibility(View.GONE);
        } else {
            proc_to_check_service_tax_label.setVisibility(View.VISIBLE);
            proc_to_check_service_tax_label.setText(outletDetails.getTaxLabelName().trim());
            service_tax_txt.setVisibility(View.VISIBLE);
        }

        setServiceTaxAmount();
        PlatFormCharge();



        sub_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()))));




        LoadProductFromDB();
        setGrandTotalAmount();

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdatePromoCode();
            }
        });

        proceed_payment_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });


        ed_date.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            if (dpd == null) {
                dpd = DatePickerDialog.newInstance(
                        PickUpFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            } else {
                dpd.initialize(
                        PickUpFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
            }

            Calendar ThreedaysAdded = Calendar.getInstance();
            ThreedaysAdded.add(Calendar.DAY_OF_MONTH,2);


            dpd.setMinDate(now);
            dpd.setMaxDate(ThreedaysAdded);
            dpd.show(getFragmentManager(), "Datepickerdialog");

        });


        ed_time.setOnClickListener(v -> {

            if (ed_date.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.please_select_date), Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar now = Calendar.getInstance();

            if (tpd == null) {
                tpd = TimePickerDialog.newInstance(
                        PickUpFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),false);

            } else {
                tpd.initialize(
                        PickUpFragment.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        now.get(Calendar.SECOND),
                        false
                );
            }

            int hr =  now.get(Calendar.HOUR_OF_DAY);
            int min =  now.get(Calendar.MINUTE);
            int sec =  now.get(Calendar.SECOND);

            //for today date only need to set min time
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());

            if (ed_date.getText().toString().equals(date)) {
                tpd.setMinTime(hr, min, sec);
            }
            tpd.setTimeInterval(1, 15);
            tpd.show(getFragmentManager(), "Timepickerdialog");

        });



        return pickUpView;
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(++monthOfYear)+"-"+dayOfMonth;
        ed_date.setText(date);
        dpd = null;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        String secondString = second < 10 ? "0"+second : ""+second;
        String time = hourString+":"+minuteString;

        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        SimpleDateFormat convertedFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

        try {
            time= convertedFormat.format(simpleDateFormat.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ed_time.setText(time);
        tpd = null;
    }
    private Float PlatFormCharge() {
        float platformCharge = 0;

        if (outletDetails.getPlatformCharge()!=null || !outletDetails.getPlatformCharge().isEmpty()) {
            platformCharge= Float.parseFloat(outletDetails.getPlatformCharge());
            platformChargesText.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(outletDetails.getPlatformCharge()))));

        }

        outletDetails.setPlatformCharge("" + platformCharge);

        return platformCharge;
    }


    private void LoadProductFromDB() {

        delivFragProductAdapter = new DelivFragProductAdapter(getContext(), productRespository.getCartProductList());
        prod_list_recycler_view.setAdapter(delivFragProductAdapter);


    }
    private void setGrandTotalAmount() {



        if (outletDetails.getDeliveryType() == 1) {

            if (outletDetails.getTaxType() == 2) {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice())+PlatFormCharge())));
                grand_total_at = "" + (Float.parseFloat(productRespository.totalPrice()) + PlatFormCharge());


            } else {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice())+ setServiceTaxAmount()+PlatFormCharge())));
                grand_total_at = "" + (Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount()+PlatFormCharge());

            }

        } else if (outletDetails.getDeliveryType() == 2) {

            if (outletDetails.getTaxType() == 2) {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice() )+PlatFormCharge())));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice())+ setServiceTaxAmount()+PlatFormCharge());

            } else {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount()+PlatFormCharge() )));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount()+PlatFormCharge());

            }

        } else if (outletDetails.getDeliveryType() == 3) {

            if (outletDetails.getTaxType() == 2) {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(productRespository.totalPrice())+PlatFormCharge())));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice())+PlatFormCharge() );

            } else {

                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat( productRespository.totalPrice()) + setServiceTaxAmount()+PlatFormCharge())));
                grand_total_at = String.valueOf(Float.parseFloat(productRespository.totalPrice()) + setServiceTaxAmount()+PlatFormCharge());

            }

        }
    }
    private void ApplyCopouncode() {

        try {

//            Log.e("user_id", "" + prefManager.getStringValue("user_id"));
//            Log.e("getVendorID", "" + productRespository.getVendorID().get(1));
//            Log.e("user_token", "" + prefManager.getStringValue("user_token"));
//            Log.e("OutletsId", "" + outletDetails.getOutletsId());
//
//            Log.e("coupon_code_edt_txt", "" + proc_to_check_coupon_edt_txt_view.getText().toString());
//            Log.e("getGrandTotal", "" + grand_total_at);

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService promocode_service = Webdata.getRetrofit().create(APIService.class);
            promocode_service.promo_code_call("" + proc_to_check_coupon_edt_txt_view.getText().toString(),
                    "" + prefManager.getStringValue("user_id"),
                    "" + outletDetails.getOutletsId(),"" + prefManager.getStringValue("Lang_code"), "" + prefManager.getStringValue("user_token"),
                    "" + grand_total_at).enqueue(new Callback<PromoCode>() {
                @Override
                public void onResponse(Call<PromoCode> call, Response<PromoCode> response) {

                    try {

                        progressBarDialog.dismiss();

//                        Log.e("promo_code_call", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            CouponCodeSuccessMethod(response.body().getResponse());

                        } else if (response.body().getResponse().getHttpCode() == 400) {
                            Toast.makeText(getContext(), "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
//                        Log.e("DelivFrag Promo Excep", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<PromoCode> call, Throwable t) {
                    progressBarDialog.dismiss();
//                    Log.e("DelivFrag Promo onFail", "" + t.getMessage());
                }
            });

        } catch (Exception e) {
//            Log.e("ApplyPromocode", "Exception" + e.getMessage());
        }

    }
    private void CouponCodeSuccessMethod(PromoCodeResponse promoCodeResponse) {

        try {

            proc_to_check_coupon_apply_table_lay.setVisibility(View.GONE);
            proc_to_check_coupon_remove_table_lay.setVisibility(View.VISIBLE);

            proc_to_check_coupon_discount_table_lay.setVisibility(View.VISIBLE);
            proc_to_check_coupon_amt_pay_table_lay.setVisibility(View.VISIBLE);

            proc_to_check_coupon_txt_view.setText("" + promoCodeResponse.getCouponDetails().getCouponCode());



            if (promoCodeResponse.getCouponDetails().getOfferType() == 1) {

                deliv_vali_promo_code_txt.setText("" + getString(R.string.promo_code_applied_succ_txt) + " " + promoCodeResponse.getCouponDetails().getCouponCode());
                deliv_vali_promo_code_amt_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(promoCodeResponse.getCouponDetails().getOfferAmount().trim()))));

                double promo_code_amt = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferAmount());
                double total_amt = Double.parseDouble("" + grand_total_at);
                double subtract_anount = (total_amt - promo_code_amt);

                grand_total_at = "" + subtract_anount;
                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) total_amt))); // new
//                grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) subtract_anount))); // new

                proc_to_chec_coupon_disc_amt_txt_view.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) promo_code_amt)));
                proc_to_chec_amt_pay_txt_view.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) subtract_anount)));

                outletDetails.setApplyCoupon(true);
                outletDetails.setCouponAmount("" + promoCodeResponse.getCouponDetails().getOfferAmount());
                outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
                outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

            } else {

                Log.e("grand_total_at", "" + grand_total_at);
                Log.e("getOfferPercentage", "" + promoCodeResponse.getCouponDetails().getOfferPercentage());

                double grand_total = Double.parseDouble("" + grand_total_at);
                double average_value = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferPercentage());
                double subtract_anount = (grand_total * average_value) / 100;
                double final_amt = (grand_total - subtract_anount);
                grand_total_at = "" + final_amt;

//                Log.e("grand_total", "-" + Double.toString(grand_total));
//                Log.e("average_value", "-" + Double.toString(average_value));
//                Log.e("subtract_anount", "-" + Double.toString(subtract_anount));
//                Log.e("final_amt", "-" + Double.toString(final_amt));

//            grand_total_txt.setText(prefManager.getFormatCurrencyValue(String.format("%.2f", final_amt))); // old
                proc_to_chec_amt_pay_txt_view.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) grand_total))); // new


                proc_to_chec_coupon_disc_amt_txt_view.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) subtract_anount)));
                proc_to_chec_amt_pay_txt_view.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) final_amt)));

                outletDetails.setApplyCoupon(true);
                outletDetails.setCouponAmount("" + Double.toString(subtract_anount));
                outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
                outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

            }

        } catch (Exception e) {
//            Log.e("CouponCodeSuccessMethod", "" + e.getMessage().toString());
        }

    }

    private void RemoveCouponRequestMethod() {

        proc_to_check_coupon_apply_table_lay.setVisibility(View.VISIBLE);
        proc_to_check_coupon_remove_table_lay.setVisibility(View.GONE);

        proc_to_check_coupon_edt_txt_view.setText("");

        proc_to_check_coupon_discount_table_lay.setVisibility(View.GONE);
        proc_to_check_coupon_amt_pay_table_lay.setVisibility(View.GONE);


        double promo_code_amt = Double.parseDouble("" + outletDetails.getCouponAmount());
        double old_grand_total_amt = Double.parseDouble("" + grand_total_at);
        double new_grand_total_amt = (promo_code_amt + old_grand_total_amt);

        grand_total_at = "" + new_grand_total_amt;
//            grand_total_txt.setText(prefManager.getFormatCurrencyValue(String.format("%.2f", subtract_anount))); // old
        grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) new_grand_total_amt))); // new


        outletDetails.setApplyCoupon(false);
        outletDetails.setCouponAmount(null);
        outletDetails.setCouponId(null);
        outletDetails.setCouponType(null);


    }
    private float setServiceTaxAmount() {

        float serviceTax = 0;

        if (outletDetails.getTaxType() == 1) {

            serviceTax = Float.parseFloat("" + productRespository.totalPrice()) * Float.parseFloat("" + outletDetails.getTaxPercentage()) / 100;
            serviceTax = round(serviceTax, 2);
            service_tax_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(serviceTax)));
        }



        outletDetails.setServiceTax("" + serviceTax);

        return serviceTax;
    }
    public static Float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    private void submit() {

        if (ed_date.getText().toString().isEmpty()){
            Toast.makeText(getContext(), ""+getString(R.string.please_select_date), Toast.LENGTH_SHORT).show();
            return;
        }

        if (ed_time.getText().toString().isEmpty()){
            Toast.makeText(getContext(), ""+getString(R.string.please_select_time), Toast.LENGTH_SHORT).show();
            return;
        }

        if (InputDelivery.getText().toString().isEmpty()) {
            outletDetails.setDeliveryInstruction("");
        } else {
            outletDetails.setDeliveryInstruction("" + InputDelivery.getText().toString().trim());
        }


        if (!grand_total_at.equals("0")) {
            outletDetails.setGrandTotal("" + grand_total_at);
        }

        if (promo_offer_amount != 0) {
            outletDetails.setDeliveryPromoCode("" + promo_offer_amount);
        }

        outletDetails.setSubTotal("" + productRespository.totalPrice());
        outletDetails.setPaymentOption(2);
        outletDetails.setDeliveryDate(ed_date.getText().toString().trim()+" "+ed_time.getText().toString().trim());

        Intent place_order = new Intent(getContext(), PickUpProceedToPayment.class);
        place_order.putExtra("outlet_details", outletDetails);
        place_order.putExtra("vendor_id", vendor_id);
        startActivity(place_order);

    }



    // old promo code apply method
    private void UpdatePromoCode() {


//        Log.e("user_id", "" + prefManager.getStringValue("user_id"));
//        Log.e("getVendorID", "" + productRespository.getVendorID().get(1));
//        Log.e("user_token", "" + prefManager.getStringValue("user_token"));
//        Log.e("OutletsId", "" + outletDetails.getOutletsId());
//
//        Log.e("getGrandTotal", "" + grand_total_at);

        if (!validatePromo()) {
            return;
        }

        if (progressBarDialog != null) {
            progressBarDialog.show();
        }

        APIService promocode_service = Webdata.getRetrofit().create(APIService.class);
        promocode_service.promo_code_call("" + coupon_code_edt_txt.getText().toString(), "" + prefManager.getStringValue("user_id"),
                "" + outletDetails.getOutletsId(),"" + prefManager.getStringValue("Lang_code"),
                "" + prefManager.getStringValue("user_token"), "" + grand_total_at).enqueue(new Callback<PromoCode>() {
            @Override
            public void onResponse(Call<PromoCode> call, Response<PromoCode> response) {

                try {

                    progressBarDialog.dismiss();

//                    Log.e("promo_code_call", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {

                        ApplyPromoCodeSuccessMethod(response.body().getResponse());

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(getContext(), "" + response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
//                    Log.e("DelivFrag Promo Excep", "" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PromoCode> call, Throwable t) {
                progressBarDialog.dismiss();
//                Log.e("DelivFrag Promo onFail", "" + t.getMessage());
            }
        });

    }

    private void ApplyPromoCodeSuccessMethod(PromoCodeResponse promoCodeResponse) {

        deliv_promo_code_apply_row.setVisibility(View.GONE);
        deliv_valide_promo_code_table_row.setVisibility(View.VISIBLE);

        if (promoCodeResponse.getCouponDetails().getOfferType() == 1) {

            deliv_vali_promo_code_txt.setText("" + getString(R.string.promo_code_applied_succ_txt) + " " + promoCodeResponse.getCouponDetails().getCouponCode());
            deliv_vali_promo_code_amt_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues(Float.parseFloat(promoCodeResponse.getCouponDetails().getOfferAmount().trim()))));

            double promo_code_amt = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferAmount());
            double total_amt = Double.parseDouble("" + grand_total_at);
            double subtract_anount = (total_amt - promo_code_amt);

            grand_total_at = "" + subtract_anount;
            grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) subtract_anount)));

            outletDetails.setApplyCoupon(true);
            outletDetails.setCouponAmount("" + promoCodeResponse.getCouponDetails().getOfferAmount());
            outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
            outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

        } else {

//            Log.e("grand_total_at", "" + grand_total_at);
//            Log.e("getOfferPercentage", "" + promoCodeResponse.getCouponDetails().getOfferPercentage());

            double grand_total = Double.parseDouble("" + grand_total_at);
            double average_value = Double.parseDouble("" + promoCodeResponse.getCouponDetails().getOfferPercentage());
            double subtract_anount = (grand_total * average_value) / 100;
            double final_amt = (grand_total - subtract_anount);
            grand_total_at = "" + final_amt;

//            Log.e("grand_total", "-" + Double.toString(grand_total));
//            Log.e("average_value", "-" + Double.toString(average_value));
//            Log.e("subtract_anount", "-" + Double.toString(subtract_anount));
//            Log.e("final_amt", "-" + Double.toString(final_amt));

            grand_total_txt.setText(prefManager.getFormatCurrencyValue(prefManager.GetEngDecimalFormatValues((float) final_amt)));

            deliv_vali_promo_code_txt.setText("" + getString(R.string.promo_code_applied_succ_txt) + " " + promoCodeResponse.getCouponDetails().getCouponCode());
            deliv_vali_promo_code_amt_txt.setText(prefManager.getFormatCurrencyValue("" + Double.toString(subtract_anount)));

            outletDetails.setApplyCoupon(true);
            outletDetails.setCouponAmount("" + Double.toString(subtract_anount));
            outletDetails.setCouponId("" + promoCodeResponse.getCouponDetails().getCouponId());
            outletDetails.setCouponType("" + promoCodeResponse.getCouponDetails().getCouponType());

        }


    }


//    private void submit() {
//
//        if (!validateDelivery()) {
//            return;
//        }
//
//        Intent next=new Intent(getContext(), ProceedToPayment.class);
//        next.putExtra("KEY", response_result);
//        startActivity(next);
//
//    }


    private boolean validateDelivery() {

        if (InputDelivery.getText().toString().trim().isEmpty()) {
            input_layout_delivery.setError(getString(R.string.err_delivery_detail));
            return false;
        } else {
            input_layout_delivery.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePromo() {

        if (coupon_code_edt_txt.getText().toString().trim().isEmpty()) {
            input_layout_promocode.setError(getString(R.string.err_promo_detail));
            return false;
        } else {
            input_layout_promocode.setErrorEnabled(false);
        }

        return true;
    }


    private void requestFocus(View v) {
        if (v.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private final View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.del_instruction:
                    validateDelivery();
                    break;

                case R.id.coupon_code_edt_txt:
                    validatePromo();
                    break;
            }
        }
    }


}

