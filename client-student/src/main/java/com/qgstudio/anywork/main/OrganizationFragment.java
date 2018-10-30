package com.qgstudio.anywork.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Filter;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.common.DialogManagerActivity;
import com.qgstudio.anywork.data.model.Organization;
import com.qgstudio.anywork.main.data.OrganizationRepository;
import com.qgstudio.anywork.paper.PaperActivity;
import com.qgstudio.anywork.mvp.MVPBaseFragment;
import com.qgstudio.anywork.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OrganizationFragment extends MVPBaseFragment<OrganizationFragView, OrganizationRepository> implements OrganizationFragView {

    @BindView(R.id.recycler_all)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
//    @BindView(R.id.empty_view)
//    View mEmptyView;

    private int mType;//fragment类型

    public static final int TYPE_ALL = 0;//显示全部组织
    public static final int TYPE_JOIN = 1;//只显示已加入的组织

    private OrganizationAdapter mOrganizationAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = (int) getArguments().get("TYPE");
    }

    public static OrganizationFragment newInstance(int type) {
        OrganizationFragment fragment = new OrganizationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getRootId() {
        return R.layout.fragment_organization;
    }

    @Override
    public void initView() {
        mUnbinder = ButterKnife.bind(this, mRoot);

        mOrganizationAdapter = new OrganizationAdapter(mType, (DialogManagerActivity) mActivity, new ArrayList<Organization>());
        mOrganizationAdapter.setJoinOrganizationListener(new OrganizationAdapter.JoinOrganizationListener() {
            @Override
            public void join(int organizationId, String password, int position) {
                mPresenter.joinOrganization(organizationId, password, position);
            }
        });
//        mOrganizationAdapter.setLeaveOrganizationListener(new OrganizationAdapter.LeaveOrganizationListener() {
//            @Override
//            public void leave(int organizationId, int position) {
//                mPresenter.leaveOrganization(organizationId, position);
//            }
//        });
        mRecyclerView.setAdapter(mOrganizationAdapter);

        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if (mType == TYPE_ALL) {
            mSwipeRefreshLayout.setEnabled(false);
        } else {
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mPresenter.updateJoinOrganization();
                }
            });
        }

    }

    @Override
    public void loadData() {
        switch (mType) {
            case TYPE_ALL: {
                mPresenter.getAllOrganization();
                break;
            }
            case TYPE_JOIN: {
                mPresenter.getJoinOrganization();
                break;
            }
            default: {
            }
        }
    }

    @Override
    public void onDestroyView() {
        mUnbinder.unbind();
        super.onDestroyView();
    }

    /**
     * 根据 organizationId 进行过滤
     */
    public void filter(String s) {
        Filter f = mOrganizationAdapter.getFilter();
        if (f != null) {
            f.filter(s);
        }
    }

    @Override
    public void addOrganization(Organization organization) {
        mOrganizationAdapter.add(organization);
    }

    @Override
    public void addOrganizations(List<Organization> organizations) {
        //无组织提示
//        mEmptyView.setVisibility(organizations.isEmpty() ? View.VISIBLE : View.GONE);
        mOrganizationAdapter.addAll(organizations);
    }

    @Override
    public void removeOrganization(int position) {
        mOrganizationAdapter.remove(position);
    }

    @Override
    public void startPaperAty(int organizatonId) {
        PaperActivity.start(mActivity, organizatonId);
    }

    @Override
    public void updateItemJoinStatus(int positon, boolean isJoin) {
        mOrganizationAdapter.updateItemJoinStatus(positon, isJoin);
    }

    @Override
    public void sendUpdateBroadCast() {
        Intent intent = new Intent(HomeActivity.ACTION);
        mActivity.sendBroadcast(intent);
    }

    @Override
    public void destroySelf() {
        mActivity.finish();
    }

    @Override
    public void showLoading() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideLoading() {
        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showToast(String s) {
        ToastUtil.showToast(s);
    }
}

