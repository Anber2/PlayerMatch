package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/17/2017.
 */

public class ListOFGamesListData {

    String listOFGamesId;
    String listOFGamesData;
    String listOFGamesTime;
    String listOFGameslocationName;
    String listOFGamesPlayersJoined;
    String listOFGamesPlayersTotal;



    public ListOFGamesListData(String listOFGamesId, String listOFGamesData, String listOFGamesTime, String listOFGameslocationName, String listOFGamesPlayersJoined, String listOFGamesPlayersTotal) {
        this.listOFGamesId = listOFGamesId;
        this.listOFGamesData = listOFGamesData;
        this.listOFGamesTime = listOFGamesTime;
        this.listOFGameslocationName = listOFGameslocationName;
        this.listOFGamesPlayersJoined = listOFGamesPlayersJoined;
        this.listOFGamesPlayersTotal = listOFGamesPlayersTotal;
    }


    public void setListOFGamesId(String listOFGamesId) {
        this.listOFGamesId = listOFGamesId;
    }

    public String getListOFGamesId() {
        return listOFGamesId;
    }
    public void setListOFGamesData(String listOFGamesData) {
        this.listOFGamesData = listOFGamesData;
    }

    public void setListOFGamesTime(String listOFGamesTime) {
        this.listOFGamesTime = listOFGamesTime;
    }

    public void setListOFGameslocationName(String listOFGameslocationName) {
        this.listOFGameslocationName = listOFGameslocationName;
    }

    public void setListOFGamesPlayersJoined(String listOFGamesPlayersJoined) {
        this.listOFGamesPlayersJoined = listOFGamesPlayersJoined;
    }

    public void setListOFGamesPlayersTotal(String listOFGamesPlayersTotal) {
        this.listOFGamesPlayersTotal = listOFGamesPlayersTotal;
    }

    public String getListOFGamesData() {
        return listOFGamesData;
    }

    public String getListOFGamesTime() {
        return listOFGamesTime;
    }

    public String getListOFGameslocationName() {
        return listOFGameslocationName;
    }

    public String getListOFGamesPlayersJoined() {
        return listOFGamesPlayersJoined;
    }

    public String getListOFGamesPlayersTotal() {
        return listOFGamesPlayersTotal;
    }
}
