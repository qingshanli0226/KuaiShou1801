package com.lqs.kuaishou.kuaishou1801.home.mode;

import java.util.List;

public class HomeBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : [{"vedioUrl":"/video/bw1.mp4","vedioId":1,"userId":10010,"coverImg":"http://image.tupian114.com/20121212/08002510.jpg"},{"vedioUrl":"/video/bw2.mp4","vedioId":2,"userId":10011,"coverImg":"http://upload.northnews.cn/2012/0228/1330419635343.jpg"},{"vedioUrl":"/video/bw3.mp4","vedioId":3,"userId":10012,"coverImg":"http://img.juimg.com/tuku/yulantu/110930/106-11093014452716.jpg"},{"vedioUrl":"/video/bw4.mp4","vedioId":4,"userId":10013,"coverImg":"http://yweb2.china.cnlive.com/cnlive/public/160519071448241_351.jpg"}]
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
         * vedioUrl : /video/bw1.mp4
         * vedioId : 1
         * userId : 10010
         * coverImg : http://image.tupian114.com/20121212/08002510.jpg
         */

        private String vedioUrl;
        private int vedioId;
        private int userId;
        private String coverImg;

        public String getVedioUrl() {
            return vedioUrl;
        }

        public void setVedioUrl(String vedioUrl) {
            this.vedioUrl = vedioUrl;
        }

        public int getVedioId() {
            return vedioId;
        }

        public void setVedioId(int vedioId) {
            this.vedioId = vedioId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }
    }
}
