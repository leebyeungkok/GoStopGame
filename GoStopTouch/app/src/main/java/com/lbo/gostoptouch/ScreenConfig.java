package com.lbo.gostoptouch;

public class ScreenConfig {
    public   int mScreenWidth;			// 실제화면의폭
    public   int mScreenHeight;			// 실제화면의높이

    public   int mVirtualScreenWidth;	// 가상화면의폭
    public   int mVirtualScreenHeight;	// 가상화면의높이

    public   int mCardWidth;            // 카드의폭
    public   int mCardHeight;           // 카드의높이
    public   int mCardBigWidth;         // 큰카드의폭
    public   int mCardBigHeight;        // 큰카드의높이
    public   int mCardGabX;             // 카드간가로간격
    public   int mCardGabY;             // 카드간세로간격
    public   int mCardGabSmallX;        // 카드간좁은가로간격
    public   int mCardGabSmallY;        // 카드간좁은세로간격
    // 필드의 가로위치
    public   int mCenterX;              // 가운데가로위치
    public   int mFieldX;               // 필드의가로위치
    public   int mInMyHandX;            // 내가가지고있는카드가로위치
    public   int mInYourHandX;          // 컴퓨터가가지고있는카드가로위치
    public   int mInMyGet20X;           // 내가따낸 20점카드 가로위치
    public   int mInYourGet20X;         // 컴퓨터가따낸 20점카드 가로위치
    public   int mInMyGet10X;           // 내가따낸 10점카드 가로위치
    public   int mInYourGet10X;         // 컴퓨터가따낸 10점카드 가로위치
    public   int mInMyGet05X;           // 내가따낸 5점카드 가로위치
    public   int mInYourGet05X;         // 컴퓨터가따낸 5점카드 가로위치
    public   int mInMyGet01X;           // 내가따낸 1점카드 가로위치
    public   int mInYourGet01X;         // 컴퓨터가따낸 1점카드 가로위치
    // 필드의 세로위치
    public   int mCenterY;              // 가운데세로위치
    public   int mFieldY;                // 필드의세로위치
    public   int mInMyHandY;           // 내가가지고있는카드세로위치
    public   int mInYourHandY;          // 컴퓨터가가지고있는카드세로위치
    public   int mInMyGet20Y;           // 내가따낸 20점카드 가로위치
    public   int mInYourGet20Y;         // 컴퓨터가따낸 20점카드 가로위치
    public   int mInMyGet10Y;           // 내가따낸 10점카드 가로위치
    public   int mInYourGet10Y;         // 컴퓨터가따낸 10점카드 가로위치
    public   int mInMyGet05Y;           // 내가따낸 5점카드 가로위치
    public   int mInYourGet05Y;         // 컴퓨터가따낸 5점카드 가로위치
    public   int mInMyGet01Y;           // 내가따낸 1점카드 가로위치
    public   int mInYourGet01Y;         // 컴퓨터가따낸 1점카드 가로위치

    public   int[] mFieldDetailX = new int[12+1];
    public   int[] mFieldDetailY = new int[12+1];
    public   int mMySignJumsuX;         // 내점수 가로위치  (확인)
    public   int mMySignJumsuY;         // 내점수 세로위치
    public   int mYourSignJumsuX;       // 컴퓨터점수 가로위치
    public   int mYourSignJumsuY;       // 컴퓨터점수 세로위치

    public ScreenConfig(int screenWidth , int screenHeight, int virtualWidth, int virtualHeight) {
        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;
        mVirtualScreenWidth = virtualWidth;
        mVirtualScreenHeight = virtualHeight;

        double mCalcScreenWidth =
                (double)virtualWidth/(double)screenWidth;
        double mCalcScreenHeight =
                (double)virtualHeight/(double)screenHeight;
        mCardWidth      = (int)(mVirtualScreenWidth/15);
        mCardHeight     = (int)(mVirtualScreenHeight/7.5);
        mCardBigWidth   = (int)(mVirtualScreenWidth/10);
        mCardBigHeight  = (int)(mVirtualScreenHeight/5);
        mCardGabX       = (int)(mCardWidth/3);
        mCardGabY       = (int)(mCardHeight/3);
        mCardGabSmallX  = (int)(mCardWidth/5);
        mCardGabSmallY  = (int)(mCardHeight/5);
        mCenterX        = (int)(mVirtualScreenWidth/2) - (int)(mCardWidth/2);
        mFieldX         = 0;
        mInMyHandX      = 1;
        mInYourHandX    = 1;
        mInMyGet20X     = 1;
        mInYourGet20Y   = 1;
        mInMyGet10X     = (int)(mVirtualScreenWidth*2/10);
        mInYourGet10X   = (int)(mVirtualScreenWidth*2/10);
        mInMyGet05X     = (int)(mVirtualScreenWidth*4/10);
        mInYourGet05X   = (int)(mVirtualScreenWidth*4/10);
        mInMyGet01X     = (int)(mVirtualScreenWidth*6/10);
        mInYourGet01X   = (int)(mVirtualScreenWidth*6/10);
        mCenterY        = (int)(mVirtualScreenHeight/2) -
                (int)(mCardHeight/2) -
                (int)(mCardBigHeight/2);
        mFieldY         = (int)(mVirtualScreenHeight/2) -
                (int)(mCardHeight/2) +
                mCardHeight;
        mInMyHandY      = mVirtualScreenHeight - mCardBigHeight;
        mInYourHandY    = (int)(mCardBigHeight * (-0.9));
        mInMyGet20Y     =
                mVirtualScreenHeight - mCardHeight- mCardBigHeight;
        mInYourGet20Y   = (int)(mCardBigHeight * 0.1);
        mInMyGet10Y     =
                mVirtualScreenHeight - mCardHeight- mCardBigHeight;
        mInYourGet10Y   = (int)(mCardBigHeight * 0.1);
        mInMyGet05Y     =
                mVirtualScreenHeight - mCardHeight-mCardBigHeight;
        mInYourGet05Y   = (int)(mCardBigHeight * 0.1);
        mInMyGet01Y     =
                mVirtualScreenHeight - mCardHeight-mCardBigHeight;
        mInYourGet01Y   = (int)(mCardBigHeight * 0.1);
        mFieldDetailX[1] = mCenterX -mCardWidth - mCardGabX*2;
        mFieldDetailX[2] = mCenterX +mCardWidth + mCardGabX*2;
        mFieldDetailX[3] = mCenterX -mCardWidth - mCardGabX*2;
        mFieldDetailX[4] = mCenterX +mCardWidth + mCardGabX*2;
        mFieldDetailX[5] = mCenterX -mCardWidth*2 - mCardGabX*4;
        mFieldDetailX[6] = mCenterX +mCardWidth*2 + mCardGabX*4;
        mFieldDetailX[7] = mCenterX -mCardWidth*2 - mCardGabX*4;
        mFieldDetailX[8] = mCenterX +mCardWidth*2 + mCardGabX*4;
        mFieldDetailX[9] = mCenterX -mCardWidth*3 - mCardGabX*6;
        mFieldDetailX[10] = mCenterX +mCardWidth*3 + mCardGabX*6;
        mFieldDetailX[11] = mCenterX -mCardWidth*3 - mCardGabX*6;
        mFieldDetailX[12] = mCenterX +mCardWidth*3 + mCardGabX*6;
        mFieldDetailY[1] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[2] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[3] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        mFieldDetailY[4] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        mFieldDetailY[5] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[6] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[7] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        mFieldDetailY[8] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        mFieldDetailY[9] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[10] = mCenterY - (int)(mCardHeight/2) - mCardGabY;
        mFieldDetailY[11] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        mFieldDetailY[12] = mCenterY + (int)(mCardHeight/2) + mCardGabY;
        // ����ũ�⸦ ������ �Ϳ� ���� ���� ��ġ�� ��ȯ�Ѵ�.
        mCardWidth = (int)(mCardWidth/mCalcScreenWidth);
        mCardHeight = (int)(mCardHeight/mCalcScreenHeight);
        mCardBigWidth = (int)(mCardBigWidth/mCalcScreenWidth);
        mCardBigHeight = (int)(mCardBigHeight/mCalcScreenHeight);
        mCardGabX = (int)(mCardGabX/mCalcScreenWidth);
        mCardGabY = (int)(mCardGabY/mCalcScreenHeight);
        mCardGabSmallX = (int)(mCardGabSmallX/mCalcScreenWidth);
        mCardGabSmallY = (int)(mCardGabSmallY/mCalcScreenHeight);
        mCenterX = (int)(mCenterX/mCalcScreenWidth);
        mFieldX = (int)(mFieldX/mCalcScreenWidth);
        mInMyHandX = (int)(mInMyHandX/mCalcScreenWidth);
        mInYourHandX = (int)(mInYourHandX/mCalcScreenWidth);
        mInMyGet20X = (int)(mInMyGet20X/mCalcScreenWidth);
        mInMyGet10X = (int)(mInMyGet10X/mCalcScreenWidth);
        mInYourGet10X = (int)(mInYourGet10X/mCalcScreenWidth);
        mInMyGet05X = (int)(mInMyGet05X/mCalcScreenWidth);
        mInYourGet05X = (int)(mInYourGet05X/mCalcScreenWidth);
        mInMyGet01X = (int)(mInMyGet01X/mCalcScreenWidth);
        mInYourGet01X = (int)(mInYourGet01X/mCalcScreenWidth);
        mCenterY = (int)(mCenterY/mCalcScreenHeight);
        mFieldY = (int)(mFieldY/mCalcScreenHeight);
        mInMyHandY = (int)(mInMyHandY/mCalcScreenHeight);
        mInYourHandY = (int)(mInYourHandY/mCalcScreenHeight);
        mInMyGet20Y = (int)(mInMyGet20Y/mCalcScreenHeight);
        mInYourGet20Y = (int)(mInYourGet20Y/mCalcScreenHeight);
        mInMyGet10Y = (int)(mInMyGet10Y/mCalcScreenHeight);
        mInYourGet10Y = (int)(mInYourGet10Y/mCalcScreenHeight);
        mInMyGet05Y = (int)(mInMyGet05Y/mCalcScreenHeight);
        mInYourGet05Y = (int)(mInYourGet05Y/mCalcScreenHeight);
        mInMyGet01Y = (int)(mInMyGet01Y/mCalcScreenHeight);
        mInYourGet01Y = (int)(mInYourGet01Y/mCalcScreenHeight);
        mMySignJumsuX = mInMyGet20X;
        mYourSignJumsuX = mInYourGet20X;
        mMySignJumsuY = mCenterY + (int)(mCardHeight*2);
        mYourSignJumsuY = mCenterY - (int)(mCardHeight/2);
        for(int i=1; i<=12; i++)
        {
            mFieldDetailX[i] = (int)(mFieldDetailX[i]/mCalcScreenWidth);
        }
        for(int i=1; i<=12; i++)
        {
            mFieldDetailY[i] = (int)(mFieldDetailY[i]/mCalcScreenHeight);
        }
    }
    public int getX(int x) {
        return (int)( x * mScreenWidth/mVirtualScreenWidth);
    }
    public int getY(int y)
    {
        return (int)( y * mScreenHeight/mVirtualScreenHeight);
    }
}