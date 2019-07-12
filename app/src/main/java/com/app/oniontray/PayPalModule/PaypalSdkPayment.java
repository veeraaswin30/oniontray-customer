package com.app.oniontray.PayPalModule;

import java.io.Serializable;



public class PaypalSdkPayment implements Serializable {

//    {
//        "client": {
//        "environment": "sandbox",
//                "paypal_sdk_version": "2.15.0",
//                "platform": "Android",
//                "product_name": "PayPal-Android-SDK"
//    },
//        "response": {
//        "create_time": "2016-12-20T15:13:33Z",
//                "id": "PAY-07V81572GT937941CLBMUVFA",
//                "intent": "sale",
//                "state": "approved"
//    },
//        "response_type": "payment"
//    }

    private String environment = "";
    private String paypal_sdk_version = "";
    private String platform = "";
    private String product_name = "";
    private String create_time = "";
    private String id = "";
    private String intent = "";
    private String state = "";
    private String response_type = "";

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }


    public String getPaypalSdkVersion() {
        return paypal_sdk_version;
    }

    public void setPaypalSdkVersion(String paypal_sdk_version) {
        this.paypal_sdk_version = paypal_sdk_version;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getProductName() {
        return product_name;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }


    public String getCreateTime() {
        return create_time;
    }

    public void setCreateTime(String create_time) {
        this.create_time = create_time;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getResponseType() {
        return response_type;
    }

    public void setResponseType(String response_type) {
        this.response_type = response_type;
    }



}
