package com.lbo.gostopposconfig;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainView extends SurfaceView implements SurfaceHolder.Callback{

    private static int SCREEN_WIDTH = 2000;
    private static int SCREEN_HEIGHT = 1000;

    private MainActivity mMainActivity;
    private MainThread mMainThread;
    private Handler mHandler;
    private Context mMainContext;
    private boolean mDrawCls = false;

    private ScreenConfig mScreenConfig;

    private Bitmap[] mBitmap = new Bitmap[50];

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
        CardInfo.init();
        for(int i=0; i<=48; i++){
            Bitmap bitmaporg = BitmapFactory.decodeResource(getResources(), CardInfo.mCard[i][CardInfo.C_PIC]);
            mBitmap[i]= Bitmap.createScaledBitmap(bitmaporg, mScreenConfig.mCardWidth, mScreenConfig.mCardHeight, false);
        }

        mDrawCls = true;
    }
    @Override
    public  void draw(Canvas canvas){
        super.draw(canvas);
        if(mDrawCls == false) {
            return;
        }
        canvas.drawColor(Color.rgb(0,100,0));
        onDrawInMyHand(canvas);
        onDrawInYourHand(canvas);
        onDrawField(canvas);
        onDrawCenter(canvas);
        onDrawInMyGet20(canvas);
        onDrawInMyGet10(canvas);
        onDrawInMyGet05(canvas);
        onDrawInMyGet01(canvas);
        onDrawInYourGet20(canvas);
        onDrawInYourGet10(canvas);
        onDrawInYourGet05(canvas);
        onDrawInYourGet01(canvas);
    }
    public void onDrawInMyHand(Canvas canvas){
        for(int i=0; i<10;i++){
            canvas.drawBitmap(mBitmap[i],
                    (int)mScreenConfig.mInMyHandX + mScreenConfig.mCardBigWidth * i,
                    (int)mScreenConfig.mInMyHandY, null);
        }
    }
    public void onDrawInYourHand(Canvas canvas){
        for(int i=10; i<20;i++){
            canvas.drawBitmap(mBitmap[i],
                    (int)mScreenConfig.mInYourHandX + mScreenConfig.mCardBigWidth * (i-10),
                    (int)mScreenConfig.mInYourHandY + 50, null);
        }
    }
    public void onDrawField(Canvas canvas){
        for(int i=20; i<32;i++){
            canvas.drawBitmap(mBitmap[i],
                    (int)mScreenConfig.mFieldDetailX[i-20 + 1],
                    (int)mScreenConfig.mFieldDetailY[i-20 + 1], null);
        }
    }
    public void onDrawCenter(Canvas canvas){
        canvas.drawBitmap(mBitmap[32],
                (int)mScreenConfig.mCenterX,
                (int)mScreenConfig.mCenterY, null);
    }
    public void onDrawInMyGet20(Canvas canvas){
        canvas.drawBitmap(mBitmap[33],
                (int)mScreenConfig.mInMyGet20X ,
                (int)mScreenConfig.mInMyGet20Y, null);
    }
    public void onDrawInMyGet10(Canvas canvas){
        canvas.drawBitmap(mBitmap[34],
                (int)mScreenConfig.mInMyGet10X ,
                (int)mScreenConfig.mInMyGet10Y, null);
    }
    public void onDrawInMyGet05(Canvas canvas){
        canvas.drawBitmap(mBitmap[35],
                (int)mScreenConfig.mInMyGet05X ,
                (int)mScreenConfig.mInMyGet05Y, null);
    }
    public void onDrawInMyGet01(Canvas canvas)
    {
        canvas.drawBitmap(mBitmap[36],
                (int)mScreenConfig.mInMyGet01X ,
                (int)mScreenConfig.mInMyGet01Y, null);
    }
    public void onDrawInYourGet20(Canvas canvas){
        canvas.drawBitmap(mBitmap[37],
                (int)mScreenConfig.mInYourGet20X ,
                (int)mScreenConfig.mInYourGet20Y, null);
    }
    public void onDrawInYourGet10(Canvas canvas){
        canvas.drawBitmap(mBitmap[38],
                (int)mScreenConfig.mInYourGet10X ,
                (int)mScreenConfig.mInYourGet10Y, null);
    }
    public void onDrawInYourGet05(Canvas canvas){
        canvas.drawBitmap(mBitmap[39],
                (int)mScreenConfig.mInYourGet05X ,
                (int)mScreenConfig.mInYourGet05Y, null);
    }
    public void onDrawInYourGet01(Canvas canvas){
        canvas.drawBitmap(mBitmap[40],
                (int)mScreenConfig.mInYourGet01X ,
                (int)mScreenConfig.mInYourGet01Y, null);
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
