package com.qgstudio.anywork.notice.data;

public abstract class Message {
    static public final int TYPE_ONLINE_COUNT = 1;
    static public final int TYPE_NOTICE = 2;
    public int type;// int类型。注明该消息为公告推送，1为在线人数，2是公告推送
}
