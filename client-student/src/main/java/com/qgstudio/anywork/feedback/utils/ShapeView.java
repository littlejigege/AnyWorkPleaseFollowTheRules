package com.qgstudio.anywork.feedback.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ShapeView extends View {

    private static Paint paint = new Paint();
    private int widthSize;
    private int heightSize;

    private int y = 0;

    static {
        paint.setColor(0x88000000);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public ShapeView(Context context) {
        super(context);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setShapeView(int length, int hasWrited) {
        float temp = ((float)hasWrited / (float)length / 2 - (float)0.5) * 2;
        if (temp > 0) {
            y = (int)((float)heightSize * temp) - 5;
            postInvalidate();
        }
    }

    public void setVoid() {
        y = heightSize;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, y, widthSize, heightSize, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
    }
}
