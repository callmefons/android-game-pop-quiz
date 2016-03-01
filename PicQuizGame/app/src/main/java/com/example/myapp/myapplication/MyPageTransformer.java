package com.example.myapp.myapplication;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Fon on 2/28/2015.
 */
public class MyPageTransformer implements ViewPager.PageTransformer {


    public void transformPage(View view, float position) {
        if (position > 1 || position < -1) {
            view.setAlpha(0);
        } else {
            float alpha = 1 - (float) (Math.abs(position));
            view.setAlpha(alpha);
        }
    }

}