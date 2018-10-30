package com.qgstudio.anywork.notice.data;

import java.lang.reflect.Method;

public class Notice extends Message {
    public int messageId;
    public String title;
    public String content;
    public String publisher;
    public String createTime;
    public int status;// 0为未读，1为已读
    public final static int STATUS_READED = 1;
    public final static int STATUS_UNREADED = 0;

    @Override
    public String toString() {
        return "Notice{" +
                "messageId=" + messageId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publisher='" + publisher + '\'' +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
