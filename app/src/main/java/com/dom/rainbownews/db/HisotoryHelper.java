package com.dom.rainbownews.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by reeman_uesr on 2016/9/7.
 */
public class HisotoryHelper extends SQLiteOpenHelper {
private static final String DBNAME="history";
    public HisotoryHelper(Context context) {
        super(context, DBNAME+".db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
    String sql="create table history(_id integer primary key autoincrement,title varchar(50),url varchar(255))";
   db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table history";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
