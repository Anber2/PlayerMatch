package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/19/2017.
 */

public class LocationListData {

    String locationId;
    String locationName;

    public LocationListData(String locationId, String locationName) {
        this.locationId = locationId;
        this.locationName = locationName;
    }


    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }
}
