package com.warofoffice.warofoffice.obj.boss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.scoreboard.Score;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.obj.WorldObj;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.moneySound;

public class Money extends WorldObj implements OnTouchObject {
    private static final int DELAY_TIME = 5;
    
    private static Bitmap moneyBitmap;

    private int width;
    private int height;

    private int vx;
    private int vy;

    private int delay;

    public static int moneyNumber;  //用來計算錢的數量

    public Money(Context context, int x, int y){
        super(context);
        this.x = x;
        this.y = y;

        this.vy = -((int)(Math.random()*20)+10);
        this.vx = (int)(Math.random()*30)-15;

        this.delay = 0;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = 70;
        int bitmapHeight = 70;

        if(moneyBitmap == null) {
            moneyBitmap = bm.getBitmap(R.drawable.money, bitmapWidth, bitmapHeight);
        }

        width = bitmapWidth;
        height = bitmapHeight;

    }
    @Override
    public void onPaint(Canvas canvas, Paint paint){
        if(delay < DELAY_TIME){
            delay++;
        }
        canvas.drawBitmap(moneyBitmap,
                new Rect(0,0,width,height),
                new Rect(x,y,width+x,height+y),
                null);
        vy += 5;
        if(y < MainActivity.screenHeight - this.height - vy){
            this.y += vy;
        }else{
            this.y = MainActivity.screenHeight - this.height;
            this.vx = 0;
        }
        x += vx;
    }

    @Override
    public void touchEvent() {

        moneyNumber++; //錢++

        Score.score++;

        soundPool.play(moneySound,1,1,3,0,1);
    }

    @Override
    public boolean isTouch(float x, float y) {
        if(delay < DELAY_TIME)
            return false;
        if(this.x > x || this.x + this.width < x){
            return false;
        }
        if(this.y > y || this.y + this.height < y){
            return false;
        }
        return true;
    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {
        return;
    }

}
