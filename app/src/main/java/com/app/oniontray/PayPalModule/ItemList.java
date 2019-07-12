
package com.app.oniontray.PayPalModule;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemList implements Serializable {

    @SerializedName("items")
    @Expose
    private List<Object> items = null;
    @SerializedName("shipping_address")
    @Expose
    private ShippingAddress_ shippingAddress;

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }

    public ShippingAddress_ getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress_ shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

}
