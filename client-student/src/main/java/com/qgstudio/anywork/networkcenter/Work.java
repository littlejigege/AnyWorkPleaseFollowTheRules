package com.qgstudio.anywork.networkcenter;

import java.util.HashMap;

public abstract class Work {
    private WorkType workType = WorkType.FOREVER;
    private HashMap<String, Object> params;

    public WorkType getWorkType() {
        return workType;
    }

    public <T> T getParam(String key, Class<T> clazz) {
        if (params == null) {
            return null;
        } else {
            return (T) params.get(key);
        }
    }

    public Object getParam(String key) {
        if (params == null) {
            return null;
        } else {
            return params.get(key);
        }
    }


    public Work(WorkType workType, HashMap<String, Object> params) {
        this.workType = workType;
        this.params = params;
    }

    abstract public WorkState doWork();

    public enum WorkState {
        DONE,
        FAILED
    }
}
