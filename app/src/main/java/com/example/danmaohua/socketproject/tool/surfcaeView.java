package com.example.danmaohua.socketproject.tool;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class surfcaeView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder mHolder;
    /**
     * 与surfaceView绑定的View
     */
    private Canvas mCanvas;
    /**
     * 用于绘制的线程
     */

    private Thread t;

    /**
     *线程的控制开关
     */

    private boolean isRunning;

    public surfcaeView(Context context) {
        super(context);
    }

    public surfcaeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHolder=getHolder();
        mHolder.addCallback(this);
        //setZOrderOnTop(true);// 设置画布 背景透明  
        //mHolder.setFormat(PixelFormat.TRANSLUCENT);  
          //设置可获得焦点  
         setFocusable(true);
         setFocusableInTouchMode(true);
          //设置常亮  
          this.setKeepScreenOn(true);

    }

    public surfcaeView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
         isRunning=true;
         t=new Thread(this);
         t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {
        while (isRunning){
            draw();
        }
    }
    private void draw(){
        try {
            mCanvas=mHolder.lockCanvas();
            if (mCanvas!=null){
              //drawSomething
            }
        }catch (Exception e){

        }finally {
         if (mCanvas!=null){
             mHolder.unlockCanvasAndPost(mCanvas);
         }
        }

    }
}
