package com.mattsteban.checkyoself;

import com.mattsteban.checkyoself.models.Judgement;

/**
 * Created by Esteban on 8/1/16.
 */
public class Static {
    public static final int RC_SIGN_IN = 123;
    public static final String USERS = "users";
    public static final String RATINGS = "ratings";


    public static final String RATING_CARD_USER_ID = "RATING_CARD_USER_ID";

    public static final String SHOW_LEFT_ARROW = "SHOW_LEFT_ARROW";
    public static final String SHOW_RIGHT_ARROW = "SHOW_RIGHT_ARROW";


    public static final Judgement[] judgementList = {
            new Judgement("Trustworthy", 0),
            new Judgement("Attractiveness", 0),
            new Judgement("Artistic", 0),
            new Judgement("Naughty", 0)
    };
}
