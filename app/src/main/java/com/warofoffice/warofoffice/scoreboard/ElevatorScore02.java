package com.warofoffice.warofoffice.scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;

public class ElevatorScore02 extends ScoreBoardObj {

    public static int currentStatusES2;

    public static final int STATUS_STAND = 0;
    public static final int STATUS_RUN = 1;
    public static final int STATUS_RETURN = 2;
    public static final int STATUS_OVER = 3;

    private Bitmap ElevetorBitmap;


    public ElevatorScore02(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatusES2 = STATUS_RUN;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight*900/1080;
        int bitmapWidth = MainActivity.screenWidth*425/1920;

        ElevetorBitmap = bm.getBitmap(R.drawable.elevator_door_small_right, bitmapWidth, bitmapHeight);

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {


        if (this.x > MainActivity.screenWidth*1370/1920) {

            this.currentStatusES2 = STATUS_STAND;
            this.x = MainActivity.screenWidth*1370/1920;

        }else

        if (currentStatusES2 == STATUS_RUN) {
            this.x = this.x + 10;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                    null);

        }else

        if (currentStatusES2 == STATUS_RETURN) {

            this.x = this.x - 10;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                    null);

            if (this.x <= MainActivity.screenWidth*945/1920) {

                this.currentStatusES2 = STATUS_OVER;

                this.x = MainActivity.screenWidth*945/1920;

                return;

            }
        }

        if (currentStatusES2 == STATUS_OVER) {

            this.x = MainActivity.screenWidth*945/1920;

            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                    null);

        }


    }

    public void update () {


    }

}