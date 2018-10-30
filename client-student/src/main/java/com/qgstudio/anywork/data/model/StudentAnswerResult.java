package com.qgstudio.anywork.data.model;

/**
 * Created by chenyi on 17-7-28.
 */

public class StudentAnswerResult {

    private int id;
    private int position;
    private int type;
    private String content;

    public StudentAnswerResult(int type1, String content1) {
        super();
        type = type1;
        content = content1;
    }

    public StudentAnswerResult(StudentAnswerAnalysis analysis) {
        super();
        id = analysis.getQuestion().getQuestionId();
        type = analysis.getQuestion().getType();
        content = analysis.getIsTrue() == 1 ? "true" : "false";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(int type, String content) {
        String contentType;
        switch (type) {
            case 1:
                contentType = "选择题";
                break;
            case 2:
                contentType = "判断题";
                break;
            case 3:
                contentType = "填空题";
                break;
            case 4:
                contentType = "问答题";
                break;
            case 5:
                contentType = "编程题";
                break;
            case 6:
                contentType = "综合题";
                break;
            default:
                contentType = "";
        }
        this.content = contentType + "正确率：" + content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
