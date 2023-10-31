package com.yizi.mydiary.entity;

import java.io.Serializable;

import java.util.Objects;

/**
 * @Author: create by ziyi
 * @Function: 日记类
 */
public class DiaryEntity implements Serializable {
    /*自增id*/
    Long id;
    /*标题*/
    String title;
    /*更新时间*/
    String time;
    /*内容*/
    String content;
    /*是否置顶*/
    Integer up;
    /*是否锁定内容*/
    Integer lock;

    public DiaryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DiaryEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", up=" + up +
                ", lock=" + lock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiaryEntity that = (DiaryEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(time, that.time) && Objects.equals(content, that.content) && Objects.equals(up, that.up);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, time, content, up);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    public DiaryEntity(Long id, String title, String time, String content, Integer up, Integer lock) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.up = up;
        this.lock = lock;
    }
}
