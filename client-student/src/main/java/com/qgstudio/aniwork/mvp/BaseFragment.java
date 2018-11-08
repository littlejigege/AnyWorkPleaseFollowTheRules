package com.qgstudio.aniwork.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * 自定义 BaseFragment 模版
 * Created by Yason on 2017/4/3.
 */

public abstract class BaseFragment extends Fragment {
    private WeakReference viewWeakReference;


    /**
     * 供子类调用的父Activity，不要直接getActivity()
     */
    protected Activity mActivity;

    /**
     * 在此方法中返回顶层视图
     */
    public abstract int getRootId();

    /**
     * 在此方法中初始化View
     */
    public abstract void initView(View view);

    /**
     * 在此方法中请求数据
     */
    public abstract void loadData(View view);


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //试图不让rootView死亡，避免闪烁
        if (viewWeakReference == null || viewWeakReference.get() == null) {
            View root = inflater.inflate(getRootId(), container, false);
            viewWeakReference = new WeakReference<View>(root);
        }
        initView((View) viewWeakReference.get());
        loadData((View) viewWeakReference.get());
        return (View) viewWeakReference.get();
    }

}
