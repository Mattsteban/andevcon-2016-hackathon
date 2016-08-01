package com.mattsteban.checkyoself.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mattsteban.checkyoself.R;

/**
 * Created by matt on 8/1/16.
 */
public class RatingView extends LinearLayout {
    public enum RatingEnum{ONE_STAR,TWO_STAR,THREE_STAR,FOUR_STAR,FIVE_STAR}

    TextView fieldName;
    FiveStarRatingView starRatingView;

    public RatingView(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.inflatable_rating_view, this, true);

        bindViews();

        handleAttrs(context,attrs);

    }

    private void handleAttrs(Context context,AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.rating_view);

        fieldName.setText(a.getString(R.styleable.rating_view_field_name));
        starRatingView.setTotalStars(a.getInt(R.styleable.rating_view_stars,0));
    }

    private void bindViews(){
        fieldName = (TextView)findViewById(R.id.field_name);
        starRatingView = (FiveStarRatingView) findViewById(R.id.rating_view);
    }

}
