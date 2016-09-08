package com.dom.rainbownews.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dom.rainbownews.domain.History;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by reeman_uesr on 2016/9/8.
 */
public class HistoryRecord {
    private HisotoryHelper helper;
    private Context context;

    public HistoryRecord(Context context) {
        helper = new HisotoryHelper(context);
    }

    /**
     * 添加历史纪录
     *
     * @return
     * @paramtitle标题
     * @paramurl
     */
    public boolean addHistory(String title, String url) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long rowId;
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("url", url);
        rowId = db.insert("history", null, cv);
        db.close();
        return rowId > 0 ? true : false;
    }

    public List<History> findAllHistory() {
        SQLiteDatabase db = helper.getReadableDatabase();
        List<History> list = new ArrayList<>();
        Cursor cursor = db.query("history", new String[]{"_id", "title", "url"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            History history = new History();
            history.setId(cursor.getInt(0));
            history.setTitle(cursor.getString(1));
            history.setUrl(cursor.getString(2));
            list.add(history);
        }
        return list;
    }
    public boolean deleteHistory(int _id){
    SQLiteDatabase db=helper.getWritableDatabase();
      int rowNumber=db.delete("history","_id=?",new String[]{_id+""});
        return rowNumber==0?false:true;
    }
}
