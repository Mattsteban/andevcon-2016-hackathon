package com.mattsteban.checkyoself.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mattsteban.checkyoself.RatingCardFragment;
import com.mattsteban.checkyoself.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 8/2/16.
 */
public class RatingPagerAdapter extends FragmentPagerAdapter {
    List<User> listOfUsers = new ArrayList<>();

    public RatingPagerAdapter(FragmentManager fm, List<User>userList) {
        super(fm);
        this.listOfUsers = userList;
    }

    @Override
    public Fragment getItem(int position) {
        return RatingCardFragment.newInstance(listOfUsers.get(position).getId());
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return listOfUsers.size();
    }
}
