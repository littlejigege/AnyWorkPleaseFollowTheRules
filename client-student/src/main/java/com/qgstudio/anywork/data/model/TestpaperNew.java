package com.qgstudio.anywork.data.model;

public class TestpaperNew {
    private int testpaperId;  //试卷id
    private String testpaperTitle;  //试卷标题
    private int status;  //用于标志当前登录用户与这份试卷的关系
    private String createTime;  //开始时间
    private String endingTime;  //结束时间
    private int timeStatus;  //时间状态
    private int totalQuestions;  //题目总数
    private int doneQuestions;  //已做题目数量
    private double testpaperScore;  //考试总分

    public int getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(int testpaperId) {
        this.testpaperId = testpaperId;
    }

    public String getTestpaperTitle() {
        return testpaperTitle;
    }

    public void setTestpaperTitle(String testpaperTitle) {
        this.testpaperTitle = testpaperTitle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public int getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(int timeStatus) {
        this.timeStatus = timeStatus;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getDoneQuestions() {
        return doneQuestions;
    }

    public void setDoneQuestions(int doneQuestions) {
        this.doneQuestions = doneQuestions;
    }

    public double getTestpaperScore() {
        return testpaperScore;
    }

    public void setTestpaperScore(double testpaperScore) {
        this.testpaperScore = testpaperScore;
    }
}
