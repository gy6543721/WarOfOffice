package com.warofoffice.warofoffice.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.R;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.buttonSound;


public class ButtonTutorial extends MenuObj implements OnTouchObject {

    public static final int N0_BUTTON = 0;          //設按鈕一開始的位置
    public static final int YES_BUTTON = 1;

    private Bitmap buttonBitmap;    //兩張不一樣的圖
    private Bitmap buttonBitmap1;

    public static int currentStatus;    //用來判斷當前的狀態是哪一個

    private int width;      //寬
    private int height;     //高

    public ButtonTutorial(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatus = N0_BUTTON;     //決定狀態是甚麼

        BitmapManager bm = new BitmapManager(context);

        int bitmapHeight = MainActivity.screenHeight*160/1080;
        int bitmapWidth = MainActivity.screenWidth*480/1920;

        buttonBitmap = bm.getBitmap(R.drawable.tutorial_button_0, bitmapWidth,bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

        buttonBitmap1 = bm.getBitmap(R.drawable.tutorial_button_1, bitmapWidth,bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

    }
    @Override
    public void onPaint(Canvas canvas, Paint paint){
        if(currentStatus == N0_BUTTON){
            canvas.drawBitmap(buttonBitmap,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);
        }
        if(currentStatus == YES_BUTTON){
            canvas.drawBitmap(buttonBitmap1,
                    new Rect(0,0,width,height),
                    new Rect(x,y,width+x,height+y),
                    null);
        }
    }

    @Override
    public void touchEvent() {

        soundPool.play(buttonSound,1,1,0,0,1);

        if(currentStatus == N0_BUTTON){
            currentStatus = YES_BUTTON;
        }else{
            currentStatus = N0_BUTTON;
        }
    }

    @Override
    public boolean isTouch(float x, float y) {
        if((this.x +  MainActivity.screenWidth*150/1920 ) > x || (this.x + this.width -  MainActivity.screenWidth*35/1920) < x){
            return false;
        }
        if((this.y + MainActivity.screenHeight*35/1080) > y || (this.y+this.height - MainActivity.screenHeight*15/1080) < y){
            return false;
        }
        return true;
    }
}
