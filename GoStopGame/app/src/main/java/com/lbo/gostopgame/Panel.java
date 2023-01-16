package com.lbo.gostopgame;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Panel
{
    public  int mCardNum;
    private Bitmap mBitmap;
    private Resources mRes;
    private int mBitmapId;
    private int mPanelX;
    private int mPanelY;
    private int mPanelWidth;
    private int mPanelHeight;
    private boolean mIsAble = true;

    public Panel( int num, int width, int height,
                  Resources res, int BITMAP_ID){
        mRes = res;
        mBitmapId = BITMAP_ID;
        mCardNum = num;
        mPanelWidth = width;
        mPanelHeight = height;
        Bitmap mBitmaporg =
                BitmapFactory.decodeResource(mRes, mBitmapId);
        mBitmap = Bitmap.createScaledBitmap(
                mBitmaporg, mPanelWidth, mPanelHeight, false);
    }
    public void destory(){
        if(mBitmap != null){
            mBitmap.recycle();
        }
    }
    public  void move(int x, int y){
        mPanelX = x;
        mPanelY = y;
    }
    public void setAbleCard(boolean is_able){
        this.mIsAble = is_able;
    }
    public boolean isSelected(int x, int y){
        boolean is_selected = false;
        if(mIsAble == true)	{
            if( (x > mPanelX && x < mPanelX + mPanelWidth) &&
                    (y > mPanelY && y < mPanelY + mPanelHeight)){
                is_selected = true;
            }
        }
        return is_selected;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(mBitmap,mPanelX, mPanelY, null);
    }
}

