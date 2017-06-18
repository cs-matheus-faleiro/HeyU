package com.matheusfaleiro.heyu.activities.heyu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matheusfaleiro.heyu.activities.heyu.fragment.ChatsFragment;
import com.matheusfaleiro.heyu.activities.heyu.fragment.FriendsFragment;
import com.matheusfaleiro.heyu.activities.heyu.fragment.RequestsFragment;

public class SectionsPageAdapter extends FragmentPagerAdapter {


    private final int totalFragments = 3;

    public SectionsPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ChatsFragment.newInstance();
            case 1:
                return FriendsFragment.newInstance();
            case 2:
                return RequestsFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    @Override
    public int getCount() {
        return totalFragments;
    }
}
