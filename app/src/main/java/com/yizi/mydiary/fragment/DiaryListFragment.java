package com.yizi.mydiary.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yizi.mydiary.R;
import com.yizi.mydiary.activity.DiaryEditActivity;
import com.yizi.mydiary.adapter.DiaryAdapter;
import com.yizi.mydiary.dao.DiaryDao;
import com.yizi.mydiary.dao.ImageDao;
import com.yizi.mydiary.entity.DiaryEntity;

import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 首页进行日记列表展示的fragment
*/
public class DiaryListFragment extends Fragment implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener {
    private View view;
    private FloatingActionButton floatingActionButton;
    DiaryDao diaryDao;
    ImageDao imageDao;
    ListView listView;
    SearchView searchView;

    List<DiaryEntity> diaryEntities;
    DiaryAdapter diaryAdapter;

    /**
     *@Author: create by ziyi
     *@Function: 界面初始胡啊，同时初始胡啊数据库相关操作
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diaryDao = new DiaryDao(this.getActivity());
        imageDao = new ImageDao(this.getActivity());
    }

     /**
      *@Author: create by ziyi
      *@Function: 界面元素进行初始化后，同时初始化界面元素
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_diary, container, false);
        }

        //设置适配器
        listView = view.findViewById(R.id.diary_list_view);
        init("");
        searchView = view.findViewById(R.id.search_view);
        /**
         *@Author: create by ziyi
         *@Function: 监听搜索框变化，在搜索框提交搜索文字以及搜索框文字实时变化
        */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                init(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                init(s);
                return false;
            }
        });
        return view;
    }

    /**
     *@Author: create by ziyi
     *@Function: 当界面再次出现的时候刷新，用于界面返回刷新
    */
    @Override
    public void onResume() {
        super.onResume();
        init("");
    }
    /**
     *@Author: create by ziyi
     *@Function: 这里适配器强制init刷新，我不知道为什么监听适配器变化失效，所以用init强行刷一下
    */
    public void init(String s) {
        diaryEntities = diaryDao.getList(s);
        diaryAdapter = new DiaryAdapter(diaryEntities, getActivity());
        //5、将适配器加载到控件中
        listView.setAdapter(diaryAdapter);
        listView.setOnItemLongClickListener(this);
        listView.setOnItemClickListener(this);
    }


    /**
     *@Author: create by ziyi
     *@Function: 刷新适配器
    */
    public void refresh(String s) {
        diaryEntities = diaryDao.getList(s);
        diaryAdapter.notifyDataSetChanged();
    }

    /**
     *@Author: create by ziyi
     *@Function: 长按删除日记
    */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity(), R.style.dialogStyle)
                .setIcon(null)//设置标题的图片
                .setMessage("确定删除此记录吗？")//设置对话框的内容
                //设置对话框的按钮
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        diaryDao.delete(diaryEntities.get(position).getId());
                        imageDao.delete(diaryEntities.get(position).getId());
                        diaryEntities.remove(position);
                        diaryAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        return true;
    }

    /**
     *@Author: create by ziyi
     *@Function: 控制浮动添加按钮
    */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        floatingActionButton = (FloatingActionButton) getActivity().findViewById(R.id.diaryFloatingButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiaryEditActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     *@Author: create by ziyi
     *@Function: 点击item进入日记activity
    */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), DiaryEditActivity.class);
        intent.putExtra("diaryId", diaryAdapter.getItem(i).getId());
        startActivity(intent);
    }
}

