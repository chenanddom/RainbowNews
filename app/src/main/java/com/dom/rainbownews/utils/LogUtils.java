package com.dom.rainbownews.utils;

import android.util.Log;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class LogUtils {
    private static boolean flag=true;
    public static void printInfo(String tag,String info){
    if (flag){
        Log.i(tag,info);
    }
    }
}
