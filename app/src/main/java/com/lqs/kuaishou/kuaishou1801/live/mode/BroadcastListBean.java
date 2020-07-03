package com.lqs.kuaishou.kuaishou1801.live.mode;

import java.util.List;

public class BroadcastListBean {

    /**
     * code : 200
     * message : 获取数据成功
     * result : [{"name":"1111111","livePushUrl":"rtmp://3891.livepush.myqcloud.com/live/3891_user_950c9268_0b73?bizid=3891&txSecret=0a7f729486388eff382b4d42cb1bc63f&txTime=5EFB5E86","livePlayUrl":"rtmp://3891.liveplay.myqcloud.com/live/3891_user_950c9268_0b73","avatar":null},{"name":"222222","livePushUrl":"rtmp://3891.livepush.myqcloud.com/live/3891_user_0fccadc7_2c30?bizid=3891&txSecret=ef4cb59dcfd4264835554048d25913fc&txTime=5EFB5E8B","livePlayUrl":"rtmp://3891.liveplay.myqcloud.com/live/3891_user_0fccadc7_2c30","avatar":null}]
     */

    private String code;
    private String message;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 1111111
         * livePushUrl : rtmp://3891.livepush.myqcloud.com/live/3891_user_950c9268_0b73?bizid=3891&txSecret=0a7f729486388eff382b4d42cb1bc63f&txTime=5EFB5E86
         * livePlayUrl : rtmp://3891.liveplay.myqcloud.com/live/3891_user_950c9268_0b73
         * avatar : null
         */

        private String name;
        private String livePushUrl;
        private String livePlayUrl;
        private Object avatar;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLivePushUrl() {
            return livePushUrl;
        }

        public void setLivePushUrl(String livePushUrl) {
            this.livePushUrl = livePushUrl;
        }

        public String getLivePlayUrl() {
            return livePlayUrl;
        }

        public void setLivePlayUrl(String livePlayUrl) {
            this.livePlayUrl = livePlayUrl;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }
    }
}
