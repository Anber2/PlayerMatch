package com.mawaqaa.playermatch.Data;

/**
 * Created by HP on 10/16/2017.
 */

public class FAQListData {

    String fAQId;
    String fAQQuestion;
    String fAQAnswer;

    public FAQListData(String fAQId, String fAQQuestion, String fAQAnswer) {
        this.fAQId = fAQId;
        this.fAQQuestion = fAQQuestion;
        this.fAQAnswer = fAQAnswer;
    }

    public void setfAQId(String fAQId) {
        this.fAQId = fAQId;
    }

    public void setfAQQuestion(String fAQQuestion) {
        this.fAQQuestion = fAQQuestion;
    }

    public void setfAQAnswer(String fAQAnswer) {
        this.fAQAnswer = fAQAnswer;
    }

    public String getfAQId() {
        return fAQId;
    }

    public String getfAQQuestion() {
        return fAQQuestion;
    }

    public String getfAQAnswer() {
        return fAQAnswer;
    }
}
