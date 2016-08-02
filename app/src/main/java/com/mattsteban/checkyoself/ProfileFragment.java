package com.mattsteban.checkyoself;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by matt on 8/1/16.
 */
public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_pic)
    ImageView ivProfilePhoto;

    @BindView(R.id.name)
    TextView tvProfileName;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    String ratingCardUserId;

    RatingRecyclerViewAdapter adapter;

    public static Fragment newInstance(String ratingCardUserId){
        Bundle b = new Bundle();
        b.putString(Static.RATING_CARD_USER_ID, ratingCardUserId);
        Fragment f = new ProfileFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        ButterKnife.bind(this,view);


        Bundle b = getArguments();
        if (b != null){
            ratingCardUserId = b.getString(Static.RATING_CARD_USER_ID);
        }


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRefUser = db.getReference(Static.USERS + "/" + ratingCardUserId);
        dbRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                tvProfileName.setText(user.getName());
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
                        if (((ProfileActivity) getActivity()).currentUser.getId().equals(rating.getPersonAbout())) {
                            bindAdapter(rating);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new RatingRecyclerViewAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void bindAdapter(Rating rating){
        if (rating == null){
            adapter.setJudgementList(Arrays.asList(Static.judgementList));
        }
        else {
            adapter.setJudgementList(rating.getJudgementList());
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onStarClickedEvent(StarClickedEvent event){
        Rating rating = new Rating();

        // Judgments
        List<Judgement> judgements = adapter.getJudgementList();
        int index = judgements.indexOf(event.getJudgement());
        judgements.get(index).setFieldValue(event.getStarClicked());
        rating.setJudgementList(judgements);

        rating.setPersonAbout(ratingCardUserId);
        rating.setPersonFrom(ratingCardUserId);

        RatingsHelper.postRating(rating);
    }

    @OnClick(R.id.btn_check_yoself)
    public void onCheckYoselfClicked(View view){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
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
