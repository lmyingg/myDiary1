package com.yizi.mydiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yizi.mydiary.R;
import com.yizi.mydiary.entity.DiaryEntity;

import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 自定义适配器，用于首页列表展示日记
*/
public class DiaryAdapter extends BaseAdapter {
    List<DiaryEntity> list;
    Context context;

    public DiaryAdapter() {

    }

    public DiaryAdapter(List<DiaryEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DiaryEntity getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.diary_list, null);
        //得到是第几个，然后返回
        DiaryEntity diary = list.get(i);
        TextView titleView = view.findViewById(R.id.list_title);
        titleView.setText(diary.getTitle());
        TextView timeView = view.findViewById(R.id.list_time);
        timeView.setText(diary.getTime());

        return view;
    }
}
