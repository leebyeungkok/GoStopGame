package com.lbo.gostoptouch;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainView extends SurfaceView implements SurfaceHolder.Callback{

    private static int SCREEN_WIDTH = 2000;
    private static int SCREEN_HEIGHT = 1000;

    public static final int MY_TURN     = 1;
    public static final int YOUR_TURN   = 2;
    // 선택모드
    final static int H_SENDCARDCHOICE 	= 1;	// 카드선택(손에서 낼때)
    final static int H_OPENCARDCHOICE 	= 2;	// 카드선택 (가운데 카드 선택)
    final static int H_BOMBCARDCHOICE 	= 3;	// 폭탄선택
    final static int H_GOSTOPCHOICE 	= 4;	// 고와 스톱 선택
    final static int H_CALCCHOICE 		= 5;	// 계산모드
    final static int H_OPENCARDMODE 	= 6;	// 카드 뒤집기
    final static int H_DEVIDE 			= 7;	// 카드분배

    private MainActivity mMainActivity;
    private MainThread mMainThread;
    private Handler mHandler;
    private Context mMainContext;
    private boolean mDrawCls = false;

    private ScreenConfig mScreenConfig;

    private Card[] mCard;
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
    // 따낼카드
    public int mGetCardTagetSize = 0;
    public int[] mGetCardTagetList = new int[20+1];
    int mTurnCls = MY_TURN;
    public MainView(Context context){
        super(context);
        getHolder().addCallback(this);
        mMainThread = new MainThread(getHolder(),this);
        setFocusable(true);
        mMainContext = context;

        mHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message msg){
                switch (msg.what) {
                    case H_SENDCARDCHOICE:
                        break;
                    case H_OPENCARDCHOICE:
                        break;
                    case H_BOMBCARDCHOICE:
                        break;
                    case H_GOSTOPCHOICE:
                        break;
                    case H_CALCCHOICE:
                        break;
                    case H_DEVIDE:
                        shuffleCard();
                        break;
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

        raiseEventForDistribute();
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
        for (int i = 1; i <= mInMyHandCardSize; i++) {
            if(mInMyHandCardList[i] != 0)
                mCard[mInMyHandCardList[i]].drawBig(canvas);
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
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if ( event.getAction() == MotionEvent.ACTION_DOWN){
            float x = event.getX();
            float y = event.getY();
            int currentNum = selectCard((int)x, (int)y);
            sendCardToField(currentNum);
        }
        return true;
    }
    // 새로 추가된 함수=================================
    public int selectCard(float x, float y){
        int selected_x = (int)x;
        int selected_y = (int)y;
        int selectedCardNum = 0;
        for(int i=0; i<= 48;i++)	{
            if(mCard[i].isSelected(selected_x, selected_y))	{
                selectedCardNum = i;
                break;
            }
        }
        return selectedCardNum;
    }
    public void shuffleCard(){
        synchronized (mMainThread.getSurfaceHolder()) {
            mInYourHandCardSize =0;
            mInMyHandCardSize = 0;

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
                tempArray[i][1] = java.lang.Math.random();
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
    public void raiseEventForDistribute(){
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
    }
    // 가운데로 이동
    public void moveCenter(int cardNum) {
        mCard[cardNum].move(mScreenConfig.mCenterX,
                mScreenConfig.mCenterY);
    }
    // 컴퓨터손으로 이동
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
    // 내손안으로 이동
    public void moveFromCenterToMyHand() {
        int cardNum = mCenterCardList[mCenterCardSize];
        mCenterCardSize--;
        mInMyHandCardSize++;
        mInMyHandCardList[mInMyHandCardSize] = cardNum;
        mCard[cardNum].moveTo(mScreenConfig.mInMyHandX
                        + (mScreenConfig.mCardBigWidth * (mInMyHandCardSize -1)),
                mScreenConfig.mInMyHandY);
    }
    // 가운데에서 바닥의 12곳중 빈곳으로 이동
    public void moveFromCenterToField() {
        int cardNum = mCenterCardList[mCenterCardSize];
        mCenterCardSize--;
        for (int j = 1; j <= 12; j++) {
            if (mFieldCardDetailSize[j] == 0){
                mCard[cardNum].moveTo(
                        mScreenConfig.mFieldDetailX[j],
                        mScreenConfig.mFieldDetailY[j]);
                mFieldCardListDetail[j][1] = cardNum;
                mFieldCardDetailSize[j]++;
                break;
            }
        }
    }

    // 컴퓨터의 카드를 정렬
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
    // 내카들 정렬
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
    // 카드를 바닥에 보낸다.
    public void sendCardToField(int cardNum){
        // 임의의 지점으로 이동
        mCard[cardNum].moveTo(mScreenConfig.mFieldDetailX[11],mScreenConfig.mFieldDetailY[11]);
    }
}
