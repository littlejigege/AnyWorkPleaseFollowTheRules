package com.qgstudio.anywork.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.exam.adapters.AskingAdapter;
import com.qgstudio.anywork.exam.adapters.ChoiceAdapter;
import com.qgstudio.anywork.exam.adapters.OptionAdapter;
import com.qgstudio.anywork.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionView extends FrameLayout {
    Question mQuestion;
    StudentAnswerAnalysis mAnalysis;
    TextView tvQuestionInfo;

    TextView tvQuestionContent;
    ImageView btnCollect;

    RecyclerView recyclerViewQuestionSelections;

    //EditText editTextQuestionFillBlank;

    TextView btnAnswerControl;

    TextView tvAnswer;
    TextView tvAnswerInvisible;
    Drawable answerShowIcon;
    Drawable answerHideIcon;
    private boolean isAnswerBottomShowing;
    ValueAnimator showAnimator;
    OptionAdapter optionAdapter;
    private boolean isTestMode = true;
    private boolean isCollected;
    private OnAnswerStateChangedListener onAnswerStateChangedListener;

    public QuestionView(@NonNull Context context) {
        super(context);
        init();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QuestionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_workout, this, false);
        addView(view);
        //find view 为啥这里不用@BindView呢？，你懂的
        tvQuestionInfo = view.findViewById(R.id.tv_question_info);
        tvQuestionContent = view.findViewById(R.id.tv_question_content);
        recyclerViewQuestionSelections = view.findViewById(R.id.recycler_view_question_selections);
        //editTextQuestionFillBlank = view.findViewById(R.id.editTv_question_fill_blank);
        tvAnswer = view.findViewById(R.id.tv_answer);
        btnAnswerControl = view.findViewById(R.id.btn_answer_control);
        tvAnswerInvisible = view.findViewById(R.id.tv_answer_invisible);
        btnCollect = view.findViewById(R.id.btn_collect);
        //取图
        answerShowIcon = getContext().getResources().getDrawable(R.drawable.icon_on);
        answerHideIcon = getContext().getResources().getDrawable(R.drawable.icon_off);
        //设置初始icon
        setDrawableBounds(40);
        setBtnAnswerControlIcon(answerHideIcon);
        //默认隐藏答案
        tvAnswer.setVisibility(View.GONE);
        //设置answerControl监听
        btnAnswerControl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleShowAnswerBottom();
            }
        });


        if (isTestMode) {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(GONE);
        } else {
            btnAnswerControl.setVisibility(VISIBLE);
        }

    }


    public void showAnswerBottom() {
        if (!isAnswerBottomShowing) {
            toggleShowAnswerBottom();
        }
    }

    public void hideAnswerBottom() {
        if (isAnswerBottomShowing) {
            toggleShowAnswerBottom();
        }
    }

    /**
     * 设置题目
     *
     * @param question
     */
    public void setQuestion(Question question, int pos, int sum) {
        mQuestion = question;
        if (question.getEnumType() == Question.Type.UNKNOWN) {
            ToastUtil.showToast(Question.Type.UNKNOWN.string);
            return;
        }

        setAnswerBottom();

        setQuestionContent(mQuestion.getContent());//设置题目内容
        setPosition(pos, sum);

        //解析模式下选项不可点击
        if (!isTestMode) {
            pos = -1;
        }

        switch (mQuestion.getEnumType()) {
            case TRUE_OR_FALSE:
            case SELECT:

                optionAdapter = new ChoiceAdapter(getContext(), mQuestion, pos, mAnalysis == null ? null : mAnalysis.getStudentAnswer());
                recyclerViewQuestionSelections.setAdapter(optionAdapter);
                recyclerViewQuestionSelections.setLayoutManager(new LinearLayoutManager(getContext()));

                break;
            case FILL_BLANK:
            case SHORT_ANSWER:

                optionAdapter = new AskingAdapter(getContext(), mQuestion, pos, mAnalysis == null ? null : mAnalysis.getStudentAnswer());
                recyclerViewQuestionSelections.setAdapter(optionAdapter);
                recyclerViewQuestionSelections.setLayoutManager(new LinearLayoutManager(getContext()));


                break;
            case UNKNOWN:

                ToastUtil.showToast(Question.Type.UNKNOWN.string);

                break;
        }

    }

    /**
     * 解析模式下调用
     *
     * @param analysis
     */
    public void setAnalysis(StudentAnswerAnalysis analysis, boolean isOpenBottomAnswer) {
        if (!isTestMode) {
            if (isOpenBottomAnswer) {
                isAnswerBottomShowing = true;
                tvAnswer.setVisibility(VISIBLE);
                btnAnswerControl.setText("收起解析");
                setBtnAnswerControlIcon(answerShowIcon);
            }

            mAnalysis = analysis;
            setQuestion(mAnalysis.getQuestion(), 0, 1);
        }
    }

    public void setAnalysis(StudentAnswerAnalysis analysis, boolean isOpenBottomAnswer, int pos, int sum) {
        if (!isTestMode) {
            mAnalysis = analysis;
            setQuestion(mAnalysis.getQuestion(), pos, sum);
            if (isOpenBottomAnswer) {
                isAnswerBottomShowing = true;
                tvAnswer.setVisibility(VISIBLE);
                btnAnswerControl.setText("收起解析");
                setBtnAnswerControlIcon(answerShowIcon);
                ViewGroup.LayoutParams layoutParams = tvAnswer.getLayoutParams();
                layoutParams.height = tvAnswerInvisible.getHeight();
                tvAnswer.setLayoutParams(layoutParams);
            } else {
                isAnswerBottomShowing = false;
                tvAnswer.setVisibility(GONE);
                btnAnswerControl.setText("展开解析");
                setBtnAnswerControlIcon(answerHideIcon);
            }

        }
    }

    /**
     * 没有调用此方法则不显示题号相关信息
     *
     * @param pos 当前题号
     * @param sum 题目总数
     */
    public void setPosition(int pos, int sum) {
        Question.Type type = null;
        if (mQuestion != null) {
            type = mQuestion.getEnumType();
        }
        setQuestionInfo(pos + 1 + "/" + sum + "  (" + (type != null ? type.string : "") + ")");
    }

    public void setQuestionInfo(String info) {
        tvQuestionInfo.setText(info);
    }

    public void setQuestionContent(String content) {
        tvQuestionContent.setText(content);
    }

    public void setBtnCollectListener(OnClickListener listener) {
        btnCollect.setOnClickListener(listener);
    }

    private void toggleShowAnswerBottom() {
        //反转
        isAnswerBottomShowing = !isAnswerBottomShowing;
        if (onAnswerStateChangedListener != null) {
            onAnswerStateChangedListener.onAnswerStateChanged(isAnswerBottomShowing);
        }
        if (showAnimator == null) {
            showAnimator = ValueAnimator.ofInt(0, tvAnswerInvisible.getHeight());
            showAnimator.setDuration(200);
            showAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            showAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    ViewGroup.LayoutParams layoutParams = tvAnswer.getLayoutParams();
                    layoutParams.height = (int) animation.getAnimatedValue();
                    tvAnswer.setLayoutParams(layoutParams);
                }
            });
            showAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    //show的动画，则先设置为可见
                    if (isAnswerBottomShowing) {
                        tvAnswer.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    //hide动画，则最后设置gone
                    if (!isAnswerBottomShowing) {
                        tvAnswer.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }

        if (isAnswerBottomShowing) {
            showAnimator.start();
            btnAnswerControl.setText("收起解析");
            setBtnAnswerControlIcon(answerShowIcon);
        } else {
            btnAnswerControl.setText("展开解析");
            setBtnAnswerControlIcon(answerHideIcon);
            showAnimator.reverse();
        }
    }

    private void setAnswerBottom() {
        String key = mQuestion.getKey();
        if (key != null && mQuestion.getType() == 3) {
            //分割填空题答案
            key = key.replaceAll("∏", " ");
        }
        if (key != null && mQuestion.getEnumType() == Question.Type.TRUE_OR_FALSE) {
            if (key.equals("1")) {
                key = "正确";
            } else {
                key = "错误";
            }
        }

        String s = "<font color='#F13E58'>正确答案是"
                + key
                + "</font><br><br>"
                + (mQuestion.getAnalysis() == null ? "暂无解析" : mQuestion.getAnalysis());
        tvAnswer.setText(Html.fromHtml(s));
        tvAnswerInvisible.setText(Html.fromHtml(s));
    }

    private void setDrawableBounds(int param) {
        answerShowIcon.setBounds(0, 0, param, param);
        answerHideIcon.setBounds(0, 0, param, param);
    }

    private void setBtnAnswerControlIcon(Drawable drawable) {
        Drawable[] drawables = btnAnswerControl.getCompoundDrawables();
        btnAnswerControl.setCompoundDrawables(drawables[0], drawables[1], drawable, drawables[3]);
    }

    public void setTestMode(boolean enable) {
        isTestMode = enable;
        if (isTestMode) {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(GONE);
        } else {
            tvAnswer.setVisibility(View.GONE);
            btnAnswerControl.setVisibility(VISIBLE);
            btnAnswerControl.setText("展开解析");
            setBtnAnswerControlIcon(answerHideIcon);
        }
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setIsCollected(boolean is) {
        isCollected = is;
        if (isCollected) {
            btnCollect.setImageResource(R.drawable.icon_collect);
        } else {
            btnCollect.setImageResource(R.drawable.icon_uncollect);
        }
    }

    public void setCollectionEnable(boolean enable) {
        btnCollect.setVisibility(enable ? VISIBLE : GONE);
    }

    public void setOnAnswerStateChangedListener(OnAnswerStateChangedListener listener) {
        onAnswerStateChangedListener = listener;
    }

    public interface OnAnswerStateChangedListener {
        void onAnswerStateChanged(boolean isShowing);
    }
}
