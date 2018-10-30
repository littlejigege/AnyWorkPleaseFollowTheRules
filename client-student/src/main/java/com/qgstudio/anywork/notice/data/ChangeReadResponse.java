package com.qgstudio.anywork.notice.data;

/**
 * Created by KK on 2018/9/15.
 */

public class ChangeReadResponse {
    private int state;
    private String stateInfo;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
