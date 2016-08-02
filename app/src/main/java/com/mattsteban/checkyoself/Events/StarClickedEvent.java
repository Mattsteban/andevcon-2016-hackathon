package com.mattsteban.checkyoself.Events;

import com.mattsteban.checkyoself.models.Judgement;

/**
 * Created by Esteban on 8/1/16.
 */
public class StarClickedEvent {

    Judgement judgement;
    int starClicked;

    public StarClickedEvent(Judgement judgement, int starClicked) {
        this.judgement = judgement;
        this.starClicked = starClicked;
    }

    public Judgement getJudgement() {
        return judgement;
    }

    public int getStarClicked() {
        return starClicked;
    }
}
