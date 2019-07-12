package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class SettOffMod {

    @SerializedName("module_name")
    @Expose
    private String module_name;
    @SerializedName("active_status")
    @Expose
    private String active_status;

    /**
     *
     * @return
     * The module_name
     */
    public String getModuleName() {
        return module_name;
    }

    /**
     *
     * @param module_name
     * The module_name
     */
    public void setModuleName(String module_name) {
        this.module_name = module_name;
    }

    /**
     *
     * @return
     * The active_status
     */
    public String getActiveStatus() {
        return active_status;
    }

    /**
     *
     * @param active_status
     * The active_status
     */
    public void setActiveStatus(String active_status) {
        this.active_status = active_status;
    }



}
