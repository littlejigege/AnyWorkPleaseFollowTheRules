package com.qgstudio.anywork.networkcenter;

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
