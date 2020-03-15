package com.warofoffice.warofoffice.obj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.obj.buttons.CellphoneCountDown;
import com.warofoffice.warofoffice.scoreboard.Score;
import com.warofoffice.warofoffice.obj.background.Level;
import com.warofoffice.warofoffice.obj.boss.AttackObject;
import com.warofoffice.warofoffice.obj.boss.Ghost;
import com.warofoffice.warofoffice.obj.boss.Boss;
import com.warofoffice.warofoffice.obj.boss.BossBlood;
import com.warofoffice.warofoffice.obj.boss.BossBloodBar;
import com.warofoffice.warofoffice.obj.boss.IncommingHint;
import com.warofoffice.warofoffice.obj.boss.Money;
import com.warofoffice.warofoffice.obj.boss.MultiObject;
import com.warofoffice.warofoffice.obj.buttons.BandanButton;
import com.warofoffice.warofoffice.obj.buttons.CellphoneButton;
import com.warofoffice.warofoffice.obj.buttons.RedBullButton;
import com.warofoffice.warofoffice.obj.buttons.SkillButton;
import com.warofoffice.warofoffice.obj.elevator.Elevator1;
import com.warofoffice.warofoffice.obj.elevator.Elevator2;
import com.warofoffice.warofoffice.obj.moneyBar.Bar;
import com.warofoffice.warofoffice.obj.player.Muda;
import com.warofoffice.warofoffice.obj.player.PlayerBar;
import com.warofoffice.warofoffice.obj.player.PlayerBlood;
import com.warofoffice.warofoffice.obj.player.Scar;
import com.warofoffice.warofoffice.timer.TimerBar;
import com.warofoffice.warofoffice.timer.TimerBar2;

import java.util.ArrayList;
import java.util.List;

import static com.warofoffice.warofoffice.controller.GameController.currentLevel;

public class World{
    private static final String TAG = "World";

    public static final int WORLD_STATUS_STOP = 0;
    public static final int WORLD_STATUS_START = 1;
    public static final int WORLD_STATUS_NEXT = 2;
    public static final int WORLD_STATUS_OVER = 3;

    public static int scoreBoardStatus; // 控制積分板顯示 你贏了 你輸了 或時間到 的變數

    public static final int YOU_WIN = 0;
    public static final int YOU_LOSE = 1;
    public static final int TIME_OUT = 2;

    public interface WorldStatusInterface{      //內部介面
        public void updateStatus(int status);
    }
    public static WorldStatusInterface worldStatusInterface;// 存別人處理好的updateStatus 的方法



    private Context context;

    // obj
    private List<WorldObj> worldObjs;

    //宣告一個Boss的變數
    private Boss boss;

    //宣告一個Money類別的List存所有的money;
    private List<Money> moneys;

    //宣告一個煙灰缸的變數
    private AttackObject object;


    // 所有的ghost;
    private Ghost ghost01;
    private Ghost ghost02;
    private Ghost ghost03;
    private Ghost ghost04;
    private Ghost ghost05;
//    // 讓可以被觸碰的鬼跳下一隻
//    private Ghost ghostTmp;

    // 第四關所有新增的攻擊物件
    private MultiObject multiObject01;
    private MultiObject multiObject02;

    // 攻擊提示
    private IncommingHint incommingHint;

    //宣告一個受傷提示的變數
    private  Scar scar;

    //宣告一個無馱提示的變數
    private Muda muda;

    //宣告一個技能按鈕的變數
    public SkillButton skillButton;

    //宣告一個便當按鈕的變數
    public BandanButton bandanButton;

    //宣告一個紅牛按鈕的變數
    public RedBullButton redBullButton;

    //宣告一個勞動局按鈕的變數
    public CellphoneButton cellphoneButton;

    //宣告一個勞動局技能倒數的變數
    public CellphoneCountDown cellphoneCountDown;

    //控制觸控的開關
    public static boolean enableTouch;

    public World(Context context){
        preInit(context);
    }

    public World(Context context, WorldStatusInterface worldStatusInterface){
        preInit(context);
        this.worldStatusInterface = worldStatusInterface;

        //暫時把錢的數值設定在低個世界一開始給個訂值
        Money.moneyNumber = -1;
    }
    private World(Context context, Param param){
        this.worldStatusInterface = param.worldStatusInterface;
        preInit(context, param.level, param.option, param.attackObject);
    }

    private void init(Context context){     //把角色傳進來的接口

        // 最後一關觸控失效，錢歸零
        if (currentLevel == 4) {

            enableTouch = false;
            Money.moneyNumber = 0;

        } else {

            enableTouch = true;

        }


        worldObjs.add(new Bar(context,MainActivity.screenWidth*1700/1920, MainActivity.screenHeight*100/1080));
        worldObjs.add(new BossBloodBar(context,MainActivity.screenWidth*800/1920 ,MainActivity.screenHeight*170/1080));
        worldObjs.add(new BossBlood(context,
                MainActivity.screenWidth * 805 / 1920,
                MainActivity.screenHeight * 175 / 1080,
                new BossBlood.BloodBarListener() {
                    @Override
                    public float getBlood() {
                        return boss.blood;
                    }
                })); // 介面，不用static之外拿參數的方法
        worldObjs.add(new PlayerBar(context, MainActivity.screenHeight*100/1920, MainActivity.screenHeight*100/1080));
        worldObjs.add(new PlayerBlood(context,MainActivity.screenHeight*110/1920,MainActivity.screenHeight*105/1080, 100));
        worldObjs.add(new Elevator1(context,MainActivity.screenWidth*520/1920,MainActivity.screenHeight*120/1080)); // 450,90
        worldObjs.add(new Elevator2(context,MainActivity.screenWidth*945/1920,MainActivity.screenHeight*120/1080)); // 1000,90
        worldObjs.add(new TimerBar(context,MainActivity.screenWidth*1700/1920,MainActivity.screenHeight*200/1080));
        worldObjs.add(new TimerBar2(context,MainActivity.screenWidth*1770/1920,MainActivity.screenHeight*200/1080));
        scar =  new Scar(context,0,0);
        worldObjs.add(scar);

        muda = new Muda(context,MainActivity.screenWidth*850/1920,MainActivity.screenHeight*450/1080);
        worldObjs.add(muda);

        updateGhost(currentLevel);
        updateMultiObj (currentLevel);
        updateButton(currentLevel);

        updateIncommingHint (currentLevel);

    }
    // 控制按鈕的出現
    private void updateButton (int i) {

        switch (i) {
            case 0:

                skillButton = null;
                bandanButton = null;
                redBullButton = null;
                cellphoneButton = null;

                break;
            case 1:

                skillButton = new SkillButton(context,
                        MainActivity.screenWidth * 1730 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        3, new SkillButton.Skill() {
                    @Override
                    public void useSkill() {
                        boss.bossCurrentStatus = boss.STATUS_COUNTERATTACK;
                        boss.blood -= boss.bossDamage;

                        Boss.bossAttackAgain = true; //解攻擊限制

                    }
                });
                worldObjs.add(skillButton);

                bandanButton = null;
                redBullButton = null;
                cellphoneButton = null;

                break;

            case 2:

                skillButton = new SkillButton(context,
                        MainActivity.screenWidth * 1730 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        3, new SkillButton.Skill() {
                    @Override
                    public void useSkill() {
                        boss.bossCurrentStatus = boss.STATUS_COUNTERATTACK;
                        boss.blood -= boss.bossDamage;

                        Boss.bossAttackAgain = true; //解攻擊限制

                    }
                });
                worldObjs.add(skillButton);

                redBullButton = new RedBullButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 600 / 1080,
                        6, new RedBullButton.RedBullSkill() {
                    @Override
                    public void useRedBullSkill() {

                        SkillButton.redBullSkill = true;
                        SkillButton.redBullCount = 3;

                        Boss.bossAttackAgain = true; //解攻擊限制

                        redBullButton.redbullSkillOn = false;
                    }
                });
                worldObjs.add(redBullButton);

                bandanButton = null;
                cellphoneButton = null;

                break;

            case 3:

                skillButton = new SkillButton(context,
                        MainActivity.screenWidth * 1730 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        3, new SkillButton.Skill() {
                    @Override
                    public void useSkill() {
                        boss.bossCurrentStatus = boss.STATUS_COUNTERATTACK;
                        boss.blood -= boss.bossDamage;

                        Boss.bossAttackAgain = true; //解攻擊限制
                    }
                });
                worldObjs.add(skillButton);


                bandanButton = new BandanButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        8, new BandanButton.BandanSkill() {
                    @Override
                    public void useBandanSkill() {
                        PlayerBlood.life = PlayerBlood.initialLife;

                        Boss.bossAttackAgain = true; //解攻擊限制

                        BandanButton.bandanSkillOn = false;
                    }
                });
                worldObjs.add(bandanButton);

                redBullButton = new RedBullButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 600 / 1080,
                        6, new RedBullButton.RedBullSkill() {
                    @Override
                    public void useRedBullSkill() {

                        SkillButton.redBullSkill = true;
                        SkillButton.redBullCount = 3;

                        Boss.bossAttackAgain = true; //解攻擊限制

                        redBullButton.redbullSkillOn = false;
                    }
                });
                worldObjs.add(redBullButton);


                cellphoneButton = null;

                break;

            case 4:

                skillButton = new SkillButton(context,
                        MainActivity.screenWidth * 1730 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        3, new SkillButton.Skill() {
                    @Override
                    public void useSkill() {
                        boss.bossCurrentStatus = boss.STATUS_COUNTERATTACK;
                        boss.blood -= boss.bossDamage;

                        Boss.bossAttackAgain = true; //解攻擊限制
                    }
                });
                worldObjs.add(skillButton);


                bandanButton = new BandanButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        8, new BandanButton.BandanSkill() {
                    @Override
                    public void useBandanSkill() {
                        PlayerBlood.life = PlayerBlood.initialLife;

                        Boss.bossAttackAgain = true; //解攻擊限制

                        BandanButton.bandanSkillOn = false;
                    }
                });
                worldObjs.add(bandanButton);

                redBullButton = new RedBullButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 600 / 1080,
                        6, new RedBullButton.RedBullSkill() {
                    @Override
                    public void useRedBullSkill() {

                        SkillButton.redBullSkill = true;
                        SkillButton.redBullCount = 3;

                        Boss.bossAttackAgain = true; //解攻擊限制

                        redBullButton.redbullSkillOn = false;
                    }
                });
                worldObjs.add(redBullButton);

                cellphoneButton = new CellphoneButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 400 / 1080,
                        1, new CellphoneButton.CellphoneSkill() {
                    @Override
                    public void useCellphoneSkill() {

                        cellphoneCountDown.countdownAppear = true;

                        cellphoneButton.cellphoneButtonCharge = 0; // 充電次數歸零
                        cellphoneButton.cellphoneSkillOn = true;
                        cellphoneButton.cellphoneCount = 10; // 開完可以擋10次

                        enableTouch = true;
                        MainActivity.handler.postDelayed(new Runnable(){
                            @Override
                            public void run() {

                                //過6秒後要做的事情
                                enableTouch = false;
                                Boss.bossAttackAgain = true; //解攻擊限制
                                cellphoneButton.cellphoneCount = 0;
                                cellphoneCountDown.countdownAppear = false; //倒數停止

                            }}, 6000);

                        cellphoneButton.cellphoneSkillOn = false;

                    }
                });
                worldObjs.add(cellphoneButton);

                // 勞動部倒數
                cellphoneCountDown = new CellphoneCountDown(context,
                        MainActivity.screenWidth * (-20) / 1920,
                        MainActivity.screenHeight * 200 / 1080);
                worldObjs.add(cellphoneCountDown);

                break;

            default:

                skillButton = new SkillButton(context,
                        MainActivity.screenWidth * 1730 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        3, new SkillButton.Skill() {
                    @Override
                    public void useSkill() {
                        boss.bossCurrentStatus = boss.STATUS_COUNTERATTACK;
                        boss.blood -= boss.bossDamage;

                        Boss.bossAttackAgain = true; //解攻擊限制

                    }
                });
                worldObjs.add(skillButton);


                bandanButton = new BandanButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 800 / 1080,
                        8, new BandanButton.BandanSkill() {
                    @Override
                    public void useBandanSkill() {
                        PlayerBlood.life = PlayerBlood.initialLife;

                        BandanButton.bandanSkillOn = false;
                    }
                });
                worldObjs.add(bandanButton);

                redBullButton = new RedBullButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 600 / 1080,
                        6, new RedBullButton.RedBullSkill() {
                    @Override
                    public void useRedBullSkill() {

                        SkillButton.redBullSkill = true;
                        SkillButton.redBullCount = 3;
                        Boss.bossAttackAgain = true; //解攻擊限制

                        redBullButton.redbullSkillOn = false;
                    }
                });
                worldObjs.add(redBullButton);

                cellphoneButton = new CellphoneButton(context,
                        MainActivity.screenWidth * 20 / 1920,
                        MainActivity.screenHeight * 400 / 1080,
                        1, new CellphoneButton.CellphoneSkill() {
                    @Override
                    public void useCellphoneSkill() {

                        cellphoneButton.cellphoneButtonCharge = 0; // 充電次數歸零
                        cellphoneButton.cellphoneSkillOn = true;
                        cellphoneButton.cellphoneCount = 10; // 開完可以擋10次
                        enableTouch = true;
                        MainActivity.handler.postDelayed(new Runnable(){
                            @Override
                            public void run() {

                                //過6秒後要做的事情
                                enableTouch = false;
                                Boss.bossAttackAgain = true;
                                cellphoneButton.cellphoneCount = 0;
                                cellphoneCountDown.countdownAppear = false; //倒數停止

                            }}, 6000);

                        cellphoneButton.cellphoneSkillOn = false;

                    }
                });
                worldObjs.add(cellphoneButton);

                // 勞動部倒數
                cellphoneCountDown = new CellphoneCountDown(context,
                        MainActivity.screenWidth * (-20) / 1920,
                        MainActivity.screenHeight * 200 / 1080);
                worldObjs.add(cellphoneCountDown);

                break;
        }
    }

    // 控制鬼兒出現在哪些關卡
    private void updateGhost (int i) {

        switch (i) {
            case 0:

                ghost01 = null;
                ghost02 = null;
                ghost03 = null;
                ghost04 = null;
                ghost05 = null;

                Boss.bossAttackAgain = true;

                break;

            case 1:

                ghost01 = new Ghost(context,MainActivity.screenWidth*200/1920,MainActivity.screenHeight*250/1080,30,15,0);
                worldObjs.add(ghost01);

                ghost02 = new Ghost(context,MainActivity.screenWidth*500/1920,MainActivity.screenHeight*250/1080,30,15,0);
                worldObjs.add(ghost02);

                ghost03 = new Ghost(context,MainActivity.screenWidth*800/1920,MainActivity.screenHeight*250/1080,30,15,1);
                worldObjs.add(ghost03);

                ghost04 = new Ghost(context,MainActivity.screenWidth*1100/1920,MainActivity.screenHeight*250/1080,30,15,0);
                worldObjs.add(ghost04);

                ghost05 = new Ghost(context,MainActivity.screenWidth*1400/1920,MainActivity.screenHeight*250/1080,30,15,0);
                worldObjs.add(ghost05);


                break;

            case 2:

                ghost01 = null;
                ghost02 = null;
                ghost03 = null;
                ghost04 = null;
                ghost05 = null;

                Boss.bossAttackAgain = true;

                break;

            case 3:

                ghost01 = null;
                ghost02 = null;
                ghost03 = null;
                ghost04 = null;
                ghost05 = null;

                Boss.bossAttackAgain = true;

                break;

            case 4:

                ghost01 = null;
                ghost02 = null;
                ghost03 = null;
                ghost04 = null;
                ghost05 = null;

                Boss.bossAttackAgain = true;

                break;

            default:

                ghost01 = null;
                ghost02 = null;
                ghost03 = null;
                ghost04 = null;
                ghost05 = null;

                Boss.bossAttackAgain = true;

                break;
        }
    }

    // 控制多重物件出現在哪些關卡
    private void updateMultiObj (int i) {

        switch (i) {
            case 0:

                multiObject01 = null;
                multiObject02 = null;

                Boss.bossAttackAgain = true;

                break;

            case 1:

                multiObject01 = null;
                multiObject02 = null;

                Boss.bossAttackAgain = true;

                break;

            case 2:

                multiObject01 = null;
                multiObject02 = null;

                Boss.bossAttackAgain = true;

                break;

            case 3:

                multiObject01 = new MultiObject(context,MainActivity.screenWidth*400/1920,MainActivity.screenHeight*100/1080,30,0);
                worldObjs.add(multiObject01);

                multiObject02 = new MultiObject(context,MainActivity.screenWidth*1520/1920,MainActivity.screenHeight*100/1080,30,1);
                worldObjs.add(multiObject02);

                break;

            case 4:

                multiObject01 = null;
                multiObject02 = null;

                Boss.bossAttackAgain = true;

                break;

            default:

                multiObject01 = null;
                multiObject02 = null;

                Boss.bossAttackAgain = true;

                break;
        }
    }

    // 控制各關卡的IncommingHint
    private void updateIncommingHint (int i) {

        switch (i) {
            case 0:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,0);
                worldObjs.add(incommingHint);

                break;

            case 1:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,1);
                worldObjs.add(incommingHint);

                break;

            case 2:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,2);
                worldObjs.add(incommingHint);

                break;

            case 3:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,3);
                worldObjs.add(incommingHint);

                break;

            case 4:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,4);
                worldObjs.add(incommingHint);

                break;

            default:

                incommingHint = new IncommingHint(context,MainActivity.screenWidth*350/1920,MainActivity.screenHeight*200/1080,0);
                worldObjs.add(incommingHint);

                break;
        }
    }


    private void preInit(Context context){

        this.context = context;
        worldObjs = new ArrayList<>();
        moneys = new ArrayList<>();

        worldObjs.add(new Level(context, 0, 0,0, Level.LEVEL_1));
        boss = new Boss(context,
                MainActivity.screenWidth*600/1920,
                MainActivity.screenHeight*100/1080,
                new Boss.Option(0));   //new出一個Boss的實體
        worldObjs.add(boss);

        object = new AttackObject(context,AttackObject.attackObjectX,AttackObject.attackObjectY, new AttackObject.Option(0));
        worldObjs.add(object); //new出一個煙灰缸的實體

        init(context);
    }
    private void preInit(Context context, Level level, Boss.Option option, AttackObject.Option attackObjectOption){
        this.context = context;
        worldObjs = new ArrayList<>();
        moneys = new ArrayList<>();
//        ghosts = new ArrayList<>();

        worldObjs.add(level);//加入背景

        boss = new Boss(context,
                MainActivity.screenWidth*600/1920,
                MainActivity.screenHeight*100/1080,
                option);   //new出一個Boss的實體
        worldObjs.add(boss);

        object = new AttackObject(context,AttackObject.attackObjectX,AttackObject.attackObjectY, attackObjectOption);
        worldObjs.add(object); //new出一個攻擊物件的實體

        init(context);
    }

    public void onPaint(Canvas canvas, Paint paint){

        for(WorldObj worldObj: worldObjs){// for each
            worldObj.onPaint(canvas, paint);
        }

        for(Money money: moneys){       //畫出每一個錢幣
            money.onPaint(canvas,paint);
        }


    }

    public void updateData(){
        for(WorldObj worldObj: worldObjs){// for each
            updateWorldObj(worldObj);

        }


        if(worldStatusInterface != null){

            //判斷電梯到達某個位置觸發換關
            if ( Elevator1.currentStatusE1 == Elevator1.STATUS_OVER || Elevator2.currentStatusE2 == Elevator2.STATUS_OVER ) {

                PlayerBlood.overLife = PlayerBlood.life; //一關結束後，把玩家血量繼承到下一關
                worldStatusInterface.updateStatus(WORLD_STATUS_NEXT);

            }
            else if ( PlayerBlood.life <= 0 ) {

                scoreBoardStatus = YOU_LOSE;

                worldStatusInterface.updateStatus(WORLD_STATUS_OVER);

                //玩家血量在重玩時回滿
                PlayerBlood.life = PlayerBlood.initialLife;
                PlayerBlood.overLife = PlayerBlood.life;

            } else if ( TimerBar2.countdown == 0 ) {

                scoreBoardStatus = TIME_OUT;

                worldStatusInterface.updateStatus(WORLD_STATUS_OVER);

                //玩家血量在重玩時回滿
                PlayerBlood.life = PlayerBlood.initialLife;
                PlayerBlood.overLife = PlayerBlood.life;

            } else {

                scoreBoardStatus = YOU_WIN;

            }

//            // 讓通關後再重玩時的血量回滿
//            if (ElevatorScore01.currentStatusES1 == ElevatorScore01.STATUS_OVER || ElevatorScore02.currentStatusES2 == ElevatorScore02.STATUS_OVER ) {
//
//                //玩家血量在重玩時回滿
//                PlayerBlood.life = PlayerBlood.initialLife;
//                PlayerBlood.overLife = PlayerBlood.life;
//            }
        }
    }

    private void updateWorldObj(WorldObj worldObj) {

    }

    public boolean onTouch(MotionEvent event){
        float position = 0;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指碰到螢幕時觸發
//                for(WorldObj worldObj: worldObjs){// for each
//                    if(worldObj instanceof OnTouchObject){
//                        OnTouchObject oto = (OnTouchObject)worldObj;
//                        if(oto.isTouch(event.getX(), event.getY())){
//                            oto.touchEvent();
//                        }
//                    }
//                }

                int c = moneys.size();
                for(int i=0;i<c;i++){
                    Money money = moneys.get(i);
                    if(money.isTouch(event.getX(), event.getY())){
                        money.touchEvent();
                        moneys.remove(money);
                        i--;c--;
                    }
                }

                if (boss.attack == false && boss.blood > 0 && incommingHint.hintAppearHint == false && enableTouch == true && Elevator1.currentStatusE1 != Elevator1.STATUS_OVER && Elevator2.currentStatusE2 != Elevator2.STATUS_OVER) { //只有老闆不在攻擊模式時，觸碰才會觸發扣血和噴錢

                    // 電梯關門後也不會進這串
                    if(boss.isTouch(event.getX(), event.getY())){

                        if(moneys.size() <= 40){
                            moneys.add(new Money(context, (int)event.getX(), (int)event.getY()));
                        }

                        boss.touchEvent();
                        SkillButton.touchTimes++;
                    }

                }

                if ( object.objectAppear == true && enableTouch == true ) {

                    if (object.isTouch(event.getX(), event.getY())){

                        object.touchEvent();

                    }
                }

                if ( skillButton != null && skillButton.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    skillButton.touchEvent();
                }

                if ( bandanButton != null && bandanButton.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    bandanButton.touchEvent();
                }

                if ( redBullButton != null && redBullButton.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    redBullButton.touchEvent();
                }

                // 唯一不受enableTouch控制的按鈕
                if ( cellphoneButton != null && cellphoneButton.isTouch(event.getX(),event.getY())) {

                    cellphoneButton.touchEvent();
                }

//                // 只有一隻鬼兒會反應，其他點了不會有反應
//                if ( ghost03 != null && ghost03.isTouch(event.getX(),event.getY()) ) {
//
//                    ghost03.touchEvent();
//                    ghost01.touchEvent();
//                    ghost02.touchEvent();
//                    ghost04.touchEvent();
//                    ghost05.touchEvent();
//
//                    // 讓會反應的鬼兒往下跳一隻
//                    ghostTmp = ghost03;
//                    ghost03 = ghost04;
//                    ghost04 = ghost05;
//                    ghost05 = ghost01;
//                    ghost01 = ghost02;
//                    ghost02 = ghostTmp;
//                }

                break;

            case MotionEvent.ACTION_MOVE:
                //碰到之後持續觸發

                int d = moneys.size();
                for(int i=0;i<d;i++){
                    Money money = moneys.get(i);
                    if(money.isTouch(event.getX(), event.getY())){
                        money.touchEvent();
                        moneys.remove(money);
                        i--;d--;
                    }
                }

                if ( ghost01 != null && ghost01.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    ghost01.touchEvent();

                    MainActivity.handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {

                            //過1.5秒後要做的事情
                            ghost01.ghostAppear = false;

                        }}, 1500);
                }

                if ( ghost02 != null && ghost02.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    ghost02.touchEvent();

                    MainActivity.handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {

                            //過1.5秒後要做的事情
                            ghost02.ghostAppear = false;

                        }}, 1500);
                }

                if ( ghost03 != null && ghost03.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    ghost03.touchEvent();

                    MainActivity.handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {

                            //過1.5秒後要做的事情
                            ghost03.ghostAppear = false;

                        }}, 1500);
                }

                if ( ghost04 != null && ghost04.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    ghost04.touchEvent();

                    MainActivity.handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {

                            //過1.5秒後要做的事情
                            ghost04.ghostAppear = false;

                        }}, 1500);
                }

                if ( ghost05 != null && ghost05.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    ghost05.touchEvent();

                    MainActivity.handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {

                            //過1.5秒後要做的事情
                            ghost05.ghostAppear = false;

                        }}, 1500);
                }


                if ( multiObject01 != null && multiObject01.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    multiObject01.touchEvent();
                    multiObject01.multiAppear = false;
                }

                if ( multiObject02 != null && multiObject02.isTouch(event.getX(),event.getY()) && enableTouch == true ) {

                    multiObject02.touchEvent();
                    multiObject02.multiAppear = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                //放開時觸發

                break;
        }
        return true;
    }
    public void updateLimit(int leftLimit, int topLimit, int rightLimit, int botLimit){
        for(WorldObj worldObj: worldObjs){
            worldObj.updateLimit(leftLimit,topLimit,rightLimit,botLimit);
        }
    }

    private static class Param {
        WorldStatusInterface worldStatusInterface;
        Level level;
        Boss.Option option;
        AttackObject.Option attackObject;
    }

    public static class Builder {
        private static Param param;
        private Context context;

        public Builder(Context context){
            this.context = context;
            param = new Param();
        }
        public Builder setWorldStatusInterface(WorldStatusInterface worldStatusInterface){
            param.worldStatusInterface = worldStatusInterface;
            return this;
        }
        public Builder setLevel(int i){
            int level = 0;
            switch (i){
                case 0:
                    level = Level.LEVEL_1;
                    break;
                case 1:
                    level = Level.LEVEL_2;
                    break;
                case 2:
                    level = Level.LEVEL_3;
                    break;
                case 3:
                    level = Level.LEVEL_4;
                    break;
                case 4:
                    level = Level.LEVEL_5;
                    break;
                default:
                    level = Level.LEVEL_1;
                    break;
            }
            param.level = new Level(context, 0, 0,0, level);
            return this;
        }
        public Builder setBoss(int i){

            param.option = new Boss.Option(i);
            return this;
        }
        public Builder setAttackObject(int i){

            param.attackObject = new AttackObject.Option(i);
            return this;
        }


        public World getWorld(){
            return new World(context, param);
        }
    }
}
