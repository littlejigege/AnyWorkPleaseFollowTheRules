package com.qgstudio.anywork.workout;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.StudentAnswer;
import com.qgstudio.anywork.data.model.StudentAnswerResult;
import com.qgstudio.anywork.utils.DesityUtil;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by chenyi on 2017/7/10.
 */

public class AnswerCardAdapter extends Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_TYPE_TITLE = 1;
    private final int ITEM_TYPE_CONTENT = 2;

    private SparseArray<StudentAnswer> mDatas;
    private Context mContext;
    private int sum;


    public void setmDatas(SparseArray<StudentAnswer> datas) {
        mDatas = datas;
    }

    public AnswerCardAdapter(SparseArray<StudentAnswer> datas, int sum, Context context) {
        super();
        mDatas = new SparseArray<>();
        setmDatas(datas);
        mContext = context;
        this.sum = sum;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_answer_card, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StudentAnswer answer = mDatas.get(position);
        //取到的答案无效则设置为无效
        if (answer != null && answer.getStudentAnswer().isEmpty()) {
            answer.isVaild = false;
        }

        //没有这个答案则自动填一个无效答案
        if (answer == null) {
            answer = new StudentAnswer();
            answer.isVaild = false;
        }
        TextView text = ((ItemHolder) holder).text;
        text.setText(position + 1 + "");

        if (answer.isVaild) {
            text.setBackgroundResource(R.drawable.bg_choice_selected);
            text.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        } else {
            text.setBackgroundResource(R.drawable.bg_choice_normal);
            text.setTextColor(ContextCompat.getColor(mContext, R.color.sample_blue));
        }
    }

    @Override
    public int getItemCount() {
        return sum;
    }

    class ItemHolder extends RecyclerView.ViewHolder {


        TextView text;

        ItemHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_answer_card);
        }
    }

}
