package com.qgstudio.aniwork.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class RankRecyclerView extends RecyclerView {
    public boolean isNeedScroll = true;

    public RankRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RankRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RankRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_MOVE && !isNeedScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(e);
    }
}
