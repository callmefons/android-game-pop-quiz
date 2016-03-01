package com.example.myapp.myapplication;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Fon on 2/28/2015.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"food ","employees","Weather","Month","Days","Number","Vehicle","location"};

    private Context mContext;

    public MyPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {


        if (position == 0) {
            return tabemono.newInstance();
        } else if (position == 1)
            return employees.newInstance();
        else if (position == 2)
            return tenkiyoho.newInstance();
        else if (position == 3)
            return gatsu.newInstance();
        else if (position == 4)
            return yobi.newInstance();
        else if (position == 5)
            return number.newInstance();
        else if (position == 6)
            return norimono.newInstance();
        else if (position == 7)
            return location.newInstance();
        return null;


    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}