package com.yizi.mydiary.entity;

import java.io.Serializable;

/**
 *@Author: create by ziyi
 *@Function: 手写图片类
*/
public class ImageEntity implements Serializable {
    /*图片id*/
    Long id;
    /*关联日记id*/
    Long diaryId;
    /*图片内容*/
    String src;

    public ImageEntity(Long id, Long diaryId, String src) {
        this.id = id;
        this.diaryId = diaryId;
        this.src = src;
    }

    public ImageEntity() {
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", diaryId=" + diaryId +
                ", src='" + src + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Long diaryId) {
        this.diaryId = diaryId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
