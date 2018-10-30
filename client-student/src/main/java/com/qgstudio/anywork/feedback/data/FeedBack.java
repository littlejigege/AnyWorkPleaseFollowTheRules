package com.qgstudio.anywork.feedback.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedBack implements Serializable {
    @Expose(serialize = false)
    private String type;  //反馈类型
    @Expose(serialize = false)
    private String content;  //详细内容
    @Expose(serialize = false)
    private String module;  //模块
    @Expose(serialize = false)
    private String contantWay;//联系方式
    @SerializedName("description")
    private String  output;//合成信息
    public void buildOutput(){
        output = "反馈类型：" + type + "\n"
                + "详细内容：" + content + "\n"
                + "模块：" + module + "\n"
                + "联系方式：" + contantWay;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getContantWay() {
        return contantWay;
    }

    public void setContantWay(String contantWay) {
        this.contantWay = contantWay;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
