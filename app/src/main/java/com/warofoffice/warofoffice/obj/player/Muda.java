package com.warofoffice.warofoffice.obj.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.warofoffice.warofoffice.BitmapManager;
import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.R;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.obj.WorldObj;
import com.warofoffice.warofoffice.obj.boss.Boss;
import com.warofoffice.warofoffice.obj.buttons.CellphoneButton;

public class Muda extends WorldObj implements OnTouchObject {

    private static final int DELAY_TIME = 30;

    private static Bitmap mudaBitmap;

    public static boolean mudaAppear;

    private int width;
    private int height;

    private int delay;

    private int aniObjectX;
    private int aniObjectY;

    public Muda(Context context, int x, int y) {
        super(context);

        this.x = x;
        this.y = y;

        this.mudaAppear = false;

        this.aniObjectX = 0;
        this.aniObjectY = 0;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenWidth * 700 / 1920;
        int bitmapHeight = MainActivity.screenHeight * 700 / 1080;

        mudaBitmap = bm.getBitmap(R.drawable.bill, bitmapWidth, bitmapHeight);


        width = bitmapWidth;
        height = bitmapHeight;
    }

    @Override
    public void touchEvent() {

    }

    @Override
    public boolean isTouch(float x, float y) {
        return false;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        if (delay < DELAY_TIME) {
            delay++;
        }

        if (mudaAppear == true && (x - aniObjectX < -200 || y + aniObjectY > 1280)) {

            Boss.bossAttackAgain = true; // 把boss再攻擊的判斷再開起來
//            CellphoneButton.cellphoneCount--; //可用次數減1
            mudaAppear = false;
            aniObjectX = 0;
            aniObjectY = 0;
            return;
        }

        if (mudaAppear == true) {

            aniObjectX += width/10;
            aniObjectY += height/10;

            canvas.drawBitmap(mudaBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x - aniObjectX, y - aniObjectY, x + aniObjectX, y + aniObjectY ),
                    null);

        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
