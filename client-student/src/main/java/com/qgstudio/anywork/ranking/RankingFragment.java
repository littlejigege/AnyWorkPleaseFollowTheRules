package com.qgstudio.anywork.ranking;

import android.app.ActionBar;
import android.arch.lifecycle.Lifecycle;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.ResponseResult;
import com.qgstudio.anywork.data.RetrofitClient;
import com.qgstudio.anywork.data.model.RankingMessage;
import com.qgstudio.anywork.mvp.BaseFragment;
import com.qgstudio.anywork.ranking.adapters.RankingAdapter;
import com.qgstudio.anywork.widget.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RankingFragment extends BaseFragment {

    protected View rootView;
    private int testpaperId = -1;

    private TextView ranking1;
    private ArrowsView arrows1;
    private ListView listView1;
    private PopupWindow popupWindow1;
    private RecyclerView rankingList;
    private LoadingView loadingView;
    private TextView score;
    private int leaderboardType = 1;

    //排行榜适配器
    private RankingAdapter rankingAdapter;

    //界面中的两个下拉框的适配器
    private ArrayList<String> data1 = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter1;

    private RankingApi rankingApi;

    private OnBackListener onBackListener;

    public interface OnBackListener {
        void onClick();
    }

    public void setOnBackListener(OnBackListener onBackListener) {
        this.onBackListener = onBackListener;
    }

    public RankingFragment() {
    }

    public static RankingFragment newInstance(int testpaperId) {
        RankingFragment fragment = new RankingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TESTPAPER_ID", testpaperId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            testpaperId = bundle.getInt("TESTPAPER_ID");
        }
    }

    /**
     * @param testpaperId 试卷id
     * @deprecated 设置要获取对应id的试卷的排行榜
     */
    public void setTestpaperId(int testpaperId) {
        this.testpaperId = testpaperId;
    }

    @Override
    public int getRootId() {
        return R.layout.fragment_ranking;
    }

    @Override
    public void initView(View view) {
        try {
            initRankingList(view);
            initBackButton(view);
            initSpinner(view);
            //避免重复下移
            if (view.getTag() == null) {
                setDetails(view);
                view.setTag(new Object());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadData(View view) {
        //初始化填充时的数据是按学生所在班级进行排名
        getRankingMessage(testpaperId, 1);
    }

    private void initBackButton(View rootView) {
        View view = rootView.findViewById(R.id.btn_back);
        if (testpaperId != -1) {
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackListener.onClick();
                }
            });
        }
    }

    private void setDetails(View rootView) {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resourceId);
        }

        View view = rootView.findViewById(R.id.ranking_frame);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.topMargin = params.topMargin + result;
        view.setLayoutParams(params);
    }

    /**
     * 初始化RecycleView
     *
     * @param rootView
     */
    private void initRankingList(View rootView) {
        rankingList = (RecyclerView) rootView.findViewById(R.id.recycler_view_ranking);
        rankingList.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadingView = rootView.findViewById(R.id.loading_view);
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                loadingView.load(rankingList);
                getRankingMessage(testpaperId, leaderboardType);
            }
        });
    }

    /**
     * 初始化Spinner
     *
     * @param rootView
     */
    private void initSpinner(View rootView) throws NoSuchFieldException, IllegalAccessException {
        ranking1 = (TextView) rootView.findViewById(R.id.ranking1);
        arrows1 = (ArrowsView) rootView.findViewById(R.id.arrows1);

        initSelectPopup();

        //设置点击ranking1和ranking2时让其显示下拉列表窗口
        ranking1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow1.showAsDropDown(ranking1, 8, 4);
                arrows1.mUp();
            }
        });
    }

    /**
     * 设置下拉列表窗口
     */
    private void initSelectPopup() {

        listView1 = new ListView(getActivity());
        listView1.setBackground(getActivity().getResources().getDrawable(R.drawable.background));

        if (data1.size() == 0) {
            data1.add("学生所在班级");
            data1.add("教师所教班级");
        }

        arrayAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.layout_listview, data1);
        listView1.setAdapter(arrayAdapter1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kind = data1.get(position);
                ranking1.setText(kind);

                if (kind.equals("学生所在班级")) {
                    getRankingMessage(testpaperId, 1);
                } else if (kind.equals("教师所教班级")) {
                    getRankingMessage(testpaperId, 2);
                }

                popupWindow1.dismiss();
            }
        });

        popupWindow1 = new PopupWindow(listView1, 148 * 3, ActionBar.LayoutParams.WRAP_CONTENT, true);

        //下拉列表窗口消失时的监听器
        popupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                arrows1.mDown();
            }
        });
    }

    private void getRankingMessage(int testpaperId, int leaderboardType) {
        this.leaderboardType = leaderboardType;
        if (rankingApi == null) {
            rankingApi = RetrofitClient.RETROFIT_CLIENT.getRetrofit().create(RankingApi.class);
        }

        if (testpaperId == -1) {
            Map<String, Integer> info = new HashMap<>();
            info.put("leaderboardType", leaderboardType);
            rankingApi.getTotalRanking(info)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseResult<ArrayList<RankingMessage>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                return;
                            }
                            loadingView.error(rankingList);
                        }

                        @Override
                        public void onNext(ResponseResult<ArrayList<RankingMessage>> arrayListResponseResult) {
                            if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                return;
                            }
                            ArrayList<RankingMessage> messages = (ArrayList<RankingMessage>) arrayListResponseResult.getData();
                            if (messages == null || messages.isEmpty()) {
                                loadingView.empty(rankingList);
                            } else {
                                loadingView.loadSuccess(rankingList);
                            }
                            rankingAdapter = new RankingAdapter(getActivity(), messages);
                            rankingList.setAdapter(rankingAdapter);
                            rankingAdapter.notifyDataSetChanged();
                        }
                    });
        } else {
            Map<String, Integer> info = new HashMap<>();
            info.put("testpaperId", testpaperId);
            info.put("leaderboardType", leaderboardType);
            rankingApi.getRanking(info)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseResult<ArrayList<RankingMessage>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                return;
                            }
                            loadingView.error(rankingList);
                        }

                        @Override
                        public void onNext(ResponseResult<ArrayList<RankingMessage>> arrayListResponseResult) {
                            if (getLifecycle().getCurrentState() != Lifecycle.State.RESUMED) {
                                return;
                            }
                            ArrayList<RankingMessage> messages = (ArrayList<RankingMessage>) arrayListResponseResult.getData();
                            if (messages.isEmpty()) {
                                loadingView.empty(rankingList);
                            } else {
                                loadingView.loadSuccess(rankingList);
                            }
                            rankingAdapter = new RankingAdapter(getActivity(), messages);
                            rankingList.setAdapter(rankingAdapter);
                            rankingAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
}
