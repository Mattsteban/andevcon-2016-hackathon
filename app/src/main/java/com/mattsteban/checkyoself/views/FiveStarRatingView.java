package com.mattsteban.checkyoself.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mattsteban.checkyoself.R;

/**
 * Created by matt on 8/1/16.
 */
public class FiveStarRatingView extends LinearLayout {
    ImageView star1;
    ImageView star2;
    ImageView star3;
    ImageView star4;
    ImageView star5;
    private int totalStars = 0;

    public FiveStarRatingView(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.inflatable_five_star_layout, this, true);
        bindViews();

        handleAttrs(context,attrs);

    }

    private void handleAttrs(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.rating_view);
        totalStars = a.getInt(R.styleable.rating_view_stars,0);

        switch (totalStars){
            case 5:
                star5.setBackground(getResources().getDrawable(R.drawable.filled_star));
            case 4:
                star4.setBackground(getResources().getDrawable(R.drawable.filled_star));
            case 3:
                star3.setBackground(getResources().getDrawable(R.drawable.filled_star));
            case 2:
                star2.setBackground(getResources().getDrawable(R.drawable.filled_star));
            case 1:
                star1.setBackground(getResources().getDrawable(R.drawable.filled_star));
            case 0:
                break;
        }
    }

    public void setTotalStars(int stars){
        totalStars = stars;
    }

    private void bindViews(){
        star1 = (ImageView)findViewById(R.id.star1);
        star2 = (ImageView)findViewById(R.id.star2);
        star3 = (ImageView)findViewById(R.id.star3);
        star4 = (ImageView)findViewById(R.id.star4);
        star5 = (ImageView)findViewById(R.id.star5);
    }
}
