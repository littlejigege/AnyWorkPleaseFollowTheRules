package com.qgstudio.anywork.data.model;

public class StudentAnswerAnalysisNew {
    private QuestionNew question;
    private String studentAnswer;  //学生的答案
    private int isTrue;  //是否正确 1正确 0错误
    private double socre;  //该题得分

    public QuestionNew getQuestion() {
        return question;
    }

    public void setQuestion(QuestionNew question) {
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
