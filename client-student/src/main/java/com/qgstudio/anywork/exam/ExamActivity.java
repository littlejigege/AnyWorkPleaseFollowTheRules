package com.qgstudio.anywork.exam;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswer;
import com.qgstudio.anywork.data.model.StudentAnswerResult;
import com.qgstudio.anywork.data.model.StudentPaper;
import com.qgstudio.anywork.dialog.BaseDialog;
import com.qgstudio.anywork.exam.adapters.AnswerBuffer;
import com.qgstudio.anywork.exam.data.ExamRepository;
import com.qgstudio.anywork.grade.GradeActivity;
import com.qgstudio.anywork.mvp.MVPBaseActivity;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.widget.ExamPagerView;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 整套试卷的容器
 *
 * @author Yason 2017/4/2.
 */

public class ExamActivity extends MVPBaseActivity<ExamView, ExamRepository> implements ExamView,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.epv)
    ExamPagerView mExamPagerView;

    private int mTestPaperId;
    private int mTestPaperType;//1为考试，0为练习
    private String mTestPaperTittle;
    private QuestionFragAdapter mQuestionFragAdapter;//数据适配器
    private int state;

    private BaseDialog mBaseDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mTestPaperId = getIntent().getIntExtra("TESTPAPER_ID", -1);
        mTestPaperType = getIntent().getIntExtra("TESTPAPER_TYPE", -1);
        mTestPaperTittle = getIntent().getStringExtra("TESTPAPER_TITTLE");
        state = getIntent().getIntExtra("STATE", 0);
        initView();
        loadData();
    }

    private void loadData() {
        mPresenter.getTestpaper(mTestPaperId, state);
    }

    private void initView() {
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        mQuestionFragAdapter = new QuestionFragAdapter(getSupportFragmentManager(), new ArrayList<Fragment>());
        mExamPagerView.setViewPagerAdapter(mQuestionFragAdapter);
        mExamPagerView.setViewPagerListener(this);
        mExamPagerView.setOnBottomButtonClickListener(new ExamPagerView.OnBottomButtonClickListener() {
            @Override
            public void onLeftClick() {
                submit(true);
            }

            @Override
            public void onRightClick() {
                submit(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        //退出前一定要清空缓存
        AnswerBuffer.getInstance().clear();

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        submit(true);
        super.onBackPressed();
    }

    public void checkExit() {
        if (mBaseDialog == null) {
            mBaseDialog = new BaseDialog.Builder(this)
                    .title("提示")
                    .content("您还未提交试卷，退出将不保存作答内容！！！")
                    .setNegativeListener("确认", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finishAty();
                        }
                    })
                    .setPositiveListener("取消", null)
                    .cancelTouchout(true)
                    .build();
        }
        if (!mBaseDialog.isShowing()) {
            mBaseDialog.show();
        }
    }


    public void submit(boolean isSave) {
        StudentPaper studentPaper = new StudentPaper();
        studentPaper.setStudentId(((App) getApplication()).getUser().getUserId());
        studentPaper.setStudentAnswer(AnswerBuffer.getInstance().getResult());
        if (!isSave) {
            //直接提交
            //一题都没做
            if (mQuestionFragAdapter.getCount() != studentPaper.getStudentAnswer().size()) {
                ToastUtil.showToast("请完成所有题目后提交");
                return;
            }
            //没做完
            for (StudentAnswer studentAnswer : studentPaper.getStudentAnswer()) {
                System.out.println(studentAnswer.getStudentAnswer());
                if (studentAnswer.getStudentAnswer().isEmpty()) {
                    ToastUtil.showToast("请完成所有题目后提交");
                    return;
                }
            }
            studentPaper.setTemporarySave(0);//0为正常提交
        } else {
            //临时保存
            //来到这里就是做了一部分
            studentPaper.setTemporarySave(1);//1为临时保存
        }
        studentPaper.setTestpaperId(mTestPaperId);
        mPresenter.submitTestPaper(studentPaper);

    }

    @Override
    public void onPageSelected(int position) {
        int total = mQuestionFragAdapter.getCount();
        int pos = total != 0 ? position + 1 : 0;
        //每页显示当前页序
        mExamPagerView.setTitleCenterTextString(pos + "/" + total);
        //末页显示提交按钮
        mExamPagerView.showBottomButtons(pos == total);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static void start(Context context, int testpaperId, int testpaperType, String paperTittle, int state) {
        Intent intent = new Intent(context, ExamActivity.class);
        intent.putExtra("TESTPAPER_ID", testpaperId);
        intent.putExtra("TESTPAPER_TYPE", testpaperType);
        intent.putExtra("TESTPAPER_TITTLE", paperTittle);
        intent.putExtra("STATE", state);
        context.startActivity(intent);
    }

    @Override
    public void addQuestions(List<Question> questions) {
        List<Fragment> fragments = new ArrayList<>();
        int position = 0;
        for (Question question : questions) {
            //将每道题传入每个fragment中
            fragments.add(QuestionFragment.newInstance(question, position, questions.size()));
            position++;
        }
        //读取保存的进度
        for (int i = 0; i < questions.size(); i++) {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setQuestionId(questions.get(i).getQuestionId());
            studentAnswer.setStudentAnswer(questions.get(i).getKey());
            AnswerBuffer.getInstance().addStudentAnswer(i, studentAnswer);
        }
        mQuestionFragAdapter.addAll(fragments);
        mExamPagerView.setTitleCenterTextString((position != 0 ? 1 : 0) + "/" + questions.size());

        //如若习题只有一道（即最后一道），则显示提交按钮
        if (mQuestionFragAdapter.getCount() == 1) {
            mExamPagerView.showBottomButtons(true);
        }
        mExamPagerView.setOnTopRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mExamPagerView.showAnswerCard(AnswerBuffer.getInstance().getStudentAnswerArray(), mQuestionFragAdapter.getCount());
            }
        });
    }

    @Override
    public void startGradeAty(double socre, List<StudentAnswerResult> results) {
        ToastUtil.showToast("试卷已提交");
        GradeActivity.start(this, socre, GsonUtil.GsonString(results), mTestPaperTittle);
        finishAty();
    }

    @Override
    public void destroySelf() {
        finishAty();
    }

    @Override
    public void submitDone() {

    }

    @Override
    public void saveDone() {
        ToastUtil.showToast("进度已保存");
        finishAty();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(s);
    }


    private void finishAty() {
        this.finish();
    }

}
