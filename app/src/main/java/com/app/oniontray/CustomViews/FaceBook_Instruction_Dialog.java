package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.material.textfield.TextInputLayout;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.oniontray.CountrySpinner.Country;
import com.app.oniontray.CountrySpinner.CountryAdapter;
import com.app.oniontray.CountrySpinner.CustomPhoneNumberFormattingTextWatcher;
import com.app.oniontray.CountrySpinner.OnPhoneChangedListener;
import com.app.oniontray.CountrySpinner.PhoneUtils;
import com.app.oniontray.DotsProgressBar.DDProgressBarDialog;
import com.app.oniontray.R;
import com.app.oniontray.RequestModels.SocUserCredReqResp;
import com.app.oniontray.Utils.LoginPrefManager;
import com.app.oniontray.WebService.APIService;
import com.app.oniontray.WebService.Webdata;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nextbrain on 4/4/17.
 */

public class FaceBook_Instruction_Dialog extends Dialog {

    private Context context;
    private Bundle bundle;
    private LoginPrefManager loginPrefManager;
    private DDProgressBarDialog ddProgressBarDialog;


    private boolean error_status = false;
    private Integer error_code = 0;
    private String error_message = "";


    private TextView fb_gp_dialog_tit_txt_view;
    private Button fb_info_aleart_cancel_btn;
    private Button fb_info_aleart_ok_btn;


    private TextInputLayout inputLayoutEmail;
    private EditText fb_email_edt_txt;

    private LinearLayout input_phone_no_lay;
    private TextInputLayout fb_mobile_input_Layout;
    private EditText fb_mobile_det_txt;

    private TextView user_crede_miss_match_txt_view;


    protected String mLastEnteredPhone;
    protected Spinner fb_phone_Spinner;
    protected PhoneNumberUtil mPhoneNumberUtil = PhoneNumberUtil.getInstance();
    protected SparseArray<ArrayList<Country>> mCountriesMap = new SparseArray<ArrayList<Country>>();
    protected CountryAdapter fb_country_mAdapter;


    protected static final TreeSet<String> CANADA_CODES = new TreeSet<String>();
    protected static final TreeSet<String> US_CODES = new TreeSet<String>();
    protected static final TreeSet<String> DO_CODES = new TreeSet<String>();
    protected static final TreeSet<String> PR_CODES = new TreeSet<String>();

    static {
        //USA
        US_CODES.add("201");
        US_CODES.add("202");
        US_CODES.add("203");
        US_CODES.add("205");
        US_CODES.add("206");
        US_CODES.add("207");
        US_CODES.add("208");
        US_CODES.add("209");
        US_CODES.add("210");
        US_CODES.add("212");
        US_CODES.add("213");
        US_CODES.add("214");
        US_CODES.add("215");
        US_CODES.add("216");
        US_CODES.add("217");
        US_CODES.add("218");
        US_CODES.add("219");
        US_CODES.add("224");
        US_CODES.add("225");
        US_CODES.add("228");
        US_CODES.add("229");
        US_CODES.add("231");
        US_CODES.add("234");
        US_CODES.add("239");
        US_CODES.add("240");
        US_CODES.add("248");
        US_CODES.add("251");
        US_CODES.add("252");
        US_CODES.add("253");
        US_CODES.add("254");
        US_CODES.add("256");
        US_CODES.add("260");
        US_CODES.add("262");
        US_CODES.add("267");
        US_CODES.add("269");
        US_CODES.add("270");
        US_CODES.add("276");
        US_CODES.add("281");
        US_CODES.add("301");
        US_CODES.add("302");
        US_CODES.add("303");
        US_CODES.add("304");
        US_CODES.add("305");
        US_CODES.add("307");
        US_CODES.add("308");
        US_CODES.add("309");
        US_CODES.add("310");
        US_CODES.add("312");
        US_CODES.add("313");
        US_CODES.add("314");
        US_CODES.add("315");
        US_CODES.add("316");
        US_CODES.add("317");
        US_CODES.add("318");
        US_CODES.add("319");
        US_CODES.add("320");
        US_CODES.add("321");
        US_CODES.add("323");
        US_CODES.add("325");
        US_CODES.add("330");
        US_CODES.add("334");
        US_CODES.add("336");
        US_CODES.add("337");
        US_CODES.add("339");
        US_CODES.add("347");
        US_CODES.add("351");
        US_CODES.add("352");
        US_CODES.add("360");
        US_CODES.add("361");
        US_CODES.add("386");
        US_CODES.add("401");
        US_CODES.add("402");
        US_CODES.add("404");
        US_CODES.add("405");
        US_CODES.add("406");
        US_CODES.add("407");
        US_CODES.add("408");
        US_CODES.add("409");
        US_CODES.add("410");
        US_CODES.add("412");
        US_CODES.add("413");
        US_CODES.add("414");
        US_CODES.add("415");
        US_CODES.add("417");
        US_CODES.add("419");
        US_CODES.add("423");
        US_CODES.add("425");
        US_CODES.add("430");
        US_CODES.add("432");
        US_CODES.add("434");
        US_CODES.add("435");
        US_CODES.add("440");
        US_CODES.add("443");
        US_CODES.add("469");
        US_CODES.add("478");
        US_CODES.add("479");
        US_CODES.add("480");
        US_CODES.add("484");
        US_CODES.add("501");
        US_CODES.add("502");
        US_CODES.add("503");
        US_CODES.add("504");
        US_CODES.add("505");
        US_CODES.add("507");
        US_CODES.add("508");
        US_CODES.add("509");
        US_CODES.add("510");
        US_CODES.add("512");
        US_CODES.add("513");
        US_CODES.add("515");
        US_CODES.add("516");
        US_CODES.add("517");
        US_CODES.add("518");
        US_CODES.add("520");
        US_CODES.add("530");
        US_CODES.add("540");
        US_CODES.add("541");
        US_CODES.add("551");
        US_CODES.add("559");
        US_CODES.add("561");
        US_CODES.add("562");
        US_CODES.add("563");
        US_CODES.add("567");
        US_CODES.add("570");
        US_CODES.add("571");
        US_CODES.add("573");
        US_CODES.add("574");
        US_CODES.add("575");
        US_CODES.add("580");
        US_CODES.add("585");
        US_CODES.add("586");
        US_CODES.add("601");
        US_CODES.add("602");
        US_CODES.add("603");
        US_CODES.add("605");
        US_CODES.add("606");
        US_CODES.add("607");
        US_CODES.add("608");
        US_CODES.add("609");
        US_CODES.add("610");
        US_CODES.add("612");
        US_CODES.add("614");
        US_CODES.add("615");
        US_CODES.add("616");
        US_CODES.add("617");
        US_CODES.add("618");
        US_CODES.add("619");
        US_CODES.add("620");
        US_CODES.add("623");
        US_CODES.add("626");
        US_CODES.add("630");
        US_CODES.add("631");
        US_CODES.add("636");
        US_CODES.add("641");
        US_CODES.add("646");
        US_CODES.add("650");
        US_CODES.add("651");
        US_CODES.add("660");
        US_CODES.add("661");
        US_CODES.add("662");
        US_CODES.add("678");
        US_CODES.add("682");
        US_CODES.add("701");
        US_CODES.add("702");
        US_CODES.add("703");
        US_CODES.add("704");
        US_CODES.add("706");
        US_CODES.add("707");
        US_CODES.add("708");
        US_CODES.add("712");
        US_CODES.add("713");
        US_CODES.add("714");
        US_CODES.add("715");
        US_CODES.add("716");
        US_CODES.add("717");
        US_CODES.add("718");
        US_CODES.add("719");
        US_CODES.add("720");
        US_CODES.add("724");
        US_CODES.add("727");
        US_CODES.add("731");
        US_CODES.add("732");
        US_CODES.add("734");
        US_CODES.add("740");
        US_CODES.add("754");
        US_CODES.add("757");
        US_CODES.add("760");
        US_CODES.add("763");
        US_CODES.add("765");
        US_CODES.add("770");
        US_CODES.add("772");
        US_CODES.add("773");
        US_CODES.add("774");
        US_CODES.add("775");
        US_CODES.add("781");
        US_CODES.add("785");
        US_CODES.add("786");
        US_CODES.add("801");
        US_CODES.add("802");
        US_CODES.add("803");
        US_CODES.add("804");
        US_CODES.add("805");
        US_CODES.add("806");
        US_CODES.add("808");
        US_CODES.add("810");
        US_CODES.add("812");
        US_CODES.add("813");
        US_CODES.add("814");
        US_CODES.add("815");
        US_CODES.add("816");
        US_CODES.add("817");
        US_CODES.add("818");
        US_CODES.add("828");
        US_CODES.add("830");
        US_CODES.add("831");
        US_CODES.add("832");
        US_CODES.add("843");
        US_CODES.add("845");
        US_CODES.add("847");
        US_CODES.add("848");
        US_CODES.add("850");
        US_CODES.add("856");
        US_CODES.add("857");
        US_CODES.add("858");
        US_CODES.add("859");
        US_CODES.add("860");
        US_CODES.add("862");
        US_CODES.add("863");
        US_CODES.add("864");
        US_CODES.add("865");
        US_CODES.add("866");
        US_CODES.add("870");
        US_CODES.add("901");
        US_CODES.add("903");
        US_CODES.add("904");
        US_CODES.add("906");
        US_CODES.add("907");
        US_CODES.add("908");
        US_CODES.add("909");
        US_CODES.add("910");
        US_CODES.add("912");
        US_CODES.add("913");
        US_CODES.add("914");
        US_CODES.add("915");
        US_CODES.add("916");
        US_CODES.add("917");
        US_CODES.add("918");
        US_CODES.add("919");
        US_CODES.add("920");
        US_CODES.add("925");
        US_CODES.add("928");
        US_CODES.add("931");
        US_CODES.add("936");
        US_CODES.add("937");
        US_CODES.add("940");
        US_CODES.add("941");
        US_CODES.add("947");
        US_CODES.add("949");
        US_CODES.add("951");
        US_CODES.add("952");
        US_CODES.add("954");
        US_CODES.add("956");
        US_CODES.add("970");
        US_CODES.add("971");
        US_CODES.add("972");
        US_CODES.add("973");
        US_CODES.add("978");
        US_CODES.add("979");
        US_CODES.add("980");
        US_CODES.add("985");
        US_CODES.add("989");

        //Dominican Republic
        DO_CODES.add("809");
        DO_CODES.add("829");
        DO_CODES.add("849");

        //Puerto Rico
        PR_CODES.add("787");
        PR_CODES.add("939");

        //Canada
        CANADA_CODES.add("204");
        CANADA_CODES.add("226");
        CANADA_CODES.add("236");
        CANADA_CODES.add("249");
        CANADA_CODES.add("250");
        CANADA_CODES.add("289");
        CANADA_CODES.add("306");
        CANADA_CODES.add("343");
        CANADA_CODES.add("365");
        CANADA_CODES.add("387");
        CANADA_CODES.add("403");
        CANADA_CODES.add("416");
        CANADA_CODES.add("418");
        CANADA_CODES.add("431");
        CANADA_CODES.add("437");
        CANADA_CODES.add("438");
        CANADA_CODES.add("450");
        CANADA_CODES.add("506");
        CANADA_CODES.add("514");
        CANADA_CODES.add("519");
        CANADA_CODES.add("548");
        CANADA_CODES.add("579");
        CANADA_CODES.add("581");
        CANADA_CODES.add("587");
        CANADA_CODES.add("604");
        CANADA_CODES.add("613");
        CANADA_CODES.add("639");
        CANADA_CODES.add("647");
        CANADA_CODES.add("672");
        CANADA_CODES.add("705");
        CANADA_CODES.add("709");
        CANADA_CODES.add("742");
        CANADA_CODES.add("778");
        CANADA_CODES.add("780");
        CANADA_CODES.add("782");
        CANADA_CODES.add("807");
        CANADA_CODES.add("819");
        CANADA_CODES.add("825");
        CANADA_CODES.add("867");
        CANADA_CODES.add("873");
        CANADA_CODES.add("902");
        CANADA_CODES.add("905");
    }


    protected AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Country c = (Country) fb_phone_Spinner.getItemAtPosition(position);
            if (mLastEnteredPhone != null && mLastEnteredPhone.startsWith(c.getCountryCodeStr())) {
                return;
            }

            fb_mobile_det_txt.getText().clear();
            fb_mobile_det_txt.getText().insert(fb_mobile_det_txt.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            fb_mobile_det_txt.setSelection(fb_mobile_det_txt.length());
            mLastEnteredPhone = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    protected OnPhoneChangedListener mOnPhoneChangedListener = new OnPhoneChangedListener() {
        @Override
        public void onPhoneChanged(String phone) {
            try {
                mLastEnteredPhone = phone;
                Phonenumber.PhoneNumber p = mPhoneNumberUtil.parse(phone, null);
                ArrayList<Country> list = mCountriesMap.get(p.getCountryCode());
                Country country = null;
                if (list != null) {
                    if (p.getCountryCode() == 1) {
                        String num = String.valueOf(p.getNationalNumber());
                        if (num.length() >= 3) {
                            String code = num.substring(0, 3);
                            if (CANADA_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Canada has priority 1, US has priority 0
                                    if (c.getPriority() == 1) {
                                        country = c;
                                        break;
                                    }
                                }
                            } else if (DO_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Dominican Republic has priority 2
                                    if (c.getPriority() == 2) {
                                        country = c;
                                        break;
                                    }
                                }
                            } else if (PR_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Puerto Rico has priority 3
                                    if (c.getPriority() == 3) {
                                        country = c;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (country == null) {
                        for (Country c : list) {
                            if (c.getPriority() == 0) {
                                country = c;
                                break;
                            }
                        }
                    }
                }
                if (country != null) {
                    final int position = country.getNum();
                    fb_phone_Spinner.post(new Runnable() {
                        @Override
                        public void run() {
                            fb_phone_Spinner.setSelection(position);
                        }
                    });
                }
            } catch (NumberParseException ignore) {
            }

        }
    };


    public FaceBook_Instruction_Dialog(@NonNull Context context, Bundle bundle, boolean error_status, Integer error_code, String Message) {
        super(context);

        this.context = context;
        this.bundle = bundle;

        this.error_status = error_status;
        this.error_code = error_code;
        this.error_message = Message;

        this.loginPrefManager = new LoginPrefManager(context);
        this.ddProgressBarDialog = new DDProgressBarDialog(context);

    }

    public FaceBook_Instruction_Dialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected FaceBook_Instruction_Dialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fb_email_id_dialog_layout);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(context.getDrawable(R.drawable.fb_gp_instru_background));
        this.setCanceledOnTouchOutside(false);


        fb_gp_dialog_tit_txt_view = (TextView) findViewById(R.id.fb_gp_dialog_tit_txt_view);

        SetDialogTitle();

//        if (bundle.getString("email_id").isEmpty()) {
//            fb_gp_dialog_tit_txt_view.setText(context.getString(R.string.fb_login_email_phone_desc_txt));
//        } else {
//            fb_gp_dialog_tit_txt_view.setText(context.getString(R.string.fb_login_phone_desc_txt));
//        }

        fb_phone_Spinner = (Spinner) findViewById(R.id.fb_phone_Spinner);

        input_phone_no_lay = (LinearLayout) findViewById(R.id.input_phone_no_lay);
        fb_mobile_input_Layout = (TextInputLayout) findViewById(R.id.fb_mobile_input_Layout);
        fb_mobile_det_txt = (EditText) findViewById(R.id.fb_mobile_det_txt);
        fb_mobile_det_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateMobile();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        fb_country_mAdapter = new CountryAdapter(context);
        fb_phone_Spinner.setAdapter(fb_country_mAdapter);
        fb_phone_Spinner.setOnItemSelectedListener(mOnItemSelectedListener);

        initCodes(context);

        fb_mobile_det_txt.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart > 0 && !Character.isDigit(c)) {
                        return "";
                    }
                }
                return null;
            }
        };

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_email);
        fb_email_edt_txt = (EditText) findViewById(R.id.input_email_id);
        LoadEmailTextChangeListener();


        user_crede_miss_match_txt_view = (TextView) findViewById(R.id.user_crede_miss_match_txt_view);


        fb_info_aleart_cancel_btn = (Button) findViewById(R.id.fb_info_aleart_cancel_btn);
        fb_info_aleart_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fb_login_dialog_interface != null) {
                    fb_login_dialog_interface.FB_Login_Cancel_Btn_Method();
                }
            }
        });


        fb_info_aleart_ok_btn = (Button) findViewById(R.id.fb_info_aleart_ok_btn);
        fb_info_aleart_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (error_code == 0) {

                    if (bundle.getString("email_id").isEmpty()) {
                        ValidateEmailAndMobile();
                    } else {
                        ValidateMobile();
                    }

                } else if (error_code == 2) {

                    ValidateEmailAndMobile();

                } else if (error_code == 3) {

                    ValidateEmail();

                } else if (error_code == 4) {

                    ValidateMobile();

                }

            }
        });


        if (error_code == 0) {

            if (bundle.getString("email_id").isEmpty()) {

                inputLayoutEmail.setVisibility(View.VISIBLE);
                input_phone_no_lay.setVisibility(View.VISIBLE);

            } else {

                inputLayoutEmail.setVisibility(View.GONE);
                input_phone_no_lay.setVisibility(View.VISIBLE);

            }

        } else if (error_code == 2) {

            inputLayoutEmail.setVisibility(View.VISIBLE);
            input_phone_no_lay.setVisibility(View.VISIBLE);

        } else if (error_code == 3) {

            inputLayoutEmail.setVisibility(View.VISIBLE);
            input_phone_no_lay.setVisibility(View.GONE);

        } else if (error_code == 4) {

            inputLayoutEmail.setVisibility(View.GONE);
            input_phone_no_lay.setVisibility(View.VISIBLE);

        }

        if (error_status) {

            user_crede_miss_match_txt_view.setText(error_message);

//            if (error_code == 2) {
//
//                fb_email_edt_txt.setText(bundle.getString("email_id"));
//                String mobile_no = "" + bundle.getString("phone_no").replaceAll("\\s+", "");
//                fb_mobile_det_txt.setText("" + mobile_no);
//                user_crede_miss_match_txt_view.setText(error_message);
//
//            } else if (error_code == 3) {
//
//                fb_email_edt_txt.setText(bundle.getString("email_id"));
//                String mobile_no = "" + bundle.getString("phone_no").replaceAll("\\s+", "");
//                fb_mobile_det_txt.setText("" + mobile_no);
//                user_crede_miss_match_txt_view.setText(error_message);
//
//            } else if (error_code == 4) {
//
//                fb_email_edt_txt.setText(bundle.getString("email_id"));
//                String mobile_no = "" + bundle.getString("phone_no").replaceAll("\\s+", "");
//                fb_mobile_det_txt.setText("" + mobile_no);
//                user_crede_miss_match_txt_view.setText(error_message);
//
//            }

//            fb_email_edt_txt.setText(bundle.getString("email_id"));
//            String mobile_no = "" + bundle.getString("phone_no").replaceAll("\\s+", "");
//            fb_mobile_det_txt.setText("" + mobile_no);
//            user_crede_miss_match_txt_view.setText(error_message);
        } else {
            user_crede_miss_match_txt_view.setVisibility(View.GONE);
        }


    }

    private void SetDialogTitle() {

        if (bundle.getString("email_id").isEmpty()) {

            fb_gp_dialog_tit_txt_view.setText(context.getString(R.string.fb_login_email_phone_desc_txt));

            if (error_code == 3) {
                fb_gp_dialog_tit_txt_view.setText(context.getString(R.string.fb_login_email_desc_txt));
            }

        } else {
            fb_gp_dialog_tit_txt_view.setText(context.getString(R.string.fb_login_phone_desc_txt));
        }

    }

    private void LoadEmailTextChangeListener() {

        if (bundle.getString("email_id").isEmpty()) {

            inputLayoutEmail.setVisibility(View.VISIBLE);

            fb_email_edt_txt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (fb_email_edt_txt.getText().toString().isEmpty()) {
                        inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
                    } else if (!isValidEmail(fb_email_edt_txt.getText().toString())) {
                        inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
                    } else {
                        inputLayoutEmail.setErrorEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            inputLayoutEmail.setVisibility(View.GONE);
        }

    }


    private void ValidateEmailAndMobile() {

        String email_id_value = "";


        if (bundle.getString("email_id").isEmpty()) {

            String email = fb_email_edt_txt.getText().toString();

            if (email.isEmpty()) {
                inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
                return;
            } else if (!isValidEmail(email)) {
                inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
                return;
            } else {
                inputLayoutEmail.setEnabled(false);

                email_id_value = fb_email_edt_txt.getText().toString();
            }

        } else {

            email_id_value = bundle.getString("email_id");

        }

        if (!validateMobileNumber()) {
            return;
        }

        Log.e("id", "-" + bundle.getString("id"));
        Log.e("email_id", "-" + email_id_value);
        Log.e("phone_no", "-" + fb_mobile_det_txt.getText().toString().replaceAll("\\s+", ""));


        Bundle fb_bundle = new Bundle();
        fb_bundle.putString("id", "" + bundle.getString("id"));
        fb_bundle.putString("email_id", "" + email_id_value);
        fb_bundle.putString("name", "" + bundle.getString("name"));
        fb_bundle.putString("firstName", "" + bundle.getString("firstName"));
        fb_bundle.putString("lastName", "" + bundle.getString("lastName"));
        fb_bundle.putString("gender", "" + bundle.getString("gender"));
        fb_bundle.putString("piictureURL", "" + bundle.getString("piictureURL"));
        fb_bundle.putString("phone_no", "" + fb_mobile_det_txt.getText().toString().replaceAll("\\s+", ""));

        CheckFaceBookUserCredientials(fb_bundle);

//        if (fb_login_dialog_interface != null) {
//
//            Bundle fb_bundle = new Bundle();
//            fb_bundle.putString("id", "" + bundle.getString("id"));
//            fb_bundle.putString("email_id", "" + email_id_value);
//            fb_bundle.putString("name", "" + bundle.getString("name"));
//            fb_bundle.putString("firstName", "" + bundle.getString("firstName"));
//            fb_bundle.putString("lastName", "" + bundle.getString("lastName"));
//            fb_bundle.putString("gender", "" + bundle.getString("gender"));
//            fb_bundle.putString("piictureURL", "" + bundle.getString("piictureURL"));
//            fb_bundle.putString("phone_no", "" + fb_mobile_det_txt.getText().toString());
//
//            fb_login_dialog_interface.FB_Ok_Button_Method(fb_bundle);
//
//        }


    }

    private void ValidateEmail() {

        String email_id_value = "";


        String email = fb_email_edt_txt.getText().toString();

        if (email.isEmpty()) {
            inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
            return;
        } else if (!isValidEmail(email)) {
            inputLayoutEmail.setError(context.getString(R.string.err_msg_email));
            return;
        } else {
            inputLayoutEmail.setEnabled(false);

            email_id_value = fb_email_edt_txt.getText().toString();
        }


        Log.e("id", "-" + bundle.getString("id"));
        Log.e("email_id", "-" + email_id_value);
        Log.e("phone_no", "-" + fb_mobile_det_txt.getText().toString());


        Bundle fb_bundle = new Bundle();
        fb_bundle.putString("id", "" + bundle.getString("id"));
        fb_bundle.putString("email_id", "" + email_id_value);
        fb_bundle.putString("name", "" + bundle.getString("name"));
        fb_bundle.putString("firstName", "" + bundle.getString("firstName"));
        fb_bundle.putString("lastName", "" + bundle.getString("lastName"));
        fb_bundle.putString("gender", "" + bundle.getString("gender"));
        fb_bundle.putString("piictureURL", "" + bundle.getString("piictureURL"));
        fb_bundle.putString("phone_no", "" + bundle.getString("phone_no"));

        CheckFaceBookUserCredientials(fb_bundle);

    }

    private void ValidateMobile() {

        if (!validateMobileNumber()) {
            return;
        }

        Log.e("ValidateMobile", "-" + fb_mobile_det_txt.getText().toString().replaceAll("\\s+", ""));

        Bundle fb_bundle = new Bundle();
        fb_bundle.putString("id", "" + bundle.getString("id"));
        fb_bundle.putString("email_id", "" + bundle.getString("email_id"));
        fb_bundle.putString("name", "" + bundle.getString("name"));
        fb_bundle.putString("firstName", "" + bundle.getString("firstName"));
        fb_bundle.putString("lastName", "" + bundle.getString("lastName"));
        fb_bundle.putString("gender", "" + bundle.getString("gender"));
        fb_bundle.putString("piictureURL", "" + bundle.getString("piictureURL"));
        fb_bundle.putString("phone_no", "" + fb_mobile_det_txt.getText().toString().replaceAll("\\s+", ""));

        CheckFaceBookUserCredientials(fb_bundle);

    }


    private void CheckFaceBookUserCredientials(final Bundle fb_bundle) {

        try {

            if (ddProgressBarDialog != null) {
                ddProgressBarDialog.show();
            }

            APIService apiService = Webdata.getRetrofit().create(APIService.class);
            apiService.CheckSocialUserCredientialsReqMethod("" + loginPrefManager.getStringValue("Lang_code"),
                    "" + context.getString(R.string.user_type_fb), "" + fb_bundle.getString("email_id"),
                    "" + fb_bundle.getString("phone_no")).enqueue(new Callback<SocUserCredReqResp>() {
                @Override
                public void onResponse(Call<SocUserCredReqResp> call, Response<SocUserCredReqResp> response) {

                    try {

                        ddProgressBarDialog.dismiss();

                        Log.e("CheckSocUserCredReqMeth", "" + response.raw().toString());

                        if (response.body().getResponse().getHttpCode() == 200) {

                            if (fb_login_dialog_interface != null) {

                                fb_login_dialog_interface.FB_Ok_Button_Method(fb_bundle);

                            }

//                                Log.e("Response 400", "-" + response.body().getResponse().getMessage());
//
//                                if (fb_login_dialog_interface != null) {
//                                    fb_login_dialog_interface.FB_Verify_Email_Phone_Error_Method(fb_bundle, "" + response.body().getResponse().getMessage());
//                                }

//                                user_crede_miss_match_txt_view.setText(response.body().getResponse().getMessage());
//                                user_crede_miss_match_txt_view.setVisibility(View.VISIBLE);
//
//                                getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        } else {

                            Log.e("Response 400", "-" + response.body().getResponse().getMessage());
                            Toast.makeText(context, response.body().getResponse().getMessage(), Toast.LENGTH_SHORT).show();
//                            if (fb_login_dialog_interface != null) {
//                                fb_login_dialog_interface.FB_Verify_Email_Phone_Error_Method(fb_bundle, "" + response.body().getResponse().getMessage());
//                            }

                            if (response.body().getResponse().getCode() == 2) {

                                fb_bundle.putString("email_id", "");
                                fb_bundle.putString("phone_no", "");


                                if (fb_login_dialog_interface != null) {
                                    fb_login_dialog_interface.FB_Verify_Email_Phone_Error_Method(fb_bundle, 2, "" + response.body().getResponse().getMessage());
                                }


                            } else if (response.body().getResponse().getCode() == 3) {

                                fb_bundle.putString("email_id", "");

//                                SetDialogTitle();
//                                LoadEmailTextChangeListener();

                                if (fb_login_dialog_interface != null) {
                                    fb_login_dialog_interface.FB_Verify_Email_Phone_Error_Method(fb_bundle, 3, "" + response.body().getResponse().getMessage());
                                }


                            } else if (response.body().getResponse().getCode() == 4) {

                                fb_bundle.putString("phone_no", "");

                                if (fb_login_dialog_interface != null) {
                                    fb_login_dialog_interface.FB_Verify_Email_Phone_Error_Method(fb_bundle, 4, "" + response.body().getResponse().getMessage());
                                }

//                                user_crede_miss_match_txt_view.setText(response.body().getResponse().getMessage());
//                                user_crede_miss_match_txt_view.setVisibility(View.VISIBLE);

                            }

                        }

                    } catch (Exception e) {
                        Log.e("onResponse", "" + e.getMessage());
                        ddProgressBarDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<SocUserCredReqResp> call, Throwable t) {
                    ddProgressBarDialog.dismiss();
                    Log.e("onFailure", "" + t.getMessage());
                }
            });

        } catch (Exception e) {
            ddProgressBarDialog.dismiss();
            Log.e("CheckFaceBookUserCredi", "" + e.getMessage());
        }

    }


    protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
    }

    protected class AsyncPhoneInitTask extends AsyncTask<Void, Void, ArrayList<Country>> {

        private int mSpinnerPosition = 181;
        private Context mContext;

        public AsyncPhoneInitTask(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<Country> doInBackground(Void... params) {
            ArrayList<Country> data = new ArrayList<Country>(233);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(mContext.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

                // do reading, usually loop until end of file reading
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    //process line
                    Country c = new Country(mContext, line, i);
                    data.add(c);
                    ArrayList<Country> list = mCountriesMap.get(c.getCountryCode());
                    if (list == null) {
                        list = new ArrayList<Country>();
                        mCountriesMap.put(c.getCountryCode(), list);
                    }
                    list.add(c);
                    i++;
                }
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }
            if (!TextUtils.isEmpty(fb_mobile_det_txt.getText())) {
                return data;
            }
            String countryRegion = PhoneUtils.getCountryRegionFromPhone(mContext);
            int code = mPhoneNumberUtil.getCountryCodeForRegion(countryRegion);
            ArrayList<Country> list = mCountriesMap.get(code);
            if (list != null) {
                for (Country c : list) {
                    if (c.getPriority() == 0) {
                        mSpinnerPosition = c.getNum();
                        break;
                    }
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Country> data) {
            fb_country_mAdapter.addAll(data);
            if (mSpinnerPosition > 0) {
                fb_phone_Spinner.setSelection(mSpinnerPosition);
            }
        }
    }


    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateMobileNumber() {

        if (fb_mobile_det_txt.getText().toString().trim().length() < 7) {
            fb_mobile_input_Layout.setError(context.getString(R.string.err_msg_mobile));
            requestFocus(fb_mobile_det_txt);
            return false;
        } else {
            fb_mobile_input_Layout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {

        fb_mobile_input_Layout.setError(null);

        String phone = validate();

        if (phone == null) {
            requestFocus(fb_mobile_det_txt);
            fb_mobile_input_Layout.setError(context.getString(R.string.label_error_incorrect_phone));
            return false;
        } else {
            fb_mobile_input_Layout.setErrorEnabled(false);
        }

        return true;
    }

    protected String validate() {

        String region = null;
        String phone = null;

        if (mLastEnteredPhone != null) {
            try {
                Phonenumber.PhoneNumber p = mPhoneNumberUtil.parse(mLastEnteredPhone, null);
                StringBuilder sb = new StringBuilder(16);
                sb.append('+').append(p.getCountryCode()).append(p.getNationalNumber());
                phone = sb.toString();
                region = mPhoneNumberUtil.getRegionCodeForNumber(p);
            } catch (NumberParseException ignore) {
            }
        }

        if (region != null) {
            return phone;
        } else {
            return null;
        }

    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public FB_Login_Dialog_Interface fb_login_dialog_interface;

    public void FB_Login_Call_Back_Method(FB_Login_Dialog_Interface fb_login_dialog_interface) {
        this.fb_login_dialog_interface = fb_login_dialog_interface;
    }

    public interface FB_Login_Dialog_Interface {

        void FB_Login_Cancel_Btn_Method();

        void FB_Ok_Button_Method(Bundle bundle);

        void FB_Verify_Email_Phone_Error_Method(Bundle bundle, Integer Error_Code, String error_msg);

    }


}
