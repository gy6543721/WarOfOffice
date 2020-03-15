package com.warofoffice.warofoffice.obj.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.obj.WorldObj;
import com.warofoffice.warofoffice.R;

public class Level extends WorldObj {
    public static final int LEVEL_1 = R.drawable.level1;
    public static final int LEVEL_2 = R.drawable.level2;
    public static final int LEVEL_3 = R.drawable.level3;
    public static final int LEVEL_4 = R.drawable.level4;
    public static final int LEVEL_5 = R.drawable.level5;

    private static final int STATUS_STAND = 0;

    private Bitmap backgroundBitmap;  //先設定一個一開始的動作

    public static int currentStatus;


    private int currentLevel;

    private int width;
    private int height;

    public Level(Context context, int x, int y, int speed, int level){
        super(context);

        this.x = x;
        this.y = y;


        this.currentStatus = STATUS_STAND;


        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight;
        int bitmapWidth = MainActivity.screenWidth;

        currentLevel = level;

        backgroundBitmap = bm.getBitmap(level, bitmapWidth, bitmapHeight);
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
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {
        BitmapManager bm = new BitmapManager(context);
        backgroundBitmap = bm.getBitmap(currentLevel, rightLimit, botLimit);
        width = rightLimit;
        height = botLimit;
    }
}
