package com.qgstudio.anywork.workout.data;

import com.qgstudio.anywork.workout.data.WorkoutInfo;

/**
 * Created by logan on 2017/7/11.
 */
public class Chapter extends WorkoutInfo{

    private int chapterId;
    private int organizationId;
    private String chapterName;

    public Chapter() {
    }

    public Chapter(int chapterId, int organizationId, String chapterName) {
        this.chapterId = chapterId;
        this.organizationId = organizationId;
        this.chapterName = chapterName;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId=" + chapterId +
                ", organizationId=" + organizationId +
                ", chapterName='" + chapterName + '\'' +
                '}';
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
