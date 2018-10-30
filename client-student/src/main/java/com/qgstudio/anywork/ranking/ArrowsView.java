package com.qgstudio.anywork.ranking;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qgstudio.anywork.R;

public class ArrowsView extends View{

    private Paint paint;

    private float height = 0;
    private float width = 0;

    private float head = 2/3;
    private float tail = 1/3;

    private float x1, y1;
    private float x2, y2;
    private float x3, y3;

    public ArrowsView(Context context) {
        super(context);
    }

    public ArrowsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(0xFF2E85EA);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArrowsView);
        height = typedArray.getDimension(R.styleable.ArrowsView_android_layout_height, 0);
        width = typedArray.getDimension(R.styleable.ArrowsView_android_layout_width, 0);
        Log.d("linzonghzhan", "ArrowsView: " + height + "," + width);
        x1 = width / 4;
        y1 = height / 5 * 2;
        x2 = width / 2;
        y2 = height / 5 * 3;
        x3 = width / 4 * 3;
        y3 = height / 5 * 2;
    }

    public ArrowsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setArguments(float head, float tail) {
        this.head = head;
        this.tail = tail;

        y2 = head * height;

        y1 = tail * width;
        y3 = tail * width;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] pts = {x1, y1, x2 + 3, y2, x2 - 3, y2, x3, y3};
        Log.d("linzongzhan", "onDraw: " + x1 + "," + y1 + "," + x2 + "," + y2 + "," + x3 + "," + y3);
        canvas.drawLines(pts, paint);
    }

    public void mUp() {
        ValueAnimator animator = ValueAnimator.ofFloat(30, 20);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                curValue = curValue / 50;
                setArguments(curValue, 1 - curValue);
                invalidate();
            }
        });
        animator.start();
    }

    public void mDown() {
        ValueAnimator animator = ValueAnimator.ofFloat(20, 30);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                curValue = curValue / 50;
                setArguments(curValue, 1 - curValue);
                invalidate();
            }
        });
        animator.start();
    }
}
