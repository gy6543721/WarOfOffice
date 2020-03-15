package com.warofoffice.warofoffice.obj.boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.WorldObj;

public class BossBloodBar extends WorldObj {

    public static final int STATUS_STAND = 0;

    private Bitmap bloodBarBitmap;

    public static int currentStatus;

    private int width;
    private int height;

    public BossBloodBar(Context context, int x , int y){
        super(context);

        this.x =x;
        this.y =y;

        this.currentStatus = STATUS_STAND;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*250/1920;
        int bitmapHeight = MainActivity.screenHeight*45/1080;

        bloodBarBitmap = bm.getBitmap(R.drawable.blood_bar,bitmapWidth,bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;


    }
    @Override
    public void onPaint(Canvas canvas, Paint paint){
        canvas.drawBitmap(bloodBarBitmap,
                new Rect(0,0,width,height),
                new Rect(x,y,width+x,height+y),
                null);
    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

        return;
    }
}
