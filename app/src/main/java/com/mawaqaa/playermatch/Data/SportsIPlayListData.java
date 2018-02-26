package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/11/2017.
 */

public class SportsIPlayListData {

    String sportID;
    String sportName;

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getSportID() {
        return sportID;
    }

    public String getSportName() {
        return sportName;
    }

    public SportsIPlayListData(String sportID, String sportName) {
        this.sportID = sportID;
        this.sportName = sportName;
    }
}
