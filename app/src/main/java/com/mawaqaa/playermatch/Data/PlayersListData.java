package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 2/1/2018.
 */

public class PlayersListData {

    String playerListId;
    String playerListName;
    String playerListPhone;
    String playerListStatus;

    public PlayersListData(String playerListId, String playerListName, String playerListPhone, String playerListStatus) {
        this.playerListId = playerListId;
        this.playerListName = playerListName;
        this.playerListPhone = playerListPhone;
        this.playerListStatus = playerListStatus;
    }

    public String getPlayerListId() {
        return playerListId;
    }

    public void setPlayerListId(String playerListId) {
        this.playerListId = playerListId;
    }

    public String getPlayerListName() {
        return playerListName;
    }

    public void setPlayerListName(String playerListName) {
        this.playerListName = playerListName;
    }

    public String getPlayerListPhone() {
        return playerListPhone;
    }

    public void setPlayerListPhone(String playerListPhone) {
        this.playerListPhone = playerListPhone;
    }

    public String getPlayerListStatus() {
        return playerListStatus;
    }

    public void setPlayerListStatus(String playerListStatus) {
        this.playerListStatus = playerListStatus;
    }
}
