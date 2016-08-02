package com.mattsteban.checkyoself;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mattsteban.checkyoself.Events.StarClickedEvent;
import com.mattsteban.checkyoself.adapter.RatingRecyclerViewAdapter;
import com.mattsteban.checkyoself.models.Judgement;
import com.mattsteban.checkyoself.models.Rating;
import com.mattsteban.checkyoself.models.User;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matt on 8/1/16.
 */
public class RatingCardFragment extends Fragment {

    @BindView(R.id.profile_pic)
    ImageView ivProfilePhoto;

    @BindView(R.id.is_online)
    ImageView ivIsOnline;

    @BindView(R.id.name)
    TextView tvName;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.left_arrow)
    ImageView leftArrow;

    @BindView(R.id.right_arrow)
    ImageView rightArrow;

    String ratingCardUserId;

    RatingRecyclerViewAdapter adapter;

    public static Fragment newInstance(String ratingCardUserId){
        Bundle b = new Bundle();
        b.putString(Static.RATING_CARD_USER_ID, ratingCardUserId);
        Fragment f = new RatingCardFragment();
        f.setArguments(b);
        return f;
    }

    public static Fragment newInstance(String ratingCardUserId,boolean showLeftArrow,boolean showRightArrow){
        Fragment f = newInstance(ratingCardUserId);
        f.getArguments().putBoolean(Static.SHOW_LEFT_ARROW,showLeftArrow);
        f.getArguments().putBoolean(Static.SHOW_RIGHT_ARROW,showRightArrow);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_card,container,false);
        ButterKnife.bind(this,view);

        boolean showLeftArrow = false;
        boolean showRightArrow = false;

        Bundle b = getArguments();
        if (b != null){
            ratingCardUserId = b.getString(Static.RATING_CARD_USER_ID);
            showLeftArrow = b.getBoolean(Static.SHOW_LEFT_ARROW);
            showRightArrow = b.getBoolean(Static.SHOW_RIGHT_ARROW);
        }

        manageArrows(showLeftArrow,showRightArrow);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRefUser = db.getReference(Static.USERS + "/" + ratingCardUserId);
        dbRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                tvName.setText(user.getName());
                if (user.isOnline()){
                    ivIsOnline.setImageDrawable(getResources().getDrawable(R.drawable.circle_green, getActivity().getTheme()));
                }
                else {
                    ivIsOnline.setImageDrawable(getResources().getDrawable(R.drawable.circle_red, getActivity().getTheme()));
                }

//                ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(view.getContext(), user.getPhotoURL(), Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        DatabaseReference dbRefRatings = db.getReference(Static.RATINGS);

        Query userRatings = dbRefRatings.orderByChild("personAbout").equalTo(ratingCardUserId);
        userRatings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0){
                    bindAdapter(null);
                }
                else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Rating rating = snapshot.getValue(Rating.class);

                        if (getActivity() != null && ((MainActivity) getActivity()).currentUser != null && ((MainActivity) getActivity()).currentUser.getId().equals(rating.getPersonFrom())) {
                            bindAdapter(rating);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new RatingRecyclerViewAdapter(ratingCardUserId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void manageArrows(boolean showLeftArrow,boolean showRightArrow){
        if(showLeftArrow)
            leftArrow.setVisibility(View.VISIBLE);
        else
            leftArrow.setVisibility(View.INVISIBLE);

        if(showRightArrow)
            rightArrow.setVisibility(View.VISIBLE);
        else
            rightArrow.setVisibility(View.INVISIBLE);
    }

    private void bindAdapter(Rating rating){
        if (rating == null){
            adapter.setJudgementList(new ArrayList<>(Static.getDefaultJudgments()));
        }
        else {
            adapter.setJudgementList(rating.getJudgementList());
        }
        adapter.notifyDataSetChanged();
    }


    @Subscribe
    public void onStarClickedEvent(StarClickedEvent event){
        if (event.getRatingCardUserId().equals(ratingCardUserId)) {
            Rating rating = new Rating();

            // Judgments
            List<Judgement> judgements = adapter.getJudgementList();
            int index = judgements.indexOf(event.getJudgement());
            judgements.get(index).setFieldValue(event.getStarClicked());
            rating.setJudgementList(judgements);

            rating.setPersonAbout(ratingCardUserId);
            rating.setPersonFrom(((MainActivity) getActivity()).currentUser.getId());

            RatingsHelper.postRating(rating);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
