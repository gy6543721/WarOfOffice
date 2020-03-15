package com.warofoffice.warofoffice.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import android.view.MotionEvent;
import android.view.View;

import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.obj.buttons.CellphoneButton;
import com.warofoffice.warofoffice.obj.player.PlayerBlood;
import com.warofoffice.warofoffice.scoreboard.Score;
import com.warofoffice.warofoffice.scoreboard.ScoreBoard;
import com.warofoffice.warofoffice.leaderboard.LeaderBoard;
import com.warofoffice.warofoffice.menu.Menu;
import com.warofoffice.warofoffice.obj.World;
import com.warofoffice.warofoffice.obj.boss.Money;
import com.warofoffice.warofoffice.scoreboard.scorecollection.ScoreCollection;
import com.warofoffice.warofoffice.timer.TimerBar2;
import com.warofoffice.warofoffice.tutorial.Tutorial;
import com.warofoffice.warofoffice.tutorial.TutorialBackground;

import org.json.JSONArray;
import org.json.JSONException;

import static com.warofoffice.warofoffice.MainActivity.fightMusic;
import static com.warofoffice.warofoffice.MainActivity.menuMusic;
import static com.warofoffice.warofoffice.leaderboard.LeaderBoard.LEADERBOARD_STOP;

public class GameController implements View.OnTouchListener{

    public static final int GAME_STATUS_MENU = 0;
    public static final int GAME_STATUS_GAME = 1;
    public static final int GAME_STATUS_SCOREBOARD = 2;
    public static final int GAME_STATUS_TUTORIAL = 3;
    public static final int GAME_STATUS_LEADERBOARD = 4;

    private Context context;

    // menu
    // game
    private Menu menu;      //import 這個Class進來
    private World world;

    //scoreBoard
    private ScoreBoard scoreBoard;

    private Tutorial tutorial;

    private LeaderBoard leaderBoard;

    private World.Builder builder;
    public static int currentLevel; //控制現在第幾關的變數

    //
    private int currentStatus;

    private int botLimit;
    private int topLimit;
    private int leftLimit;
    private int rightLimit;

    //稱號名稱
    private String leaderName;

    //彩蛋
    public static boolean easterEgg;

    public GameController(final Context context){
        this.context = context;
        currentStatus = GAME_STATUS_MENU;

        currentLevel = 0;

        builder = new World.Builder(context);




        world = builder.setBoss(currentLevel)
                .setLevel(currentLevel)
                .setAttackObject(currentLevel)
                .setWorldStatusInterface(new World.WorldStatusInterface() {

            @Override
            public void updateStatus(int status) {
                switch(status){
//                    case World.WORLD_STATUS_START:
//                        currentStatus = GAME_STATUS_GAME;
//                        break;
                    case World.WORLD_STATUS_STOP:
                        currentStatus = GAME_STATUS_MENU;

                        break;
                    case World.WORLD_STATUS_NEXT:
                        currentLevel++;

                        //這邊設定每當破一關時候的分數計算
                        Score.score += TimerBar2.countdown * 300;

                        if(currentLevel > 4){ //通關後進scoreboard並重設血量

                            easterEgg = true;

                            // buttonStart也有這串，回復玩家血量用
                            PlayerBlood.life = PlayerBlood.initialLife;
                            PlayerBlood.overLife = PlayerBlood.life;

                            CellphoneButton.cellphoneCount = 0; //讓撒錢歸零

                            //原本是直接傳狀態 我把World的變數改成static然後在這裡改變狀態來達到最後一關結束可以重新開始的狀態
                            World.worldStatusInterface.updateStatus(World.WORLD_STATUS_OVER);
                            return;
                        }

                        world = builder.setLevel(currentLevel).setBoss(currentLevel).setAttackObject(currentLevel).getWorld();
                        //用新的world覆蓋掉原本的world，currentLevel會到World變成Option()的變數i進入Boss和Level的設定(0~4)
                        //每觸發到WORLD_STATUS_NEXT，currentLevel就會增加1，i也增加1


                        break;
                    case World.WORLD_STATUS_OVER:

                        checkLeaderName();

                        getLeaderBoard();   //取得排名的方法

                        currentStatus = GAME_STATUS_SCOREBOARD;

                        currentLevel = 0;   //當血量歸零從第一關開始
                        break;
                }
            }
        }).getWorld();

        menu = new Menu(context, new Menu.MenuStatusInterface() {
            @Override
            public void updateStatus(int status) {
                switch(status){
//                    case Menu.MENU_START:
//                        currentStatus = GAME_STATUS_MENU;
//                        break;
                    case Menu.MENU_GAME_START:
                        currentStatus = GAME_STATUS_GAME;
                        break;

                    case Menu.MENU_TUTORIAL:
                        currentStatus = GAME_STATUS_TUTORIAL;
                        break;

                    case Menu.MENU_LEADERBOARD:
                        currentStatus = GAME_STATUS_LEADERBOARD;
                        break;

                }
            }
        });
        scoreBoard = new ScoreBoard(context, new ScoreBoard.ScoreBoardInterface() {
            @Override
            public void updateStatus(int status) {
                switch(status){
                    case ScoreBoard.SCOREBOARD_STOP:

                        easterEgg = false;

                        menu = menu.getMenu();

                        world = builder.setLevel(currentLevel).setBoss(currentLevel).setAttackObject(currentLevel).getWorld();

                        currentStatus = GAME_STATUS_MENU;

                        Money.moneyNumber = 0;  //當這個按鈕按下時金錢歸零

                        scoreBoard.init(context); //重設所有scoreBoard
                        break;
                }
            }
        });

        tutorial = new Tutorial(context, new Tutorial.TutorialInterface() {
            @Override
            public void updateStatus(int status) {

                switch(status){
                    case Tutorial.TUTORIAL_STOP:

                        menu = menu.getMenu();

                        world = builder.setLevel(currentLevel).setBoss(currentLevel).setAttackObject(currentLevel).getWorld();

                        currentStatus = GAME_STATUS_MENU;

                        tutorial.init(context); //重設所有tutorial
                        break;
                }
            }
        });

        leaderBoard = new LeaderBoard(context, new LeaderBoard.LeaderBoardInterface() {
            @Override
            public void updateStatus(int status) {
                switch(status){
                    case LEADERBOARD_STOP:

                        menu = menu.getMenu();

                        world = builder.setLevel(currentLevel).setBoss(currentLevel).setAttackObject(currentLevel).getWorld();

                        currentStatus = GAME_STATUS_MENU;

//                        Money.moneyNumber = 0;  //當這個按鈕按下時金錢歸零

                        leaderBoard.init(context); //重設所有scoreBoard  沒有的話會進不了下次判斷
                        break;

                }
            }
        });

    }

    public void run(){
        switch (currentStatus){
            case GAME_STATUS_MENU:

                menuMusic.start();  //暫時先讓這個音樂在這裡時開始撥放

                menu.updateData();
                break;
            case GAME_STATUS_GAME:

                menuMusic.pause();
                menuMusic.seekTo(0); //把時間reset回0秒
                fightMusic.start();

                world.updateData();
                break;
            case GAME_STATUS_SCOREBOARD:

                fightMusic.pause();
                fightMusic.seekTo(0); //把時間reset回0秒

                scoreBoard.updateData();
                break;

            case GAME_STATUS_TUTORIAL:

                tutorial.updateData();
                break;
            case GAME_STATUS_LEADERBOARD:

                leaderBoard.updateData();
                break;


        }
    }

    public void onPaint(Canvas canvas, Paint paint){
        switch (currentStatus){
            case GAME_STATUS_MENU:
                menu.onPaint(canvas, paint);
                break;
            case GAME_STATUS_GAME:
                world.onPaint(canvas, paint);
                break;
            case GAME_STATUS_SCOREBOARD:
                scoreBoard.onPaint(canvas, paint);
                break;
            case GAME_STATUS_TUTORIAL:
                tutorial.onPaint(canvas, paint);
                break;
            case GAME_STATUS_LEADERBOARD:
                leaderBoard.onPaint(canvas,paint);
                break;
        }
    }

    public void updateLimit(int topLimit, int leftLimit, int botLimit, int rightLimit){
        this.topLimit = topLimit;
        this.leftLimit = leftLimit;
        this.botLimit = botLimit;
        this.rightLimit = rightLimit;

        world.updateLimit(leftLimit, topLimit, rightLimit, botLimit);       //把世界的邊界抓進來
        //        menu.updateLimit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (this.currentStatus){
            case GAME_STATUS_MENU:
                return menu.onTouch(event);
            case GAME_STATUS_GAME:
                return world.onTouch(event);        //一個Callback
            case GAME_STATUS_SCOREBOARD:
                return scoreBoard.onTouch(event);
            case GAME_STATUS_TUTORIAL:
                return tutorial.onTouch(event);
            case GAME_STATUS_LEADERBOARD:
                return leaderBoard.onTouch(event);
        }
        return true;
    }
    public void getLeaderBoard(){
        JSONArray jsonArray;
        ScoreCollection scoreCollection;
        try {
            jsonArray = new JSONArray(MainActivity.sharedPreferences.getString("JSON","[]"));
            scoreCollection = new ScoreCollection(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        if(Score.score < 0){
            return;
        }


        scoreCollection.add(MainActivity.name, Score.score, leaderName);

        MainActivity.sharedPreferences.edit().putString("JSON", scoreCollection.toJSONArray().toString()).commit();

    }
    public void checkLeaderName(){
        switch(currentLevel){
            case 0:
                leaderName = "菜鳥實習生";
                break;
            case 1:
                leaderName = "操勞業務";
                break;
            case 2:
                leaderName = "爆肝HR";
                break;
            case 3:
                leaderName = "燒腦PM";
                break;
            case 4:
                leaderName = "幹話副總";
                break;
            case 5:
                leaderName = "新老闆";
                break;
        }
    }
}
