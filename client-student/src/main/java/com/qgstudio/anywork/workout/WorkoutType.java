package com.qgstudio.anywork.workout;

public enum WorkoutType {

    PREVIEW("课前预习"),
    EXERCISE("课后练习"),
    EXAM("课程考试");
    String value;

    WorkoutType(String value) {
        this.value = value;
    }

    public int toInt() {
        int out;
        if (value.equals("课前预习")) {
            out = 2;
        } else if (value.equals("课后练习")) {
            out = 3;
        } else {
            out = 1;
        }
        return out;
    }
}
