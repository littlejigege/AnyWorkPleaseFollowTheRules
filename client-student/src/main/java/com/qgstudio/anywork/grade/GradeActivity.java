package com.qgstudio.anywork.grade;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.data.model.StudentAnswerResult;
import com.qgstudio.anywork.exam.QuestionFragment;
import com.qgstudio.anywork.mvp.MVPBaseActivity;
import com.qgstudio.anywork.utils.ActivityUtil;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenyi on 2017/7/10.
 */

public class GradeActivity extends MVPBaseActivity<GradeContract.View, GradePresenter> implements GradeContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private GradeAdapter gradeAdapter;
    @BindView(R.id.tv_paper_tittle)
    TextView tvPaperTittle;
    @BindView(R.id.tv_count_right)
    TextView tvCountRight;
    @BindView(R.id.tv_sum_questions)
    TextView tvSumQuestion;
    @BindView(R.id.btn_back)
    View btnClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        double socre = intent.getDoubleExtra("socre", 0);
        List<StudentAnswerResult> results = GsonUtil.GsonToList
                (intent.getStringExtra("results"), StudentAnswerResult[].class);
        //初始化视图
        String paperTittle = intent.getStringExtra("paperTittle");
        int sum = results.size();
        int right = 0;
        for (StudentAnswerResult result : results) {
            if (result.getContent().equals("true")) {
                right++;
            }
        }
        tvCountRight.setText(right + "");
        tvSumQuestion.setText(sum + "");
        tvPaperTittle.setText(paperTittle);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }

        initToolbar("做题报告");
        initRecyclerView(results);
    }

    private void initToolbar(String title) {
        TextView tittle = toolbar.findViewById(R.id.tv_tittle);
        tittle.setText(title);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView(List<StudentAnswerResult> results) {
        gradeAdapter = new GradeAdapter(results, this);
        gradeAdapter.setrListener(new GradeAdapter.ResultListener() {
            @Override
            public void getTestResult(int id) {
                mPresenter.getDetail(id);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (gradeAdapter.getDatasType(position) == 0) {
                    return 5;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(gradeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static void start(Context context, double socre, String results, String paperTittle) {
        Intent intent = new Intent(context, GradeActivity.class);
        intent.putExtra("socre", socre);
        intent.putExtra("results", results);
        intent.putExtra("paperTittle", paperTittle);
        context.startActivity(intent);
    }

    @Override
    public void showSuccess(StudentAnswerAnalysis analysis) {
        AnalysisFragment.newInstanse(analysis).show(getFragmentManager(), "");
    }

    @Override
    public void showError(String s) {
        ToastUtil.showToast(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setTransition(Fragment fragment) {
        Fade fadeTransition = new Fade();
        fadeTransition.setDuration(300);

        ChangeBounds changeBoundsTransition = new ChangeBounds();
        changeBoundsTransition.setDuration(300);
        // 为 fragment 设置进出场的动画
        fragment.setEnterTransition(fadeTransition);
        fragment.setAllowEnterTransitionOverlap(true);
        fragment.setAllowReturnTransitionOverlap(true);
        fragment.setSharedElementEnterTransition(changeBoundsTransition);
    }

}
