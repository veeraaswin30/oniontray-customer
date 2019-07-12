package com.app.oniontray.CustomViews;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.oniontray.R;
import com.app.oniontray.Utils.NetworkStatus;



public class MyOrderInvoiceDialog extends Dialog {

    private Context context;
    private String invoice_url;

    private ImageView my_ord_invoice_close_btn;

    private WebView my_ord_dialog_invoice_web_view;

//    private PDFView invoicePdfView;

    public MyOrderInvoiceDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public MyOrderInvoiceDialog(Context context, String invoice_url) {
        super(context);
        this.context = context;
        this.invoice_url = invoice_url;
    }

    protected MyOrderInvoiceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_order_invoice_dialog);
        this.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        this.setCanceledOnTouchOutside(false);

        my_ord_invoice_close_btn = (ImageView) findViewById(R.id.my_ord_invoice_close_btn);
        my_ord_invoice_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        my_ord_dialog_invoice_web_view = (WebView) findViewById(R.id.my_ord_dialog_invoice_web_view);

        my_ord_dialog_invoice_web_view.setBackgroundColor(Color.TRANSPARENT);
        my_ord_dialog_invoice_web_view.setWebViewClient(new WebViewClient());
        WebSettings settings = my_ord_dialog_invoice_web_view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);

        my_ord_dialog_invoice_web_view.getSettings().setPluginState(WebSettings.PluginState.ON);
        my_ord_dialog_invoice_web_view.getSettings().setAllowFileAccess(true);

//        https://drive.google.com/viewerng/viewer?embedded=true&url=http://food.oddappz.com/assets/admin/base/images/invoice/INV000000501495868885.pdf

        if (!NetworkStatus.isConnectingToInternet(context)) {
            Toast.makeText(context, "" + context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }


//        http://docs.google.com/gview?&embedded=true&url=http://192.168.0.180:1011/assets/front/tijik/images/invoice/INV000006061495871929.pdf



        String myPdfUrl = "" + invoice_url;
        String url = "http://docs.google.com/gview?&embedded=true&url=" + myPdfUrl;
        my_ord_dialog_invoice_web_view.getSettings().setJavaScriptEnabled(true);

        Log.e("Invoice url :","" + url);
        my_ord_dialog_invoice_web_view.loadUrl("" + url);


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
