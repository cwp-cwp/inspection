package com.puzek.platform.inspection.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Msg implements Serializable{
    // 返回状态，Success成功， Failed失败
    private String result;
    // 返回成功和失败的信息
    private String msg;
    // 要返回给浏览器的数据
    private Map<String, Object> extend = new HashMap<>();

    public Msg() {
    }

    public Msg(String result, String msg, Map<String, Object> extend) {
        this.result = result;
        this.msg = msg;
        this.extend = extend;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public static Msg success(String msg) {
        Msg ret = new Msg();
        ret.setResult(AllConstant.RESULT_SUCCESS);
        ret.setMsg(msg);
        return ret;
    }

    public static Msg success() {
        Msg ret = new Msg();
        ret.setResult(AllConstant.RESULT_SUCCESS);
        return ret;
    }

    public static Msg failed(String msg) {
        Msg ret = new Msg();
        ret.setResult(AllConstant.RESULT_FAILED);
        ret.setMsg(msg);
        return ret;
    }

    public static Msg failed() {
        Msg ret = new Msg();
        ret.setResult(AllConstant.RESULT_FAILED);
        return ret;
    }

    public Msg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }
}
