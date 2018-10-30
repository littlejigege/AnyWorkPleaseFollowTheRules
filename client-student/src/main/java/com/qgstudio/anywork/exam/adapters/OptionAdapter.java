package com.qgstudio.anywork.exam.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswer;
import com.qgstudio.anywork.utils.ToastUtil;


/**
 * 选项适配器
 *
 * @author Yason 2017/7/10.
 */

public abstract class OptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 获取学生填写的答案
     */
    protected abstract String getAnswer();

    protected Context mContext;
    protected Question mQuestion;
    protected int mPosition;//题目序号
    protected boolean mIsReadOnly;//只读标记位
    protected String studentAnswer;

    public void setData(Question question, int pos, String studentAnswer) {
        mQuestion = question;
        mPosition = pos;
        mIsReadOnly = pos < 0;
        this.studentAnswer = studentAnswer;
    }

    public OptionAdapter(Context context, Question question, int position, String studentAnswer) {
        mContext = context;
        mQuestion = question;
        mPosition = position;
        mIsReadOnly = position < 0;
        this.studentAnswer = studentAnswer;
    }

    public OptionAdapter(Context context, Question question, int position) {
        mContext = context;
        mQuestion = question;
        mPosition = position;
        mIsReadOnly = position < 0;
    }

//    public void setIsReadOnly(boolean isReadOnly) {
//        mIsReadOnly = isReadOnly;
//    }

    protected void storeAnswer() {
        if (mIsReadOnly) {
            return;
        }
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setQuestionId(mQuestion.getQuestionId());
        studentAnswer.setStudentAnswer(getAnswer());
        AnswerBuffer.getInstance().addStudentAnswer(mPosition, studentAnswer);
    }

    protected StudentAnswer restoreAnswer() {
        return mIsReadOnly ? null : AnswerBuffer.getInstance().getStudentAnswer(mPosition);
    }

}
