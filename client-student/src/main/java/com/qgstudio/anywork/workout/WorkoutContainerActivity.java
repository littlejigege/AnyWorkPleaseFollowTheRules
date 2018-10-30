package com.qgstudio.anywork.workout;



import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.utils.ToastUtil;

public class WorkoutContainerActivity extends AppCompatActivity {
    private WorkoutType workoutType;
    private int classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_container);
        getWorkoutType();
        showFragment(workoutType, classId);
    }

    /**
     * 从Intent中获取题目类型，获取失败则退出活动
     */
    private void getWorkoutType() {
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        workoutType = (WorkoutType) intent.getSerializableExtra("TYPE");
        classId = intent.getIntExtra("CLASS_ID", -1);
        if (workoutType == null) {
            finish();
        }
        if (classId == -1) {
            ToastUtil.showToast("未加入任何班级");
            finish();
        }
    }

    /**
     * @param context
     * @param type    启动的题目类型
     */
    public static void start(Context context, WorkoutType type, int classId) {
        Intent intent = new Intent(context, WorkoutContainerActivity.class);
        intent.putExtra("TYPE", type);
        intent.putExtra("CLASS_ID", classId);
        context.startActivity(intent);
    }

    private void showFragment(WorkoutType type, int classId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.workout_activity_container, WorkoutCatalogFragment.newInstance(type, classId));
        transaction.commit();

    }
}
