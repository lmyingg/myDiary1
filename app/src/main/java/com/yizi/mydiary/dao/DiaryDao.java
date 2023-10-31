package com.yizi.mydiary.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yizi.mydiary.database.DiaryDatabase;
import com.yizi.mydiary.entity.DiaryEntity;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 日记的数据库操作
*/
public class DiaryDao {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;

    private static final String[] columns = {
            "id", "title", "content", "up", "time", "lock"
    };

    public DiaryDao(Context context) {
        openHelper = new DiaryDatabase(context);
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void close() {
        openHelper.close();
    }

    public ContentValues newContent(DiaryEntity diary) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", diary.getTitle());
        contentValues.put("time", diary.getTime());
        contentValues.put("content", diary.getContent());
        contentValues.put("up", diary.getUp());
        contentValues.put("lock", diary.getLock());
        return contentValues;
    }

    public Long insert(DiaryEntity diary) {
        open();
        Long id = database.insert("diary", null, newContent(diary));
        close();
        return id;
    }


    @SuppressLint("Range")
    public List<DiaryEntity> getList(String title) {
        open();
        Cursor cursor;
        if (title != null && title.length() != 0) {
            cursor = database.query("diary", columns, "title like?", new String[]{"%"+title+"%"}, null, null, "id");
        } else {
            cursor = database.query("diary", columns, null, null, null, null, "id");
        }
        List<DiaryEntity> diaryEntities1 = new ArrayList<>();
        List<DiaryEntity> diaryEntities2 = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DiaryEntity diary = new DiaryEntity();
                diary.setId(cursor.getLong(cursor.getColumnIndex("id")));
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setTime(cursor.getString(cursor.getColumnIndex("time")));
                Integer up = cursor.getInt(cursor.getColumnIndex("up"));
                diary.setUp(up);
                if (up == 1) {
                    diaryEntities1.add(diary);
                } else {
                    diaryEntities2.add(diary);
                }
            }
        }
        close();
        List<DiaryEntity> list = new ArrayList<>();
        list.addAll(diaryEntities1);
        list.addAll(diaryEntities2);
        return list;
    }

    @SuppressLint("Range")
    public DiaryEntity getById(Long id) {
        open();
        Cursor cursor = database.query("diary", columns, "id=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        DiaryEntity diary = new DiaryEntity();
        diary.setId(cursor.getLong(cursor.getColumnIndex("id")));
        diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
        diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        diary.setUp(cursor.getInt(cursor.getColumnIndex("up")));
        diary.setLock(cursor.getInt(cursor.getColumnIndex("lock")));
        diary.setTime(cursor.getString(cursor.getColumnIndex("time")));
        close();
        return diary;
    }


    public int delete(Long id) {
        open();
        int res = database.delete("diary", "id=?", new String[]{String.valueOf(id)});
        close();
        return res;
    }

    public int deleteList(List<Integer> ids) {
        open();
        int res = 0;
        if (ids != null && ids.size() != 0) {
            for (Integer id : ids) {
                res = database.delete("diary", "id=?", new String[]{String.valueOf(id)});
            }
        }
        close();
        return res;
    }

    public int update(DiaryEntity diary) {
        open();
        int res = database.update("diary", newContent(diary), "id=?", new String[]{String.valueOf(diary.getId())});
        close();
        return res;
    }
}
