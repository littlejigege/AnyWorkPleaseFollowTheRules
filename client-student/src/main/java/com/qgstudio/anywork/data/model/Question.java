package com.qgstudio.anywork.data.model;

import java.io.Serializable;

/**
 * 问题实体
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    private int questionId;     //问题id
    private int type;           //题目类型  1-选择题 2-判断题 3-填空题 4-问答题 5-编程题 6-综合题

    public enum Type {
        SELECT("单选题", 1),
        TRUE_OR_FALSE("判断题", 2),
        FILL_BLANK("填空题", 3),
        SHORT_ANSWER("问答题", 4),
        UNKNOWN("未知题型", 5);
        public String string;
        public int code;

        Type(String string, int code) {
            this.string = string;
            this.code = code;
        }

        public static Type getByCode(int code) {
            switch (code) {
                case 1:
                    return SELECT;
                case 2:
                    return TRUE_OR_FALSE;
                case 3:
                    return FILL_BLANK;
                case 4:
                    return SHORT_ANSWER;
                default:
                    return UNKNOWN;
            }
        }
    }

    private String a;
    private String b;
    private String c;
    private String d;
    private String key;         //答案
    private String content;     //题目内容
    private int socre;          //分数
    private int testpaperId;    //试卷id
    private int other;          //填空题个数
    private String studentAnswer;
    private String analysis;

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public Question() {
    }

    // get & set

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getType() {
        return type;
    }

    public Type getEnumType() {
        return Type.getByCode(type);
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSocre() {
        return socre;
    }

    public void setSocre(int socre) {
        this.socre = socre;
    }

    public int gettestpaperId() {
        return testpaperId;
    }

    public void settestpaperId(int testpaperId) {
        this.testpaperId = testpaperId;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    //toString

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", type=" + type +
                ", A='" + a + '\'' +
                ", B='" + b + '\'' +
                ", C='" + c + '\'' +
                ", D='" + d + '\'' +
                ", key='" + key + '\'' +
                ", content='" + content + '\'' +
                ", socre=" + socre +
                ", testpaperId=" + testpaperId +
                ", other=" + other +
                '}';
    }
}
