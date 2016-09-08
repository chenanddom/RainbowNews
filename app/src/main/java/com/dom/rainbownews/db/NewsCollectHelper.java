package com.dom.rainbownews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import git.dom.com.rainbownews.Const;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class NewsCollectHelper extends SQLiteOpenHelper {

    public NewsCollectHelper(Context context) {
        super(context, Const.NEWSDBNAME + ".db", null, Const.NEWSDBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table news(_id integer primary key autoincrement,title varchar(50),url varchar(255),time varchar(255))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table news";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
