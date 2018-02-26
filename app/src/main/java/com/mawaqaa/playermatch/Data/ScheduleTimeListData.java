package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/18/2017.
 */

public class ScheduleTimeListData {

    String timeId;
    String timeText;

    public ScheduleTimeListData(String timeId, String timeText) {
        this.timeId = timeId;
        this.timeText = timeText;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }

    public String getTimeId() {
        return timeId;
    }

    public String getTimeText() {
        return timeText;
    }


}
