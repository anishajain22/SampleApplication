package com.example.myapplication;

import android.content.Context;
import android.util.Log;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        Log.d("Debug","MyAdapter");
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        String url=null;
        switch (position) {
            case 0:
                url ="https://www.microsoft.com/en-in/microsoft-365/microsoft-teams/group-chat-software";
                break;
            case 1:
                url="https://bing.com/";
                break;
            case 2:
                url="https://azure.microsoft.com/en-us/";
                break;
            default:
                url=null;
        }

        return new TabClass(url);
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}