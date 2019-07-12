package com.app.oniontray.Interface;

import com.app.oniontray.RequestModels.PayFortData;

/**
 * Created by nextbrain on 2/22/2017.
 */

public interface IPaymentRequestCallBack {
    void onPaymentRequestResponse(int responseType, PayFortData responseData);
}
