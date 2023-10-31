package com.yizi.mydiary;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.yizi.mydiary.fragment.DiaryListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *@Author: create by ziyi
 *@Function: 首页展示
*/
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private RadioButton nav_plan, nav_diary;
    private RadioGroup rg_group;
    private List<Fragment> fragments;
    private int position = 0;

    /**
     *@Author: create by ziyi
     *@Function: 初始化创建
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     *@Author: create by ziyi
     *@Function: 界面元素初始化
    */
    public void init() {
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        nav_plan = findViewById(R.id.navigation_plan);
        nav_diary = findViewById(R.id.navigation_diary);
        rg_group = findViewById(R.id.rg_group);

        //默认选中第一个
        nav_diary.setSelected(true);
        rg_group.setOnCheckedChangeListener(this);

        //初始化fragment
        initFragment();

        //默认布局，选第一个
        defaultFragment();
    }

    /**
     *@Author: create by ziyi
     *@Function: fragment元素初始化
    */
    private void defaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_layout, fragments.get(0));
        transaction.commit();
    }

    /**
     *@Author: create by ziyi
     *@Function: 控制底部导航栏元素选择
    */
    private void setSelected() {
        nav_diary.setSelected(false);
        nav_plan.setSelected(false);
    }

    /**
     *@Author: create by ziyi
     *@Function: 初始化内部fragment元素列表
    */
    private void initFragment() {
        fragments = new ArrayList<>();
        fragments.add(0, new DiaryListFragment());

    }

    /**
     *@Author: create by ziyi
     *@Function: 监听底部导航栏radiobutton组变化
    */
    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        //获取fragment管理类对象
        FragmentManager fragmentManager = getSupportFragmentManager();
        //拿到fragmentManager的触发器
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (i) {
            case R.id.navigation_diary:
                position = 0;
                //调用replace方法，将fragment,替换到fragment_layout这个id所在UI，或者这个控件上面来
                //这是创建replace这个事件，如果想要这个事件执行，需要把这个事件提交给触发器
                //用commit()方法
                transaction.replace(R.id.fragment_layout, fragments.get(0));
                //将所有导航栏设成默认色
                setSelected();
                nav_diary.setSelected(true);
                break;
            case R.id.navigation_plan:
                position = 1;
                transaction.replace(R.id.fragment_layout, fragments.get(1));
                //将所有导航栏设成默认色
                setSelected();
                nav_plan.setSelected(true);
                break;
        }
        //事件的提交
        transaction.commit();
    }
}
