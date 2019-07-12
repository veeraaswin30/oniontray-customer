package com.app.oniontray.RequestModels;


        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class MobTrackingResult {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("process")
    @Expose
    private String process;
    @SerializedName("order_comments")
    @Expose
    private String orderComments;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     *
     * @return
     * The process
     */
    public String getProcess() {
        return process;
    }

    /**
     *
     * @param process
     * The process
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     *
     * @return
     * The orderComments
     */
    public String getOrderComments() {
        return orderComments;
    }

    /**
     *
     * @param orderComments
     * The order_comments
     */
    public void setOrderComments(String orderComments) {
        this.orderComments = orderComments;
    }

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
