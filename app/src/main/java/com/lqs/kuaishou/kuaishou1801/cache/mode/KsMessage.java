package com.lqs.kuaishou.kuaishou1801.cache.mode;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

//通过该注解，可以生成一个数据库的表，在该表中存储信息
@Entity
public class KsMessage {

    @Id(autoincrement = true)
    Long id;//类型一定是大写的Long型

    String userId;
    String avatar;
    String content;

    boolean isRead;

    @Generated(hash = 1810402684)
    public KsMessage(Long id, String userId, String avatar, String content,
            boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.avatar = avatar;
        this.content = content;
        this.isRead = isRead;
    }

    @Generated(hash = 1188219642)
    public KsMessage() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
