package com.dom.rainbownews.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class ToastUtils {
    public static void ToastInfo(Context context,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
