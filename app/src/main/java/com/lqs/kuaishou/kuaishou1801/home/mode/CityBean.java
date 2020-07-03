package com.lqs.kuaishou.kuaishou1801.home.mode;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class CityBean {


    /**
     * code : 200
     * msg : 请求成功
     * result : [{"vedioUrl":"http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4","vedioId":1,"userId":10010,"coverImg":"http://image.tupian114.com/20121212/08002510.jpg"}]
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

    //Android通过Intent传递对象时，需要通过Parceable序列化对象.
    public static class ResultBean implements Parcelable {
        /**
         * vedioUrl : http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4
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

        @Override
        public int describeContents() {
            return 0;//默认0即可
        }

        //序列化对象方法
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(vedioUrl);
            dest.writeInt(vedioId);
            dest.writeInt(userId);
            dest.writeString(coverImg);
        }


        //实现反序列化
        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                ResultBean resultBean = new ResultBean();
                resultBean.setVedioUrl(source.readString());
                resultBean.setVedioId(source.readInt());
                resultBean.setUserId(source.readInt());
                resultBean.setCoverImg(source.readString());
                return resultBean;
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }
}
