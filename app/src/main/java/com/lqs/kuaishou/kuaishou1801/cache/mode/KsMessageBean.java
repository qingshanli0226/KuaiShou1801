package com.lqs.kuaishou.kuaishou1801.cache.mode;

import java.util.List;

public class KsMessageBean {


    /**
     * code : 200
     * msg : 请求成功
     * result : [{"userId":"风之子","avatar":"http://image2.sina.com.cn/ty/o/p/2005-08-07/U333P6T12D1707715F44DT20050807230922.jpg","content":"什么时候来南京啊?","isRead":false},{"userId":"夏言","avatar":"http://upload.northnews.cn/2012/0228/1330419635343.jpg","content":"我最近发了两个视频，帮关注","isRead":false},{"userId":"岩松","avatar":"http://06imgmini.eastday.com/mobile/20180827/20180827230103_4b8017aa5fe754c1e44960747e375068_15.jpeg","content":"路灯下的小姑娘","isRead":false},{"userId":"费正清","avatar":"http://05img.shaqm.com/mobile/20180525/20180525021411_a5a608a79ee96949e269619cbf45f1aa_13.jpeg","content":"一剪梅","isRead":false}]
     */

    private int code;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * userId : 风之子
         * avatar : http://image2.sina.com.cn/ty/o/p/2005-08-07/U333P6T12D1707715F44DT20050807230922.jpg
         * content : 什么时候来南京啊?
         * isRead : false
         */

        private String userId;
        private String avatar;
        private String content;
        private boolean isRead;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }
    }
}
