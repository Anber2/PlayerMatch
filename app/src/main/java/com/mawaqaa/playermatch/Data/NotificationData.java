package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 12/19/2017.
 */

public class NotificationData {

    String notificationId;
    String notificationTitle;
    String notificationDate;
    String notificationDescription;
    String notificationType;
    String MatchId;

    public NotificationData(String notificationId, String notificationTitle, String notificationDate, String notificationDescription, String notificationType, String matchId) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDate = notificationDate;
        this.notificationDescription = notificationDescription;
        this.notificationType = notificationType;
        MatchId = matchId;
    }

    public String getMatchId() {
        return MatchId;
    }

    public void setMatchId(String matchId) {
        MatchId = matchId;
    }

    public NotificationData(String notificationId, String notificationTitle, String notificationDate, String notificationDescription, String notificationType) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDate = notificationDate;
        this.notificationDescription = notificationDescription;
        this.notificationType = notificationType;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public void setNotificationDescription(String notificationDescription) {
        this.notificationDescription = notificationDescription;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public String getNotificationDescription() {
        return notificationDescription;
    }

    public String getNotificationType() {
        return notificationType;
    }
}
