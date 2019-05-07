package com.example.android.schoolapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.schoolapp.fragment.FeedFragment;
import com.example.android.schoolapp.fragment.InboxFragment;
import com.example.android.schoolapp.fragment.ForumFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitle = new String[]{"INBOX", "FORUM", "FEED"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new InboxFragment();

            case 1:
                return new ForumFragment();

            case 2:
                return new FeedFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
