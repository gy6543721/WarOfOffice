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
import com.warofoffice.warofoffice.obj.buttons.BandanButton;
import com.warofoffice.warofoffice.obj.buttons.CellphoneButton;
import com.warofoffice.warofoffice.obj.buttons.RedBullButton;
import com.warofoffice.warofoffice.obj.buttons.SkillButton;
import com.warofoffice.warofoffice.obj.elevator.Elevator1;
import com.warofoffice.warofoffice.obj.elevator.Elevator2;
import com.warofoffice.warofoffice.obj.player.Muda;

import static com.warofoffice.warofoffice.MainActivity.soundPool;
import static com.warofoffice.warofoffice.SoundPoolSystem.attackSound1;
import static com.warofoffice.warofoffice.SoundPoolSystem.attackSound2;
import static com.warofoffice.warofoffice.SoundPoolSystem.attackSound3;
import static com.warofoffice.warofoffice.SoundPoolSystem.attackSound4;
import static com.warofoffice.warofoffice.SoundPoolSystem.attackSound5;
import static com.warofoffice.warofoffice.SoundPoolSystem.hitSound;
import static com.warofoffice.warofoffice.controller.GameController.currentLevel;

public class Boss extends WorldObj implements OnTouchObject{
    public static class Option{
        private int bossBitmap;  //先設定一個一開始的動作
        private int bossBitmap1; //可以切換的動作
        private int bossBitmap2; //被點到
        private int bossBitmap3; //攻擊預備動作
        private int bossBitmap4; //丟的動作
        private int bossBitmap5; //被公事包反擊的動作
        private int bossBitmap6; //被g sensor反擊的動作

        private static int count;

        private int moveSpeed; //個別設定boss的移動速度
        private int damage; // 個別設定boss的攻擊傷害

        public Option(int i){
            switch (i){
                case 0: // 所有第一關會變的東西都在這裡設定
                    bossBitmap = R.drawable.sales_stand;
                    bossBitmap1 = R.drawable.sales_stand;
                    bossBitmap2 = R.drawable.sales_touch;
                    bossBitmap3 = R.drawable.sales_attack;
                    bossBitmap4 = R.drawable.sales_right_throw;
                    bossBitmap5 = R.drawable.sales_counterattack;
                    bossBitmap6 = R.drawable.sales_gsensor;

                    Boss.blood = 10;
                    //60
                    moveSpeed = 0;
                    damage = 10;

                    this.count = 1;

                    break;
                case 1: // 所有第一關到第二關會變的東西都在這裡設定
                    bossBitmap = R.drawable.pm_normal_small;
                    bossBitmap1 = R.drawable.pm_normal_small;
                    bossBitmap2 = R.drawable.pm_touch_small;
                    bossBitmap3 = R.drawable.pm_attack_small;
                    bossBitmap4 = R.drawable.pm_throw_small_right;
                    bossBitmap5 = R.drawable.pm_counterattack;
                    bossBitmap6 = R.drawable.pm_gsensor;

                    Boss.blood = 20;
                    //150
                    moveSpeed = 5;
                    damage = 10;

                    this.count = 2;
                    break;

                case 2: // 所有第二關到第三關會變的東西都在這裡設定
                    bossBitmap = R.drawable.hr_front_stand;
                    bossBitmap1 = R.drawable.hr_front_stand;
                    bossBitmap2 = R.drawable.hr_front_touch;
                    bossBitmap3 = R.drawable.hr_front_attack;
                    bossBitmap4 = R.drawable.hr_front_right_throw;
                    bossBitmap5 = R.drawable.hr_front_counterattack;
                    bossBitmap6 = R.drawable.hr_front_gsensor;

                    Boss.blood = 30;
                    //200
                    moveSpeed = 60;
                    damage = 10;

                    this.count = 3;
                    break;

                case 3: // 所有第三關到第四關會變的東西都在這裡設定
                    bossBitmap = R.drawable.angry_manager_normal_big;
                    bossBitmap1 = R.drawable.angry_manager_normal_big;
                    bossBitmap2 = R.drawable.angry_manager_touch_big;
                    bossBitmap3 = R.drawable.angry_manager_attack_big;
                    bossBitmap4 = R.drawable.angry_manager_throw_big;
                    bossBitmap5 = R.drawable.angry_manager_counterattack_big;
                    bossBitmap6 = R.drawable.angry_manager_gsensor_big;

                    Boss.blood = 40;
                    //250
                    moveSpeed = 10;
                    damage = 10;

                    this.count = 4;
                    break;

                case 4: // 所有第四關到第五關會變的東西都在這裡設定
                    bossBitmap = R.drawable.boss_body_front_complete_small;
                    bossBitmap1 = R.drawable.boss_body_front_complete_small;
                    bossBitmap2 = R.drawable.boss_body_front_touch_small;
                    bossBitmap3 = R.drawable.boss_body_front_attack_small;
                    bossBitmap4 = R.drawable.boss_body_front_throw_small_left;
                    bossBitmap5 = R.drawable.boss_body_player_counterattack;
                    bossBitmap6 = R.drawable.boss_body_player_gsensor;

                    Boss.blood = 50;
                    //300
                    moveSpeed = 15;
                    damage = 10;

                    this.count = 5;
                    break;

                default:
                    bossBitmap = R.drawable.boss_body_front_complete_small;
                    bossBitmap1 = R.drawable.boss_body_front_complete_small;
                    bossBitmap2 = R.drawable.boss_body_front_touch_small;
                    bossBitmap3 = R.drawable.boss_body_front_attack_small;
                    bossBitmap4 = R.drawable.boss_body_front_throw_small_left;
                    bossBitmap5 = R.drawable.boss_body_player_counterattack;
                    bossBitmap6 = R.drawable.boss_body_player_gsensor;

                    Boss.blood = 100;

                    moveSpeed = 10;
                    damage = 10;

                    this.count = 5;
                    break;

            }
        }
    }

    public static final int STATUS_STAND = 0;
    public static final int STATUS_TOUCH = 1;
    public static final int STATUS_ATTACK = 2;
    public static final int STATUS_THROW = 3;
    public static final int STATUS_COUNTERATTACK = 4;   //按按鈕發動公事包的攻擊
    public static final int STATUS_GSENSOR = 5;    //結束動畫
    public static final int STATUS_OVER = 6;    //結束動畫

    private static int CHARAC_DELAY = 3;

    public int bossDamage; // 技能給boss的傷害

    private int speed;

    public static float blood; //因為用getBlood，不用public也沒差
    private float initialBlood; // 存一開始的血量
    private float heal; // boss回復血量

    private Bitmap BossBitmap;  //先設定一個一開始的動作

    private Bitmap BossBitmap1; //可以切換的動作
    private Bitmap BossBitmap2; //被點到
    private Bitmap BossBitmap3; //攻擊預備動作
    private Bitmap BossBitmap4; //丟的動作
    private Bitmap BossBitmap5; //被公事包反擊的動作
    private Bitmap BossBitmap6; //被g sensor反擊的動作

    public static int bossCurrentStatus;
    private int currentBitmapPosition;
    private int delay;

    private int width;
    private int height;

    private int toAttackOrNot; //判斷老闆是否攻擊的random數
    public static boolean attack; //給World的onTouch判斷是否載入噴錢動畫，如果老闆攻擊時就停止噴錢

    public static boolean bossAttackAgain; //判斷老闆能否連續攻擊

    private int aniAttack = 0; //控制攻擊前準備動畫播兩輪
    private int aniThrow = 0; //控制丟的動畫播一輪
    private int aniCounterAttack = 0; //控制被公事包反擊的動畫播一輪
    private int aniGsensor = 0; //控制被g sensor反擊的動畫播一輪

    public Boss(Context context, int x, int y, Option option){
        super(context);

        this.speed = option.moveSpeed;

        this.x = x;
        this.y = y;

        this.initialBlood = Boss.blood;
        this.heal = 5;

        this.bossDamage = option.damage;

        this.bossCurrentStatus = STATUS_STAND;
        this.currentBitmapPosition = 0;
        this.delay = 0;

        this.bossAttackAgain = true;

        // init bitmap
        BitmapManager bm = new BitmapManager(context);

        int bitmapHeight = MainActivity.screenHeight*900/1080;
        int bitmapWidth = MainActivity.screenWidth*2700/1920;

        BossBitmap = bm.getBitmap(option.bossBitmap, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap1 = bm.getBitmap(option.bossBitmap1, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap2 = bm.getBitmap(option.bossBitmap2, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap3 = bm.getBitmap(option.bossBitmap3, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap4 = bm.getBitmap(option.bossBitmap4, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap5 = bm.getBitmap(option.bossBitmap5, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;

        BossBitmap6 = bm.getBitmap(option.bossBitmap6, bitmapWidth, bitmapHeight);
        width = bitmapWidth/4;
        height = bitmapHeight;
    }

    @Override
    public void onPaint(Canvas canvas, Paint paint) {

        update();
        move();

        int bitmapLeft = width * (currentBitmapPosition%4);// 1
        int bitmapTop = height * (currentBitmapPosition/4);// 1
        int bitmapRight = width * (currentBitmapPosition%4 + 1);//2
        int bitmapBottom = height * (currentBitmapPosition/4 + 1);//2

        canvas.drawBitmap(BossBitmap,
                new Rect(bitmapLeft, bitmapTop, bitmapRight, bitmapBottom),
                new Rect(x, y, width + x, height + y),
                null);
    }

    // 控制boss的移動
    private void move(){

        if (this.x < MainActivity.screenWidth*480/1920) {

            this.x = MainActivity.screenWidth*480/1920;
            speed = speed*(-1);
            return;

        } else if (this.x > MainActivity.screenWidth*800/1920) {

            this.x = MainActivity.screenWidth*800/1920;
            speed = speed*(-1);
            return;

        }

        this.x -= speed;
    }


    private void update(){

        // 讓boss在攻擊提示結束前暫停攻擊

        if (IncommingHint.hintAppearHint == true) {

            bossAttackAgain = false;

        }

        if(CHARAC_DELAY > delay){
            delay++;
            return;
        }else{
            delay = 0;
        }

        // boss受到攻擊時
        if (BossBlood.bossDamage == true) {

            this.bossCurrentStatus = STATUS_TOUCH;

        }else

            //
            if (this.blood > 0 && SkillButton.skillAttack == true) {

                this.bossCurrentStatus = STATUS_COUNTERATTACK;

            }else

            if ( blood <= 0 && bossCurrentStatus != STATUS_COUNTERATTACK && bossCurrentStatus != STATUS_GSENSOR ) {

                Elevator1.currentStatusE1 = Elevator1.STATUS_RETURN;
                Elevator2.currentStatusE2 = Elevator2.STATUS_RETURN;
                // Elevator的狀態改變要寫在boss的判斷之前，因為boss執行後就會一直畫，要畫完才會進下一個判斷

                Score.score += 15000;//打敗一隻boss之後給的獎勵得分

                this.bossCurrentStatus = STATUS_OVER;

            }else

            if (bossCurrentStatus == STATUS_STAND || bossCurrentStatus == STATUS_TOUCH) {

                this.toAttackOrNot = (int)(Math.random()*100)+1;
                if ( toAttackOrNot > 94 && (SkillButton.skillAttack == false || CellphoneButton.cellphoneSkillOn == false || RedBullButton.redbullSkillOn == false || BandanButton.bandanSkillOn == false) && this.bossAttackAgain == true) {
                    //避免按鈕音效被吃掉，判斷變嚴格

                    this.bossCurrentStatus = STATUS_ATTACK;
                    this.attack = true;

                    switch(Option.count){
                        case 1:
                            soundPool.play(attackSound1,1,1,0,0,1);
                            break;
                        case 2:
                            soundPool.play(attackSound2,1,1,0,0,1);
                            break;
                        case 3:
                            soundPool.play(attackSound3,1,1,0,0,1);
                            break;
                        case 4:
                            soundPool.play(attackSound4,1,1,0,0,1);
                            break;
                        case 5:
                            soundPool.play(attackSound5,1,1,0,0,1);
                            break;

                    }

                }
                else if(bossCurrentStatus == STATUS_STAND){

                    this.bossCurrentStatus = STATUS_STAND;
                }else{
                    this.bossCurrentStatus = STATUS_TOUCH;

                }

            }

        if(bossCurrentStatus == STATUS_STAND){

            this.BossBitmap = BossBitmap1;
            this.currentBitmapPosition = (this.currentBitmapPosition+1)%4;//1 2 3 0 1 2 3 0 1 2 3 0


        } else if(bossCurrentStatus == STATUS_ATTACK){

            this.bossAttackAgain = false; // 禁止連續攻擊造成鬼兒閃現

            this.BossBitmap = BossBitmap3;

            this.currentBitmapPosition = (this.currentBitmapPosition+1)%4;

            ++aniAttack;

            if (aniAttack%8 == 0) {

                bossCurrentStatus = STATUS_THROW;
                aniAttack = 0;

            }

        } else if (bossCurrentStatus == STATUS_THROW) {

            this.BossBitmap = BossBitmap4;
            this.currentBitmapPosition = (this.aniThrow)%4;

            ++aniThrow;

            if (aniThrow%4 == 0) {

                // 第二關boss加血
                if (Option.count == 2 && this.blood <= this.initialBlood) {

                    if (this.initialBlood - this.blood <= this.heal ) {

                        this.blood = this.initialBlood;

                    } else {

                        this.blood += this.heal;
                    }
                }

                this.bossCurrentStatus = STATUS_STAND;
                this.aniThrow = 0;
                this.attack = false;

                if (CellphoneButton.cellphoneCount > 0) {

                    Muda.mudaAppear = true;

                } else if ( CellphoneButton.cellphoneCount <= 0 ) {

                    // 控制第二關出現的物件
                    if (currentLevel == 1 ) {

                        Ghost.ghostAppear = true;
                        this.bossAttackAgain = false; // 禁止連續攻擊造成鬼兒閃現

                    }

//                    // 第四關
//                    else if (currentLevel == 3) {
//
//                        AttackObject.objectAppear = true;
//                        MultiObject.multiAppear = true;
//                        this.bossAttackAgain = false; // 禁止連續攻擊造成物件閃現
//
//                    }

                    else {

                        AttackObject.objectAppear = true;
                        this.bossAttackAgain = true;
                    }

                } else {

                    // 控制第二關出現的物件
                    if (currentLevel == 1 ) {

                        Ghost.ghostAppear = true;
                        this.bossAttackAgain = false; // 禁止連續攻擊造成鬼兒閃現

                    }

//                    // 第四關
//                    else if (currentLevel == 3) {
//
//                        AttackObject.objectAppear = true;
//                        MultiObject.multiAppear = true;
//                        this.bossAttackAgain = false; // 禁止連續攻擊造成物件閃現
//
//                    }

                    else {

                        AttackObject.objectAppear = true;
                        this.bossAttackAgain = true;
                    }

                }
            }

        } else if(bossCurrentStatus == STATUS_TOUCH){

            this.BossBitmap = BossBitmap2;
            this.currentBitmapPosition = (this.currentBitmapPosition+1)%4;
            this.bossCurrentStatus = STATUS_STAND;

            if (Ghost.ghostAppear == true || MultiObject.multiAppear == true) {

                Boss.bossAttackAgain = false; // 當鬼兒出現時，讓老闆不可連續攻擊

            }

        } else if(bossCurrentStatus == STATUS_COUNTERATTACK){

            this.BossBitmap = BossBitmap5;
            this.currentBitmapPosition = (this.aniCounterAttack)%4;

            ++aniCounterAttack;

            if (aniCounterAttack%4 == 0) {

                SkillButton.skillAttack = false;
                bossCurrentStatus = STATUS_STAND;
                aniCounterAttack = 0;

            }

        } else if(bossCurrentStatus == STATUS_GSENSOR){

            this.BossBitmap = BossBitmap6;
            this.currentBitmapPosition = (this.aniGsensor)%4;

            ++aniGsensor;

            if (aniGsensor%4 == 0) {

                bossCurrentStatus = STATUS_STAND;
                aniGsensor = 0;

            }

        } else if(bossCurrentStatus == STATUS_OVER){

            this.BossBitmap = BossBitmap2;
            this.currentBitmapPosition = (this.currentBitmapPosition+1)%4;

            // 控制第五關出現的物件
            if (currentLevel == 4 ) {

                CellphoneButton.cellphoneCount = 0; // 避免撒錢繼承到下一關

            }
        }

    }

    @Override
    public void touchEvent() {
        //點擊後做的事情

        this.bossCurrentStatus = STATUS_TOUCH;
        this.aniAttack = 0;
        this.aniThrow = 0;
        this.toAttackOrNot = 0;

        blood -= 1;
        Score.score += 10;

        soundPool.play(hitSound,1,1,0,0,1);
    }

    @Override
    public boolean isTouch(float x, float y) {

        if(this.x + MainActivity.screenWidth*200/1920 > x || this.x + this.width - MainActivity.screenWidth*200/1920 < x){
            return false;
        }
        if(this.y + MainActivity.screenHeight*150/1080 > y || this.y + this.height - MainActivity.screenHeight*150/1080 < y){
            return false;
        }
        return true;
    }
    @Override
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit) {
        return;
    }


}