package com.yizi.mydiary.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yizi.mydiary.database.DiaryDatabase;
import com.yizi.mydiary.database.ImageDatabase;
import com.yizi.mydiary.entity.DiaryEntity;
import com.yizi.mydiary.entity.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 图片的数据库操作
*/
public class ImageDao {
    SQLiteOpenHelper openHelper;
    SQLiteDatabase database;

    private static final String[] columns = {
            "id", "diary_id", "src"
    };

    public ImageDao(Context context) {
        openHelper = new ImageDatabase(context);
    }

    public void open() {
        database = openHelper.getWritableDatabase();
    }

    public void close() {
        openHelper.close();
    }


    public ContentValues newContent(ImageEntity image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("diary_id", image.getDiaryId());
        contentValues.put("src", image.getSrc());
        return contentValues;
    }

    public Long insert(ImageEntity image) {
        open();
        Long id = database.insert("image", null, newContent(image));
        close();
        return id;
    }

    @SuppressLint("Range")
    public List<ImageEntity> getList(Long diaryId) {
        open();
        Cursor cursor;

        cursor = database.query("image", columns, "diary_id=?", new String[]{String.valueOf(diaryId)}, null, null, "id");

        List<ImageEntity> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ImageEntity image = new ImageEntity();
                image.setId(cursor.getLong(cursor.getColumnIndex("id")));
                image.setSrc(cursor.getString(cursor.getColumnIndex("src")));
                list.add(image);
            }
        }
        close();
        return list;
    }

    public int delete(Long diaryId) {
        open();
        int res=database.delete("image", "diary_id=?", new String[]{String.valueOf(diaryId)});
        close();
        return res;
    }

    public int deleteById(Long id) {
        open();
        int res=database.delete("image", "id=?", new String[]{String.valueOf(id)});
        close();
        return res;
    }
}
