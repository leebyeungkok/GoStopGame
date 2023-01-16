package com.lbo.gostopscreenconfig;


import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.os.*;


public class MainView extends SurfaceView implements SurfaceHolder.Callback{

    private static int SCREEN_WIDTH = 2000;
    private static int SCREEN_HEIGHT = 1000;

    private MainActivity mMainActivity;
    private MainThread mMainThread;
    private Handler mHandler;
    private Context mMainContext;
    private boolean mDrawCls = false;

    private ScreenConfig mScreenConfig;


    public MainView(Context context){
        super(context);
        getHolder().addCallback(this);
        mMainThread = new MainThread(getHolder(),this);
        setFocusable(true);
        mMainContext = context;

        mHandler = new Handler() {
            public void handleMessage(Message msg){
                switch (msg.what) {

                }
            }
        };
    }
    public void init(int width, int height, MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
        mScreenConfig = new ScreenConfig(width,height, SCREEN_WIDTH,SCREEN_HEIGHT);
        mDrawCls = true;
    }
    @Override
    public  void draw(Canvas canvas){
        super.draw(canvas);
        if(mDrawCls == false) {
            return;
        }
        //canvas.drawColor(Color.WHITE);
        Paint backPaint = new Paint();
        backPaint.setColor(Color.WHITE);
        canvas.drawRect (  mScreenConfig.getX(0) ,
                mScreenConfig.getY(0),
                mScreenConfig.getX(2000),
                mScreenConfig.getY(1000), backPaint);
        backPaint.setColor(Color.RED);
        canvas.drawRect (  mScreenConfig.getX(0) ,
                mScreenConfig.getY(0),
                mScreenConfig.getX(200),
                mScreenConfig.getY(200), backPaint);
        backPaint.setColor(Color.BLUE);
        canvas.drawRect (  mScreenConfig.getX(1800) ,
                mScreenConfig.getY(800),
                mScreenConfig.getX(2000),
                mScreenConfig.getY(1000), backPaint);
    }
    //@Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    // @Override
    public void surfaceCreated(SurfaceHolder holder){

        Log.i("MainView", "surfaceCreated called");
        mMainThread.setRunning(true);
        try {
            if(mMainThread.getState() == Thread.State.TERMINATED ) {
                mMainThread = new MainThread(getHolder(),this);
                mMainThread.setRunning(true);
                setFocusable(true);
                mMainThread.start();
            }
            else {
                mMainThread.start();
            }
        }
        catch(Exception ex) {
            Log.i("MainView", "ex:" + ex.toString());
        }
    }

    //@Override
    public void surfaceDestroyed(SurfaceHolder holder){
        Log.i("MainView", "surfaceDestoryed called");
        boolean retry= true;
        mMainThread.setRunning(false);
        while(retry){
            try{
                mMainThread.join();
                retry= false;
            }
            catch(Exception e) {
                Log.i("MainView", "surfaceDestoryed ex" + e.toString());
            }
        }
    }
}
