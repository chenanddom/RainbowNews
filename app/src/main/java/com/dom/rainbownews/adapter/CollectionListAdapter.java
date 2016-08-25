package com.dom.rainbownews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dom.rainbownews.domain.Collection;

import org.w3c.dom.Text;

import java.util.List;

import git.dom.com.rainbownews.R;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class CollectionListAdapter extends BaseAdapter {
    private Context context;
    private List<Collection> list;

    public CollectionListAdapter(Context context, List<Collection> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.collection_item, null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_collect_title);
            viewHolder.tv_Url = (TextView) convertView.findViewById(R.id.tv_collect_url);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(list.get(position).getTitle());
        viewHolder.tv_Url.setText(list.get(position).getUrl());
        return convertView;
    }

    public class ViewHolder {
        private TextView tvTitle;
        private TextView tv_Url;
    }
}
