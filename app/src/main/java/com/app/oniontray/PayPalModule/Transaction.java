
package com.app.oniontray.PayPalModule;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction implements Serializable {

    @SerializedName("amount")
    @Expose
    private Amount amount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("soft_descriptor")
    @Expose
    private String softDescriptor;
    @SerializedName("item_list")
    @Expose
    private ItemList itemList;
    @SerializedName("related_resources")
    @Expose
    private List<RelatedResource> relatedResources = null;

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSoftDescriptor() {
        return softDescriptor;
    }

    public void setSoftDescriptor(String softDescriptor) {
        this.softDescriptor = softDescriptor;
    }

    public ItemList getItemList() {
        return itemList;
    }

    public void setItemList(ItemList itemList) {
        this.itemList = itemList;
    }

    public List<RelatedResource> getRelatedResources() {
        return relatedResources;
    }

    public void setRelatedResources(List<RelatedResource> relatedResources) {
        this.relatedResources = relatedResources;
    }

}
