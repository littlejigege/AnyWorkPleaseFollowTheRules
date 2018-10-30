package com.qgstudio.anywork.exam.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Yason 2017/7/17.
 */

public class AskingAdapter extends OptionAdapter {

    private String mAnswer;

    public AskingAdapter(Context context, Question question, int position, String studentAnswer) {
        super(context, question, position, studentAnswer);
    }

    @Override
    public String getAnswer() {
        return mAnswer == null ? "" : mAnswer;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_asking, parent, false);
        return new AskingHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AskingHolder ah = (AskingHolder) holder;

        if (mIsReadOnly) {
            //设置不可编辑
            ah.edi_asking.setFocusable(false);
            ah.edi_asking.setFocusableInTouchMode(false);
            ah.edi_asking.setText(studentAnswer == null ? "你未作答" : studentAnswer);
            return;
        }

        //选项点击事件监听
        ah.edi_asking.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAnswer = s.toString();
                storeAnswer();
            }
        });

        //fragment回退时恢复已答的内容
        StudentAnswer origin = restoreAnswer();
        if (origin != null) {
            String os = origin.getStudentAnswer();
            mAnswer = os;
            ah.edi_asking.setText(os);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }


    class AskingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.edi_asking)
        public EditText edi_asking;

        public AskingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
