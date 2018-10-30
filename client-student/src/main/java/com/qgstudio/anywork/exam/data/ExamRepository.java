package com.qgstudio.anywork.exam.data;

import android.content.Context;

import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.RetrofitSubscriber;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.data.model.StudentAnswerResult;
import com.qgstudio.anywork.data.model.StudentPaper;
import com.qgstudio.anywork.data.model.StudentTestResult;
import com.qgstudio.anywork.exam.ExamView;
import com.qgstudio.anywork.exam.adapters.AnswerBuffer;
import com.qgstudio.anywork.mvp.BasePresenterImpl;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.LogUtil;
import com.qgstudio.anywork.workout.data.Testpaper;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Yason 2017/4/14.
 */

public class ExamRepository extends BasePresenterImpl<ExamView> implements ExamPresenter {

    public static final String TAG = "ExamRepository";

    private ExamApi mExamApi;

    public ExamRepository() {
        Retrofit retrofit = RetrofitClient.RETROFIT_CLIENT.getRetrofit();
        mExamApi = retrofit.create(ExamApi.class);
    }

    public void getTestpaper(int textpaperId, int state) {
        Map map = new HashMap<>();
        map.put("testpaperId", textpaperId + "");
        if (state == Testpaper.STATUS_UNFINISHED) {
            map.put("choice", 1);
        } else {
            map.put("choice", 0);
        }
        mExamApi.getTestpaper(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<List<Question>>() {
                    @Override
                    protected void onSuccess(List<Question> data) {
                        LogUtil.d(TAG, "[getTestpaper] " + "onSuccess -> " + data);
                        mView.addQuestions(data);
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d(TAG, "[getTestpaper] " + "onFailure -> " + info);
                        handleGetPaperContentFailure();
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[getTestpaper] " + "onMistake -> " + t.getMessage());

                        if (t instanceof ConnectException) {
                            mView.showToast("无法连接到服务器");
                            mView.destroySelf();
                            return;
                        }

                        handleGetPaperContentFailure();
                    }
                });
    }

    public void submitTestPaper(StudentPaper studentPaper) {
        LogUtil.d(TAG, "[submitTestPaper] " + "studentPaper -> " + GsonUtil.GsonString(studentPaper));
        mExamApi.submitTestpaper(studentPaper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RetrofitSubscriber<StudentTestResult>() {
                    @Override
                    protected void onSuccess(StudentTestResult data) {
                        LogUtil.d(TAG, "[submitTestPaper] " + "onSuccess -> " + data);
                        AnswerBuffer.getInstance().clear();//提交必清空
                        if (data == null) {
                            //保存进度成功
                            mView.saveDone();
                            return;
                        }
                        double socre = data.getSocre();
                        List<StudentAnswerResult> results = new ArrayList<>();

                        List<StudentAnswerAnalysis> analysis = data.getStudentAnswerAnalysis();
                        for (StudentAnswerAnalysis analysi : analysis) {
                            results.add(new StudentAnswerResult(analysi));
                        }
                        mView.startGradeAty(socre, results);
                    }

                    @Override
                    protected void onFailure(String info) {
                        LogUtil.d(TAG, "[submitTestPaper] " + "onFailure -> " + info);
                        mView.showToast("提交试卷失败");
                    }

                    @Override
                    protected void onMistake(Throwable t) {
                        LogUtil.d(TAG, "[submitTestPaper] " + "onMistake -> " + t);

                        if (t instanceof ConnectException) {
                            mView.showToast("无法连接到服务器");
                            return;
                        }

                        mView.showToast("提交试卷失败");
                    }
                });
    }

    private void handleGetPaperContentFailure() {
        mView.showToast("获取试卷内容失败");
        mView.destroySelf();
    }

    @Override
    public void detachView() {
        super.detachView();
        mView = new ExamView() {
            @Override
            public void addQuestions(List<Question> questions) {

            }

            @Override
            public void startGradeAty(double socre, List<StudentAnswerResult> analysis) {

            }

            @Override
            public void destroySelf() {

            }

            @Override
            public void submitDone() {

            }

            @Override
            public void saveDone() {

            }

            @Override
            public Context getContext() {
                return null;
            }

            @Override
            public void showLoading() {

            }

            @Override
            public void hideLoading() {

            }

            @Override
            public void showToast(String s) {

            }
        };
    }
}
