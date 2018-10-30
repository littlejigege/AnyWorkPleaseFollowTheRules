package com.qgstudio.anywork.collection;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.qgstudio.anywork.R;
import com.qgstudio.anywork.data.model.Question;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_back)
    View btnBack;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    CollectionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.sample_blue));
        }
        viewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);
        //请求id列表
//        List<Question> list = new ArrayList<>();
//        for (int i = 454; i <= 454 + 15; i++) {
//            Question question = new Question();
//            question.setQuestionId(i);
//            list.add(question);
//        }
//        recyclerView.setAdapter(new CollectionAdapter(list));
//        recyclerView.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
        viewModel.getAllContions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if (questions == null) {
                    questions = new ArrayList<>();
                }
                recyclerView.setAdapter(new CollectionAdapter(questions));
                recyclerView.setLayoutManager(new LinearLayoutManager(CollectionActivity.this));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
