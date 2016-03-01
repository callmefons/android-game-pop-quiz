package com.example.myapp.myapplication;

import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class Helper {

    public static Point getScreenSize(){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return  new Point(metrics.widthPixels,metrics.heightPixels);
    }

}
