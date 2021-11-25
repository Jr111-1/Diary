package com.example.diaryclf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBOpenHelper extends SQLiteOpenHelper {

    //创建数据库
    public static final String TB_name = "WordDiary";

    public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(@Nullable Context context, @Nullable String basename, int version) {
        super(context, basename, null, version);
    }


    //建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE IF NOT EXISTS WordDiary (_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20),meaning VARCHAR(20),example VARCHAR(20))";

        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "WordDiary");
        onCreate(sqLiteDatabase);


    }
}
