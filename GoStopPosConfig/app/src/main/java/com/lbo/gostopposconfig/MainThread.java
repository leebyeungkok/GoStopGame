package com.lbo.gostopposconfig;


import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;


public class MainThread extends Thread {
    private SurfaceHolder mSurfaceHolder;
    private MainView mMainView;
    private boolean mRunning = false;

    public MainThread(SurfaceHolder surfaceHolder, MainView mMainView) {
        mSurfaceHolder = surfaceHolder;
        this.mMainView = mMainView;
    }

    public SurfaceHolder getSurfaceHolder()
    {
        return mSurfaceHolder;
    }
    public void setRunning(boolean run){
        mRunning = run;
    }

    @Override
    public void run(){
        Log.i("mainThread", "run called:" + mRunning);
        try {
            Canvas c;
            while(mRunning){
                c = null;
                try{
                    c = mSurfaceHolder.lockCanvas(null);
                    synchronized(mSurfaceHolder){
                        try {
                            mMainView.draw(c);
                            Thread.sleep(2);
                        }
                        catch(Exception exTemp) {
                            Log.e("", exTemp.toString());
                        }
                    }
                }
                finally{
                    if( c!= null){
                        mSurfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
        catch(Exception exTot) {
            Log.e("", exTot.toString());
        }
    }
}
