package com.lqs.kuaishou.kuaishou1801.cache.mode;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;


//在数据库中创建一个表，这个表存储用户观看的历史记录
@Entity
public class HistoryEntity {

    @Id(autoincrement = true)
    Long id;


    String imageUrl;
    String videoUrl;
    String userId;
    long time;//时间戳，按照时间戳进行排序
    @Generated(hash = 1659941041)
    public HistoryEntity(Long id, String imageUrl, String videoUrl, String userId,
            long time) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.videoUrl = videoUrl;
        this.userId = userId;
        this.time = time;
    }
    @Generated(hash = 1235354573)
    public HistoryEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getVideoUrl() {
        return this.videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
}
