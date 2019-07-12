
package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditWalletReq {

    @SerializedName("response")
    @Expose
    private AddWalletRes response;

    public AddWalletRes getResponse() {
        return response;
    }

    public void setResponse(AddWalletRes response) {
        this.response = response;
    }

}
