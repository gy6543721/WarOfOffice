package com.warofoffice.warofoffice.obj.elevator;

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

public class Elevator2 extends WorldObj {

    public static int currentStatusE2;

    private static final int STATUS_STAND = 0;
    private static final int STATUS_RUN = 1;
    public static final int STATUS_RETURN = 2;
    public static final int STATUS_OVER = 3;

    private Bitmap ElevetorBitmap;


    public Elevator2(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatusE2 = STATUS_RUN;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight*900/1080;
        int bitmapWidth = MainActivity.screenWidth*425/1920;

        ElevetorBitmap = bm.getBitmap(R.drawable.elevator_door_small_right, bitmapWidth, bitmapHeight);

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        // 讓電梯在攻擊提示結束後才開
        if (IncommingHint.hintAppearHint == false) {

            if (this.x > MainActivity.screenWidth*1370/1920) {

                this.currentStatusE2 = STATUS_STAND;
                this.x = MainActivity.screenWidth*1370/1920;

            }else

            if (currentStatusE2 == STATUS_RUN) {
                this.x = this.x + 10;
                canvas.drawBitmap(ElevetorBitmap,
                        new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                        new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                        null);

            }else

            if (currentStatusE2 == STATUS_RETURN) {

                this.x = this.x - 10;
                canvas.drawBitmap(ElevetorBitmap,
                        new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                        new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                        null);

                if (this.x <= MainActivity.screenWidth*945/1920) {

                    this.currentStatusE2 = STATUS_OVER;

                    this.x = MainActivity.screenWidth*945/1920;

                    return;

                }
            }

            if (currentStatusE2 == STATUS_OVER) {

                this.x = MainActivity.screenWidth*945/1920;

                canvas.drawBitmap(ElevetorBitmap,
                        new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                        new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                        null);

            }

        } else {

            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth*1370/1920, ElevetorBitmap.getHeight() + y),
                    null);

        }
    }

    public void update () {


    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }

}