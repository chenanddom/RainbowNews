package com.dom.rainbownews.utils;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class DownLoadPicture {
    public static Drawable getPic(String httpUrl){
        HttpURLConnection conn = null;
        URL url;
        try {
            url = new URL(httpUrl);
            conn = (HttpURLConnection)url.openConnection();
            //设置连接方法
            conn.setRequestMethod("GET");
            //设置连接时长
            conn.setConnectTimeout(8000);
            //设置读取的时长
            conn.setReadTimeout(5000);
            conn.connect();//建立链接
            if(conn.getResponseCode()==200){
                InputStream is=conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                @SuppressWarnings("deprecation")
                Drawable drawable = new BitmapDrawable(bitmap);
                return drawable;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}