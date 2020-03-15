package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;

public class LeaderBoardOutfit extends LeaderBoardObj {
    private static final int STATUS_STAND = 0;

    private Bitmap backgroundBitmap;  //先設定一個一開始的動作

    private int currentStatus;


    private int width;
    private int height;


    public LeaderBoardOutfit(Context context, int x, int y, int speed){
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatus = STATUS_STAND;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight;
        int bitmapWidth = MainActivity.screenWidth;

        backgroundBitmap = bm.getBitmap(R.drawable.leaderboard_outfit, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        canvas.drawBitmap(backgroundBitmap,
                new Rect(0, 0, width, height),
                new Rect(x, y, width + x, height + y),
                null);


    }
}
