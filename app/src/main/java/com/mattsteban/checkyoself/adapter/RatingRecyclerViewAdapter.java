package com.mattsteban.checkyoself.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattsteban.checkyoself.BusProvider;
import com.mattsteban.checkyoself.Events.StarClickedEvent;
import com.mattsteban.checkyoself.R;
import com.mattsteban.checkyoself.models.Judgement;
import com.mattsteban.checkyoself.views.FiveStarRatingView;

import java.util.List;

/**
 * Created by matt on 8/1/16.
 */
public class RatingRecyclerViewAdapter extends RecyclerView.Adapter<RatingRecyclerViewAdapter.CustomViewHolder> {
    private List<Judgement> judgementList;
    String ratingCardUserId;

    public RatingRecyclerViewAdapter(String ratingCardUserId) {
        this.ratingCardUserId = ratingCardUserId;
    }

    public void setJudgementList(List<Judgement> judgementList){
        this.judgementList = judgementList;
    }

    public List<Judgement> getJudgementList(){
        return this.judgementList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inflatable_rating_view, null);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, final int i) {
            Judgement judgementItem = judgementList.get(i);

            customViewHolder.ratingView.setTotalStars(judgementItem.getFieldValue());
            //Setting text view title
            customViewHolder.fieldName.setText(judgementItem.getFieldName());

        ImageView star1 = customViewHolder.ratingView.star1;
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new StarClickedEvent(judgementList.get(i), 1, ratingCardUserId));
            }
        });
        ImageView star2 = customViewHolder.ratingView.star2;
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new StarClickedEvent(judgementList.get(i), 2, ratingCardUserId));
            }
        });
        ImageView star3 =customViewHolder.ratingView.star3;
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new StarClickedEvent(judgementList.get(i), 3, ratingCardUserId));
            }
        });
        ImageView star4 =  customViewHolder.ratingView.star4;
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new StarClickedEvent(judgementList.get(i), 4, ratingCardUserId));
            }
        });
        ImageView star5 = customViewHolder.ratingView.star5;
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusProvider.getInstance().post(new StarClickedEvent(judgementList.get(i), 5, ratingCardUserId));
            }
        });
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
