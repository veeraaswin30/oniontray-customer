package com.app.oniontray.RequestModels;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewResponse {

    @SerializedName("httpCode")
    @Expose
    private Integer httpCode;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("review_list")
    @Expose
    private List<ReviewListArray> reviewList = null;
    @SerializedName("review_count")
    @Expose
    private int reviewCount;

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

    public List<ReviewListArray> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<ReviewListArray> reviewList) {
        this.reviewList = reviewList;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

}