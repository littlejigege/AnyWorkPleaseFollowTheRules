package com.qgstudio.anywork.workout;

import android.app.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qgstudio.anywork.App;
import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.StudentAnswerAnalysis;
import com.qgstudio.anywork.data.model.StudentAnswerResult;
import com.qgstudio.anywork.data.model.StudentTestResult;
import com.qgstudio.anywork.dialog.LoadingDialog;
import com.qgstudio.anywork.exam.ExamActivity;
import com.qgstudio.anywork.grade.GradeActivity;
import com.qgstudio.anywork.paper.PaperAdapter;
import com.qgstudio.anywork.ranking.RankingFragment;
import com.qgstudio.anywork.utils.GsonUtil;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.workout.data.Chapter;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.workout.data.WorkoutInfo;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class ChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<WorkoutInfo> datas = new ArrayList<>();
    public Context context;
    private OnChapterClickListener listener;
    private boolean isTest;

    public ChapterAdapter(List<? extends WorkoutInfo> datas, Context context) {
        this.datas.clear();
        this.datas.addAll(datas);
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == WorkoutInfo.TYPE_CHAPTER) {
            view = LayoutInflater.from(context).inflate(R.layout.item_workout_chapter, parent, false);
            viewHolder = new ChapterViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_workout_info, parent, false);
            viewHolder = new CatalogViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder.getItemViewType() == WorkoutInfo.TYPE_CHAPTER) {
            //章节
            ChapterViewHolder chapterViewHolder = (ChapterViewHolder) holder;
            final Chapter chapter = (Chapter) datas.get(position);
            chapterViewHolder.tvChapterTittle.setText(chapter.getChapterName());
            chapterViewHolder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onChapterClick(chapter.getChapterId());
                    }
                }
            });
        } else {
            //小节
            CatalogViewHolder catalogViewHolder = (CatalogViewHolder) holder;
            final Testpaper testpaper = (Testpaper) datas.get(position);
            catalogViewHolder.tvCatalogTittle.setText(testpaper.getTestpaperTitle());
            catalogViewHolder.tvCatalogTime.setText(testpaper.getCreateTime() + "-" + testpaper.getEndingTime());

            switch (testpaper.getStatus()) {
                case Testpaper.STATUS_UNDO:
                    catalogViewHolder.stateTab.setText("未完成");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#F54864"));
                    break;
                case Testpaper.STATUS_UNFINISHED:
                    catalogViewHolder.stateTab.setText("未提交");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#4898F5"));
                    break;
                case Testpaper.STATUS_DONE:
                    catalogViewHolder.stateTab.setText("已完成");
                    catalogViewHolder.stateTab.setBackgroundColor(Color.parseColor("#33C571"));
                    break;
            }
            ((CatalogViewHolder) holder).card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String createTime = testpaper.getCreateTime();
                    String endTime = testpaper.getEndingTime();
                    Date createD = getDate(createTime);
                    Date endD = getDate(endTime);

                    long now = System.currentTimeMillis();

                    //只有在考试不是完成状态才需要
                    if (testpaper.getStatus()!= Testpaper.STATUS_DONE) {
                        if (now < createD.getTime()) {
                            ToastUtil.showToast("未到达考试时间");
                            return;
                        }
                        if (now > endD.getTime()) {
                            ToastUtil.showToast("考试时间已截止");
                            return;
                        }
                    }

                    LoadingDialog dialog = new LoadingDialog();
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "");
                    intoTestActivity(v.getContext(), testpaper.getTestpaperId(), testpaper.getTestpaperTitle(), 1, dialog, testpaper.getStatus());
                }
            });
            ((CatalogViewHolder) holder).btnRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RankingFragment fragment = RankingFragment.newInstance(testpaper.getTestpaperId());
                    AppCompatActivity activity = (AppCompatActivity) context;
                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.workout_activity_container, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getClass() == Chapter.class ? WorkoutInfo.TYPE_CHAPTER : WorkoutInfo.TYPE_CATALOG;
    }

    class ChapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.chapter_tittle)
        TextView tvChapterTittle;
        @BindView(R.id.card_view)
        View card;

        public ChapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CatalogViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.catalog_tittle)
        TextView tvCatalogTittle;
        @BindView(R.id.catalog_time)
        TextView tvCatalogTime;
        @BindView(R.id.tv_workout_state_tab)
        TextView stateTab;
        @BindView(R.id.card)
        View card;
        @BindView(R.id.btn_rank)
        View btnRank;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnChapterClickListener(OnChapterClickListener listener) {
        this.listener = listener;
    }

    interface OnChapterClickListener {
        void onChapterClick(int chapterID);
    }

    private void intoTestActivity(final Context context, final int testpaperId, final String paperTittle, final int type, final LoadingDialog dialog, final int state) {


        Map<String, Integer> info = new HashMap<>();
        info.put("testpaperId", testpaperId);
        info.put("userId", App.getInstance().getUser().getUserId());

        Log.e(TAG, "checkTheTestFinish: " + GsonUtil.GsonString(info));
        RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(PaperAdapter.CheckApi.class)
                .checkTheTestFinish(info)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseResult<StudentTestResult>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        e.printStackTrace();
                        ToastUtil.showToast("网络连接错误");
                    }

                    @Override
                    public void onNext(ResponseResult<StudentTestResult> result) {
                        dialog.dismiss();
                        if (result.getState() == 1) {
                            double socre = result.getData().getSocre();
                            List<StudentAnswerResult> results = new ArrayList<>();

                            List<StudentAnswerAnalysis> analysis = result.getData().getStudentAnswerAnalysis();
                            for (StudentAnswerAnalysis analysi : analysis) {
                                results.add(new StudentAnswerResult(analysi));
                            }
                            GradeActivity.start(context, socre, GsonUtil.GsonString(results), paperTittle);
                        } else {
                            ExamActivity.start(context, testpaperId, type, paperTittle, state);
                        }
                    }
                });
    }

    /**
     * 将后台传过来的时间数据转化为Date
     *
     * @param str 输入后台传来的时间数据
     * @return Date
     */
    private Date getDate(String str) {
        System.out.println("=============" + str);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException p) {
            p.printStackTrace();
        }
        return date;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public interface CheckApi {

        @POST("organization/studentTestDetail")
        @Headers("Content-Type:application/json")
        Observable<ResponseResult<StudentTestResult>> checkTheTestFinish(@Body Map<String, Integer> testpaperId);
    }
}
