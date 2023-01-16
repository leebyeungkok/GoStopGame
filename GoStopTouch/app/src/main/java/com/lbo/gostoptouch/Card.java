package com.lbo.gostoptouch;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Card
{
    public  int 		mCardNum;		//카드의고유번호
    private Bitmap 		mBitmap;		//이미지
    private Bitmap 		mBitmapClose;	//뒷면이미지
    private Bitmap 		mBitmapBig;		//큰이미지
    private Bitmap 		mBitmapBigClose;//뒷면큰이미지
    private Resources 	mRes;			//리소스
    private int 		mOpenBitmapId;	//이미지고유번호
    private int 		mCloseBitmapId;	//뒷면이미지고유번호
    public int 			mCardX;			//카드의 X좌표
    public int 			mCardY;			//카드의 Y좌표
    public int    		mTargetX;		//움직일카드의 X좌표
    public int 			mTargetY;		//움직일카드의 Y좌표
    public int 			mGapX;			//움직임갭X
    public int			mGapY;			//움직임갭Y
    private int 		mCardWidth;		//카드의폭
    private int 		mCardHeight;	//카드의높이
    private int 		mCardBigWidth;	//큰 카드의 폭
    private int 		mCardBigHeight;	//큰 카드의 높이


    public Card( int num, int width, int height, int widthbig, int heightbig,
                 Resources res, int OPEN_BITMAP_ID, int CLOSE_BITMAP_ID){
        mRes = res;
        mOpenBitmapId = OPEN_BITMAP_ID;
        mCloseBitmapId = CLOSE_BITMAP_ID;
        mCardNum = num;
        mCardWidth = width;
        mCardHeight = height;
        mCardBigWidth = widthbig;
        mCardBigHeight = heightbig;
        Bitmap mBitmaporg =
                BitmapFactory.decodeResource(mRes, mOpenBitmapId);
        mBitmap= Bitmap.createScaledBitmap(
                mBitmaporg, mCardWidth, mCardHeight, false);
        Bitmap mBitmapcloseorg =
                BitmapFactory.decodeResource(mRes, mCloseBitmapId);
        mBitmapClose= Bitmap.createScaledBitmap(
                mBitmapcloseorg, mCardWidth, mCardHeight, false);
        mBitmapBig= Bitmap.createScaledBitmap(
                mBitmaporg, mCardBigWidth, mCardBigHeight, false);
        mBitmapBigClose= Bitmap.createScaledBitmap(
                mBitmapcloseorg, mCardBigWidth, mCardBigHeight, false);
    }
    public void setImage(int card_num, int BITMAP_ID){
        mCardNum = card_num;
        Bitmap mBitmaporg =
                BitmapFactory.decodeResource(mRes, BITMAP_ID);
        mBitmap	= Bitmap.createScaledBitmap(
                mBitmaporg, mCardWidth, mCardHeight, false);
        mBitmapBig = Bitmap.createScaledBitmap(
                mBitmaporg, mCardBigWidth, mCardBigHeight, false);
    }
    // 카드를 연다
    public void openCard(){
        Bitmap mBitmaporg =
                BitmapFactory.decodeResource(mRes, mOpenBitmapId);
        mBitmap	= Bitmap.createScaledBitmap(
                mBitmaporg, mCardWidth, mCardHeight, false);
    }
    // 카드를 뒤집는다.
    public void closeCard(){
        Bitmap mBitmaporg =
                BitmapFactory.decodeResource(mRes, mCloseBitmapId);
        mBitmap = Bitmap.createScaledBitmap(
                mBitmaporg, mCardWidth, mCardHeight, false);
    }
    public  void move(int x, int y){
        mCardX = x;
        mCardY = y;
    }
    public boolean moveTo(int x, int y){
        int currCount=30;
        // 30단계로 이동좌표를 갖는다.
        int[] step_x = new int[currCount];
        int[] step_y = new int[currCount];
        // 현재위치 대입
        step_x[0]= mCardX;
        step_y[0]= mCardY;
        // 최종위치 대입
        step_x[currCount-1]= x;
        step_y[currCount-1]= y;
        float gab_value_x = Math.abs((int)((mCardX-x)));
        float gab_value_y = Math.abs((int)((mCardY-y)));
        // 중간처리
        for(int i=1; i< currCount-1; i++){
            if(x>= mCardX){
                step_x[i]= mCardX + (int)( gab_value_x * i/currCount);
            }else if(x< mCardX){
                step_x[i]= mCardX - (int)( gab_value_x * i/currCount);
            }
            if(y>= mCardY){
                step_y[i]= mCardY + (int)( gab_value_y * i/currCount);
            }else if(y< mCardY)	{
                step_y[i]= mCardY - (int)( gab_value_y * i/currCount);
            }
        }
        for(int i=1; i<=currCount-1;i++){
            try	{
                int tempSpeed = (int)((currCount-i)/3);
                if(tempSpeed == 0)
                    tempSpeed = 1;
                Thread.sleep(tempSpeed);
            }
            catch(Exception exTemp){
            }
            mCardX = step_x[i];
            mCardY = step_y[i];
        }
        mCardX = x;
        mCardY = y;
        return true;
    }
    // 선택되었을 경우
    public boolean isSelected(int x, int y){
        boolean is_selected = false;
        if( (x > mCardX && x < mCardX + mCardBigWidth) &&
                (y > mCardY && y < mCardY + mCardBigHeight)){
            is_selected = true;
        }
        return is_selected;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(mBitmap,mCardX, mCardY, null);
    }
    public void drawClose(Canvas canvas){
        canvas.drawBitmap(mBitmapClose,mCardX, mCardY, null);
    }
    public void drawBig(Canvas canvas){
        canvas.drawBitmap(mBitmapBig,mCardX, mCardY, null);
    }
    public void drawBigClose(Canvas canvas){
        canvas.drawBitmap(mBitmapBigClose,mCardX, mCardY, null);
    }
    public void destroy(){
        try{
            if(mBitmap != null){
                mBitmap.recycle();
            }
        }
        catch(Exception ex){}
        try{
            if(mBitmapClose != null){
                mBitmapClose.recycle();
            }
        }
        catch(Exception ex){}
        try{
            if(mBitmapBig != null){
                mBitmapBig.recycle();
            }
        }
        catch(Exception ex){}
        try{
            if(mBitmapBigClose != null){
                mBitmapBigClose.recycle();
            }
        }
        catch(Exception ex){}
    }
}

