package com.qgstudio.anywork.data.model;

import java.util.List;

public class StudentTestResultNew {
    private int studentId;  //学生id
    private String testpaperId;  //试卷id
    private double socre; //总分数
    private List<StudentAnswerAnalysisNew> studentAnswerAnalysis;  //每道题的情况

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(String testpaperId) {
        this.testpaperId = testpaperId;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public List<StudentAnswerAnalysisNew> getStudentAnswerAnalysis() {
        return studentAnswerAnalysis;
    }

    public void setStudentAnswerAnalysis(List<StudentAnswerAnalysisNew> studentAnswerAnalysis) {
        this.studentAnswerAnalysis = studentAnswerAnalysis;
    }
}
