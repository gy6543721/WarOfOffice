package com.warofoffice.warofoffice.obj.boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.hintSound;


public class IncommingHint extends WorldObj{

    private static int HINT_DELAY = 7;

    private int currentBitmapPosition;

    private static Bitmap hintBitmap;

    private int width;
    private int height;

    public static boolean hintAppearHint;
    private int aniHint;

    private int delay;

    private int whichPicture; // 要載入哪張圖

    boolean check;  //判斷音效進來

    public IncommingHint(Context context, int x, int y, int choice){
        super(context);
        this.x = x;
        this.y = y;
        this.delay = 0;
        this.currentBitmapPosition = 0;
        this.hintAppearHint = true;
        this.aniHint = 0;

        // 控制音效播放
        this.check = true;


        // 依照傳入的choice載入相應的圖
        if (choice == 0) {

            this.whichPicture = R.drawable.salesman_hint;

        } else if (choice == 1) {

            this.whichPicture = R.drawable.hr_hint;

        } else if (choice == 2) {

            this.whichPicture = R.drawable.pm_hint;

        } else if (choice == 3) {

            this.whichPicture = R.drawable.vp_hint;

        } else {

            this.whichPicture = R.drawable.boss_hint;
        }

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*4800/1920; // 1200
        int bitmapHeight = MainActivity.screenHeight*1120/1920; // 280

        hintBitmap = bm.getBitmap(whichPicture, bitmapWidth, bitmapHeight);

        width = bitmapWidth/4;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        //如果要讓音效在畫的時候播放

        if(hintAppearHint == true) {

            update();

            int bitmapLeft = width * (currentBitmapPosition % 4);// 1
            int bitmapTop = height * (currentBitmapPosition / 4);// 1
            int bitmapRight = width * (currentBitmapPosition % 4 + 1);//2
            int bitmapBottom = height * (currentBitmapPosition / 4 + 1);//2

            canvas.drawBitmap(hintBitmap, new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                    new Rect(x, y, width + x, height + y),
                    null);
        }


    }

    public void update(){

        if (HINT_DELAY > delay) {
            delay++;
            return;
        } else {
            delay = 0;
        }

        this.currentBitmapPosition = (aniHint) % 4;

        aniHint++;

        if(aniHint % 8 == 0){

            // 播音效
            if(this.check == true){
                soundPool.play(hintSound,1,1,0,0,1);
                this.check = false;
            }
        }

        if(aniHint % 12 == 0){

            aniHint = 0;
            hintAppearHint = false;
            Boss.bossAttackAgain = true;

            this.check = true;
        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
