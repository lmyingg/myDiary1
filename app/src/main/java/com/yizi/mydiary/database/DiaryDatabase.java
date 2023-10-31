package com.yizi.mydiary.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 *@Author: create by ziyi
 *@Function: 日记数据库创建操作
*/
public class DiaryDatabase extends SQLiteOpenHelper {
    private Context context;


    public static final String Diary = "create table diary ("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "content text,"
            + "time text,"
            + "up integer,"
            + "lock integer"
            + ")";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Diary);//执行建表语句，创建数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public DiaryDatabase(Context context) {
        super(context, "diary", null, 1);
        this.context = context;
    }
}
