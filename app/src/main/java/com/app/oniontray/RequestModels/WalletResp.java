
package com.app.oniontray.RequestModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletResp {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("credit_wallet_list")
    @Expose
    private List<CreditWalletData> creditWalletDatas = null;
    @SerializedName("debit_wallet_list")
    @Expose
    private List<DebitWalletData> debitWalletDatas = null;
    @SerializedName("user_wallet_amount")
    @Expose
    private String userWalletAmount;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CreditWalletData> getCreditWalletList() {
        return creditWalletDatas;
    }

    public void setCreditWalletList(List<CreditWalletData> creditWalletList) {
        this.creditWalletDatas = creditWalletList;
    }

    public List<DebitWalletData> getDebitWalletList() {
        return debitWalletDatas;
    }

    public void setDebitWalletList(List<DebitWalletData> debitWalletList) {
        this.debitWalletDatas = debitWalletList;
    }

    public String getUserWalletAmount() {
        return userWalletAmount;
    }

    public void setUserWalletAmount(String userWalletAmount) {
        this.userWalletAmount = userWalletAmount;
    }

}
