package com.dom.rainbownews.server;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.dom.rainbownews.domain.News;
import com.dom.rainbownews.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import git.dom.com.rainbownews.MainActivity;
import git.dom.com.rainbownews.R;
import git.dom.com.rainbownews.ScanActivity;

public class NewsPushServer extends Service {
    //    private HomeNewsFragment homeNewsFragment;
    private Timer timer;
    private TimerTask task;
    private String title;
    private String picUrl;
    private String url;
    private List<News> list = new ArrayList<>();
    private int i = 0;
    private String mtitle;
    private String murl;

    public NewsPushServer() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        final Intent intent = new Intent();
        intent.setAction("com.dom.rainbownews.push");


        final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final Notification.Builder builder = new Notification.Builder(this);
        final Intent intent2 = new Intent(this, ScanActivity.class);

        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                String result = NetUtils.request("http://api.huceo.com/guonei/?key=f9a6dc0392b9c598afcf80c60048f8ef&num=10&page=1");
                parseJson(result);
                mtitle = list.get(i).getTitle();
                murl = list.get(i).getUrl();
//                intent.putExtra("news", info);
//                sendBroadcast(intent);

                intent2.putExtra("title", mtitle);
                intent2.putExtra("url", murl);
                PendingIntent pendingIntent = PendingIntent.getActivity(NewsPushServer.this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentTitle("新闻");
                builder.setContentInfo("今日新闻");
                builder.setContentText(mtitle);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setTicker("新消息");
                builder.setAutoCancel(true);
                builder.setWhen(System.currentTimeMillis());
                builder.setContentIntent(pendingIntent);
                Notification notification = builder.build();
                manager.notify(1, notification);
            }
        };
        timer.schedule(task, 1000 * 30, 1000 * 60 * 30);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void parseJson(String content) {
        try {
            JSONObject json = new JSONObject(content);
            JSONArray jsonArray = json.getJSONArray("newslist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonNews = jsonArray.getJSONObject(i);
                title = jsonNews.getString("title");
                picUrl = jsonNews.getString("picUrl");

                try {

                    if (picUrl != null) {
                        String str = picUrl.substring(0, picUrl.length() - 10) + "360x250.jpg";
                        System.out.println("str==" + str);
                        url = jsonNews.getString("url");
                        News news = new News();
                        news.setTitle(title);
                        news.setUrl(url);
                        news.setPicUrl(str);
                        list.add(news);
                    } else {

                    }

                } catch (Exception e) {
                    // TODO: handle exception

                    e.printStackTrace();
                }

            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
