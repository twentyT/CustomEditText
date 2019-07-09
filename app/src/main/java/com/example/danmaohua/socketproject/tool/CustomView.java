package com.example.danmaohua.socketproject.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.danmaohua.socketproject.R;


public class CustomView extends View {
    private String TAG = "自定义View";

    private int paintWidth = 3;
    private int canvasColor;
    private int paintColor;
    private int animationSpeed;

    @SuppressLint("ResourceAsColor")
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
        paintWidth = ta.getInteger(R.styleable.CustomView_paintWidth, 3);
        canvasColor = ta.getColor(R.styleable.CustomView_canvasColor, R.color.red);
        paintColor = ta.getColor(R.styleable.CustomView_paintColor, R.color.blak);
        animationSpeed = ta.getInteger(R.styleable.CustomView_speedSelect, 0);
    }

    private Boolean start = true;
    private AnmationThread anmationThread;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.v(TAG, "5");
        canvas.drawColor(canvasColor);
        Paint mPaint = new Paint();
        mPaint.setColor(paintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF re21 = new RectF(paintWidth / 2, paintWidth / 2, canvas.getWidth() - paintWidth / 2, canvas.getHeight() - paintWidth / 2);
        canvas.drawOval(re21, mPaint);
        Log.v(TAG, "canvas宽高=" + canvas.getWidth() + "高" + canvas.getHeight());
        if (anmationThread == null) {
            anmationThread = new AnmationThread();
            anmationThread.start();
        }

    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.v(TAG, "4");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.v(TAG, "3");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200 + paintWidth * 2 + getPaddingLeft() + getPaddingRight(), 200 + paintWidth * 2 + getPaddingBottom() + getPaddingTop());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200 + paintWidth * 2 + getPaddingLeft() + getPaddingRight(), heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 200 + paintWidth * 2 + getPaddingBottom() + getPaddingTop());
        }
    }

    private boolean addorresubtrca = true;

    class AnmationThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    sleep(animationSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (addorresubtrca) {
                        if (paintWidth > 0) {
                            --paintWidth;
                        } else {
                            addorresubtrca = false;
                        }
                    } else {
                        if (paintWidth < 51) {
                            ++paintWidth;
                        } else {
                            addorresubtrca = true;
                        }
                    }

                    Message message = mHandler.obtainMessage();
                    mHandler.sendMessage(message);
                }
            } while (start);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "触摸事件" + "MOVE");
            case MotionEvent.ACTION_DOWN:
                Log.v(TAG, "触摸事件" + "DOWN");
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "触摸事件" + "UP");
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.v(TAG, "触摸事件" + "CANCEL");

        }
        return super.onTouchEvent(event);
    }

    public void stopAnimation() {
        Log.v(TAG, "触摸事件" + "停止呀");
        start = false;
    }
}



