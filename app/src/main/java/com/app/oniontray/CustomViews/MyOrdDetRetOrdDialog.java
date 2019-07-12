package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.MobReturnReason;
import com.app.oniontray.RequestModels.MyOrdRetOrdRes;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyOrdDetRetOrdDialog extends Dialog {

    private Context context;

    private DDProgressBarDialog progressBarDialog;
    private LoginPrefManager loginPrefManager;

    private Spinner my_ord_det_return_rec_spinner;

    private TextInputLayout my_ord_det_ret_ord_comm_txt_in_lay;
    private EditText my_ord_det_ret_ord_dia_comm_edt_txt;

    private ArrayAdapter<MobReturnReason> mobReturnReasonArrayAdapter;
    private List<MobReturnReason> mobRetReas;
    private String OrderId = "";
    private String Vender_name = "";
    private String Reason_ID = "";


    public MyOrdDetRetOrdDialog(Context context, List<MobReturnReason> mobReturnReasons, String order_id, String vender_name) {
        super(context);
        this.context = context;
        this.mobRetReas = mobReturnReasons;
        this.OrderId = order_id;
        this.Vender_name = vender_name;
    }

    public MyOrdDetRetOrdDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyOrdDetRetOrdDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_ord_det_ret_ord_dialog_layout);

        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.setCanceledOnTouchOutside(false);

        loginPrefManager = new LoginPrefManager(context);
        progressBarDialog = new DDProgressBarDialog(getContext());


        my_ord_det_return_rec_spinner = (Spinner) findViewById(R.id.my_ord_det_return_rec_spinner);
        ViewCompat.setBackgroundTintList(my_ord_det_return_rec_spinner, ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor())));
        ReturnReasonSpinnerMethod();

        my_ord_det_ret_ord_comm_txt_in_lay = (TextInputLayout) findViewById(R.id.my_ord_det_ret_ord_comm_txt_in_lay);
        my_ord_det_ret_ord_dia_comm_edt_txt = (EditText) findViewById(R.id.my_ord_det_ret_ord_dia_comm_edt_txt);

        Button my_ord_det_ret_ord_dia_sub_btn = (Button) findViewById(R.id.my_ord_det_ret_ord_dia_sub_btn);
        my_ord_det_ret_ord_dia_sub_btn.setBackgroundColor(Color.parseColor(loginPrefManager.getThemeColor()));
        my_ord_det_ret_ord_dia_sub_btn.setTextColor(Color.parseColor(loginPrefManager.getThemeFontColor()));


        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor(loginPrefManager.getThemeColor()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            my_ord_det_ret_ord_comm_txt_in_lay.setBackgroundTintList(colorStateList);
        }


        my_ord_det_ret_ord_comm_txt_in_lay.setDefaultHintTextColor(colorStateList);


        my_ord_det_ret_ord_comm_txt_in_lay.setErrorTextColor(colorStateList);





        my_ord_det_ret_ord_dia_sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReturmOrderReasonRequMethod();
            }
        });


    }

    private void ReturnReasonSpinnerMethod() {

        mobReturnReasonArrayAdapter = new ArrayAdapter<MobReturnReason>(context, android.R.layout.simple_spinner_dropdown_item, mobRetReas) {

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(mobRetReas.get(position).getName());
                text.setTextAppearance(context, android.R.attr.textAppearanceSmall);
//                text.setTextSize(20);
                text.setTextColor(Color.BLACK);
                return view;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setText(mobRetReas.get(position).getName());
                text.setTextAppearance(context, android.R.attr.textAppearanceSmall);
//                text.setTextSize(20);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        my_ord_det_return_rec_spinner.setAdapter(mobReturnReasonArrayAdapter);


    }

    private void ReturmOrderReasonRequMethod() {

        if (mobReturnReasonArrayAdapter == null && mobReturnReasonArrayAdapter.getCount() == 0) {
            Toast.makeText(context, "" + getContext().getString(R.string.my_ord_det_ret_dia_spin_err_msg), Toast.LENGTH_LONG).show();
        } else {
            Reason_ID = "" + mobReturnReasonArrayAdapter.getItem(my_ord_det_return_rec_spinner.getSelectedItemPosition()).getId();
            Log.e("Reason_ID", "-" + Reason_ID);
        }

        if (!validateComments()) {
            return;
        }

        try {

            if (progressBarDialog != null) {
                progressBarDialog.show();
            }

            APIService ret_order_det_api = Webdata.getRetrofit().create(APIService.class);
            ret_order_det_api.return_order_call("" + loginPrefManager.getStringValue("user_id"), "" + OrderId,
                    "" + loginPrefManager.getStringValue("user_token"), "" + loginPrefManager.getStringValue("Lang_code"),
                    "" + Reason_ID, "" + my_ord_det_ret_ord_dia_comm_edt_txt.getText().toString(),
                    "" + Vender_name).enqueue(new Callback<MyOrdRetOrdRes>() {
                @Override
                public void onResponse(Call<MyOrdRetOrdRes> call, Response<MyOrdRetOrdRes> response) {
                    progressBarDialog.dismiss();

                    Log.e("return_order_call", "" + response.raw().toString());

                    if (response.body().getResponse().getHttpCode() == 200) {
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();

                        if(myorderReloadInterface != null){
                            myorderReloadInterface.ReloadOrderDetatils();
                        }

                        dismiss();

                    } else if (response.body().getResponse().getHttpCode() == 400) {
                        Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<MyOrdRetOrdRes> call, Throwable t) {
                    progressBarDialog.dismiss();
                    Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            });

        } catch (Exception e) {
            progressBarDialog.dismiss();
            Log.e("Exception", "" + e.getMessage());
        }


    }

    private boolean validateComments() {
        if (my_ord_det_ret_ord_dia_comm_edt_txt.getText().toString().trim().isEmpty()) {
            my_ord_det_ret_ord_comm_txt_in_lay.setError(context.getString(R.string.my_ord_det_comments_err_msg_txt));
            requestFocus(my_ord_det_ret_ord_dia_comm_edt_txt);
            return false;
        } else {
            my_ord_det_ret_ord_comm_txt_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public interface MyorderReloadInterface{
        void ReloadOrderDetatils();
    }

    private MyorderReloadInterface myorderReloadInterface;

    public void MyOrderinterfaceCallMethod(MyorderReloadInterface myorderReloadInterface){
        this.myorderReloadInterface = myorderReloadInterface;
    }

}
