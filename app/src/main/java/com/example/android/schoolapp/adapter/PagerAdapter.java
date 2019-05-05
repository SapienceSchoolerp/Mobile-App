package com.example.android.schoolapp.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.android.schoolapp.fragment.ChatsFragment;
import com.example.android.schoolapp.fragment.FuzeFragment;
import com.example.android.schoolapp.fragment.InboxFragment;
import com.example.android.schoolapp.fragment.KnitFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private String tabTitle[] = new String[]{"INBOX", "CHATS", "KNIT", "FUZE"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new InboxFragment();

            case 1:
                return new ChatsFragment();

            case 2:
                return new KnitFragment();

            case 3:
                return new FuzeFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
