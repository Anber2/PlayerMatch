package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/12/2017.
 */

public class GameListData {

    String gameID;
    String sportName;
    String sportDate;
    String sportLocationName;
    String sportDaysToGo;

    public GameListData(String gameID, String sportName, String sportDate, String sportLocationName, String sportDaysToGo) {
        this.gameID = gameID;
        this.sportName = sportName;
        this.sportDate = sportDate;
        this.sportLocationName = sportLocationName;
        this.sportDaysToGo = sportDaysToGo;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public void setSportDate(String sportDate) {
        this.sportDate = sportDate;
    }

    public void setSportLocationName(String sportLocationName) {
        this.sportLocationName = sportLocationName;
    }

    public void setSportDaysToGo(String sportDaysToGo) {
        this.sportDaysToGo = sportDaysToGo;
    }

    public String getGameID() {
        return gameID;
    }

    public String getSportName() {
        return sportName;
    }

    public String getSportDate() {
        return sportDate;
    }

    public String getSportLocationName() {
        return sportLocationName;
    }

    public String getSportDaysToGo() {
        return sportDaysToGo;
    }
}
