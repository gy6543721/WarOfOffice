package com.warofoffice.warofoffice.obj.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;

public class PlayerBar extends WorldObj {
    private Bitmap playerBarBitmap;

    private int width;
    private int height;



    public PlayerBar(Context context, int x, int y){
        super(context);
        this.x = x;
        this.y = y;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*335/1920; //300
        int bitmapHeight = MainActivity.screenHeight*70/1080;

        playerBarBitmap = bm.getBitmap(R.drawable.playerbar,bitmapWidth,bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;


    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {
        canvas.drawBitmap(playerBarBitmap,
                new Rect(0,0,width,height),
                new Rect(x,y,width + x,height + y ),
                null);

    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

        return;
    }
}
