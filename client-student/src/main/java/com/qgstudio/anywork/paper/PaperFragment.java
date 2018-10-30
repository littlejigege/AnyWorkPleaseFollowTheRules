package com.qgstudio.anywork.paper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.paper.data.PaperRepository;
import com.qgstudio.anywork.mvp.MVPBaseFragment;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示考试或练习(单个)列表
 * @author Yason 2017/4/2.
 */

public class PaperFragment extends MVPBaseFragment<PaperFragView, PaperRepository> implements PaperFragView {

    @BindView(R.id.recycler_paper) RecyclerView mRecyclerView;
    @BindView(R.id.refresh) SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.bg) RelativeLayout mBg;//加载异常时显示的布局
    @BindView(R.id.img) ImageView mImg;
    @BindView(R.id.tv) TextView mTv;

    private PaperAdapter mPaperAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private int mType;//fragment类型，考试或练习
    private int mOrganizationId;

    public static final int TYPE_PRACTICE = 0;
    public static final int TYPE_EXMINATION = 1;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("TYPE");
        mOrganizationId = getArguments().getInt("ORGANIZATION_ID");
    }

    public static PaperFragment newInstance(int type, int organizationId) {
        PaperFragment fragment = new PaperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        bundle.putInt("ORGANIZATION_ID", organizationId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getRootId() {
        return R.layout.fragment_paper;
    }

    @Override
    public void initView() {
        mUnbinder = ButterKnife.bind(this, mRoot);

        mPaperAdapter = new PaperAdapter(mActivity,new ArrayList<Testpaper>());
        mRecyclerView.setAdapter(mPaperAdapter);

        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mSwipeRefreshLayout.setEnabled(false);

    }

    @Override
    public void loadData() {
        switch (mType) {
            case TYPE_EXMINATION: {
                mPresenter.getExaminationPaper(mOrganizationId);
                break;
            }
            case TYPE_PRACTICE: {
                mPresenter.getPracticePaper(mOrganizationId);
                break;
            }
            default: {}
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void addPracticePapers(List<Testpaper> testpapers) {
        addAll(testpapers);
    }

    @Override
    public void addExaminationPapers(List<Testpaper> testpapers) {
        addAll(testpapers);
    }

    @Override
    public void showImageError() {
        mSwipeRefreshLayout.setVisibility(View.GONE);

        mBg.setVisibility(View.VISIBLE);
        mImg.setImageResource(R.drawable.bg_load_error);
        mTv.setText(R.string.tip_load_error);

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    public void hideImageError() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        mBg.setVisibility(View.GONE);
    }

    @Override
    public void showImageBlank() {
        mSwipeRefreshLayout.setVisibility(View.GONE);

        mBg.setVisibility(View.VISIBLE);
        mImg.setImageResource(R.drawable.bg_load_blank);
        mTv.setText(R.string.tip_load_blank);
    }

//    @Override
//    public void hideImageBlank() {
//        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
//        mBg.setVisibility(View.GONE);
//    }

    @Override
    public void showLoading() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(s);
    }

    private void addAll(List<Testpaper> papers){
        mPaperAdapter.addAll(papers);
    }

}
