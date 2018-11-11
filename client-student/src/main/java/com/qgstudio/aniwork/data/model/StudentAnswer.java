package com.qgstudio.aniwork.data.model;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * 学生答案实体
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class StudentAnswer implements Serializable {
    private int studentAnswerId;
    private int questionId;
    private String studentAnswer;
    private Question mquestion;
    public boolean isVaild = true;

    public StudentAnswer(Question question) {
        setQuestion(question);
    }

    public Question getQuestion() {
        return mquestion;
    }

    public void setQuestion(Question question) {
        this.mquestion = question;
    }

    public int getStudentAnswerId() {
        return studentAnswerId;
    }

    public void setStudentAnswerId(int studentAnswerId) {
        this.studentAnswerId = studentAnswerId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public String getStudentAnswerForSubmit() {
        //填空题需要特殊处理
        if (mquestion.getEnumType() == Question.Type.FILL_BLANK) {
            int fillCount = mquestion.getOther();
            StringTokenizer tokenizer = new StringTokenizer(studentAnswer, "∏");
            if (tokenizer.countTokens() != fillCount) {
                //未填满无效
                return "";
            }
        }
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    @Override
    public String toString() {
        return "StudentAnswer{" +
                "studentAnswerId=" + studentAnswerId +
                ", questionId=" + questionId +
                ", studentAnswer='" + studentAnswer + '\'' +
                '}';
    }
}
