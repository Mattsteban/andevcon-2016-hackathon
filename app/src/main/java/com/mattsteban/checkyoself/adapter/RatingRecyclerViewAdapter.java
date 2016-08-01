package com.mattsteban.checkyoself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mattsteban.checkyoself.R;
import com.mattsteban.checkyoself.models.Judgement;
import com.mattsteban.checkyoself.views.FiveStarRatingView;

import java.util.List;

/**
 * Created by matt on 8/1/16.
 */
public class RatingRecyclerViewAdapter extends RecyclerView.Adapter<RatingRecyclerViewAdapter.CustomViewHolder> {
    private List<Judgement> judgementList;



    public RatingRecyclerViewAdapter(List<Judgement> judgementList) {
            this.judgementList = judgementList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inflatable_rating_view, null);

            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
            Judgement judgementItem = judgementList.get(i);

            customViewHolder.ratingView.setTotalStars(judgementItem.getFieldValue());

            //Setting text view title
            customViewHolder.fieldName.setText(judgementItem.getFieldName());
    }

    @Override
    public int getItemCount() {
        return (null != judgementList ? judgementList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected FiveStarRatingView ratingView;
        protected TextView fieldName;

        public CustomViewHolder(View view) {
            super(view);
            this.ratingView = (FiveStarRatingView) view.findViewById(R.id.rating_view);
            this.fieldName = (TextView) view.findViewById(R.id.field_name);
        }
    }

}
