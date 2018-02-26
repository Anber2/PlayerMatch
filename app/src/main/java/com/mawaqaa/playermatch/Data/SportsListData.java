package com.mawaqaa.playermatch.Data;

import org.json.JSONArray;

/**
 * Created by HP on 10/10/2017.
 */

public class SportsListData {

    String sportID;
    String sportImage;
    String sportName;
    Boolean isPostion ;
    String icon;
    JSONArray lstPostion ;




    public SportsListData(String sportID, String sportImage, String sportName, Boolean isPostion, String icon, JSONArray lstPostion) {
        this.sportID = sportID;
        this.sportImage = sportImage;
        this.sportName = sportName;
        this.isPostion = isPostion;
        this.icon = icon;
        this.lstPostion = lstPostion;
    }

    public SportsListData(String sportID, String sportImage, String sportName, Boolean isPostion, JSONArray lstPostion) {
        this.sportID = sportID;
        this.sportImage = sportImage;
        this.sportName = sportName;
        this.isPostion = isPostion;
        this.lstPostion = lstPostion;
    }

    public SportsListData(String sportID, String sportImage, String sportName) {
        this.sportID = sportID;
        this.sportImage = sportImage;
        this.sportName = sportName;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Boolean getPostion() {
        return isPostion;
    }

    public void setPostion(Boolean postion) {
        isPostion = postion;
    }

    public JSONArray getLstPostion() {
        return lstPostion;
    }

    public void setLstPostion(JSONArray lstPostion) {
        this.lstPostion = lstPostion;
    }

    public String getSportID() {
        return sportID;
    }

    public String getSportImage() {
        return sportImage;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportID(String sportID) {
        this.sportID = sportID;
    }

    public void setSportImage(String sportImage) {
        this.sportImage = sportImage;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
