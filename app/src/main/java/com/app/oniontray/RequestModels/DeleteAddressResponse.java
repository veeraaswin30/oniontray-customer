package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DeleteAddressResponse {

    @SerializedName("httpCode")
    @Expose
    private int httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("card_detail")
    @Expose
    private CardDetail cardDetail;

    /**
     *
     * @return
     * The httpCode
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     *
     * @param httpCode
     * The httpCode
     */
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The Message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     * The cardDetail
     */
    public CardDetail getCardDetail() {
        return cardDetail;
    }

    /**
     *
     * @param cardDetail
     * The card_detail
     */
    public void setCardDetail(CardDetail cardDetail) {
        this.cardDetail = cardDetail;
    }

}