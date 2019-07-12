
package com.app.oniontray.RequestModels;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationbasedCityDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("zone_name")
    @Expose
    private String zoneName;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The zoneName
     */
    public String getZoneName() {
        return zoneName;
    }

    /**
     * 
     * @param zoneName
     *     The zone_name
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

}
