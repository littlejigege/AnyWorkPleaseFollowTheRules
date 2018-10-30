package com.qgstudio.anywork.data.model;

import java.util.List;

/**
 * 学生成绩实体
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class StudentTestResult {

    private int studentTestResultId;        // 考试结果ID
    private int studentId;                  // 学生ID
    private int testpaperId;                // 考试ID
    private double socre;                   //分数
    private List<StudentAnswerAnalysis> studentAnswerAnalysis;  //具体题目分析

    public StudentTestResult() {
    }

    @Override
    public String toString() {
        return "StudentTestResult{" +
                "studentTestResultId=" + studentTestResultId +
                ", studentId=" + studentId +
                ", testpaperId=" + testpaperId +
                ", socre=" + socre +
                ", studentAnswerAnalysis=" + studentAnswerAnalysis +
                '}';
    }

    public int getStudentTestResultId() {
        return studentTestResultId;
    }

    public void setStudentTestResultId(int studentTestResultId) {
        this.studentTestResultId = studentTestResultId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(int testpaperId) {
        this.testpaperId = testpaperId;
    }

    public double getSocre() {
        return socre;
    }

    public void setSocre(double socre) {
        this.socre = socre;
    }

    public List<StudentAnswerAnalysis> getStudentAnswerAnalysis() {
        return studentAnswerAnalysis;
    }

    public void setStudentAnswerAnalysis(List<StudentAnswerAnalysis> studentAnswerAnalysis) {
        this.studentAnswerAnalysis = studentAnswerAnalysis;
    }
}
