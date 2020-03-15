package com.warofoffice.warofoffice.timer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;

public class TimerBar extends WorldObj {

    private Bitmap timerBitmap;

    private int currentBitmapPosition;
    private int width;
    private int height;

    public TimerBar(Context context, int x, int y){
        super(context);
        this.x = x;
        this.y = y;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*1000/1920; // MainActivity.screenHeight*800/1080
        int bitmapHeight = MainActivity.screenHeight*100/1080; // MainActivity.screenWidth*70/1920

        currentBitmapPosition = 6;

        timerBitmap = bm.getBitmap(R.drawable.timer_numbers, bitmapWidth, bitmapHeight);   //0~9的一維圖

        width = bitmapWidth/10;
        height = bitmapHeight;

    }


    @Override
    public void onPaint(Canvas canvas, Paint paint) {

            //delayTime();
        if(TimerBar2.carry == true){
            currentBitmapPosition = currentBitmapPosition - 1;

            TimerBar2.carry = false;
        }

            int bitmapLeft = width * (currentBitmapPosition%10);// 1
            int bitmapTop = height * (currentBitmapPosition/10);// 1
            int bitmapRight = width * (currentBitmapPosition%10 + 1);//2
            int bitmapBottom = height * (currentBitmapPosition/10 + 1);//2

            canvas.drawBitmap(timerBitmap,
                    new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                    new Rect(x, y, width + x, height + y),
                    null);




    }


    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
