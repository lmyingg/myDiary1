package com.yizi.mydiary.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.yizi.mydiary.R;
import com.yizi.mydiary.entity.DiaryEntity;
import com.yizi.mydiary.entity.ImageEntity;

import java.io.File;
import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 自定义图片适配器，用于diary item内部手写图片展示
*/
public class ImageAdapter extends BaseAdapter {
    List<ImageEntity> list;
    Context context;

    public ImageAdapter(List<ImageEntity> list, Context context) {
        this.list = list;
        this.context=context;
    }

    public ImageAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.image_list, null);
        //得到是第几个，然后返回
        ImageEntity image = list.get(i);
        ImageView imageView = view.findViewById(R.id.list_image);
        imageView.setImageURI(Uri.fromFile(new File(image.getSrc())));
        return view;
    }
}
