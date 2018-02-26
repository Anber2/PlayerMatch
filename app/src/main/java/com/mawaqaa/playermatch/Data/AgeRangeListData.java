package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/11/2017.
 */

public class AgeRangeListData {

    String AgeRangeID;
    String AgeRangeName;

    public AgeRangeListData(String ageRangeID, String ageRangeName) {
        AgeRangeID = ageRangeID;
        AgeRangeName = ageRangeName;
    }


    public void setAgeRangeID(String ageRangeID) {
        AgeRangeID = ageRangeID;
    }

    public void setAgeRangeName(String ageRangeName) {
        AgeRangeName = ageRangeName;
    }

    public String getAgeRangeID() {
        return AgeRangeID;
    }

    public String getAgeRangeName() {
        return AgeRangeName;
    }
}
