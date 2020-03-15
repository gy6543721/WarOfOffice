package com.warofoffice.warofoffice.scoreboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.OnTouchObject;

public class ExitButton extends ScoreBoardObj implements OnTouchObject {
    public static final int NO_SBUTTON = 0;
    public static final int YES_SBUTTON = 1;

    private Bitmap scoreBoardBitmap;
    private Bitmap scoreBoardBitmap1;

    public static int currentStatus;

    private int width;
    private int height;

    public ExitButton(Context context, int x , int y){
        super(context);

        this.x = x;
        this.y = y;

        currentStatus = NO_SBUTTON;


        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth*130/1080;
        int bitmapHeight = MainActivity.screenHeight*350/1920;

        scoreBoardBitmap = bm.getBitmap(R.drawable.enterkey, bitmapWidth, bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

        scoreBoardBitmap1 = bm.getBitmap(R.drawable.enterkey_2, bitmapWidth, bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

    }
    @Override
    public void onPaint(Canvas canvas, Paint paint){
        if(currentStatus == NO_SBUTTON){
            canvas.drawBitmap(scoreBoardBitmap,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);
        }
        if(currentStatus == YES_SBUTTON){
            canvas.drawBitmap(scoreBoardBitmap1,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);

            currentStatus = NO_SBUTTON; //這裡是讓被按到的按鈕一畫完按鈕就變回沒按的狀態
            return;
        }
    }

    @Override
    public void touchEvent() {
        if(currentStatus == NO_SBUTTON){

            ElevatorScore01.currentStatusES1 = ElevatorScore01.STATUS_RETURN;
            ElevatorScore02.currentStatusES2 = ElevatorScore02.STATUS_RETURN;
            currentStatus = YES_SBUTTON;



        }else{
            currentStatus = NO_SBUTTON;
        }
    }

    @Override
    public boolean isTouch(float x, float y) {
        if(this.x  > x || this.x + this.width  < x){
            return false;
        }
        if(this.y  > y || this.y + this.height  < y){
            return false;
        }
        return true;
    }
}
