package com.lbo.gostopgame;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class MainView extends SurfaceView implements SurfaceHolder.Callback{

    private static int SCREEN_WIDTH = 2000;		// 가로폭
    private static int SCREEN_HEIGHT = 1000;	// 세로폭

    public static final int MY_TURN     = 1;	// 내차례
    public static final int YOUR_TURN   = 2;	// 컴퓨터 차례

    final static int BOMB_NUM 		= 100;	// 폭탄선택
    final static int GO_NUM 		= 101;	// 고 선택
    final static int STOP_NUM 		= 102;	// 스톱 선택
    final static int CONTINUE_NUM 	= 103;	// 계속 선택
    final static int END_NUM 		= 104;	// 종료 선택

    final static int H_SENDCARDCHOICE 	= 1;	// 카드선택(손에서 낼때)
    final static int H_OPENCARDCHOICE 	= 2;	// 카드선택 (가운데 카드 선택)
    final static int H_BOMBCARDCHOICE 	= 3;	// 폭탄선택
    final static int H_GOSTOPCHOICE 	= 4;	// 고와 스톱 선택
    final static int H_CALCCHOICE 		= 5;	// 계산모드
    final static int H_OPENCARDMODE 	= 6;	// 카드 뒤집기
    final static int H_DEVIDE 			= 7;	// 카드분배

    boolean mSelectPopSendCardChoiceMode 	= false;	// 내야할카드 선택모드
    boolean mSelectPopOpenCardChoiceMode 	= false;	// 오픈한 카드 선택모드
    boolean mSelectPopBombCardChoiceMode 	= false;	// 폭탄 카드 선택모드
    boolean mSelectPopBombNoCardChoiceMode 	= false; 	// 폭탄 또는 빈카드 선택모드
    boolean mSelectPopCalcChoiceMode 		= false;	// 국화 쌍피 10점, 쌍피 선택모드
    boolean mSelectPopGoStopChoiceMode 		= false;	// Go,Stop 선택모드

    int mTakeAwayCount 		= 0; // 상대카드를 따낼 카드수
    int mSendCardNumOn 		= 0; // 낸카드
    int mSendCardNum 		= 0; // 낸 카드
    int mOpenCardNumFromCenter = 0; // 오픈한카드
    int mFieldCardExistNumOn = 0;	//
    int mFieldCardExistNum 	= 0; // 필드에 같은 월이 있는 12곳중 한곳
    boolean mIsFieldCardExist = false;

    private MainActivity mMainActivity;
    private MainThread mMainThread;
    private Handler mHandler;
    private Context mMainContext;
    private boolean mDrawCls = false;

    private ScreenConfig mScreenConfig;


    Card[] mCard;
    // 가운데 카드
    public int mCardTotCnt = 48;
    public int mCenterCardSize = 48;
    public int[] mCenterCardList = new int[48 + 1];
    // 손안에 들어갈 카드 10장 (내것과 컴퓨터것)
    public int mInMyHandCardSize = 10;
    public int[] mInMyHandCardList = new int[10 + 1];
    public int mInYourHandCardSize = 10;
    public int[] mInYourHandCardList = new int[10 + 1];
    // 따낸 카드 목록
    public int mInMyGet20CardSize = 5;
    public int[] mInMyGet20CardList = new int[5+1];
    public int mInYourGet20CardSize = 5;
    public int[] mInYourGet20CardList = new int[5+1];
    public int mInMyGet10CardSize = 10;
    public int[] mInMyGet10CardList = new int[10+1];
    public int mInYourGet10CardSize = 10;
    public int[] mInYourGet10CardList = new int[10+1];
    public int mInMyGet5CardSize = 10;
    public int[] mInMyGet5CardList = new int[10+1];
    public int mInYourGet5CardSize = 10;
    public int[] mInYourGet5CardList = new int[10+1];
    public int mInMyGet1CardSize = 28;
    public int[] mInMyGet1CardList = new int[28+1];
    public int mInYourGet1CardSize = 28;
    public int[] mInYourGet1CardList = new int[28+1];
    public int[] mFieldCardDetailSize = new int[12+1];
    public int[][] mFieldCardListDetail = new int[12+1][7+1];

    // 폭탄 패널
    Panel mSelectBombPan;
    Card[] mSelectBombCard = new Card[4 + 1];
    // 고스톱 패널
    Panel mSelectGoStopPan;
    Card[] mSelectGoStopCard = new Card[2 + 1];
    // 낼카드 선택 패널
    Panel mSelectSendPan;
    Card[] mSelectSendCard = new Card[7 + 1];
    // 계속, 중지 선택 패널
    Panel mSelectContinuePan;
    Card[] mSelectCalcCard = new Card[2 + 1];

    // 빈카드
    int mMyBlankCardSize = 0;
    int mYourBlankCardSize = 0;
    Card[] mMyBlankCard = new Card[10+1];
    Card[] mYourBlankCard = new Card[10+1];

    // 따낼카드
    public int mGetCardTagetSize = 0;
    public int[] mGetCardTagetList = new int[20+1];
    int mTurnCls = MY_TURN;
    int mWinner 			= 0;	// 승리자

    long mMyMaxScore 	= 0;
    long mMyMoneySum 	= 10000;
    int mMyTotScore 	= 0;
    int mMy20Score 		= 0;
    int mMy10Score 		= 0;
    int mMy5Score 		= 0;
    int mMy1Score 		= 0;
    long mYourMaxScore 	= 0;
    long mYourMoneySum 	= 10000;
    int mYourTotScore 	= 0;
    int mYour20Score 	= 0;
    int mYour10Score 	= 0;
    int mYour5Score 	= 0;
    int mYour1Score 	= 0;
    // 변수설정
    int mMyHongdan 		= 0;
    int mMyChungdan 	= 0;
    int mMyTidan 		= 0;
    int mMyGodori 		= 0;
    int mMyGoCount 		= 0;
    int mYourHongdan 	= 0;
    int mYourChungdan 	= 0;
    int mYourTidan 	= 0;
    int mYourGodori 	= 0;
    int mYourHongdanCount = 0;
    int mYourChungdanCount = 0;
    int mYourTidanCount = 0;
    int mYourGodoriCount = 0;
    int mYourGoCount 	= 0;
    int mYour20Count 	= 0;
    int mYour5Count 	= 0;
    int mYour1Count 	= 0;

    int mChoiceNum 	= 0;
    // 컴퓨터 연산을 위한 멤버변수
    final static int SEND_YES 	= 1;
    final static int SEND_NO 	= 0;
    final static int CALC_GODORI_F 	= 1;
    final static int CALC_GODORI_H 	= 2;
    final static int CALC_HONGDAN_F = 3;
    final static int CALC_HONGDAN_H = 4;
    final static int CALC_TIDAN_F 	= 5;
    final static int CALC_TIDAN_H 	= 6;
    final static int CALC_CHUNGDAN_F = 7;
    final static int CALC_CHUNGDAN_H = 8;
    final static int CALC_5_GWANG_F = 9;
    final static int CALC_5_GWANG_H = 10;
    final static int CALC_3_GWANG_F = 11;
    final static int CALC_3_GWANG_H = 12;
    final static int CALC_GWANG_F 	= 13;
    final static int CALC_GWANG_H 	= 14;
    final static int SCORE_ALERT 	= 2;

    public MainView(Context context){
        super(context);
        getHolder().addCallback(this);
        mMainThread = new MainThread(getHolder(),this);
        setFocusable(true);
        mMainContext = context;
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case H_OPENCARDMODE:			// 가운데 카드를 연다.
                        openCardToField();
                        break;
                    case H_SENDCARDCHOICE:			// 낼 카드를 선택한다.
                        choiceSendCardToField();
                        break;
                    case H_OPENCARDCHOICE:			// 따낼 카드를 선택한다.
                        choiceOpenCard();
                        break;
                    case H_BOMBCARDCHOICE:			// 폭탄카드를 선택한다.
                        choiceBombCard();
                        break;
                    case H_GOSTOPCHOICE:			// 고스톱을 선택한다.
                        choiceGoStop();
                        break;
                    case H_CALCCHOICE:				// 연산후 계속, 멈춤을 선택한다.
                        choiceContinue();
                        break;
                    case H_DEVIDE:					// 카드를 분배한다.
                        shuffleCard();
                        break;
                }
            }
        };
    }
    public void controlChoice(int x,int y){
        if(mSelectPopBombNoCardChoiceMode == true){
            if(mSelectBombCard[1].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[1].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[2].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[2].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[3].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[3].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[4].isSelected((int)x, (int)y)){
                mChoiceNum = BOMB_NUM;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }
            disableSelectMode();
        }else if(mSelectPopBombCardChoiceMode == true){
            if(mSelectBombCard[1].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[1].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[2].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[2].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[3].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectBombCard[3].mCardNum;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }else if(mSelectBombCard[4].isSelected((int)x, (int)y)){
                mChoiceNum = BOMB_NUM;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_BOMBCARDCHOICE));
            }
            disableSelectMode();
        }else if(mSelectPopGoStopChoiceMode == true){
            if(mSelectGoStopCard[1].isSelected((int)x, (int)y)){
                mChoiceNum = GO_NUM;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_GOSTOPCHOICE));
            }else if(mSelectGoStopCard[2].isSelected((int)x, (int)y)){
                mChoiceNum = STOP_NUM;
                mHandler.sendMessage(
                        Message.obtain(mHandler, H_GOSTOPCHOICE));
            }
            disableSelectMode();
        }else if(mSelectPopCalcChoiceMode == true){
            if(mSelectCalcCard[1].isSelected((int)x, (int)y)){
                mChoiceNum = CONTINUE_NUM;
            }else if(mSelectCalcCard[2].isSelected((int)x, (int)y)){
                mChoiceNum = END_NUM;
            }
            mHandler.sendMessage(
                    Message.obtain(mHandler, H_CALCCHOICE));
            disableSelectMode();
        }else if(mSelectPopSendCardChoiceMode == true ||
                mSelectPopOpenCardChoiceMode == true){
            if(mSelectSendCard[1].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectSendCard[1].mCardNum;
                if(mSelectPopSendCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_SENDCARDCHOICE));
                }else if(mSelectPopOpenCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_OPENCARDCHOICE));
                }else if(mSelectPopBombCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_BOMBCARDCHOICE));
                }else if(mSelectPopGoStopChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_GOSTOPCHOICE));
                }else if(mSelectPopCalcChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_CALCCHOICE));
                }
            }else if(mSelectSendCard[2].isSelected((int)x, (int)y)){
                mChoiceNum = mSelectSendCard[2].mCardNum;
                if(mSelectPopSendCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_SENDCARDCHOICE));
                }else if(mSelectPopOpenCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_OPENCARDCHOICE));
                }else if(mSelectPopBombCardChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_BOMBCARDCHOICE));
                }else if(mSelectPopGoStopChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_GOSTOPCHOICE));
                }else if(mSelectPopCalcChoiceMode==true){
                    mHandler.sendMessage(
                            Message.obtain(mHandler, H_CALCCHOICE));
                }
            }
            disableSelectMode();
        }
    }
    public void init(int width, int height, MainActivity mMainActivity) {
        synchronized(mMainThread.getSurfaceHolder()) {
            this.mMainActivity = mMainActivity;
            mScreenConfig = new ScreenConfig(width, height, SCREEN_WIDTH, SCREEN_HEIGHT);

            CardInfo.init();
			/*
			for(int i=0; i<=48; i++){
				Bitmap bitmaporg = BitmapFactory.decodeResource(getResources(), CardInfo.mCard[i][CardInfo.C_PIC]);
				mBitmap[i]= Bitmap.createScaledBitmap(bitmaporg, mScreenConfig.mCardWidth, mScreenConfig.mCardHeight, false);
			}
			 */
            mCard = new Card[48 + 1];
            for (int i = 0; i <= 48; i++) {
                mCard[i] = new Card(i,
                        mScreenConfig.mCardWidth,
                        mScreenConfig.mCardHeight,
                        mScreenConfig.mCardBigWidth,
                        mScreenConfig.mCardBigHeight,
                        getResources(),
                        CardInfo.mCard[i][CardInfo.C_PIC],
                        CardInfo.mCard[0][CardInfo.C_PIC]);
            }

            for (int i = 0; i <= 10; i++) {
                mMyBlankCard[i] = new Card(i,
                        mScreenConfig.mCardWidth,
                        mScreenConfig.mCardHeight,
                        mScreenConfig.mCardBigWidth,
                        mScreenConfig.mCardBigHeight,
                        getResources(),
                        R.drawable.c00_1,
                        CardInfo.mCard[0][0]);
                mYourBlankCard[i] = new Card(i,
                        mScreenConfig.mCardWidth,
                        mScreenConfig.mCardHeight,
                        mScreenConfig.mCardBigWidth,
                        mScreenConfig.mCardBigHeight,
                        getResources(),
                        R.drawable.c00_1,
                        CardInfo.mCard[0][0]);
            }
            // 패널 초기화
            mSelectSendPan = new Panel(0,
                    (int) (mScreenConfig.mScreenWidth * 0.8),
                    (int)(mScreenConfig.mScreenHeight * 0.6),
                    getResources(),
                    R.drawable.select_pan);
            mSelectSendPan.move(
                    (int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 4));
            for (int i = 0; i <= 2; i++) {
                mSelectSendCard[i] = new Card(0,
                        mScreenConfig.mCardWidth,
                        mScreenConfig.mCardHeight,
                        mScreenConfig.mCardBigWidth,
                        mScreenConfig.mCardBigHeight, getResources(),
                        CardInfo.mCard[0][0],
                        CardInfo.mCard[0][0]);
            }

            // 폭탄패널
            mSelectBombPan = new Panel(0,
                    (int) (mScreenConfig.mScreenWidth * 0.8),
                    (int)(mScreenConfig.mScreenHeight * 0.6),
                    getResources(),
                    R.drawable.select_pan);
            mSelectBombPan.move(
                    (int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 4));
            for (int i = 0; i <= 3; i++) {
                mSelectBombCard[i] = new Card(0,
                        mScreenConfig.mCardWidth,
                        mScreenConfig.mCardHeight,
                        mScreenConfig.mCardBigWidth,
                        mScreenConfig.mCardBigHeight,
                        getResources(),
                        CardInfo.mCard[0][0],
                        CardInfo.mCard[0][0]);
            }
            mSelectBombCard[4] = new Card(0,
                    mScreenConfig.mCardWidth,
                    mScreenConfig.mCardHeight,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    CardInfo.mCard[0][0], CardInfo.mCard[0][0]);
            // 고스톱패널
            mSelectGoStopPan = new Panel(0,
                    (int) (mScreenConfig.mScreenWidth * 0.8),
                    (int) (mScreenConfig.mScreenHeight * 0.6),
                    getResources(),
                    R.drawable.select_pan);
            mSelectGoStopPan.move(
                    (int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 4));
            mSelectGoStopCard[1] = new Card(0,
                    mScreenConfig.mCardWidth * 2,
                    mScreenConfig.mCardHeight/2,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    CardInfo.mCard[0][0],
                    CardInfo.mCard[0][0]);
            mSelectGoStopCard[2] = new Card(0,
                    mScreenConfig.mCardWidth * 2,
                    mScreenConfig.mCardHeight/2,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    CardInfo.mCard[0][0],
                    CardInfo.mCard[0][0]);
            // 계속 중지 모드
            mSelectContinuePan = new Panel(0,
                    (int) (mScreenConfig.mScreenWidth * 0.8),
                    (int)(mScreenConfig.mScreenHeight * 0.6),
                    getResources(),
                    R.drawable.select_pan);
            mSelectContinuePan.move(
                    (int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 4));
            mSelectCalcCard[1] = new Card(0,
                    mScreenConfig.mCardWidth *2,
                    mScreenConfig.mCardHeight/2,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    R.drawable.continue_button,
                    CardInfo.mCard[0][0]);
            mSelectCalcCard[2] = new Card(0,
                    mScreenConfig.mCardWidth *2,
                    mScreenConfig.mCardHeight/2,
                    mScreenConfig.mCardBigWidth,
                    mScreenConfig.mCardBigHeight,
                    getResources(),
                    R.drawable.end_button,
                    CardInfo.mCard[0][0]);
        }
        raiseEventForDevide();
        // mDrawCls = true; 이동
    }
    public void think(){
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
        onDrawPanel(canvas);
    }
    public void onDrawPanel(Canvas canvas) {
        Paint scorePaint = new Paint();
        scorePaint.setTextSize(40);
        scorePaint.setColor(Color.WHITE);
        canvas.drawText("MY SCORE : " + mMyTotScore,
                mScreenConfig.mMySignJumsuX,
                mScreenConfig.mMySignJumsuY, scorePaint);
        canvas.drawText("MY MONEY : " + mMyMoneySum,
                mScreenConfig.mMySignJumsuX,
                mScreenConfig.mMySignJumsuY + 40, scorePaint);
        canvas.drawText("YOUR SCORE : " + mYourTotScore,
                mScreenConfig.mYourSignJumsuX,
                mScreenConfig.mYourSignJumsuY, scorePaint);
        canvas.drawText("YOUR MONEY : " + mYourMoneySum,
                mScreenConfig.mYourSignJumsuX,
                mScreenConfig.mYourSignJumsuY + 40, scorePaint);
        // 낼카드 선택모드
        if(mSelectPopSendCardChoiceMode == true){
            mSelectSendPan.draw(canvas);
            for(int i=1; i<=2;i++){
                mSelectSendCard[i].drawBig(canvas);
            }
        }
        // 따낼카드 선택모드
        else if(mSelectPopOpenCardChoiceMode == true){
            mSelectSendPan.draw(canvas);
            for(int i=1; i<= 2;i++){
                mSelectSendCard[i].drawBig(canvas);
            }
        }
        // 폭탄, 빈카드 선택모드
        else if(mSelectPopBombNoCardChoiceMode == true){
            mSelectBombPan.draw(canvas);
            for(int i=1; i<=3;i++){
                mSelectBombCard[i].drawBig(canvas);
            }
        }
        // 폭탄 선택모드
        else if(mSelectPopBombCardChoiceMode == true){
            mSelectBombPan.draw(canvas);
            for(int i=1; i<=4;i++){
                mSelectBombCard[i].drawBig(canvas);
            }
        }
        // 고스톱 선택모드
        else if(mSelectPopGoStopChoiceMode == true){
            mSelectGoStopPan.draw(canvas);
            if (mCenterCardSize >= 1) {
                mSelectGoStopCard[1].draw(canvas);
            }
            mSelectGoStopCard[2].draw(canvas);
        }
        // 게임의 계속, 중지 선택창 보여주기
        else if(mSelectPopCalcChoiceMode == true){
            mSelectContinuePan.draw(canvas);
            Paint backPaint = new Paint();
            backPaint.setColor(Color.WHITE);
            int x1,y1,x2,y2;
            x1 = 1;
            x2 = (int)(mScreenConfig.mScreenWidth);
            y1 = 1;
            y2 = (int)(mScreenConfig.mScreenWidth);
            canvas.drawRect ( x1, y1, x2 ,y2 , backPaint);
            backPaint.setColor(Color.rgb(0,0,0));
            if(mWinner == MY_TURN){
                backPaint.setTextSize(40);
                backPaint.setColor(Color.BLACK);
                canvas.drawText("총점수" + mMyTotScore,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.1),
                        backPaint);
                canvas.drawText("20점" + mMy20Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.2),
                        backPaint);
                canvas.drawText("10점" + mMy10Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.3),
                        backPaint);
                canvas.drawText("5점" + mMy5Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1+ (int)(mScreenConfig.mScreenHeight*0.4),
                        backPaint);
                canvas.drawText("피" + mMy1Score,
                        x1+mScreenConfig.mCardGabX ,
                        y1+ (int)(mScreenConfig.mScreenHeight*0.5),
                        backPaint);
                String strTempYack = "";
                if(mMyGodori >=5)
                    strTempYack = strTempYack +  " 고도리 ";
                if(mMyChungdan >=3)
                    strTempYack = strTempYack +  " 청단 ";
                if(mMyHongdan >=3)
                    strTempYack = strTempYack +  " 홍단 ";
                if(mMyTidan >=3)
                    strTempYack = strTempYack +  " 띠단 ";
                canvas.drawText(strTempYack,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.6),
                        backPaint);
                String strTempDouble = "";
                if(mMyGoCount >=1 && mMyGoCount <=2){
                    strTempDouble =
                            strTempDouble + " " +  mMyGoCount + "고X1 ";
                }
                if(mMyGoCount >=3){
                    strTempDouble =
                            strTempDouble + " " +  mMyGoCount + "고X2 ";
                }
                canvas.drawText(strTempDouble,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.7),
                        backPaint);
            }else if(mWinner == YOUR_TURN){
                // 컴퓨터승
                backPaint.setTextSize(40);
                backPaint.setColor(Color.BLACK);
                canvas.drawText("총점수" + mYourTotScore,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.1),
                        backPaint);
                canvas.drawText("20점" + mYour20Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.2),
                        backPaint);
                canvas.drawText("10점" + mYour10Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.3),
                        backPaint);
                canvas.drawText("5점" + mYour5Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.4),
                        backPaint);
                canvas.drawText("피" + mYour1Score,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.5),
                        backPaint);
                String strTempYack = "";
                if(mYourGodori >=5)
                    strTempYack = strTempYack +  " 고도리 ";
                if(mYourChungdan >=3)
                    strTempYack = strTempYack +  " 청단 ";
                if(mYourHongdan >=3)
                    strTempYack = strTempYack +  " 홍단 ";
                if(mYourTidan >=3)
                    strTempYack = strTempYack +  " 띠단 ";
                canvas.drawText(strTempYack,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.6),
                        backPaint);
                String strTempDouble = "";
                if(mYourGoCount >=1 && mYourGoCount <=2)
                    strTempDouble = strTempDouble + " " +  mYourGoCount + "고X1 ";
                if(mYourGoCount >=3)
                    strTempDouble = strTempDouble + " " +  mYourGoCount + "고X2 ";
                canvas.drawText(strTempDouble,
                        x1 + mScreenConfig.mCardGabX ,
                        y1 + (int)(mScreenConfig.mScreenHeight*0.7),
                        backPaint);
            }
            for(int i=1; i<=2;i++){
                mSelectCalcCard[i].draw(canvas);
            }
        }
    }
    public void onDrawInMyHand(Canvas canvas) {
        for (int i = 1; i <= mInMyHandCardSize; i++) {
            if(mInMyHandCardList[i] != 0)
                mCard[mInMyHandCardList[i]].drawBig(canvas);
        }
        for (int i = 1; i <= mMyBlankCardSize; i++) {
            mMyBlankCard[i].drawBig(canvas);
        }
    }
    // 컴퓨터가들고있는카드
    public void onDrawInYourHand(Canvas canvas) {
        for (int i = 1; i <= mInYourHandCardSize; i++) {
            if(mInYourHandCardList[i] != 0)
                mCard[mInYourHandCardList[i]].drawBigClose(canvas);
        }
    }
    // 바닥에 깔린 카드
    public void onDrawField(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            for (int j = 1; j <= mFieldCardDetailSize[i]; j++) {
                if(mFieldCardListDetail[i][j] !=0)
                    mCard[mFieldCardListDetail[i][j]].draw(canvas);
            }
        }
    }
    // 가운데 카드
    public void onDrawCenter(Canvas canvas) {
        for (int i = 1; i <= mCenterCardSize; i++) {
            if(mCenterCardList[i] !=0)
                mCard[mCenterCardList[i]].drawClose(canvas);
        }
    }
    // 내가 따낸 20점 짜리 카드
    public void onDrawInMyGet20(Canvas canvas) {
        for (int i = 1; i <= mInMyGet20CardSize; i++) {
            if(mInMyGet20CardList[i] !=0)
                mCard[mInMyGet20CardList[i]].draw(canvas);
        }
    }
    // 내가 따낸 10점 짜리 카드
    public void onDrawInMyGet10(Canvas canvas) {
        for (int i = 1; i <= mInMyGet10CardSize; i++) {
            if(mInMyGet10CardList[i] !=0)
                mCard[mInMyGet10CardList[i]].draw(canvas);
        }
    }
    // 내가 따낸 5점 짜리 카드
    public void onDrawInMyGet05(Canvas canvas) {
        for (int i = 1; i <= mInMyGet5CardSize; i++) {
            if(mInMyGet5CardList[i] !=0)
                mCard[mInMyGet5CardList[i]].draw(canvas);
        }
    }
    // 내가 따낸 1점 짜리 카드
    public void onDrawInMyGet01(Canvas canvas) {
        for (int i = 1; i <= mInMyGet1CardSize; i++) {
            if(mInMyGet1CardList[i] !=0)
                mCard[mInMyGet1CardList[i]].draw(canvas);
        }
    }
    // 컴퓨터가 따낸 20점 짜리 카드
    public void onDrawInYourGet20(Canvas canvas) {
        for (int i = 1; i <= mInYourGet20CardSize; i++) {
            if(mInYourGet20CardList[i] !=0)
                mCard[mInYourGet20CardList[i]].draw(canvas);
        }
    }
    // 컴퓨터가 따낸 10점 짜리 카드
    public void onDrawInYourGet10(Canvas canvas) {
        for (int i = 1; i <= mInYourGet10CardSize; i++) {
            if(mInYourGet10CardList[i] !=0)
                mCard[mInYourGet10CardList[i]].draw(canvas);
        }
    }
    // 컴퓨터가 따낸 5점 짜리 카드
    public void onDrawInYourGet05(Canvas canvas) {
        for (int i = 1; i <= mInYourGet5CardSize; i++) {
            if(mInYourGet5CardList[i] !=0)
                mCard[mInYourGet5CardList[i]].draw(canvas);
        }
    }
    // 컴퓨터가 따낸 1점 짜리 카드
    public void onDrawInYourGet01(Canvas canvas) {
        for (int i = 1; i <= mInYourGet1CardSize; i++) {
            if(mInYourGet1CardList[i] !=0)
                mCard[mInYourGet1CardList[i]].draw(canvas);
        }
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
    public void shuffleCard(){
        synchronized (mMainThread.getSurfaceHolder()) {
            disableSelectMode();

            mMyHongdan 		= 0;
            mMyChungdan 	= 0;
            mMyTidan 		= 0;
            mMyGodori 		= 0;
            mMyGoCount 		= 0;

            mYourHongdan 	= 0;
            mYourChungdan 	= 0;
            mYourTidan 	= 0;
            mYourGodori 		= 0;
            mYourHongdanCount 	= 0;
            mYourChungdanCount 	= 0;
            mYourTidanCount 	= 0;
            mYourGodoriCount 	= 0;
            mYourGoCount 		= 0;
            mCenterCardSize 	= 48;
            mInYourHandCardSize = 0;
            mInMyHandCardSize 	= 0;
            mInMyGet20CardSize 	= 0;
            mInMyGet10CardSize 	= 0;
            mInMyGet5CardSize 	= 0;
            mInMyGet1CardSize 	= 0;
            mInYourGet20CardSize = 0;
            mInYourGet10CardSize = 0;
            mInYourGet5CardSize = 0;
            mInYourGet1CardSize = 0;

            mMyBlankCardSize = 0;
            mYourBlankCardSize = 0;

            //가운데카드를 초기화한다.
            for (int i = 0; i <= mCardTotCnt; i++) {
                mCenterCardList[i] = 0;
            }
            // 따낸 1점짜리 카드목록을 초기화한다.
            for (int i = 0; i <= 28; i++) {
                mInYourGet1CardList[i] = 0;
                mInMyGet1CardList[i] = 0;
            }
            // 내손, 컴퓨터에 있는 카드와 10점, 5점짜리 카드를 초기화 한다.
            for (int i = 0; i <= 10; i++) {
                mInMyHandCardList[i] = 0;
                mInYourHandCardList[i] = 0;
                mInMyGet10CardList[i] = 0;
                mInYourGet10CardList[i] = 0;
                mInMyGet5CardList[i] = 0;
                mInYourGet5CardList[i] = 0;
            }
            // 20점짜리 카드를 초기화한다.
            for (int i = 0; i <= 5; i++) {
                mInMyGet20CardList[i] = 0;
                mInYourGet20CardList[i] = 0;
            }
            // 바닥의 카드를 초기화 한다.
            for (int i = 0; i <= 12; i++) {
                mFieldCardDetailSize[i] = 0;
                for (int j = 0; j <= 7; j++) {
                    mFieldCardListDetail[i][j] = 0;
                }
            }
            // 카드를 랜덤하게 부여한다.
            double[][] tempArray = new double[48 + 1][2];
            for (int i = 1; i <= 48; i++) {
                tempArray[i][0] = i;
                tempArray[i][1] = Math.random();
            }
            // 랜덤순서에 맞게 카드를 지정함
            for (int i = 1; i <= 48-1; i++) {
                for (int j = i+1; j <= 48; j++) {
                    if (tempArray[j][1] > tempArray[i][1]) {
                        double temp1 = tempArray[i][0];
                        double temp2 = tempArray[i][1];
                        tempArray[i][0] = tempArray[j][0];
                        tempArray[i][1] = tempArray[j][1];
                        tempArray[j][0] = temp1;
                        tempArray[j][1] = temp2;
                    }
                }
            }
            for (int i = 1; i <= 48; i++) {
                mCenterCardList[i] = (int) (tempArray[i][0]);
                mCard[mCenterCardList[i]].move(mScreenConfig.mCenterX,mScreenConfig.mCenterY);
            }
            // 첫번째 카드를  밖으로 보낸다.(안보이게 처리한다. 미사용)
            mCard[0].move(-1000,-1000);
            mDrawCls = true;
        }
        distributeCard();
    }
    public void raiseEventForDevide(){
        mHandler.sendMessageDelayed(Message.obtain(mHandler, H_DEVIDE), 3000);
    }
    // 카드를 배분한다.
    public void distributeCard() {
        // 가운데 있는 카드를 컴퓨터에게 보낸다.
        for (int i =1; i <= 10; i++) {
            moveFromCenterToYourHand();
        }
        // 가운데 있는 카드를 나에게 보낸다.
        for (int i=1; i <= 10; i++) {
            moveFromCenterToMyHand();
        }
        //가운데 있는 카드를 바닥에 깐다.
        for (int i=1; i <= 8; i++) {
            moveFromCenterToField();
        }
        // 컴퓨터 카드를 정렬한다.
        moveYourCardForOrder();
        // 내카드를 정렬한다.
        moveMyCardForOrder();
        // 내턴이면
        if (mTurnCls == MY_TURN) {
            moveFieldCardMyTurnForOrder();
        } else if (mTurnCls == YOUR_TURN) {
            moveFieldCardYourTurnForOrder();
        }
        if(mWinner == MY_TURN){
            mTurnCls = MY_TURN;
        } else if(mWinner == YOUR_TURN){
            mTurnCls = YOUR_TURN;
            comBrain();
        }
    }
    // 카드를 가운데로 이동시킨다.
    public void moveCenter(int cardNum) {
        mCard[cardNum].move(mScreenConfig.mCenterX,
                mScreenConfig.mCenterY);
    }
    // 가운데 카드를 컴퓨터 카드로 이동시킨다.
    public void moveFromCenterToYourHand() {
        int cardNum = mCenterCardList[mCenterCardSize];
        mCenterCardSize--;
        mInYourHandCardSize++;
        mInYourHandCardList[mInYourHandCardSize] = cardNum;
        mCard[cardNum].moveTo(
                mScreenConfig.mInYourHandX
                        + (mScreenConfig.mCardBigWidth * (mInYourHandCardSize -1)),
                mScreenConfig.mInYourHandY);
    }
    // 가운데 카드를 내 손안의 카드로 이동시킨다.
    public void moveFromCenterToMyHand() {
        int cardNum = mCenterCardList[mCenterCardSize];
        mCenterCardSize--;
        mInMyHandCardSize++;
        mInMyHandCardList[mInMyHandCardSize] = cardNum;
        mCard[cardNum].moveTo(mScreenConfig.mInMyHandX
                        + (mScreenConfig.mCardBigWidth * (mInMyHandCardSize -1)),
                mScreenConfig.mInMyHandY);
    }
    // 가운데 카드를 바닥 필드로 이동시킨다.
    public void moveFromCenterToField() {
        int cardNum = mCenterCardList[mCenterCardSize];
        mCenterCardSize--;

        for (int j = 1; j <= 12; j++) {
            if (mFieldCardDetailSize[j] == 0){ // 바닥 12곳에 이동시킨다.
                mCard[cardNum].moveTo(
                        mScreenConfig.mFieldDetailX[j],
                        mScreenConfig.mFieldDetailY[j]);
                mFieldCardListDetail[j][1] = cardNum;
                mFieldCardDetailSize[j]++;
                break;
            }
        }
    }

    // 컴퓨터 카드를 정렬한다.
    public void moveYourCardForOrder() {
        for (int i = 1; i <= 10-1; i++) {
            for (int j = i + 1; j <= 10; j++) {
                if (mInYourHandCardList[j] < mInYourHandCardList[i]) {
                    int mCardNumTemp = mInYourHandCardList[j];
                    mInYourHandCardList[j] = mInYourHandCardList[i];
                    mInYourHandCardList[i] = mCardNumTemp;

                }
            }
        }
        for (int i =1; i <= 10; i++) {
            mCard[mInYourHandCardList[i]].move(mScreenConfig.mInYourHandX
                            + (mScreenConfig.mCardBigWidth * (i-1)),
                    mScreenConfig.mInYourHandY);
        }
    }
    // 내손안의 카드를 정열한다.
    public void moveMyCardForOrder() {
        for (int i = 1; i <= 10 - 1; i++) {
            for (int j = i + 1; j <= 10; j++) {
                if (mInMyHandCardList[j] < mInMyHandCardList[i]) {
                    int mCardNumTemp = mInMyHandCardList[j];
                    mInMyHandCardList[j] = mInMyHandCardList[i];
                    mInMyHandCardList[i] = mCardNumTemp;

                }
            }
        }

        for (int i = 1; i <= 10; i++) {
            mCard[mInMyHandCardList[i]].moveTo(mScreenConfig.mInMyHandX
                            + (mScreenConfig.mCardBigWidth * (i-1)),
                    mScreenConfig.mInMyHandY);
        }

    }

    // 내카드를 내고 바닥에 카드를 정렬한다.
    public void moveFieldCardMyTurnForOrder() {
        for (int i = 1; i <= 12 - 1; i++) {
            // 바닥에 카드가 있을경우
            if (mFieldCardDetailSize[i] > 0) {
                int tempFieldCardListDetail_i =
                        mFieldCardListDetail[i][mFieldCardDetailSize[i]];
                for (int j = i + 1; j <= 12; j++) {
                    if (mFieldCardDetailSize[j] > 0) {
                        int tempFieldCardListDetail_j =
                                mFieldCardListDetail[j][mFieldCardDetailSize[j]];
                        if (CardInfo.mCard[tempFieldCardListDetail_i][CardInfo.C_MONTH] ==
                                CardInfo.mCard[tempFieldCardListDetail_j][CardInfo.C_MONTH]) {
                            mFieldCardDetailSize[i]++;
                            mFieldCardListDetail[i][mFieldCardDetailSize[i]] =
                                    mFieldCardListDetail[j][mFieldCardDetailSize[j]];
                            mCard[tempFieldCardListDetail_j].moveTo(
                                    mScreenConfig.mFieldDetailX[i]
                                            + (mFieldCardDetailSize[i] -1)
                                            * mScreenConfig.mCardGabX,
                                    mScreenConfig.mFieldDetailY[i]
                                            + (mFieldCardDetailSize[i] -1)
                                            * mScreenConfig.mCardGabY);
                            mFieldCardListDetail[j][mFieldCardDetailSize[j]] = 0;
                            mFieldCardDetailSize[j]--;
                        }
                    }
                }
            }
        }
    }
    // 컴퓨터 카드를 내고 바닥에 카드를 정렬한다.
    public void moveFieldCardYourTurnForOrder() {
        for (int i = 1; i <= 12 - 1; i++) {
            // 카드가 있을경우
            if (mFieldCardDetailSize[i] > 0) {
                int tempFieldCardListDetail_i =
                        mFieldCardListDetail[i][mFieldCardDetailSize[i]];
                for (int j = i + 1; j <= 12; j++) {
                    // 카드고유번호
                    int tempFieldCardListDetail_j =
                            mFieldCardListDetail[j][mFieldCardDetailSize[j]];
                    // 바닥에 같은 월의 카드가 있다면
                    if (CardInfo.mCard[tempFieldCardListDetail_i][CardInfo.C_MONTH] ==
                            CardInfo.mCard[tempFieldCardListDetail_j][CardInfo.C_MONTH]) {
                        //옮겨오는 곳은 카드를 증가시키고 카드를 이동시킨다.
                        mFieldCardDetailSize[i]++;
                        mFieldCardListDetail[i][mFieldCardDetailSize[i]] =
                                mFieldCardListDetail[j][mFieldCardDetailSize[j]];
                        mCard[tempFieldCardListDetail_j].moveTo(
                                mScreenConfig.mFieldDetailX[i]
                                        + (mFieldCardDetailSize[i]-1)
                                        * mScreenConfig.mCardGabX,
                                mScreenConfig.mFieldDetailY[i]
                                        + (mFieldCardDetailSize[i]-1)
                                        * mScreenConfig.mCardGabY);
                        // 옮겨진쪽의 카드는 갑소된다.
                        mFieldCardListDetail[j][mFieldCardDetailSize[j]] = 0;
                        mFieldCardDetailSize[j]--;
                    }
                }
            }
        }
    }
    // 빈카드 내기
    public void sendBlankCard(int CardNum) {
        mTakeAwayCount = 0; // 따낼카드
        mSelectPopBombNoCardChoiceMode = false;	// 폭탄 빈카드 선택모드
        mGetCardTagetSize = 0;
        for (int i = 0; i <= 20; i++) {
            mGetCardTagetList[i] = 0;
        }
        mIsFieldCardExist = false;
        if (mTurnCls == MY_TURN) {
            mMyBlankCardSize--;
            arrangeInHandCard();
            mSendCardNum = 0;
            mFieldCardExistNum = 0;
            openCard(0, 0);
        } else if (mTurnCls == YOUR_TURN) {
            mYourBlankCardSize--;
            arrangeInHandCard();
            mSendCardNum = 0;
            mFieldCardExistNum = 0;
            openCard(0, 0);
        }
    }

    // 카드를 바닥에 보낸다.
    public void sendCardToField(int cardNum){
        boolean isBomb = false;	// 폭탄여부
        mGetCardTagetSize = 0;	// 따낼 카드의 개수
        mTakeAwayCount = 0; 	// 상대카드 가져올 수
        mSelectPopBombNoCardChoiceMode = false;	// 폭탄카드선택모드

        for (int i = 0; i <= 20; i++) {	// 따낼 카드를 초기화한다.
            mGetCardTagetList[i] = 0;
        }
        mIsFieldCardExist = false;	// 바닥에 동일한 카드가 있는지 여부
        int FieldCardExistNum = 0;	// 바닥에 동일한 카드의 개수
        int SameBombCount = 0;		// 동시 폭탄 개수
        int[] SameBombList = new int[5+1];	// 폭탄리스트 초기화
        for (int i = 0; i <= 5; i++) {
            SameBombList[i] = 0;
        }

        if (mTurnCls == MY_TURN) { // 동일한 카드가 3개가 있다면 폭탄을 선택할수 있다.
            for (int i = 1; i <= mInMyHandCardSize; i++) {
                if (mInMyHandCardList[i] > 0) {
                    if (CardInfo.mCard[mInMyHandCardList[i]][CardInfo.C_MONTH]==
                            CardInfo.mCard[cardNum][CardInfo.C_MONTH]) {
                        SameBombCount++;
                        SameBombList[SameBombCount] = mInMyHandCardList[i];
                        mSelectBombCard[SameBombCount].setImage(
                                mInMyHandCardList[i],
                                CardInfo.getCardRes(mInMyHandCardList[i]));
                    }
                }
            }
            if (SameBombCount == 3) {	// 3장이면 패널을 보여주고 마지막에 폭탄 카드를 선택하도록 보여준다.
                mSelectBombCard[4].setImage(mInMyHandCardList[0],
                        R.drawable.c00_bomb);
            }
        } else if (mTurnCls == YOUR_TURN) {	// 컴퓨터의 경우 폭탄일 경우를 동일하게 체크한다.
            for (int i = 1; i <= mInYourHandCardSize; i++) {
                if (mInYourHandCardList[i] > 0) {
                    if (CardInfo.mCard[mInYourHandCardList[i]][CardInfo.C_MONTH] ==
                            CardInfo.mCard[cardNum][CardInfo.C_MONTH]) {
                        SameBombCount++;
                        SameBombList[SameBombCount] = mInYourHandCardList[i];
                        mSelectBombCard[SameBombCount].setImage(
                                mInYourHandCardList[i],
                                CardInfo.getCardRes(mInYourHandCardList[i]));
                    }
                }
            }
            if (SameBombCount == 3) {
                mSelectBombCard[4].setImage(
                        mInYourHandCardList[0],
                        R.drawable.c00_bomb);
            }
        }
        if (SameBombCount == 3) {	// 폭탄을 체크한다.
            isBomb = true;
        }else if (SameBombCount == 4) {	// 동일 카드가 4장이면 종료한다.
            mWinner = mTurnCls;
            calcMoney();
            return;
        }

        for (int i = 1;i <= 12; i++) {	// 바닥에 동일 패가 있는지 확인한다. 바닥은 12개가 깔릴수 있다.
            if (mFieldCardDetailSize[i] > 0) {	// 12개 장소중 각각의 곳에 카드가 있다면

                int tempFieldCardListDetail_first =
                        mFieldCardListDetail[i][CardInfo.C_KIND]; // 카드의 1, 5, 10, 20자 구분
                int tempFieldValue_i =
                        CardInfo.mCard[tempFieldCardListDetail_first][CardInfo.C_MONTH];	//카드의 해당월
                // 카드가 같은 월이라면
                if (CardInfo.mCard[cardNum][CardInfo.C_MONTH] ==
                        tempFieldValue_i) {
                    // 폭탄은 바닥에 카드가 있는 경우와 없는 경우 모드가 다르다.
                    if (isBomb == true) {
                        mSelectPopBombCardChoiceMode= true;
                        mSelectPopBombNoCardChoiceMode = false;
                        // 따낼 카드 목록에 넣는다.
                        mGetCardTagetSize++;
                        mGetCardTagetList[mGetCardTagetSize] =
                                tempFieldCardListDetail_first;
                        //  폭탄 선택 패널을 보여준다.
                        showBombCardPanel(SameBombList);
                        return;
                    }
                    // 폭탄이 아닐경우 계속 진행한다.
                    // 동일한 카드가 있다.
                    mIsFieldCardExist = true;
                    // 이때의 바닥 필드는 i 번째의 묶음이다.
                    FieldCardExistNum = i;
                    // 화면에서 터치한 카드를 필드에 내려놓는다.
                    mFieldCardDetailSize[i]++;
                    mCard[cardNum].moveTo(
                            mScreenConfig.mFieldDetailX[i]
                                    + (mFieldCardDetailSize[i] -1) * mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i]
                                    + (mFieldCardDetailSize[i] -1) * mScreenConfig.mCardGabY);
                    //  카드를 냈으므로 바닥의 같은 월의 묶음에 바닥 필드의 카드번호를 추가하고 1을 증가시킨다.
                    mFieldCardListDetail[i][mFieldCardDetailSize[i]] =
                            cardNum;
                    // 내손안의 카드나 컴퓨터 손안의 카드를 제거하기위해 0으로 설정한다.
                    for (int k = 1; k <= 10; k++) {
                        if (mTurnCls == MY_TURN) {
                            if (mInMyHandCardList[k] == cardNum) {
                                mInMyHandCardList[k] = 0;

                            }
                        } else if (mTurnCls == YOUR_TURN) {
                            if (mInYourHandCardList[k] == cardNum) {
                                mInYourHandCardList[k] = 0;
                            }
                        }
                    }
                    // 바닥의 카드가 4개 이상일 경우 전부 먹는다.
                    if (mFieldCardDetailSize[i] == 4) {
                        for (int k = 1; k <= 4; k++) {
                            mGetCardTagetSize++;
                            mGetCardTagetList[mGetCardTagetSize] =
                                    mFieldCardListDetail[i][k];
                        }
                    }else if (mFieldCardDetailSize[i] == 3) {
                        // 바닥의 카드가 3개일 경우 선택한다. (낸카드포함)
                        mSelectPopSendCardChoiceMode = true;
                        // 나중을 위해 내가 낸 카드번호를 기억한다.
                        mSendCardNumOn = cardNum;
                        // 가운데 카드 더미에서 뒤집을 카드를 초기화 시킨다.
                        mOpenCardNumFromCenter = 0;
                        mFieldCardExistNumOn = FieldCardExistNum;
                        // 카드를 선택하도록 카드를 보여준다.
                        showSendCardPanel(2, mFieldCardListDetail[i]);
                        return; //종료한다.
                    }else if (mFieldCardDetailSize[i] == 2) {
                        // 낸 카드 포함 2개이므로 먹는다. 단 뻑이 있을 수 있다.
                        mGetCardTagetSize++;
                        mGetCardTagetList[mGetCardTagetSize] =
                                cardNum;
                        mGetCardTagetSize++;
                        mGetCardTagetList[mGetCardTagetSize] =
                                tempFieldCardListDetail_first;
                    } else if (mFieldCardDetailSize[i] == 1) {
                        // 1개일 경우 먹을개 없다.
                    }
                    break;
                }
            }
        }
        // 내가 낸 카드가 바닥에 동일한 패가 없는 경우
        if (mIsFieldCardExist == false) {
            // 폭탄일 경우 폭탄 패널을 보여주고 종료한다.
            if (isBomb == true) {
                mSelectPopBombNoCardChoiceMode = true;
                showBombCardPanel(SameBombList);
                return;
            }
            // 먹을 패가 없을 경우 필드 12곳중 비어있는 필드를 찾아간다.
            for (int i = 1; i <= 12; i++) {
                if (mFieldCardDetailSize[i] == 0) {
                    mFieldCardDetailSize[i]++;
                    mCard[cardNum].moveTo(
                            mScreenConfig.mFieldDetailX[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabX);
                    mFieldCardListDetail[i][1] = cardNum;
                    for (int k = 0; k <= 10; k++) {
                        if (mTurnCls == MY_TURN) {
                            if (mInMyHandCardList[k] == cardNum) {
                                mInMyHandCardList[k] = 0;
                            }
                        } else if (mTurnCls == YOUR_TURN) {
                            if (mInYourHandCardList[k] == cardNum) {
                                mInYourHandCardList[k] = 0;
                            }
                        }
                    }
                    break;
                }
            }
        }
        // 팝업모드가 아닐 경우 계속 진행한다.
        if (mSelectPopBombNoCardChoiceMode == false &&
                mSelectPopBombNoCardChoiceMode == false &&
                mSelectPopSendCardChoiceMode   == false) {
            mSendCardNum = cardNum;
            mFieldCardExistNum = FieldCardExistNum;
            // 가운데 카드 더미에서 카드를 뒤집어 연다.
            openCard(cardNum, FieldCardExistNum);
        }
    }
    // 보낼카드를 선택한다.
    public void choiceSendCardToField() {
        mGetCardTagetSize++;
        mGetCardTagetList[mGetCardTagetSize] = mSendCardNumOn;
        mGetCardTagetSize++;
        mGetCardTagetList[mGetCardTagetSize] = mChoiceNum;
        arrangeInHandCard();
        openCard(mSendCardNumOn, mFieldCardExistNumOn);
        arrangeInHandCard();
    }
    // 폭탄패널에서 선택
    public void choiceBombCard() {
        // 폭탄선택일 경우
        if (mChoiceNum == BOMB_NUM) {
            mTakeAwayCount++;
            if (mTurnCls == MY_TURN) {
                mMyBlankCardSize += 2;

            } else if (mTurnCls == YOUR_TURN) {
                mYourBlankCardSize += 2;
            }
            for (int i = 1; i <= 3; i++) {
                // mFieldCardExistNumOn : 같은 월이 있는 필드 12곳 중 한곳
                int tempx = mScreenConfig.mFieldDetailX[mFieldCardExistNumOn]
                        + (mFieldCardDetailSize[mFieldCardExistNumOn] -1)
                        * mScreenConfig.mCardGabX;
                int tempy = mScreenConfig.mFieldDetailY[mFieldCardExistNumOn]
                        + (mFieldCardDetailSize[mFieldCardExistNumOn] -1)
                        * mScreenConfig.mCardGabY;
                mSelectBombCard[i].moveTo(tempx, tempy);
                mFieldCardDetailSize[mFieldCardExistNumOn]++;
                mFieldCardListDetail[mFieldCardExistNumOn][1] =
                        mSelectBombCard[i].mCardNum;
                mGetCardTagetSize++;
                mGetCardTagetList[mGetCardTagetSize] =
                        mSelectBombCard[i].mCardNum;
                if (mTurnCls == MY_TURN) {
                    for (int k = 1; k <= 10; k++) {
                        if (mInMyHandCardList[k] ==
                                mSelectBombCard[i].mCardNum) {
                            mInMyHandCardList[k] = 0;
                        }
                    }
                } else if (mTurnCls == YOUR_TURN) {
                    for (int k =1; k <= 10; k++) {
                        if (mInYourHandCardList[k] ==
                                mSelectBombCard[i].mCardNum) {
                            mInYourHandCardList[k] = 0;
                        }
                    }
                }
            }
        } else if (mSelectPopBombNoCardChoiceMode == true) {
            mFieldCardExistNumOn = 0;
            for (int i = 1; i <= 12; i++) {
                // �ٴڿ� �´� ī�尡 ����.
                if (mFieldCardDetailSize[i] == 0) {
                    mFieldCardDetailSize[i]++;
                    mCard[mChoiceNum].moveTo(
                            mScreenConfig.mFieldDetailX[i]
                                    + (mFieldCardDetailSize[i] -1)
                                    * mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i]
                                    + (mFieldCardDetailSize[i]-1)
                                    * mScreenConfig.mCardGabX);
                    mFieldCardListDetail[i][mFieldCardDetailSize[i]] = mChoiceNum;
                    for (int k = 1; k <= 10; k++) {
                        if (mTurnCls == MY_TURN) {
                            if (mInMyHandCardList[k] == mChoiceNum) {
                                mInMyHandCardList[k] = 0;
                            }
                        } else if (mTurnCls == YOUR_TURN) {
                            if (mInYourHandCardList[k] == mChoiceNum) {
                                mInYourHandCardList[k] = 0;
                            }
                        }
                    }
                    break;
                }
            }
        } else if (mSelectPopBombNoCardChoiceMode == false) {
            mGetCardTagetSize++;
            mGetCardTagetList[mGetCardTagetSize] = mChoiceNum;
            for (int k = 1; k <= 10; k++) {
                if (mTurnCls == MY_TURN) {
                    if (mInMyHandCardList[k] == mChoiceNum) {
                        mInMyHandCardList[k] = 0;
                    }
                } else if (mTurnCls == YOUR_TURN) {
                    if (mInYourHandCardList[k] == mChoiceNum) {
                        mInYourHandCardList[k] = 0;
                    }
                }
            }
        }
        disableSelectMode();
        arrangeInHandCard();
        mSendCardNum = mSendCardNumOn;
        mFieldCardExistNum = mFieldCardExistNumOn;
        openCard(mSendCardNumOn, mFieldCardExistNumOn);
        arrangeInHandCard();
    }
    // 스톱일 경우 종료한다.
    public void choiceGoStop() {
        if (mChoiceNum == GO_NUM) {
            if (mTurnCls == MY_TURN) {
                mMyGoCount++;
            } else if (mTurnCls == YOUR_TURN) {
                mYourGoCount++;
            }
            chgTurnCls();
        } else if (mChoiceNum == STOP_NUM) {
            mWinner = mTurnCls;
            calcMoney();
        }
    }
    // 계속, 중지 선택
    public void choiceContinue() {
        if (mChoiceNum == CONTINUE_NUM) {
            raiseEventForDevide();
        } else if (mChoiceNum == END_NUM) {
            mMainActivity.finish();
        }
    }
    // 열린카드 선택
    public void choiceOpenCard() {
        disableSelectMode();
        mGetCardTagetSize++;
        mGetCardTagetList[mGetCardTagetSize] = mOpenCardNumFromCenter;
        mGetCardTagetSize++;
        mGetCardTagetList[mGetCardTagetSize] = mChoiceNum;
        takeAway();
        getCard();
        arrangeInHandCard();
        if (mSelectPopCalcChoiceMode != true) {
            chgTurnCls();
        }
    }
    // 가운데 카드 더미에서 연다.
    public void openCard(int cardNum, int fieldCardExistNum) {
        mSendCardNum = cardNum;
        mFieldCardExistNum = fieldCardExistNum;
        mHandler.sendMessage(Message.obtain(mHandler, H_OPENCARDMODE));
    }

    // 가운데카드에서 카드를 꺼낼경우
    public void openCardToField() {
        // mSendCardNum 낸카드의 고유번호
        // mFieldCardExistNum : 바닥 12곳중 동일한 월의 카드가 있는 번호 (1~12)
        int tempCenterCardLastNum =
                mCenterCardList[mCenterCardSize];	// 가운데 카드번호와 월
        int tempCenterCardMonth =
                CardInfo.mCard[tempCenterCardLastNum][CardInfo.C_MONTH];
        mCenterCardSize--;	// 가운데 카드 더미수를 감소시킨다.
        boolean isFieldCardExist = false;
        for (int i = 1; i <= 12; i++) {
            // 카드가 있을 경우
            if (mFieldCardDetailSize[i] > 0) {
                // 카드고유번호 - 첫번째 카드 기준
                int tempFieldCardListDetail_first =
                        mFieldCardListDetail[i][1];
                int tempFieldMonth =
                        CardInfo.mCard[tempFieldCardListDetail_first][CardInfo.C_MONTH];

                // 카드가 같은 월이면
                if (tempCenterCardMonth == tempFieldMonth) {
                    // 카드가 있을을 체크하고 가운데 더미의 카드가 이동한다.
                    isFieldCardExist = true;
                    mFieldCardDetailSize[i]++;
                    mFieldCardListDetail[i][mFieldCardDetailSize[i]] =
                            tempCenterCardLastNum;
                    mCard[tempCenterCardLastNum].moveTo(
                            mScreenConfig.mFieldDetailX[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabY);
                    // 손에서 낸 카드와 동일한 월의 카드라면
                    if (mFieldCardExistNum == i) {
                        // 4장일 경우 따닥이다
                        if (mFieldCardDetailSize[i] == 4) {
                            mTakeAwayCount++;
                            mGetCardTagetSize = 0;
                            for (int k = 1; k <= 20; k++) {
                                mGetCardTagetList[k] = 0;
                            }
                            // 따낼 카드 목록에 모두 추가한다.
                            for (int k = 1; k < mFieldCardDetailSize[i]; k++) {
                                mGetCardTagetSize++;
                                mGetCardTagetList[k] =
                                        mFieldCardListDetail[i][k];
                            }
                        }
                        // 3장일 경우 뻑이다. 따낼 카드를 초기화 시킨다.
                        else if (mFieldCardDetailSize[i] == 3) {
                            mGetCardTagetSize = 0;
                            for (int k = 1; k <= 20; k++) {
                                mGetCardTagetList[k] = 0;
                            }
                        }
                        // 2장일 경우 쪽이다.  ?? 로직 확인
                        else if (mFieldCardDetailSize[i] == 2) {
                            mTakeAwayCount++;
                        }
                    }
                    // 동일한 위치가 아닌 다른 위치로 갔다면
                    else {
                        // 4장일 경우 남이 싼 카드 또는 3장의 카드를 먹는다.
                        if (mFieldCardDetailSize[i] == 4) {
                            mTakeAwayCount++;
                            for (int k = 1; k <= mFieldCardDetailSize[i]; k++) {
                                mGetCardTagetSize++;
                                mGetCardTagetList[mGetCardTagetSize] =
                                        mFieldCardListDetail[i][k];
                            }

                        } else if (mFieldCardDetailSize[i] == 3) {	// 3장일 경우 따낼 카드를 선택한다.
                            mSelectPopOpenCardChoiceMode = true;
                            // 일단 전역변수로 사용
                            mOpenCardNumFromCenter = tempCenterCardLastNum;
                            mSendCardNumOn = 0;
                            mFieldCardExistNumOn = mFieldCardExistNum;
                            // 카드선택 창을 보여준다.
                            showOpenCardPanel(2, mFieldCardListDetail[i]);
                            return;

                        } else if (mFieldCardDetailSize[i]  == 2) {	//2장일 경우 따낸다.
                            mGetCardTagetSize++;
                            mGetCardTagetList[mGetCardTagetSize] =
                                    tempCenterCardLastNum;
                            mGetCardTagetSize++;
                            mGetCardTagetList[mGetCardTagetSize] =
                                    tempFieldCardListDetail_first;
                        }
                    }
                    break;
                }
            }
        }
        // 먹을 것은 없는 경우 필드의 12곳 중 빈곳으로 이동한다.
        if (isFieldCardExist == false) {
            for (int i = 1; i < 12; i++) {
                if (mFieldCardDetailSize[i] == 0) {
                    mFieldCardDetailSize[i]++;
                    mFieldCardListDetail[i][1] = tempCenterCardLastNum; // 1->0
                    mCard[tempCenterCardLastNum].moveTo(
                            mScreenConfig.mFieldDetailX[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i] +
                                    (mFieldCardDetailSize[i]-1) *
                                            mScreenConfig.mCardGabX);
                    break;
                }
            }
        }
        // 카드를 선택하는 모드가 아닐경우 (다 정리된 경우)
        if (mSelectPopOpenCardChoiceMode == false) {
            // 상대의 카드를 따낸다.
            takeAway();
            // 카드를 가져온다.
            getCard();
            // 손안의 카드를 정리한다.
            arrangeInHandCard();
            // 고스톱 선택과 게임 계속 중단모드가 아닐경우
            if (mSelectPopGoStopChoiceMode != true &&
                    mSelectPopCalcChoiceMode != true) {
                if (mCenterCardSize == 0) {
                    onShowMessage("다시 시작합니다.");
                    raiseEventForDevide();
                }
                chgTurnCls();
            }
        }
    }
    // 메시지 관리
    void onShowMessage(String pParam) {
        Toast toast = Toast.makeText(mMainContext, pParam, Toast.LENGTH_LONG);
        toast.show();
    }
    // 순서를 바꾼다.
    public void chgTurnCls() {
        disableSelectMode();
        if (mTurnCls == MY_TURN) {
            mTurnCls = YOUR_TURN;
            sleep(100);
            comBrain();
        } else if (mTurnCls == YOUR_TURN) {
            mTurnCls = MY_TURN;
        }
    }
    public void disableSelectMode(){
        mSelectPopSendCardChoiceMode = false;   // 카드를 낼때 선택모드
        mSelectPopOpenCardChoiceMode = false;	// 카드를 오픈할때 선택모드
        mSelectPopBombCardChoiceMode = false;	// 폭탄 선택모드
        mSelectPopBombNoCardChoiceMode = false;	// 폭탄, 빈카드 선택모드
        mSelectPopGoStopChoiceMode = false;		// 고스톱 선택모드
        mSelectPopCalcChoiceMode = false;		// 계속 중지 선택모드
    }
    // 컴퓨터의 손안의 카드를 정렬한다.
    public void arrangeInHandCard() {
        if (mTurnCls == MY_TURN) {
            for (int i = 1; i <= 10 - 1; i++) {
                for (int j = i + 1; j <= 10; j++) {
                    if (mInMyHandCardList[i] == 0) {
                        int tempCardNum = mInMyHandCardList[j];
                        mInMyHandCardList[j] = mInMyHandCardList[i];
                        mInMyHandCardList[i] = tempCardNum;
                    } else if (mInMyHandCardList[j] < mInMyHandCardList[i]
                            && mInMyHandCardList[j] != 0) {
                        int tempCardNum = mInMyHandCardList[j];
                        mInMyHandCardList[j] = mInMyHandCardList[i];
                        mInMyHandCardList[i] = tempCardNum;
                    }
                }
            }
            int sizeTemp = 0;
            for (int i = 1; i <= 10; i++) {
                if (mInMyHandCardList[i] != 0) {
                    sizeTemp++;
                }
                mCard[mInMyHandCardList[i]].move(
                        mScreenConfig.mInMyHandX
                                + (mScreenConfig.mCardBigWidth * (i - 1)),
                        mScreenConfig.mInMyHandY);
            }
            mInMyHandCardSize = sizeTemp;
            for (int i = 1; i <= mMyBlankCardSize; i++) {
                mMyBlankCard[i].move(
                        mScreenConfig.mInMyHandX +
                                (mScreenConfig.mCardBigWidth * (mInMyHandCardSize + i - 1)),
                        mScreenConfig.mInMyHandY);
            }
        } else if (mTurnCls == YOUR_TURN) {
            for (int i = 1; i <= 10 - 1; i++) {
                for (int j = i + 1; j <= 10; j++) {
                    if (mInYourHandCardList[i] == 0) {
                        int tempCardNum = mInYourHandCardList[j];
                        mInYourHandCardList[j] = mInYourHandCardList[i];
                        mInYourHandCardList[i] = tempCardNum;
                    } else if (mInYourHandCardList[j] < mInYourHandCardList[i]
                            && mInYourHandCardList[j] != 0) {
                        int tempCardNum = mInYourHandCardList[j];
                        mInYourHandCardList[j] = mInYourHandCardList[i];
                        mInYourHandCardList[i] = tempCardNum;
                    }
                }
            }

            int sizeTemp = 0;
            for (int i = 1; i <= 10; i++) {
                if (mInYourHandCardList[i] != 0) {
                    sizeTemp++;
                }
                mCard[mInYourHandCardList[i]].move(mScreenConfig.mInYourHandX
                                + (mScreenConfig.mCardBigWidth * (i - 1)),
                        mScreenConfig.mInYourHandY);
            }
            mInYourHandCardSize = sizeTemp;
            for (int i = 1; i <= mYourBlankCardSize; i++) {
                mYourBlankCard[i].move(mScreenConfig.mInYourHandX
                        + (mScreenConfig.mCardBigWidth * (mInYourHandCardSize
                        + i - 1)), mScreenConfig.mInYourHandY);
            }
        }
    }
    public void arrangeFieldDetailCard() {
        for (int i = 1; i <= 12 - 1; i++) {
            // 필드의 12곳에 있는 각각의 장소에 카드가 겹칠경우 정렬한다.
            if (mFieldCardDetailSize[i] > 0) {
                for (int j = 1; j <= mFieldCardDetailSize[i]; j++) {
                    // 약간의 갭을 두어 오른쪽 아래로 겹쳐서 위치시킨다.
                    mCard[mFieldCardListDetail[i][j]].move(
                            mScreenConfig.mFieldDetailX[i] + (j - 1)
                                    * mScreenConfig.mCardGabX,
                            mScreenConfig.mFieldDetailY[i] + (j - 1)
                                    * mScreenConfig.mCardGabY);
                }
            }
        }
    }
    public void showSendCardPanel(int Count, int[] CardNum) {
        if (Count == 2) {
            if (mTurnCls == MY_TURN) {
                mSelectSendPan.move((int) (mScreenConfig.mScreenWidth * 0.1),
                        (int) (mScreenConfig.mScreenHeight / 2 -
                                mScreenConfig.mCardHeight));
                mSelectSendCard[1].setImage(CardNum[1],
                        CardInfo.mCard[CardNum[1]][CardInfo.C_PIC]);
                mSelectSendCard[1].move(
                        (int) (mScreenConfig.mScreenWidth * 0.4),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[2].setImage(CardNum[2],
                        CardInfo.mCard[CardNum[2]][CardInfo.C_PIC]);
                mSelectSendCard[2].move(
                        (int) (mScreenConfig.mScreenWidth * 0.6),
                        (int) (mScreenConfig.mScreenHeight / 2));
            } else {
                mSelectSendPan.move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2 -
                                mScreenConfig.mCardHeight));
                mSelectSendCard[1].setImage(CardNum[1],
                        CardInfo.mCard[CardNum[1]][CardInfo.C_PIC]);
                mSelectSendCard[1].move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[2].setImage(CardNum[2],
                        CardInfo.mCard[CardNum[2]][CardInfo.C_PIC]);
                mSelectSendCard[2].move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2));
            }
        }
        if (Count == 3) {
            if (mTurnCls == 1) {
                mSelectSendPan.move((int) (mScreenConfig.mScreenWidth * 0.1),
                        (int) (mScreenConfig.mScreenHeight / 2 -
                                mScreenConfig.mCardHeight));
                mSelectSendCard[1].move(
                        (int) (mScreenConfig.mScreenWidth * 0.4),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[2].move(
                        (int) (mScreenConfig.mScreenWidth * 0.5),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[3].move(
                        (int) (mScreenConfig.mScreenWidth * 0.6),
                        (int) (mScreenConfig.mScreenHeight / 2));
            } else {
                mSelectSendPan.move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2 -
                                mScreenConfig.mCardHeight));
                mSelectSendCard[1].move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[2].move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2));
                mSelectSendCard[3].move((int) (-1000),
                        (int) (mScreenConfig.mScreenHeight / 2));
            }
        }
        if (mTurnCls == YOUR_TURN) {
            sleep(2000);
            comBrainSendCardToField(Count, mSelectSendCard);
        }
    }
    public void showOpenCardPanel(int Count, int[] CardNum) {
        if (Count == 2) {
            mSelectSendPan.move((int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
            mSelectSendCard[1].setImage(CardNum[1],
                    CardInfo.mCard[CardNum[1]][CardInfo.C_PIC]);
            mSelectSendCard[1].move((int) (mScreenConfig.mScreenWidth * 0.4),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectSendCard[2].setImage(CardNum[2],
                    CardInfo.mCard[CardNum[2]][CardInfo.C_PIC]);
            mSelectSendCard[2].move((int) (mScreenConfig.mScreenWidth * 0.6),
                    (int) (mScreenConfig.mScreenHeight / 2));
        }
        if (Count == 3) {
            mSelectSendPan.move((int) (mScreenConfig.mScreenWidth * 0.1),
                    (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
            mSelectSendCard[1].move((int) (mScreenConfig.mScreenWidth * 0.4),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectSendCard[2].move((int) (mScreenConfig.mScreenWidth * 0.5),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectSendCard[3].move((int) (mScreenConfig.mScreenWidth * 0.6),
                    (int) (mScreenConfig.mScreenHeight / 2));
        }
        if (mTurnCls == YOUR_TURN) {
            sleep(2000);
            comBrainOpenCard(Count, mSelectSendCard);
        }
    }
    // 폭탄패널 보여주기
    public void showBombCardPanel(int[] CardNum) {
        if (mSelectPopBombNoCardChoiceMode == true) {
            mSelectBombPan.move((int) (mScreenConfig.mScreenWidth * 0.2),
                    (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
            mSelectBombCard[1].move((int) (mScreenConfig.mScreenWidth * 0.4),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectBombCard[2].move((int) (mScreenConfig.mScreenWidth * 0.5),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectBombCard[3].move((int) (mScreenConfig.mScreenWidth * 0.6),
                    (int) (mScreenConfig.mScreenHeight / 2));
        } else if (mSelectPopBombNoCardChoiceMode == false) {
            mSelectBombPan.move((int) (mScreenConfig.mScreenWidth * 0.2),
                    (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
            mSelectBombCard[1].move((int) (mScreenConfig.mScreenWidth * 0.4),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectBombCard[2].move((int) (mScreenConfig.mScreenWidth * 0.5),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectBombCard[3].move((int) (mScreenConfig.mScreenWidth * 0.6),
                    (int) (mScreenConfig.mScreenHeight / 2));
            mSelectBombCard[4].move((int) (mScreenConfig.mScreenWidth * 0.7),
                    (int) (mScreenConfig.mScreenHeight / 2));
        }
        if (mTurnCls == YOUR_TURN) {
            sleep(2000);
            comBrainBombCard(mSelectPopBombNoCardChoiceMode, mSelectBombCard);
        }
    }
    // 고,스톱 선택 패널을 보여준다.
    public void showGoStopPanel() {
        mSelectBombPan.move((int) (mScreenConfig.mScreenWidth * 0.2),
                (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
        mSelectGoStopCard[1].move((int) (mScreenConfig.mScreenWidth * 0.4),
                (int) (mScreenConfig.mScreenHeight / 2));
        mSelectGoStopCard[2].move((int) (mScreenConfig.mScreenWidth * 0.6),
                (int) (mScreenConfig.mScreenHeight / 2));
        if (mTurnCls == YOUR_TURN) {
            sleep(2000);
            comBrainGoStop();
        }
    }

    public void showContinuePanel() {
        mSelectPopCalcChoiceMode = true;
        mWinner = mTurnCls;
        mSelectContinuePan.move((int) (mScreenConfig.mScreenWidth * 0.2),
                (int) (mScreenConfig.mScreenHeight / 2 - mScreenConfig.mCardHeight));
        mSelectCalcCard[1].move((int) (mScreenConfig.mScreenWidth / 2 -
                        mScreenConfig.mCardWidth),
                (int) (mScreenConfig.mScreenHeight * 0.8));
        mSelectCalcCard[2].move((int) (mScreenConfig.mScreenWidth / 2 +
                        mScreenConfig.mCardWidth),
                (int) (mScreenConfig.mScreenHeight  * 0.8));

    }
    // 상대카드 가져오기
    public void takeAway() {
        if (mTurnCls == MY_TURN) {
            for (int i = 1; i <= mTakeAwayCount; i++) {
                if (mInYourGet1CardSize > 0) {
                    int tempCardNum = mInYourGet1CardList[mInYourGet1CardSize];
                    mInYourGet1CardList[mInYourGet1CardSize] = 0;
                    mInYourGet1CardSize--;
                    int TempBaseY = (int) ((mInMyGet1CardSize - 1) / 10)
                            * mScreenConfig.mCardHeight;
                    int TempBaseX = (int) ((mInMyGet1CardSize - 1) % 10);
                    mCard[tempCardNum].moveTo(mScreenConfig.mInMyGet01X
                                    + mScreenConfig.mCardGabX * TempBaseX,
                            mScreenConfig.mInMyGet01Y - TempBaseY);
                    mInMyGet1CardSize++;
                    mInMyGet1CardList[mInMyGet1CardSize] = tempCardNum;
                }
            }
        } else if (mTurnCls == YOUR_TURN) {
            for (int i = 1; i <= mTakeAwayCount; i++) {
                if (mInMyGet1CardSize > 0) {
                    int tempCardNum = mInMyGet1CardList[mInMyGet1CardSize];
                    mInMyGet1CardList[mInMyGet1CardSize] = 0;
                    mInMyGet1CardSize--;
                    int TempBaseY = (int) ((mInYourGet1CardSize - 1) / 10)
                            * mScreenConfig.mCardHeight;
                    int TempBaseX = (int) ((mInYourGet1CardSize - 1) % 10);
                    mCard[tempCardNum].moveTo(mScreenConfig.mInYourGet01X
                                    + mScreenConfig.mCardGabX * TempBaseX,
                            mScreenConfig.mInYourGet01Y - TempBaseY);
                    mInYourGet1CardSize++;
                    mInYourGet1CardList[mInYourGet1CardSize] = tempCardNum;
                }
            }
        }
    }
    // 필드의 카드를 가져온다.
    public void getCard() {
        if (mTurnCls == MY_TURN) {
            for (int i = mGetCardTagetSize ; i >= 1; i--) {
                int cardNum = mGetCardTagetList[i];
                if (CardInfo.mCard[cardNum][CardInfo.C_KIND] == 20) {
                    sleep(10);
                    mCard[cardNum].moveTo(mScreenConfig.mInMyGet20X
                                    + mScreenConfig.mCardGabX * mInMyGet20CardSize,
                            mScreenConfig.mInMyGet20Y);
                    mInMyGet20CardSize++;
                    mInMyGet20CardList[mInMyGet20CardSize] = cardNum;

                } else if (CardInfo.mCard[cardNum][CardInfo.C_KIND] == 10) {
                    sleep(10);
                    mCard[cardNum].moveTo(mScreenConfig.mInMyGet10X
                                    + mScreenConfig.mCardGabX * mInMyGet10CardSize,
                            mScreenConfig.mInMyGet10Y);
                    mInMyGet10CardSize++;
                    mInMyGet10CardList[mInMyGet10CardSize] = cardNum;

                } else if (CardInfo.mCard[cardNum][CardInfo.C_KIND] == 5) {
                    sleep(10);
                    mCard[cardNum].moveTo(mScreenConfig.mInMyGet05X
                                    + mScreenConfig.mCardGabX * mInMyGet5CardSize,
                            mScreenConfig.mInMyGet05Y);
                    mInMyGet5CardSize++;
                    mInMyGet5CardList[mInMyGet5CardSize] = cardNum;

                } else if (CardInfo.mCard[cardNum][CardInfo.C_KIND] == 1) {
                    int TempBaseY = (int) ((mInMyGet1CardSize - 1) / 10)
                            * mScreenConfig.mCardHeight;
                    int TempBaseX = (int) ((mInMyGet1CardSize - 1) % 10);
                    mCard[cardNum].moveTo(mScreenConfig.mInMyGet01X
                                    + mScreenConfig.mCardGabX * TempBaseX,
                            mScreenConfig.mInMyGet01Y - TempBaseY);
                    mInMyGet1CardSize++;
                    mInMyGet1CardList[mInMyGet1CardSize] = cardNum;
                }
                // 해당하는 아이템을 뺀다.
                for (int j = 1; j <= 12; j++) {
                    for (int k = 1; k <= mFieldCardDetailSize[j]; k++) {
                        if (cardNum == mFieldCardListDetail[j][k]) {
                            for (int l = k; l < mFieldCardDetailSize[j]; l++) {
                                mFieldCardListDetail[j][l] = mFieldCardListDetail[j][l + 1];
                            }
                            mFieldCardDetailSize[j]--;
                        }
                    }
                }
            }
        } else if (mTurnCls == YOUR_TURN) {
            for (int i = mGetCardTagetSize; i >= 1; i--) {
                int CardNum = mGetCardTagetList[i];
                if (CardInfo.mCard[CardNum][CardInfo.C_KIND] == 20) {
                    sleep(10);
                    mCard[CardNum].moveTo(mScreenConfig.mInYourGet20X
                                    + mScreenConfig.mCardGabX * mInYourGet20CardSize,
                            mScreenConfig.mInYourGet20Y);
                    mInYourGet20CardSize++;
                    mInYourGet20CardList[mInYourGet20CardSize] = CardNum;

                } else if (CardInfo.mCard[CardNum][CardInfo.C_KIND] == 10) {
                    sleep(10);
                    mCard[CardNum].moveTo(mScreenConfig.mInYourGet10X
                                    + mScreenConfig.mCardGabX * mInYourGet10CardSize,
                            mScreenConfig.mInYourGet10Y);
                    mInYourGet10CardSize++;
                    mInYourGet10CardList[mInYourGet10CardSize] = CardNum;
                } else if (CardInfo.mCard[CardNum][CardInfo.C_KIND] == 5) {
                    sleep(10);
                    mCard[CardNum].moveTo(mScreenConfig.mInYourGet05X
                                    + mScreenConfig.mCardGabX * mInYourGet5CardSize,
                            mScreenConfig.mInYourGet05Y);
                    mInYourGet5CardSize++;
                    mInYourGet5CardList[mInYourGet5CardSize] = CardNum;
                } else if (CardInfo.mCard[CardNum][CardInfo.C_KIND] == 1) {
                    int TempBaseY = (int) ((mInYourGet1CardSize - 1) / 10)
                            * mScreenConfig.mCardHeight;
                    int TempBaseX = (int) ((mInYourGet1CardSize - 1) % 10);
                    sleep(10);
                    mCard[CardNum].moveTo(mScreenConfig.mInYourGet01X
                                    + mScreenConfig.mCardGabX * TempBaseX,
                            mScreenConfig.mInYourGet01Y + TempBaseY);
                    mInYourGet1CardSize++;
                    mInYourGet1CardList[mInYourGet1CardSize] = CardNum;
                }

                // 해당하는 아이템을 뺀다.
                for (int j = 1; j <= 12; j++) {
                    for (int k = 1; k <= mFieldCardDetailSize[j]; k++) {
                        if (CardNum == mFieldCardListDetail[j][k]) {
                            for (int l = k; l <= mFieldCardDetailSize[j]; l++){
                                mFieldCardListDetail[j][l] =
                                        mFieldCardListDetail[j][l + 1];
                            }
                            mFieldCardDetailSize[j]--;
                        }
                    }
                }
            }
        }
        setScore();
        arrangeFieldDetailCard();
    }
    // 점수를 계산한다.
    public void setScore() {
        // 내 패를 계산한다.
        {
            // 3광일 경우 계산 비광이 있다면 2점이다.
            if(mInMyGet20CardSize == 3){
                boolean temp45Exist = false;
                for(int i=1;i<=mInMyGet20CardSize;i++){
                    if(mInMyGet20CardList[i]==45){
                        temp45Exist = true;
                    }
                }
                if(temp45Exist == true){
                    mMy20Score= 2;
                }else{
                    mMy20Score=3;
                }
            }else if(mInMyGet20CardSize == 5){
                // 5광
                mMy20Score= 15;
            }else if(mInMyGet20CardSize == 4){
                // 4광
                mMy20Score=4;
            }else{
                mMy20Score = 0;
            }
            // 10끗이 5장 이상이면 점수가 올라간다.
            if(mInMyGet10CardSize >=5){
                mMy10Score =mInMyGet10CardSize-4;
            }else{
                mMy10Score = 0;
            }
            // 고도리인지 체크한다.
            int tempGodoriCnt =0;
            for(int i=1; i<= mInMyGet5CardSize;i++){
                if( mInMyGet5CardList[i] == 5 ||
                        mInMyGet5CardList[i] == 13 ||
                        mInMyGet5CardList[i] == 30 ){
                    tempGodoriCnt++;
                }
            }
            if(tempGodoriCnt >=3){
                mMyGodori = 5;
            }else{
                mMyGodori = 0;
            }
            // 5끗이 5장 이상이면 점수가 올라간다.
            if(mInMyGet5CardSize >=5){
                mMy5Score =mInMyGet5CardSize-4;
            }else{
                mMy5Score = 0;
            }
            // 홍단 청단 띠단을 계산한다.
            int tempHongdanCnt = 0;
            int tempChungdanCnt =0;
            int tempTidanCnt =0;
            for(int i=1; i<= mInMyGet5CardSize;i++){
                if( mInMyGet5CardList[i] == 2 ||
                        mInMyGet5CardList[i] == 6 ||
                        mInMyGet5CardList[i] == 10 ){
                    tempHongdanCnt++;
                }
                if( mInMyGet5CardList[i] == 22 ||
                        mInMyGet5CardList[i] == 34 ||
                        mInMyGet5CardList[i] == 38 ){
                    tempChungdanCnt++;
                }
                if( mInMyGet5CardList[i] == 14 ||
                        mInMyGet5CardList[i] == 18 ||
                        mInMyGet5CardList[i] == 26 ){
                    tempTidanCnt++;
                }
            }
            if(tempHongdanCnt==3){
                mMyHongdan = 3;
            }else{
                mMyHongdan = 0;
            }
            if(tempChungdanCnt==3){
                mMyChungdan = 3;
            }else{
                mMyChungdan = 0;
            }
            if(tempTidanCnt==3){
                mMyTidan = 3;
            }else{
                mMyTidan = 0;
            }
            // 피가 10장 이상이면 점수가 올라간다 쌍피는 2점으로 계산한다.
            int temp1ScoreSum = 0;
            for(int i=1; i<= mInMyGet1CardSize;i++){
                if(mInMyGet1CardList[i]==33){
                    temp1ScoreSum +=2;
                }else{
                    temp1ScoreSum +=1;
                }
            }
            if(temp1ScoreSum >=10){
                mMy1Score =temp1ScoreSum-9;
            }else{
                mMy1Score =0;
            }
            mMyTotScore =
                    mMy20Score + mMy10Score+ mMy5Score + mMy1Score +
                            mMyHongdan + mMyChungdan + mMyTidan + mMyGodori;

        }
        // 상대패를 계산한다.
        {
            // 3광일 경우 계산 비광이 있다면 2점이다.
            if(mInYourGet20CardSize == 3){
                boolean temp45Exist = false;
                for(int i=1;i<=mInYourGet20CardSize;i++){
                    if(mInYourGet20CardList[i]==45){
                        temp45Exist = true;
                    }
                }
                if(temp45Exist == true){
                    mYour20Score= 2;
                }else{
                    mYour20Score=3;
                }
            }
            else if(mInYourGet20CardSize >= 4){ // 20점이 4장 이상일 경우

                mYour20Score=mInYourGet20CardSize;
            }else{
                mYour20Score = 0;
            }
            // 10끗이 5장 이상일 경우 점수가 올라간다.
            if(mInYourGet10CardSize >=5){
                mYour10Score =mInYourGet10CardSize-4;
            }else{
                mYour10Score = 0;
            }
            // 고도리 여부
            int tempGodoriCnt =0;
            for(int i=1; i<= mInYourGet5CardSize;i++){
                if( mInYourGet5CardList[i] == 5 ||
                        mInYourGet5CardList[i] == 13 ||
                        mInYourGet5CardList[i] == 30 ){
                    tempGodoriCnt++;
                }
            }
            if(tempGodoriCnt >=3){
                mYourGodori = 5;
            }else{
                mYourGodori = 0;
            }
            // 5끗이 5장 이상일 경우 점수가 올라간다.
            if(mInYourGet5CardSize >=5){
                mYour5Score =mInYourGet5CardSize-4;
            }else{
                mYour5Score = 0;
            }
            // 홍단, 청단, 띠단을 체크한다.
            int tempHongdanCnt = 0;
            int tempChungdanCnt =0;
            int tempTidanCnt =0;
            for(int i=1; i<= mInYourGet5CardSize;i++){
                if( mInYourGet5CardList[i] == 2 ||
                        mInYourGet5CardList[i] == 6 ||
                        mInYourGet5CardList[i] == 10 ){
                    tempHongdanCnt++;
                }
                if( mInYourGet5CardList[i] == 22 ||
                        mInYourGet5CardList[i] == 34 ||
                        mInYourGet5CardList[i] == 38 ){
                    tempChungdanCnt++;
                }
                if( mInYourGet5CardList[i] == 14 ||
                        mInYourGet5CardList[i] == 18 ||
                        mInYourGet5CardList[i] == 26 ){
                    tempTidanCnt++;
                }
            }
            if(tempHongdanCnt==3){
                mYourHongdan = 3;
            }else{
                mYourHongdan = 0;
            }
            if(tempChungdanCnt==3){
                mYourChungdan = 3;
            }else{
                mYourChungdan = 0;
            }
            if(tempTidanCnt==3){
                mYourTidan = 3;
            }else{
                mYourTidan = 0;
            }
            // 1끗이 10장이상일 경우 점수에 반영되며 쌍피는 2점으로 계산한다.
            int temp1ScoreSum = 0;
            for(int i=1; i<= mInYourGet1CardSize;i++){
                if(mInYourGet1CardList[i]==33){
                    temp1ScoreSum +=2;
                }else{
                    temp1ScoreSum +=1;
                }
            }
            if(temp1ScoreSum >=10){
                mYour1Score =temp1ScoreSum-9;
            }else{
                mYour1Score =0;
            }
            mYourTotScore =
                    mYour20Score + mYour10Score+ mYour5Score + mYour1Score +
                            mYourHongdan + mYourChungdan + mYourTidan + mYourGodori;
        }
        // 5점이상이면 고 스톱 여부를 체크한다. 단 점수가 삭감됐을 경우 이전 점수를 초과해야 한다.
        if(mTurnCls == MY_TURN){
            if(mMyTotScore>=5 && mMyTotScore > mMyMaxScore){
                if(mInMyHandCardSize > 1){
                    mSelectGoStopCard[1].setImage(0, R.drawable.go_button);
                    mSelectGoStopCard[2].setImage(0, R.drawable.stop_button);
                    mSelectPopGoStopChoiceMode= true;
                    showGoStopPanel();
                    return;
                }else{	// 손에 카드가 없다면 계산모드로 간다.
                    calcMoney();
                }
            }
        }else if(mTurnCls == YOUR_TURN){
            if(mYourTotScore>=5 && mYourTotScore > mYourMaxScore){
                if(mInYourHandCardSize > 1){
                    mSelectGoStopCard[1].setImage(0, R.drawable.go_button);
                    mSelectGoStopCard[2].setImage(0, R.drawable.stop_button);
                    mSelectPopGoStopChoiceMode= true;
                    showGoStopPanel();
                    return;
                }else{
                    calcMoney();
                }
            }

        }
        // 이전 점수를 계산한다. 이전 점수보다 같거나 작다면 고 스톱을 부를수 없다.
        if(mMyMaxScore < mMyTotScore){
            mMyMaxScore = mMyTotScore;
        }
        if(mYourMaxScore < mYourTotScore){
            mYourMaxScore = mYourTotScore;
        }
    }
    // 금액을 계산한다.
    public void calcMoney() {
        if(mTurnCls == MY_TURN){
            mMyTotScore =
                    mMy20Score + mMy10Score+ mMy5Score + mMy1Score +
                            mMyHongdan + mMyChungdan + mMyTidan + mMyGodori;
            // 점 100원으로 계산
            long tempScoreCalc = (mMyTotScore + mMyGoCount);
            // 3고 이상일 경우
            if(mMyGoCount >=3){
                tempScoreCalc = tempScoreCalc * 2;
            }
            // 광박
            if(mMy20Score >= 2 && mInYourGet20CardSize == 0){
                tempScoreCalc = tempScoreCalc * 2;
            }
            //피박
            if(mMy1Score >= 1 && mInYourGet1CardSize < 6){
                tempScoreCalc = tempScoreCalc * 2;
            }
            long tempSumMoney = (tempScoreCalc) * 100;
            mMyMoneySum = mMyMoneySum + tempSumMoney;
            mYourMoneySum = mYourMoneySum - tempSumMoney;
        }else if(mTurnCls == YOUR_TURN){
            mYourTotScore =
                    mYour20Score + mYour10Score+ mYour5Score + mYour1Score +
                            mYourHongdan + mYourChungdan + mYourTidan + mYourGodori;
            // 점 100원으로 계산
            long tempScoreCalc = (mYourTotScore + mYourGoCount);
            // 3고 이상일 경우
            if(mYourGoCount >=3){
                tempScoreCalc = tempScoreCalc * 2;
            }
            // 광박
            if(mYour20Score >= 2 && mInMyGet20CardSize == 0){
                tempScoreCalc = tempScoreCalc * 2;
            }
            //피박
            if(mYour1Score >= 1 && mInMyGet1CardSize < 6){
                tempScoreCalc = tempScoreCalc * 2;
            }
            long tempSumMoney = (tempScoreCalc) * 100;
            mYourMoneySum = mYourMoneySum + tempSumMoney;
            mMyMoneySum = mMyMoneySum - tempSumMoney;
        }
        // 내가 소유한 돈이 없음
        if(mMyMoneySum <0){
            //showContinuePanel();
            onShowMessage("패!! 종료합니다.");
            this.mMainActivity.finish();
            return;
        }else if(mYourMoneySum< 0){
            //showContinuePanel();
            onShowMessage( "승!! 종료합니다.");
            this.mMainActivity.finish();
            return;
        }
        mSelectCalcCard[1].setImage(0, R.drawable.continue_button);
        mSelectCalcCard[2].setImage(0, R.drawable.end_button);
        mWinner = mTurnCls;
        mSelectPopCalcChoiceMode= true;
        showContinuePanel();
    }

    // 시간간격
    public void sleep(int i){
        try{
            Thread.sleep(i);
        }
        catch(Exception ex){}
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if ( event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            if (mSelectPopSendCardChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else if (mSelectPopOpenCardChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else if (mSelectPopBombNoCardChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else if (mSelectPopBombCardChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else if (mSelectPopGoStopChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else if( mSelectPopCalcChoiceMode == true) {
                controlChoice((int) x, (int) y);
            } else {
                if(mTurnCls == MY_TURN) {
                    int selectedCardNum = selectCard( x,  y);
                    if( selectedCardNum != 0){
                        for(int i=1;i<=10;i++){
                            if( selectedCardNum ==
                                    mInMyHandCardList[i]){
                                sendCardToField(selectedCardNum);
                                break;
                            }
                        }
                    } else {
                        // 빈 카드도 체크여부를 체크한다.
                        int selectedBlankCardNum = selectBlankCard( x,  y);
                        if(selectedBlankCardNum > 0){
                            for(int i=1;i<=10;i++){
                                if( selectedBlankCardNum ==
                                        mMyBlankCard[i].mCardNum){
                                    sendBlankCard(selectedBlankCardNum);
                                    break;
                                }
                            }
                        }
                    }
                } else if(mTurnCls == YOUR_TURN){
                    int selectedCardNum = selectCard( x,  y);
                    if( selectedCardNum != 0){
                        for(int i=1;i<=10;i++){
                            if( selectedCardNum ==
                                    mInYourHandCardList[i]){
                                sendCardToField(selectedCardNum);
                                break;
                            }
                        }
                    }else{
                        // 빈 카드도 체크여부를 체크한다.
                        int selectedBlankCardNum = selectBlankCard( x,  y);
                        if(selectedBlankCardNum > 0){
                            for(int i=1;i<=10;i++){
                                if( selectedBlankCardNum ==
                                        mYourBlankCard[i].mCardNum){
                                    sendBlankCard(selectedBlankCardNum);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    // 빈카드 선택
    int selectBlankCard(float x, float y){
        int selectedX = (int)x;
        int selectedY = (int)y;
        int returnCardNum = 0;
        for(int i=1; i<=10;i++)	{
            if(mTurnCls == 1) {
                if(mMyBlankCard[i].isSelected(selectedX, selectedY)){
                    returnCardNum = i;
                    break;
                }
            } else if(mTurnCls == 2){
                if(mYourBlankCard[i].isSelected(selectedX, selectedY)){
                    returnCardNum = i;
                    break;
                }
            }
        }
        return returnCardNum;
    }
    // 카드를 선택한다.
    public int selectCard(float x, float y){
        int selectedX = (int)x;
        int selectedY = (int)y;
        int selectedCardNum = 0;
        for(int i=1; i<= 48;i++)	{
            if(mCard[i].isSelected(selectedX, selectedY))	{
                selectedCardNum = i;
                break;
            }
        }
        return selectedCardNum;
    }

    // 아래는 컴퓨터 연산. ---------------------------------------------
    public void comBrainBombCard(boolean mSelectBombLoseMode,
                                 Card[] selectBombCard) {
        if (mTurnCls != YOUR_TURN) {
            return;
        }
        // 잃고 있느냐 따고 있느냐에 따라서 구분
        if (mSelectBombLoseMode == true) {
            mChoiceNum = mSelectBombCard[1].mCardNum;
        } else {
            mChoiceNum = BOMB_NUM;
        }
        mHandler.sendMessage(Message.obtain(mHandler, H_BOMBCARDCHOICE));

    }
    // 카드선택을 임으로 처리함.
    public void comBrainSendCardToField(int count, Card[] selectSendCard) {
        if (mTurnCls != YOUR_TURN) {
            return;
        }
        // 바닥에 넬 카드를 생각한다. 우선순위를 정한다.
        int tempChoiceNum = 0;
        for (int i = 1; i <= count; i++) {
            if (selectSendCard[i].mCardNum == 42) // 똥 상피
            {
                tempChoiceNum = 42;
                break;
            } else if (selectSendCard[i].mCardNum == 48) // 비 쌍피
            {
                tempChoiceNum = 48;
                break;
            }else if (selectSendCard[i].mCardNum == 33) // 구 쌍피
            {
                tempChoiceNum = 33;
                break;
            } else if (selectSendCard[i].mCardNum == 41) // 똥광
            {
                tempChoiceNum = 41;
                break;
            } else if (selectSendCard[i].mCardNum == 29) // 8광
            {
                tempChoiceNum = 29;
                break;
            } else if (selectSendCard[i].mCardNum == 9) // 3광
            {
                tempChoiceNum = 9;
                break;
            } else if (selectSendCard[i].mCardNum == 1) // 1광
            {
                tempChoiceNum = 1;
                break;
            } else if (selectSendCard[i].mCardNum == 45) // 비광
            {
                tempChoiceNum = 45;
                break;
            }

        }
        if (tempChoiceNum == 0) {
            tempChoiceNum = selectSendCard[0].mCardNum;
        }
        mChoiceNum = tempChoiceNum;
        mHandler.sendMessage(Message.obtain(mHandler, H_SENDCARDCHOICE));
    }
    // 카드오픈
    public void comBrainOpenCard(int count, Card[] selectSendCard) {
        if (mTurnCls != YOUR_TURN) {
            return;
        }
        // 가운데 카드를 열었을 때
        int tempChoiceNum = 0;
        for (int i = 1; i <= count; i++) {
            if (selectSendCard[i].mCardNum == 42) // 똥쌍피
            {
                tempChoiceNum = 42;
                break;
            } else if (selectSendCard[i].mCardNum == 33) // 9 쌍피
            {
                tempChoiceNum = 33;
                break;
            } else if (selectSendCard[i].mCardNum == 48) // 비 쌍피
            {
                tempChoiceNum = 48;
                break;
            } else if (selectSendCard[i].mCardNum == 41) // 똥광
            {
                tempChoiceNum = 41;
                break;
            } else if (selectSendCard[i].mCardNum == 29) // 8광
            {
                tempChoiceNum = 29;
                break;
            } else if (selectSendCard[i].mCardNum == 9) // 3광
            {
                tempChoiceNum = 9;
                break;
            } else if (selectSendCard[i].mCardNum == 1) // 1광
            {
                tempChoiceNum = 1;
                break;
            } else if (selectSendCard[i].mCardNum == 45) // 비광
            {
                tempChoiceNum = 45;
                break;
            }
        }
        if (tempChoiceNum == 0) {
            tempChoiceNum = selectSendCard[0].mCardNum;
        }
        mChoiceNum = tempChoiceNum;
        mHandler.sendMessage(Message.obtain(mHandler, H_OPENCARDCHOICE));
    }
    // 컴퓨터가 고스톱을 선택한다.
    public void comBrainGoStop() {
        if (mTurnCls == MY_TURN) {
            return;
        }
        // 손에 카드가 있고 상대가 2점보다 크다면 스톱한다.
        if (mInYourHandCardSize > 0) {
            if (mMyTotScore > 2) {
                mChoiceNum = STOP_NUM;
            } else {
                mChoiceNum = GO_NUM;
            }
        } else {
            mChoiceNum = STOP_NUM;
        }
        mHandler.sendMessage(Message.obtain(mHandler, H_GOSTOPCHOICE));
    }
    // 컴퓨터 연산
    public void comBrain() {
        if(mTurnCls == MY_TURN)
        {
            return;
        }
        // 카드가 없으면 계속여부를 선택할 패널을 보여준다.
        if (mInYourHandCardSize == 0 && mInMyHandCardSize == 0
                && mYourBlankCardSize == 0 && mMyBlankCardSize == 0) {
            showContinuePanel();
            return;
        }
        // 카드가 없고 폭탄으로 인한 빈카드만 있다면 빈 카드를 낸다.
        if (mInYourHandCardSize == 0 && mYourBlankCardSize > 1) {
            sendBlankCard(1);
            return;
        }
        // 낼 카드번호 초기화
        int SendCardNum = 0;
        // 손에 카드가 있다면
        if (mInYourHandCardSize > 0) {

            int myTotScore = mMyTotScore;
            // 플레이어 점수가 높을 경우
            if (myTotScore >= SCORE_ALERT) {
                if (mYour20Count == 4) { // 20점 4개를 가지고 있을 경우
                    if (comBrainCalc(CALC_5_GWANG_F) == SEND_YES)	// 5광이 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_5_GWANG_H) == SEND_YES)	// 5광이 손에 있을 경우
                        return;
                }
                if (mYourGodoriCount == 2) {	// 고도리 2개를 가지고 있을 경우
                    if (comBrainCalc(CALC_GODORI_F) == SEND_YES)	// 고도리가 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_GODORI_H) == SEND_YES)	// 고도리가 손에 있을 경우
                        return;
                }
                if (mYourHongdanCount == 2) {	// 홍단 2개를 가지고 있을 경우
                    if (comBrainCalc(CALC_HONGDAN_F) == SEND_YES)	// 홍단이 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_HONGDAN_H) == SEND_YES)	// 홍단이 손에 있을 경우
                        return;
                }
                if (mYourTidanCount == 2) {	// 띠단 2개를 가지고 있을 경우
                    if (comBrainCalc(CALC_TIDAN_F) == SEND_YES)	// 띠단이 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_TIDAN_H) == SEND_YES)	// 띠단이 손에 있을 경우
                        return;
                }
                if (mYourChungdanCount == 2) {	// 청단 2개를 가지고 있을 경우
                    if (comBrainCalc(CALC_CHUNGDAN_F) == SEND_YES)	// 청단이 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_CHUNGDAN_H) == SEND_YES)	// 청단이 손에 있을 경우
                        return;
                }
                if (mYour20Count >= 2) {	// 광 2개를 가지고 있을 경우
                    if (comBrainCalc(CALC_3_GWANG_F) == SEND_YES)	// 광이 바닥에 있을 경우
                        return;
                    if (comBrainCalc(CALC_3_GWANG_H) == SEND_YES)	// 광이 손에 있을 경우
                        return;
                }
            }
            // 플레이어 점수가 낮을 경우
            else {
                if (mYour20Count == 4) {
                    if (comBrainCalc(CALC_5_GWANG_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_5_GWANG_H) == SEND_YES)
                        return;
                }
                if (mYourGodoriCount == 2) {
                    if (comBrainCalc(CALC_GODORI_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_GODORI_H) == SEND_YES)
                        return;
                }
                if (mYourHongdanCount == 2) {
                    if (comBrainCalc(CALC_HONGDAN_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_HONGDAN_H) == SEND_YES)
                        return;
                }
                if (mYourTidanCount == 2) {
                    if (comBrainCalc(CALC_TIDAN_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_TIDAN_H) == SEND_YES)
                        return;
                }
                if (mYourChungdanCount == 2) {
                    if (comBrainCalc(CALC_CHUNGDAN_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_CHUNGDAN_H) == SEND_YES)
                        return;
                }
                if (mYour20Count >= 2) {
                    if (comBrainCalc(CALC_3_GWANG_F) == SEND_YES)
                        return;
                    if (comBrainCalc(CALC_3_GWANG_H) == SEND_YES)
                        return;
                }
            }
            // 똥 쌍피가 바닥에 있고 11자를 들고 있을 경우
            if (comBrainFieldN(42) > 0 && comBrainHandMonth(11) > 0)
            {
                SendCardNum = comBrainHandChoice(12);
                sendCardToField(SendCardNum);
                return;
            }
            // 구 쌍피가 바닥에 있고 9자를 들고 있을 경우
            else if (comBrainFieldN(33) > 0 && comBrainHandMonth(9) > 0)
            {
                SendCardNum = comBrainHandChoice(9);
                sendCardToField(SendCardNum);
                return;
            }
            // 똥쌍피가 바닥에 있고 12자를 들고 있을 경우
            else if (comBrainFieldN(48) > 0 && comBrainHandMonth(12) > 0)
            {
                SendCardNum = comBrainHandChoice(12);
                sendCardToField(SendCardNum);
                return;
            }
            // 똥광이 바닥에 있고 11자를 들고 있을 경우
            else if (comBrainFieldN(41) > 0 && comBrainHandMonth(11) > 0)
            {
                SendCardNum = comBrainHandChoice(11);
                sendCardToField(SendCardNum);
                return;

            }
            // 8광이 바닥에 있고 8자를 들고 있을 경우
            else if (comBrainFieldN(29) > 0 && comBrainHandMonth(8) > 0)
            {
                SendCardNum = comBrainHandChoice(8);
                sendCardToField(SendCardNum);
                return;
            }
            // 3광이 바닥에 있고 3자를 들고 있을 경우
            else if (comBrainFieldN(9) > 0 && comBrainHandMonth(3) > 0)
            {
                SendCardNum = comBrainHandChoice(3);
                sendCardToField(SendCardNum);
                return;
            }
            // 1광이 바닥에 있고 1자를 들고 있을 경우
            else if (comBrainFieldN(1) > 0 && comBrainHandMonth(1) > 0)
            {
                SendCardNum = comBrainHandChoice(1);
                sendCardToField(SendCardNum);
                return;
            }
            // 비광이 바닥에 있고 12자를 들고 있을 경우
            else if (comBrainFieldN(45) > 0 && comBrainHandMonth(12) > 0)
            {
                SendCardNum = comBrainHandChoice(12);
                sendCardToField(SendCardNum);
                return;
            }
            // 그외 동일 한 자가 있다면 해당 카드를 냄.
            boolean exsitField = false;
            for (int i = 1; i <= 12; i++) {
                if (comBrainFieldMonth(i) > 0) {
                    if (comBrainHandMonth(i) > 0)
                    {
                        exsitField = true;
                        SendCardNum = comBrainHandChoice(i);
                        sendCardToField(SendCardNum);
                        return;
                    }
                }
            }
            // 카드가 없는데 폭탄으로 인한 빈 카드가 있다면 빈카드를 냄.
            if (exsitField == false) {
                if (mYourBlankCardSize > 0) {
                    sendBlankCard(1);
                    return;
                }
            }
        }
        // 아무것도 속해있지 않다면 첫번째 카드를 냄.
        if (SendCardNum == 0) {
            SendCardNum = mInYourHandCardList[1];
        }
        sendCardToField(SendCardNum);
        return;
    }

    // 컴퓨터 연산 계산
    public int comBrainCalc(int calcType) {
        switch (calcType) {
            //
            case CALC_5_GWANG_F:	// 5광이 바닥에 있는경우
                if (comBrainFieldN(41) > 0 && comBrainHandMonth(11) > 0) {
                    int SendCardNum = comBrainHandChoice(11);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(29) > 0 && comBrainHandMonth(8) > 0) {
                    int SendCardNum = comBrainHandChoice(8);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(9) > 0 && comBrainHandMonth(3) > 0) {
                    int SendCardNum = comBrainHandChoice(3);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(1) > 0 && comBrainHandMonth(1) > 0) {
                    int SendCardNum = comBrainHandChoice(1);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }else if (comBrainFieldN(45) > 0 && comBrainHandMonth(12) > 0) {
                    int SendCardNum = comBrainHandChoice(12);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 5광이 손에 있는경우
            case CALC_5_GWANG_H:
                if (comBrainHandN(41) > 0 && comBrainFieldMonth(11) > 0) {
                    int SendCardNum = 41;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }else if (comBrainHandN(29) > 0 && comBrainFieldMonth(8) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }else if (comBrainHandN(9) > 0 && comBrainFieldMonth(3) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }else if (comBrainHandN(1) > 0 && comBrainFieldMonth(1) > 0) {
                    int SendCardNum = 1;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }else if (comBrainHandN(45) > 0 && comBrainFieldMonth(12) > 0) {
                    int SendCardNum = 45;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 고도리가 바닥에 있는 경우
            case CALC_GODORI_F:
                if (comBrainFieldN(5) > 0 && comBrainHandMonth(2) > 0) {
                    int SendCardNum = comBrainHandChoice(2);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(13) > 0 && comBrainHandMonth(4) > 0) {
                    int SendCardNum = comBrainHandChoice(4);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(30) > 0 && comBrainHandMonth(8) > 0) {
                    int SendCardNum = comBrainHandChoice(8);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 고도리가 손에 있는 경우
            case CALC_GODORI_H:
                if (comBrainHandN(5) > 0 && comBrainFieldMonth(2) > 0) {
                    int SendCardNum = 5;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(13) > 0 && comBrainFieldMonth(4) > 0) {
                    int SendCardNum = 13;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(30) > 0 && comBrainFieldMonth(8) > 0) {
                    int SendCardNum = 30;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 홍단이 바닥에 있는 경우
            case CALC_HONGDAN_F:
                if (comBrainFieldN(2) > 0 && comBrainHandMonth(1) > 0) {
                    int SendCardNum = comBrainHandChoice(1);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(6) > 0 && comBrainHandMonth(2) > 0) {
                    int SendCardNum = comBrainHandChoice(2);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(10) > 0 && comBrainHandMonth(3) > 0) {
                    int SendCardNum = comBrainHandChoice(3);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 홍단이 손에 있는 경우
            case CALC_HONGDAN_H:
                if (comBrainHandN(2) > 0 && comBrainFieldMonth(1) > 0) {
                    int SendCardNum = 2;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(6) > 0 && comBrainFieldMonth(2) > 0) {
                    int SendCardNum = 6;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(10) > 0 && comBrainFieldMonth(3) > 0) {
                    int SendCardNum = 10;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 띠단이 바닥에 있는 경우
            case CALC_TIDAN_F:
                if (comBrainFieldN(14) > 0 && comBrainHandMonth(4) > 0) {
                    int SendCardNum = comBrainHandChoice(4);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(18) > 0 && comBrainHandMonth(5) > 0) {
                    int SendCardNum = comBrainHandChoice(5);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(26) > 0 && comBrainHandMonth(7) > 0) {
                    int SendCardNum = comBrainHandChoice(7);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 띠단이 손에 있는 경우
            case CALC_TIDAN_H:
                if (comBrainHandN(14) > 0 && comBrainFieldMonth(4) > 0) {
                    int SendCardNum = 14;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(18) > 0 && comBrainFieldMonth(5) > 0) {
                    int SendCardNum = 18;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(26) > 0 && comBrainFieldMonth(7) > 0) {
                    int SendCardNum = 26;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 청단이 바닥에 있는 경우
            case CALC_CHUNGDAN_F:
                if (comBrainFieldN(22) > 0 && comBrainHandMonth(6) > 0) {
                    int SendCardNum = comBrainHandChoice(6);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(34) > 0 && comBrainHandMonth(9) > 0) {
                    int SendCardNum = comBrainHandChoice(9);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainFieldN(38) > 0 && comBrainHandMonth(10) > 0) {
                    int SendCardNum = comBrainHandChoice(10);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 청단이 손에 있는 경우
            case CALC_CHUNGDAN_H:
                if (comBrainHandN(22) > 0 && comBrainFieldMonth(6) > 0) {
                    int SendCardNum = 22;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(34) > 0 && comBrainFieldMonth(9) > 0) {
                    int SendCardNum = 34;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                if (comBrainHandN(38) > 0 && comBrainFieldMonth(10) > 0) {
                    int SendCardNum = 38;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 3광이 바닥에 있는 경우
            case CALC_3_GWANG_F:
                if (comBrainFieldN(41) > 0 && comBrainHandMonth(11) > 0) {
                    int SendCardNum = comBrainHandChoice(11);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(29) > 0 && comBrainHandMonth(8) > 0) {
                    int SendCardNum = comBrainHandChoice(8);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(9) > 0 && comBrainHandMonth(3) > 0) {
                    int SendCardNum = comBrainHandChoice(3);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(1) > 0 && comBrainHandMonth(1) > 0) {
                    int SendCardNum = comBrainHandChoice(1);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(45) > 0 && comBrainHandMonth(12) > 0) {
                    int SendCardNum = comBrainHandChoice(12);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 3광이 손에 있는 경우
            case CALC_3_GWANG_H:
                if (comBrainHandN(41) > 0 && comBrainFieldMonth(11) > 0) {
                    int SendCardNum = 41;
                    sendCardToField(SendCardNum);
                    return SEND_YES;

                } else if (comBrainHandN(29) > 0 && comBrainFieldMonth(8) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(9) > 0 && comBrainFieldMonth(3) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(1) > 0 && comBrainFieldMonth(1) > 0) {
                    int SendCardNum = 1;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(45) > 0 && comBrainFieldMonth(12) > 0) {
                    int SendCardNum = 45;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 광이 바닥에 있을 경우
            case CALC_GWANG_F:
                if (comBrainFieldN(41) > 0 && comBrainHandMonth(11) > 0) {
                    int SendCardNum = comBrainHandChoice(11);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(29) > 0 && comBrainHandMonth(8) > 0) {
                    int SendCardNum = comBrainHandChoice(8);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(9) > 0 && comBrainHandMonth(3) > 0) {
                    int SendCardNum = comBrainHandChoice(3);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(1) > 0 && comBrainHandMonth(1) > 0) {
                    int SendCardNum = comBrainHandChoice(1);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainFieldN(45) > 0 && comBrainHandMonth(12) > 0) {
                    int SendCardNum = comBrainHandChoice(12);
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            // 광이 손에 있는 경우
            case CALC_GWANG_H:
                if (comBrainHandN(41) > 0 && comBrainFieldMonth(11) > 0) {
                    int SendCardNum = 41;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(29) > 0 && comBrainFieldMonth(8) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(9) > 0 && comBrainFieldMonth(3) > 0) {
                    int SendCardNum = 29;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(1) > 0 && comBrainFieldMonth(1) > 0) {
                    int SendCardNum = 1;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                } else if (comBrainHandN(45) > 0 && comBrainFieldMonth(12) > 0) {
                    int SendCardNum = 45;
                    sendCardToField(SendCardNum);
                    return SEND_YES;
                }
                break;
            default:
        }
        return SEND_NO;
    }

    // 손에 있는 것 선택.
    public int comBrainHandChoice(int cardValue) {
        int return_card_num = 0;
        int mSameListSize = 0;
        int num_20 = 0;
        int num_10 = 0;
        int num_5 = 0;
        int num_1 = 0;
        int[] mSameList = new int[10];
        for (int i = 1; i <= mInYourHandCardSize; i++) {
            int tempFieldMonth = CardInfo.mCard[mInYourHandCardList[i]][CardInfo.C_MONTH];
            int tempFieldKind = CardInfo.mCard[mInYourHandCardList[i]][CardInfo.C_KIND];
            if (cardValue == tempFieldMonth) {
                mSameListSize++;
                mSameList[mSameListSize] = mInYourHandCardList[i];
            }
            // 끗 별로
            if (tempFieldKind == 20) {
                num_20 = mInYourHandCardList[i];
            } else if (tempFieldKind == 10) {
                num_10 = mInYourHandCardList[i];
            } else if (tempFieldKind == 5) {
                num_5 = mInYourHandCardList[i];
            } else if (tempFieldKind == 1) {
                num_1 = mInYourHandCardList[i];
            }
        }
        // 1개일 경우
        if (mSameListSize == 1) {
            return_card_num = mSameList[mSameListSize];
        } else {
            // 20개가 복수일 경우
            if (mYour20Count >= 2) {
                return_card_num = num_20;
            } else if (mYour1Count >= 10) {
                return_card_num = num_1;
            } else if (mYour5Count >= 5) {
                return_card_num = num_5;
            } else if (num_20 > 0){
                return_card_num = num_20;
            } else if (num_1 > 0) {
                return_card_num = num_1;
            } else if (num_5 > 0) {
                return_card_num = num_5;
            } else if (num_10 > 0) {
                return_card_num = num_10;
            }
        }
        return return_card_num;
    }
    // 카드의 월 반환
    public int comBrainFieldMonth(int cardValue) {
        int return_value = 0;
        for (int i = 0; i < 12; i++) {
            if (mFieldCardDetailSize[i] > 0) {
                int tempFieldFirstCardNum =
                        mFieldCardListDetail[i][mFieldCardDetailSize[i]];
                int tempFieldValue =
                        CardInfo.mCard[tempFieldFirstCardNum][CardInfo.C_MONTH];
                if (tempFieldValue == cardValue) {
                    return_value = mFieldCardDetailSize[i];
                }
            }
        }
        return return_value;
    }
    // 바닥에 있는 카드의 개수 반환
    public int comBrainFieldN(int cardNum) {
        int return_num = 0;
        for (int i = 0; i < 12; i++) {
            if (mFieldCardDetailSize[i] > 0) {
                for (int j = 0; j < mFieldCardDetailSize[i]; j++) {
                    if (mFieldCardListDetail[i][j] == cardNum) {
                        return_num++;
                        break;
                    }
                }
            }
        }
        return return_num;
    }
    // 손안에 카드번호 개수 반환.
    public int comBrainHandN(int cardNum) {
        int return_num = 0;
        for (int i = 1; i <= mInYourHandCardSize; i++) {
            int tempFieldValue = mInYourHandCardList[i];
            if (cardNum == tempFieldValue) {
                return_num ++;
            }
        }
        return return_num;
    }
    // 카드번호에 해당하는 월개수 반환
    public int comBrainHandMonth(int cardValue) {
        int return_value = 0;
        for (int i = 1; i <= mInYourHandCardSize; i++) {
            int tempFieldValue = CardInfo.mCard[mInYourHandCardList[i]][CardInfo.C_MONTH];
            if (cardValue == tempFieldValue) {
                return_value++;
            }
        }
        return return_value;
    }
}
