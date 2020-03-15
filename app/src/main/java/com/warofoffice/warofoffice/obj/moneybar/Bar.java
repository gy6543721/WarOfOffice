package com.warofoffice.warofoffice.obj.moneyBar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;
import com.warofoffice.warofoffice.obj.boss.Money;

public class Bar extends WorldObj {


    private Bitmap moneyBitmap;

    private int currentBitmapPosition;

    private int width;
    private int height;
    private int offset;

    //private int currentMoney;

    public Bar(Context context, int x, int y){
        super(context);

        this.x = x;
        this.y = y;
        this.offset = 70;


        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight*800/1080;
        int bitmapHeight = MainActivity.screenWidth*offset/1920;

        moneyBitmap = bm.getBitmap(R.drawable.numbers, bitmapWidth, bitmapHeight);   //0~9的一維圖

        width = bitmapWidth/11;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint){
        int x0 = x;
        //加一個delay?

        for(int i=2;i>=0;i--){
            int pow = (int)Math.pow(10,i);
            if(Money.moneyNumber < ((pow==1)?0:pow)){
                currentBitmapPosition = 0;
            }else {
                currentBitmapPosition = (Money.moneyNumber / pow) % 10 + 1;
            }
            int bitmapLeft = width * (currentBitmapPosition%11);// 1
            int bitmapTop = height * (currentBitmapPosition/11);// 1
            int bitmapRight = width * (currentBitmapPosition%11 + 1);//2
            int bitmapBottom = height * (currentBitmapPosition/11 + 1);//2
            canvas.drawBitmap(moneyBitmap,
                    new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom ),
                    new Rect(x0, y, width + x0, height + y),
                    null);

            x0 += MainActivity.screenWidth*offset/1920;
        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

        return;
    }
}
