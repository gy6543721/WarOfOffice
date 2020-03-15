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
import com.warofoffice.warofoffice.obj.boss.Money;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.propSound;

public class BandanButton extends WorldObj implements OnTouchObject {

    public interface BandanSkill{
        public void useBandanSkill();
    }
    private BandanSkill bandanskill;

    public static boolean bandanSkillOn;

    private static Bitmap skillBitmap;

    private int currentBitmapPosition;

    private int interval; //   觸發動畫的錢數間隔

    private int width;
    private int height;


    public BandanButton(Context context, int x, int y, int interval, BandanSkill bandanskill) {
        super(context);

        this.x = x;
        this.y = y;
        this.currentBitmapPosition = 0;
        this.interval = interval;
        this.bandanSkillOn = false;
        this.bandanskill = bandanskill;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight*900/1080; // 原本用MainActivity.screenWidth*900/1920，但長寬比會跑掉
        int bitmapHeight = MainActivity.screenHeight*150/1080;

        skillBitmap = bm.getBitmap(R.drawable.bandan_logo_big, bitmapWidth, bitmapHeight);

        width = bitmapWidth/6;
        height = bitmapHeight;
    }

    @Override
    public void touchEvent() {

        if (currentBitmapPosition == 5 && Money.moneyNumber >= interval*5) {

            Boss.attack = false;

            //蓄力滿了放招時才能放音效
            soundPool.play(propSound,1,1,0,0,1);

            this.bandanSkillOn = true;
            Money.moneyNumber -= interval*5;
            bandanskill.useBandanSkill();

            Score.score += 1000;

            this.currentBitmapPosition = 0;
            //順序不能錯，不然先跑圖就發動不了技能了
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


        if (Money.moneyNumber < interval) {

            this.currentBitmapPosition = 0;
            BossBlood.bossBigDamage = false;

        } else if (Money.moneyNumber > (interval - 1) && Money.moneyNumber < 2*interval) {

            this.currentBitmapPosition = 1;

        } else if (Money.moneyNumber > (2*interval - 1) && Money.moneyNumber < 3*interval) {

            this.currentBitmapPosition = 2;

        } else if (Money.moneyNumber > (3*interval - 1) && Money.moneyNumber < 4*interval) {

            this.currentBitmapPosition = 3;

        }  else if (Money.moneyNumber > (4*interval - 1) && Money.moneyNumber < 5*interval) {

            this.currentBitmapPosition = 4;

        } else {

            this.currentBitmapPosition = 5;

        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
