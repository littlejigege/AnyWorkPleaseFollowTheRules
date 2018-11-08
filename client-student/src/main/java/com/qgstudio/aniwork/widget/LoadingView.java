package com.qgstudio.aniwork.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.qgstudio.aniwork.R;
import com.qgstudio.aniwork.utils.DesityUtil;

public class LoadingView extends FrameLayout {
    private OnRetryListener retryListener;
    private LottieAnimationView progressBar;
    private TextView stateTextView;

    public LoadingView(@NonNull Context context) {
        super(context);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
        addProgressBar();
        addStateTextView();
        setVisibility(GONE);
    }

    private void addProgressBar() {
        progressBar = new LottieAnimationView(getContext());
        progressBar.setAnimation(R.raw.loading);
        progressBar.setImageAssetsFolder("loader/");
        progressBar.loop(true);
        progressBar.playAnimation();
        LayoutParams layoutParams = new LayoutParams(DesityUtil.dp2px(getContext(),100), DesityUtil.dp2px(getContext(),100), Gravity.CENTER);
        progressBar.setLayoutParams(layoutParams);
        addView(progressBar);
    }

    private void addStateTextView() {
        stateTextView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        stateTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = stateTextView.getTag();
                if (tag != null && (Boolean) tag) {
                    if (retryListener != null) {
                        retryListener.onRetry();
                    }
                }
            }
        });
        stateTextView.setLayoutParams(layoutParams);
        stateTextView.setTextColor(Color.parseColor("#616161"));
        stateTextView.setGravity(Gravity.CENTER);
        addView(stateTextView);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        retryListener = listener;
    }

    public void loadSuccess(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
        }
        setVisibility(GONE);
    }

    public void load(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        progressBar.setVisibility(VISIBLE);
        stateTextView.setVisibility(GONE);
        progressBar.playAnimation();
    }

    public void empty(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        stateTextView.setVisibility(VISIBLE);
        stateTextView.setText("空空如也");
        stateTextView.setTag(false);
    }

    public void error(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
        setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        stateTextView.setVisibility(VISIBLE);
        stateTextView.setText("出现错误，点击重试");
        stateTextView.setTag(true);
    }

    public interface OnRetryListener {
        void onRetry();
    }
}
