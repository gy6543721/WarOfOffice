package com.warofoffice.warofoffice.obj.buttons;

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
import com.warofoffice.warofoffice.obj.boss.Boss;
import com.warofoffice.warofoffice.obj.boss.BossBlood;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.propSound;

public class CellphoneButton extends WorldObj implements OnTouchObject {

    public interface CellphoneSkill {

        public void useCellphoneSkill();
    }

    private CellphoneSkill cellphoneSkill;

    public static boolean cellphoneSkillOn; //用來改變勞動部技能狀態的布林值
    public static int cellphoneCount;//用來計算打給勞動部後可避掉煙灰缸傷害的次數

    public static int cellphoneButtonCharge; // 用來計算cellphoneButton變圖一次的充電次數

    private static Bitmap skillBitmap;

    private int currentBitmapPosition;

    private int interval; //   觸發動畫的錢數間隔

    private int width;
    private int height;

    public CellphoneButton(Context context, int x, int y, int interval, CellphoneSkill cellphoneSkill) {

        super(context);

        this.x = x;
        this.y = y;
        this.cellphoneButtonCharge = 0;
        this.currentBitmapPosition = 0;
        this.interval = interval;
        this.cellphoneSkillOn = false;
        this.cellphoneCount = 0;

        this.cellphoneSkill = cellphoneSkill;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight*900/1080; // 原本用MainActivity.screenWidth*900/1920，但長寬比會跑掉
        int bitmapHeight = MainActivity.screenHeight*150/1080;

        skillBitmap = bm.getBitmap(R.drawable.cellphone_logo_big, bitmapWidth, bitmapHeight);

        width = bitmapWidth/6;
        height = bitmapHeight;

    }

    @Override
    public void touchEvent() {

        if (currentBitmapPosition == 5 && cellphoneButtonCharge >= interval*5) {

            Boss.attack = false;

            //蓄力滿了放招時才能放音效
            soundPool.play(propSound,1,1,0,0,1);

            this.cellphoneSkillOn = true;
            this.cellphoneButtonCharge = 0;
            cellphoneSkill.useCellphoneSkill();

            //順序不能錯，不然先跑圖就發動不了技能了

            Score.score += 1000;
        }
    }

    @Override
    public boolean isTouch(float x, float y) {

        if(this.x > x || this.x + this.width < x){
            return false;
        }
        if(this.y > y || this.y + this.height < y){
            return false;
        }
        return true;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        update();

        int bitmapLeft = width*(currentBitmapPosition%6);// 1
        int bitmapTop = height*(currentBitmapPosition/6);// 1
        int bitmapRight = width*(currentBitmapPosition%6 + 1);//2
        int bitmapBottom = height*(currentBitmapPosition/6 + 1);//2

        canvas.drawBitmap(skillBitmap,
                new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                new Rect(x,y,x + width,y + height),
                null);

    }


    private void update () {

        if (cellphoneCount <= 0) {

            cellphoneSkillOn = false;
            this.currentBitmapPosition = 0;
        }

        // 利用g sensor躲過的次數來加
        if (MainActivity.gBlockOrNot == true) {

            cellphoneButtonCharge++;
        }

        if (cellphoneSkillOn == true ) {

//            currentBitmapPosition = cellphoneCount; //會指示你還剩幾次
            currentBitmapPosition = 0; // 指示燈熄滅

        } else {

            if (cellphoneButtonCharge < interval) {

                this.currentBitmapPosition = 0;
                BossBlood.bossBigDamage = false;

            } else if (cellphoneButtonCharge > (interval - 1) && cellphoneButtonCharge < 2*interval) {

                this.currentBitmapPosition = 1;

            } else if (cellphoneButtonCharge > (2*interval - 1) && cellphoneButtonCharge < 3*interval) {

                this.currentBitmapPosition = 2;

            } else if (cellphoneButtonCharge > (3*interval - 1) && cellphoneButtonCharge < 4*interval) {

                this.currentBitmapPosition = 3;

            }  else if (cellphoneButtonCharge > (4*interval - 1) && cellphoneButtonCharge < 5*interval) {

                this.currentBitmapPosition = 4;

            } else {

                this.currentBitmapPosition = 5;

            }

        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }

}
