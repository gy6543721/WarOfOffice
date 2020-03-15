package com.warofoffice.warofoffice.scoreboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.warofoffice.warofoffice.MainActivity;

import com.warofoffice.warofoffice.controller.GameController;
import com.warofoffice.warofoffice.obj.OnTouchObject;
import com.warofoffice.warofoffice.obj.World;

import java.util.ArrayList;
import java.util.List;

import static com.warofoffice.warofoffice.MainActivity.sharedPreferences;
import static com.warofoffice.warofoffice.scoreboard.ExitButton.YES_SBUTTON;

public class ScoreBoard {
    public static final int SCOREBOARD_START = 1;         //切換狀態的屬性
    public static final int SCOREBOARD_STOP  = 0;


    public interface ScoreBoardInterface{         //介面用來告知controller的方法
        public void updateStatus(int status);

    }


    private ScoreBoardInterface scoreBoardInterface;  //設一個介面的變數用來知道現在的狀態
    private Context context;                         //當前畫面的參數

    private List<ScoreBoardObj> scoreBoardObjs;      //一個存menuObj物件的變數用來代表ArrayList


    //因為要畫在文字上層，不能綁在scoreBoardObjs禮onPaint
    private ElevatorScore01 elevatorScore01;
    private ElevatorScore02 elevatorScore02;


    public ScoreBoard(Context context){       //處理沒有介面的狀態的情況下Menu要能正常運行
        init(context);
    }
    public ScoreBoard(Context context,ScoreBoardInterface scoreBoardInterface){      //處理有介面狀態的情況下
        init(context);
        this.scoreBoardInterface = scoreBoardInterface;
    }

    public void init(Context context){
        this.context = context;
        scoreBoardObjs = new ArrayList<>();

        scoreBoardObjs.add(new ScoreBoardBackground(context,0,0));
        scoreBoardObjs.add(new Score(context ,MainActivity.screenWidth*710/1920,MainActivity.screenHeight*650/1080));
        scoreBoardObjs.add(new ExitButton(context,MainActivity.screenWidth*1690/1920,MainActivity.screenHeight*880/1080));

        elevatorScore01 = new ElevatorScore01(context,MainActivity.screenWidth*520/1920,MainActivity.screenHeight*120/1080); // 450,90
        elevatorScore02 = new ElevatorScore02(context,MainActivity.screenWidth*945/1920,MainActivity.screenHeight*120/1080); // 1000,90
    }

    public void onPaint(Canvas canvas, Paint paint){        //用來畫出當前物件


        for(ScoreBoardObj scoreBoardObj : scoreBoardObjs){        //跑過所有的menuObj
            scoreBoardObj.onPaint(canvas, paint);     //畫出所有的menuObj物件
        }

        String resultOut = winOrLose(); // 你贏了 你輸了 時間到

        //直接畫文字
        paint.setColor(Color.parseColor("#000000")); //決定畫筆顏色 最重要 傳入參數是int //Color.parseColor() 把色碼轉成int  //Color.BLACK 是一個 public static final int BLACK
        paint.setStrokeWidth(25);
        paint.setStrokeWidth(10); //線條寬度
        paint.setTextSize(MainActivity.screenWidth*120/1920); //決定文字的大小
        paint.setTextAlign(Paint.Align.LEFT); //決定文字座標  在y軸上會對應baseLine 在y軸上是不會置中的


        canvas.drawText(resultOut,MainActivity.screenWidth*740/1920,MainActivity.screenHeight*400/1080,paint);



        if (MainActivity.name.equals(null) || MainActivity.name.equals("")) {

            MainActivity.name = "你";

        }

        //直接畫文字
        paint.setColor(Color.parseColor("#000000")); //決定畫筆顏色 最重要 傳入參數是int //Color.parseColor() 把色碼轉成int  //Color.BLACK 是一個 public static final int BLACK
        paint.setStrokeWidth(25);
        paint.setStrokeWidth(10); //線條寬度
        paint.setTextSize(MainActivity.screenWidth*80/1920); //決定文字的大小
        paint.setTextAlign(Paint.Align.LEFT); //決定文字座標  在y軸上會對應baseLine 在y軸上是不會置中的


        canvas.drawText(MainActivity.name + "的得分：" ,MainActivity.screenWidth*620/1920,MainActivity.screenHeight*550/1080,paint);

        // 這樣就能擋住字了
        elevatorScore01.onPaint(canvas, paint);
        elevatorScore02.onPaint(canvas, paint);

    }

    public String winOrLose() {

        String result = "";

        if (World.scoreBoardStatus == World.TIME_OUT) {

            result = "時間到";

        } else if (World.scoreBoardStatus == World.YOU_WIN) {

            result = "你贏了";

        } else if (World.scoreBoardStatus == World.YOU_LOSE) {

            result = "你輸了";

        }

        return result;
    }


    public void updateData(){

//        //用來判斷
//
//        for(ScoreBoardObj scoreBoardObj : scoreBoardObjs){
//            updateMenuObj(scoreBoardObj);
//
//        }

        //
        if(scoreBoardInterface != null){

//            if(ExitButton.currentStatus == YES_SBUTTON){
//                scoreBoardInterface.updateStatus(SCOREBOARD_STOP);
//            }

            // if(elevatorScore01.currentStatusES1 == elevatorScore01.STATUS_OVER || elevatorScore02.currentStatusES2 == elevatorScore02.STATUS_OVER){
            if(elevatorScore01.currentStatusES1 == elevatorScore01.STATUS_OVER){
                scoreBoardInterface.updateStatus(SCOREBOARD_STOP);

                // 把有new出實體的東西清空，避免重玩時越畫越多層
                scoreBoardObjs.add(elevatorScore01);
                scoreBoardObjs.add(elevatorScore02);
                scoreBoardObjs.remove(elevatorScore01);
                scoreBoardObjs.remove(elevatorScore02);

                Score.score = 0;
            }
        }
    }

//    public void updateMenuObj(ScoreBoardObj scoreBoardObj){     //更新裡面的狀態
//
//    }

    public boolean onTouch(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                //手指碰到螢幕時觸發
                for(ScoreBoardObj scoreBoardObj : scoreBoardObjs){// for each
                    if(scoreBoardObj instanceof OnTouchObject){
                        OnTouchObject oto = (OnTouchObject)scoreBoardObj;
                        if(oto.isTouch(event.getX(), event.getY())){
                            oto.touchEvent();
                        }
                    }
                }
//                try{
//                    TransparentInputBox inputBox = TransparentInputBox.getInstance(context);
//
//                    inputBox.checkTouch(event.getX(), event.getY(), "a");
//
//                }catch(TransparentInputBox.InputBoxException e){
//                    Log.d("test", e.getMessage());
//                }

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
