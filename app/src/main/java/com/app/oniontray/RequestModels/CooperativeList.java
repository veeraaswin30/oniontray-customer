package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CooperativeList {

    @SerializedName("outlets_id")
    @Expose
    private String outletsId;
    @SerializedName("outlet_name")
    @Expose
    private String outletName;

    /**
     *
     * @return
     * The outletsId
     */
    public String getOutletsId() {
        return outletsId;
    }

    /**
     *
     * @param outletsId
     * The outlets_id
     */
    public void setOutletsId(String outletsId) {
        this.outletsId = outletsId;
    }

    /**
     *
     * @return
     * The outletName
     */
    public String getOutletName() {
        return outletName;
    }

    /**
     *
     * @param outletName
     * The outlet_name
     */
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

}