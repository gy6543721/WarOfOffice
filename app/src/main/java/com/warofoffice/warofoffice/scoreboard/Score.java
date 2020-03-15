package com.warofoffice.warofoffice.scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;

public class Score extends ScoreBoardObj{
    public static int score;

    private Bitmap moneyBitmap;

    private int currentBitmapPosition;

    private int width;
    private int height;
    private int offset;

    //private int currentMoney;

    public Score(Context context, int x, int y){
        super(context);

        this.x = x;
        this.y = y;
        this.offset = 70;

        score = 0;
        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight*800/1080;
        int bitmapHeight = MainActivity.screenWidth*offset/1920;

        moneyBitmap = bm.getBitmap(R.drawable.new_numbers, bitmapWidth, bitmapHeight);   //0~9的一維圖

        width = bitmapWidth/11;
        height = bitmapHeight;

    }

    @Override
    public void onPaint(Canvas canvas, Paint paint){
        int x0 = x;
        //加一個delay?

        for(int i=5;i>=0;i--){
            int pow = (int)Math.pow(10,i);

            currentBitmapPosition = (score / pow) % 10 + 1;

            int bitmapLeft = width * (currentBitmapPosition%11);// 1
            int bitmapTop = height * (currentBitmapPosition/11);// 1
            int bitmapRight = width * (currentBitmapPosition%11 + 1);//2
            int bitmapBottom = height * (currentBitmapPosition/11 + 1);//2
            canvas.drawBitmap(moneyBitmap,
                    new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom ),
                    new Rect(x0, y, width + x0, height + y),
                    null);

            x0 += MainActivity.screenWidth*offset/1920;
        }
    }
}
