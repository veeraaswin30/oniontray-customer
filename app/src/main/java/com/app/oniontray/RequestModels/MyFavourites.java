package com.app.oniontray.RequestModels;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyFavourites {

    @SerializedName("response")
    @Expose
    private MyFavouritesResponse response;

    /**
     *
     * @return
     * The response
     */
    public MyFavouritesResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(MyFavouritesResponse response) {
        this.response = response;
    }

}
