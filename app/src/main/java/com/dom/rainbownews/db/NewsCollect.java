package com.dom.rainbownews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dom.rainbownews.domain.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class NewsCollect {
    private NewsCollectHelper helper;

    public NewsCollect(Context context) {
        // TODO Auto-generated constructor stub
        helper = new NewsCollectHelper(context);
    }

    public boolean add(String title, String url, String time) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("url", url);
        contentValues.put("time", time);
        long rowId = db.insert("news", null, contentValues);

        if (rowId == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 查询所有的收藏的网址和网址对应的标题
     *
     * @return返回一个list集合
     */
    public List<Collection> findAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Collection> list = new ArrayList<Collection>();

        Cursor cursor = db.query("news", new String[]{"title", "url", "time"}, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            Collection collection = new Collection();

            collection.setTitle(cursor.getString(0));
            collection.setUrl(cursor.getString(1));
            collection.setTime(cursor.getString(2));
            list.add(collection);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 通过标题删除相关的收藏项
     *
     * @paramtitle删除的项的标题
     */
    public boolean delete(String time) {
        // TODO Auto-generated method stub
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowNumber = db.delete("news", "time=?", new String[]{time});
        if (rowNumber == 0) {
            return false;
        } else {
            return true;
        }

    }
}