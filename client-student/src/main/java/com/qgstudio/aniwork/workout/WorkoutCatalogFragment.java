package com.qgstudio.aniwork.workout;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qgstudio.aniwork.R;
import com.qgstudio.aniwork.search.SearchActivity;
import com.qgstudio.aniwork.utils.ToastUtil;
import com.qgstudio.aniwork.widget.LoadingView;
import com.qgstudio.aniwork.workout.data.Testpaper;
import com.qgstudio.aniwork.workout.data.WorkoutInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WorkoutCatalogFragment extends Fragment {

    @BindView(R.id.workout_frag_toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_workout)
    RecyclerView recyclerView;
    public WorkoutType type;
    public int classId;
    private ChapterViewModel viewModel;
    private ChapterAdapter adapter;
    public int chapterID;
    @BindView(R.id.loading_view)
    public LoadingView loadingView;

    public WorkoutCatalogFragment() {
        // Required empty public constructor
    }


    public static WorkoutCatalogFragment newInstance(WorkoutType type, int classId) {
        WorkoutCatalogFragment fragment = new WorkoutCatalogFragment();
        Bundle args = new Bundle();
        args.putSerializable("TYPE", type);
        args.putInt("CLASS_ID", classId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = (WorkoutType) getArguments().getSerializable("TYPE");
            classId = getArguments().getInt("CLASS_ID");
        }
        viewModel = ViewModelProviders.of(this).get(ChapterViewModel.class);
        viewModel.setFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout_catalog, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getActivity().getResources().getColor(R.color.sample_blue));
        }

        initToolbar();
        loadingView.setOnRetryListener(new LoadingView.OnRetryListener() {
            @Override
            public void onRetry() {
                viewModel.getChapter();
            }
        });
        return view;
    }

    private void initToolbar() {
        TextView tvTittle = toolbar.findViewById(R.id.tv_tittle);
        tvTittle.setText(type.value);
    }

    @OnClick(R.id.btn_back)
    public void clickBack() {
        if (adapter != null && !adapter.datas.isEmpty() && adapter.datas.get(0).getClass() == Testpaper.class) {
            //退回到章节
            viewModel.getChapter();
        } else if (!getFragmentManager().popBackStackImmediate()) {
            //退回主页
            getActivity().finish();
        }
    }

    @OnClick(R.id.btn_search)
    public void clickSearch() {
        startActivity(new Intent(getActivity(), SearchActivity.class));
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void onChapterGet(List<? extends WorkoutInfo> list) {
        if (adapter == null) {
            adapter = new ChapterAdapter(list, getActivity());
            adapter.setOnChapterClickListener(new ChapterAdapter.OnChapterClickListener() {
                @Override
                public void onChapterClick(int chapterID) {
                    WorkoutCatalogFragment.this.chapterID = chapterID;
                    viewModel.getCatalog(chapterID);
                }
            });
        } else {

            adapter.datas.clear();
            adapter.datas.addAll(list);
            adapter.notifyDataSetChanged();
        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void onCatalogGet(List<? extends WorkoutInfo> list) {

        if (adapter == null) {
            adapter = new ChapterAdapter(list, getActivity());
        } else {
            if (list == null) {
                ToastUtil.showToast("无题目");
            } else {

                adapter.datas.clear();
                adapter.datas.addAll(list);
                adapter.notifyDataSetChanged();
            }

        }
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setTest(type == WorkoutType.EXAM);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null && !adapter.datas.isEmpty()) {
            if (adapter.datas.get(0).getClass() == Testpaper.class) {
                viewModel.getCatalog(chapterID);
            } else {
                viewModel.getChapter();
            }
        } else {
            viewModel.getChapter();
        }

    }


    public void loading() {
        loadingView.load(recyclerView);
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


}
