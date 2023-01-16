package com.lbo.gostopcard;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    private Card[] mCard;

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

        mCard = new Card[48+1];
        for (int i=0; i<=48; i++ ){
            mCard[i]= new Card(i,
                    mScreenConfig.mCardWidth,
                    mScreenConfig.mCardHeight,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    CardInfo.mCard[i][CardInfo.C_PIC],
                    CardInfo.mCard[0][CardInfo.C_PIC]);
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
            mCard[i].move(
                    (int)mScreenConfig.mInMyHandX + mScreenConfig.mCardBigWidth * i,
                    (int)mScreenConfig.mInMyHandY   );
            mCard[i].drawBig(canvas);
        }
    }
    public void onDrawInYourHand(Canvas canvas){
        for(int i=10; i<20;i++){
            mCard[i].move(
                    (int)mScreenConfig.mInYourHandX + mScreenConfig.mCardBigWidth * (i-10),
                    (int)mScreenConfig.mInYourHandY);
            mCard[i].drawBigClose(canvas);
        }
    }
    public void onDrawField(Canvas canvas){
        for(int i=20; i<32;i++){
            mCard[i].move(
                    (int)mScreenConfig.mFieldDetailX[i-20 + 1],
                    (int)mScreenConfig.mFieldDetailY[i-20 + 1]);
            mCard[i].draw(canvas);
        }
    }
    public void onDrawCenter(Canvas canvas){
        mCard[32].move((int)mScreenConfig.mCenterX,
                (int)mScreenConfig.mCenterY);
        mCard[32].drawClose(canvas);
    }
    public void onDrawInMyGet20(Canvas canvas){
        mCard[33].move((int)mScreenConfig.mInMyGet20X ,
                (int)mScreenConfig.mInMyGet20Y);
        mCard[33].draw(canvas);
    }
    public void onDrawInMyGet10(Canvas canvas){
        mCard[34].move((int)mScreenConfig.mInMyGet10X ,
                (int)mScreenConfig.mInMyGet10Y);
        mCard[34].draw(canvas);
    }
    public void onDrawInMyGet05(Canvas canvas){
        mCard[35].move((int)mScreenConfig.mInMyGet05X ,
                (int)mScreenConfig.mInMyGet05Y);
        mCard[35].draw(canvas);
    }
    public void onDrawInMyGet01(Canvas canvas){
        mCard[36].move((int)mScreenConfig.mInMyGet01X ,
                (int)mScreenConfig.mInMyGet01Y);
        mCard[36].draw(canvas);
    }
    public void onDrawInYourGet20(Canvas canvas){
        mCard[37].move((int)mScreenConfig.mInYourGet20X ,
                (int)mScreenConfig.mInYourGet20Y);
        mCard[37].draw(canvas);
    }
    public void onDrawInYourGet10(Canvas canvas){
        mCard[38].move( (int)mScreenConfig.mInYourGet10X ,
                (int)mScreenConfig.mInYourGet10Y);
        mCard[38].draw(canvas);
    }
    public void onDrawInYourGet05(Canvas canvas){
        mCard[39].move((int)mScreenConfig.mInYourGet05X ,
                (int)mScreenConfig.mInYourGet05Y);
        mCard[39].draw(canvas);
    }
    public void onDrawInYourGet01(Canvas canvas){
        mCard[40].move((int)mScreenConfig.mInYourGet01X ,
                (int)mScreenConfig.mInYourGet01Y);
        mCard[40].draw(canvas);
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
