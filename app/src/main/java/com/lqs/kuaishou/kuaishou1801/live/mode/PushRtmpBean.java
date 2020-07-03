package com.lqs.kuaishou.kuaishou1801.live.mode;

public class PushRtmpBean {


    /**
     * code : 200
     * message : 获取数据成功
     * result : rtmp://3891.livepush.myqcloud.com/live/3891_user_c9cd2916_3634?bizid=3891&txSecret=0d00cbeb7a1808408488e1d32caec1f2&txTime=5EFBD6DF
     */

    private String code;
    private String message;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
