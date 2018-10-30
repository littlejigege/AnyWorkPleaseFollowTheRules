package com.qgstudio.anywork.data;

/**
 * Http返回的数据格式预定义
 * Created by chenyi on 2017/4/10.
 */

public class ResponseResult<T> {
    private int state;//状态
    private String stateInfo;//提示
    private T data;//数据

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
