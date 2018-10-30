package com.qgstudio.anywork.exam;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class QuestionFragAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public QuestionFragAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public void addAll(List<Fragment> fragments) {
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return mFragments.size();
    }

}
