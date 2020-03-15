package com.warofoffice.warofoffice.tutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.obj.boss.AttackObject;

public class TutorialBackground extends TutorialObj implements OnTouchObject {

    private Bitmap backgroundBitmap;  //先設定一個一開始的動作
    private Bitmap tutorial01;
    private Bitmap tutorial02;
    private Bitmap tutorial03;

    public static int switchPicture; //換圖用

    private int width;
    private int height;

    @Override
    public void touchEvent() {

        if (switchPicture < 3 && switchPicture >= 0) {

            this.switchPicture++;

        } else {

            this.switchPicture = 0;
        }
    }

    @Override
    public boolean isTouch(float x, float y) {

        if(this.x > x || this.x < x){
            return false;
        }
        if(this.y > y || this.y < y){
            return false;
        }
        return true;
    }


    public TutorialBackground(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.switchPicture = 0;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight;
        int bitmapWidth = MainActivity.screenWidth;

        backgroundBitmap = tutorial01 = bm.getBitmap(R.drawable.tutorial_01, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

        tutorial02 = bm.getBitmap(R.drawable.tutorial_02, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

        tutorial03 = bm.getBitmap(R.drawable.tutorial_03, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        update();

        canvas.drawBitmap(backgroundBitmap,
                new Rect(0, 0, width, height),
                new Rect(x, y, width + x, height + y),
                null);
    }

    public void update() {

        switch (switchPicture){

            case 0:
                backgroundBitmap = tutorial01;
                break;

            case 1:
                backgroundBitmap = tutorial02;
                break;

            case 2:
                backgroundBitmap = tutorial03;
                break;

            default:
                backgroundBitmap = tutorial01;
                break;

        }

    }
}
