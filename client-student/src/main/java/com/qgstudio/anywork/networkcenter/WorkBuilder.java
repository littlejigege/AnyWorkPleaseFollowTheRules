package com.qgstudio.anywork.networkcenter;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class WorkBuilder {
    private WorkType workType = WorkType.FOREVER;
    private HashMap<String, Object> params = null;

    public WorkBuilder onceWork() {
        workType = WorkType.ONCE;
        return this;
    }

    public WorkBuilder foreverWork() {
        workType = WorkType.FOREVER;
        return this;
    }

    public WorkBuilder param(String key, Object value) {
        if (params == null) {
            params = new HashMap<>();
        }
        params.put(key, value);
        return this;
    }

    public <T extends Work> T build(Class<T> workClass) {
        T work = null;
        try {
            work = workClass.getConstructor(WorkType.class, HashMap.class).newInstance(workType, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return work;
    }
}
