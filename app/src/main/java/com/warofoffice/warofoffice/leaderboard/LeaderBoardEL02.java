package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;

public class LeaderBoardEL02 extends LeaderBoardObj {

    public static int currentStatusLB2;

    public static final int LB2_STATUS_STAND = 0;
    public static final int LB2_STATUS_RUN = 1;
    public static final int LB2_STATUS_RETURN = 2;
    public static final int LB2_STATUS_OVER = 3;

    private Bitmap ElevetorBitmap;


    public LeaderBoardEL02(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatusLB2 = LB2_STATUS_RUN;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight;
        int bitmapWidth = MainActivity.screenWidth/2;

        ElevetorBitmap = bm.getBitmap(R.drawable.leaderboard_elevator_right, bitmapWidth, bitmapHeight);

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {


        if (this.x > MainActivity.screenWidth) {

            this.currentStatusLB2 = LB2_STATUS_STAND;
            this.x = MainActivity.screenWidth;

        }

        if (currentStatusLB2 == LB2_STATUS_RUN) {
            this.x = this.x + 20;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth, ElevetorBitmap.getHeight() + y),
                    null);

        }

        if (currentStatusLB2 == LB2_STATUS_RETURN) {

            this.x = this.x - 20;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth, ElevetorBitmap.getHeight() + y),
                    null);

            if (this.x <= MainActivity.screenWidth*960/1920) {

                this.currentStatusLB2 = LB2_STATUS_OVER;

                this.x = MainActivity.screenWidth*960/1920;

                return;

            }
        }

        if (currentStatusLB2 == LB2_STATUS_OVER) {

            this.x = MainActivity.screenWidth*960/1920;

            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(x, y, MainActivity.screenWidth, ElevetorBitmap.getHeight() + y),
                    null); //left = 500

        }

    }

}