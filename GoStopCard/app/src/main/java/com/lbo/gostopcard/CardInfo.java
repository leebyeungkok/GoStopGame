package com.lbo.gostopcard;


public  class CardInfo {

    public static int[][] mCard = new int[51][4];
    // 상수관리
    public static int C_PIC = 0;
    public static int C_KIND = 1;
    public static int C_MONTH = 2;
    public static int C_JUM_1 = 3;

    public static void init()
    {
        // 이미지설정
        mCard[0][C_PIC]=R.drawable.c00_0;
        mCard[1][C_PIC]=R.drawable.c01_1;
        mCard[2][C_PIC]=R.drawable.c01_2;
        mCard[3][C_PIC]=R.drawable.c01_3;
        mCard[4][C_PIC]=R.drawable.c01_4;
        mCard[5][C_PIC]=R.drawable.c02_1;
        mCard[6][C_PIC]=R.drawable.c02_2;
        mCard[7][C_PIC]=R.drawable.c02_3;
        mCard[8][C_PIC]=R.drawable.c02_4;
        mCard[9][C_PIC]=R.drawable.c03_1;
        mCard[10][C_PIC]=R.drawable.c03_2;
        mCard[11][C_PIC]=R.drawable.c03_3;
        mCard[12][C_PIC]=R.drawable.c03_4;
        mCard[13][C_PIC]=R.drawable.c04_1;
        mCard[14][C_PIC]=R.drawable.c04_2;
        mCard[15][C_PIC]=R.drawable.c04_3;
        mCard[16][C_PIC]=R.drawable.c04_4;
        mCard[17][C_PIC]=R.drawable.c05_1;
        mCard[18][C_PIC]=R.drawable.c05_2;
        mCard[19][C_PIC]=R.drawable.c05_3;
        mCard[20][C_PIC]=R.drawable.c05_4;
        mCard[21][C_PIC]=R.drawable.c06_1;
        mCard[22][C_PIC]=R.drawable.c06_2;
        mCard[23][C_PIC]=R.drawable.c06_3;
        mCard[24][C_PIC]=R.drawable.c06_4;
        mCard[25][C_PIC]=R.drawable.c07_1;
        mCard[26][C_PIC]=R.drawable.c07_2;
        mCard[27][C_PIC]=R.drawable.c07_3;
        mCard[28][C_PIC]=R.drawable.c07_4;
        mCard[29][C_PIC]=R.drawable.c08_1;
        mCard[30][C_PIC]=R.drawable.c08_2;
        mCard[31][C_PIC]=R.drawable.c08_3;
        mCard[32][C_PIC]=R.drawable.c08_4;
        mCard[33][C_PIC]=R.drawable.c09_1;
        mCard[34][C_PIC]=R.drawable.c09_2;
        mCard[35][C_PIC]=R.drawable.c09_3;
        mCard[36][C_PIC]=R.drawable.c09_4;
        mCard[37][C_PIC]=R.drawable.c10_1;
        mCard[38][C_PIC]=R.drawable.c10_2;
        mCard[39][C_PIC]=R.drawable.c10_3;
        mCard[40][C_PIC]=R.drawable.c10_4;
        mCard[41][C_PIC]=R.drawable.c11_1;
        mCard[42][C_PIC]=R.drawable.c11_2;
        mCard[43][C_PIC]=R.drawable.c11_3;
        mCard[44][C_PIC]=R.drawable.c11_4;
        mCard[45][C_PIC]=R.drawable.c12_1;
        mCard[46][C_PIC]=R.drawable.c12_2;
        mCard[47][C_PIC]=R.drawable.c12_3;
        mCard[48][C_PIC]=R.drawable.c12_4;

        // 카드별 점수분리
        mCard[0][C_KIND] =0;
        mCard[1][C_KIND] =20;	//1자 20 1광
        mCard[2][C_KIND] =5;	//1자 5 홍단
        mCard[3][C_KIND] =1;
        mCard[4][C_KIND] =1;
        mCard[5][C_KIND] =10;
        mCard[6][C_KIND] =5;
        mCard[7][C_KIND] =1;
        mCard[8][C_KIND] =1;
        mCard[9][C_KIND] =20;
        mCard[10][C_KIND]=5;
        mCard[11][C_KIND]=1;
        mCard[12][C_KIND]=1;
        mCard[13][C_KIND]=10;
        mCard[14][C_KIND]=5;
        mCard[15][C_KIND]=1;
        mCard[16][C_KIND]=1;
        mCard[17][C_KIND]=10;
        mCard[18][C_KIND]=5;
        mCard[19][C_KIND]=1;
        mCard[20][C_KIND]=1;
        mCard[21][C_KIND]=10;
        mCard[22][C_KIND]=5;
        mCard[23][C_KIND]=1;
        mCard[24][C_KIND]=1;
        mCard[25][C_KIND]=10;
        mCard[26][C_KIND]=5;
        mCard[27][C_KIND]=1;
        mCard[28][C_KIND]=1;
        mCard[29][C_KIND]=20;
        mCard[30][C_KIND]=10;
        mCard[31][C_KIND]=1;
        mCard[32][C_KIND]=1;
        mCard[33][C_KIND]=10;
        mCard[34][C_KIND]=5;
        mCard[35][C_KIND]=1;
        mCard[36][C_KIND]=1;
        mCard[37][C_KIND]=10;
        mCard[38][C_KIND]=5;
        mCard[39][C_KIND]=1;
        mCard[40][C_KIND]=1;
        mCard[41][C_KIND]=20;
        mCard[42][C_KIND]=1;
        mCard[43][C_KIND]=1;
        mCard[44][C_KIND]=1;
        mCard[45][C_KIND]=20;	// 12자 20 비광
        mCard[46][C_KIND]=10;
        mCard[47][C_KIND]=5;
        mCard[48][C_KIND]=1;	//12자 1 비쌍피

        // 카드별 월별분리
        mCard[0][C_MONTH] =0;   // 폭탄
        mCard[1][C_MONTH] =1;   // 1자
        mCard[2][C_MONTH] =1;
        mCard[3][C_MONTH] =1;
        mCard[4][C_MONTH] =1;
        mCard[5][C_MONTH] =2;
        mCard[6][C_MONTH] =2;
        mCard[7][C_MONTH] =2;
        mCard[8][C_MONTH] =2;
        mCard[9][C_MONTH] =3;
        mCard[10][C_MONTH]=3;
        mCard[11][C_MONTH]=3;
        mCard[12][C_MONTH]=3;
        mCard[13][C_MONTH]=4;
        mCard[14][C_MONTH]=4;
        mCard[15][C_MONTH]=4;
        mCard[16][C_MONTH]=4;
        mCard[17][C_MONTH]=5;
        mCard[18][C_MONTH]=5;
        mCard[19][C_MONTH]=5;
        mCard[20][C_MONTH]=5;
        mCard[21][C_MONTH]=6;
        mCard[22][C_MONTH]=6;
        mCard[23][C_MONTH]=6;
        mCard[24][C_MONTH]=6;
        mCard[25][C_MONTH]=7;
        mCard[26][C_MONTH]=7;
        mCard[27][C_MONTH]=7;
        mCard[28][C_MONTH]=7;
        mCard[29][C_MONTH]=8;
        mCard[30][C_MONTH]=8;
        mCard[31][C_MONTH]=8;
        mCard[32][C_MONTH]=8;
        mCard[33][C_MONTH]=9;
        mCard[34][C_MONTH]=9;
        mCard[35][C_MONTH]=9;
        mCard[36][C_MONTH]=9;
        mCard[37][C_MONTH]=10;
        mCard[38][C_MONTH]=10;
        mCard[39][C_MONTH]=10;
        mCard[40][C_MONTH]=10;
        mCard[41][C_MONTH]=11;
        mCard[42][C_MONTH]=11;
        mCard[43][C_MONTH]=11;
        mCard[44][C_MONTH]=11;
        mCard[45][C_MONTH]=12;
        mCard[46][C_MONTH]=12;
        mCard[47][C_MONTH]=12;
        mCard[48][C_MONTH]=12;

        // 각 카드별 피 분리계산
        mCard[0][C_JUM_1] =0;   //폭탄
        mCard[1][C_JUM_1] =0;   // 1자 20
        mCard[2][C_JUM_1] =0;   // 1자 10
        mCard[3][C_JUM_1] =1;   // 1자 피
        mCard[4][C_JUM_1] =1;   // 1자 피
        mCard[5][C_JUM_1] =0;
        mCard[6][C_JUM_1] =0;
        mCard[7][C_JUM_1] =1;
        mCard[8][C_JUM_1] =1;
        mCard[9][C_JUM_1] =0;
        mCard[10][C_JUM_1]=0;
        mCard[11][C_JUM_1]=1;
        mCard[12][C_JUM_1]=1;
        mCard[13][C_JUM_1]=0;
        mCard[14][C_JUM_1]=0;
        mCard[15][C_JUM_1]=1;
        mCard[16][C_JUM_1]=1;
        mCard[17][C_JUM_1]=0;
        mCard[18][C_JUM_1]=0;
        mCard[19][C_JUM_1]=1;
        mCard[20][C_JUM_1]=1;
        mCard[21][C_JUM_1]=0;
        mCard[22][C_JUM_1]=0;
        mCard[23][C_JUM_1]=1;
        mCard[24][C_JUM_1]=1;
        mCard[25][C_JUM_1]=0;
        mCard[26][C_JUM_1]=0;
        mCard[27][C_JUM_1]=1;
        mCard[28][C_JUM_1]=1;
        mCard[29][C_JUM_1]=0;
        mCard[30][C_JUM_1]=0;
        mCard[31][C_JUM_1]=1;
        mCard[32][C_JUM_1]=1;
        mCard[33][C_JUM_1]=2;
        mCard[34][C_JUM_1]=0;
        mCard[35][C_JUM_1]=1;
        mCard[36][C_JUM_1]=1;
        mCard[37][C_JUM_1]=0;
        mCard[38][C_JUM_1]=0;
        mCard[39][C_JUM_1]=1;
        mCard[40][C_JUM_1]=1;
        mCard[41][C_JUM_1]=0;
        mCard[42][C_JUM_1]=2;
        mCard[43][C_JUM_1]=1;
        mCard[44][C_JUM_1]=1;
        mCard[45][C_JUM_1]=0;
        mCard[46][C_JUM_1]=0;
        mCard[47][C_JUM_1]=0;
        mCard[48][C_JUM_1]=2;
    }
    // 카드의 index값을 넣으면 몇자인지 리턴
    public static int getCardMonth(int i){
        return mCard[i][C_MONTH];
    }
    // 카드의 index값을 넣으면 리소스를 반환
    public static int getCardRes(int i){
        return mCard[i][C_PIC];
    }
    // 카드의 index값을 넣으면 20점(광)인지 반환
    public static int getCard20Cls(int i){
        int returnValue = 0;
        if( mCard[i][C_MONTH] == 20){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 10점인지 반환
    public static int getCard10Cls(int i){
        int returnValue = 0;
        if( mCard[i][C_MONTH] == 10){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 5점인지 반환
    public static int getCard5Cls(int i){
        int returnValue = 0;
        if( mCard[i][C_MONTH] == 5){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 1점인지 반환
    public static int getCard1Cls(int i){
        int returnValue = 0;
        if( mCard[i][C_MONTH] == 1){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 고도리인지 반환
    public static int getCardGodoriCls(int i){
        int returnValue = 0;
        if( i==5 ||
                i==13 ||
                i==30){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 홍단인지 반환
    public static int getCardHongdanCls(int i){
        int returnValue = 0;
        if( i==2 ||
                i==6 ||
                i==10)
        {
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 청단인지 반환
    public static int getCardChungdanCls(int i){
        int returnValue = 0;
        if( i==22 ||
                i==34 ||
                i==38){
            returnValue = 1;
        }
        return returnValue;
    }
    // 카드의 index값을 넣으면 띠단인지 반환
    public static int getCardTidanCls(int i){
        int returnValue = 0;
        if( i==14 ||
                i==18 ||
                i==26)
        {
            returnValue = 1;
        }
        return returnValue;
    }
    // // 카드의 index값을 넣으면 쌍피인지 반환
    public static int getCardDouble1Cls(int i)	{
        int returnValue = 0;
        if( i==33 ||
                i==42 ||
                i==48)
        {
            returnValue = 1;
        }
        return returnValue;
    }

    // 광이 있는 월인지 반환
    public static int getField20Cls(int i)	{
        int returnValue = 0;
        if( i==1 ||
                i==3 ||
                i==8 ||
                i==11 ||
                i==12 )	{
            returnValue = 1;
        }
        return returnValue;
    }
    // 고도리가 있는 월인지 반환
    public static int getFieldGodoriCls(int i)	{
        int returnValue = 0;
        if( i==2 ||
                i==4 ||
                i==8){
            returnValue = 1;
        }
        return returnValue;
    }
    // 홍단이 있는 월인지 반환
    public static int getFieldHongdanCls(int i){
        int returnValue = 0;
        if( i==1 ||
                i==2 ||
                i==3)
        {
            returnValue = 1;
        }
        return returnValue;
    }
    // 청단이 있는 월인지 반환
    public static int getFieldChungdanCls(int i){
        int returnValue = 0;
        if( i==6 ||
                i==9 ||
                i==10){
            returnValue = 1;
        }
        return returnValue;
    }
    // 띠단이 있는 월인지 반환
    public static int getFieldTidanCls(int i){
        int returnValue = 0;
        if( i==4 ||
                i==5 ||
                i==7){
            returnValue = 1;
        }
        return returnValue;
    }
    // 쌍피가 있는 월인지 반환
    public static int getFieldDouble1Cls(int i){
        int returnValue = 0;
        if( i==9 ||
                i==11 ||
                i==12){
            returnValue = 1;
        }
        return returnValue;
    }
}
