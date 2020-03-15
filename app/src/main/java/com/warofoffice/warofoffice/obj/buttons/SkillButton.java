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
import static com.warofoffice.warofoffice.SoundPoolSystem.skillSound;

public class SkillButton extends WorldObj implements OnTouchObject {
    public interface Skill{
        public void useSkill();
    }
    private Skill skill;

    public static int touchTimes; // 計算手點的次數
    public static boolean skillAttack;
    public static boolean redBullSkill;//用來改變紅牛技能狀態的布林值
    public static int redBullCount;//用來計算開紅牛後可免CD按按鈕的次數

    private boolean skillButtonCharge;

    private static Bitmap skillBitmap;

    private int currentBitmapPosition;

    private int times; //   觸發動畫的點擊次數間隔

    private int width;
    private int height;

    public SkillButton(Context context, int x, int y, int times, Skill skill) {
        super(context);

        this.x = x;
        this.y = y;
        this.skillButtonCharge = true;
        this.currentBitmapPosition = 0;
        this.touchTimes = 0;
        this.times = times;
        this.skillAttack = false;
        this.skill = skill;

        this.redBullSkill = false;
        this.redBullCount = 0;

        BitmapManager bm = new BitmapManager(context);

        int bitmapWidth = MainActivity.screenHeight*900/1080; // 原本用MainActivity.screenWidth*900/1920，但長寬比會跑掉
        int bitmapHeight = MainActivity.screenHeight*150/1080;

        skillBitmap = bm.getBitmap(R.drawable.skill_logo_big, bitmapWidth, bitmapHeight);

        width = bitmapWidth/6;
        height = bitmapHeight;

    }

    @Override
    public void touchEvent() {

        if (currentBitmapPosition == 5) {

            //蓄力滿了放招時才能放音效
            soundPool.play(skillSound,1,1,0,0,2); //rate是幾倍速的意思

            Boss.attack = false;

            skillButtonCharge = true;
            this.touchTimes = 0;
            this.skillAttack = true;
            skill.useSkill();
            redBullCount--;

            Boss.bossAttackAgain = true; // 解boss攻擊限制

            this.currentBitmapPosition = 0;
            //順序不能錯，不然先跑圖就扣不到傷害了

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

        if ( redBullSkill == true && redBullCount > 0 ) {

            this.currentBitmapPosition = 5;
            skillButtonCharge = false;

        }


        if (touchTimes < times && skillButtonCharge == true) {

            this.currentBitmapPosition = 0;
            BossBlood.bossBigDamage = false;

        } else if ( touchTimes > (times - 1) && touchTimes < 2*times  && skillButtonCharge == true) {

            this.currentBitmapPosition = 1;

        } else if ( touchTimes > (2*times - 1) && touchTimes < 3*times && skillButtonCharge == true) {

            this.currentBitmapPosition = 2;

        } else if ( touchTimes > (3*times - 1) && touchTimes < 4*times && skillButtonCharge == true) {

            this.currentBitmapPosition = 3;

        }  else if ( touchTimes > (4*times - 1) && touchTimes < 5*times && skillButtonCharge == true) {

            this.currentBitmapPosition = 4;

        } else {

            this.currentBitmapPosition = 5;
            skillButtonCharge = false;

//            Boss.bossAttackAgain = true; // 解攻擊限制

        }
    }

    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {

    }
}
