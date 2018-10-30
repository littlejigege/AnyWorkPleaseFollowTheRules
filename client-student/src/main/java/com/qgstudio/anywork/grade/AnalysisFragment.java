package com.qgstudio.anywork.grade;

import android.app.DialogFragment;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.mobile.utils.CoroutineUtilsKt;
import com.mobile.utils.ViewUtilsKt;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.widget.QuestionView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.jvm.functions.Function1;

public class AnalysisFragment extends DialogFragment implements LifecycleOwner {
    private StudentAnswerAnalysis mAnalysis;
    @BindView(R.id.toolbar)
    View btnBack;
    @BindView(R.id.question_view)
    QuestionView questionView;
    @BindView(R.id.btn_collect)
    View btnCollect;
    AnalysisViewModel viewModel;
    LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
    List<Question> collectedQuestions;

    public static AnalysisFragment newInstanse(StudentAnswerAnalysis analysis) {
        AnalysisFragment fragment = new AnalysisFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ANALYSIS", analysis);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new AnalysisViewModel();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        mAnalysis = (StudentAnswerAnalysis) getArguments().getSerializable("ANALYSIS");
        ButterKnife.bind(this, view);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        questionView.setTestMode(false);//调到解析模式
        questionView.setCollectionEnable(true);//启动收藏


        questionView.setBtnCollectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCollect.setClickable(false);
                if (questionView.isCollected()) {
                    viewModel.uncollect(mAnalysis.getQuestion().getQuestionId(), new AnalysisViewModel.OnResultListener() {
                        @Override
                        public void onResult(Object o) {
                            if ((Boolean) o) {
                                questionView.setIsCollected(false);
                                ToastUtil.showToast("取消收藏成功");
                            } else {
                                ToastUtil.showToast("取消收藏失败");
                            }
                            btnCollect.setClickable(true);

                        }
                    });
                } else {
                    viewModel.collect(mAnalysis.getQuestion().getQuestionId(), new AnalysisViewModel.OnResultListener() {
                        @Override
                        public void onResult(Object o) {
                            if ((Boolean) o) {
                                questionView.setIsCollected(true);
                                ToastUtil.showToast("收藏成功");
                            } else {
                                ToastUtil.showToast("收藏失败");
                            }
                            btnCollect.setClickable(true);
                        }
                    });
                }
            }
        });
        questionView.setAnalysis(mAnalysis, true);
        //一进来先初始化星星
        viewModel.getAllContions(new AnalysisViewModel.OnResultListener() {
            @Override
            public void onResult(Object o) {
                for (Question question : (List<Question>) o) {
                    if (question.getQuestionId() == mAnalysis.getQuestion().getQuestionId()) {
                        questionView.setIsCollected(true);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
