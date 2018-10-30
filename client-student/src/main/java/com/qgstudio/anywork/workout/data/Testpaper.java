package com.qgstudio.anywork.workout.data;

import com.qgstudio.anywork.workout.data.WorkoutInfo;

import java.util.Date;
import java.util.List;

/**
 * 试卷实体
 * Created by FunriLy on 2017/7/10.
 * From small beginnings comes great things.
 */
public class Testpaper extends WorkoutInfo{

    private int testpaperId;                //试卷ID
    private String testpaperTitle;          //试卷标题
    private int authorId;                   //教师ID
    private int organizationId;             //组织ID        
    private String createTime;                //开始时间
    private String endingTime;                //结束时间
    private int chapterId;                    //章节id，为练习卷添加章节
    private String chapterName;               //章节名称，为练习卷添加章节
    private int testpaperScore;             //试卷分数
    private int testpaperType;              //试卷类型，0为练习、1为考试，若将来扩展可在这个字段上实现
    private int status;
    private int timeStatus;
    private int totalQuestions;
    private int doneQuestions;
    private int score;
    public static final int STATUS_DONE = 1;
    public static final int STATUS_UNDO = 3;
    public static final int STATUS_UNFINISHED = 2;
    public static final int TIME_STATUS_OUT = 1;
    public static final int TIME_STATUS_IN = 2;
    public static final int TIME_STATUS_NOT_YET = 3;
    public Testpaper(){}

    //get & set


    @Override
    public String toString() {
        return "Testpaper{" +
                "testpaperId=" + testpaperId +
                ", testpaperTitle='" + testpaperTitle + '\'' +
                ", authorId=" + authorId +
                ", organizationId=" + organizationId +
                ", createTime='" + createTime + '\'' +
                ", endingTime='" + endingTime + '\'' +
                ", chapterId=" + chapterId +
                ", chapterName='" + chapterName + '\'' +
                ", testpaperScore=" + testpaperScore +
                ", testpaperType=" + testpaperType +
                ", status=" + status +
                ", timeStatus=" + timeStatus +
                ", totalQuestions=" + totalQuestions +
                ", doneQuestions=" + doneQuestions +
                ", score=" + score +
                '}';
    }

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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getTestpaperScore() {
        return testpaperScore;
    }

    public void setTestpaperScore(int testpaperScore) {
        this.testpaperScore = testpaperScore;
    }

    public int getTestpaperType() {
        return testpaperType;
    }

    public void setTestpaperType(int testpaperType) {
        this.testpaperType = testpaperType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
