package com.yizi.mydiary.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yizi.mydiary.R;
import com.yizi.mydiary.adapter.ImageAdapter;
import com.yizi.mydiary.dao.DiaryDao;
import com.yizi.mydiary.dao.ImageDao;
import com.yizi.mydiary.entity.DiaryEntity;
import com.yizi.mydiary.entity.ImageEntity;
import com.yizi.mydiary.service.MusicIntentService;
import com.yizi.mydiary.view.WriteDialogListener;
import com.yizi.mydiary.view.WritePadDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: create by ziyi
 * @Function: 掌握日记的基本增删改查界面
 */

public class DiaryEditActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    private DiaryDao dao;
    private ImageDao imageDao;

    //画面参数
    EditText diaryTitle;
    EditText diaryContent;
    TextView diaryTime;
    Button lockButton;
    Button upButton;
    Button deleteButton;
    Button musicButton;
    Button writeButton;
    Drawable upIcon;
    Drawable upedIcon;
    Drawable lockIcon;
    Drawable lockedIcon;
    Drawable musicIcon;
    Drawable musicedIcon;
    Intent intentMusic;
    Bitmap mSignBitmap;
    ListView imageListView;

    //up和lock标签是用来表明假如在编辑的时候的
    Integer up = 0;
    Integer lock = 0;
    Long id;
    DiaryEntity diaryEntity;
    ImageAdapter imageAdapter;
    ArrayList<ImageEntity> imageEntities;
    ArrayList<ImageEntity> imageEntities2;

    private static final int REQUEST_CODE = 1024;


    /**
     * @Author: create by ziyi
     * @Function: 在需要保存书写的时候，进行询问是否获取sd卡的写权限
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * @Author: create by ziyi
     * @Function: 初次创建函数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Intent intent = getIntent();
        this.dao = new DiaryDao(this);
        this.imageDao = new ImageDao(this);

        id = intent.getLongExtra("diaryId", 0);

        init();
    }

    /**
     * @Author: create by ziyi
     * @Function: 图片适配器的初始化
     */
    public void handleAdapter() {
        if (id == 0) {
            this.diaryEntity = null;
            this.imageEntities = new ArrayList<>();
        } else {
            this.diaryEntity = dao.getById(id);
            this.imageEntities = (ArrayList<ImageEntity>) imageDao.getList(id);
            this.imageEntities2 = new ArrayList<>();
        }
        imageAdapter = new ImageAdapter(imageEntities, getApplicationContext());
        imageListView.setAdapter(imageAdapter);
        imageListView.setOnItemLongClickListener(this);

    }

    /**
     * @Author: create by ziyi
     * @Function: 元素初始化
     */
    public void init() {
        lockedIcon = getResources().getDrawable(R.drawable.lock_selected);
        lockedIcon.setBounds(0, 0, lockedIcon.getMinimumWidth(), lockedIcon.getMinimumHeight());
        lockIcon = getResources().getDrawable(R.drawable.lock);
        lockIcon.setBounds(0, 0, lockIcon.getMinimumWidth(), lockIcon.getMinimumHeight());
        upedIcon = getResources().getDrawable(R.drawable.up_selected);
        upedIcon.setBounds(0, 0, upedIcon.getMinimumWidth(), upedIcon.getMinimumHeight());
        upIcon = getResources().getDrawable(R.drawable.up);
        upIcon.setBounds(0, 0, upIcon.getMinimumWidth(), upIcon.getMinimumHeight());
        musicIcon = getResources().getDrawable(R.drawable.yinfu);
        musicIcon.setBounds(0, 0, musicIcon.getMinimumWidth(), musicIcon.getMinimumHeight());
        musicedIcon = getResources().getDrawable(R.drawable.yinfu_selected);
        musicedIcon.setBounds(0, 0, musicedIcon.getMinimumWidth(), musicedIcon.getMinimumHeight());


        diaryTitle = findViewById(R.id.input_diary_title);
        diaryTime = findViewById(R.id.text_time);
        diaryContent = findViewById(R.id.input_diary_content);
        lockButton = findViewById(R.id.diary_lock);
        upButton = findViewById(R.id.diary_up);
        deleteButton = findViewById(R.id.diary_delete);
        musicButton = findViewById(R.id.diary_music);
        writeButton = findViewById(R.id.diary_write);
        imageListView = findViewById(R.id.diary_image);

        handleAdapter();

        if (this.diaryEntity != null) {
            diaryTitle.setText(diaryEntity.getTitle());
            diaryContent.setText(diaryEntity.getContent());
            diaryTime.setText(diaryEntity.getTime());
            this.lock = diaryEntity.getLock();
            this.up = diaryEntity.getUp();
            setLockIcon();
            setUpIcon();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            diaryTime.setText(format.format(new Date()));
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 返回按键事件处理，如果是home按键不会保存，返回按键自动保存改变
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            //todo 不知道要不要做处理，我不懂，是否加个弹窗询问要保存
            handleSave();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * @Author: create by ziyi
     * @Function: 保存分发，判断是保存还是更新
     */
    public void handleSave() {
        if (this.diaryEntity == null) {
            save();
        } else {
            update();
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理删除按钮点击
     */
    public void onClickDelete(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialogStyle)
                .setIcon(null)//设置标题的图片
                .setMessage("确定删除吗？")//设置对话框的内容
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
                        if (diaryEntity == null) {
                            Toast.makeText(DiaryEditActivity.this, "都没有保存有什么好删除的", Toast.LENGTH_SHORT).show();
                        } else {
                            delete();
                            finish();
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理点击置顶按钮事件
     */
    public void onClickUp(View view) {
        handleButtonUp();
        if (this.diaryEntity != null) {
            update();
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理点击手写按钮事件
     */
    public void onClickWrite(View view) {
        //申请权限
        requestPermission();
        if (this.lock == 1) {
            Toast.makeText(this, "当前处于不能编辑状态", Toast.LENGTH_SHORT).show();
        } else {
            WritePadDialog mWritePadDialog = new WritePadDialog(
                    DiaryEditActivity.this, new WriteDialogListener() {
                @Override
                public void onPaintDone(Object object) {
                    mSignBitmap = (Bitmap) object;
                    createSignFile();
                }
            });
            mWritePadDialog.show();
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 创建手写文件并保存入sd卡
     */
    private void createSignFile() {
        ByteArrayOutputStream baos = null;
        FileOutputStream fos = null;
        String path = null;
        File file = null;
        try {
            path = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".jpg";
            file = new File(path);
            fos = new FileOutputStream(file);
            baos = new ByteArrayOutputStream();
            //如果设置成Bitmap.compress(CompressFormat.JPEG, 100, fos黑/baos白) 图片的背景都是黑色的
            mSignBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            byte[] b = baos.toByteArray();
            if (b != null) {
                fos.write(b);
                handleInsertImage(path);
            }
        } catch (IOException e) {
            Toast.makeText(this, "保存异常", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理手写图片插入的时候，数据库相关处理
     */
    public void handleInsertImage(String path) {
        //设置插入该列表
        if (this.diaryEntity != null) {
            ImageEntity image = new ImageEntity(null, diaryEntity.getId(), path);
            imageEntities.add(image);
            imageEntities2.add(image);
        } else {
            imageEntities.add(new ImageEntity(null, null, path));
        }
        //刷新适配器
        Log.d("imageEntitie", imageEntities.toString());
        imageAdapter.notifyDataSetChanged();
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理音乐播放点击功能
     */
    public void onClickMusic(View view) {
        final String[] musics = {"atlantia", "heartstrings", "home", "hyme to hope", "papillon", "reflection", "停止播放"};
        new AlertDialog.Builder(DiaryEditActivity.this, R.style.dialogListStyle)
                .setTitle("播放音乐")
                .setItems(musics, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_1 = MusicIntentService.ACTION_MUSIC_1;
                                // 设置action
                                intentMusic.setAction(action_1);
                                startService(intentMusic);
                                break;
                            case 1:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_2 = MusicIntentService.ACTION_MUSIC_2;
                                // 设置action
                                intentMusic.setAction(action_2);
                                startService(intentMusic);
                                break;
                            case 2:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_3 = MusicIntentService.ACTION_MUSIC_3;
                                // 设置action
                                intentMusic.setAction(action_3);
                                startService(intentMusic);
                                break;
                            case 3:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_4 = MusicIntentService.ACTION_MUSIC_4;
                                // 设置action
                                intentMusic.setAction(action_4);
                                startService(intentMusic);
                                break;
                            case 4:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_5 = MusicIntentService.ACTION_MUSIC_5;
                                // 设置action
                                intentMusic.setAction(action_5);
                                startService(intentMusic);
                                break;
                            case 5:
                                musicButton.setCompoundDrawables(null, musicedIcon, null, null);
                                // 启动服务播放背景音乐
                                intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
                                String action_6 = MusicIntentService.ACTION_MUSIC_6;
                                // 设置action
                                intentMusic.setAction(action_6);
                                startService(intentMusic);
                                break;
                            case 6:
                                musicButton.setCompoundDrawables(null, musicIcon, null, null);
                                stopMusic();
                                break;
                        }
                    }
                }).create().show();
    }

    /**
     * @Author: create by ziyi
     * @Function: 音乐播放停止功能
     */
    public void stopMusic() {
        intentMusic = new Intent(DiaryEditActivity.this, MusicIntentService.class);
        String action_7 = MusicIntentService.ACTION_MUSIC_7;
        // 设置action
        intentMusic.setAction(action_7);
        startService(intentMusic);
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理锁定按钮的点击
     */
    public void onClickLock(View view) {
        //动态设置按钮状态
        handleButtonLock();
        //如果是刚刚创建的新笔记，那么lock值将在保存时直接保存
        //如果不是刚刚创建的笔记，那么需要保存新的变化
        if (this.diaryEntity != null) {
            update();
        }
    }


    /**
     * @Author: create by ziyi
     * @Function: 锁定按钮点击的是否相关元素变化
     */
    public void handleButtonLock() {
        if (this.lock == 0) {
            this.lock = 1;
            setLockIcon();
        } else {
            this.lock = 0;
            setLockIcon();
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 掌握锁定点击的时，设置不可编辑状态
     */
    public void setLockIcon() {
        if (this.lock == 1) {
            lockButton.setCompoundDrawables(null, lockedIcon, null, null);
            diaryTitle.setFocusable(false);
            diaryContent.setFocusable(false);
        } else {
            lockButton.setCompoundDrawables(null, lockIcon, null, null);
            diaryTitle.setFocusable(true);
            diaryTitle.setFocusableInTouchMode(true);
            diaryContent.setFocusable(true);
            diaryContent.setFocusableInTouchMode(true);
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 设置点击置顶按钮的时候，相关元素的变化
     */
    public void handleButtonUp() {
        if (this.up == 0) {
            this.up = 1;
            upButton.setCompoundDrawables(null, upedIcon, null, null);
        } else {
            this.up = 0;
            upButton.setCompoundDrawables(null, upIcon, null, null);
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 将up和元素变化进行绑定
     */
    public void setUpIcon() {
        if (this.up == 1) {
            upButton.setCompoundDrawables(null, upedIcon, null, null);
        } else {
            upButton.setCompoundDrawables(null, upIcon, null, null);
        }
    }

    /**
     * @Author: create by ziyi
     * @Function: 数据库新增操作
     */
    public long save() {
        DiaryEntity diary = new DiaryEntity();
        String title = diaryTitle.getText().toString();
        String content = diaryContent.getText().toString();

        if (content.length() == 0 && imageEntities.size() == 0) {
            if (imageEntities.size() == 0) {
                return -1;
            } else {
                if (title.length() == 0) {
                    title = "default";
                }
            }
        }
        if (title.length() == 0) {
            if (content.length() <= 10) {
                title = content;
            } else {
                title = content.substring(0, 10) + "…";
            }
        }


        diary.setTitle(title);
        diary.setContent(content);
        diary.setUp(up);
        diary.setLock(lock);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        diary.setTime(format.format(new Date()));

        Long id = dao.insert(diary);
        for (ImageEntity image : imageEntities) {
            image.setDiaryId(id);
            imageDao.insert(image);
        }
        return id;
    }

    /**
     * @Author: create by ziyi
     * @Function: 数据库删除操作
     */
    public int delete() {
        stopMusic();
        //删除
        Integer res = dao.delete(diaryEntity.getId());
        imageDao.delete(diaryEntity.getId());
        return res;
    }

    /**
     * @Author: create by ziyi
     * @Function: 数据库更新操作
     */
    public int update() {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        diaryEntity.setTitle(diaryTitle.getText().toString());
        diaryEntity.setContent(diaryContent.getText().toString());
        diaryEntity.setUp(up);
        diaryEntity.setLock(lock);
        diaryEntity.setTime(format.format(new Date()));

        for (ImageEntity image : imageEntities2) {
            imageDao.insert(image);
        }
        return dao.update(diaryEntity);
    }

    /**
     * @Author: create by ziyi
     * @Function: 处理listView中图片item的长按删除
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialogStyle)
                .setIcon(null)//设置标题的图片
                .setMessage("确定删除此手写记录吗？")//设置对话框的内容
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
                        handleDeleteImage(i);
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
        return false;
    }

    /**
     * @Author: create by ziyi
     * @Function: 图片item删除功能
     */
    public void handleDeleteImage(int i) {
        if (this.diaryEntity == null) {
            imageEntities.remove(i);
        } else {
            //现在正常的序列中删除
            //如果是新增的情况，那么删除就删除了，之后就不用加进去了
            //如果不是新增的情况，
            //不是新增的而且在数据库中存在此项，需要直接找到此项并删除
            //那么先要判断是否此项是新有的，然后一定要拿到这项的id,
            //判断此项是否在数据库的依据是是否存在id，如果有id，那么就在此项中删除
            Long id = imageEntities.get(i).getId();
            if (id != null) {
                //如果id不等于null，那么数据库中删除这条记录
                imageDao.deleteById(id);
            } else {
                //如果id=null，那么说明是新加入的id，需要在entity中移除，由于是新加入的，所以
                //在2列表中的为i-1.size()
                imageEntities2.remove(i - imageEntities.size() + 1);
            }
            imageEntities.remove(i);
        }
        imageAdapter.notifyDataSetChanged();
    }
}