package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 12/10/2017.
 */

public class PostionModel {
    private String positionId ;
    private String positionName ;

    public PostionModel(String positionId, String positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
