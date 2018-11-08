package com.qgstudio.aniwork.networkcenter;

public class NetWorkCenter {
    private static NetWorkCenter netWorkCenter;

    public static NetWorkCenter getInstance() {
        if (netWorkCenter == null) {
            netWorkCenter = new NetWorkCenter();
        }
        return netWorkCenter;
    }
    public void addWork(Work work){

    }
}
