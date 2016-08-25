package com.dom.rainbownews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dom.chache.ImageLoader;
import com.dom.rainbownews.domain.News;

import java.util.List;

import git.dom.com.rainbownews.R;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<News> list;
    //加载图片的加载器
    private ImageLoader mImageLoader;

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }
    public CarAdapter(Context context,List<News> list) {
        // TODO Auto-generated constructor stub
        this.context=context;
        this.list=list;
        mImageLoader = new ImageLoader(context);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @SuppressWarnings("unused")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.fragment_item, null);
            viewHolder.tv_title = (TextView) convertView
                    .findViewById(R.id.fragment_title);
            viewHolder.image_picutre = (ImageView) convertView
                    .findViewById(R.id.fragment_picture);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(list.get(position).getTitle());
//		viewHolder.image_picutre.setImageDrawable(list.get(position).getPicUrl());
        mImageLoader.DisplayImage(list.get(position).getPicUrl(), viewHolder.image_picutre, false);
//		System.out.println(list.get(position).getPicUrl()+"");
        return convertView;
    }
    public class ViewHolder {
        TextView tv_title;
        ImageView image_picutre;
    }
}