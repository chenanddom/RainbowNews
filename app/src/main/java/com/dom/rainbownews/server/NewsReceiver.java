package com.dom.rainbownews.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dom.rainbownews.utils.LogUtils;

/**
 * Created by Administrator on 2016/8/27 0027.
 */
public class NewsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    String info=intent.getStringExtra("news");
        LogUtils.printInfo("tag","info"+info);

    }
}
