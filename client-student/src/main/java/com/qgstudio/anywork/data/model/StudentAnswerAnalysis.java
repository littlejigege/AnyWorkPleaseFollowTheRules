package com.qgstudio.anywork.data.model;

import java.io.Serializable;

/**
 * Created by logan on 2017/7/10.
 */
public class StudentAnswerAnalysis implements Serializable {

    private int studentAnswerAnalysisId;       //分析id
    private int studentId;              //学生id
    private Question question;        //试题
    private String studentAnswer;   //学生答案
    private int isTrue;         //是否正确
    private double socre;       //得分情况
    public boolean isOpened;

    public StudentAnswerAnalysis() {
    }

    @Override
    public String toString() {
        return "StudentAnswerAnalysis{" +
                "studentAnswerAnalysisId=" + studentAnswerAnalysisId +
                ", question=" + question +
                ", studentAnswer='" + studentAnswer + '\'' +
                ", isTrue=" + isTrue +
                ", socre=" + socre +
                '}';
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentAnswerAnalysisId() {
        return studentAnswerAnalysisId;
    }

    public void setStudentAnswerAnalysisId(int studentAnswerAnalysisId) {
        this.studentAnswerAnalysisId = studentAnswerAnalysisId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }
}
