package com.mattsteban.checkyoself;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mattsteban.checkyoself.models.Rating;

import java.util.List;

/**
 * Created by Esteban on 8/1/16.
 */
public class RatingsHelper {

    public static void postRating(Rating rating){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = db.getReference(Static.RATINGS);
        ratingsRef.push().setValue(rating);
    }

    public void readRating(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ratingsRef = db.getReference(Static.RATINGS);

    }
}
