package com.qgstudio.anywork.workout;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.search.SearchActivity;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.workout.data.Chapter;
import com.qgstudio.anywork.workout.data.Testpaper;
import com.qgstudio.anywork.workout.data.WorkoutInfo;

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
        //viewModel.getChapter();
        return view;
    }

    private void initToolbar() {
        TextView tvTittle = toolbar.findViewById(R.id.tv_tittle);
        tvTittle.setText(type.value);
    }

    @OnClick(R.id.btn_back)
    public void clickBack() {
        if (adapter != null && adapter.datas.get(0).getClass() == Testpaper.class) {
            viewModel.getChapter();
        } else if (!getFragmentManager().popBackStackImmediate()) {
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
}
