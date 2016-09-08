package com.dom.rainbownews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dom.rainbownews.base.BaseActivity;
import com.dom.rainbownews.domain.History;

import java.util.List;

import git.dom.com.rainbownews.R;

/**
 * Created by reeman_uesr on 2016/9/8.
 */
public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private List<History> list;

    public HistoryAdapter(Context context, List<History> list) {
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
        ViewHolder holder = null;
        if (holder == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.history_item, null);
            holder.mtitle = (TextView) convertView.findViewById(R.id.mhistorytitle);
            holder.murl = (TextView) convertView.findViewById(R.id.mhistoryurl);
            holder.mcb = (CheckBox) convertView.findViewById(R.id.cbselect);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mtitle.setText(list.get(position).getTitle());
        holder.murl.setText(list.get(position).getUrl());
        holder.mcb.setVisibility(View.GONE);
        return convertView;
    }

    public class ViewHolder {
        private TextView mtitle;
        private TextView murl;
        private CheckBox mcb;
    }

}
