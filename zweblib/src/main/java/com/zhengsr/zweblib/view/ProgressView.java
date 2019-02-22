package com.zhengsr.zweblib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhengshaorui
 * Time on 2019/2/22
 */

public class ProgressView extends View {
    private static final String TAG = "ProgressView";
    private Paint mPaint;
    private int mWidth,mHeight = 3;
    private int mCurentWidth;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FF4081"));
        mPaint.setStrokeWidth(10);
    }

    public ProgressView setSize(int width,int height){
        if (width != -1) {
            mWidth = width;
        }
        if (height != -1) {
            mHeight = height;
        }
        return this;
    }

    public void setProgress(int progress){
        mCurentWidth = (int) (progress * 1.0f / 100  * mWidth);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,mCurentWidth,mHeight,mPaint);
    }

    public void setColor(int color){
        if (color != -1) {
            mPaint.setColor(color);
        }
    }

}
