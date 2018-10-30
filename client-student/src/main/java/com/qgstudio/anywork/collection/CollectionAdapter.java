package com.qgstudio.anywork.collection;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.Question;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.grade.AnalysisViewModel;
import com.qgstudio.anywork.grade.GradeApi;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.widget.QuestionView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    List<Question> questions;
    Map<Integer, StudentAnswerAnalysis> analyses = new HashMap<>();
    GradeApi api = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(GradeApi.class);
    CollectionApi collectionApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(CollectionApi.class);

    public CollectionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Question question = questions.get(position);
        final QuestionView questionView = holder.questionView;
        questionView.setTestMode(false);//调到解析模式
        questionView.setCollectionEnable(true);//启动收藏
        questionView.setIsCollected(true);
        final StudentAnswerAnalysis analysis = analyses.get(question.getQuestionId());
        if (analysis == null) {
            Map info = new HashMap<>();
            info.put("questionId", question.getQuestionId() + "");
            api.changeInfo(info)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseResult<StudentAnswerAnalysis>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(final ResponseResult<StudentAnswerAnalysis> studentAnswerAnalysisResponseResult) {
                            questionView.setOnAnswerStateChangedListener(new QuestionView.OnAnswerStateChangedListener() {
                                @Override
                                public void onAnswerStateChanged(boolean isShowing) {
                                    studentAnswerAnalysisResponseResult.getData().isOpened = isShowing;

                                }
                            });

                            analyses.put(question.getQuestionId(), studentAnswerAnalysisResponseResult.getData());
                            questionView.setAnalysis(studentAnswerAnalysisResponseResult.getData()
                                    , studentAnswerAnalysisResponseResult.getData().isOpened
                                    , position
                                    , questions.size());
                        }
                    });
        } else {
            questionView.setOnAnswerStateChangedListener(new QuestionView.OnAnswerStateChangedListener() {
                @Override
                public void onAnswerStateChanged(boolean isShowing) {
                    analysis.isOpened = isShowing;
                }
            });

            questionView.setAnalysis(analysis
                    , analysis.isOpened
                    , position
                    , questions.size());
        }
        questionView.setBtnCollectListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uncollect(question.getQuestionId(),holder.getAdapterPosition());
            }
        });

    }

    public void uncollect(final int questionID, final int pos) {
        Map map = new HashMap();
        map.put("questionId", questionID);
        collectionApi.uncollect(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("取消收藏失败");
                    }

                    @Override
                    public void onNext(ResponseResult responseResult) {
                        //从列表中删除

                        questions.remove(pos);
                        notifyItemRemoved(pos);

                    }
                });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        QuestionView questionView;

        public ViewHolder(View itemView) {
            super(itemView);
            questionView = (QuestionView) itemView;
        }
    }
}
