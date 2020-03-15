package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.OnTouchObject;

public class LeaderBoardExitButton extends LeaderBoardObj implements OnTouchObject {
    public static final int NO_BUTTON = 0;
    public static final int YES_BUTTON = 1;

    private Bitmap scoreBoardBitmap;
    private Bitmap scoreBoardBitmap1;

    public static int leaderBoardStatus;

    private int width;
    private int height;

    public LeaderBoardExitButton(Context context, int x , int y){
        super(context);

        this.x = x;
        this.y = y;

        leaderBoardStatus = NO_BUTTON;


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
        if(leaderBoardStatus == NO_BUTTON){
            canvas.drawBitmap(scoreBoardBitmap,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);
        }
        if(leaderBoardStatus == YES_BUTTON){
            canvas.drawBitmap(scoreBoardBitmap1,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);

            leaderBoardStatus = NO_BUTTON; //這裡是讓被按到的按鈕一畫完按鈕就變回沒按的狀態
            return;
        }
    }

    @Override
    public void touchEvent() {
        if(leaderBoardStatus == NO_BUTTON){

            LeaderBoardEL02.currentStatusLB2 = LeaderBoardEL02.LB2_STATUS_RETURN;
            LeaderBoardEL01.currentStatusLB1 = LeaderBoardEL01.LB1_STATUS_RETURN;

            leaderBoardStatus = YES_BUTTON;


        } else if(leaderBoardStatus == YES_BUTTON){
            leaderBoardStatus = NO_BUTTON;
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
