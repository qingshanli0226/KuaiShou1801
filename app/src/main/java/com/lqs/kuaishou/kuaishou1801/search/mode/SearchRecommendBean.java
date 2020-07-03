package com.lqs.kuaishou.kuaishou1801.search.mode;

import java.util.List;

public class SearchRecommendBean {


    /**
     * code : 200
     * message : 数据获取成功
     * result : [{"name":"李一凡","rId":"1000101"},{"name":"韩红","rId":"1000102"},{"name":"高晓松","rId":"1000103"},{"name":"周星驰","rId":"1000104"},{"name":"陈道明","rId":"1000105"},{"name":"陈宝国","rId":"1000106"},{"name":"朱之文","rId":"1000107"},{"name":"任贤齐","rId":"1000108"}]
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
         * name : 李一凡
         * rId : 1000101
         */

        private String name;
        private String rId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRId() {
            return rId;
        }

        public void setRId(String rId) {
            this.rId = rId;
        }
    }
}
