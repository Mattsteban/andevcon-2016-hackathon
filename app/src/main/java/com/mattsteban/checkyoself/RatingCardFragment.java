package com.mattsteban.checkyoself;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mattsteban.checkyoself.adapter.RatingRecyclerViewAdapter;
import com.mattsteban.checkyoself.models.Judgement;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        ButterKnife.bind(this,view);

        Bundle b = getArguments();
        if (b != null){
            ratingCardUserId = b.getString("RATING_CARD_USER_ID");
        }



        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbRefRatings = db.getReference(Static.RATINGS);

        List<Judgement> judgementList = new ArrayList<>();
        judgementList.add(new Judgement("Happy",4));
        judgementList.add(new Judgement("Ugly",2));
        judgementList.add(new Judgement("TrustWorthy",0));
        judgementList.add(new Judgement("Git Gud",1));

        RatingRecyclerViewAdapter adapter = new RatingRecyclerViewAdapter(judgementList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}