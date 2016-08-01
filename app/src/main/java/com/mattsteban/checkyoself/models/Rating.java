package com.mattsteban.checkyoself.models;

import java.util.List;

/**
 * Created by Esteban on 8/1/16.
 */
public class Rating {

    String personAbout;
    String personFrom;
    List<Judgement> judgementList;

    public Rating() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public List<Judgement> getJudgementList() {
        return judgementList;
    }

    public void setJudgementList(List<Judgement> judgementList) {
        this.judgementList = judgementList;
    }

    public String getPersonAbout() {
        return personAbout;
    }

    public void setPersonAbout(String personAbout) {
        this.personAbout = personAbout;
    }

    public String getPersonFrom() {
        return personFrom;
    }

    public void setPersonFrom(String personFrom) {
        this.personFrom = personFrom;
    }
}
