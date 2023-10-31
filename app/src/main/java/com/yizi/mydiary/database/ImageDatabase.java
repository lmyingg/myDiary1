package com.yizi.mydiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *@Author: create by ziyi
 *@Function: 图片数据库创建操作
*/
public class ImageDatabase extends SQLiteOpenHelper {
    private Context context;

    public static final String Diary = "create table image ("
            + "id integer primary key autoincrement,"
            + "diary_id integer,"
            + "src text"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Diary);//执行建表语句，创建数据库
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public ImageDatabase(Context context) {
        super(context, "image", null, 1);
        this.context = context;
    }
}
