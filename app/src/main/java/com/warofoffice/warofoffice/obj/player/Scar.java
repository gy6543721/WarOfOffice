package com.warofoffice.warofoffice.obj.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.obj.WorldObj;

public class Scar extends WorldObj implements OnTouchObject {

    private static Bitmap scarBitmap;

    private int width;
    private int height;

    public static boolean scarAppear;

    public Scar(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;

        this.scarAppear = false;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth * 650 / 1920;
        int bitmapHeight = MainActivity.screenHeight * 523 / 1080;

        scarBitmap = bm.getBitmap(R.drawable.injury, bitmapWidth, bitmapHeight);


        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void touchEvent() {

    }

    @Override
    public boolean isTouch(float x, float y) {
        return false;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        if (scarAppear == true) {

            vibrate();

            canvas.drawBitmap(scarBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, 3*width + x, 3*height + y ),
                    null);

            MainActivity.handler.postDelayed(new Runnable(){

                @Override
                public void run() {

                    //過0.7秒後要做的事情
                    scarAppear = false;

                }}, 700);
//            scarAppear = false;
        }
    }

    public void vibrate () {


        //停0.01秒之後震動0.1秒(重覆三次)
        MainActivity.myVibrator.vibrate(new long[]{10, 100, 10, 100, 10, 100}, -1);

//        //震動3秒
//        MainActivity.myVibrator.vibrate(3000);

//        //震動0.1秒
//        MainActivity.myVibrator.vibrate(100);

    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
