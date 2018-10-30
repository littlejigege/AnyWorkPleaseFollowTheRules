package com.qgstudio.anywork.search;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.utils.ToastUtil;
import com.qgstudio.anywork.workout.ChapterAdapter;
import com.qgstudio.anywork.workout.data.Testpaper;

import java.lang.reflect.Field;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(R.id.recycler_view_paper)
    RecyclerView recyclerView;
    ChapterAdapter adapter;
    SearchViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        //初始化布局
        initView();
    }

    private void initView() {
        //调整状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        initSearch();

    }

    /**
     * 设置搜索框
     */
    private void initSearch() {
        //设置搜索
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setQueryHint("请输入搜索关键字...");
        LinearLayout view = (LinearLayout) searchView.getChildAt(0);
        LinearLayout view1 = (LinearLayout) view.getChildAt(2);
        LinearLayout view2 = (LinearLayout) view1.getChildAt(1);
        AutoCompleteTextView view3 = (AutoCompleteTextView) view2.getChildAt(0);
        view3.setHintTextColor(getResources().getColor(R.color.text_hint));
        //观察数据
        viewModel.paperList.observe(this, new Observer<List<Testpaper>>() {
            @Override
            public void onChanged(@Nullable List<Testpaper> testpapers) {
                onSearchResult(testpapers);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void onSearchResult(List<Testpaper> result) {
        if (adapter == null) {
            adapter = new ChapterAdapter(result, this);
            adapter.setTest(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            adapter.datas.clear();
            adapter.datas.addAll(result);
            adapter.notifyDataSetChanged();
        }

    }


    @OnClick(R.id.btn_back)
    public void clickBack() {
        onBackPressed();
    }
}
