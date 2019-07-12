package com.app.oniontray.RequestModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddFavourites {

    @SerializedName("response")
    @Expose
    private AddFavouritesResponse response;

    /**
     *
     * @return
     * The response
     */
    public AddFavouritesResponse getResponse() {
        return response;
    }

    /**
     *
     * @param response
     * The response
     */
    public void setResponse(AddFavouritesResponse response) {
        this.response = response;
    }

}