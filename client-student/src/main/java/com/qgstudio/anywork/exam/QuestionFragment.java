package com.qgstudio.anywork.exam;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.mvp.BaseFragment;
import com.qgstudio.anywork.widget.QuestionView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示试卷中的一道题
 * @author Yason 2017/4/2.
 */
public class QuestionFragment extends BaseFragment {

    public static final String TAG = "QuestionFragment";

    @BindView(R.id.question_view)
    QuestionView questionView;


    private Question mQuestion;//此页的题目
    private int mPosition;//题目的序号
    private int sum;//题目总数



    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (Question) getArguments().get("QUESTION");
        mPosition = getArguments().getInt("POSITION", -1);
        sum = getArguments().getInt("SUM", -1);
    }

    public static QuestionFragment newInstance(Question question, int position,int sum) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        bundle.putInt("POSITION", position);
        bundle.putInt("SUM", sum);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getRootId() {
        return R.layout.fragment_question;
    }

    @Override
    public void initView() {
        mUnbinder = ButterKnife.bind(this, mRoot);
        questionView.setQuestion(mQuestion,mPosition,sum);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

}
