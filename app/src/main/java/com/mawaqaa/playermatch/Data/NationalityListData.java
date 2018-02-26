package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/11/2017.
 */

public class NationalityListData {

    String NationalityID;
    String NationalityName;

    public void setNationalityID(String nationalityID) {
        NationalityID = nationalityID;
    }

    public void setNationalityName(String nationalityName) {
        NationalityName = nationalityName;
    }

    public String getNationalityID() {
        return NationalityID;
    }

    public String getNationalityName() {
        return NationalityName;
    }

    public NationalityListData(String nationalityID, String nationalityName) {
        NationalityID = nationalityID;
        NationalityName = nationalityName;
    }
}
