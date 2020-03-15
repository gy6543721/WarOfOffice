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
import com.warofoffice.warofoffice.obj.player.PlayerBlood;
import com.warofoffice.warofoffice.obj.player.Scar;

public class MultiObject extends WorldObj implements OnTouchObject {

    private static final int DELAY_TIME = 30;

    private static Bitmap multiBitmap;

    public static boolean multiAppear; // 判斷東西是否出現
    public static int multiDamage; // 東西給的傷害

    private int width;
    private int height;

    private int delay;

    private int aniObjectX;
    private int aniObjectY;

    private int initialX;
    private int initialY;

    private int multiObjectSpeed; //控制多重物件左右移動
    private int multiObjectUpSpeed; //控制多重物件的上下速度

    private int choice; //控制物件移動的方向

    public MultiObject(Context context, int x, int y, int damage, int choice) {
        super(context);
        this.x = x;
        this.y = y;

        this.initialX = x;
        this.initialY = y; //儲存物件的初始位置

        this.delay = 0;
        this.multiAppear = false;

        this.aniObjectX = 0;
        this.aniObjectY = 0;

        this.multiDamage = damage;

        this.choice = choice;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight * 300 / 1080;
        int bitmapHeight = MainActivity.screenHeight * 300 / 1080;

        multiBitmap = bm.getBitmap(R.drawable.key, bitmapWidth, bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void touchEvent() {

        Score.score += 100;

//        multiAppear = false;

        Boss.bossAttackAgain = true; //讓老闆可以再度攻擊
        aniObjectX = 0;
        aniObjectY = 0;
        Score.score += 1000;

    }

    @Override
    public boolean isTouch(float x, float y) {


        if (this.x - aniObjectX > x || this.x + width + aniObjectX < x) {
            return false;
        }

        if (this.y - aniObjectY > y || this.y + height + aniObjectY < y) {
            return false;
        }
        return true;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        Scar.scarAppear = false;

        update();
//        move();

        if (delay < DELAY_TIME) {
            delay++;
        }

        if (x + multiObjectSpeed - aniObjectX < 0 || x + multiObjectSpeed + aniObjectX > 1920) {

            // 震動
            MainActivity.myVibrator.vibrate(new long[]{10, 100, 10, 100, 10, 100}, -1);

            multiAppear = false;
            PlayerBlood.playerDamage = true;

            aniObjectX = 0;
            aniObjectY = 0;
            x = initialX;
            y = initialY;

            Scar.scarAppear = true;

            Boss.bossAttackAgain = true; //讓老闆可以再度攻擊

            return;
        }

        if (multiAppear == true) {

            aniObjectX += width/15;
            aniObjectY += height/15;

            canvas.drawBitmap(multiBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x + multiObjectSpeed - aniObjectX, y + multiObjectUpSpeed - aniObjectY, x + multiObjectSpeed + aniObjectX, y + + multiObjectUpSpeed + aniObjectY),
                    null);

//            // g sensor
//            if (MainActivity.gBlockOrNot == true) {
//
//                Boss.blood -= 10; // g sensor閃掉時boss扣血
//                Boss.bossCurrentStatus = Boss.STATUS_GSENSOR; // boss被g-sensor反擊
//                touchEvent();
//            }

        }
    }

//    // 控制多重物件的移動
//    private void move () {
//
//        if (this.x < MainActivity.screenWidth * 0 / 1920) {
//
//            this.x = MainActivity.screenWidth * 0 / 1920;
//            multiObjectSpeed = multiObjectSpeed * (-1);
//            return;
//
//        } else if (this.x > MainActivity.screenWidth * 1620 / 1920) {
//
//            this.x = MainActivity.screenWidth * 1620 / 1920;
//            multiObjectSpeed = multiObjectSpeed * (-1);
//            return;
//
//        }
//
//        this.x -= multiObjectSpeed;
//    }

    private void update () {

        // 東西一消失就重設
        if (multiAppear == false) {

            this.aniObjectX = 0;
            this.aniObjectY = 0;
            x = initialX;
            y = initialY;

            Boss.bossAttackAgain = true; //讓老闆可以再度攻擊

        }

        if (this.choice%4 == 0) {

            // 向上
            multiObjectUpSpeed = -10;
            multiObjectSpeed = 0;

            this.choice++;


        } else if (this.choice%4 == 1) {

            // 向右
            multiObjectUpSpeed = 0;
            multiObjectSpeed = 10;

            this.choice++;

        } else if (this.choice%4 == 2) {

            // 向下
            multiObjectUpSpeed = 10;
            multiObjectSpeed = 0;

            this.choice++;

        } else {

            // 向左
            multiObjectUpSpeed = 0;
            multiObjectSpeed = -10;

            this.choice = 0;
        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
