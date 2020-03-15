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

public class Ghost extends WorldObj implements OnTouchObject {

    private static final int DELAY_TIME = 30;

    private static Bitmap ghostBitmap; //先設定一個一開始的動作

    private static Bitmap ghostBitmap01; // choice = 0 的時候
    private static Bitmap ghostBitmap02; // choice = 1 的時候
    private static Bitmap ghostBitmap01touch; // choice = 2 的時候
    private static Bitmap ghostBitmap02touch; // choice = 3 的時候

    public static boolean ghostAppear; // 判斷東西是否出現
    public static int ghostDamage; // 東西給的傷害

    private int width;
    private int height;

    private int delay;

    private int aniObjectY;
    private int initialY;

    private int ghostSpeed; //控制鬼兒左右移動
    private int upSpeed; //控制鬼兒飄上去的速度

    private int choice; //控制鬼兒的圖

    public Ghost(Context context, int x, int y, int ghostSpeed, int ghostDamage, int choice) {
        super(context);
        this.x = x;
        this.y = y;

        this.initialY = y; //儲存鬼兒的初始位置

        this.delay = 0;
        this.ghostAppear = false;
        this.aniObjectY = 0;

        this.upSpeed = 15;

        this.ghostSpeed = ghostSpeed;
        this.ghostDamage = ghostDamage;

        this.choice = choice;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight * 300/1080;
        int bitmapHeight = MainActivity.screenHeight * 300/1080;

        ghostBitmap = bm.getBitmap(R.drawable.ghost_target, bitmapWidth, bitmapHeight);
        ghostBitmap01 = bm.getBitmap(R.drawable.ghost, bitmapWidth, bitmapHeight);
        ghostBitmap02 = bm.getBitmap(R.drawable.ghost_target, bitmapWidth, bitmapHeight);
        ghostBitmap01touch = bm.getBitmap(R.drawable.ghost_touch, bitmapWidth, bitmapHeight);
        ghostBitmap02touch = bm.getBitmap(R.drawable.ghost_target_touch, bitmapWidth, bitmapHeight);

        width = bitmapWidth;
        height = bitmapHeight;

    }

    @Override
    public void touchEvent() {

        Score.score += 50;

        if (this.choice == 0 || this.choice == 2) {

            this.choice = 2; //不讓進ghostBitmap01

        } else {

            this.choice = 3; //不讓進ghostBitmap02

        }

//        // 讓鬼兒上升速度隨點擊次數增加
//        if ( upSpeed <= 3 ) {
//
//            upSpeed = 1;
//
//        } else {
//
//            upSpeed -= 2;
//        }

    }

    @Override
    public boolean isTouch(float x, float y) {


        if(this.x > x || this.x + width < x){
            return false;
        }

        if(this.y - aniObjectY > y || this.y + height + aniObjectY < y){
            return false;
        }
        return true;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        Scar.scarAppear = false;

        update();
        move();

        if (delay < DELAY_TIME) {
            delay++;
        }

        if (ghostAppear == true) {

            if (this.choice == 0 || this.choice == 1) {

                aniObjectY += height / upSpeed;

            }

            if ( this.y - aniObjectY < 0 ) {

                PlayerBlood.playerDamage = true;

                Boss.bossAttackAgain = true; //鬼兒消失後，讓老闆可以再度攻擊

                this.aniObjectY = 0;
                this.y = initialY;
                Scar.scarAppear = true;

                // 震動
                MainActivity.myVibrator.vibrate(new long[]{10, 100, 10, 100, 10, 100}, -1);
                this.ghostAppear = false;

                return;
            }


            canvas.drawBitmap(ghostBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x, y - aniObjectY, x + width, y + height - aniObjectY),
                    null);

//            // 鬼兒不需用 g sensor
//            if (MainActivity.gBlockOrNot == true) {
//
//                touchEvent();
//            }

        } else {

            Boss.bossAttackAgain = true; //鬼兒消失後，讓老闆再度攻擊
        }
    }

    // 控制ghost的移動
    private void move(){

        if (this.x < MainActivity.screenWidth*0/1920) {

            this.x = MainActivity.screenWidth*0/1920;
            ghostSpeed = ghostSpeed*(-1);
            return;

        } else if (this.x > MainActivity.screenWidth*1620/1920) {

            this.x = MainActivity.screenWidth*1620/1920;
            ghostSpeed = ghostSpeed*(-1);
            return;

        }

        this.x -= ghostSpeed;
    }

    private void update () {

        // 老闆一攻擊就重設
        if (Boss.bossCurrentStatus == Boss.STATUS_THROW) {

            this.aniObjectY = 0;
            this.y = initialY;

            if (this.choice == 2 || this.choice == 0) {

                this.choice = 0;

            } else {

                this.choice = 1;
            }
        }

        // 用鬼兒的出現與否來判斷狀況
        if (ghostAppear == true) {

            Boss.bossAttackAgain = false; //鬼兒出現後，讓老闆不可再度攻擊

            if ( this.choice == 0 ) {

                ghostBitmap = ghostBitmap01;

            } else if (this.choice == 1) {

                ghostBitmap = ghostBitmap02;

            } else if (this.choice == 2) {

                ghostBitmap = ghostBitmap01touch;

            } else {

                ghostBitmap = ghostBitmap02touch;

            }

        } else {

            this.aniObjectY = 0;
            this.y = initialY;

            return;
        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
