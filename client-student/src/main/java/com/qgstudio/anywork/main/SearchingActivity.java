package com.qgstudio.anywork.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.qgstudio.anywork.R;
import com.qgstudio.anywork.common.DialogManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchingActivity extends DialogManagerActivity {

    @BindView(R.id.edi_searching) EditText searching;

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.frame, OrganizationFragment.newInstance(OrganizationFragment.TYPE_ALL))
                .commit();

        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                OrganizationFragment ofm = (OrganizationFragment) mFragmentManager.getFragments().get(0);
                ofm.filter(s.toString());
            }
        });


    }

    @OnClick(R.id.btn_back)
    public void back() {
        finishAty();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchingActivity.class);
        context.startActivity(intent);
    }

    private void finishAty(){
        this.finish();
    }

}
