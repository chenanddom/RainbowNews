
package com.dom.rainbownews.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.dom.chache.ImageLoader;
import com.dom.rainbownews.adapter.CarAdapter;
import com.dom.rainbownews.base.BaseFragment;
import com.dom.rainbownews.domain.News;
import com.dom.rainbownews.utils.DownLoadPicture;
import com.dom.rainbownews.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import git.dom.com.rainbownews.R;
import git.dom.com.rainbownews.ScanActivity;


/**
 * 国内新闻
 * Created by Administrator on 2016/8/24 0024.
 */

public class HomeNewsFragment extends BaseFragment {
    View view;
    ListView lv_card;
    // 请求返回的数据的数目
    private int num = 10;
    // 请求的页码
    private int page = 2;
    // 请求网址
    private String httpUrl = "http://api.huceo.com/guonei/?key=f9a6dc0392b9c598afcf80c60048f8ef&num="
            + num + "&page=" + page;
    private List<News> list = new ArrayList<>();
    private String title;
    private String picUrl;
    private String url;
    public Drawable picture;
    private CarAdapter adapter;
    private News myNews;
    private int lastItem;
    private boolean hadIntercept;
    private static final HomeNewsFragment homeNewsFragment=new HomeNewsFragment();
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x123:
                    parseJson((String) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;
                default:
            }
        }
    };
public HomeNewsFragment(){

}
    public static HomeNewsFragment getInstance(){
        return homeNewsFragment;
    }
    public CarAdapter getAdapter() {
        return adapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_home_news, null);
        getData();
        adapter = new CarAdapter(getActivity(), list);
        lv_card = (ListView) view.findViewById(R.id.lv_home_news);
        lv_card.setAdapter(adapter);
        lv_card.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                if (lastItem == list.size() - 1) {
                    page++;
                    httpUrl = "http://api.huceo.com/guonei/?key=f9a6dc0392b9c598afcf80c60048f8ef&num="
                            + num + "&page=" + page;
                    getData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                lastItem = lv_card.getLastVisiblePosition();
                /*
				 * System.out.println("------->"+lv_card.getLastVisiblePosition()
				 * );
				 */

            }
        });
        lv_card.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getActivity(), ScanActivity.class);
                intent.putExtra("url", list.get(position).getUrl());
                startActivity(intent);
                // 页面之间动画切换 enterAnim进入的动画 exitAnim退出的动画
                // overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
            }
        });
        return view;
    }

    /**
     * 解析数据返回一个集合
     *
     * @param content 要解析的内容
     * @return 返货一个List集合
     */
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

    /**
     * 多线程的网络请求
     */
    public void getData() {
        new Thread() {
            public void run() {
                Message msg = Message.obtain();
                msg.what = 0x123;
                msg.obj = NetUtils.request(httpUrl);
                handler.sendMessage(msg);
            }
        }.start();
    }

    public void getDrawable(final String httpUrl, final News news,
                            final List<News> list) {
        new Thread() {
            public void run() {
                Message msg = Message.obtain();
                Drawable drawable = DownLoadPicture.getPic(httpUrl);
                msg.what = 0x124;
                // news.setPicUrl(drawable);
                list.add(news);
                msg.obj = list;
                handler.sendMessage(msg);

            };
        }.start();
    }
/**
*按下回退键返回的时候就将缓存去除
* */
    @Override
    public boolean onBackPressd() {
        if (hadIntercept) {
            return false;
        } else {
            ImageLoader mImageLoader = adapter.getImageLoader();
            if (mImageLoader != null) {
                mImageLoader.clearCache();
            }
            hadIntercept = true;
            return true;
        }
    }
}

