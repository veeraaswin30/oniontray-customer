package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.oniontray.R;

/**
 * Created by NEXTBRAIN on 3/12/2017.
 */

public class MyWalletAddMoneyDialog extends Dialog {

    private Context context;

    private ImageView my_wall_add_money_dia_close_btn;

    private Button my_wall_add_money_ok_btn;
    private Button my_wall_add_money_dia_debt_btn;
    private Button my_wall_add_money_dia_credit_btn;

    private TextInputLayout my_wall_amount_txt_view_in_lay;
    private EditText my_wall_amount_edt_txt_view;


    public MyWalletAddMoneyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyWalletAddMoneyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MyWalletAddMoneyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_money_dialog_layout);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        Init();

    }

    private void Init() {

        my_wall_add_money_dia_close_btn = (ImageView) findViewById(R.id.my_wall_add_money_dia_close_btn);

        my_wall_add_money_ok_btn = (Button) findViewById(R.id.my_wall_add_money_ok_btn);

        my_wall_add_money_dia_debt_btn = (Button) findViewById(R.id.my_wall_add_money_dia_debt_btn);
        my_wall_add_money_dia_credit_btn = (Button) findViewById(R.id.my_wall_add_money_dia_credit_btn);


        my_wall_amount_txt_view_in_lay = (TextInputLayout) findViewById(R.id.my_wall_amount_txt_view_in_lay);
        my_wall_amount_edt_txt_view = (EditText) findViewById(R.id.my_wall_amount_edt_txt_view);
        my_wall_amount_edt_txt_view.addTextChangedListener(new MyProfTextWatcher(my_wall_amount_edt_txt_view));


        DialogButtonClickEvents();

    }

    private void DialogButtonClickEvents() {

        my_wall_add_money_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMoneyOkBtnEvent();
            }
        });

        my_wall_add_money_dia_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        my_wall_add_money_dia_debt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addMoneyDialogInterface != null) {
                    addMoneyDialogInterface.DebitCardBtnClickEventMethod();
                }

            }
        });

        my_wall_add_money_dia_credit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (addMoneyDialogInterface != null) {
                    addMoneyDialogInterface.CreditCardBtnClickEventMethod();
                }

            }
        });

    }


    private void AddMoneyOkBtnEvent() {

        if (!ValidateAmount()) {
            return;
        }

        if (addMoneyDialogInterface != null) {
            dismiss();
            addMoneyDialogInterface.OKClickEventMethod(my_wall_amount_edt_txt_view.getText().toString());
        }

    }


    private boolean ValidateAmount() {

        if (my_wall_amount_edt_txt_view.getText().toString().trim().isEmpty()) {
            my_wall_amount_txt_view_in_lay.setError(context.getString(R.string.my_wallet_amount_err_msg_txt));
            requestFocus(my_wall_amount_edt_txt_view);
            return false;
        } else if (my_wall_amount_edt_txt_view.getText().toString().equals("0")) {
            my_wall_amount_txt_view_in_lay.setError(context.getString(R.string.my_wallet_amount_err_msg_txt));
            requestFocus(my_wall_amount_edt_txt_view);
            return false;
        } else {
            my_wall_amount_txt_view_in_lay.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }



    private class MyProfTextWatcher implements TextWatcher {

        private final View view;

        private MyProfTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.my_wall_amount_edt_txt_view:
                    ValidateAmount();
                    break;
            }
        }

    }



    public AddMoneyDialogInterface addMoneyDialogInterface;

    public void AddMoneyDialogInteface(AddMoneyDialogInterface addMoneyDialogInterface) {
        this.addMoneyDialogInterface = addMoneyDialogInterface;
    }

    public interface AddMoneyDialogInterface {

        void OKClickEventMethod(String amount);

        void CreditCardBtnClickEventMethod();

        void DebitCardBtnClickEventMethod();
    }


}
