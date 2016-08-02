package com.mattsteban.checkyoself.Events;

import com.mattsteban.checkyoself.models.Judgement;

/**
 * Created by Esteban on 8/1/16.
 */
public class StarClickedEvent {

    String ratingCardUserId;
    Judgement judgement;
    int starClicked;

    public StarClickedEvent(Judgement judgement, int starClicked, String ratingCardUserId) {
        this.judgement = judgement;
        this.starClicked = starClicked;
        this.ratingCardUserId = ratingCardUserId;
    }

    public String getRatingCardUserId() {
        return ratingCardUserId;
    }

    public Judgement getJudgement() {
        return judgement;
    }

    public int getStarClicked() {
        return starClicked;
    }
}
