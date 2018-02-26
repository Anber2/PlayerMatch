package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 12/5/2017.
 */

public class LocatioModel {
    private String Loc_name ;
    private String Loc_id ;

    public LocatioModel(String loc_name, String loc_id) {
        Loc_name = loc_name;
        Loc_id = loc_id;
    }

    public String getLoc_name() {
        return Loc_name;
    }

    public void setLoc_name(String loc_name) {
        Loc_name = loc_name;
    }

    public String getLoc_id() {
        return Loc_id;
    }

    public void setLoc_id(String loc_id) {
        Loc_id = loc_id;
    }
}
