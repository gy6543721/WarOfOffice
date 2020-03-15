package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;

public class LeaderBoardEL01 extends LeaderBoardObj {

    public static int currentStatusLB1;

    public static final int LB1_STATUS_STAND = 0;
    public static final int LB1_STATUS_RUN = 1;
    public static final int LB1_STATUS_RETURN = 2;
    public static final int LB1_STATUS_OVER = 3;

    private Bitmap ElevetorBitmap;

    public LeaderBoardEL01(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatusLB1 = LB1_STATUS_RUN;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight; //900
        int bitmapWidth = MainActivity.screenWidth/2; //425

        ElevetorBitmap = bm.getBitmap(R.drawable.leaderboard_elevator_left, bitmapWidth, bitmapHeight);
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {



        if(this.x < 0){

            this.x = 0;

            this.currentStatusLB1 = LB1_STATUS_STAND;

        }

        if (currentStatusLB1 == LB1_STATUS_RUN) {
            this.x = this.x - 10;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(0, y, this.x, ElevetorBitmap.getHeight() + y),
                    null); //left = 500

        }

        if (currentStatusLB1 == LB1_STATUS_RETURN) {

            this.x = this.x + 10;
            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(0, y, this.x, ElevetorBitmap.getHeight() + y),
                    null);

            if (this.x  >= MainActivity.screenWidth*960/1920) {

                this.x = MainActivity.screenWidth*960/1920;

                this.currentStatusLB1 = LB1_STATUS_OVER;

                return;

            }
        }

        if (currentStatusLB1 == LB1_STATUS_OVER) {

            this.x = MainActivity.screenWidth*960/1920;

            canvas.drawBitmap(ElevetorBitmap,
                    new Rect(0, 0, ElevetorBitmap.getWidth(), ElevetorBitmap.getHeight()),
                    new Rect(0, y, this.x, ElevetorBitmap.getHeight() + y),
                    null); //left = 500

        }

    }

}