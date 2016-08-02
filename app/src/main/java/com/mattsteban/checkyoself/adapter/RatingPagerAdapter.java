package com.mattsteban.checkyoself.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mattsteban.checkyoself.MyRatingsCardFragment;
import com.mattsteban.checkyoself.RatingCardFragment;
import com.mattsteban.checkyoself.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matt on 8/2/16.
 */
public class RatingPagerAdapter extends FragmentPagerAdapter {
    List<User> listOfUsers = new ArrayList<>();
    boolean isCheckMyselfAdapter = false;

    public RatingPagerAdapter(FragmentManager fm, List<User>userList) {
        super(fm);
        this.listOfUsers = userList;
    }

    public RatingPagerAdapter(FragmentManager fm, List<User>userList, boolean isCheckMyselfAdapter) {
        super(fm);
        this.listOfUsers = userList;
        this.isCheckMyselfAdapter = isCheckMyselfAdapter;
    }

    @Override
    public Fragment getItem(int position) {
        if (isCheckMyselfAdapter){
            return MyRatingsCardFragment.newInstance(listOfUsers.get(position).getId());
        }
        return RatingCardFragment.newInstance(listOfUsers.get(position).getId());
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return listOfUsers.size();
    }
}
