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
import com.warofoffice.warofoffice.obj.boss.IncommingHint;
import com.warofoffice.warofoffice.obj.elevator.Elevator1;

public class TimerBar2 extends WorldObj {

    private Bitmap timerBitmap2;

    public static boolean carry = false;
    public static final int COUNTDOWN = 60;

    private static int TIMER_DELAY = 30;

    public static int countdown;
    private int currentBitmapPosition;
    private int width;
    private int height;
    private int delay;


    public TimerBar2(Context context, int x, int y){

        super(context);
        this.x = x;
        this.y = y;

        delay = 0;
        countdown = COUNTDOWN;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*1000/1920; // MainActivity.screenHeight*800/1080
        int bitmapHeight = MainActivity.screenHeight*100/1080; // MainActivity.screenWidth*70/1920

        currentBitmapPosition = 0;

        timerBitmap2 = bm.getBitmap(R.drawable.timer_numbers, bitmapWidth, bitmapHeight);   //0~9的一維圖

        width = bitmapWidth/10;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        int bitmapLeft = width * (currentBitmapPosition%10);// 1
        int bitmapTop = height * (currentBitmapPosition/10);// 1
        int bitmapRight = width * (currentBitmapPosition%10 + 1);//2
        int bitmapBottom = height * (currentBitmapPosition/10 + 1);//2

        canvas.drawBitmap(timerBitmap2,
                new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                new Rect(x, y, width + x, height + y),
                null);


        delayTime();

    }

    public void delayTime(){

        if(TIMER_DELAY > delay){
            delay++;
            return;
        }else {
            delay = 0;
        }

        if (IncommingHint.hintAppearHint == false) {

            if(currentBitmapPosition == 0){
                currentBitmapPosition = 10;
                carry = true;
            }

            currentBitmapPosition -= 1;
            countdown -= 1;

            if(countdown == 0){ //倒數結束後執行的事
                timerBitmap2 = null;

            }
        } else {

            currentBitmapPosition -= 0;
            countdown -= 0;
        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
