package com.warofoffice.warofoffice.menu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.GameView;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.TransparentInputBox;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.player.PlayerBlood;

import static com.warofoffice.warofoffice.GameView.player;
import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.buttonSound;

public class ButtonStart extends MenuObj implements OnTouchObject,SurfaceHolder.Callback {

    public static final int NO_BUTTON = 0;
    public static final int YES_BUTTON = 1;

    private Bitmap buttonBitmap;
    private Bitmap buttonBitmap1;

    public static int currentStatus;

    private int width;
    private int height;

    public ButtonStart(Context context, int x, int y){
        super(context);

        this.x = x;
        this.y = y;

        this.currentStatus = NO_BUTTON;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);
        int bitmapHeight = MainActivity.screenHeight*160/1080; //450
        int bitmapWidth = MainActivity.screenWidth*480/1920; //500

        buttonBitmap = bm.getBitmap(R.drawable.start_button_0, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

        buttonBitmap1 = bm.getBitmap(R.drawable.start_button_1, bitmapWidth, bitmapHeight);
        width = bitmapWidth;
        height = bitmapHeight;

    }
    @Override
    public void onPaint(Canvas canvas, Paint paint) {
        if(currentStatus == NO_BUTTON) {
            canvas.drawBitmap(buttonBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, width + x, height + y),
                    null);
        }
        if(currentStatus == YES_BUTTON){
            canvas.drawBitmap(buttonBitmap1,
                    new Rect(0, 0, width, height),
                    new Rect(x, y, width + x, height + y),
                    null);
        }

    }

    @Override
    public void touchEvent() {

        // 玩家血量若沒被打死，在重玩時回滿
        PlayerBlood.life = PlayerBlood.initialLife;
        PlayerBlood.overLife = PlayerBlood.life;

        soundPool.play(buttonSound,1,1,0,0,1);

        if(currentStatus == NO_BUTTON){
            currentStatus = YES_BUTTON;

        }else{

            currentStatus = NO_BUTTON;
        }
    }

    @Override
    public boolean isTouch(float x, float y) {
        if((this.x +  MainActivity.screenWidth*185/1920 ) > x || (this.x + this.width -  MainActivity.screenWidth*35/1920) < x){
            return false;
        }
        if((this.y +  MainActivity.screenHeight*35/1080 ) > y || (this.y + this.height -  MainActivity.screenHeight*15/1080) < y){
            return false;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
