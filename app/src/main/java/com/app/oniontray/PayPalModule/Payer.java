
package com.app.oniontray.PayPalModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payer implements Serializable {

    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("payer_info")
    @Expose
    private PayerInfo payerInfo;
    @SerializedName("use_vendor_currency_conversion")
    @Expose
    private Boolean useVendorCurrencyConversion;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PayerInfo getPayerInfo() {
        return payerInfo;
    }

    public void setPayerInfo(PayerInfo payerInfo) {
        this.payerInfo = payerInfo;
    }

    public Boolean getUseVendorCurrencyConversion() {
        return useVendorCurrencyConversion;
    }

    public void setUseVendorCurrencyConversion(Boolean useVendorCurrencyConversion) {
        this.useVendorCurrencyConversion = useVendorCurrencyConversion;
    }

}
