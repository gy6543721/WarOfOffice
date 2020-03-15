package com.warofoffice.warofoffice.obj.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;


public class CellphoneCountDown extends WorldObj {

    public static boolean countdownAppear;

    private Bitmap countdownBMP;

    private static int TIMER_DELAY = 30;

    private int currentBitmapPosition;
    private int width;
    private int height;

    private int delay;

    public CellphoneCountDown(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.delay = 0;

        this.countdownAppear = false;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*1400/1920;
        int bitmapHeight = MainActivity.screenHeight*180/1080;

        currentBitmapPosition = 0;

        countdownBMP = bm.getBitmap(R.drawable.phonecall_countdown, bitmapWidth, bitmapHeight);   //0~9的一維圖

        width = bitmapWidth/6;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        if (this.countdownAppear == true) {

            if ( currentBitmapPosition >= 5) {

                this.countdownAppear = false;
                currentBitmapPosition = 0;
            }
            update();

            int bitmapLeft = width * (currentBitmapPosition%6);// 1
            int bitmapTop = height * (currentBitmapPosition/6);// 1
            int bitmapRight = width * (currentBitmapPosition%6 + 1);//2
            int bitmapBottom = height * (currentBitmapPosition/6 + 1);//2

            canvas.drawBitmap(countdownBMP,
                    new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                    new Rect(x, y, width + x, height + y),
                    null);

        } else {

            currentBitmapPosition = 0;
        }
    }

    public void update () {

        if(TIMER_DELAY > delay){
            delay++;
            return;
        }else {
            delay = 0;
        }

        currentBitmapPosition += 1;


    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
