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

import static com.warofoffice.warofoffice.controller.GameController.currentLevel;

public class AttackObject extends WorldObj implements OnTouchObject {

    public static class Option{

        private int currentAttackObject; //要載入哪張圖

        private int pictureWidth; //圖片的寬
        private int pictureHeight; //圖片的高

        private int aniX;
        private int aniY;

        private int objectSpeed;

        public Option(int i){
            switch (i){
                case 0: // 所有第一關會變的東西都在這裡設定
                    currentAttackObject = R.drawable.bag;
                    attackObjectX = MainActivity.screenWidth*1000/1920;
                    attackObjectY = MainActivity.screenHeight*400/1080;

                    pictureWidth = MainActivity.screenWidth * 300/1920;
                    pictureHeight = MainActivity.screenHeight * 300/1080;

                    aniX = pictureWidth/10;
                    aniY = pictureHeight/10;

                    damage = 10;
                    objectSpeed = 0;

                    break;
                case 1: // 所有第一關到第二關會變的東西都在這裡設定
                    currentAttackObject = R.drawable.nothing;

                    attackObjectX = MainActivity.screenWidth*1050/1920;
                    attackObjectY = MainActivity.screenHeight*550/1080;

                    pictureWidth = MainActivity.screenWidth * 250/1920;
                    pictureHeight = MainActivity.screenHeight * 300/1080;

                    aniX = 0;
                    aniY = 0;

                    damage = 0;
                    objectSpeed = 0;

                    break;

                case 2: // 所有第二關到第三關會變的東西都在這裡設定
                    currentAttackObject = R.drawable.pen_and_letter;

                    attackObjectX = MainActivity.screenWidth*1000/1920;
                    attackObjectY = MainActivity.screenHeight*350/1080;

                    pictureWidth = MainActivity.screenWidth * 300/1920;
                    pictureHeight = MainActivity.screenHeight * 250/1080;

                    aniX = pictureWidth/10;
                    aniY = pictureHeight/10;

                    damage = 20;
                    objectSpeed = 100;

                    break;

                case 3: // 所有第三關到第四關會變的東西都在這裡設定
                    currentAttackObject = R.drawable.watch;

                    attackObjectX = MainActivity.screenWidth*680/1920;
                    attackObjectY = MainActivity.screenHeight*580/1080;

                    pictureWidth = MainActivity.screenWidth * 280/1920;
                    pictureHeight = MainActivity.screenHeight * 300/1080;

                    aniX = pictureWidth/10;
                    aniY = pictureHeight/10;

                    damage = 30;
                    objectSpeed = 120;

                    break;

                case 4: // 所有第四關到第五關會變的東西都在這裡設定
                    currentAttackObject = R.drawable.ashtray_small;

                    attackObjectX = MainActivity.screenWidth*650/1920;
                    attackObjectY = MainActivity.screenHeight*350/1080;

                    pictureWidth = MainActivity.screenWidth * 300 / 1920;
                    pictureHeight = MainActivity.screenHeight * 300 / 1080;

                    aniX = pictureWidth/10;
                    aniY = pictureHeight/10;

                    damage = 90;
                    objectSpeed = 150;

                    break;

                default:
                    currentAttackObject = R.drawable.bag;
                    attackObjectX = MainActivity.screenWidth*1000/1920;
                    attackObjectY = MainActivity.screenHeight*400/1080;

                    pictureWidth = MainActivity.screenWidth * 300/1920;
                    pictureHeight = MainActivity.screenHeight * 300/1080;

                    aniX = pictureWidth/10;
                    aniY = pictureHeight/10;

                    damage = 20;
                    objectSpeed = 100;

                    break;
            }
        }
    }

    public static int attackObjectX;
    public static int attackObjectY;


    private static final int DELAY_TIME = 30;

    private static Bitmap objectBitmap;

    public static boolean objectAppear; // 判斷東西是否出現
    public static int damage; // 東西給的傷害

    private int width;
    private int height;

    private int delay;

    private int aniObjectX;
    private int aniObjectY;

    // 吃option的aniX和aniY
    private int bigX;
    private int bigY;

    private int speed;


    public AttackObject(Context context, int x, int y, Option option) {
        super(context);
        this.x = x;
        this.y = y;

        this.delay = 0;
        this.objectAppear = false;
        this.aniObjectX = 0;
        this.aniObjectY = 0;

        bigX = option.aniX;
        bigY = option.aniY;

        this.speed = option.objectSpeed;


        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = option.pictureWidth;
        int bitmapHeight = option.pictureHeight;

        objectBitmap = bm.getBitmap(option.currentAttackObject, bitmapWidth, bitmapHeight);


        width = bitmapWidth;
        height = bitmapHeight;
    }

    @Override
    public void touchEvent() {

        objectAppear = false;
        aniObjectX = 0;
        aniObjectY = 0;
        Score.score += 100;

        // 除了第二關和第四關
        if (currentLevel != 1 && currentLevel != 3) {

            Boss.bossAttackAgain = true; //讓老闆可以再度攻擊

        }

        // 第四關
        if (currentLevel == 3) {

            MultiObject.multiAppear = true;
            Boss.bossAttackAgain = false; // 禁止連續攻擊造成物件閃現

        }
    }

    @Override
    public boolean isTouch(float x, float y) {

        if(this.x - aniObjectX > x || this.x + aniObjectX < x){
            return false;
        }
        if(this.y - aniObjectY > y || this.y + aniObjectY < y){
            return false;
        }
        return true;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        Scar.scarAppear = false;

        move();

        if (delay < DELAY_TIME) {
            delay++;
        }

        if (x - aniObjectX < 0 || y + aniObjectY > 1080) {


            objectAppear = false;
            PlayerBlood.playerDamage = true;
            aniObjectX = 0;
            aniObjectY = 0;
            Scar.scarAppear = true;

            // 除了第二關和第四關
            if (currentLevel != 1 && currentLevel != 3) {

                Boss.bossAttackAgain = true; //讓老闆可以再度攻擊

            }

            // 第四關
            if (currentLevel == 3) {

                MultiObject.multiAppear = true;
                Boss.bossAttackAgain = false; // 禁止連續攻擊造成物件閃現

            }

            return;
        }

        if (objectAppear == true) {

            aniObjectX += bigX;
            aniObjectY += bigY;

            canvas.drawBitmap(objectBitmap,
                    new Rect(0, 0, width, height),
                    new Rect(x - aniObjectX, y - aniObjectY, x + aniObjectX, y + aniObjectY ),
                    null);

            // g sensor
            if (MainActivity.gBlockOrNot == true) {

                Boss.blood -= 10; // g sensor閃掉時boss扣血
                Boss.bossCurrentStatus = Boss.STATUS_GSENSOR; // boss被g-sensor反擊
                touchEvent();
            }

        }
    }

    // 控制攻擊物件的移動
    private void move(){

        if (this.x < MainActivity.screenWidth*480/1920) {

            this.x = MainActivity.screenWidth*480/1920;
            speed = speed*(-1);
            return;

        } else if (this.x > MainActivity.screenWidth*1300/1920) {

            this.x = MainActivity.screenWidth*1300/1920;
            speed = speed*(-1);
            return;

        }

        this.x -= speed;
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
