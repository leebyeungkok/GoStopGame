package com.lbo.gostopscreenconfig;

public class ScreenConfig {
    public   int mScreenWidth;
    public   int mScreenHeight;

    public   int mVirtualWidth;
    public   int mVirtualHeight;

    public ScreenConfig(int screenWidth , int screenHeight, int virtualWidth, int virtualHeight) {
        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;
        mVirtualWidth = virtualWidth;
        mVirtualHeight = virtualHeight;
    }
    public int getX(int x) {
        return (int)( x * mScreenWidth/mVirtualWidth);
    }
    public int getY(int y)
    {
        return (int)( y * mScreenHeight/mVirtualHeight);
    }
}