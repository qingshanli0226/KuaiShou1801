package com.lqs.kuaishou.kuaishou1801.cache.mode;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

//在数据库中创建一个表，来存储搜索历史记录
@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    Long id;

    long time;//用来排序的

    String searchContent;//搜索的内容

    @Generated(hash = 500440015)
    public SearchHistory(Long id, long time, String searchContent) {
        this.id = id;
        this.time = time;
        this.searchContent = searchContent;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSearchContent() {
        return this.searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
