package com.qgstudio.aniwork.main;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.qgstudio.aniwork.R;
import com.qgstudio.aniwork.collection.CollectionActivity;
import com.qgstudio.aniwork.data.model.Organization;
import com.qgstudio.aniwork.mvp.MVPBaseFragment;
import com.qgstudio.aniwork.notice.NoticeActivity;
import com.qgstudio.aniwork.notice.NoticeAdapter;
import com.qgstudio.aniwork.notice.data.Notice;
import com.qgstudio.aniwork.websocket.ThreadMode;
import com.qgstudio.aniwork.websocket.WS;
import com.qgstudio.aniwork.websocket.WebSocketHolder;
import com.qgstudio.aniwork.widget.LoadingView;
import com.qgstudio.aniwork.workout.WorkoutContainerActivity;
import com.qgstudio.aniwork.workout.WorkoutType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends MVPBaseFragment<HomeContract.HomeView, HomePresenter> implements HomeContract.HomeView {
    @BindView(R.id.btn_my_class)
    TextView btnMyClass;
    @BindView(R.id.toolbar)
    View toolbar;
    @BindView(R.id.tv_online_count)
    TextView tvOnlineCount;
    @BindView(R.id.top_view)
    View topView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.recycler_view_notice)
    RecyclerView recyclerView;
    NoticeAdapter adapter;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showToast(String s) {

    }

    @Override
    public int getRootId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        btnMyClass.setTag(new Organization(-1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.sample_blue));
        }
        //监听在线人数，使用livedata，自动适应生命周期
        WebSocketHolder.getDefault().onlineCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                tvOnlineCount.setText(integer == null ? "0" : integer.toString());
            }
        });
        //获得系统状态栏高度
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelOffset(resourceId);
        }
        Log.e("gaodu", result + "");
        topView.getLayoutParams().height = result;
        topView.setLayoutParams(topView.getLayoutParams());
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                mPresenter.getNoticeNew();
            }
        });
    }

    @Override
    public void loadData(View view) {

    }

    @OnClick(R.id.btn_my_class)
    public void clickMyClass() {

        if (((Organization) btnMyClass.getTag()).getOrganizationId() == -1) {
            startActivity(new Intent(getActivity(), NewOrganizationActivity.class));
        }
    }

    @OnClick(R.id.btn_preview)
    public void clickPreview() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.PREVIEW, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_exercise)
    public void clickExercise() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.EXERCISE, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_exam)
    public void clickExam() {
        WorkoutContainerActivity.start(getActivity(), WorkoutType.EXAM, ((Organization) btnMyClass.getTag()).getOrganizationId());

    }

    @OnClick(R.id.btn_collection)
    public void clickCollection() {
        startActivity(new Intent(getActivity(), CollectionActivity.class));
    }

    /**
     * 跳转到公告列表活动
     */
    @OnClick(R.id.btn_notice_all)
    public void clickNoticeAll() {
        startActivity(new Intent(getActivity(), NoticeActivity.class));
    }

    @OnClick(R.id.tv_notice)
    public void clickNoticeText() {

    }

    @Override
    public void onMyClassGot(Organization organization) {
        btnMyClass.setTag(organization == null ? new Organization(-1) : organization);
        btnMyClass.setText(organization == null ? "无班级，快去pick你的班级吧  >" : organization.getOrganizationName());
    }

    @Override
    public void onNoticeGet(List<Notice> notices) {
        if (notices != null) {
            if (adapter == null) {
                adapter = new NoticeAdapter(notices, getActivity());
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            } else {
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
                adapter.list.clear();
                adapter.list.addAll(notices);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @WS(threadMode = ThreadMode.MAIN)
    public void onRealtimeNoticeGet(Notice notice) {
        //收到公告推送，重新拉一遍
        mPresenter.getNoticeNew();
    }

    @Override
    public void onResume() {
        super.onResume();

        //每次进入碎片都要重新拉取数据
        mPresenter.getJoinOrganization();
        WebSocketHolder.getDefault().register(this);
        mPresenter.getNoticeNew();
    }

    public void loading() {
        //为了使视图看起来稳定，只有在界面上没有item的时候才显示loading，否则静默加载
        if (adapter == null || adapter.list.isEmpty()) {
            loadingView.load(recyclerView);
        }
    }

    public void loadSuccess() {
        loadingView.loadSuccess(recyclerView);
    }

    public void loadEmpty() {
        loadingView.empty(recyclerView);
    }

    public void loadError() {
        loadingView.error(recyclerView);
    }

    @Override
    public void onStop() {
        super.onStop();
        WebSocketHolder.getDefault().unregister(this);
    }

}
