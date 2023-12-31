package com.yizi.mydiary.service;

import android.app.IntentService;
import android.content.Intent;
import android.media.MediaPlayer;

import com.yizi.mydiary.R;

/**
 *@Author: create by ziyi
 *@Function: 音乐服务
*/
public class MusicIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_FOO = "com.example.notebook.action.FOO";
    public static final String ACTION_BAZ = "com.example.notebook.action.BAZ";

    // action声明 这里有五个背景音乐
    public static final String ACTION_MUSIC_1 = "com.example.notebook.action.music1";
    public static final String ACTION_MUSIC_2 = "com.example.notebook.action.music2";
    public static final String ACTION_MUSIC_3 = "com.example.notebook.action.music3";
    public static final String ACTION_MUSIC_4 = "com.example.notebook.action.music4";
    public static final String ACTION_MUSIC_5 = "com.example.notebook.action.music5";
    public static final String ACTION_MUSIC_6 = "com.example.notebook.action.music6";
    public static final String ACTION_MUSIC_7 = "stopMusic";

    // TODO: Rename parameters
    public static final String EXTRA_PARAM1 = "com.example.notebook.extra.PARAM1";
    public static final String EXTRA_PARAM2 = "com.example.notebook.extra.PARAM2";

    // 声明MediaPlayer对象
    private static MediaPlayer mediaPlayer;

    public MusicIntentService() {
        super("MusicIntentService");
    }

    /**
     *@Author: create by ziyi
     *@Function: 处理音乐选择相关
    */
    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        if (intent != null) {
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }

            // 根据intent设置的action来执行对应服务的操作
            if (ACTION_MUSIC_1.equals(action)) {
                handleActionMusic1();
            } else if (ACTION_MUSIC_2.equals(action)) {
                handleActionMusic2();
            } else if (ACTION_MUSIC_3.equals(action)) {
                handleActionMusic3();
            } else if (ACTION_MUSIC_4.equals(action)) {
                handleActionMusic4();
            } else if (ACTION_MUSIC_5.equals(action)) {
                handleActionMusic5();
            } else if (ACTION_MUSIC_6.equals(action)) {
                handleActionMusic6();
            } else if (ACTION_MUSIC_7.equals(action)) {
                handleActionStop();
            }
        }
    }

    /**
     *@Author: create by ziyi
     *@Function: 创建并播放音乐
    */
    private void handleActionMusic1() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.atlantia);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionMusic2() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.heartstrings);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionMusic3() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.home);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionMusic4() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.hymn_to_hope);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionMusic5() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.papillon);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionMusic6() {
        if (mediaPlayer != null) mediaPlayer.stop();
        // 根据音乐资源文件创建MediaPlayer对象 设置循环播放属性 开始播放
        mediaPlayer = MediaPlayer.create(this, R.raw.reflection);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void handleActionStop() {
        if (mediaPlayer != null) mediaPlayer.stop();
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
