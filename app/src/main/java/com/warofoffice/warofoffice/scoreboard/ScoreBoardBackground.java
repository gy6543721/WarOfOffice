package com.warofoffice.warofoffice.scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.controller.GameController;

public class ScoreBoardBackground extends ScoreBoardObj{
    private static final int STATUS_STAND = 0;

    private Bitmap backgroundBitmap;  //先設定一個一開始的動作

    private Bitmap easterEgg;

    private int currentStatus;

    private int width;
    private int height;

    public ScoreBoardBackground(Context context, int x, int y){
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatus = STATUS_STAND;




        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight;
        int bitmapWidth = MainActivity.screenWidth;

        backgroundBitmap = bm.getBitmap(R.drawable.score_board, bitmapWidth, bitmapHeight);

        easterEgg = bm.getBitmap(R.drawable.easter_egg, bitmapWidth, bitmapHeight);;

        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        if((MainActivity.name.equals("林書聿")
                || MainActivity.name.equals("EdLin")
                || MainActivity.name.equals("LSYu"))
                && GameController.easterEgg){

            canvas.drawBitmap(easterEgg,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, width + x, height + y),
                    null);
        }else{
            canvas.drawBitmap(backgroundBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, width + x, height + y),
                    null);

        }
    }

}
