package com.mattsteban.checkyoself;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mattsteban.checkyoself.adapter.RatingRecyclerViewAdapter;
import com.mattsteban.checkyoself.models.Judgement;
import com.mattsteban.checkyoself.models.Rating;
import com.mattsteban.checkyoself.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by matt on 8/1/16.
 */
public class RatingCardFragment extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    String ratingCardUserId;

    RatingRecyclerViewAdapter adapter;

    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_card,container,false);
        ButterKnife.bind(this,view);

        Bundle b = getArguments();
        if (b != null){
            ratingCardUserId = b.getString("RATING_CARD_USER_ID");
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRefUser = db.getReference(Static.USERS + "/" + ratingCardUserId);
        dbRefUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
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
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Rating rating = snapshot.getValue(Rating.class);
                    if (((MainActivity)getActivity()).currentUser.getId().equals(rating.getPersonFrom())){
                        bindAdapter(rating);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void bindAdapter(Rating rating){
        if (adapter == null){
            adapter = new RatingRecyclerViewAdapter();
        }
        adapter.setJudgementList(rating.getJudgementList());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
