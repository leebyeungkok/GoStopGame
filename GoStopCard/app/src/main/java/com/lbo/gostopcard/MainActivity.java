package com.lbo.gostopcard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    MainView mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
        mainView = new MainView(this);
        setContentView(mainView);
        mainView.init(ScreenWidth, ScreenHeight, this);
    }
}