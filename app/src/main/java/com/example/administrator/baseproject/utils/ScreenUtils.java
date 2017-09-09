package com.example.administrator.baseproject.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/9/7.
 */

public class ScreenUtils {

    public static int[] getScreenHW(Context context){
        int[] result = {-1, -1};
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        result[1] = dm.widthPixels;
        result[0] = dm.heightPixels;
        return result;
    }

    public static int getScreenHeight(Context context){
        return getScreenHW(context)[0];
    }

    public static int getScreenWidth(Context context){
        return getScreenHW(context)[1];
    }
}
