package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThemeList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("theme_color")
    @Expose
    private String themeColor;
    @SerializedName("font_color")
    @Expose
    private String fontColor;
    @SerializedName("status_bar_color")
    @Expose
    private String statusBarColor;


    @SerializedName("tool_bar_icon")
    @Expose
    private String toolBarIcon;
    @SerializedName("color_acceent")
    @Expose
    private String colorAcceent;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getStatusBarColor() {
        return statusBarColor;
    }

    public void setStatusBarColor(String statusBarColor) {
        this.statusBarColor = statusBarColor;
    }

    public String getColorAcceent() {
        return colorAcceent;
    }

    public void setColorAcceent(String colorAcceent) {
        this.colorAcceent = colorAcceent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public String getToolBarIcon() {
        return toolBarIcon;
    }

    public void setToolBarIcon(String toolBarIcon) {
        this.toolBarIcon = toolBarIcon;
    }


    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
