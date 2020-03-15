package com.warofoffice.warofoffice.leaderboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.warofoffice.warofoffice.MainActivity;
import com.warofoffice.warofoffice.controller.GameController;
import com.warofoffice.warofoffice.scoreboard.Score;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoard {
    public static final int LEADERBOARD_START = 1;
    public static final int LEADERBOARD_STOP = 0;

    public interface LeaderBoardInterface{

        public void updateStatus(int status);

    }
    private LeaderBoardInterface leaderBoardInterface;

    private Context context;

    private List<LeaderBoardObj> leaderBoardObjs;

    //電梯物件
    private LeaderBoardEL01 leaderBoardEL01;
    private LeaderBoardEL02 leaderBoardEL02;

    private LeaderBoardOutfit leaderBoardOutfit;

    private LeaderBoardExitButton leaderBoardExitButton;

    //JSONArray
    private JSONArray jsonArray;

    public LeaderBoard(Context context){
        init(context);

    }
    public LeaderBoard(Context context, LeaderBoardInterface leaderBoardInterface){
        init(context);
        this.leaderBoardInterface = leaderBoardInterface;

    }


    public void init(Context context){
        this.context = context;

    leaderBoardObjs = new ArrayList<>();   //把裝這個type的ArrayList建出來

    leaderBoardObjs.add(new LeaderBoardBackground(context,0,0,0));

    leaderBoardEL01 = new LeaderBoardEL01(context,MainActivity.screenWidth*960/1920,0); // 450,90 // 這裡的x是左邊電梯右上角的位置
    leaderBoardEL02 = new LeaderBoardEL02(context,MainActivity.screenWidth*960/1920,0); // 1000,90

    leaderBoardOutfit = new LeaderBoardOutfit(context, 0, 0 ,0);

    leaderBoardExitButton = new LeaderBoardExitButton(context,MainActivity.screenWidth*1690/1920,MainActivity.screenHeight*880/1080);
    }

    public void onPaint(Canvas canvas, Paint paint){
        for(LeaderBoardObj scoreBoardObj : leaderBoardObjs){        //跑過所有的menuObj
            scoreBoardObj.onPaint(canvas, paint);     //畫出所有的menuObj物件
        }

        //直接畫文字
        paint.setColor(Color.parseColor("#000000")); //決定畫筆顏色 最重要 傳入參數是int //Color.parseColor() 把色碼轉成int  //Color.BLACK 是一個 public static final int BLACK
        paint.setStrokeWidth(25);
        paint.setStrokeWidth(10); //線條寬度
        paint.setTextSize(MainActivity.screenWidth*80/1920); //決定文字的大小
        paint.setTextAlign(Paint.Align.LEFT); //決定文字座標  在y軸上會對應baseLine 在y軸上是不會置中的

        try{

            jsonArray = new JSONArray(MainActivity.sharedPreferences.getString("JSON",""));

            canvas.drawText(jsonArray.getJSONObject(0).getString("LEADERNAME")
                            + "    "
                            +jsonArray.getJSONObject(0).getString("NAME")
                            + "    "
                            + jsonArray.getJSONObject(0).getInt("SCORE")

                    ,MainActivity.screenWidth*450/1920
                    ,MainActivity.screenHeight*380/1080
                    ,paint);
            canvas.drawText(jsonArray.getJSONObject(1).getString("LEADERNAME")
                            + "    "
                            +jsonArray.getJSONObject(1).getString("NAME")
                            + "    "
                            + jsonArray.getJSONObject(1).getInt("SCORE")
                    ,MainActivity.screenWidth*450/1920
                    ,MainActivity.screenHeight*530/1080
                    ,paint);
            canvas.drawText(jsonArray.getJSONObject(2).getString("LEADERNAME")
                            + "    "
                            +jsonArray.getJSONObject(2).getString("NAME")
                            + "    "
                            + jsonArray.getJSONObject(2).getInt("SCORE")
                    ,MainActivity.screenWidth*450/1920
                    ,MainActivity.screenHeight*680/1080
                    ,paint);
            canvas.drawText(jsonArray.getJSONObject(3).getString("LEADERNAME")
                            + "    "
                            +jsonArray.getJSONObject(3).getString("NAME")
                            + "    "
                            + jsonArray.getJSONObject(3).getInt("SCORE")
                    ,MainActivity.screenWidth*450/1920
                    ,MainActivity.screenHeight*830/1080
                    ,paint);
            canvas.drawText(jsonArray.getJSONObject(4).getString("LEADERNAME")
                            + "    "
                            +jsonArray.getJSONObject(4).getString("NAME")
                            + "    "
                            + jsonArray.getJSONObject(4).getInt("SCORE")
                    ,MainActivity.screenWidth*450/1920
                    ,MainActivity.screenHeight*980/1080
                    ,paint);

        }catch (JSONException e){

        }

        // 這樣就能擋住字了

        leaderBoardEL02.onPaint(canvas, paint);
        leaderBoardEL01.onPaint(canvas, paint);

        leaderBoardOutfit.onPaint(canvas,paint);

        leaderBoardExitButton.onPaint(canvas,paint);

    }
    public void updateData(){


        if(leaderBoardInterface != null){


            if( LeaderBoardEL01.currentStatusLB1 == LeaderBoardEL01.LB1_STATUS_OVER){
                leaderBoardInterface.updateStatus(LEADERBOARD_STOP);

                // 把有new出實體的東西清空，避免重玩時越畫越多層
                leaderBoardObjs.add(leaderBoardEL01);
                leaderBoardObjs.add(leaderBoardEL02);
                leaderBoardObjs.add(leaderBoardExitButton);
                leaderBoardObjs.remove(leaderBoardEL02);
                leaderBoardObjs.remove(leaderBoardEL02);
                leaderBoardObjs.remove(leaderBoardExitButton);

                Score.score = 0;
            }
        }
    }



    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //手指碰到螢幕時觸發
                if ( leaderBoardExitButton != null && leaderBoardExitButton.isTouch(event.getX(),event.getY())) {

                    leaderBoardExitButton.touchEvent();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                //碰到之後持續觸發
                break;
            case MotionEvent.ACTION_UP:
                //放開時觸發

                break;
        }
        return true;
    }
}
