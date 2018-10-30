package com.qgstudio.anywork.exam.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Yason 2017/7/17.
 */

public class FillingAdapter extends OptionAdapter {

    private List<String> mAnswer;

    public FillingAdapter(Context context, Question question, int position) {
        super(context, question, position);
        init();
    }


    private void init() {
        mAnswer = new ArrayList<>(getItemCount());
        for (int index = 0; index < getItemCount(); index++) {
            mAnswer.add(" ");
        }
    }

    @Override
    public String getAnswer() {
        StringBuilder sb = new StringBuilder("");
        for (int index = 0; index < getItemCount(); index++) {
            sb.append(mAnswer.get(index)).append("∏");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_filling, parent, false);
        return new FillingHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FillingHolder fh = (FillingHolder) holder;

        //选项内容初始化
        fh.tv_filling.setText((position + 1) + ".");

        if (mIsReadOnly) {
            //设置不可编辑
            fh.edi_filling.setFocusable(false);
            fh.edi_filling.setFocusableInTouchMode(false);
            return;
        }

        //选项点击事件监听
        fh.edi_filling.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAnswer.set(position, s.toString());
                storeAnswer();
            }
        });

        //fragment回退时恢复已答的内容
        StudentAnswer origin = restoreAnswer();
        if (origin != null) {
            String os = origin.getStudentAnswer();
            String[] o = os.split("∏");

            for (int index = 0; index < o.length; index++) {
                mAnswer.set(index, o[index]);
            }

            if (position < o.length) {
                fh.edi_filling.setText(o[position]);
            }
        }

    }

    @Override
    public int getItemCount() {
        //getOther()返回的是填空的个数
        return mQuestion.getOther();
    }

    class FillingHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_filling) public TextView tv_filling;
        @BindView(R.id.edi_filling) public EditText edi_filling;

        public FillingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                edi_filling.setLetterSpacing(0.2f);
            }
        }
    }

}
