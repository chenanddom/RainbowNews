package com.dom.rainbownews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dom.rainbownews.domain.MyCity;

import java.util.List;

import git.dom.com.rainbownews.R;

/**
 * Created by Administrator on 2016/8/26 0026.
 */
public class CityListAdapter extends BaseAdapter {
    private Context context;
    private List<MyCity> list;

    public CityListAdapter(Context context,List<MyCity> list){
        this.context=context;
        this.list=list;
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
            convertView = View.inflate(context, R.layout.citylist_item, null);
            viewHolder.cityName = (TextView) convertView.findViewById(R.id.tv_city_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cityName.setText(list.get(position).getName());
        return convertView;
    }
    public class ViewHolder {
        TextView cityName;
    }
}
